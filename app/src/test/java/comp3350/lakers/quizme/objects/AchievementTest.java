package comp3350.lakers.quizme.objects;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import comp3350.lakers.quizme.objects.achievements.Achievement;
import comp3350.lakers.quizme.objects.user.UserTag;

public class AchievementTest {
    @Test
    public void testEmptyConstructor() {
        Achievement achievement;
        UserTag userTag;
        System.out.println("\nStarting testEmptyConstructor");

        userTag = new UserTag("testTag");
        achievement = new Achievement(0,userTag);

        assertNotNull(achievement);

        assertEquals(0,achievement.getAchievementID());
        assertNull(achievement.getName());
        assertNull(achievement.getDescription());
        assertNull(achievement.getCategory());
        assertEquals(1,achievement.getRequirement());
        assertEquals("testTag", achievement.getUserID());
        assertEquals(0,achievement.getProgress());

        System.out.println("\nEnding testEmptyConstructor");

    }

    @Test
    public void testOtherConstructor() {
        Achievement achievement;
        UserTag tag;
        System.out.println("\nStarting testOtherConstructor");

        tag = new UserTag("testTag");
        achievement = new Achievement(0, "Success", "Pass the test", "TESTS", 1, tag,0);

        assertNotNull(achievement);
        assertEquals(0,achievement.getAchievementID());
        assertEquals("Success", achievement.getName());
        assertEquals("Pass the test", achievement.getDescription());
        assertEquals("TESTS", achievement.getCategory());
        assertEquals(1,achievement.getRequirement());
        assertEquals("testTag",achievement.getUserID());
        assertEquals(0,achievement.getProgress());

        System.out.println("\nEnding testOtherConstructor");
    }
}
