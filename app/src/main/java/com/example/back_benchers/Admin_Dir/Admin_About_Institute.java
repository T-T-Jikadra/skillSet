package com.example.back_benchers.Admin_Dir;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.back_benchers.R;

public class Admin_About_Institute extends AppCompatActivity {
    ImageButton backBtn_AdminAbout_Itnt;
    TextView AppBarTitle_AAIn;
    TextView t1, t2, t3, t4, t5, t6, t7;

    // String variables containing information about the institute
    String str1 = "INSTITUTE OVERVIEW.. \n" +
            "\n" +
            "The skillSet institute is a public research Institute and technical institute in India. skillSet is the topmost " +
            "preferred choice for Indian students in STEM fields such as computer science and engineering, medical science, " +
            "commerce. skillSet is established by government of India to provide the world class education to the students in 2004." +
            " After that skillSet becomes most popular and genuine Institute among students . \n" +
            "\n" +
            "WHAT IS SPECIAL ABOUT INSTITUTE.. \n" +
            "\n" +
            "The institute is recognised worldwide as a leader in the field of engineering education and research. Reputed for the" +
            " outstanding calibre of students graduating from its undergraduate and postgraduate programmes, the institute attracts " +
            "the best students from the country for its bachelor's, master's and doctoral programmes. Research and academic programmes " +
            "at skillSet are driven by an outstanding faculty, many of whom are reputed for their research contributions internationally.\n";
    String str2 = "DEPARTMENTS OF INSTITUTE.. \n" +
            "\n" +
            "1.  Computer Science Engineering (CSE) \n" +
            "\n" +
            "Computer Science Engineering (CSE) is an academic programme that integrates the field of Computer Engineering and Computer" +
            " Science. The topics covered in the course are computation, algorithms, programming languages, program design, computer" +
            " software, computer hardware and others. Computer science engineers are involved in many aspects of computing, from the" +
            " design of individual microprocessors, personal computers, and supercomputers to circuit designing and writing software " +
            "that powers them. \n";

    String str3 = "2.  Chemical Engineering\n" +
            "\n" +
            "Chemical Engineering is the set of techniques and ideas used to transform unusable chemicals and raw materials into finished" +
            " goods.  It involves a number of processes and principles applied to convert raw materials/chemicals into useful products" +
            " such as clothes, drinks, paint, fuel, etc.\n";

    String str4 = "3. Industrial Engineering\n" +
            "\n" +
            "Industrial Engineering is an engineering profession that deals with the development, improvement and implementation of" +
            " integrated systems of knowledge, information, equipment, human resources, and finance to optimise complex engineering " +
            "processes or systems and organisations, dealing in engineering work.\n";

    String str5 = "4. MBBS\n" +
            "\n" +
            "MBBS or Bachelor of Medicine and Bachelor of Surgery is also described as BMBS, which is an abbreviation of the Latin word," +
            " Medicinae Baccalaureus Baccalaureus Chirurgiae. Bachelor of Medicine and Bachelor of Surgery or MBBS is an undergraduate " +
            "degree programme in the field of Medicine and Surgery.\n";

    String str6 = "5. CA/CS\n" +
            "\n" +
            "CA course is among the most opted professional courses in India. Thousands of students enrol in this course every year to " +
            "become Chartered Accountants. If you’re also registering for the Chartered Accountancy course and want to know everything " +
            "about it, this is the only guide you’ll need.\n" +
            "\n" +
            "CS course consists of two stages - CS executive followed by the CS professional programmes. To get admission into the CS " +
            "executive programme (first level), candidates need to qualify in the national-level Company Secretary Executive Entrance " +
            "test (CSEET). A direct admission route is also available to eligible candidates.\n";

    String str7 = "OUR VISION.. \n" +
            "\n" +
            "Our vision is to empower the students by instilling them the principles of overall development and to make them competent " +
            "in all the walks of life also to assist the students unleash their potential in academic, sports, cultural, social and " +
            "managerial spheres by creating opportunities and generating awareness in the same .\n";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_about_institute);

        // Set a click listener for the back button to navigate back
        backBtn_AdminAbout_Itnt = findViewById(R.id.backBtn_1);
        backBtn_AdminAbout_Itnt.setOnClickListener(view -> onBackPressed());

        // Set the title for the app bar
        AppBarTitle_AAIn = findViewById(R.id.txtId_appBar);
        AppBarTitle_AAIn.setText("About Institute ..");

        // Initialize UI elements
        t1 = findViewById(R.id.text1_AAIn);
        t2 = findViewById(R.id.text2_AAIn);
        t3 = findViewById(R.id.text3_AAIn);
        t4 = findViewById(R.id.text4_AAIn);
        t5 = findViewById(R.id.text5_AAIn);
        t6 = findViewById(R.id.text6_AAIn);
        t7 = findViewById(R.id.text7_AAIn);

        // Set the text for each TextView with appropriate alignment
        t1.setText(str1);
        t1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        t2.setText(str2);
        t2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        t3.setText(str3);
        t3.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        t4.setText(str4);
        t4.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        t5.setText(str5);
        t5.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        t6.setText(str6);
        t6.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        t7.setText(str7);
        t7.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
    }
}
