package com.example.back_benchers.Admin_Dir;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.back_benchers.R;

public class Admin_About_App extends AppCompatActivity {
    ImageButton backBtn_AdminAboutApp;
    TextView AppBarTitle_AAApp;

    TextView txt3;
    TextView txt4;

    String str3 = "FACULTY / ADMIN MODULE ...";
    String str4 = "  ->  Welcome Admin. ..\n" +
            "\n" +
            "Admin, you have the following rights in this \n\tapplication :\n" +
            "\n" +
            "1. You have the access to see every student's \n\t\t" +
            "detailed details from the List of Students tab, \n\t\t" +
            "you can update the details from there and can \n\t\t" +
            "delete the Student from Database.\n" +
            "\n" +
            "2. Attendance module is static for now, we are \n\t\t" +
            "working on it , you will get full access to the \n\t\t" +
            "updated module soon.\n" +
            "\n" +
            "3. You can provide the institute notices for the \n\t\t" +
            "students.\n" +
            "\n" +
            "4. Add assignments, study materials on behalf \n\t\t" +
            "of faculty.\n" +
            "\n" +
            "5. Upload timetable to the students of different \n\t\t" +
            "departments.\n" +
            "\n" +
            "6. You have the rights for exams scheduling.\n" +
            "\n" +
            "7. You can view feedbacks submitted by \n\t\t" +
            "the students, and you have to work on it \n\t\t" +
            "according to the geedback.";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_about_app);

        backBtn_AdminAboutApp = findViewById(R.id.backBtn_1);
        backBtn_AdminAboutApp.setOnClickListener(view -> onBackPressed());

        AppBarTitle_AAApp = findViewById(R.id.txtId_appBar);
        AppBarTitle_AAApp.setText("About App ..");

        txt3 = findViewById(R.id.aboutapptxt3);
        txt4 = findViewById(R.id.aboutapptxt4);

        txt3.setText(str3);
        txt4.setText(str4);

    }
}