package comp3350.lakers.quizme.presentation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizme.R;
import com.google.android.material.textfield.TextInputLayout;

import comp3350.lakers.quizme.logic.UserHandler;
import comp3350.lakers.quizme.logic.utils.UPValidator;
import comp3350.lakers.quizme.persistence.utils.HSQLDBHelper;

public class LoginActivity extends AppCompatActivity {

    private static final String SHARED_PREFS = "sharedPrefs";
    private static final String USERNAME_KEY = "usernameKey";
    private static final String PASSWORD_KEY = "passwordKey";

    private UserHandler userHandler;
    private SharedPreferences sharedPreferences;

    private TextInputLayout userEdt, passEdt;
    private Button loginBtn;
    private TextView signUp;

    private String userPref, passPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initializeViews();
        initializeSharedPreferences();
        copyDatabaseToDevice();

        userHandler = new UserHandler();

        loginBtn.setOnClickListener(v -> handleLogin());

        signUp.setOnClickListener(v -> {
            Intent i = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(i);
        });
    }

    private void initializeViews() {
        userEdt = findViewById(R.id.loginUsername);
        passEdt = findViewById(R.id.loginPassword);
        loginBtn = findViewById(R.id.loginBtn);
        signUp = findViewById(R.id.signUpText);
    }

    private void initializeSharedPreferences() {
        sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        userPref = sharedPreferences.getString(USERNAME_KEY, null);
        passPref = sharedPreferences.getString(PASSWORD_KEY, null);
    }

    private void copyDatabaseToDevice() {
        HSQLDBHelper.copyDatabaseToDevice(this);
    }

    private void handleLogin() {
        String username = userEdt.getEditText().getText().toString();
        String password = passEdt.getEditText().getText().toString();

        if (TextUtils.isEmpty(username) && TextUtils.isEmpty(password)) {
            setErrorMessage(passEdt, "Enter a username and a password");
        } else if (TextUtils.isEmpty(username)) {
            setErrorMessage(passEdt, "Enter a username");
        } else if (TextUtils.isEmpty(password)) {
            setErrorMessage(passEdt, "Enter a password");
        } else {
            if (UPValidator.isValidInput(username) && UPValidator.isValidPassword(password)) {
                if (userHandler.userExists(username)) {
                    if (UPValidator.isCorrectPassword(userHandler.getUserByUsername(username).getPassword() ,password)) {
                        setErrorMessage(passEdt, "");
                        saveCredentials(username, password);
                        startMainActivity();
                    } else {
                        setErrorMessage(passEdt, "Incorrect username or password");
                    }
                } else {
                    setErrorMessage(passEdt, "Incorrect username or password");
                }
            } else {
                setErrorMessage(passEdt, "Incorrect username or password");
            }
        }
    }

    private void saveCredentials(String username, String password) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USERNAME_KEY, username);
        editor.putString(PASSWORD_KEY, password);
        editor.apply();
    }

    private void startMainActivity() {
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (userPref != null && passPref != null) {
            startMainActivity();
        }
    }

    private void setErrorMessage(TextInputLayout inputLayout, String msg) {
        inputLayout.setHelperText(msg);

    }
}
