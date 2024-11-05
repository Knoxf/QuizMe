package comp3350.lakers.quizme.presentation;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import comp3350.lakers.quizme.application.Main;
import comp3350.lakers.quizme.logic.AchievementHandler;
import comp3350.lakers.quizme.presentation.MainActivity;
import com.example.quizme.R;

import comp3350.lakers.quizme.logic.QuizHandler;
import comp3350.lakers.quizme.objects.quizzes.QuizBase;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.ViewHolder> {

    private final Context context;
    private final String userId;
    private QuizHandler quizHandler;
    private AchievementHandler achievementHandler;

    private MainActivity mainActivity;

    public CategoryListAdapter(Context context, QuizHandler quizHandler, String userId , MainActivity mainActivity, AchievementHandler achievementHandler) {
        this.mainActivity = mainActivity;
        this.context = context;
        this.quizHandler = quizHandler;
        this.userId = userId;
        this.achievementHandler = achievementHandler;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.quiz_list_view_row, parent, false);
        return new CategoryListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CategoryListAdapter.ViewHolder holder, int position) {
        QuizBase question = quizHandler.getQuiz(position);
        holder.quizNameView.setText(question.getQuizName());

        holder.quizNameView.setOnClickListener(v -> {
            Intent intent = new Intent(context, SingleQuiz.class);
            intent.putExtra("Question", question.getQuestion());
            intent.putExtra("Position", position);
            intent.putExtra("userId", userId);
            intent.putExtra("from" , "name");
            intent.putExtra("achievementUserId", achievementHandler.getUserId());
            context.startActivity(intent);
        });

        holder.quizNameView.setOnLongClickListener(v -> {
            boolean deletedQuestion = quizHandler.deleteQuiz(holder.getAdapterPosition(), userId);
            quizHandler = new QuizHandler(userId);
            if (deletedQuestion) {
                notifyItemRemoved(holder.getAdapterPosition());
                notifyItemRangeChanged(holder.getAdapterPosition(), quizHandler.getSize() - holder.getAdapterPosition());
                if(quizHandler.getQuizList(userId).size() == 0){
                    mainActivity.setNoQuizTextVisibility();
                }

            }
            return deletedQuestion;
        });
    }

    @Override
    public int getItemCount() {
        return quizHandler.getSize();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView quizNameView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            quizNameView = itemView.findViewById(R.id.quizListRowView);
        }
    }
}
