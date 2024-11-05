package comp3350.lakers.quizme.objects;
import comp3350.lakers.quizme.objects.user.UserTag;
import static org.junit.Assert.*;
import org.junit.Test;

public class UserTagTest {

    @Test
    public void testUUIDConstructor() {
        UserTag tag;
        System.out.println("\nStarting testUUIDConstructor");

        tag = new UserTag();

        assertNotNull(tag);
        assertNotNull("UserID shouldn't be null", tag.getUserID());
    }

    @Test
    public void testOtherConstructor() {
        UserTag tag;
        System.out.println("\nStarting testOtherConstructor");

        tag = new UserTag("Robbity Bobbity");

        assertNotNull(tag);
        assertEquals("Robbity Bobbity", tag.getUserID());
    }
}
