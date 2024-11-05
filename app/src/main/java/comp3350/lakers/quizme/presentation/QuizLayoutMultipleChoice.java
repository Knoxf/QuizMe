package comp3350.lakers.quizme.presentation;

import android.content.Context;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import comp3350.lakers.quizme.logic.AchievementHandler;
import comp3350.lakers.quizme.logic.QuizHandler;
import comp3350.lakers.quizme.objects.quizzes.MultipleChoiceQuiz;
import comp3350.lakers.quizme.objects.quizzes.QuizBase;

public class QuizLayoutMultipleChoice extends QuizLayout {

    @Override
    public void generateQuiz(QuizBase quiz, EditText answerInput, LinearLayout linearLayout, FloatingActionButton fabSubmit,
                             int pos, TextView answerView, QuizHandler quizHandler, Context context , String from, int QID, AchievementHandler achievementHandler) {
        String[] choices = ((MultipleChoiceQuiz) quiz).getOption();
        for (String choice : choices) {
            Button btn = new Button(context);
            btn.setText(choice);
            btn.setOnClickListener(view -> {
                answerText = choice;
                answerView.setText(answerText);
            });
            linearLayout.addView(btn);
        }

        fabSubmit.setOnClickListener(view -> setFeedback(pos, answerView, quizHandler , from , QID, achievementHandler));
    }
}
