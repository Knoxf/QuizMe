package comp3350.lakers.quizme.presentation;

import static comp3350.lakers.quizme.persistence.utils.HSQLDBHelper.copyDatabaseToDevice;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizme.R;
import com.google.android.material.textfield.TextInputLayout;

import comp3350.lakers.quizme.logic.UserHandler;
import comp3350.lakers.quizme.logic.utils.UPValidator;
import comp3350.lakers.quizme.objects.user.User;
import comp3350.lakers.quizme.objects.user.UserTag;

public class SignupActivity extends AppCompatActivity {

    private UserHandler userHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        copyDatabaseToDevice(this);
        userHandler = new UserHandler();
        TextView loginText = findViewById(R.id.loginText);

        loginText.setOnClickListener(v -> {
            Intent i = new Intent(SignupActivity.this, LoginActivity.class);
            startActivity(i);
        });
    }

    public void buttonCreateUserOnClick(View v) {
        User user = createUserFromText();

        if (user != null) {
            String errorMessage = getErrorMessage(user);
            if (errorMessage == null) {
                try {
                    if (userHandler.insertUser(user)) {
                        showToast("Sign up successful");
                        Intent i = new Intent(SignupActivity.this, LoginActivity.class);
                        startActivity(i);
                        finish();
                    } else {
                        showToast("Unable to create user.");
                    }
                } catch (final Exception e) {
                    showToast("Error: " + e.getMessage());
                }
            } else {
                showToast(errorMessage);
            }
        }
    }

    private User createUserFromText() {
        TextInputLayout fNameEdit = findViewById(R.id.signUpFirstName);
        TextInputLayout lNameEdit = findViewById(R.id.signUpLastName);
        TextInputLayout uNameEdit = findViewById(R.id.signUpUsername);
        TextInputLayout passEdit = findViewById(R.id.signUpPassword);
        TextInputLayout cPassEdit = findViewById(R.id.signUpConfirmPassword);

        String password = passEdit.getEditText().getText().toString();
        String confirmPass = cPassEdit.getEditText().getText().toString();
        String passMessage = isPasswordValid(password, confirmPass);
        if (passMessage == null) {
            return new User(
                    new UserTag(),
                    fNameEdit.getEditText().getText().toString(),
                    lNameEdit.getEditText().getText().toString(),
                    uNameEdit.getEditText().getText().toString(),
                    password
            );
        } else {
            showToast(passMessage);
        }

        return null;
    }

    private String isPasswordValid(String p1, String p2) {
        if (!UPValidator.isValidPassword(p1)) {
            return "Password must be at least 6 characters";
        }

        if (!UPValidator.isCorrectPassword(p1, p2)) {
            return "Passwords do not match";
        }

        return null;
    }

    private String getErrorMessage(User user) {
        if (!UPValidator.isValidInput(user.getFirstName())) {
            return "First name required";
        }
        if (!UPValidator.isValidInput(user.getLastName())) {
            return "Last name required";
        }
        if (!UPValidator.isValidInput(user.getUsername())) {
            return "Username required";
        }

        if (UPValidator.userExists(userHandler, user.getUsername())) {
            return "User " + user.getUsername() + " already exists.";
        }
        return null;
    }

    private void showToast(String message) {
        Toast.makeText(SignupActivity.this, message, Toast.LENGTH_LONG).show();
    }
}
