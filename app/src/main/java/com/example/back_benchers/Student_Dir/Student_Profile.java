package com.example.back_benchers.Student_Dir;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.back_benchers.R;

public class Student_Profile extends AppCompatActivity {
    ImageButton backBtn_StudProfile;
    TextView AppBarTitle_SP;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);

        backBtn_StudProfile = findViewById(R.id.backBtn_1);
        backBtn_StudProfile.setOnClickListener(view -> onBackPressed());

        AppBarTitle_SP = findViewById(R.id.txtId_appBar);
        AppBarTitle_SP.setText("Profile ..");

    }
}