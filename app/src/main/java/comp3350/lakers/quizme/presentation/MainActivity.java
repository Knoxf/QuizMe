package comp3350.lakers.quizme.presentation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.RenderEffect;
import android.graphics.Shader;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizme.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import comp3350.lakers.quizme.logic.AchievementHandler;
import comp3350.lakers.quizme.logic.QuizHandler;
import comp3350.lakers.quizme.logic.UserHandler;
import comp3350.lakers.quizme.objects.quizzes.QuizBase;
import comp3350.lakers.quizme.objects.user.User;
import comp3350.lakers.quizme.presentation.InsertQuiz.InsertQuizFragment;
import comp3350.lakers.quizme.presentation.ShowByCategory.QuizCategoryItemAdapter;
import comp3350.lakers.quizme.presentation.ShowByCategory.QuizCategoryItemParent;
import comp3350.lakers.quizme.presentation.ShowByCategory.QuizQuestionItemChild;


public class MainActivity extends AppCompatActivity {

    private QuizHandler quizHandler;
    private UserHandler userHandler;
    private AchievementHandler achievementHandler;
    private RecyclerView recyclerViewByName;

    private RecyclerView recyclerViewByCategoryParent;

    private QuizCategoryItemAdapter quizCategoryItemAdapter;

    private CategoryListAdapter adapter;
    private User user;

    private FloatingActionButton addQuizFab, showByCategoryFab, showByNameFab, showAchievementsFab;

    private ExtendedFloatingActionButton mAddFab;

    private TextView addQuizText, showByCategoryText, showByNameText, noQuiz,showAchievementsText;

    private Boolean isAllFabsVisible;

    private SearchView searchQuiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchQuiz = findViewById(R.id.searchQuiz);
        noQuiz = findViewById(R.id.noQuizText);
        userHandler = new UserHandler();
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("usernameKey", "");
        user = userHandler.getUserByUsername(username);
        achievementHandler = new AchievementHandler(user.getUserID());
        updateRecyclerView(user.getUserID());
        setRecyclerViewByCategory(searchQuiz.getQuery().toString());
        recyclerViewByCategoryParent.setVisibility(View.GONE);
        expandableFloatingButton();
        noQuiz.setVisibility(quizCategoryItemAdapter.getItemCount() != 0 ? View.GONE : View.VISIBLE);
        Button logout = findViewById(R.id.logout);
        searchQuiz.setQueryHint("Quiz Name");
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
        setSearchQuiz();
    }

    private void setSearchQuiz(){

        searchQuiz.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerViewByName.setVisibility(View.GONE);
                recyclerViewByCategoryParent.setVisibility(View.VISIBLE);
                Toast.makeText(MainActivity.this, "Searching", Toast.LENGTH_SHORT).show();
            }
        });

        searchQuiz.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                setRecyclerViewByCategory(searchQuiz.getQuery().toString());
                return false;
            }
        });
    }


    private void expandableFloatingButton() {
        mAddFab = findViewById(R.id.add_fab);
        addQuizFab = findViewById(R.id.CreateQuizFab);
        showByCategoryFab = findViewById(R.id.ShowByCategoryFab);
        showByNameFab = findViewById(R.id.ShowByNameFab);
        showAchievementsFab = findViewById(R.id.ShowAchievementsFab);
        mAddFab.setIconResource(com.google.android.material.R.drawable.abc_star_black_48dp);

        addQuizText = findViewById(R.id.createQuizText);
        showByCategoryText = findViewById(R.id.ShowByCategoryText);
        showByNameText = findViewById(R.id.ShowByNameText);
        showAchievementsText = findViewById(R.id.ShowAchievementsText);

        addQuizFab.setVisibility(View.GONE);
        showByCategoryFab.setVisibility(View.GONE);
        showByNameFab.setVisibility(View.GONE);
        showAchievementsFab.setVisibility(View.GONE);

        addQuizText.setVisibility(View.GONE);
        showByCategoryText.setVisibility(View.GONE);
        showByNameText.setVisibility(View.GONE);
        showAchievementsText.setVisibility(View.GONE);

        isAllFabsVisible = false;
        mAddFab.shrink();

        mAddFab.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!isAllFabsVisible) {
                            addQuizFab.show();
                            showByCategoryFab.show();
                            showByNameFab.show();
                            showAchievementsFab.show();

                            addQuizText.setVisibility(View.VISIBLE);
                            showByCategoryText.setVisibility(View.VISIBLE);
                            showByNameText.setVisibility(View.VISIBLE);
                            showAchievementsText.setVisibility(View.VISIBLE);

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                                recyclerViewByName.setRenderEffect(
                                        RenderEffect.createBlurEffect(
                                                30f,
                                                30f,
                                                Shader.TileMode.CLAMP
                                        )
                                );

                                recyclerViewByCategoryParent.setRenderEffect(
                                        RenderEffect.createBlurEffect(
                                                30f,
                                                30f,
                                                Shader.TileMode.CLAMP
                                        )
                                );

                                noQuiz.setRenderEffect(
                                        RenderEffect.createBlurEffect(
                                                200f,
                                                200f,
                                                Shader.TileMode.CLAMP
                                        )
                                );
                            }

                            mAddFab.extend();
                            isAllFabsVisible = true;
                        } else {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                                recyclerViewByName.setRenderEffect(null);
                                noQuiz.setRenderEffect(null);
                                recyclerViewByCategoryParent.setRenderEffect(null);
                            }

                            addQuizFab.hide();
                            showByCategoryFab.hide();
                            showByNameFab.hide();
                            showAchievementsFab.hide();

                            addQuizText.setVisibility(View.GONE);
                            showByCategoryText.setVisibility(View.GONE);
                            showByNameText.setVisibility(View.GONE);
                            showAchievementsText.setVisibility(View.GONE);
                            mAddFab.shrink();

                            isAllFabsVisible = false;
                        }
                    }
                });
        showByCategoryFab.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {

                            recyclerViewByCategoryParent.setRenderEffect(null);
                        }

                        Toast.makeText(MainActivity.this, "Show by category", Toast.LENGTH_SHORT).show();
                        setRecyclerViewByCategory(searchQuiz.getQuery().toString());
                        recyclerViewByName.setVisibility(View.GONE);
                        recyclerViewByCategoryParent.setVisibility(View.VISIBLE);

                        addQuizFab.hide();
                        showByCategoryFab.hide();
                        showByNameFab.hide();
                        showAchievementsFab.hide();

                        addQuizText.setVisibility(View.GONE);
                        showByCategoryText.setVisibility(View.GONE);
                        showByNameText.setVisibility(View.GONE);
                        showAchievementsText.setVisibility(View.GONE);

                        mAddFab.shrink();

                        isAllFabsVisible = false;

                        //update achievement progress
                        achievementHandler.updateProgress("QUIZ_SORT");
                    }
                });
        showByNameFab.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                            recyclerViewByName.setRenderEffect(null);
                            recyclerViewByCategoryParent.setRenderEffect(null);
                        }

                        Toast.makeText(MainActivity.this, "Show by name", Toast.LENGTH_SHORT).show();
                        recyclerViewByName.setVisibility(View.VISIBLE);
                        recyclerViewByCategoryParent.setVisibility(View.GONE);
                        setRecyclerViewByCategory(searchQuiz.getQuery().toString());

                        addQuizFab.hide();
                        showByCategoryFab.hide();
                        showByNameFab.hide();
                        showAchievementsFab.hide();

                        addQuizText.setVisibility(View.GONE);
                        showByCategoryText.setVisibility(View.GONE);
                        showByNameText.setVisibility(View.GONE);
                        showAchievementsText.setVisibility(View.GONE);
                        mAddFab.shrink();

                        isAllFabsVisible = false;

                        searchQuiz.setQuery("", false);
                        searchQuiz.setIconified(true);

                        //update achievement progress
                        achievementHandler.updateProgress("QUIZ_SHOW_ALL");
                    }
                }
        );
        addQuizFab.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MainActivity.this, "Adding Quiz", Toast.LENGTH_SHORT).show();
                        Bundle bundle = new Bundle();
                        bundle.putString("userId", user.getUserID());
                        Fragment destination = new InsertQuizFragment();
                        destination.setArguments(bundle);

                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.mainPage, destination)
                                .addToBackStack(null)
                                .commit();

                        recyclerViewByCategoryParent.setVisibility(View.GONE);
                        recyclerViewByName.setVisibility(View.GONE);
                        addQuizFab.hide();
                        showByCategoryFab.hide();
                        showByNameFab.hide();
                        showAchievementsFab.hide();

                        noQuiz.setVisibility(View.GONE);

                        addQuizText.setVisibility(View.GONE);
                        showByCategoryText.setVisibility(View.GONE);
                        showByNameText.setVisibility(View.GONE);
                        showAchievementsText.setVisibility(View.GONE);

                        mAddFab.shrink();

                        isAllFabsVisible = false;

                        mAddFab.hide();

                        searchQuiz.setQuery("", false);
                        searchQuiz.setIconified(true);
                    }
                });
        showAchievementsFab.setOnClickListener(view -> {
            searchQuiz.setQuery("", false);
            searchQuiz.setIconified(true);
            //update achievement progress
            achievementHandler.updateProgress("ACHIEVEMENT_LIST");

            //start new activity
            Intent intent = new Intent(this, AchievementsActivity.class);
            intent.putExtra("userId", user.getUserID());
            startActivity(intent);
        });
    }

    public void setFloatingActionButtonVisibility(boolean visible) {
        if (visible) {
            mAddFab.show();
        } else {
            mAddFab.hide();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            recyclerViewByName.setRenderEffect(null);
            recyclerViewByCategoryParent.setRenderEffect(null);
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setRecyclerViewVisibility() {
        quizHandler = new QuizHandler(user.getUserID());
        recyclerViewByName.setVisibility(View.VISIBLE);
        updateRecyclerView(user.getUserID());
        setRecyclerViewByCategory(searchQuiz.getQuery().toString());
        noQuiz.setVisibility(quizCategoryItemAdapter.getItemCount() != 0 ? View.GONE : View.VISIBLE);
    }

    private void updateRecyclerView(String userId) {
        quizHandler = new QuizHandler(userId);
        recyclerViewByName = findViewById(R.id.categoryListRecycler);
        adapter = new CategoryListAdapter(this, quizHandler, userId , this, achievementHandler);
        recyclerViewByName.setAdapter(adapter);
        recyclerViewByName.setLayoutManager(new LinearLayoutManager(this));

    }

    public void setNoQuizTextVisibility(){
        noQuiz.setVisibility(View.VISIBLE);
    }

    public void setNoQuizTextUnBlur(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            noQuiz.setRenderEffect(null);
        }
    }



    @SuppressLint("ResourceType")
    private void setRecyclerViewByCategory(String searchQuery) {
        recyclerViewByCategoryParent = findViewById(R.id.categoryParentRecyclerView);

        quizCategoryItemAdapter = new QuizCategoryItemAdapter(getCategory(searchQuery), this, quizHandler , searchQuery, achievementHandler);

        recyclerViewByCategoryParent.setAdapter(quizCategoryItemAdapter);

        recyclerViewByCategoryParent.setLayoutManager(new LinearLayoutManager(this));

    }

    private List<QuizCategoryItemParent> getCategory(String searchQuery) {
        List<QuizBase> quizBaseList = quizHandler.getQuizList(user.getUserID());
        Map<String, List<QuizQuestionItemChild>> categoryMap = new HashMap<>();

        for (QuizBase quizBase : quizBaseList) {
            if (quizBase.getQuizName().toLowerCase().contains(searchQuery.toLowerCase())) {
                String category = quizBase.getCategory();
                List<QuizQuestionItemChild> questionList = categoryMap.getOrDefault(category, new ArrayList<>());
                questionList.add(new QuizQuestionItemChild(quizBase.getQuizName(), quizBase.getQID(), user.getUserID()));
                categoryMap.put(category, questionList);
            }
        }

        List<QuizCategoryItemParent> quizCategoryItemParentsList = new ArrayList<>();
        for (Map.Entry<String, List<QuizQuestionItemChild>> entry : categoryMap.entrySet()) {
            quizCategoryItemParentsList.add(new QuizCategoryItemParent(entry.getKey(), entry.getValue()));
        }

        return quizCategoryItemParentsList;
    }


}