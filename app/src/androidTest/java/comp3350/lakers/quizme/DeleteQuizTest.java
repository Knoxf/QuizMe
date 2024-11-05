package comp3350.lakers.quizme;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.example.quizme.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import comp3350.lakers.quizme.presentation.LoginActivity;

public class DeleteQuizTest {


    @Rule
    public ActivityScenarioRule<LoginActivity> activityScenario = new ActivityScenarioRule<>(LoginActivity.class);

    @Before
    public void setup() {
        Intents.init();
    }



    @Test
    public void testDeleteFirstQuiz(){
        onView(withId(R.id.userLoginEditText)).perform(typeText("america1776"));
        onView(withId(R.id.userPasswordEditText)).perform(typeText("suckItEngland"));
        onView(withId(R.id.loginBtn)).perform(ViewActions.click());
        Intents.intended(IntentMatchers.hasComponent("comp3350.lakers.quizme.presentation.MainActivity"));


        onView(withId(R.id.categoryListRecycler)).perform(RecyclerViewActions.scrollToPosition(5)).perform(RecyclerViewActions.actionOnItemAtPosition(4 , longClick()));

        onView(withId(R.id.logout)).perform(click());
        Intents.intended(IntentMatchers.hasComponent("comp3350.lakers.quizme.presentation.LoginActivity"));
    }



    @Test
    public void testDeleteSecondQuiz(){
        onView(withId(R.id.userLoginEditText)).perform(typeText("america1776"));
        onView(withId(R.id.userPasswordEditText)).perform(typeText("suckItEngland"));
        onView(withId(R.id.loginBtn)).perform(ViewActions.click());
        Intents.intended(IntentMatchers.hasComponent("comp3350.lakers.quizme.presentation.MainActivity"));


        onView(withId(R.id.categoryListRecycler)).perform(RecyclerViewActions.scrollToPosition(5)).perform(RecyclerViewActions.actionOnItemAtPosition(4 , longClick()));

        onView(withId(R.id.logout)).perform(click());
        Intents.intended(IntentMatchers.hasComponent("comp3350.lakers.quizme.presentation.LoginActivity"));
    }






    @After
    public void teardown() {
        Intents.release();
    }
}
