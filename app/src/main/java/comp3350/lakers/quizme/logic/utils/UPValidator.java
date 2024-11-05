package comp3350.lakers.quizme.logic.utils;

import comp3350.lakers.quizme.logic.UserHandler;

public class UPValidator {

    public static boolean isCorrectPassword(String p1, String p2) {
        return p1.equals(p2);
    }

    public static boolean isValidInput(String username) {
        return username != null && !username.equals("");
    }

    public static boolean isValidPassword(String password) {
        return password != null && !password.equals("") && password.length() >= 6;
    }

    public static boolean userExists(UserHandler userHandler, String username) {
        return userHandler != null && username != null && userHandler.userExists(username);
    }
}
