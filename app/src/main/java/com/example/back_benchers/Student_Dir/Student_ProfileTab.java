package com.example.back_benchers.Student_Dir;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.back_benchers.Adpters.ViewPagerAdapter;
import com.example.back_benchers.R;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

public class Student_ProfileTab extends AppCompatActivity {
    TabLayout tabLayout_Profile;
    ViewPager2 viewPager_AdminProfile;
    ImageButton backBtn_Profile;
    TextView AppBarTitle_AP;
    ViewPagerAdapter myViewPagerAdapter;
    String id_AdminProfile ;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile_tab);

        // Initialize UI elements
        backBtn_Profile = findViewById(R.id.backBtn_1);
        backBtn_Profile.setOnClickListener(view -> onBackPressed());

        AppBarTitle_AP = findViewById(R.id.txtId_appBar);
        AppBarTitle_AP.setText("Profile ..");
        // Get the intent and retrieve the email for profile
        Intent i_get = getIntent();
        String gotFinalMail = i_get.getStringExtra("Mail_For_Profile");
        id_AdminProfile = gotFinalMail;

        // Initialize TabLayout and ViewPager2
        tabLayout_Profile = findViewById(R.id.tab_layout_Profile);
        viewPager_AdminProfile = findViewById(R.id.view_pager_Profile);

        // Create and set up the adapter for the ViewPager2
        myViewPagerAdapter = new ViewPagerAdapter(this);
        viewPager_AdminProfile.setAdapter(myViewPagerAdapter);

        // Set up tab selection listener
        tabLayout_Profile.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // Switch to the selected tab when clicked
                viewPager_AdminProfile.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        // Set up ViewPager2 page change callback
        viewPager_AdminProfile.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                // Select the corresponding tab when a page is selected
                Objects.requireNonNull(tabLayout_Profile.getTabAt(position)).select();
            }
        });
    }
}
