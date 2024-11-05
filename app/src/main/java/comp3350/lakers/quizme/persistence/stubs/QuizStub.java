package comp3350.lakers.quizme.persistence.stubs;

import java.util.ArrayList;
import java.util.List;

import comp3350.lakers.quizme.objects.quizzes.MultipleChoiceQuiz;
import comp3350.lakers.quizme.objects.quizzes.QuizBase;
import comp3350.lakers.quizme.persistence.IQuiz;

public class QuizStub implements IQuiz {
    List<QuizBase> questions = new ArrayList<>();

    public QuizStub() {
        questions.add(new MultipleChoiceQuiz(
                "Which city is the capital of Canada?",
                "Ottawa",
                new String[]{"Toronto", "Winnipeg", "Ottawa", "Vancouver"}, "Capital", "Geography")
        );
        questions.add(new MultipleChoiceQuiz("What is 2+2?",
                "4",
                new String[]{"1", "2", "3", "4"}, "Sum", "Math")
        );
        questions.add(new MultipleChoiceQuiz("What is 2*2?",
                "4",
                new String[]{"1", "2", "3", "4"}, "Multiplication", "Math")
        );
        questions.add(new MultipleChoiceQuiz("Who do you think you are?",
                "I do not know",
                new String[]{"No one", "Nappster", "I do not know", "Heck yea"}, "Who?", "Philosophy")
        );
    }

    @Override
    public List<QuizBase> getQuizList(String userId) {
        return questions;
    }

    @Override
    public QuizBase insertQuiz(QuizBase currentQuestion, String userId) {
        questions.add(currentQuestion);
        return currentQuestion;
    }

    @Override
    public boolean deleteQuiz(int position, String userId) {
        QuizBase questionToBeDeleted = questions.get(position);
        QuizBase delete = null;
        if (validatePosition(position)) {
            delete = questions.remove(position);
        }
        return questionToBeDeleted == delete;
    }

    private boolean validatePosition(int position) {
        return position >= 0 && position < questions.size();
    }

    public int getQuestionsLength() {
        return questions.size();
    }
}
