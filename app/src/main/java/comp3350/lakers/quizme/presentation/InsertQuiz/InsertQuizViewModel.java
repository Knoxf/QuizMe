package comp3350.lakers.quizme.presentation.InsertQuiz;

import android.annotation.SuppressLint;

import androidx.lifecycle.ViewModel;

import java.util.List;

import comp3350.lakers.quizme.logic.QuizHandler;
import comp3350.lakers.quizme.objects.quizzes.LongAnswerQuiz;
import comp3350.lakers.quizme.objects.quizzes.MultipleChoiceQuiz;
import comp3350.lakers.quizme.objects.quizzes.QuizBase;
import comp3350.lakers.quizme.presentation.MainActivity;

public class InsertQuizViewModel extends ViewModel {
    @SuppressLint("StaticFieldLeak")
    private MainActivity mainActivity;
    private String quizName;

    protected void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    protected void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    protected void insertLongAnswerQuestion(QuizHandler quizHandler, String question, String answer, String category, String userId) {
        quizHandler.insertQuiz(new LongAnswerQuiz(question, answer, quizName, category), userId);
    }

    protected boolean checkOption(String[] option, String answer){
        boolean hasSame = false;
        for(int i = 0 ; i < option.length ; i++){
            if(option[i].equals(answer)){
                hasSame = true;
            }
        }
        return hasSame;
    }

    protected boolean hasSameQuizName(List<QuizBase> quizBaseList , String quizName){
        boolean hasSameName = false;
        for(int i = 0 ; i < quizBaseList.size() ; i++){
            if(quizBaseList.get(i).getQuizName().equals(quizName)){
                hasSameName = true;
                break;
            }
        }

        return hasSameName;
    }

    protected void setMainMenuNoQuizBlur(){
        mainActivity.setNoQuizTextUnBlur();
    }


    protected void insertMultipleQuestion(QuizHandler quizHandler, String question, String[] option, String answer, String category, String userId) {
        quizHandler.insertQuiz(new MultipleChoiceQuiz(question, answer, option, quizName, category), userId);
    }

    protected void setMainMenuFloatingButton() {
        mainActivity.setFloatingActionButtonVisibility(true);
    }

    protected void setRecyclerView() {
        mainActivity.setRecyclerViewVisibility();
    }

}