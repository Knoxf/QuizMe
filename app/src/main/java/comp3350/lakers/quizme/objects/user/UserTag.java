package comp3350.lakers.quizme.objects.user;
import java.util.UUID;

public class UserTag {
    private final String userID;

    public UserTag(final String newID) {
        this.userID = newID;
    }

    public UserTag() {
        this.userID = UUID.randomUUID().toString();
    }

    public String getUserID() {
        return userID;
    }
}
