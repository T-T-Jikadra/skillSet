package com.example.back_benchers.Student_Dir;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.back_benchers.R;
import com.example.back_benchers._MustHave.select_TypeOf_User;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class Student_Reg extends AppCompatActivity {
    private final DatabaseReference regDatabaseReference = FirebaseDatabase.getInstance().getReference();
    //Declaration
    TextInputEditText edinput_reg_fname;
    TextInputEditText edinput_reg_surname;
    TextInputEditText edinput_reg_sid;
    TextInputEditText edinput_reg_mono;
    TextInputEditText edinput_reg_email;
    RadioButton rbMale;
    RadioButton rbFemale;
    TextView txt_dob;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String set_DOBDate;
    FirebaseFirestore fbFStore_reg;
    FirebaseAuth fAuth_reg;
    ProgressBar progressBar_reg;
    String[] DeptNameList = {
            "Department of Computer Science",
            "Department of Medical Science",
            "Department of Management",
            "Department of Commerce",
            "Department of Special Studies"};
    AutoCompleteTextView txt_Spinner_Dept;
    String Spinner_selectedDept = "";
    String[] CourseNameList = {"B.C.A.", "B.B.A.", "B.COM", "M.B.B.S.", "CA"};
    AutoCompleteTextView txt_Spinner_Course;
    String Spinner_selectedCourse = "";

    @SuppressLint({"WrongViewCast", "SetTextI18n", "SimpleDateFormat", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_reg);

        fAuth_reg = FirebaseAuth.getInstance();
        fbFStore_reg = FirebaseFirestore.getInstance();

        edinput_reg_fname = findViewById(R.id.txt_reg_fname);
        edinput_reg_surname = findViewById(R.id.txt_reg_surname);
        edinput_reg_sid = findViewById(R.id.txt_reg_sid);
        edinput_reg_mono = findViewById(R.id.txt_reg_mono);
        edinput_reg_email = findViewById(R.id.txt_reg_email);
        rbMale = findViewById(R.id.rbM);
        rbFemale = findViewById(R.id.rbF);
        txt_dob = findViewById(R.id.show_dob);
        txt_Spinner_Dept = findViewById(R.id.autoComplete_Clg);
        txt_Spinner_Course = findViewById(R.id.autoComplete_Course);

        Button select_dob = findViewById(R.id.btn_reg_dob);
        Button proceedToLogin = findViewById(R.id.btn_reg_proceed);
        Button bypassToLogin = findViewById(R.id.regToLoginBypass);
        progressBar_reg = findViewById(R.id.PBar);

        /*if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(reg_page.this, MainActivity.class));
            finish();
        }*/

        //show Default Current Date in textview
        Calendar calendar = Calendar.getInstance();
        //String i = "Selected Date Is :" + calendar.getTime();
        //Toast.makeText(this, "a" +calendar.getTime(), Toast.LENGTH_SHORT).show();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        txt_dob.setText("  Selected : " + simpleDateFormat.format(calendar.getTime()));

        //Calender for DOB selection
        select_dob.setOnClickListener(view -> {
            MaterialDatePicker<Long> materialDatePicker = MaterialDatePicker.Builder.datePicker().setTitleText("Select Your Date Of Birth :").setSelection(MaterialDatePicker.todayInUtcMilliseconds()).build();

            materialDatePicker.addOnPositiveButtonClickListener(selection -> {
                set_DOBDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date(selection));
                txt_dob.setText(MessageFormat.format("  Selected : {0}", set_DOBDate));
            });
            materialDatePicker.show(getSupportFragmentManager(), "tag");
        });

        //Spinner Of Course ..
        ArrayAdapter<String> adapter_Course;
        adapter_Course = new ArrayAdapter<>(this, R.layout.layout_spinner_item, CourseNameList);
        txt_Spinner_Course.setAdapter(adapter_Course);

        txt_Spinner_Course.setOnItemClickListener((adapterView, view, i, l) -> {
            Object selectedCourseItem = adapterView.getItemAtPosition(i);
            if (selectedCourseItem != null) {
                Spinner_selectedCourse = selectedCourseItem.toString();
            } else {
                // Handle case where selectedItem is null
                Spinner_selectedCourse = "";
            }
        });

        //Spinner Of Clg ..
        ArrayAdapter<String> adapter_Clg;
        adapter_Clg = new ArrayAdapter<>(this, R.layout.layout_spinner_item, DeptNameList);
        txt_Spinner_Dept.setAdapter(adapter_Clg);

        txt_Spinner_Dept.setOnItemClickListener((adapterView, view, i, l) -> {
            Object selectedClgItem = adapterView.getItemAtPosition(i);
            if (selectedClgItem != null) {
                Spinner_selectedDept = selectedClgItem.toString();
            } else {
                // Handle case where selectedItem is null
                Spinner_selectedCourse = "";
            }
        });

        //btn to proceed registration --- // Button proceedToLogin on click
        proceedToLogin.setOnClickListener(view -> {
            progressBar_reg.setVisibility(View.VISIBLE);

            if (!isConnected_SR(Student_Reg.this)) {
                // Show a dialog prompting user to connect to the internet
                showCustomDialog_SR();
                return;
            }

            createAc();
        });

        //click on Login if already have account // guest
        bypassToLogin.setOnClickListener(view -> {
            Intent intent_bypassToLogin = new Intent(Student_Reg.this, Student_Login.class);
            startActivity(intent_bypassToLogin);
            finish();
        });
    }

    private void showCustomDialog_SR() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Student_Reg.this);
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

    private boolean isConnected_SR(Student_Reg student_reg) {
        ConnectivityManager connectivityManager = (ConnectivityManager) student_reg.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiCOnn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        return (wifiCOnn != null && wifiCOnn.isConnected()) || (mobileConn != null && mobileConn.isConnected());
    }

//*******************************************************************************************************************
// ******************************************************************************************************************


    // UDF - out Of OnCreate
    private void createAc() {
        boolean isRegDataValid = validate_RegData();
        if (isRegDataValid) {
            checkReg_DatabaseInfo();
            //sendDateToDatabase();
        }
    }

    //Validate The Input Data Fields
    boolean validate_RegData() {
        if (Objects.requireNonNull(edinput_reg_fname.getText()).toString().trim().length() < 3) {
            progressBar_reg.setVisibility(View.VISIBLE);
            edinput_reg_fname.setError("Too Short Fname ..");
            edinput_reg_fname.requestFocus();
            progressBar_reg.setVisibility(View.INVISIBLE);
            return false;
        }
        if (Objects.requireNonNull(edinput_reg_surname.getText()).toString().trim().length() < 5) {
            edinput_reg_surname.setError("Too Short Surname ..");
            progressBar_reg.setVisibility(View.INVISIBLE);
            edinput_reg_surname.requestFocus();
            return false;
        }
        if (Objects.requireNonNull(edinput_reg_sid.getText()).length() < 7) {
            edinput_reg_sid.setError("Enter 7 Digit SID ..");
            progressBar_reg.setVisibility(View.INVISIBLE);
            edinput_reg_sid.requestFocus();
            return false;
        }
        if (Objects.requireNonNull(edinput_reg_mono.getText()).length() < 10) {
            edinput_reg_mono.setError("Enter 10 Digit Mobile Number ..");
            progressBar_reg.setVisibility(View.INVISIBLE);
            edinput_reg_mono.requestFocus();
            return false;
        }
        if (!Objects.requireNonNull(edinput_reg_email.getText()).toString().trim().matches(emailPattern)) {
            edinput_reg_email.setError("Enter valid Email ..");
            progressBar_reg.setVisibility(View.INVISIBLE);
            edinput_reg_email.requestFocus();
            return false;
        }
        if (Spinner_selectedDept.isEmpty()) {
            // A valid course is selected, perform the desired action
            txt_Spinner_Dept.setError("Select Your Department ..");
            progressBar_reg.setVisibility(View.INVISIBLE);
            txt_Spinner_Dept.requestFocus();
            //Toast.makeText(this, "Select Course .. " + selectedCourse, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (Spinner_selectedCourse.isEmpty()) {
            // A valid course is selected, perform the desired action
            txt_Spinner_Course.setError("Select Your Course ..");
            progressBar_reg.setVisibility(View.INVISIBLE);
            txt_Spinner_Course.requestFocus();
            //Toast.makeText(this, "Select Course .. " + selectedCourse, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    //Check For Duplicate Entry In Realtime DB
    //Check For Duplicate Entry In Firestore
    private void checkReg_DatabaseInfo() {
        final String reg_CheckSid = Objects.requireNonNull(edinput_reg_sid.getText()).toString().trim();

        DocumentReference docRef = fbFStore_reg.collection("Users").document(reg_CheckSid);
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    Toast.makeText(Student_Reg.this,
                                    "SID Already Registered , Enter Unique One ..",
                                    Toast.LENGTH_LONG)
                            .show();
                    progressBar_reg.setVisibility(View.INVISIBLE);
                } else {
                    // SID is not registered, proceed with registration
                    createUserWithFirestore();
                }
            } else {
                Toast.makeText(Student_Reg.this,
                                "Error checking SID: " + Objects.requireNonNull(task.getException()).getMessage(),
                                Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    //Firestore DB
    private void createUserWithFirestore() {
        final String sendemail1 = Objects.requireNonNull(edinput_reg_email.getText()).toString().trim();
        final String sendSurame1 = Objects.requireNonNull(edinput_reg_mono.getText()).toString().trim();

        fAuth_reg.createUserWithEmailAndPassword(sendemail1, sendSurame1)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(Student_Reg.this,
                                        "User Created Succcessfully ..",
                                        Toast.LENGTH_SHORT)
                                .show();
                        startActivity(new Intent(Student_Reg.this, Student_Login.class));
                        //Send The User Data If The Record Is Unique Else Go To Exception block..
                        sendTocloud();
                        finish();
                    } else {
                        try {
                            Toast.makeText(Student_Reg.this,
                                            "Error : " + Objects.requireNonNull(task.getException()).getMessage(),
                                            Toast.LENGTH_SHORT)
                                    .show();
                            progressBar_reg.setVisibility(View.INVISIBLE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    //Store Data Into The Firestore  Database..
    public void sendTocloud() {
        //Send data To Firestore
        String sendFname = Objects.requireNonNull(edinput_reg_fname.getText()).toString().trim();
        String sendSurame = Objects.requireNonNull(edinput_reg_surname.getText()).toString().trim();
        String sendSid = Objects.requireNonNull(edinput_reg_sid.getText()).toString().trim();
        String sendMono = Objects.requireNonNull(edinput_reg_mono.getText()).toString().trim();
        String sendEmail = Objects.requireNonNull(edinput_reg_email.getText()).toString().trim();
        String sendGender = "";
        String sendDOB = set_DOBDate;
        String sendDept = txt_Spinner_Dept.getText().toString().trim();
        String sendCourse = txt_Spinner_Course.getText().toString();
        Timestamp userCreateTime = Timestamp.now();

        //Gender Selection..
        if (rbMale.isChecked()) {
            sendGender = "Male";
        } else if (rbFemale.isChecked()) {
            sendGender = "Female";
        }

        DocumentReference documentReference;
        documentReference = FirebaseFirestore.getInstance().collection("Users").document(sendEmail);
        Map<String, Object> User = new HashMap<>();
        User.put("Student_First_Name", sendFname);
        User.put("Surname", sendSurame);
        User.put("Student_Id", sendSid);
        User.put("Mobile_No", sendMono);
        User.put("Email_Id", sendEmail);
        User.put("Date_Of_Birth", sendDOB);
        User.put("Department", sendDept);
        User.put("Gender", sendGender);
        User.put("Course_Name", sendCourse);
        User.put("User_Created_On", userCreateTime);
        User.put("isFees", "0 /-");

        documentReference.set(User).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(Student_Reg.this, "Data Added", Toast.LENGTH_SHORT).show();
                progressBar_reg.setVisibility(View.INVISIBLE);
            } else {
                Toast.makeText(Student_Reg.this, " Data Add Failed", Toast.LENGTH_SHORT).show();
                progressBar_reg.setVisibility(View.INVISIBLE);
            }
        });
    }

//    @NonNull
//    static CollectionReference getRegCollectRef() {
//        return FirebaseFirestore.getInstance().collection("Users").document().getParent();
//    }

    //Realtime DB
    private void sendDateToDatabase2() {
        final String sendFname = Objects.requireNonNull(edinput_reg_fname.getText()).toString().trim();
        final String sendSurame = Objects.requireNonNull(edinput_reg_surname.getText()).toString().trim();
        final String sendSid = Objects.requireNonNull(edinput_reg_sid.getText()).toString().trim();
        final String sendMono = Objects.requireNonNull(edinput_reg_mono.getText()).toString().trim();
        final String sendEmail = Objects.requireNonNull(edinput_reg_email.getText()).toString().trim();
        String sendGender = "";
        String sendDOB = set_DOBDate;
        final String sendClg = txt_Spinner_Dept.getText().toString().trim();

        //Gender Selection..
        if (rbMale.isChecked()) {
            sendGender = "Male";

        } else if (rbFemale.isChecked()) {
            sendGender = "Female";
        }
        //send data to Firebase into Users Root
        regDatabaseReference.child("Users").child(sendSid).child("First Name").setValue(sendFname);
        regDatabaseReference.child("Users").child(sendSid).child("Surname").setValue(sendSurame);
        regDatabaseReference.child("Users").child(sendSid).child("Sid").setValue(sendSid);
        regDatabaseReference.child("Users").child(sendSid).child("Mobile No").setValue(sendMono);
        regDatabaseReference.child("Users").child(sendSid).child("Email Id ").setValue(sendEmail);
        regDatabaseReference.child("Users").child(sendSid).child("Gender").setValue(sendGender);
        regDatabaseReference.child("Users").child(sendSid).child("Date Of Birth").setValue(sendDOB);
        regDatabaseReference.child("Users").child(sendSid).child("College").setValue(sendClg);
    }
}