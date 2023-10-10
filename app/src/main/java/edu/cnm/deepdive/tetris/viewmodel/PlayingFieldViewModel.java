package edu.cnm.deepdive.tetris.viewmodel;

import android.content.Context;
import android.content.res.Resources;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.preference.PreferenceManager;
import dagger.hilt.android.lifecycle.HiltViewModel;
import dagger.hilt.android.qualifiers.ApplicationContext;
import edu.cnm.deepdive.tetris.R;
import edu.cnm.deepdive.tetris.model.Dealer;
import edu.cnm.deepdive.tetris.model.Field;
import edu.cnm.deepdive.tetris.service.PlayingFieldRepository;
import edu.cnm.deepdive.tetris.service.PreferencesRepository;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import javax.inject.Inject;
import org.jetbrains.annotations.NotNull;

@HiltViewModel

public class PlayingFieldViewModel extends ViewModel implements DefaultLifecycleObserver {
  private final Context context;
  private final Resources resources;

  private final PlayingFieldRepository playingFieldRepository;
  private final PreferencesRepository preferencesRepository;
  private final MutableLiveData<Throwable> throwable;
  private final CompositeDisposable pending;
  private int playingFieldWidth;
  private final String playingFieldWidthKey;
  private final  int playingFieldWidthDefault;
  @Inject

  PlayingFieldViewModel(@ApplicationContext Context context,
      PlayingFieldRepository playingFieldRepository,
      PreferencesRepository preferencesRepository) {
    this.context = context;
    resources = context.getResources();
    this.playingFieldRepository = playingFieldRepository;
    this.preferencesRepository = preferencesRepository;

    throwable = new MutableLiveData<>();
    pending = new CompositeDisposable();
    playingFieldWidthDefault = resources.getInteger(R.integer.playing_field_width_default);
    playingFieldWidthKey = resources.getString(R.string.playing_field_width_key);
    playingFieldWidth = playingFieldWidthDefault;
    preferencesRepository.registerPreferencesChangedListener((prefs, key) -> {
      if( key.equals(playingFieldWidthKey) ) {
        playingFieldWidth = prefs.getInt(playingFieldWidthKey, playingFieldWidthDefault);
        // TODO check if playing field good idea
      }
    });

    create();

  }

  public LiveData<Field> getPlayingField() {
    return playingFieldRepository.getPlayingField();
  }
  public LiveData<Dealer> getDealer() {
    return playingFieldRepository.getDealer();
  }

  public LiveData<Throwable> getThrowable() {
    return throwable;
  }
  public void create() {
    int width = PreferenceManager.getDefaultSharedPreferences(context)
        .getInt(context.getString(R.string.playing_field_width_key),  resources.getInteger(R.integer.playing_field_width_default));
   ;
   Disposable disposable =playingFieldRepository.create(25, width, 5, 5 )
    .subscribe(() -> {
        },
        throwable::postValue
    );
   pending.add(disposable);
  }
  public void start(){
    // TODO: 10/6/23 Invoke start in repository......
  }

  public void moveLeft() {
    // TODO: 10/6/23 Invoke Moveleft in repository........

  }
  public void moveRight() {


  }
  public void rotateRight(){

  }
  public void rotateLeft() {

  }
  public void drop() {

  }

  @Override
  public void onStop(@NotNull LifecycleOwner owner) {
    DefaultLifecycleObserver.super.onStop(owner);
    pending.clear();
  }
}
