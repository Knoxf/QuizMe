package comp3350.lakers.quizme.objects.quizzes;

import androidx.annotation.Nullable;

import comp3350.lakers.quizme.objects.questions.QuestionBase;
import comp3350.lakers.quizme.presentation.QuizLayout;

public abstract class QuizBase {


    protected final QuestionBase question;
    private final String quizName;
    private final String category;
    private final QuizLayout layout;

    private int QID = -1;

    public QuizBase(QuestionBase question, String quizName, String category, QuizLayout layout) {
        this.question = question;
        this.quizName = quizName;
        this.category = category;
        this.layout = layout;
    }

    public int getQID(){
        return QID;
    }

    public void setQID(int QID) {
        this.QID = QID;
    }

    public String getQuestion() {
        return question.getQuestion();
    }

    public String getQuizName() {
        return quizName;
    }

    public String getCategory() {
        return category;
    }

    public String getAnswer() {
        return question.getAnswer();
    }

    public boolean equals(@Nullable QuizBase other) {
        boolean isEqual = false;
        if (other != null) {
            isEqual = this.question.equals(other.question)
                    && this.category.equals(other.category)
                    && this.quizName.equals(other.quizName);
        }
        return isEqual;
    }

    public QuizLayout getLayout() {
        return layout;
    }
}
