package com.example.back_benchers.Admin_Dir;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.back_benchers.Adpters.ViewPagerAdapter;
import com.example.back_benchers.R;
import com.google.android.material.tabs.TabLayout;

public class Admin_Profile extends AppCompatActivity {
    ImageButton backBtn_AdminProfile;
    TextView AppBarTitle_AP;
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    ViewPagerAdapter ViewPagerAdapter;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_profile);

        backBtn_AdminProfile = findViewById(R.id.backBtn_1);
        backBtn_AdminProfile.setOnClickListener(view -> onBackPressed());

        AppBarTitle_AP = findViewById(R.id.txtId_appBar);
        AppBarTitle_AP.setText("Admin Profile ..");


//        tabLayout = findViewById(R.id.tab_layout);
//        viewPager2 = findViewById(R.id.view_pager);
//        myViewPagerAdapter = new myViewPagerAdapter(this);
//        viewPager2.setAdapter(myViewPagerAdapter);
//
//        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                viewPager2.setCurrentItem(tab.getPosition());
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
//
//        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
//            @Override
//            public void onPageSelected(int position) {
//                super.onPageSelected(position);
//                tabLayout.getTabAt(position).select();
//            }
//        });
    }
}