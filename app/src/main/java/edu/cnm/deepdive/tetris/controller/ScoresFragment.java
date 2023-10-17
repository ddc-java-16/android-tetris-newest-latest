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
import androidx.navigation.Navigation;
import edu.cnm.deepdive.tetris.R;
import edu.cnm.deepdive.tetris.databinding.FragmentScoresBinding;
import org.jetbrains.annotations.NotNull;


public class ScoresFragment extends Fragment {

 private FragmentScoresBinding binding;
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    binding = FragmentScoresBinding.inflate(inflater, container, false);

    binding.dummyText.setText(String.valueOf(ScoresFragmentArgs.fromBundle(getArguments()).getScore()));
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view,
      @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
     // TODO Connect to veiwmodels and add observers
    //noinspection DataFlowIssue
    ActionBar actionbar = ((AppCompatActivity)getActivity()).getSupportActionBar();
    actionbar.setDisplayHomeAsUpEnabled(true);
    actionbar.setDisplayShowHomeEnabled(true);
  }
}