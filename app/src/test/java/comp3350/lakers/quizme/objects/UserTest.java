package comp3350.lakers.quizme.objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import comp3350.lakers.quizme.objects.user.User;
import comp3350.lakers.quizme.objects.user.UserTag;

public class UserTest {

    @Test
    public void testEmptyConstructor() {
        User user;
        UserTag tag;
        System.out.println("\nStarting testEmptyConstructor");

        tag = new UserTag("testTag");
        user = new User(tag);

        assertNotNull(user);
        assertEquals("testTag", user.getUserID());
        assertNull(user.getFirstName());
        assertNull(user.getLastName());
    }

    @Test
    public void testOtherConstructor() {
        User user;
        UserTag tag;
        System.out.println("\nStarting testEmptyConstructor");

        tag = new UserTag("testTag");
        user = new User(tag, "Father", "ICraveCheddar", "realMickey", "cheddar");

        assertNotNull(user);
        assertEquals("testTag", user.getUserID());
        assertEquals("Father", user.getFirstName());
        assertEquals("ICraveCheddar", user.getLastName());
        assertEquals("realMickey", user.getUsername());
        assertEquals("cheddar", user.getPassword());
    }
}
