package comp3350.lakers.quizme;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        InsertQuizTest.class,
        DeleteQuizTest.class,
        LoginTest.class,
        SignupTest.class,
        AchievementsTest.class
})
public class AllSystemTests {
}
