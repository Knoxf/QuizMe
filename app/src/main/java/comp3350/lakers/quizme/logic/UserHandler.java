package comp3350.lakers.quizme.logic;

import comp3350.lakers.quizme.application.Services;
import comp3350.lakers.quizme.logic.utils.UPValidator;
import comp3350.lakers.quizme.objects.user.User;
import comp3350.lakers.quizme.persistence.IUser;

public class UserHandler {

    private final IUser userData;

    public UserHandler() {
        userData = Services.getUserPersistence();
    }

    public UserHandler(IUser userData) {
        this.userData = userData;
    }

    public boolean insertUser(User currentUser) {
        return currentUser != null && userData.insertUser(currentUser) != null;
    }

    public User getUserByUsername(String username) {
        return UPValidator.isValidInput(username) ? userData.getUserByUsername(username) : null;
    }

    public boolean userExists(String username) {
        return userData.userExists(username);
    }

    public void deleteUser(String username) {
        userData.deleteUser(username);
    }
}
