package comp3350.lakers.quizme.persistence;

import java.util.List;

import comp3350.lakers.quizme.objects.achievements.Achievement;

public interface IAchievement {
    List<Achievement> getUserAchievements(String userID);
    void updateProgress(String userId, String category);
    void insertUser(String userId);
    boolean userAchievementExists(String userId, int achievementId);
}
