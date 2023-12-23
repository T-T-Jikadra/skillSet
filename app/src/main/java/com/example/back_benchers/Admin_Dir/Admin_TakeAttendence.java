package com.example.back_benchers.Admin_Dir;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.back_benchers.R;

public class Admin_TakeAttendence extends AppCompatActivity {
    ImageButton backBtn_TakeAttend;
    TextView AppBarTitle_AAttend;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_take_attendence);

        backBtn_TakeAttend = findViewById(R.id.backBtn_1);
        backBtn_TakeAttend.setOnClickListener(view -> onBackPressed());

        AppBarTitle_AAttend = findViewById(R.id.txtId_appBar);
        AppBarTitle_AAttend.setText("Attendance ..");
    }
}