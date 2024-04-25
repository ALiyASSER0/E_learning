package com.example.learning.Moduls;

import java.io.Serializable;

public class CreateCourse implements Serializable {
    private String courseName;
    private String gradeQuiz;
    private String projectGrade;
    private String attendanceGrade;
    private String courseId;
    private String doctorId;
    private String gradeAvailable;
    public CreateCourse() {
    }

    public CreateCourse(String courseName, String gradeQuiz, String projectGrade, String attendanceGrade, String courseId, String doctorId, String gradeAvailable) {
        this.courseName = courseName;
        this.gradeQuiz = gradeQuiz;
        this.projectGrade = projectGrade;
        this.attendanceGrade = attendanceGrade;
        this.courseId = courseId;
        this.doctorId = doctorId;
        this.gradeAvailable = gradeAvailable;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getGradeQuiz() {
        return gradeQuiz;
    }

    public void setGradeQuiz(String gradeQuiz) {
        this.gradeQuiz = gradeQuiz;
    }

    public String getProjectGrade() {
        return projectGrade;
    }

    public void setProjectGrade(String projectGrade) {
        this.projectGrade = projectGrade;
    }

    public String getAttendanceGrade() {
        return attendanceGrade;
    }

    public void setAttendanceGrade(String attendanceGrade) {
        this.attendanceGrade = attendanceGrade;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getGradeAvailable() {
        return gradeAvailable;
    }

    public void setGradeAvailable(String gradeAvailable) {
        this.gradeAvailable = gradeAvailable;
    }
}
