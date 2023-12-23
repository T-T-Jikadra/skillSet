package com.example.back_benchers.Adpters;

import com.google.firebase.Timestamp;

public class feedback_Item {

    String Feedback_Title;  // Title of the feedback
    String Feedback_Content;  // Content of the feedback
    Timestamp Timestamp;
    String Feedback_Giver_Sid;
    String Feedback_Giver_Name;
    // Timestamp indicating when the feedback was created

    public String getFeedback_Giver_Sid() {
        return Feedback_Giver_Sid;
    }

    public void setFeedback_Giver_Sid(String feedback_Giver_Sid) {
        Feedback_Giver_Sid = feedback_Giver_Sid;
    }

    public String getFeedback_Giver_Name() {
        return Feedback_Giver_Name;
    }

    public void setFeedback_Giver_Name(String feedback_Giver_Name) {
        Feedback_Giver_Name = feedback_Giver_Name;
    }

    public feedback_Item() {
        // Empty constructor required for Firebase
    }

    // Getter method for the feedback title
    public String getFeedback_Title() {
        return Feedback_Title;
    }

    // Setter method for the feedback title
    public void setFeedback_Title(String feedback_Title) {
        Feedback_Title = feedback_Title;
    }

    // Getter method for the feedback content
    public String getFeedback_Content() {
        return Feedback_Content;
    }

    // Setter method for the feedback content
    public void setFeedback_Content(String feedback_Content) {
        Feedback_Content = feedback_Content;
    }

    // Getter method for the timestamp
    public com.google.firebase.Timestamp getTimestamp() {
        return Timestamp;
    }

    // Setter method for the timestamp
    public void setTimestamp(com.google.firebase.Timestamp timestamp) {
        Timestamp = timestamp;
    }
}
