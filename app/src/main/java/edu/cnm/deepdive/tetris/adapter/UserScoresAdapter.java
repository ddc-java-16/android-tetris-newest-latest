package edu.cnm.deepdive.tetris.adapter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import edu.cnm.deepdive.tetris.R;
import edu.cnm.deepdive.tetris.adapter.UserScoresAdapter.Holder;
import edu.cnm.deepdive.tetris.databinding.ItemUserScoreBinding;
import edu.cnm.deepdive.tetris.model.entity.pojo.UserScore;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class UserScoresAdapter extends RecyclerView.Adapter<Holder> {

private final Context context;
private final List<UserScore> scores;
private final LayoutInflater inflater;
private final DateTimeFormatter formatter;
private final NumberFormat numberFormatter;
private final ZoneId zone;

@ColorInt
private int oddRowBackground;
@ColorInt
private final int evenRowBackground;

public UserScoresAdapter(Context context, List<UserScore> scores) {
  this.context = context;
  this.scores = scores;
  this.inflater = LayoutInflater.from(context);
  formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT, FormatStyle.SHORT);
  zone = ZoneId.systemDefault();
  oddRowBackground = context.getColor(R.color.odd_row_background);
  evenRowBackground = context.getColor(R.color.even_row_background);
  numberFormatter = NumberFormat.getIntegerInstance();
}
  @NonNull

  @Override
  public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new Holder(ItemUserScoreBinding.inflate(inflater, parent, false));
  }

  @Override
  public void onBindViewHolder(@NonNull Holder holder, int position) {
  holder.bind(position);

  }

  @Override
  public int getItemCount() {
    return scores.size();
  }


  public class Holder  extends RecyclerView.ViewHolder {

  private final ItemUserScoreBinding binding;

    private Holder(
        @NonNull ItemUserScoreBinding binding) {
      super(binding.getRoot());
      this.binding = binding;

    }
    private void bind(int position) {
      UserScore score = scores.get(position);
      binding.ranking.setText(String.valueOf(position + 1));
      binding.displayname.setText(score.getDisplayName());
      binding.score.setText(String.valueOf(score.getValue()));
      binding.score.setText(numberFormatter.format(score.getValue()));
      binding.rowsRemoved.setText(numberFormatter.format(score.getRows_removed()));
      binding.created.setText(LocalDateTime.ofInstant(score.getCreated(), zone).format(formatter));
      binding.getRoot().setBackgroundColor((position % 2 == 0) ? evenRowBackground : oddRowBackground);
  }

}
}
