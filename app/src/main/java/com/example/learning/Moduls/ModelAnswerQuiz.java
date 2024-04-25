package com.example.learning.Moduls;

public class ModelAnswerQuiz {
    private String totalGrades;
    private String quizGrideCorrect;
    private String quizGrideWrong;


    public ModelAnswerQuiz(){}
    public ModelAnswerQuiz(String totalGrades, String quizGrideCorrect, String quizGrideWrong) {
        this.totalGrades = totalGrades;
        this.quizGrideCorrect = quizGrideCorrect;
        this.quizGrideWrong = quizGrideWrong;
    }

    public String getTotalGrades() {
        return totalGrades;
    }

    public void setTotalQestion(String totalQestion) {
        this.totalGrades = totalQestion;
    }

    public String getQuizGrideCorrect() {
        return quizGrideCorrect;
    }

    public void setQuizGrideCorrect(String quizGrideCorrect) {
        this.quizGrideCorrect = quizGrideCorrect;
    }

    public String getQuizGrideWrong() {
        return quizGrideWrong;
    }

    public void setQuizGrideWrong(String quizGrideWrong) {
        this.quizGrideWrong = quizGrideWrong;
    }
}
