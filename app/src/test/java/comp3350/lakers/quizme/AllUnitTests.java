package comp3350.lakers.quizme;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import comp3350.lakers.quizme.logic.AchievementHandlerTest;
import comp3350.lakers.quizme.logic.QuizHandlerTest;
import comp3350.lakers.quizme.logic.QuizValidatorTest;
import comp3350.lakers.quizme.logic.UPValidatorTest;
import comp3350.lakers.quizme.logic.UserHandlerTest;
import comp3350.lakers.quizme.objects.AchievementTest;
import comp3350.lakers.quizme.objects.LongAnswerQuizTest;
import comp3350.lakers.quizme.objects.MultipleChoiceQuestionTest;
import comp3350.lakers.quizme.objects.UserTagTest;
import comp3350.lakers.quizme.objects.UserTest;
import comp3350.lakers.quizme.persistence.AchievementStubTest;
import comp3350.lakers.quizme.persistence.QuizStubTest;


@RunWith(Suite.class)
@Suite.SuiteClasses({
        QuizStubTest.class,
        AchievementStubTest.class,
        MultipleChoiceQuestionTest.class,
        LongAnswerQuizTest.class,
        QuizHandlerTest.class,
        QuizValidatorTest.class,
        UserTest.class,
        UserHandlerTest.class,
        UserTagTest.class,
        AchievementTest.class,
        AchievementHandlerTest.class,
        UPValidatorTest.class,
})
public class AllUnitTests {

}
