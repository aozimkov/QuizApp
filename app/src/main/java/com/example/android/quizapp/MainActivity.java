package com.example.android.quizapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    static final String STATE_STEPNO = "stepNo";
    static final String STATE_RIGHTANSWERS = "rightAnswersCounter";
    static final String STATE_BUTTONTEXT = "buttonText";

    String buttonText;

    TextView question;
    LinearLayout quizCard; // LinnearLayout View for Quiz cards
    LinearLayout quizAnswers;
    LinearLayout.LayoutParams answersLayoutParams;
    Button submitButton;

    Toast toastRight;
    Toast toastWrong;
    Toast toastEmpty;

    int stepNo = -1;
    boolean currentAnswer;
    int rightAnswersCounter;
    boolean radioButtonsChecker;


    /**
     * Cards:
     * <p>
     * 1 | radio | Чему равно IX ?              | 0 | 9 | 4 | 11 | 9 |
     * 2 | check | Чему НЕ равно XXXIII ?       | 0 | 153, 103 | 33 | 153 | 303 |
     * 3 | radio | Чему равно CXLIV ?           | 0 | 144 | 134 | 144 | 164 |
     * 4 | radio | Чему равно DCCLXXXII ?       | 0 | 782 | 432 | 732 | 782 |
     * 5 | check | Чему НЕ равно CMXCIX ?       | 0 | 199, 1199 | 199 | 1199 | 999 |
     * 6 | radio | Чему равно MCDXL ?           | 0 | 1440 | 1660 | 1550 | 1440 |
     * 7 | check | Чему НЕ равно MCMLXXXVI ?    | 0 | 2586, 1896 | 2586 | 1896 | 1986 |
     * 8 | input | Чему равно MMMMCDLXIX ?      | 0 | 4469 |
     */



    QuizCard quizCard1 = new QuizCard(1, "radio", "Чему равно IX ?", 0, "9", "4", "11", "9");
    QuizCard quizCard2 = new QuizCard(2, "check", "Чему НЕ равно XXXIII ?", 0, "153", "303", "33", "153", "303");
    QuizCard quizCard3 = new QuizCard(3, "radio", "Чему равно CXLIV ?", 0, "144", "134", "144", "164");
    QuizCard quizCard4 = new QuizCard(4, "radio", "Чему равно DCCLXXXII ?", 0, "782", "432", "732", "782");
    QuizCard quizCard5 = new QuizCard(5, "check", "Чему НЕ равно CMXCIX ?", 0, "199", "1199", "199", "1199", "999");
    QuizCard quizCard6 = new QuizCard(6, "radio", "Чему равно MCDXL ?", 0, "1440", "1660", "1550", "1440");
    QuizCard quizCard7 = new QuizCard(7, "check", "Чему НЕ равно MCMLXXXVI ?", 0, "2586", "1896", "2586", "1896", "1986");
    QuizCard quizCard8 = new QuizCard(8, "input", "Чему равно MMMMCDLXIX ?", 0, "4469", "0");

    ArrayList<QuizCard> q = new ArrayList<>();


    private View.OnClickListener mRadioAnswerListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            checkRadioAnswer(view);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            stepNo = savedInstanceState.getInt(STATE_STEPNO);
            rightAnswersCounter = savedInstanceState.getInt(STATE_RIGHTANSWERS);
            buttonText = savedInstanceState.getString(STATE_BUTTONTEXT);
        }

        submitButton = (Button) findViewById(R.id.submit_button);

        quizCard = findViewById(R.id.quiz_card);
        quizAnswers = findViewById(R.id.quiz_answers);
        question = findViewById(R.id.quiz_header);

        answersLayoutParams = new LinearLayout.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.MATCH_PARENT);
        answersLayoutParams.gravity = Gravity.CENTER;

        quizCard8.setQuestionImage(R.drawable.high_level);

        q.add(quizCard1);
        q.add(quizCard2);
        q.add(quizCard3);
        q.add(quizCard4);
        q.add(quizCard5);
        q.add(quizCard6);
        q.add(quizCard7);
        q.add(quizCard8);

        toastRight = Toast.makeText(getApplicationContext(), "Right!", Toast.LENGTH_SHORT);
        toastWrong = Toast.makeText(getApplicationContext(), "Wrong!", Toast.LENGTH_SHORT);
        toastEmpty = Toast.makeText(getApplicationContext(), "Please input your choice!", Toast.LENGTH_SHORT);

        if (stepNo == -1) {
            firstPage();
        } else {
            nextQuestion();
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putInt(STATE_STEPNO, stepNo);
        outState.putInt(STATE_RIGHTANSWERS, rightAnswersCounter);
        outState.putString(STATE_BUTTONTEXT, buttonText);

        super.onSaveInstanceState(outState);

    }


    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to get out?")
                .setCancelable(false)
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        finish();
                    }
                }).create().show();
    }

    private void nextQuestion() {

        question.setGravity(Gravity.CENTER);

        if (stepNo < q.size()) {

            question.setText(q.get(stepNo).getQuestion());
            submitButton.setText(R.string.next);

            if (q.get(stepNo).getQuestionImage() != 0) {
                ImageView questionImage = new ImageView(this);
                questionImage.setImageResource(q.get(stepNo).getQuestionImage());
                quizAnswers.addView(questionImage);
            }

            createAnswers(q.get(stepNo).getType());

        } else {

            question.setText("Quiz is Done. Thanks!");

            TextView quizResults = new TextView(this);
            quizResults.setText("Your have " + rightAnswersCounter + "/" + q.size() +
                    " right answers. Cool!");
            quizAnswers.addView(quizResults);

            submitButton.setText(R.string.reset);

        }

    }

    private void createAnswers(String type) {

        int answersLength = q.get(stepNo).getAnswers().length;
        String[] answers = q.get(stepNo).getAnswers();

        switch (type) {

            case "radio":

                RadioButton[] radioButton = new RadioButton[answersLength];
                RadioGroup radioGroup = new RadioGroup(this);

                RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.MATCH_PARENT);
                params.gravity = Gravity.CENTER;

                for (int i = 0; answersLength > i; i++) {
                    radioButton[i] = new RadioButton(this);
                    radioButton[i].setText(answers[i]);
                    radioButton[i].setOnClickListener(mRadioAnswerListener);
                    radioGroup.addView(radioButton[i]);
                }

                radioButton[0].setId(R.id.radio_button_1);
                radioButton[1].setId(R.id.radio_button_2);
                radioButton[2].setId(R.id.radio_button_3);
                radioGroup.setLayoutParams(params);
                radioGroup.setId(R.id.radio_group);
                quizAnswers.addView(radioGroup);
                radioButtonsChecker = false;
                break;

            case "check":

                CheckBox[] checkBox = new CheckBox[answersLength];

                for (int i = 0; answersLength > i; i++) {

                    checkBox[i] = new CheckBox(this);
                    checkBox[i].setText(answers[i]);
                    quizAnswers.addView(checkBox[i]);
                }

                quizAnswers.setLayoutParams(answersLayoutParams);

                checkBox[0].setId(R.id.checkbox_1);
                checkBox[1].setId(R.id.checkbox_2);
                checkBox[2].setId(R.id.checkbox_3);

                break;

            case "input":

                EditText answer = new EditText(this);
                answer.setHint("Example: 100");
                answer.setId(R.id.input_field);
                quizAnswers.addView(answer);
                answer.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(answer, InputMethodManager.SHOW_IMPLICIT);

                break;

        }

    }

    private void checkRadioAnswer(View view) {

        radioButtonsChecker = true;
        RadioButton checkedButton = (RadioButton) view;
        String thisAnswer = checkedButton.getText().toString();
        String rightAnswer = q.get(stepNo).getRightAnswer();

        if (thisAnswer.equals(rightAnswer)) {
            currentAnswer = true;
        } else {
            currentAnswer = false;
        }
    }

    public void submitAnswer(View view) {

        if (stepNo == q.size()) { //When quiz is done
            resetQuiz();
            return;

        } else if (stepNo == -1) { //Quiz first page
            quizAnswers.removeAllViewsInLayout();
            stepNo = 0;
            nextQuestion();
            return;
        }

        switch (q.get(stepNo).getType()) {

            case "radio":

                if (!radioButtonsChecker) {
                    nothingChecked();
                    return;
                }

                break;

            case "check":

                String[] rightAnswers = q.get(stepNo).getRightAnswers();
                ArrayList<String> checkedBoxes = new ArrayList<>(3);

                CheckBox checkbox_1 = (CheckBox) findViewById(R.id.checkbox_1);
                CheckBox checkbox_2 = (CheckBox) findViewById(R.id.checkbox_2);
                CheckBox checkbox_3 = (CheckBox) findViewById(R.id.checkbox_3);

                if (checkbox_1.isChecked()) checkedBoxes.add(checkbox_1.getText().toString());
                if (checkbox_2.isChecked()) checkedBoxes.add(checkbox_2.getText().toString());
                if (checkbox_3.isChecked()) checkedBoxes.add(checkbox_3.getText().toString());

                if ( checkedBoxes.size() == 0 ) {
                    nothingChecked();
                    return;
                }

                if (checkedBoxes.size() == rightAnswers.length) {

                    int totalRight = 0;

                    for (int i = 0; i < rightAnswers.length; i++) {
                        for (int j = 0; j < checkedBoxes.size(); j++) {
                            if (rightAnswers[i].equals(checkedBoxes.get(j))) totalRight++;
                        }
                    }

                    if (totalRight == rightAnswers.length) currentAnswer = true;
                }
                break;

            case "input":

                EditText answer = (EditText) findViewById(R.id.input_field);
                String input = answer.getText().toString();

                if (input.length() == 0) {
                    nothingChecked();
                    return;
                }


                if (q.get(stepNo).getRightAnswer().equals(input)) currentAnswer = true;

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(answer.getWindowToken(), 0);

                break;

        }

        if (currentAnswer) {
            rightAnswersCounter++;

        }

        showResult(currentAnswer);

        stepNo++;
        currentAnswer = false;
        quizAnswers.removeAllViewsInLayout();

        nextQuestion();
    }

    private void showResult(boolean answerRight) {

        if (answerRight) {
            toastRight.show();
        } else {
            toastWrong.show();
        }
    }

    private void resetQuiz() {
        stepNo = -1;
        currentAnswer = false;
        rightAnswersCounter = 0;
        quizAnswers.removeAllViewsInLayout();

        firstPage();
    }

    private void firstPage(){
        question.setText("Древнеримский тест по математике");
        question.setGravity(Gravity.CENTER);

        TextView quizResults = new TextView(this);
        quizResults.setText("Только избранные смогут ответить на X из X вопросов. Попробуй и ты!");
        quizAnswers.addView(quizResults);

        quizAnswers.setLayoutParams(answersLayoutParams);

        submitButton.setText(R.string.start);

    }

    private void nothingChecked(){

        toastEmpty.show();

    }
}