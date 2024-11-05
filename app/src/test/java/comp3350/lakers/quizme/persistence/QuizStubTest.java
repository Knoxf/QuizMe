package comp3350.lakers.quizme.persistence;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import comp3350.lakers.quizme.objects.quizzes.LongAnswerQuiz;
import comp3350.lakers.quizme.objects.quizzes.MultipleChoiceQuiz;
import comp3350.lakers.quizme.objects.quizzes.QuizBase;
import comp3350.lakers.quizme.persistence.stubs.QuizStub;

public class QuizStubTest {

    private QuizStub quizStub;
    private final String userID = "29289049-b0c4-4aff-9d05-667067ace368";

    @Before
    public void setUp() {
        quizStub = new QuizStub();
    }

    @Test
    public void testGetQuizList() {
        List<QuizBase> questions = quizStub.getQuizList(userID);
        assertNotNull(questions);
        assertEquals(4, questions.size());
    }

    @Test
    public void testInsertMultipleQuestionQuiz() {
        int initialSize = quizStub.getQuestionsLength();
        QuizBase newQuestion = new MultipleChoiceQuiz(
                "What is the capital of France?",
                "Paris",
                new String[]{"Madrid", "Berlin", "London", "Paris"}, "Capital", "Geography");

        QuizBase insertedQuestion = quizStub.insertQuiz(newQuestion, userID);
        assertNotNull(insertedQuestion);
        assertEquals(initialSize + 1, quizStub.getQuestionsLength());
        assertEquals(newQuestion, insertedQuestion);
    }

    @Test
    public void testInsertLongAnswerQuiz() {
        int initialSize = quizStub.getQuestionsLength();
        QuizBase newQuestion = new LongAnswerQuiz(
                "Explain the concept of gravity.",
                "Gravity is a force that attracts objects with mass towards each other. " +
                        "It is responsible for the phenomenon of weight and the motion of objects on Earth. " +
                        "Gravity is described by Isaac Newton's law of universal gravitation, which states " +
                        "that every particle of matter in the universe attracts every other particle with a " +
                        "force that is directly proportional to the product of their masses and inversely " +
                        "proportional to the square of the distance between them." , "Gravity", "Science");

        QuizBase insertedQuestion = quizStub.insertQuiz(newQuestion, userID);
        assertNotNull(insertedQuestion);
        assertEquals(initialSize + 1, quizStub.getQuestionsLength());
        assertEquals(newQuestion, insertedQuestion);
    }

}
