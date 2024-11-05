package comp3350.lakers.quizme.presentation.InsertQuiz;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.example.quizme.databinding.FragmentInsertQuizBinding;

import java.util.Objects;

import comp3350.lakers.quizme.logic.AchievementHandler;
import comp3350.lakers.quizme.logic.QuizHandler;
import comp3350.lakers.quizme.logic.utils.QuizValidator;
import comp3350.lakers.quizme.presentation.MainActivity;

public class InsertQuizFragment extends Fragment {


    private FragmentInsertQuizBinding binding;
    private InsertQuizViewModel InsertQuizViewModel;

    private String userId;
    private QuizHandler quizHandler;
    private AchievementHandler achievementHandler;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        InsertQuizViewModel = new ViewModelProvider(this)
                .get(comp3350.lakers.quizme.presentation.InsertQuiz.InsertQuizViewModel.class);

        InsertQuizViewModel.setMainActivity((MainActivity) getActivity());
        binding = FragmentInsertQuizBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        userId = getArguments().getString("userId");
        quizHandler = new QuizHandler(userId);
        achievementHandler = new AchievementHandler(userId);
        binding.switchQuizType.setChecked(true);
        binding.switchQuizType.setChecked(false);
        setMultipleQuestionInVisible();
        switchChecker();

        return root;
    }


    public void switchChecker() {

        binding.nextQuestion.setOnClickListener(v -> getLongAnswerQuestionText());


        binding.switchQuizType.setOnCheckedChangeListener((buttonView, isChecked) -> {
            //on multiple question part
            if (isChecked) {
                //make long answer question inVisible
                setLongAnswerQuestionInVisible();

                //make multiple question Visible
                setMultipleQuestionVisible();

                binding.nextQuestion.setOnClickListener(v -> {
                    String question = Objects.requireNonNull(binding.multipleQuestionQuestion.getText()).toString();
                    String[] option = new String[]{Objects.requireNonNull(binding.multipleQuestionOptionA.getText()).toString()
                            , Objects.requireNonNull(binding.multipleQuestionOptionB.getText()).toString()
                            , Objects.requireNonNull(binding.multipleQuestionOptionC.getText()).toString()
                            , Objects.requireNonNull(binding.multipleQuestionOptionD.getText()).toString()};
                    String answer = Objects.requireNonNull(binding.multipleQuestionAnswer.getText()).toString();
                    String category = Objects.requireNonNull(Objects.requireNonNull(binding.category.getText()).toString());

                    String quizName = Objects.requireNonNull(Objects.requireNonNull(binding.quizname.getText()).toString());
                    InsertQuizViewModel.setQuizName(quizName);

                    boolean validOption = InsertQuizViewModel.checkOption(option , answer);
                    boolean hasSameQuizName = InsertQuizViewModel.hasSameQuizName(quizHandler.getQuizList(userId) , quizName);
                    if (QuizValidator.isMCQValid(question, option, answer, category) && validOption && !hasSameQuizName) {
                        InsertQuizViewModel.insertMultipleQuestion(quizHandler, question, option, answer, category, userId);
                        binding.multipleQuestionQuestion.getText().clear();
                        binding.multipleQuestionAnswer.getText().clear();
                        binding.multipleQuestionOptionA.getText().clear();
                        binding.multipleQuestionOptionB.getText().clear();
                        binding.multipleQuestionOptionC.getText().clear();
                        binding.multipleQuestionOptionD.getText().clear();
                        binding.multipleQuestionAnswer.getText().clear();

                        //update achievement progress
                        achievementHandler.updateProgress("QUIZ_CREATE");
                    } else{
                        checkEmpty(category , quizName);

                        if(hasSameQuizName){
                            setSameQuizNameError();
                        }

                        if(!QuizValidator.isValid(question)){
                            binding.multipleQuestionQuestion.setError("Empty");
                        }

                        if(!QuizValidator.isValid(option[0])){
                            binding.multipleQuestionOptionA.setError("Empty");
                        }

                        if(!QuizValidator.isValid(option[1])){
                            binding.multipleQuestionOptionB.setError("Empty");
                        }

                        if(!QuizValidator.isValid(option[2])){
                            binding.multipleQuestionOptionC.setError("Empty");
                        }

                        if (!QuizValidator.isValid(option[3])){
                            binding.multipleQuestionOptionD.setError("Empty");
                        }

                        if(!QuizValidator.isValid(answer)){
                            binding.multipleQuestionAnswer.setError("Empty");
                        }else if(!InsertQuizViewModel.checkOption(option , answer)){
                            binding.multipleQuestionAnswer.setError("Didn't match any option");
                        }

                        Toast.makeText(getContext(), "Invalid Multiple Choice Question", Toast.LENGTH_LONG).show();
                    }

                });
            } else {
                //make long answer question Visible
                setLongAnswerQuestionVisible();

                //make multiple question inVisible
                setMultipleQuestionInVisible();

                binding.nextQuestion.setOnClickListener(v -> getLongAnswerQuestionText());
            }
        });


        binding.endOfQuestionInsert.setOnClickListener(v -> {
            InsertQuizViewModel.setMainMenuFloatingButton();
            InsertQuizViewModel.setRecyclerView();
            InsertQuizViewModel.setMainMenuNoQuizBlur();
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            fragmentManager.popBackStack();
            binding.getRoot().setVisibility(View.GONE);
        });
    }

    private void setSameQuizNameError(){
        binding.quizname.setError("Quiz name already exist");
    }




    private void checkEmpty(String category , String quizName){
        if(!QuizValidator.isValid(category)){
            binding.category.setError("Empty");
        }

        if(!QuizValidator.isValid(quizName)){
            binding.quizname.setError("Empty");
        }
    }
    private void getLongAnswerQuestionText() {
        String question = Objects.requireNonNull(binding.LongAnswerQuestion.getText()).toString();
        String answer = Objects.requireNonNull(binding.LongAnswerAnswer.getText()).toString();
        String category = Objects.requireNonNull(Objects.requireNonNull(binding.category.getText()).toString());
        String quizName = Objects.requireNonNull(Objects.requireNonNull(binding.quizname.getText()).toString());
        InsertQuizViewModel.setQuizName(quizName);

        boolean sameQuizName = InsertQuizViewModel.hasSameQuizName(quizHandler.getQuizList(userId) , quizName);

        //insert to the database
        if (QuizValidator.isLAQValid(question, answer, category) & !sameQuizName) {
            InsertQuizViewModel.insertLongAnswerQuestion(quizHandler, question, answer, category, userId);

            //update achievement progress
            achievementHandler.updateProgress("QUIZ_CREATE");

            //clear the text bar
            binding.quizname.getText().clear();
            binding.LongAnswerAnswer.getText().clear();
            binding.LongAnswerQuestion.getText().clear();
        }
        else {
            checkEmpty(category , quizName);
            if(sameQuizName){
                setSameQuizNameError();
            }

            if(!QuizValidator.isValid(question)){
                binding.LongAnswerQuestion.setError("Empty");
            }

            if(!QuizValidator.isValid(answer)){
                binding.LongAnswerAnswer.setError("Empty");
            }
            Toast.makeText(getContext(), "Invalid Long Answer Question", Toast.LENGTH_LONG).show();
        }

    }

    private void setLongAnswerQuestionInVisible() {
        binding.LongAnswerQuestionHelper.setVisibility(View.GONE);
        binding.LongAnswerQuestion.setVisibility(View.GONE);
        binding.LongAnswerAnswerHelper.setVisibility(View.GONE);
        binding.LongAnswerAnswer.setVisibility(View.GONE);
    }

    private void setLongAnswerQuestionVisible() {
        binding.LongAnswerQuestionHelper.setVisibility(View.VISIBLE);
        binding.LongAnswerQuestion.setVisibility(View.VISIBLE);
        binding.LongAnswerAnswerHelper.setVisibility(View.VISIBLE);
        binding.LongAnswerAnswer.setVisibility(View.VISIBLE);
    }

    private void setMultipleQuestionVisible() {
        binding.multipleQuestionQuestionHelper.setVisibility(View.VISIBLE);
        binding.multipleQuestionQuestion.setVisibility(View.VISIBLE);
        binding.multipleQuestionOptionAHelper.setVisibility(View.VISIBLE);
        binding.multipleQuestionOptionA.setVisibility(View.VISIBLE);
        binding.multipleQuestionOptionBHelper.setVisibility(View.VISIBLE);
        binding.multipleQuestionOptionB.setVisibility(View.VISIBLE);
        binding.multipleQuestionOptionCHelper.setVisibility(View.VISIBLE);
        binding.multipleQuestionOptionC.setVisibility(View.VISIBLE);
        binding.multipleQuestionOptionDHelper.setVisibility(View.VISIBLE);
        binding.multipleQuestionOptionD.setVisibility(View.VISIBLE);
        binding.multipleQuestionAnswerHelper.setVisibility(View.VISIBLE);
        binding.multipleQuestionAnswer.setVisibility(View.VISIBLE);
    }


    private void setMultipleQuestionInVisible() {
        //make multiple question inVisible
        binding.multipleQuestionQuestionHelper.setVisibility(View.GONE);
        binding.multipleQuestionQuestion.setVisibility(View.GONE);
        binding.multipleQuestionOptionAHelper.setVisibility(View.GONE);
        binding.multipleQuestionOptionA.setVisibility(View.GONE);
        binding.multipleQuestionOptionBHelper.setVisibility(View.GONE);
        binding.multipleQuestionOptionB.setVisibility(View.GONE);
        binding.multipleQuestionOptionCHelper.setVisibility(View.GONE);
        binding.multipleQuestionOptionC.setVisibility(View.GONE);
        binding.multipleQuestionOptionDHelper.setVisibility(View.GONE);
        binding.multipleQuestionOptionD.setVisibility(View.GONE);
        binding.multipleQuestionAnswerHelper.setVisibility(View.GONE);
        binding.multipleQuestionAnswer.setVisibility(View.GONE);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        InsertQuizViewModel = new ViewModelProvider(this).get(InsertQuizViewModel.class);
    }

}