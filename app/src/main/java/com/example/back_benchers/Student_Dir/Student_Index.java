// Import statements for required classes and packages
package com.example.back_benchers.Student_Dir;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.back_benchers.Admin_Dir.Admin_About_Institute;
import com.example.back_benchers.Admin_Dir.Admin_About_us;
import com.example.back_benchers.R;
import com.example.back_benchers._MustHave.select_TypeOf_User;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Objects;

// Main activity class definition that implements NavigationView.OnNavigationItemSelectedListener
public class Student_Index extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    // Constant to control end scale for drawer animation
    static final float END_SCALE = 0.7f;
    // Declare UI elements
    DrawerLayout drawerLayout_stud;
    NavigationView navigationView_stud;
    ImageButton drawerBTN_stud;
    RelativeLayout contentView_stud;
    String Global_mailID;
    TextView moveTxt_Stud;
    TextView titleStud;
    TextView headerTextView_stud;
    TextView subheaderTextView_stud;
    ImageSlider imageSlider_Stud;
    CardView CardBtn1_Stud;
    CardView CardBtn2_Stud;
    CardView CardBtn3_Stud;
    CardView CardBtn4_Stud;
    CardView CardBtn5_Stud;
    CardView CardBtn6_Stud;
    WebView webView_Stud;
    private ImageView drawer_Img;

    // Method called when activity is created
    @SuppressLint({"SetTextI18n", "SetJavaScriptEnabled"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_index);

        //Image Slider ..
        imageSlider_Stud = findViewById(R.id.slider_Stud);
        ArrayList<SlideModel> slideModels_Stud = new ArrayList<>();
        slideModels_Stud.add(new SlideModel(R.drawable.is_blood, ScaleTypes.FIT));
        slideModels_Stud.add(new SlideModel(R.drawable.is_atham1, ScaleTypes.FIT));
        slideModels_Stud.add(new SlideModel(R.drawable.is_atham, ScaleTypes.FIT));
        slideModels_Stud.add(new SlideModel(R.drawable.is_teacher, ScaleTypes.FIT));
        slideModels_Stud.add(new SlideModel(R.drawable.is_ganesh, ScaleTypes.FIT));
        slideModels_Stud.add(new SlideModel(R.drawable.is_seminar, ScaleTypes.FIT));
        imageSlider_Stud.setImageList(slideModels_Stud, ScaleTypes.FIT);

        //6 cards ..
        CardBtn1_Stud = findViewById(R.id.cardItem1_Stud);
        CardBtn1_Stud.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), Student_ShowTimeTable.class)));

        CardBtn2_Stud = findViewById(R.id.cardItem2_Stud);
        CardBtn2_Stud.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), Student_ShowNotice.class)));

        CardBtn3_Stud = findViewById(R.id.cardItem3_Stud);
        CardBtn3_Stud.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), Student_ShowAssignment.class)));

        CardBtn4_Stud = findViewById(R.id.cardItem4_Stud);
        CardBtn4_Stud.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), Student_ShowStudy_Material.class)));

        CardBtn5_Stud = findViewById(R.id.cardItem5_Stud);
        CardBtn5_Stud.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), Student_PayFees.class)));

        CardBtn6_Stud = findViewById(R.id.cardItem6_Stud);
        CardBtn6_Stud.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), Student_ShowExams.class)));

        //Set The YouTube Video Inside WebView ..
        webView_Stud = findViewById(R.id.webId_Stud);
        String webUrl =
                "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/QupMyUyW3uM?si=_R1Hbl058RAn1oAe\" " +
                        "title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; " +
                        "gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>";

        webView_Stud.loadData(webUrl, "text/html", "utf-8");
        webView_Stud.getSettings().setJavaScriptEnabled(true);
        webView_Stud.setWebChromeClient(new WebChromeClient());

        // Initialize UI elements
        drawerLayout_stud = findViewById(R.id.drawer_layout_stud);
        navigationView_stud = findViewById(R.id.navigation_view_stud);
        drawerBTN_stud = findViewById(R.id.menuBtn_2);
        titleStud = findViewById(R.id.txtTitleToolbar);
        contentView_stud = findViewById(R.id.content_stud);

        titleStud.setText("Student Dashboard");

        moveTxt_Stud = findViewById(R.id.Highlighttxt_Stud);
        moveTxt_Stud.setSelected(true); // Make text in TextView scroll horizontally

        //btn_stud = findViewById(R.id.btn_dashboard_stud);

        // Set up navigation drawer
        Stud_NavigationDrawer();

        // Set custom text to TextView inside header of navigation drawer from database ..
        View headerView = navigationView_stud.getHeaderView(0);

        headerTextView_stud = headerView.findViewById(R.id.titletext_stud);
        subheaderTextView_stud = headerView.findViewById(R.id.subtext_stud);
        //id for drawer gender image
        drawer_Img = headerView.findViewById(R.id.imageview_studdrawer);

        FirebaseAuth fAuth_StudIndexHeader = FirebaseAuth.getInstance();
        FirebaseUser fUser_StudIndexHeader = fAuth_StudIndexHeader.getCurrentUser();
        String uID = Objects.requireNonNull(fUser_StudIndexHeader).getEmail();
        DocumentReference Ref_EducationDataRef = FirebaseFirestore
                .getInstance()
                .collection("Users")
                .document(Objects.requireNonNull(uID));

        Ref_EducationDataRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    String StudentName = document.getString("Student_First_Name");
                    String StudentMail = document.getString("Email_Id");
                    String StudentGender = document.getString("Gender");
                    // Set the student's name to the TextView
                    headerTextView_stud.setText(StudentName);
                    subheaderTextView_stud.setText(StudentMail);
                    // Check gender and set the appropriate image
                    if (Objects.requireNonNull(StudentGender).equals("Male")) {
                        drawer_Img.setImageResource(R.drawable.img_rb_male); // Male image
                    }
                    else {
                        drawer_Img.setImageResource(R.drawable.img_rb_female); // Female image
                    }
                } else {
                    // Handle the case if the document doesn't exist
                }
            } else {
                // Handle the error here
            }
        });
    }

    // Method to handle onResume event
    @Override
    protected void onResume() {
        super.onResume();
        // Set the checked item in navigation drawer
        navigationView_stud.setCheckedItem(R.id.nav_studdash);
    }

    // Method to set up the student navigation drawer
    private void Stud_NavigationDrawer() {
        // Bring navigation drawer to the front
        navigationView_stud.bringToFront();
        navigationView_stud.setNavigationItemSelectedListener(this);
        navigationView_stud.setCheckedItem(R.id.nav_studdash);

        // Toggle the navigation drawer when the button is clicked
        drawerBTN_stud.setOnClickListener(view -> {
            if (drawerLayout_stud.isDrawerVisible(GravityCompat.START))
                drawerLayout_stud.closeDrawer(GravityCompat.START);
            else drawerLayout_stud.openDrawer(GravityCompat.START);
        });
        // Animate the navigation drawer
        Stud_AnimateNavigationDrawer();
    }

    // Method to animate the student navigation drawer
    private void Stud_AnimateNavigationDrawer() {
        // Set scrim color for the drawer layout
        drawerLayout_stud.setScrimColor(getResources().getColor(R.color.my_Pirmary));

        // Add a drawer listener to animate the content view while sliding the drawer
        drawerLayout_stud.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                // Scale and translate the content view during drawer slide
                final float diffScaledOffset = slideOffset * (1 - END_SCALE);
                final float offsetScale = 1 - diffScaledOffset;
                contentView_stud.setScaleX(offsetScale);
                contentView_stud.setScaleY(offsetScale);
                final float xOffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff = contentView_stud.getWidth() * diffScaledOffset / 2;
                final float xTranslation = xOffset - xOffsetDiff;
                contentView_stud.setTranslationX(xTranslation);
            }
        });
    }

    // Method to handle back button press
    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBackPressed() {
        if (drawerLayout_stud.isDrawerVisible(GravityCompat.START))
            drawerLayout_stud.closeDrawer(GravityCompat.START);
        else {
            // Display a custom dialog on back button press
            Dialog dialog = new Dialog(Student_Index.this);
            dialog.setContentView(R.layout.costom_alert_dialog_exit);
            dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.file_round_corner));
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.setCancelable(false);
            Button adb1 = dialog.findViewById(R.id.albtn1);
            Button adb2 = dialog.findViewById(R.id.albtn2);

            // Handle button clicks in the dialog
            adb1.setOnClickListener(view -> finish());
            adb2.setOnClickListener(view -> dialog.cancel());
            dialog.show();
        }
    }

    // Method to handle navigation item selection
    @SuppressLint("QueryPermissionsNeeded")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        // Handle different navigation item selections
        if (itemId == R.id.nav_studprofile) {
            // Start Admin_ProfileTab activity
            Intent IntentToProfile = new Intent(getApplicationContext(), Student_ProfileTab.class);
            IntentToProfile.putExtra("Mail_For_Profile", Global_mailID);
            startActivity(IntentToProfile);
        } else if (itemId == R.id.nav_studattend) {
            // Start Student_ShowAttendence activity
            startActivity(new Intent(getApplicationContext(), Student_ShowAttendence.class));
        } else if (itemId == R.id.nav_studnotice) {
            // Start Student_ShowNotice activity
            startActivity(new Intent(getApplicationContext(), Student_ShowNotice.class));
        } else if (itemId == R.id.nav_studfees) {
            // Start Student_PayFees activity
            startActivity(new Intent(getApplicationContext(), Student_PayFees.class));
        } else if (itemId == R.id.nav_studass) {
            // Start Student_ShowAssignment activity
            startActivity(new Intent(getApplicationContext(), Student_ShowAssignment.class));
        } else if (itemId == R.id.nav_studstudymat) {
            // Start Student_ShowStudy_Material activity
            startActivity(new Intent(getApplicationContext(), Student_ShowStudy_Material.class));
        } else if (itemId == R.id.nav_studtimetable) {
            // Start Student_ShowTimeTable activity
            startActivity(new Intent(getApplicationContext(), Student_ShowTimeTable.class));
        } else if (itemId == R.id.nav_studexam) {
            // Start Student_ShowExams activity
            startActivity(new Intent(getApplicationContext(), Student_ShowExams.class));
        } else if (itemId == R.id.nav_studstaff) {
            // Start Student_ShowStaff_List activity
            startActivity(new Intent(getApplicationContext(), Student_ShowStaff_List.class));
        } else if (itemId == R.id.nav_studains) {
            // Start Admin_About_Institute activity
            startActivity(new Intent(getApplicationContext(), Admin_About_Institute.class));
        } else if (itemId == R.id.nav_studaapp) {
            // Start Admin_About_Institute activity
            startActivity(new Intent(getApplicationContext(), Student_About_App.class));
        } else if (itemId == R.id.nav_studaus) {
            // Start Admin_About_Institute activity
            startActivity(new Intent(getApplicationContext(), Admin_About_us.class));
        } else if (itemId == R.id.nav_studfeedback) {
            // Start Student_GiveFeedback activity
            startActivity(new Intent(getApplicationContext(), Student_GiveFeedback.class));
        } else if (itemId == R.id.nav_studshare) {

//            simple link ..
//            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://google.com"));
//            startActivity(i);

//            intent with text ..
//            Intent sendIntent = new Intent();
//            sendIntent.setAction(Intent.ACTION_SEND);
//            sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
//            sendIntent.setType("text/plain");
//
//            // Create a chooser and start the chooser activity
//            Intent chooserIntent = Intent.createChooser(sendIntent, "Share via");
//            if (sendIntent.resolveActivity(getPackageManager()) != null) {
//                startActivity(chooserIntent);
//            } else {
//                // Handle the case where no suitable app is available to handle the intent
//                Toast.makeText(this, "No app available to share", Toast.LENGTH_SHORT).show();
//            }

            //With Image [ doesnt supports ] ..
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "This is text sent from Student Index from The App ..");

            // Attach the image URI to the intent
            Uri imageUri = Uri.parse("android.resource://com.example.yourapp/" + R.drawable.img_rb_male);
            sendIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
            sendIntent.setType("*/*");

            // Create a chooser and start the chooser activity
            Intent chooserIntent = Intent.createChooser(sendIntent, "Share via");
            if (sendIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(chooserIntent);
            } else {
                // Handle the case where no suitable app is available to handle the intent
                Toast.makeText(this, "No app available to share", Toast.LENGTH_SHORT).show();
            }

        } else if (itemId == R.id.nav_studlogout) {
            // Sign out user and navigate to select_TypeOf_User activity
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(), select_TypeOf_User.class));
            finish();
        }
        return true; // Indicate that the navigation item selection is handled
    }
}