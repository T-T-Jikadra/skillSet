package com.example.back_benchers.Admin_Dir;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.back_benchers.R;
import com.example.back_benchers._MustHave.select_TypeOf_User;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class Admin_Index extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    static final float END_SCALE = 0.7f;
    DrawerLayout drawerLayout_Admin;
    NavigationView navigationView_Admin;
    ImageButton drawerBTN_Admin;
    RelativeLayout contentView_Admin;
    TextView moveTxt_Admin;
    TextView titleAdmin;
    ImageSlider imageSlider_Admin;
    CardView CardBtn1_Admin;
    CardView CardBtn2_Admin;
    CardView CardBtn3_Admin;
    CardView CardBtn4_Admin;
    CardView CardBtn5_Admin;
    CardView CardBtn6_Admin;
    WebView webView_Admin;

    @SuppressLint({"SetTextI18n", "SetJavaScriptEnabled"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_index);

        //Checks for the Internet
        if (!isConnected_AI(Admin_Index.this)) {
            // Show a dialog prompting user to connect to the internet
            showCustomDialog_AI();
            return;
        }

        //Image Slider
        imageSlider_Admin = findViewById(R.id.slider_Admin);
        ArrayList<SlideModel> slideModels_Admin = new ArrayList<>();
        slideModels_Admin.add(new SlideModel(R.drawable.is_blood, ScaleTypes.FIT));
        slideModels_Admin.add(new SlideModel(R.drawable.is_atham1, ScaleTypes.FIT));
        slideModels_Admin.add(new SlideModel(R.drawable.is_atham, ScaleTypes.FIT));
        slideModels_Admin.add(new SlideModel(R.drawable.is_teacher, ScaleTypes.FIT));
        slideModels_Admin.add(new SlideModel(R.drawable.is_ganesh, ScaleTypes.FIT));
        slideModels_Admin.add(new SlideModel(R.drawable.is_seminar, ScaleTypes.FIT));
        imageSlider_Admin.setImageList(slideModels_Admin, ScaleTypes.FIT);
        //6 Cards ..
        CardBtn1_Admin = findViewById(R.id.cardItem1_Admin);
        CardBtn1_Admin.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), Admin_Students_List.class)));

        CardBtn2_Admin = findViewById(R.id.cardItem2_Admin);
        CardBtn2_Admin.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), Admin_TimeTable.class)));

        CardBtn3_Admin = findViewById(R.id.cardItem3_Admin);
        CardBtn3_Admin.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), Admin_AddNotice.class)));

        CardBtn4_Admin = findViewById(R.id.cardItem4_Admin);
        CardBtn4_Admin.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), Admin_AddAssignment.class)));

        CardBtn5_Admin = findViewById(R.id.cardItem5_Admin);
        CardBtn5_Admin.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), Admin_Add_Study_Material.class)));

        CardBtn6_Admin = findViewById(R.id.cardItem6_Admin);
        CardBtn6_Admin.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), Admin_ExamSchedule.class)));

        //Set The YouTube Video Inside WebView ..
        webView_Admin = findViewById(R.id.webId_Admin);
        String webUrl =
                "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/QupMyUyW3uM?si=_R1Hbl058RAn1oAe\" " +
                        "title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; " +
                        "encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>";

        webView_Admin.loadData(webUrl, "text/html", "utf-8");
        webView_Admin.getSettings().setJavaScriptEnabled(true);
        webView_Admin.setWebChromeClient(new WebChromeClient());

        // Initialize UI elements
        drawerLayout_Admin = findViewById(R.id.drawer_layout_admin);
        navigationView_Admin = findViewById(R.id.navigation_view_admin);
        drawerBTN_Admin = findViewById(R.id.menuBtn_2);
        titleAdmin = findViewById(R.id.txtTitleToolbar);
        contentView_Admin = findViewById(R.id.content_admin);
        moveTxt_Admin = findViewById(R.id.Highlighttxt_Admin);

        titleAdmin.setText("Admin Dashboard");

        moveTxt_Admin.setSelected(true); // Make text in TextView scroll horizontally

        Admin_navigationDrawer(); // Initialize the navigation drawer

        // Set custom text to TextView inside header of drawer
        View headerView = navigationView_Admin.getHeaderView(0);
        TextView headerTextView_admin = headerView.findViewById(R.id.titletext_admin);
        headerTextView_admin.setText("Admin");

    }

    //check for internet method
    private boolean isConnected_AI(Admin_Index admin_index) {
        ConnectivityManager connectivityManager = (ConnectivityManager) admin_index.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiCOnn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        return (wifiCOnn != null && wifiCOnn.isConnected()) || (mobileConn != null && mobileConn.isConnected());
    }

    //check for internet method
    private void showCustomDialog_AI() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Admin_Index.this);
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

    // Set back the pointer of selected tab to the 1st tab in the drawer
    @Override
    protected void onResume() {
        super.onResume();
        navigationView_Admin.setCheckedItem(R.id.nav_admindash);
    }

    // Navigation Drawer Functions
    private void Admin_navigationDrawer() {
        navigationView_Admin.bringToFront();
        navigationView_Admin.setNavigationItemSelectedListener(this);
        navigationView_Admin.setCheckedItem(R.id.nav_admindash);

        // Set click listener for drawer button
        drawerBTN_Admin.setOnClickListener(view -> {
            if (drawerLayout_Admin.isDrawerVisible(GravityCompat.START))
                drawerLayout_Admin.closeDrawer(GravityCompat.START);
            else drawerLayout_Admin.openDrawer(GravityCompat.START);
        });
        Admin_AnimateNavigationDrawer(); // Animate navigation drawer
    }

    private void Admin_AnimateNavigationDrawer() {
        // Set scrim color and animation for navigation drawer
        drawerLayout_Admin.setScrimColor(getResources().getColor(R.color.my_Pirmary));

        drawerLayout_Admin.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                // Scale and translate the content view based on slide offset
                final float diffScaledOffset = slideOffset * (1 - END_SCALE);
                final float offsetScale = 1 - diffScaledOffset;
                contentView_Admin.setScaleX(offsetScale);
                contentView_Admin.setScaleY(offsetScale);

                final float xOffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff = contentView_Admin.getWidth() * diffScaledOffset / 2;
                final float xTranslation = xOffset - xOffsetDiff;
                contentView_Admin.setTranslationX(xTranslation);
            }
        });
    }

    // Handle navigation item selection
    @SuppressLint("QueryPermissionsNeeded")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        // Start corresponding activities based on selected item
        if (itemId == R.id.nav_adminprofile) {
            startActivity(new Intent(getApplicationContext(), Admin_Profile.class));
        } else if (itemId == R.id.nav_adminattend) {
            startActivity(new Intent(getApplicationContext(), Admin_TakeAttendence.class));
        } else if (itemId == R.id.nav_adminstudlist) {
            startActivity(new Intent(getApplicationContext(), Admin_Students_List.class));
        } else if (itemId == R.id.nav_adminnotice) {
            startActivity(new Intent(getApplicationContext(), Admin_AddNotice.class));
        } else if (itemId == R.id.nav_adminass) {
            startActivity(new Intent(getApplicationContext(), Admin_AddAssignment.class));
        } else if (itemId == R.id.nav_adminstudymat) {
            startActivity(new Intent(getApplicationContext(), Admin_Add_Study_Material.class));
        } else if (itemId == R.id.nav_admintimetable) {
            startActivity(new Intent(getApplicationContext(), Admin_TimeTable.class));
        } else if (itemId == R.id.nav_adminexam) {
            startActivity(new Intent(getApplicationContext(), Admin_ExamSchedule.class));
        } else if (itemId == R.id.nav_adminfeedback) {
            startActivity(new Intent(getApplicationContext(), Admin_Feedback.class));
        } else if (itemId == R.id.nav_adminains) {
            startActivity(new Intent(getApplicationContext(), Admin_About_Institute.class));
        } else if (itemId == R.id.nav_adminaapp) {
            startActivity(new Intent(getApplicationContext(), Admin_About_App.class));
        } else if (itemId == R.id.nav_adminaus) {
            startActivity(new Intent(getApplicationContext(), Admin_About_us.class));
        } else if (itemId == R.id.nav_adminshare) {

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

            //With Image [doesnt supports] ..
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "This is text sent from Admin Index from The App ..");

            // Attach the image URI to the intent
            Uri imageUri = Uri.parse("android.resource://com.example.back_benchers/drawable/campusvector2");
            Log.d("Image URI", imageUri.toString());
            sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Shared via MyApp");

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

        } else if (itemId == R.id.nav_adminlogout) {
            // Sign out and navigate to the login screen ..
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(), select_TypeOf_User.class));
            finish();
        }
        return true;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBackPressed() {
        if (drawerLayout_Admin.isDrawerVisible(GravityCompat.START)) {
            drawerLayout_Admin.closeDrawer(GravityCompat.START);
        } else {
            // Show a custom dialog when back button is pressed
            Dialog dialog = new Dialog(Admin_Index.this);
            dialog.setContentView(R.layout.costom_alert_dialog_exit);
            dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.file_round_corner));
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.setCancelable(false);

            Button dialog_exit = dialog.findViewById(R.id.albtn1);
            Button dialog_close = dialog.findViewById(R.id.albtn2);

            // Finish the activity if the first button is clicked
            dialog_exit.setOnClickListener(view -> finish());

            // Dismiss the dialog if the second button is clicked
            dialog_close.setOnClickListener(view -> dialog.cancel());

            // Show the custom dialog
            dialog.show();
        }
    }
}
