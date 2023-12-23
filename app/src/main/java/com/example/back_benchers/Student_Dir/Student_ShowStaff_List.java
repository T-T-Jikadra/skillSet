package com.example.back_benchers.Student_Dir;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.back_benchers.Adpters.Staff_Adapter;
import com.example.back_benchers.R;

import java.util.ArrayList;
import java.util.Arrays;

public class Student_ShowStaff_List extends AppCompatActivity {
    String[] data = {
            "Twinkle S Panchal",
            "Krupali N Prajapati",
            "Jigisha A Patel",
            "Ragini Prasad",
            "Ishvari Patel",
            "Damyanti K Patel",
            "Hitesh Patel",
            "Priyanka D Chauhan",
            "Puja Shah",
            "Bhumi N Lad",
            "Reena Akbari",
            "Mansi A Shah",
            "VIshwananth Borse",
            "Viral Polishwala",
            "Vishal M Pandya",
            "Rakshit Rathod",
            "Utsavi Shah",
            "Himani Gandhi",
            "Nayna Mistry",
            "Hiral A Patel",
            "Hiral D Patel",
            "Priti M Tailor",
            "PradeepKumar Lenka",
            "Kejal Vadza",
            "Jaimin H Shukla",};
    ImageButton backBtn_StudStaffList;
    TextView AppBarTitle_SSSL;
    SwipeRefreshLayout swipeRefresh_studtt;
    ListView listView;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_show_staff_list);

        backBtn_StudStaffList = findViewById(R.id.backBtn_1);
        backBtn_StudStaffList.setOnClickListener(view -> onBackPressed());

        AppBarTitle_SSSL = findViewById(R.id.txtId_appBar);
        AppBarTitle_SSSL.setText("Show Staff List ..");

        listView = findViewById(R.id.listview_Staff); // Replace with your ListView

        ArrayList<String> dataList = new ArrayList<>(Arrays.asList(data));

        Staff_Adapter adapter = new Staff_Adapter(this, dataList);
        listView.setAdapter(adapter);

        swipeRefresh_studtt = findViewById(R.id.swipeRefreshLayout_studstaff);
        swipeRefresh_studtt.setOnRefreshListener(this::show);
    }

    private void show() {
        ArrayList<String> dataList = new ArrayList<>(Arrays.asList(data));

        Staff_Adapter adapter = new Staff_Adapter(this, dataList);
        listView.setAdapter(adapter);
        swipeRefresh_studtt.setRefreshing(false);

    }
}
