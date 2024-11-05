package comp3350.lakers.quizme.logic;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import comp3350.lakers.quizme.logic.utils.UPValidator;

public class UPValidatorTest {
    @Test
    public void testIsCorrectPassword() {
        System.out.println("Starting testIsCorrectPassword");

        String p1 = "https://i.redd.it/6fqasafgfqb61.png";
        String p2 = "https://i.redd.it/6fqasafgfqb61.png";

        assertTrue(UPValidator.isCorrectPassword(p1, p2));

        System.out.println("Ending testIsCorrectPassword");
    }

    @Test
    public void testFailedIsCorrectPassword() {
        System.out.println("Starting testFailedIsCorrectPassword");

        String p1 = "https://i.redd.it/6fqasafgfqb61.png";
        String p2 = "https://i.redd.it/6fqasafgqb61.png";

        assertFalse(UPValidator.isCorrectPassword(p1, p2));

        System.out.println("Ending testFailedIsCorrectPassword");
    }

    @Test
    public void testIsValidUsername() {
        System.out.println("Starting testIsValidUsername");

        String p1 = "Testtestest";

        assertTrue(UPValidator.isValidInput(p1));

        System.out.println("Ending testIsValidUsername");
    }

    @Test
    public void testFailedIsValidUsername() {
        System.out.println("Starting testFailedIsValidUsername");

        String p1 = null;
        String p2 = "";

        assertFalse(UPValidator.isValidInput(p1));
        assertFalse(UPValidator.isValidInput(p2));

        System.out.println("Ending testFailedIsValidUsername");
    }

    @Test
    public void testIsValidPassword() {
        System.out.println("Starting testIsValidPassword");

        String p1 = "123456";

        assertTrue(UPValidator.isValidPassword(p1));

        System.out.println("Ending testIsValidPassword");
    }

    @Test
    public void testFailedIsValidPassword() {
        System.out.println("Starting testFailedIsValidPassword");

        String p1 = "12345";
        String p2 = "";
        String p3 = null;

        assertFalse(UPValidator.isValidPassword(p1));
        assertFalse(UPValidator.isValidPassword(p2));
        assertFalse(UPValidator.isValidPassword(p3));

        System.out.println("Ending testFailedIsValidPassword");
    }

    @Test
    public void testUserExists() {
        System.out.println("Starting testUserExists");

        UserHandler userHandler = new UserHandler();

        assertTrue(UPValidator.userExists(userHandler, "america1776"));

        System.out.println("Ending testUserExists");
    }

    @Test
    public void testFailedUserExists() {
        System.out.println("Starting testFailedUserExists");

        UserHandler userHandler = new UserHandler();

        assertFalse(UPValidator.userExists(null, "america1776"));
        assertFalse(UPValidator.userExists(userHandler, null));

        System.out.println("Ending testFailedUserExists");
    }
}
