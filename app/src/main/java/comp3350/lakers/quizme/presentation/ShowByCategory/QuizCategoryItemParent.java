package comp3350.lakers.quizme.presentation.ShowByCategory;

import java.util.List;

public class QuizCategoryItemParent {

    private String categoryTitle;
    private List<QuizQuestionItemChild> questionItemList;

    public QuizCategoryItemParent(String categoryTitle , List<QuizQuestionItemChild> questionItemList){
        this.categoryTitle = categoryTitle;
        this.questionItemList = questionItemList;
    }

    public String getCategoryTitle(){
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle){
        this.categoryTitle = categoryTitle;
    }

    public List<QuizQuestionItemChild> getQuestionItemList(){
        return questionItemList;
    }

    public void setQuestionItemList(List<QuizQuestionItemChild> questionItemList) {
        this.questionItemList = questionItemList;
    }
}
