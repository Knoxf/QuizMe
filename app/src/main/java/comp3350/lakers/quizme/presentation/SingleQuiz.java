package comp3350.lakers.quizme.presentation;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizme.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import comp3350.lakers.quizme.logic.AchievementHandler;
import comp3350.lakers.quizme.logic.QuizHandler;
import comp3350.lakers.quizme.objects.quizzes.QuizBase;

public class SingleQuiz extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView answerView;
        LinearLayout linearLayout;
        QuizHandler quizHandler;
        FloatingActionButton fabSubmit;
        EditText answerInput;
        AchievementHandler achievementHandler;


        setContentView(R.layout.single_quiz);
        Bundle extras = getIntent().getExtras();
        quizHandler = new QuizHandler(extras.getString("userId"));
        achievementHandler = new AchievementHandler(extras.getString("achievementUserId"));

        TextView questionView = findViewById(R.id.questionView);
        questionView.setText(extras.getString("Question"));

        answerView = findViewById(R.id.answerView);

        String from = extras.getString("from");
        QuizBase quiz = null;
        int pos = -1;
        int QID = -1;
        if(from.equals("name")){
             pos = extras.getInt("Position");
             quiz = quizHandler.getQuiz(pos);
        }else if(from.equals("category")){
            QID = extras.getInt("QID");
            quiz = quizHandler.getQuizByQID(QID);
        }


        linearLayout = findViewById(R.id.linearLayoutView);
        answerInput = new EditText(this);
        fabSubmit = findViewById(R.id.fabSubmitAnswer);
        assert quiz != null;
        QuizLayout quizLayout = quiz.getLayout();
        quizLayout.generateQuiz(quiz, answerInput, linearLayout, fabSubmit, pos, answerView, quizHandler, this , from , QID, achievementHandler);
    }
}
