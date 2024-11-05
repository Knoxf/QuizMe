package comp3350.lakers.quizme.objects.quizzes;

import comp3350.lakers.quizme.objects.questions.LongAnswerQuestion;
import comp3350.lakers.quizme.presentation.QuizLayoutLongAnswer;

public class LongAnswerQuiz extends QuizBase {
    public LongAnswerQuiz(String question, String answer, String quizName, String category) {
        super(new LongAnswerQuestion(question, answer), quizName, category, new QuizLayoutLongAnswer());
    }
}
