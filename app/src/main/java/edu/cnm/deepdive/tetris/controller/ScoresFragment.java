package edu.cnm.deepdive.tetris.controller;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import edu.cnm.deepdive.tetris.R;
import edu.cnm.deepdive.tetris.adapter.UserScoresAdapter;
import edu.cnm.deepdive.tetris.databinding.FragmentScoresBinding;
import edu.cnm.deepdive.tetris.viewmodel.ScoreViewModel;
import org.jetbrains.annotations.NotNull;


public class ScoresFragment extends Fragment {
  private ScoreViewModel viewmodel;


 private FragmentScoresBinding binding;
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    }


  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    binding = FragmentScoresBinding.inflate(inflater, container, false);
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view,
      @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    new ViewModelProvider(requireActivity())
        .get(ScoreViewModel.class)
        .getAllScores()
        .observe(getViewLifecycleOwner(), (scores) ->
            binding.scores.setAdapter(new UserScoresAdapter(requireContext(), scores)));
    ActionBar actionbar = ((AppCompatActivity)requireActivity()).getSupportActionBar();
    //noinspection DataFlowIssue
    actionbar.setDisplayHomeAsUpEnabled(true);
    actionbar.setDisplayShowHomeEnabled(true);
  }
}