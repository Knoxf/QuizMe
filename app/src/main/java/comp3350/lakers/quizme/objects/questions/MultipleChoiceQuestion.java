package comp3350.lakers.quizme.objects.questions;

public class MultipleChoiceQuestion extends QuestionBase {
    private final String[] choices;
    public MultipleChoiceQuestion(String question, String answer, String[] choices) {
        super(question, answer);
        this.choices = choices;
    }

    public String[] getChoices() {
        return choices;
    }
}
