package comp3350.lakers.quizme.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import comp3350.lakers.quizme.objects.user.User;
import comp3350.lakers.quizme.objects.user.UserTag;
import comp3350.lakers.quizme.persistence.IUser;
import comp3350.lakers.quizme.persistence.hsqldb.UserPersistenceHSQLDB;
import comp3350.lakers.quizme.utils.TestUtil;

public class UserHandlerIT {
    private UserHandler userHandler;
    private File tempDb;

    @Before
    public void setUp() throws IOException {
        this.tempDb = TestUtil.copyDB();
        final IUser persistence = new UserPersistenceHSQLDB(this.tempDb.getAbsolutePath().replace(".script", ""));
        this.userHandler = new UserHandler(persistence);
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
                "oooooohYEAH"
        );

        assertTrue(userHandler.insertUser(user));

        User getUser = userHandler.getUserByUsername("xXMemeIsLifEXx");

        assertNotNull("it shouldn't be null",getUser);
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

    @After
    public void tearDown() {
        this.tempDb.delete();
    }
}
