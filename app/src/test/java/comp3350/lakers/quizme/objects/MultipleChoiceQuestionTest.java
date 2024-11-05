package comp3350.lakers.quizme.objects;

import org.junit.Test;
import static org.junit.Assert.*;

import comp3350.lakers.quizme.objects.quizzes.MultipleChoiceQuiz;

public class MultipleChoiceQuestionTest {

    @Test
    public void testMCQuestion() {
        MultipleChoiceQuiz question;

        System.out.println("\nStarting testMCQuestion");

        question = new MultipleChoiceQuiz(
                "Who let the dogs out?",
                "I don't know",
                new String[]{
                        "No clue",
                        "I don't know",
                        "Cruella",
                        "Someone"
                },
                "Random name",
                "Trivia");
        assertNotNull(question);
        assertEquals("Who let the dogs out?", question.getQuestion());
        assertEquals("I don't know", question.getAnswer());
        assertEquals("Random name", question.getQuizName());
        assertEquals("Trivia", question.getCategory());
        assertArrayEquals(new String[]{
                "No clue",
                "I don't know",
                "Cruella",
                "Someone"
        }, question.getOption());

        System.out.println("\nEnd testLAQuestion");
    }
}
