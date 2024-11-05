package comp3350.lakers.quizme.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.ArrayList;
import java.util.List;

import comp3350.lakers.quizme.objects.achievements.Achievement;
import comp3350.lakers.quizme.objects.user.UserTag;

@RunWith(MockitoJUnitRunner.class)
public class AchievementHandlerTest {
    private final String userId = "29289049-b0c4-4aff-9d05-667067ace368";
    @Mock
    private AchievementHandler achievementHandler;
    private final List<Achievement> achievementData = new ArrayList<>();
    private final List<Achievement> getAchievementDataProgress1 = new ArrayList<>();
    private final List<Achievement> getAchievementDataProgress2 = new ArrayList<>();

    @Before
    public void setUp() {
        achievementHandler = mock(AchievementHandler.class);

        assertNotNull(achievementHandler);

        achievementData.add(new Achievement(1, "First Creation", "Create 1 quiz", "QUIZ_CREATE", 1, new UserTag(userId), 0));
        achievementData.add(new Achievement(2, "Expert Creator", "Create 5 quizzes", "QUIZ_CREATE", 5, new UserTag(userId), 0));
        achievementData.add(new Achievement(3, "Achievement Hunter", "Opened achievement list", "ACHIEVEMENT_LIST", 1, new UserTag(userId), 0));

        getAchievementDataProgress1.add(new Achievement(1, "First Creation", "Create 1 quiz", "QUIZ_CREATE", 1, new UserTag(userId), 1));
        getAchievementDataProgress1.add(new Achievement(2, "Expert Creator", "Create 5 quizzes", "QUIZ_CREATE", 5, new UserTag(userId), 1));
        getAchievementDataProgress1.add(new Achievement(3, "Achievement Hunter", "Opened achievement list", "ACHIEVEMENT_LIST", 1, new UserTag(userId), 0));

        getAchievementDataProgress2.add(new Achievement(1, "First Creation", "Create 1 quiz", "QUIZ_CREATE", 1, new UserTag(userId), 2));
        getAchievementDataProgress2.add(new Achievement(2, "Expert Creator", "Create 5 quizzes", "QUIZ_CREATE", 5, new UserTag(userId), 2));
        getAchievementDataProgress2.add(new Achievement(3, "Achievement Hunter", "Opened achievement list", "ACHIEVEMENT_LIST", 1, new UserTag(userId), 0));

    }

    @Test
    public void testInsertUser() {
        System.out.println("\nStarting testInsertUser");
        when(achievementHandler.userAchievementExists(1)).thenReturn(false);
        assertFalse(achievementHandler.userAchievementExists(1));
        when(achievementHandler.userAchievementExists(1)).thenReturn(true);
        assertTrue(achievementHandler.userAchievementExists(1));
        System.out.println("\nEnding testInsertUser");
    }

    @Test
    public void testGetUserAchievements() {
        System.out.println("\nStarting testGetUserAchievements");
        when(achievementHandler.getUserAchievements()).thenReturn(achievementData);
        List<Achievement> list = achievementHandler.getUserAchievements();

        assertEquals(3, list.size());
        assertNotNull(list.get(0));
        assertEquals(userId, list.get(0).getUserID());

        System.out.println("\nEnding testGetUserAchievements");
    }

    @Test
    public void testUpdateProgress() {
        System.out.println("\nStarting testUpdateProgress");
        when(achievementHandler.getUserAchievements()).thenReturn(achievementData);
        List<Achievement> list = achievementHandler.getUserAchievements();

        assertNotNull(list.get(0));
        assertEquals(0,list.get(0).getProgress());
        assertEquals(0,list.get(1).getProgress());
        assertEquals(0,list.get(2).getProgress());
        assertEquals(0,list.get(0).getProgressPercent());

        when(achievementHandler.getUserAchievements()).thenReturn(getAchievementDataProgress1);
        list = achievementHandler.getUserAchievements();
        assertNotNull(list.get(0));
        assertEquals(1,list.get(0).getProgress());
        assertEquals(1,list.get(1).getProgress());
        assertEquals(0,list.get(2).getProgress());
        assertEquals(100,list.get(0).getProgressPercent());
        assertEquals(20,list.get(1).getProgressPercent());

        when(achievementHandler.getUserAchievements()).thenReturn(getAchievementDataProgress2);
        list = achievementHandler.getUserAchievements();
        assertNotNull(list.get(0));
        assertEquals(2,list.get(0).getProgress());
        assertEquals(2,list.get(1).getProgress());
        assertEquals(0,list.get(2).getProgress());
        assertEquals(100,list.get(0).getProgressPercent());
        assertEquals(40,list.get(1).getProgressPercent());

        System.out.println("\nEnding testUpdateProgress");
    }

}
