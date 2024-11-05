package comp3350.lakers.quizme.objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import comp3350.lakers.quizme.objects.quizzes.LongAnswerQuiz;

public class LongAnswerQuizTest {
    @Test
    public void testLAQuestion() {
        LongAnswerQuiz question;

        System.out.println("\nStarting testLAQuestion");

        question = new LongAnswerQuiz(
                "If a tree falls in a forest and no one is around to hear it, does it make a sound?",
                "Do you hear your own farts?",
                "Tree",
                "Philosophy"
        );

        assertNotNull(question);
        assertEquals("If a tree falls in a forest and no one is around to hear it, does it make a sound?", question.getQuestion());
        assertEquals("Do you hear your own farts?", question.getAnswer());
        assertEquals("Tree", question.getQuizName());
        assertEquals("Philosophy", question.getCategory());

        System.out.println("\nEnd testLAQuestion");
    }
}
