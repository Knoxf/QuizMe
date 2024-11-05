package comp3350.lakers.quizme.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import comp3350.lakers.quizme.objects.achievements.Achievement;
import comp3350.lakers.quizme.persistence.IAchievement;
import comp3350.lakers.quizme.persistence.hsqldb.AchievementPersistenceHSQLDB;
import comp3350.lakers.quizme.utils.TestUtil;

public class AchievementHandlerIT {
    private final String userId = "29289049-b0c4-4aff-9d05-667067ace368";
    private AchievementHandler achievementHandler;
    private File tempDb;

    @Before
    public void setUp() throws IOException {
        this.tempDb = TestUtil.copyDB();
        final IAchievement persistence = new AchievementPersistenceHSQLDB(this.tempDb.getAbsolutePath().replace(".script", ""));
        this.achievementHandler = new AchievementHandler(persistence, userId);
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
        List<Achievement> list = achievementHandler.getUserAchievements();

        assertEquals(10, list.size());
        assertNotNull(list.get(0));
        assertEquals(userId, list.get(0).getUserID());

        System.out.println("\nEnding testGetUserAchievements");
    }

    @Test
    public void testUpdateProgress() {
        System.out.println("\nStarting testUpdateProgress");
        List<Achievement> list = achievementHandler.getUserAchievements();

        assertNotNull(list.get(0));
        assertEquals(0,list.get(0).getProgress());
        assertEquals(0,list.get(1).getProgress());
        assertEquals(0,list.get(9).getProgress());
        assertEquals(0,list.get(0).getProgressPercent());

        achievementHandler.updateProgress("QUIZ_CREATE");
        list = achievementHandler.getUserAchievements();
        assertNotNull(list.get(0));
        assertEquals(1,list.get(0).getProgress());
        assertEquals(1,list.get(1).getProgress());
        assertEquals(0,list.get(9).getProgress());
        assertEquals(100,list.get(0).getProgressPercent());
        assertEquals(20,list.get(1).getProgressPercent());

        achievementHandler.updateProgress("QUIZ_CREATE");
        list = achievementHandler.getUserAchievements();
        assertNotNull(list.get(0));
        assertEquals(2,list.get(0).getProgress());
        assertEquals(2,list.get(1).getProgress());
        assertEquals(0,list.get(9).getProgress());
        assertEquals(100,list.get(0).getProgressPercent());
        assertEquals(40,list.get(1).getProgressPercent());

        System.out.println("\nEnding testUpdateProgress");
    }

    @After
    public void tearDown() { this.tempDb.delete(); }
}
