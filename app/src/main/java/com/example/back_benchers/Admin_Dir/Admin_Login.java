package com.example.back_benchers.Admin_Dir;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.back_benchers.R;
import com.example.back_benchers.Student_Dir.Student_Login;
import com.example.back_benchers._MustHave.select_TypeOf_User;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class Admin_Login extends AppCompatActivity {
    TextInputEditText edinput_id_AdminLogin;
    TextInputEditText edinput_pwd_AdminLogin;
    Button btn_login_admin;
    Button btn_byStud_AdminLogin;
    FirebaseAuth fAuth_adLogin;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        // Initialize UI elements
        edinput_id_AdminLogin = findViewById(R.id.txt_admin_id);
        edinput_pwd_AdminLogin = findViewById(R.id.txt_admin_pwd);
        btn_login_admin = findViewById(R.id.btn_adminLogin);
        btn_byStud_AdminLogin = findViewById(R.id.byStud);

        fAuth_adLogin = FirebaseAuth.getInstance();

        // Set click listener for admin login button
        btn_login_admin.setOnClickListener(view -> {
            // Check if device is not connected to the internet
            if (!isConnected_AL(Admin_Login.this)) {
                // Show a dialog prompting user to connect to the internet
                showCustomDialog_AL();
                return;
            }
            adLogin();
        });

        // Set click listener for "Login as Student" button
        btn_byStud_AdminLogin.setOnClickListener(view -> {
            // Navigate to the student login page
            startActivity(new Intent(getApplicationContext(), Student_Login.class));
            finish();
        });
    }

    private void adLogin() {
        boolean isAddminValid = validate_AdminData();
        if (isAddminValid) {
            //Only Once  ..
            //AdminRegUserWithFirestore();
            //For Login Functionality ..
            AdminLoginUserWithFirestore();
        }
    }

    private void AdminRegUserWithFirestore() {
        final String adEmail = Objects.requireNonNull(edinput_id_AdminLogin.getText()).toString().trim();
        final String Adpass = Objects.requireNonNull(edinput_pwd_AdminLogin.getText()).toString().trim();

        fAuth_adLogin.createUserWithEmailAndPassword(adEmail, Adpass)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(Admin_Login.this,
                                        "Admin User Created Succcessfully ..",
                                        Toast.LENGTH_SHORT)
                                .show();
                        startActivity(new Intent(Admin_Login.this, Admin_Index.class));
                        //Send The User Data If The Record Is Unique Else Go To Exception block..
                        //sendTocloud();
                        finish();
                    } else {
                        try {
                            Toast.makeText(Admin_Login.this,
                                            "Error : " + Objects.requireNonNull(task.getException()).getMessage(),
                                            Toast.LENGTH_SHORT)
                                    .show();
                            //progressBar_reg.setVisibility(View.INVISIBLE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void AdminLoginUserWithFirestore() {
        String signEmail = Objects.requireNonNull(edinput_id_AdminLogin.getText()).toString().trim();
        String signPass = Objects.requireNonNull(edinput_pwd_AdminLogin.getText()).toString().trim();

        fAuth_adLogin.signInWithEmailAndPassword(signEmail, signPass)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(),
                                        "Admin Login Succcess ..",
                                        Toast.LENGTH_SHORT)
                                .show();

                        Intent intentLogin = new Intent(Admin_Login.this, Admin_Index.class);
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

    private boolean validate_AdminData() {
        if (!Objects.requireNonNull(edinput_id_AdminLogin.getText()).toString().trim().equals("admin@gmail.com")) {
            //progressBar_reg.setVisibility(View.VISIBLE);
            edinput_id_AdminLogin.setError("Enter Proper Emiail Address..");
            edinput_id_AdminLogin.requestFocus();
            //progressBar_reg.setVisibility(View.INVISIBLE);
            return false;
        }
        if (!Objects.requireNonNull(edinput_pwd_AdminLogin.getText()).toString().trim()
                //secretr PIN for admin
                .equals("JayShreeRam")) {
            edinput_pwd_AdminLogin.setError("Invalid Passowrd..");
            //progressBar_reg.setVisibility(View.INVISIBLE);
            edinput_pwd_AdminLogin.requestFocus();
            return false;
        }
        return true;
    }


    // Check if the device is connected to the internet
    private boolean isConnected_AL(Admin_Login login_admin) {
        ConnectivityManager connectivityManager = (ConnectivityManager) login_admin.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiCOnn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        return (wifiCOnn != null && wifiCOnn.isConnected()) || (mobileConn != null && mobileConn.isConnected());
    }

    // Show a custom dialog to prompt the user to connect to the internet
    private void showCustomDialog_AL() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Admin_Login.this);
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
}
