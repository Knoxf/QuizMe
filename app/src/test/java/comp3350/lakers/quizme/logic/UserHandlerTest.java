package comp3350.lakers.quizme.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import comp3350.lakers.quizme.objects.user.User;
import comp3350.lakers.quizme.objects.user.UserTag;
import comp3350.lakers.quizme.persistence.stubs.UserStub;

public class UserHandlerTest {
    private UserStub stub;
    private UserHandler userHandler;
    private List<User> users;

    @Before
    public void setUp() {
        stub = new UserStub();
        userHandler = new UserHandler(stub);

        assertNotNull(userHandler);

        users = new ArrayList<>();

        users.add(new User(
                new UserTag("29289049-b0c4-4aff-9d05-667067ace368"),
                "George",
                "Washington",
                "america1776",
                "suckItEngland"
        ));

        users.add(new User(
                new UserTag("5cde8f75-08e1-4d76-ae16-18b9ef465cc8"),
                "Napoleon",
                "Bonaparte",
                "waterloo",
                "defeat"
        ));
    }

    @Test
    public void testGetUserByUsername() {
        System.out.println("Testing getUserByUsername");

        String username = "waterloo";

        User user = userHandler.getUserByUsername(username);

        assertNotNull("User should not be null", user);
        assertEquals(username, user.getUsername());
        assertEquals("Napoleon", user.getFirstName());
        assertEquals("Bonaparte", user.getLastName());

        System.out.println("End of getUserByUsername");
    }

    @Test
    public void testFailedGetUserByUsername() {
        System.out.println("Testing failed testFailedGetUserByUsername");

        String username = "https://i.redd.it/6b4upqv6at321.jpg";

        User user = userHandler.getUserByUsername(username);

        assertNull("User should be null", user);

        System.out.println("End of failed testFailedGetUserByUsername");
    }

    @Test
    public void testUserExists() {
        System.out.println("Testing testUserExists");

        String username = "america1776";

        assertTrue(userHandler.userExists(username));

        System.out.println("End of testUserExists");
    }

    @Test
    public void testUserDoesNotExist() {
        System.out.println("Testing testUserDoesNotExist");

        String username = "https://i.redd.it/m91rkvstlmy11.jpg";

        assertFalse(userHandler.userExists(username));

        System.out.println("End of testUserDoesNotExist");
    }

    @Test
    public void testInsertUser() {
        System.out.println("Testing testInsertUser");

        User user = new User(
                new UserTag("64d4c524-8da0-4e07-b37d-769c2f6410c1"),
                "Robert",
                "Guderian",
                "xXMemeIsLifEXx",
                "internetFriend"
        );

        assertTrue(userHandler.insertUser(user));

        User getUser = userHandler.getUserByUsername("xXMemeIsLifEXx");

        assertNotNull("it shouldn't be null", getUser);
        assertEquals(user.getUserID(), getUser.getUserID());
        assertEquals(user.getFirstName(), getUser.getFirstName());
        assertEquals(user.getLastName(), getUser.getLastName());
        assertEquals(user.getUsername(), getUser.getUsername());
        assertEquals(user.getPassword(), getUser.getPassword());

        System.out.println("Ending testInsertUser");
    }

    @Test
    public void testFailedInsertUser() {
        System.out.println("Testing testFailedInsertUser");

        assertFalse(userHandler.insertUser(null));

        System.out.println("Ending testFailedInsertUser");
    }
}
