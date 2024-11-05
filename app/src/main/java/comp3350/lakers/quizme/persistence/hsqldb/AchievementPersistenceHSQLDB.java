package comp3350.lakers.quizme.persistence.hsqldb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import comp3350.lakers.quizme.objects.achievements.Achievement;
import comp3350.lakers.quizme.objects.user.UserTag;
import comp3350.lakers.quizme.persistence.IAchievement;

public class AchievementPersistenceHSQLDB implements IAchievement {

    private final String dbPath;
    private boolean userAchievementsExist = false;

    public AchievementPersistenceHSQLDB(final String dbPath) {
        this.dbPath = dbPath;
    }

    private Connection connection() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }

    private Achievement fromResultSet(final ResultSet resultSet) throws SQLException {
        final int achievementID = resultSet.getInt("achID");
        final String name = resultSet.getString("achievementName");
        final String description = resultSet.getString("achievementDescription");
        final String category = resultSet.getString("achievementCategory");
        final int requirement = resultSet.getInt("achievementRequirement");
        final String studentID = resultSet.getString("uID");
        final int progress = resultSet.getInt("achievementProgress");

        return new Achievement(achievementID, name, description, category, requirement, new UserTag(studentID), progress);
    }

    @Override
    public List<Achievement> getUserAchievements(String userId) {
        if(!userAchievementsExist) {
            insertUser(userId);
        }
        final List<Achievement> achievements = new ArrayList<>();
        try (final Connection connection = connection()) {
            final PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM achievements LEFT JOIN userAchievements ON achievements.achID = userAchievements.achID WHERE uID=?");
            preparedStatement.setString(1, userId);
            final ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                final Achievement achievement = fromResultSet(resultSet);
                achievements.add(achievement);
            }

            resultSet.close();

            return achievements;
        } catch (final SQLException e) {
            throw new DataException(e);
        }
    }

    @Override
    public void updateProgress(String userId, String category) {
        if(!userAchievementsExist) {
            insertUser(userId);
        }
        try (final Connection connection = connection()) {
            final PreparedStatement preparedStatement = connection.prepareStatement("UPDATE userAchievements SET achievementProgress = achievementProgress + 1 WHERE uID=? AND achID in (SELECT achID FROM achievements WHERE achievementCategory=?)");
            preparedStatement.setString(1, userId);
            preparedStatement.setString(2, category);
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new DataException(e);
        }
    }

    @Override
    public void insertUser(String userId) {
        //insert user's achievements into table
        try (final Connection connection = connection()) {
            final PreparedStatement countStatement = connection.prepareStatement("SELECT COUNT(*) AS count FROM achievements");
            final ResultSet resultSet = countStatement.executeQuery();
            resultSet.next();
            final int count = resultSet.getInt("count");
            resultSet.close();
            for(int i = 1; i < count+1; i++) {
                if(!userAchievementExists(userId, i)) {
                    final PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO userAchievements VALUES(?, ?, 0)");
                    insertStatement.setString(1, userId);
                    insertStatement.setInt(2, i);
                    insertStatement.executeUpdate();
                }
            }
            userAchievementsExist = true;
        } catch (final SQLException e) {
            throw new DataException(e);
        }
    }

    @Override
    public boolean userAchievementExists(String userId, int achievementId) {
        boolean exists = false;
        try (final Connection connection = connection()) {
            final PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) AS count FROM userAchievements WHERE uID=? and achID=?");
            preparedStatement.setString(1, userId);
            preparedStatement.setInt(2, achievementId);
            final ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            final int count = resultSet.getInt("count");
            if(count >= 1){
                exists = true;
            }
            resultSet.close();
        } catch (final SQLException e) {
            throw new DataException(e);
        }
        return exists;
    }

}
