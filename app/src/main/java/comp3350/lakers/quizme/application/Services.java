package comp3350.lakers.quizme.application;

import comp3350.lakers.quizme.persistence.IQuiz;
import comp3350.lakers.quizme.persistence.IUser;
import comp3350.lakers.quizme.persistence.IAchievement;
import comp3350.lakers.quizme.persistence.hsqldb.QuizPersistenceHSQLDB;
import comp3350.lakers.quizme.persistence.hsqldb.UserPersistenceHSQLDB;
import comp3350.lakers.quizme.persistence.hsqldb.AchievementPersistenceHSQLDB;

public class Services {
    private static IQuiz quizPersistence = null;
    private static IUser userPersistence = null;
    private static IAchievement achievementPersistence = null;
    public static synchronized IQuiz getQuizPersistence() {
        if (quizPersistence == null) {
            quizPersistence = new QuizPersistenceHSQLDB(Main.getDBPathName());
        }
        return quizPersistence;
    }

    public static synchronized IUser getUserPersistence() {
        if (userPersistence == null) {
            userPersistence = new UserPersistenceHSQLDB(Main.getDBPathName());
        }
        return userPersistence;
    }

    public static synchronized IAchievement getAchievementPersistence() {
        if (achievementPersistence == null) {
            achievementPersistence = new AchievementPersistenceHSQLDB(Main.getDBPathName());
        }
        return achievementPersistence;
    }
}
