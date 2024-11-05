package comp3350.lakers.quizme.persistence;

import comp3350.lakers.quizme.objects.user.User;

public interface IUser {
    User insertUser(final User currentUser);

    boolean userExists(final String username);

    User getUserByUsername(final String username);
    void deleteUser(final String username);
}
