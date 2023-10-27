package edu.cnm.deepdive.tetris.controller;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import edu.cnm.deepdive.tetris.R;
import edu.cnm.deepdive.tetris.adapter.NextQueueAdapter;
import edu.cnm.deepdive.tetris.databinding.FragmentGameBinding;
import edu.cnm.deepdive.tetris.model.Dealer;
import edu.cnm.deepdive.tetris.model.Field;
import edu.cnm.deepdive.tetris.model.entity.Score;
import edu.cnm.deepdive.tetris.model.entity.User;
import edu.cnm.deepdive.tetris.viewmodel.PlayingFieldViewModel;
import edu.cnm.deepdive.tetris.viewmodel.ScoreViewModel;
import edu.cnm.deepdive.tetris.viewmodel.UserViewModel;
import java.time.Instant;
import org.jetbrains.annotations.NotNull;

public class GameFragment extends Fragment {


private FragmentGameBinding binding;
private PlayingFieldViewModel playingFieldViewModel;
private ScoreViewModel scoreViewModel;
private UserViewModel userViewModel;
private int score;
private User currentUser;
private Boolean inProgress;

private int level;
private int rowsRemoved;
private User user;

  public GameFragment() {
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);

  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    setupUI(inflater, container);
    return binding.getRoot();
  }


  @Override
  public void onViewCreated(@NonNull View view,
      @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    setupViewmodels();
  }

  @Override
  public void onCreateOptionsMenu(@NonNull Menu menu,
      @NonNull MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    inflater.inflate(R.menu.game_options, menu);
  }


  // FIXME: 10/27/23 FIXME FIXME
  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    boolean handled = true;
    int id = item.getItemId();
    if (id == R.id.play) {
      playingFieldViewModel.run();;
    } else if (id == R.id.pause){
      playingFieldViewModel.stop();
    } else if(id == R.id.restart) {
      playingFieldViewModel.create();
    }else {
      handled = super.onOptionsItemSelected(item);
    }
    return handled;
  }

  private void setupViewmodels() {
    LifecycleOwner owner = getViewLifecycleOwner();
    FragmentActivity activity = requireActivity();
    setUpPlayingFieldViewModel(activity, owner);
    setupScoreViewModel(activity, owner);
    setUpUserViewModel(activity, owner);
  }

  private void setUpUserViewModel(FragmentActivity activity, LifecycleOwner owner) {
    userViewModel = new ViewModelProvider(activity)
        .get(UserViewModel.class);
    userViewModel
        .getCurrentUser()
        .observe(owner, (user) -> this.currentUser = user);
  }

  private void setupScoreViewModel(FragmentActivity activity, LifecycleOwner owner) {
    scoreViewModel = new ViewModelProvider(activity)
        .get(ScoreViewModel.class);
    //TODO: 10/26/23
  }

  private void setUpPlayingFieldViewModel(FragmentActivity activity, LifecycleOwner owner) {
    playingFieldViewModel =  new ViewModelProvider(activity)
        .get(PlayingFieldViewModel.class);
    playingFieldViewModel.getPlayingField()
        .observe(owner, this::handlePlayingField);
    playingFieldViewModel
        .getDealer()
        .observe(owner, this::handleDealer);
    playingFieldViewModel
        .getInProgress()
        .observe(owner, this::handleInProgress);
  }

  private void handleInProgress(Boolean inProgress) {
    if(Boolean.FALSE.equals(inProgress) && Boolean.TRUE.equals(this.inProgress)) {
      Score score = new Score();
      score.setCreated(Instant.now()); //FIXME get from repository
      score.setValue(this.score);
      score.setRowsRemoved(rowsRemoved);
      scoreViewModel.save(score, currentUser);
    } else {
      this.inProgress = inProgress;
    }
  }

  private void handleDealer(Dealer dealer) {
    NextQueueAdapter adapter = new NextQueueAdapter(requireContext(), dealer.getQueue());
    binding.nextQueue.setAdapter(adapter);
  }

  private void handlePlayingField(Field playingField) {
    score = playingField.getScore();
    level = playingField.getLevel();
    rowsRemoved = playingField.getRowsRemoved();
    binding.playingField.post(() -> binding.playingField.setPlayingField(playingField));
    binding.rowsRemoved.setText((String.valueOf(playingField.getRowsRemoved())));

    binding.level.setText(String.valueOf(score));
    binding.score.setText((String.valueOf(score)));
    int visibility = playingField.isGameOver() ? View.VISIBLE : View.GONE;
    if (playingField.isGameOver()) {
      binding.finalScore.setText(getString(R.string.final_score_format, playingField.getScore()));
      binding.gameOver.setVisibility(visibility);
      binding.showScores.setVisibility(visibility);
      binding.finalScore.setVisibility(visibility);
binding.gameOverLayout.setVisibility(playingField.isGameOver() ? View.VISIBLE : View.GONE);
    }
  }

  private void setupUI(LayoutInflater inflater, ViewGroup container) {
    binding = FragmentGameBinding.inflate(inflater, container, false);
    binding.moveLeft.setOnClickListener((v) -> playingFieldViewModel.moveLeft());
    binding.moveRight.setOnClickListener((v) -> playingFieldViewModel.moveRight());
    binding.rotateRight.setOnClickListener((v) -> playingFieldViewModel.rotateRight());
    binding.rotateLeft.setOnClickListener((v) -> playingFieldViewModel.rotateLeft());
    binding.drop.setOnClickListener((v) -> playingFieldViewModel.drop());
    binding.showScores.setOnClickListener((v) -> Navigation.findNavController(binding.getRoot())
        .navigate(GameFragmentDirections.navigateToScores(score)));
  }
  }
