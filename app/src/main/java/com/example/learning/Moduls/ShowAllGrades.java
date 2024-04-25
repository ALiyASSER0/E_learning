package com.example.learning.Moduls;

public class ShowAllGrades {

private String totalGrade;
private String myGrade;



    public ShowAllGrades(){}
    public ShowAllGrades(String totalGrade, String myGrade) {
        this.totalGrade = totalGrade;
        this.myGrade = myGrade;
    }

    public String getTotalGrade() {
        return totalGrade;
    }

    public void setTotalGrade(String totalGrade) {
        this.totalGrade = totalGrade;
    }

    public String getMyGrade() {
        return myGrade;
    }

    public void setMyGrade(String myGrade) {
        this.myGrade = myGrade;
    }
}
