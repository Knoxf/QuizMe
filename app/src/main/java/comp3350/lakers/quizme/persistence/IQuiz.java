package comp3350.lakers.quizme.persistence;

import java.util.List;

import comp3350.lakers.quizme.objects.quizzes.QuizBase;
public interface IQuiz {
    List<QuizBase> getQuizList(String userId);
    QuizBase insertQuiz(final QuizBase currentQuestion, String userId);
    boolean deleteQuiz(final int position, String userId);


}
