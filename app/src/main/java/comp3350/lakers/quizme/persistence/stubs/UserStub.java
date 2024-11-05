package comp3350.lakers.quizme.persistence.stubs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import comp3350.lakers.quizme.objects.user.User;
import comp3350.lakers.quizme.objects.user.UserTag;
import comp3350.lakers.quizme.persistence.IUser;

public class UserStub implements IUser {
    private List<User> users;

    public UserStub() {
        users = new ArrayList<>();

        users.add(new User(
                new UserTag("29289049-b0c4-4aff-9d05-667067ace368"),
                "George",
                "Washington",
                "america1776",
                "suckItEngland"
        ));

        users.add(new User(
                new UserTag("5cde8f75-08e1-4d76-ae16-18b9ef465cc8"),
                "Napoleon",
                "Bonaparte",
                "waterloo",
                "defeat"
        ));
    }

    @Override
    public User insertUser(User currentUser) {

        return users.add(currentUser) ? currentUser : null;
    }

    @Override
    public boolean userExists(String username) {
        boolean found = false;
        Iterator<User> it = users.iterator();

        while (username!= null && it.hasNext() && !found) {
            if (it.next().getUsername().equals(username)) {
                found = true;
            }
        }
        return found;
    }

    @Override
    public User getUserByUsername(String username) {
        User user = null;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(username)) {
                user = users.get(i);
            }
        }
        return user;
    }

    @Override
    public void deleteUser(String username) {
        users.removeIf(user -> user.getUsername().equals(username));
    }
}
