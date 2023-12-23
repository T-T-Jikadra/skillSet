package com.example.back_benchers.Student_Dir;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.back_benchers.Faltu.check_otp;
import com.example.back_benchers.R;
import com.example.back_benchers._MustHave.select_TypeOf_User;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

// Activity for student login
public class Student_Login extends AppCompatActivity {
    TextInputEditText edinput_Studlogin_sid;
    TextInputEditText edinput_Studlogin_mono;
    FirebaseAuth firebaseAuth_StudLogin;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);

        // Initialize Firebase Authentication instance
        firebaseAuth_StudLogin = FirebaseAuth.getInstance();

        // Initialize UI elements
        edinput_Studlogin_sid = findViewById(R.id.txt_login_emiail);
        edinput_Studlogin_mono = findViewById(R.id.txt_login_mono);
        Button proceedToLogin = findViewById(R.id.btn_studlogin);
        Button bypassToReg = findViewById(R.id.byToReg);

        // Set up click listeners
        proceedToLogin.setOnClickListener(view -> {
            if (!isConnected_SL(Student_Login.this)) {
                // Show a dialog prompting user to connect to the internet
                showCustomDialog_SL();
                return;
            }
            loginAc();
        });


        bypassToReg.setOnClickListener(view -> {
            Intent intent_bypassToReg = new Intent(Student_Login.this, Student_Reg.class);
            startActivity(intent_bypassToReg);
            finish();
        });
    }

    // Show a custom dialog to prompt the user to connect to the internet
    private void showCustomDialog_SL() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Student_Login.this);
        builder.setMessage("Please connect to the Internet for further process ..")
                .setCancelable(false)
                .setPositiveButton("Connect", (dialogInterface, i) ->
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS)))
                .setNegativeButton("Cancel", (dialogInterface, i) -> {
                    // Navigate to the user selection screen
                    startActivity(new Intent(getApplicationContext(), select_TypeOf_User.class));
                    finish();
                });

        // Create and show the dialog
        AlertDialog alert = builder.create();
        alert.show();
    }

    private boolean isConnected_SL(Student_Login student_login) {
        ConnectivityManager connectivityManager = (ConnectivityManager) student_login.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiCOnn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        return (wifiCOnn != null && wifiCOnn.isConnected()) || (mobileConn != null && mobileConn.isConnected());
    }

    // UDF to handle login process
    private void loginAc() {
        boolean isLoginDataValid = validate_LoginData();
        if (!isLoginDataValid) {
            return;
        } else {
            checkLogin_DatabaseInfo();
        }
    }

    // Validate login data
    private boolean validate_LoginData() {
        if (Objects.requireNonNull(edinput_Studlogin_sid.getText()).toString().trim().length() < 3) {
            edinput_Studlogin_sid.setError("Too Short Email Address ..");
            edinput_Studlogin_sid.requestFocus();
            return false;
        }
        if (Objects.requireNonNull(edinput_Studlogin_mono.getText()).toString().length() < 10) {
            edinput_Studlogin_mono.setError("Enter 10 Digit Mobile Number ..");
            edinput_Studlogin_mono.requestFocus();
            return false;
        }
        return true;
    }

    // Check login credentials in Firebase
    private void checkLogin_DatabaseInfo() {
        String signEmail = Objects.requireNonNull(edinput_Studlogin_sid.getText()).toString().trim();
        String signPass = Objects.requireNonNull(edinput_Studlogin_mono.getText()).toString().trim();

        firebaseAuth_StudLogin.signInWithEmailAndPassword(signEmail, signPass)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(),
                                        "Login Succcess ..",
                                        Toast.LENGTH_SHORT)
                                .show();

                        Intent intentLogin = new Intent(Student_Login.this, Student_Index.class);
                        startActivity(intentLogin);
                        finish();
                    } else {
                        try {
                            Toast.makeText(getApplicationContext(),
                                            "Error : " + Objects.requireNonNull(task.getException()).getMessage(),
                                            Toast.LENGTH_SHORT)
                                    .show();
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(),
                                            "Error : " + e.getMessage(),
                                            Toast.LENGTH_SHORT)
                                    .show();
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void checkLogin_DatabaseInfo1() {
        //Realtime
        final String login_CheckSid = Objects.requireNonNull(edinput_Studlogin_sid.getText()).toString().trim();
        final String login_CheckMono = Objects.requireNonNull(edinput_Studlogin_mono.getText()).toString().trim();

        DatabaseReference loginDatabaseReference = FirebaseDatabase.getInstance().getReference("Users");

        Query login_CheckUser = loginDatabaseReference.orderByChild("Sid").equalTo(login_CheckSid);
        login_CheckUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    edinput_Studlogin_sid.setError(null);
                    String mobileFromDB = (String) snapshot.child(login_CheckSid).child("Mobile No").getValue();
                    if (Objects.equals(mobileFromDB, login_CheckMono)) {
                        edinput_Studlogin_sid.setError(null);
                        startActivity(new Intent(Student_Login.this, check_otp.class));
                    } else {
                        edinput_Studlogin_mono.setError("Mobile Number Doesn't Matches ..");
                        edinput_Studlogin_mono.requestFocus();
                    }
                } else {
                    edinput_Studlogin_sid.setError("First Name Doesn't Matches ..");
                    edinput_Studlogin_sid.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}