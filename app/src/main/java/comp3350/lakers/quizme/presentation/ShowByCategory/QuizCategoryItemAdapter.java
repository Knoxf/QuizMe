package comp3350.lakers.quizme.presentation.ShowByCategory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizme.R;

import java.util.List;

import comp3350.lakers.quizme.logic.AchievementHandler;
import comp3350.lakers.quizme.logic.QuizHandler;


public class QuizCategoryItemAdapter extends RecyclerView.Adapter<QuizCategoryItemAdapter.QuizCategoryItemViewHolder> {

    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private List<QuizCategoryItemParent> itemList;
    private Context context;

    private String searchQuery;
    private QuizHandler quizHandler;
    private AchievementHandler achievementHandler;

    public QuizCategoryItemAdapter(List<QuizCategoryItemParent> itemList , Context context , QuizHandler quizHandler , String searchQuery, AchievementHandler achievementHandler){
        this.quizHandler = quizHandler;
        this.itemList = itemList;
        this.context = context;
        this.searchQuery = searchQuery;
        this.achievementHandler = achievementHandler;
    }

    @NonNull
    @Override
    public QuizCategoryItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quiz_shows_by_category_parent_item , parent , false);
        return new QuizCategoryItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuizCategoryItemViewHolder holder, int position) {
        QuizCategoryItemParent quizCategoryItemParent = itemList.get(position);

        holder.CategoryItemTitle.setText(quizCategoryItemParent.getCategoryTitle());

        LinearLayoutManager layoutManager = new LinearLayoutManager(
                holder.getQuizQuestionRecyclerView().getContext(),
                LinearLayoutManager.HORIZONTAL,
                false
        );

        layoutManager.setInitialPrefetchItemCount(
                quizCategoryItemParent.getQuestionItemList().size()
        );

        QuizQuestionItemAdapter quizQuestionItemAdapter = new QuizQuestionItemAdapter(
                quizCategoryItemParent.getQuestionItemList() , context , quizHandler , searchQuery, achievementHandler);
        holder.QuizQuestionRecyclerView.setLayoutManager(layoutManager);
        holder.QuizQuestionRecyclerView.setAdapter(quizQuestionItemAdapter);
        holder.QuizQuestionRecyclerView.setRecycledViewPool(viewPool);


    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


    static class QuizCategoryItemViewHolder extends RecyclerView.ViewHolder {

        private TextView CategoryItemTitle;
        private RecyclerView QuizQuestionRecyclerView;


        public QuizCategoryItemViewHolder(@NonNull View itemView) {
            super(itemView);
            CategoryItemTitle = itemView.findViewById(R.id.parent_item_title);

            QuizQuestionRecyclerView = itemView.findViewById(R.id.child_recyclerview);

        }

        public TextView getCategoryItemTitle() {
            return CategoryItemTitle;
        }

        public void setCategoryItemTitle(TextView categoryItemTitle) {
            CategoryItemTitle = categoryItemTitle;
        }

        public RecyclerView getQuizQuestionRecyclerView() {
            return QuizQuestionRecyclerView;
        }

        public void setQuizQuestionRecyclerView(RecyclerView quizQuestionRecyclerView) {
            QuizQuestionRecyclerView = quizQuestionRecyclerView;
        }
    }

}

