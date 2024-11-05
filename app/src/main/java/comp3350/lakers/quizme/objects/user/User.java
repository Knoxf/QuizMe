package comp3350.lakers.quizme.objects.user;

public class User {
    private final String firstName;
    private final String lastName;
    private final String password;
    private final String username;
    private final UserTag userTag;


    public User(final UserTag newTag) {
        this.userTag = newTag;
        this.firstName = null;
        this.lastName = null;
        this.username = null;
        this.password = null;

    }

    public User(final UserTag newTag, final String newFirstName, final String newLastName, final String newUsername, final String newPassword) {
        this.userTag = newTag;
        this.firstName = newFirstName;
        this.lastName = newLastName;
        this.username = newUsername;
        this.password = newPassword;

    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUserID() {
        return userTag.getUserID();
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}
