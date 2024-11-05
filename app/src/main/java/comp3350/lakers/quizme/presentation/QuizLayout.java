package comp3350.lakers.quizme.presentation;

import android.content.Context;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import comp3350.lakers.quizme.logic.AchievementHandler;
import comp3350.lakers.quizme.logic.QuizHandler;
import comp3350.lakers.quizme.objects.quizzes.QuizBase;

public abstract class QuizLayout {

    protected static final String CORRECT_MSG = "Correct! The answer is: ";
    protected static final String INCORRECT_MSG = "Incorrect! The answer is: ";
    protected String answerText;

    public QuizLayout() {
    }

    public abstract void generateQuiz(QuizBase quiz, EditText answerInput, LinearLayout linearLayout, FloatingActionButton fabSubmit,
                                      int pos, TextView answerView, QuizHandler quizHandler, Context context, String from , int QID, AchievementHandler achievementHandler);

    protected void setFeedback(int pos, TextView answerView, QuizHandler quizHandler , String from , int QID, AchievementHandler achievementHandler){

        if(from.equals("name")){
            boolean correct = quizHandler.checkAnswer(pos,answerText);
            String feedbackMsg;
            if(correct){
                feedbackMsg = CORRECT_MSG + quizHandler.getAnswer(pos);
                answerView.setText(feedbackMsg);

                //update achievement progress
                achievementHandler.updateProgress("QUIZ_CORRECT");
            }else{
                feedbackMsg = INCORRECT_MSG + quizHandler.getAnswer(pos);
                answerView.setText(feedbackMsg);

                //update achievement progress
                achievementHandler.updateProgress("QUIZ_INCORRECT");
            }
            answerView.setText(feedbackMsg);

        }else if(from.equals("category")){
            boolean correct = quizHandler.checkAnswerByQID(QID , answerText);
            String feedbackMsg;
            if(correct){
                feedbackMsg = CORRECT_MSG + quizHandler.getAnswerByQID(QID);
                answerView.setText(feedbackMsg);

                //update achievement progress
                achievementHandler.updateProgress("QUIZ_CORRECT");
            }else{
                feedbackMsg = INCORRECT_MSG + quizHandler.getAnswerByQID(QID);
                answerView.setText(feedbackMsg);

                //update achievement progress
                achievementHandler.updateProgress("QUIZ_INCORRECT");
            }
        }

    }

}
