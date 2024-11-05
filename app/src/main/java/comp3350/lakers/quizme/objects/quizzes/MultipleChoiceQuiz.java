package comp3350.lakers.quizme.objects.quizzes;

import androidx.annotation.Nullable;

import java.util.Arrays;

import comp3350.lakers.quizme.objects.questions.MultipleChoiceQuestion;
import comp3350.lakers.quizme.presentation.QuizLayoutMultipleChoice;

public class MultipleChoiceQuiz extends QuizBase {


    public MultipleChoiceQuiz(String question, String answer, String[] option, String quizName, String category) {
        super(new MultipleChoiceQuestion(question, answer, option), quizName, category, new QuizLayoutMultipleChoice());
    }

    public String[] getOption() {
        return ((MultipleChoiceQuestion)question).getChoices();
    }

    @Override
    public boolean equals(@Nullable QuizBase other) {
        boolean isEquals = false;
        if (other != null) {
            isEquals = Arrays.equals(getOption(), ((MultipleChoiceQuiz) other).getOption());
        }
        return super.equals(other) && isEquals;
    }


}
