package comp3350.lakers.quizme.logic.utils;

import java.util.List;

import comp3350.lakers.quizme.objects.quizzes.QuizBase;

public class QuizValidator {
    public static boolean isMCQValid(String question, String[] options, String answer, String category) {
        boolean areOptionsValid = true;
        for (int i = 0; i < options.length && areOptionsValid; i++) {
            if (options[i] == null || options[i].equals("")) {
                areOptionsValid = false;
            }
        }
        return areOptionsValid && isLAQValid(question, answer, category);
    }
    public static boolean isLAQValid(String question,  String answer, String category) {
        return isValid(question) && isValid(answer) && isValid(category);
    }



    public static boolean isValid(String s) {
        return s!= null && !s.equals("");
    }
}
