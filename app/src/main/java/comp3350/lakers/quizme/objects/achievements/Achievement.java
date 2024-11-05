package comp3350.lakers.quizme.objects.achievements;

import comp3350.lakers.quizme.objects.user.UserTag;

public class Achievement {
    private final String name;
    private final String description;
    private final String category;
    private final int requirement;

    private final int achievementID;

    private final UserTag userTag;
    private int progress;
    public Achievement(final int achievementID, final UserTag userTag) {
        this.achievementID = achievementID;
        this.name = null;
        this.description = null;
        this.category = null;
        this.requirement = 1;
        this.userTag = userTag;
        this.progress = 0;
    }

    public Achievement(final int achievementID, final String newName, final String newDescription, final String newCategory, final int newRequirement, final UserTag newTag, final int newProgress) {
        this.achievementID = achievementID;
        this.name = newName;
        this.description = newDescription;
        this.category = newCategory;
        this.requirement = newRequirement;
        this.userTag = newTag;
        this.progress = newProgress;

    }

    public int getAchievementID() {return achievementID;}
    public String getName() {return name;}
    public String getDescription() {return description;}
    public String getCategory() {return category;}
    public int getRequirement() {return requirement;}
    public String getUserID() {
        return userTag.getUserID();
    }
    public int getProgress() {return progress;}
    public void setProgress(int newProgress) {progress = newProgress;}

    public int getProgressPercent() {
        float progressDecimal = (float)progress / (float)requirement;
        return (int)Math.min(progressDecimal*100,100);
    }

}
