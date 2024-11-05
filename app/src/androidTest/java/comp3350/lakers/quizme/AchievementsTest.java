package comp3350.lakers.quizme;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.quizme.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import comp3350.lakers.quizme.presentation.LoginActivity;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class AchievementsTest {

    @Rule
    public ActivityScenarioRule<LoginActivity> activityScenario = new ActivityScenarioRule<>(LoginActivity.class);

    @Before
    public void setup() {
        Intents.init();
    }

    @Test
    public void testAchievements() {
        // Login
        onView(withId(R.id.userLoginEditText)).perform(typeText("america1776"));
        onView(withId(R.id.userPasswordEditText)).perform(typeText("suckItEngland"));
        onView(withId(R.id.loginBtn)).perform(ViewActions.click());
        Intents.intended(IntentMatchers.hasComponent("comp3350.lakers.quizme.presentation.MainActivity"));

        //open expandable fab
        onView(withId(R.id.add_fab)).perform(ViewActions.click());

        //click sort to earn achievement
        onView(withId(R.id.ShowByCategoryFab)).perform(ViewActions.click());

        //open expandable fab
        onView(withId(R.id.add_fab)).perform(ViewActions.click());

        //open achievements list
        onView(withId(R.id.ShowAchievementsFab)).perform(ViewActions.click());
        Intents.intended(IntentMatchers.hasComponent("comp3350.lakers.quizme.presentation.AchievementsActivity"));

        //check achievements that should be completed
        onView(ViewMatchers.withId(R.id.achievementRecyclerView)).perform(RecyclerViewActions.scrollTo(hasDescendant(withText("Sorted - COMPLETE"))));
        onView(ViewMatchers.withId(R.id.achievementRecyclerView)).perform(RecyclerViewActions.scrollTo(hasDescendant(withText("Achievement Hunter - COMPLETE"))));

        //check achievement that should not be completed
        onView(ViewMatchers.withId(R.id.achievementRecyclerView)).perform(RecyclerViewActions.scrollTo(hasDescendant(withText("Unsorted"))));

        Espresso.pressBack();

        onView(withId(R.id.logout)).perform(ViewActions.click());
    }

    @After
    public void teardown() {
        Intents.release();
    }
}
