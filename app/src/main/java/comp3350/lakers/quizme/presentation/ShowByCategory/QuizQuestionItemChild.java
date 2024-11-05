package comp3350.lakers.quizme.presentation.ShowByCategory;

public class QuizQuestionItemChild {
    private String quizCardQuestion;
    private int QID;
    private String UUID;

    public QuizQuestionItemChild(String quizCardQuestion, int QID , String UUID){
        this.quizCardQuestion = quizCardQuestion;
        this.QID = QID;
        this.UUID = UUID;
    }

    public String getQuizCardQuestion(){
        return quizCardQuestion;
    }

    public int getQID() {
        return QID;
    }

    public String getUUID() {
        return UUID;
    }
}
