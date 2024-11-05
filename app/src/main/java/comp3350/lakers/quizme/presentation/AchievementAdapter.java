package comp3350.lakers.quizme.presentation;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ProgressBar;
import com.example.quizme.R;
import java.util.List;

import comp3350.lakers.quizme.objects.achievements.Achievement;

public class AchievementAdapter extends RecyclerView.Adapter<AchievementAdapter.AchievementViewHolder>{

    private List<Achievement> achievements;
    private int rowLayout;

    public AchievementAdapter(List<Achievement> achievements, int rowLayout) {
        this.achievements = achievements;
        this.rowLayout = rowLayout;
    }

    @Override
    public AchievementViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new AchievementViewHolder(view);

    }

    @Override
    public void onBindViewHolder(AchievementViewHolder holder, final int position) {
        if(achievements.get(position).getProgressPercent() >= 100) {
            holder.achievementName.setText(String.format("%s - COMPLETE", achievements.get(position).getName()));
        }
        else
        {
            holder.achievementName.setText(achievements.get(position).getName());
        }
        holder.achievementDescription.setText(achievements.get(position).getDescription());
        holder.achievementProgressBar.setProgress(achievements.get(position).getProgressPercent());
    }

    @Override
    public int getItemCount() {
        return achievements.size();
    }

    public static class AchievementViewHolder extends RecyclerView.ViewHolder {
        TextView achievementName;
        TextView achievementDescription;
        ProgressBar achievementProgressBar;

        public AchievementViewHolder(View achievementView) {
            super(achievementView);
            achievementName = (TextView) achievementView.findViewById(R.id.achievementName);
            achievementDescription = (TextView) achievementView.findViewById(R.id.achievementDescription);
            achievementProgressBar = (ProgressBar) achievementView.findViewById(R.id.achievementProgressBar);
        }
    }



}
