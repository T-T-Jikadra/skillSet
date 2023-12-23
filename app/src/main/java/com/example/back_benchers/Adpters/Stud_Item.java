package com.example.back_benchers.Adpters;

import com.google.firebase.Timestamp;

// Represents a student item with various attributes.
public class Stud_Item {
    // Personal Information
    private String Student_First_Name; // First name of the student
    private String Surname; // Last name or surname of the student
    private String Student_Id; // Unique identifier for the student

    // Contact Information
    private String Mobile_No; // Mobile phone number of the student
    private String Email_Id; // Email address of the student

    // Educational Details
    private String Department; // College or educational institution name
    private String Date_Of_Birth; // Date of birth of the student
    private String Gender; // Gender of the student (e.g., "Male", "Female", etc.)
    private String Course_Name;
    // Timestamp
    private Timestamp User_Created_On; // Timestamp indicating when the user was created
    private String isFees;

    public String getIsFees() {
        return isFees;
    }

    public void setIsFees(String isFees) {
        this.isFees = isFees;
    }

    // Default constructor required for Firestore
    public Stud_Item() {
        // Default constructor required for Firestore
    }

    public String getCourse_Name() {
        return Course_Name;
    }

    public void setCourse_Name(String course_Name) {
        Course_Name = course_Name;
    }

    // Getter and setter for user created timestamp
    public com.google.firebase.Timestamp getUser_Created_On() {
        return User_Created_On;
    }

    public void setUser_Created_On(com.google.firebase.Timestamp user_Created_On) {
        User_Created_On = user_Created_On;
    }

    // Getter and setter for student's first name
    public String getStudent_First_Name() {
        return Student_First_Name;
    }

    public void setStudent_First_Name(String student_First_Name) {
        Student_First_Name = student_First_Name;
    }

    // Getter and setter for surname
    public String getSurname() {
        return Surname;
    }

    public void setSurname(String surname) {
        Surname = surname;
    }

    // Getter and setter for student ID
    public String getStudent_Id() {
        return Student_Id;
    }

    public void setStudent_Id(String student_Id) {
        Student_Id = student_Id;
    }

    // Getter and setter for mobile number
    public String getMobile_No() {
        return Mobile_No;
    }

    public void setMobile_No(String mobile_No) {
        Mobile_No = mobile_No;
    }

    // Getter and setter for college name
    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String department) {
        Department = Department;
    }

    // Getter and setter for email address
    public String getEmail_Id() {
        return Email_Id;
    }

    public void setEmail_Id(String email_Id) {
        Email_Id = email_Id;
    }

    // Getter and setter for gender
    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    // Getter and setter for date of birth
    public String getDate_Of_Birth() {
        return Date_Of_Birth;
    }

    public void setDate_Of_Birth(String date_Of_Birth) {
        Date_Of_Birth = date_Of_Birth;
    }
}