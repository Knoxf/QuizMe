package comp3350.lakers.quizme.persistence.hsqldb;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import comp3350.lakers.quizme.objects.user.User;
import comp3350.lakers.quizme.objects.user.UserTag;
import comp3350.lakers.quizme.persistence.IUser;

public class UserPersistenceHSQLDB implements IUser {
    private final String dbPath;

    public UserPersistenceHSQLDB(final String dbPath) {
        this.dbPath = dbPath;
    }

    private Connection connection() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }

    private User fromResultSet(final ResultSet resultSet) throws SQLException {
        final String studentID = resultSet.getString("uID");
        final String firstName = resultSet.getString("firstName");
        final String lastName = resultSet.getString("lastName");
        final String username = resultSet.getString("username");
        final String password = resultSet.getString("password");

        return new User(new UserTag(studentID), firstName, lastName, username, password);
    }

    @Override
    public User insertUser(User currentUser) {
        try (final Connection connection = connection()) {
            final PreparedStatement prepareStatement = connection.prepareStatement("INSERT INTO users VALUES(?, ?, ?, ?, ?)");
            prepareStatement.setString(1, currentUser.getUserID());
            prepareStatement.setString(2, currentUser.getFirstName());
            prepareStatement.setString(3, currentUser.getLastName());
            prepareStatement.setString(4, currentUser.getUsername());
            prepareStatement.setString(5, currentUser.getPassword());
            prepareStatement.executeUpdate();
            return currentUser;
        } catch (final SQLException e) {
            throw new DataException(e);
        }
    }


    @Override
    public boolean userExists(String username) {
        final List<User> users = new ArrayList<>();
        try (final Connection connection = connection()) {
            final PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM USERS WHERE USERNAME = ?");
            preparedStatement.setString(1, username);

            final ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                final User user = fromResultSet(resultSet);
                users.add(user);
            }

            resultSet.close();
            preparedStatement.close();

            return users.size() >= 1;
        } catch (final SQLException e) {
            throw new DataException(e);
        }
    }

    @Override
    public User getUserByUsername(String username) {
        try (final Connection connection = connection()) {
            final PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM USERS WHERE USERNAME = ?");
            preparedStatement.setString(1, username);

            final ResultSet resultSet = preparedStatement.executeQuery();
            User user = resultSet.next() ? fromResultSet(resultSet) : null;

            resultSet.close();
            preparedStatement.close();

            return user;
        } catch (final SQLException e) {
            throw new DataException(e);
        }
    }

    @Override
    public void deleteUser(String username) {
        try (final Connection connection = connection()) {
            final PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM USERS WHERE USERNAME = ?");
            preparedStatement.setString(1, username);
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new DataException(e);
        }
    }
}
