package comp3350.lakers.quizme;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import android.os.SystemClock;

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

import comp3350.lakers.quizme.utils.TestUtil;
import comp3350.lakers.quizme.objects.user.User;
import comp3350.lakers.quizme.presentation.SignupActivity;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class SignupTest {

    @Rule
    public ActivityScenarioRule<SignupActivity> activityScenario = new ActivityScenarioRule<SignupActivity>(SignupActivity.class);

    private TestUtil testUtil;

    @Before
    public void setup() {
        testUtil = new TestUtil();
        testUtil.deleteUser("newUsername"); // Delete user
        Intents.init();
    }

    @Test
    public void testSignup() {
        // Add user back by signing up
        onView(withId(R.id.signUpEditFName)).perform(replaceText("newFirstName"));
        onView(withId(R.id.signUpEditLName)).perform(replaceText("newLastName"));
        onView(withId(R.id.signUpEditUName)).perform(replaceText("newUsername"));
        onView(withId(R.id.signUpEditPass)).perform(replaceText("newPass"));
        onView(withId(R.id.signUpEditCPass)).perform(replaceText("newPass"));
        onView(withId(R.id.signUpBtn)).perform(click());

        // Sleep for one sec to make the database update.
        SystemClock.sleep(1000);

        // Will jump to LoginActivity if it's successful
        Intents.intended(IntentMatchers.hasComponent("comp3350.lakers.quizme.presentation.LoginActivity"));
        User user = testUtil.getUserByUsername("newUsername");

        // assert stuff
        assertNotNull(user);
        assertEquals(user.getFirstName(), "newFirstName");
        assertEquals(user.getLastName(), "newLastName");
        assertEquals(user.getUsername(), "newUsername");
    }

    @After
    public void teardown() {
        Intents.release();
    }
}
