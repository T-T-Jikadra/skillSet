package com.example.back_benchers.Student_Dir;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.back_benchers.R;

public class Student_About_App extends AppCompatActivity {
    ImageButton backBtn_StudAboutApp;
    TextView AppBarTitle_SAApp;

    TextView txt1;
    TextView txt2;

    String str1 = "STUDENT MODULE ... ";
    String str2 = "  ->  Welcome Students ...\n" +
            "\n" +
            "This application is developed in order to \n\tcommunicate with students.\n" +
            "\n" +
            "Students you have following features : \n" +
            "\n" +
            "1. In your profile tab you can view your personal \n\t\tdetails " +
            "& add your educational details, it can \n\t\tbe updated only once.\n" +
            "\n" +
            "2. Attendance module is static for now, we are \n\t\tworking on it, " +
            "you will get full access to the \n\t\tupdated module soon.\n" +
            "\n" +
            "3. Institute will provide notices to you & you can \n\t\tview and " +
            "download it in your notices page.\n" +
            "\n" +
            "4. Your faculty will add study materials, you can \n\t\talso view " +
            "and download it from Study \n\t\tMaterials page .\n" +
            "\n" +
            "5. Admin will provide you the time table for your \n\t\tlecture " +
            "schedules.\n" +
            "\n" +
            "6. you can also see your upcoming scheduled \n\t\texams.\n" +
            "\n" +
            "7. There is a fees tab in it, you can pay \n\t\tfees online from there.\n" +
            "\n" +
            "8. You are able to submit your feedback \n\t\tor suggestion regarding our institute or the \n\t\tfaculty.\n" +
            "\n" +
            "9. There is a feature for shareing our application \n\t\twith your classmates.";
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_about_app);

        backBtn_StudAboutApp = findViewById(R.id.backBtn_1);
        backBtn_StudAboutApp.setOnClickListener(view -> onBackPressed());

        AppBarTitle_SAApp = findViewById(R.id.txtId_appBar);
        AppBarTitle_SAApp.setText("About App..");

        txt1 = findViewById(R.id.aboutapptxt1);
        txt2 = findViewById(R.id.aboutapptxt2);

        txt1.setText(str1);
        txt2.setText(str2);
    }
}