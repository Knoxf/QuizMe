package comp3350.lakers.quizme;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import comp3350.lakers.quizme.logic.AchievementHandlerIT;
import comp3350.lakers.quizme.logic.QuizHandlerIT;
import comp3350.lakers.quizme.logic.UserHandlerIT;


@RunWith(Suite.class)
@Suite.SuiteClasses({
        QuizHandlerIT.class,
        UserHandlerIT.class,
        AchievementHandlerIT.class
})
public class AllIntegrationTests {

}
