package comp3350.lakers.quizme.presentation;

import android.content.Context;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import comp3350.lakers.quizme.logic.AchievementHandler;
import comp3350.lakers.quizme.logic.QuizHandler;
import comp3350.lakers.quizme.objects.quizzes.QuizBase;

public class QuizLayoutLongAnswer extends QuizLayout {



    @Override
    public void generateQuiz(QuizBase quiz, EditText answerInput, LinearLayout linearLayout,
                             FloatingActionButton fabSubmit, int pos, TextView answerView,
                             QuizHandler quizHandler, Context context , String from, int QID, AchievementHandler achievementHandler) {
        answerInput.setHint("Type your answer");
        linearLayout.addView(answerInput);

        fabSubmit.setOnClickListener(view -> {
            answerText = answerInput.getText().toString();
            setFeedback(pos, answerView, quizHandler , from , QID, achievementHandler);
        });

    }
}
