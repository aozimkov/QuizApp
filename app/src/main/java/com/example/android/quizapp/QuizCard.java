package com.example.android.quizapp;

/**
 * Created by def on 21.01.18.
 */

public class QuizCard
{

    /**
     * card id
     * card type: radio, check, input
     * card header
     * card description image
     *
     * Answers:
     * rightAnswer - for radio and input
     * rightAnswers[] - for check
     *
     * editable - for input card type
     * answerOne - answerFour - Answers for radio and check card types
     */

    int id;
    String type;
    String question;
    int questionImage;
    String rightAnswer;
    String rightAnswerOne;
    String rightAnswerTwo;
    String editable;
    String answerOne;
    String answerTwo;
    String answerThree;

    /**
     * Constructor for Radio
     * id, type, question, questionImage, rightAnswer, answers 1-3
     */
    public QuizCard(int id, String type, String question, int questionImage, String rightAnswer, String answerOne, String answerTwo, String answerThree) {
        this.id = id;
        this.type = type;
        this.question = question;
        this.questionImage = questionImage;
        this.rightAnswer = rightAnswer;
        this.answerOne = answerOne;
        this.answerTwo = answerTwo;
        this.answerThree = answerThree;
    }

    /**
     * Constructor for Check
     * id, type, question, questionImage, rightAnswerOne, rightAnswerTwo, answers1-3
     */

    public QuizCard(int id, String type, String question, int questionImage, String rightAnswerOne, String rightAnswerTwo, String answerOne, String answerTwo, String answerThree) {
        this.id = id;
        this.type = type;
        this.question = question;
        this.questionImage = questionImage;
        this.rightAnswerOne = rightAnswerOne;
        this.rightAnswerTwo = rightAnswerTwo;
        this.answerOne = answerOne;
        this.answerTwo = answerTwo;
        this.answerThree = answerThree;
    }

    /**
     * Constructor for Input
     */

    public QuizCard(int id, String type, String question, int questionImage, String rightAnswer, String editable) {
        this.id = id;
        this.type = type;
        this.question = question;
        this.questionImage = questionImage;
        this.rightAnswer = rightAnswer;
        this.editable = editable;
    }

    /**
     * Getters
     */

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getQuestion() {
        return question;
    }

    public int getQuestionImage() {
        return questionImage;
    }

    public String getRightAnswer() {
        return rightAnswer;
    }

    public String getRightAnswerOne() {
        return rightAnswerOne;
    }
    public String getRightAnswerTwo() {
        return rightAnswerTwo;
    }

    public String getEditable() {
        return editable;
    }

    public String getAnswerOne() {
        return answerOne;
    }

    public String getAnswerTwo() {
        return answerTwo;
    }

    public String getAnswerThree() {
        return answerThree;
    }

    public String[] getAnswers() {
        String[] answers = {answerOne, answerTwo, answerThree};
        return answers;
    }

    public String[] getRightAnswers() {
        String[] rightAnswers = {rightAnswerOne, rightAnswerTwo};
        return rightAnswers;
    }

    public void setQuestionImage(int questionImage) {
        this.questionImage = questionImage;
    }
}
