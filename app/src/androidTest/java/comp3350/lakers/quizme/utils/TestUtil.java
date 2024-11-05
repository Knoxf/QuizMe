package comp3350.lakers.quizme.utils;


import comp3350.lakers.quizme.logic.UserHandler;
import comp3350.lakers.quizme.objects.user.User;

public class TestUtil {

    private UserHandler userHandler;

    public TestUtil() {
        userHandler = new UserHandler();
    }

    public User getUserByUsername(String username) {
        return userHandler.getUserByUsername(username);
    }

    public void deleteUser(String username) {
        userHandler.deleteUser(username);
    }
}
