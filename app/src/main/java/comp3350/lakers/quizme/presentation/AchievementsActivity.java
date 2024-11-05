package comp3350.lakers.quizme.presentation;

import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizme.R;



import java.util.List;

import comp3350.lakers.quizme.logic.AchievementHandler;

import comp3350.lakers.quizme.objects.achievements.Achievement;

public class AchievementsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    AchievementAdapter adapter;
    List<Achievement> achievementList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AchievementHandler achievementHandler;
        setContentView(R.layout.activity_achievement_list);

        recyclerView = (RecyclerView) findViewById(R.id.achievementRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Bundle extras = getIntent().getExtras();
        achievementHandler = new AchievementHandler(extras.getString("userId"));
        achievementList = achievementHandler.getUserAchievements();

        adapter = new AchievementAdapter(achievementList, R.layout.achievement_item);
        recyclerView.setAdapter(adapter);
    }


}
