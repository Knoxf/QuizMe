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
public class InsertQuizTest {

    @Rule
    public ActivityScenarioRule<LoginActivity> activityScenario = new ActivityScenarioRule<>(LoginActivity.class);

    @Before
    public void setup() {
        Intents.init();
    }

    @Test
    public void testInsertLongAnswerQuiz() {

        onView(withId(R.id.userLoginEditText)).perform(typeText("america1776"));
        onView(withId(R.id.userPasswordEditText)).perform(typeText("suckItEngland"));
        onView(withId(R.id.loginBtn)).perform(ViewActions.click());
        Intents.intended(IntentMatchers.hasComponent("comp3350.lakers.quizme.presentation.MainActivity"));


        onView(withId(R.id.add_fab)).perform(click());
        onView(withId(R.id.CreateQuizFab)).perform(click());


        //insert

        // Enter long answer quiz
        onView(withId(R.id.category)).perform(typeText("Test category"));
        onView(withId(R.id.quizname)).perform(typeText("Geography"));
        onView(withId(R.id.LongAnswerQuestion)).perform(typeText("What is the capital of France?"));
        onView(withId(R.id.LongAnswerAnswer)).perform(typeText("Paris"));
        closeSoftKeyboard();
        onView(withId(R.id.nextQuestion)).perform(click());

        onView(withId(R.id.endOfQuestionInsert)).perform(click());
        // Validate that we return to MainActivity
        Intents.intended(IntentMatchers.hasComponent("comp3350.lakers.quizme.presentation.MainActivity"));

        onView(withId(R.id.logout)).perform(click());
        Intents.intended(IntentMatchers.hasComponent("comp3350.lakers.quizme.presentation.LoginActivity"));

    }

    @Test
    public void testInsertMultipleAnswerQuiz(){
        onView(withId(R.id.userLoginEditText)).perform(typeText("america1776"));
        onView(withId(R.id.userPasswordEditText)).perform(typeText("suckItEngland"));
        onView(withId(R.id.loginBtn)).perform(ViewActions.click());
        Intents.intended(IntentMatchers.hasComponent("comp3350.lakers.quizme.presentation.MainActivity"));


        onView(withId(R.id.add_fab)).perform(click());
        onView(withId(R.id.CreateQuizFab)).perform(click());

        onView(withId(R.id.switchQuizType)).perform(click());

        onView(withId(R.id.category)).perform(typeText("Test category"));
        onView(withId(R.id.quizname)).perform(typeText("Math"));
        onView(withId(R.id.multipleQuestionQuestion)).perform(typeText("What is 2 + 2?"));
        onView(withId(R.id.multipleQuestionOptionA)).perform(typeText("1"));
        onView(withId(R.id.multipleQuestionOptionB)).perform(typeText("2"));
        onView(withId(R.id.multipleQuestionOptionC)).perform(typeText("3"));
        onView(withId(R.id.multipleQuestionOptionD)).perform(typeText("4"));
        onView(withId(R.id.multipleQuestionAnswer)).perform(typeText("4"));
        closeSoftKeyboard();
        onView(withId(R.id.nextQuestion)).perform(click());
        onView(withId(R.id.endOfQuestionInsert)).perform(click());
        Intents.intended(IntentMatchers.hasComponent("comp3350.lakers.quizme.presentation.MainActivity"));

        onView(withId(R.id.logout)).perform(click());
        Intents.intended(IntentMatchers.hasComponent("comp3350.lakers.quizme.presentation.LoginActivity"));
    }
    @After
    public void teardown() {
        Intents.release();
    }
}
