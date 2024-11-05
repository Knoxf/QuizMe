package comp3350.lakers.quizme;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.action.ViewActions;
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
public class LoginTest {

    @Rule
    public ActivityScenarioRule<LoginActivity> activityScenario = new ActivityScenarioRule<LoginActivity>(LoginActivity.class);


    @Before
    public void setup() {
        Intents.init();
    }

    @Test
    public void testLogin() {
        // Login
        onView(withId(R.id.userLoginEditText)).perform(typeText("america1776"));
        onView(withId(R.id.userPasswordEditText)).perform(typeText("suckItEngland"));
        onView(withId(R.id.loginBtn)).perform(ViewActions.click());
        Intents.intended(IntentMatchers.hasComponent("comp3350.lakers.quizme.presentation.MainActivity"));

        // Log out
        onView(withId(R.id.logout)).perform(click());
        Intents.intended(IntentMatchers.hasComponent("comp3350.lakers.quizme.presentation.LoginActivity"));
    }

    @After
    public void teardown() {
        Intents.release();
    }
}
