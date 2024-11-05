package comp3350.lakers.quizme.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;


import java.util.List;

import comp3350.lakers.quizme.logic.AchievementHandler;
import comp3350.lakers.quizme.objects.achievements.Achievement;
import comp3350.lakers.quizme.persistence.stubs.AchievementStub;

public class AchievementStubTest {
    private final String userId = "29289049-b0c4-4aff-9d05-667067ace368";
    private AchievementHandler achievementHandler;

    @Before
    public void setUp() {
        AchievementStub as = new AchievementStub();
        achievementHandler = new AchievementHandler(as, userId);

        assertNotNull(achievementHandler);
    }

    @Test
    public void testInsertUser() {
        System.out.println("\nStarting testInsertUser");
        assertFalse(achievementHandler.userAchievementExists(1));
        achievementHandler.insertUser();
        assertTrue(achievementHandler.userAchievementExists(1));
        System.out.println("\nEnding testInsertUser");
    }

    @Test
    public void testGetUserAchievements() {
        System.out.println("\nStarting testGetUserAchievements");
        achievementHandler.insertUser();
        List<Achievement> list = achievementHandler.getUserAchievements();

        assertEquals(3, list.size());
        assertNotNull(list.get(0));
        assertEquals(userId, list.get(0).getUserID());

        System.out.println("\nEnding testGetUserAchievements");
    }

    @Test
    public void testUpdateProgress() {
        System.out.println("\nStarting testUpdateProgress");
        achievementHandler.insertUser();
        List<Achievement> list = achievementHandler.getUserAchievements();

        assertNotNull(list.get(0));
        assertEquals(0, list.get(0).getProgress());
        assertEquals(0, list.get(1).getProgress());
        assertEquals(0, list.get(2).getProgress());
        assertEquals(0, list.get(0).getProgressPercent());

        achievementHandler.updateProgress("QUIZ_CREATE");
        list = achievementHandler.getUserAchievements();
        assertNotNull(list.get(0));
        assertEquals(1, list.get(0).getProgress());
        assertEquals(1, list.get(1).getProgress());
        assertEquals(0, list.get(2).getProgress());
        assertEquals(100, list.get(0).getProgressPercent());
        assertEquals(20, list.get(1).getProgressPercent());

        achievementHandler.updateProgress("QUIZ_CREATE");
        list = achievementHandler.getUserAchievements();
        assertNotNull(list.get(0));
        assertEquals(2, list.get(0).getProgress());
        assertEquals(2, list.get(1).getProgress());
        assertEquals(0, list.get(2).getProgress());
        assertEquals(100, list.get(0).getProgressPercent());
        assertEquals(40, list.get(1).getProgressPercent());

        System.out.println("\nEnding testUpdateProgress");
    }
}
