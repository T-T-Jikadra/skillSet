package com.example.back_benchers._MustHave;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.back_benchers.Admin_Dir.Admin_Index;
import com.example.back_benchers.R;
import com.example.back_benchers.Student_Dir.Student_Index;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class _Splash extends AppCompatActivity {
    FirebaseAuth fAuth_splash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        fAuth_splash = FirebaseAuth.getInstance();
        new Handler().postDelayed(() -> {

            //if getcurrentUser is not null Then
            if (fAuth_splash.getCurrentUser() != null) {
                //get the CurrentUser into String
                String currentuserId = fAuth_splash.getCurrentUser().getEmail();
                //If it Starts With admin
                if (Objects.requireNonNull(currentuserId).startsWith("admin")) {
                    startActivity(new Intent(_Splash.this, Admin_Index.class));
                    finish();
                } else {
                    //Login to Student ..
                    startActivity(new Intent(_Splash.this, Student_Index.class));
                    Log.d("User", "User Email Id : " + fAuth_splash.getCurrentUser().getEmail());
                    finish();
                }
                //Go to the User Selection Page ..
            } else {
                startActivity(new Intent(_Splash.this, select_TypeOf_User.class));
                finish();
            }
        }, 4200);
    }
}