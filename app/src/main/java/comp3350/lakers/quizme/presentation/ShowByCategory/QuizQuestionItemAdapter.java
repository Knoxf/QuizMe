package comp3350.lakers.quizme.presentation.ShowByCategory;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.quizme.R;

import java.util.List;

import comp3350.lakers.quizme.logic.AchievementHandler;
import comp3350.lakers.quizme.logic.QuizHandler;
import comp3350.lakers.quizme.objects.quizzes.QuizBase;
import comp3350.lakers.quizme.presentation.SingleQuiz;


public class QuizQuestionItemAdapter extends RecyclerView.Adapter<QuizQuestionItemAdapter.QuizQuestionItemViewHolder>{

    private List<QuizQuestionItemChild> quizQuestionItemChildrenList;

    private ViewGroup parent;
    private Context context;

    private String searchQuery;

    private QuizHandler quizHandler;

    private AchievementHandler achievementHandler;
    public QuizQuestionItemAdapter(List<QuizQuestionItemChild> quizQuestionItemChildren , Context context , QuizHandler quizHandler , String searchQuery, AchievementHandler achievementHandler){
        this.quizHandler = quizHandler;
        this.quizQuestionItemChildrenList = quizQuestionItemChildren;
        this.context = context;
        this.searchQuery = searchQuery;
        this.achievementHandler = achievementHandler;
    }

    @NonNull
    @Override
    public QuizQuestionItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.quiz_shows_by_category_child_item ,
                parent ,
                false);
        this.parent = parent;
        return new QuizQuestionItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuizQuestionItemViewHolder holder, int position) {

        QuizQuestionItemChild quizQuestionItemChild = quizQuestionItemChildrenList.get(position);


        int QID = quizQuestionItemChild.getQID();
        String userId = quizQuestionItemChild.getUUID();
        QuizBase question = quizHandler.getQuizByQID(QID);

        holder.QuizQuestionItemTitle.setText(quizQuestionItemChild.getQuizCardQuestion());
        holder.QuizQuestionItemTitle.setOnClickListener(v ->{
            Intent intent = new Intent(context, SingleQuiz.class);
            intent.putExtra("Question" , question.getQuestion());
            intent.putExtra("QID" , QID);
            intent.putExtra("userId" , userId);
            intent.putExtra("from" , "category");
            intent.putExtra("achievementUserId", achievementHandler.getUserId());
            context.startActivity(intent);
        });



    }
    @Override
    public int getItemCount() {
        return quizQuestionItemChildrenList.size();
    }


    static class QuizQuestionItemViewHolder extends RecyclerView.ViewHolder{

        private final TextView QuizQuestionItemTitle;


        public QuizQuestionItemViewHolder(@NonNull View itemView) {
            super(itemView);

            QuizQuestionItemTitle = itemView.findViewById(R.id.child_item_title);

        }
    }
}
