package comp3350.lakers.quizme.persistence.stubs;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import comp3350.lakers.quizme.objects.achievements.Achievement;
import comp3350.lakers.quizme.objects.user.UserTag;
import comp3350.lakers.quizme.persistence.IAchievement;

public class AchievementStub implements IAchievement {

    List<Achievement> achievements;

    public AchievementStub() {
        achievements = new ArrayList<>();
    }
    @Override
    public List<Achievement> getUserAchievements(String userID) {
        List<Achievement> userAchievements = new ArrayList<>();
        for(int i = 0; i < achievements.size(); i++) {
            if(Objects.equals(achievements.get(i).getUserID(), userID)) {
                userAchievements.add(achievements.get(i));
            }
        }
        return userAchievements;
    }

    @Override
    public void updateProgress(String userId, String category) {
        for(int i = 0; i < achievements.size(); i++) {
            if(Objects.equals(achievements.get(i).getCategory(), category) && Objects.equals(achievements.get(i).getUserID(), userId)) {
                achievements.get(i).setProgress(achievements.get(i).getProgress() + 1);
            }
        }
    }

    @Override
    public void insertUser(String userId) {
        if(!userAchievementExists(userId, 1)){
            achievements.add(new Achievement(1, "First Creation", "Create 1 quiz", "QUIZ_CREATE", 1, new UserTag(userId), 0));
        }
        if(!userAchievementExists(userId, 2)) {
            achievements.add(new Achievement(2, "Expert Creator", "Create 5 quizzes", "QUIZ_CREATE", 5, new UserTag(userId), 0));
        }
        if(!userAchievementExists(userId, 3)) {
            achievements.add(new Achievement(3, "Achievement Hunter", "Opened achievement list", "ACHIEVEMENT_LIST", 1, new UserTag(userId), 0));
        }
    }

    @Override
    public boolean userAchievementExists(String userId, int achievementId) {
        boolean exists = false;
        for(int i = 0; i < achievements.size(); i++) {
            if(Objects.equals(achievements.get(i).getUserID(), userId) && achievements.get(i).getAchievementID() == achievementId) {
                exists = true;
            }
        }
        return exists;
    }
}
