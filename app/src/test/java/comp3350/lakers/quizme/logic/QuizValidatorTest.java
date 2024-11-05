package comp3350.lakers.quizme.logic;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import comp3350.lakers.quizme.logic.utils.QuizValidator;

public class QuizValidatorTest {

    @Test
    public void testIsMCQValid() {
        System.out.println("Starting testIsMCQValid");

        assertTrue(QuizValidator.isMCQValid(
                "Yoooooo?",
                new String[]{
                        "What?",
                        "Oh god",
                        "Are you high?",
                        "Nah"
                },
                "What?",
                "Philosophy"
        ));
        System.out.println("Ending testIsMCQValid");
    }

    @Test
    public void testFailedIsMCQValid() {
        System.out.println("Starting testFailedIsMCQValid");

        assertFalse(QuizValidator.isMCQValid(
                "Yoooooo?",
                new String[]{
                        "What?",
                        "Oh god",
                        null,
                        ""
                },
                "What?",
                "Philosophy"
        ));

        System.out.println("Ending testFailedIsMCQValid");
    }

    @Test
    public void testIsLAQValid() {
        System.out.println("Starting testIsLAQValid");

        assertTrue(QuizValidator.isLAQValid(
                "What is 2*2*2*2?",
                "Do I look like a calculator to you?",
                "Math"
        ));

        System.out.println("Ending testIsLAQValid");
    }

    @Test
    public void testFailedIsLAQValid() {
        System.out.println("Starting testFailedIsLAQValid");

        assertFalse(QuizValidator.isLAQValid(
                "What is 2*2*2*2?",
                "Do I look like a calculator to you?",
                ""
        ));


        System.out.println("Ending testFailedIsLAQValid");
    }

    @Test
    public void testIsValid() {
        System.out.println("Starting testIsValid");

        assertTrue(QuizValidator.isValid(
                "kek"
        ));

        System.out.println("Ending testIsValid");
    }

    @Test
    public void testFailedIsValid() {
        System.out.println("Starting testFailedIsValid");

        assertFalse(QuizValidator.isValid(""));
        assertFalse(QuizValidator.isValid(null));

        System.out.println("Ending testFailedIsValid");
    }
}
