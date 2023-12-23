package com.example.back_benchers.Faltu;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.back_benchers.R;

public class Notification_click extends AppCompatActivity {

    private boolean isbackPressedOnce = false;
    ImageButton backBtn_noty;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_click);

        backBtn_noty = findViewById(R.id.backBtn_1);

        backBtn_noty.setOnClickListener(view -> onBackPressed());
    }

    @Override
    public void onBackPressed() {

        if(isbackPressedOnce){
            super.onBackPressed();
            return;
        }
        Toast.makeText(this, "Once Again", Toast.LENGTH_SHORT).show();
        isbackPressedOnce=true;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isbackPressedOnce = false;
            }
        },1500);
    }
}
