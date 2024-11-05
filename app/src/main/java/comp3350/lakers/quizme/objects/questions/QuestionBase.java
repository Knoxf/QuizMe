package comp3350.lakers.quizme.objects.questions;

import androidx.annotation.Nullable;


public abstract class QuestionBase {
    private final String question;
    private final String answer;
    public QuestionBase(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }
    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public boolean equals(@Nullable QuestionBase other) {
        boolean isEqual = false;
        if (other != null) {
            isEqual = this.question.equals(other.question)
                    && this.answer.equals(other.answer);
        }
        return isEqual;
    }
}
