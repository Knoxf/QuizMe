package comp3350.lakers.quizme.logic;

import java.util.Collections;
import java.util.List;

import comp3350.lakers.quizme.application.Services;
import comp3350.lakers.quizme.objects.quizzes.QuizBase;
import comp3350.lakers.quizme.persistence.IQuiz;

public class QuizHandler {

    private final IQuiz quizData;
    private List<QuizBase> questions;

    public QuizHandler(String userId) {
        quizData = Services.getQuizPersistence();
        questions = quizData.getQuizList(userId);
    }

    public QuizHandler(IQuiz quizData, String userId) {
        this.quizData = quizData;
        questions = quizData.getQuizList(userId);
    }

    public int getSize() {
        return questions.size();
    }

    public QuizBase getQuiz(int position) {
        QuizBase question = null;
        if (position >= 0) {
            question = questions.get(position);
        }
        return question;
    }

    public QuizBase getQuizByQID(int QID){
        QuizBase targetQuiz = null;
        for(int i = 0 ; i < questions.size() ; i++){
            if(questions.get(i).getQID() == QID){
                targetQuiz = questions.get(i);
            }
        }

        return targetQuiz;
    }

    public QuizBase insertQuiz(QuizBase newQuestion, String userId) {
        QuizBase question = null;
        if (newQuestion != null) {
            question = quizData.insertQuiz(newQuestion, userId);
        }

        return question;
    }

    public List<QuizBase> getQuizList(String userId) {
        questions = quizData.getQuizList(userId);
        return Collections.unmodifiableList(questions);
    }

    public boolean deleteQuiz(int position, String userId) {
        boolean deleteSuccessful = false;
        if (validatePosition(position)) {
            deleteSuccessful = quizData.deleteQuiz(position, userId);
        }
        return deleteSuccessful;
    }

    private boolean validatePosition(int position) {
        return position >= 0 && position < questions.size();
    }

    public String getAnswer(int position) {
        String answer;
        answer = questions.get(position).getAnswer();
        return answer;
    }

    public String getAnswerByQID(int QID){
        String answer = "";
        for(int i = 0 ; i < questions.size() ; i++){
            if(questions.get(i).getQID() == QID){
                answer = questions.get(i).getAnswer();
            }
        }

        return answer;
    }

    public boolean checkAnswer(int position, String answer) {
        boolean correct;
        correct = questions.get(position).getAnswer().equals(answer);
        return correct;
    }

    public boolean checkAnswerByQID(int QID , String answer){
        boolean correct = false;
        for(int i = 0 ; i < questions.size() ; i++){
            if(questions.get(i).getQID() == QID){
                correct = questions.get(i).getAnswer().equals(answer);
            }
        }

        return correct;
    }
}
