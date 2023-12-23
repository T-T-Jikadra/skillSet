package com.example.back_benchers.Adpters;

import com.google.firebase.Timestamp;

public class notice_Item {

    // Fields to store notice information
    String Notice_Title;
    String Notice_Content;
    Timestamp Timestamp;

    // Default constructor required for Firebase
    public notice_Item() {
    }

    // Getter and setter methods for Notice Title
    public String getNotice_Title() {
        return Notice_Title;
    }

    public void setNotice_Title(String notice_Title) {
        Notice_Title = notice_Title;
    }

    // Getter and setter methods for Notice Content
    public String getNotice_Content() {
        return Notice_Content;
    }

    public void setNotice_Content(String notice_Content) {
        Notice_Content = notice_Content;
    }

    // Getter and setter methods for Timestamp
    public com.google.firebase.Timestamp getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(com.google.firebase.Timestamp timestamp) {
        Timestamp = timestamp;
    }
}
