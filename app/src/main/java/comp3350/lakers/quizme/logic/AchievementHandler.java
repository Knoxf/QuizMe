package comp3350.lakers.quizme.logic;

import java.util.List;

import comp3350.lakers.quizme.application.Services;
import comp3350.lakers.quizme.objects.achievements.Achievement;
import comp3350.lakers.quizme.persistence.IAchievement;

public class AchievementHandler {

    private final IAchievement achievementData;
    private final String userId;

    public AchievementHandler(String userId) {
        achievementData = Services.getAchievementPersistence();
        this.userId = userId;
    }

    public AchievementHandler(IAchievement achievementData, String userId) {
        this.achievementData = achievementData;
        this.userId = userId;
    }

    public List<Achievement> getUserAchievements() {
        return achievementData.getUserAchievements(userId);
    }

    public void updateProgress(String category) {
        achievementData.updateProgress(userId, category);
    }

    public void insertUser() {
        achievementData.insertUser(userId);
    }
    public boolean userAchievementExists(int achievementId){
        return achievementData.userAchievementExists(userId, achievementId);
    }

    public String getUserId() {return userId;}

}
