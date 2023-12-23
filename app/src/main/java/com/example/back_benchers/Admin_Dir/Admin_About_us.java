package com.example.back_benchers.Admin_Dir;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.util.Linkify;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.back_benchers.R;

public class Admin_About_us extends AppCompatActivity {

    ImageButton backBtn_AdminAboutus;
    TextView AppBarTitle_AAUs;
    TextView txt_whatsapp1;
    TextView txt_whatsapp2;
    TextView txt_insta1;
    TextView txt_insta2;
    TextView txt_gmail1;
    TextView txt_gmail2;

    @SuppressLint({"SetTextI18n", "IntentReset"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_about_us);

        backBtn_AdminAboutus = findViewById(R.id.backBtn_1);
        backBtn_AdminAboutus.setOnClickListener(view -> onBackPressed());

        AppBarTitle_AAUs = findViewById(R.id.txtId_appBar);
        AppBarTitle_AAUs.setText("About Us ..");

        txt_gmail1 = findViewById(R.id.abc_gmail1);
        txt_gmail2 = findViewById(R.id.abc_gmail2);
        txt_whatsapp1 = findViewById(R.id.abc_whatsapp1);
        txt_whatsapp2 = findViewById(R.id.abc_whatsapp2);
        txt_insta1 = findViewById(R.id.abc_insta1);
        txt_insta2 = findViewById(R.id.abc_insta2);

        //Gmail Link 1
        txt_gmail1.setOnClickListener(view -> {
            String[] recipientEmails = {"jikadra001@gmail.com"}; // Replace with the recipient's email address
            String subject = "From skillSet Application ..";
            String message = "Hello, Good Morning everyone ,This Is the text email message sent" +
                    " from Our skillSet Application In Gmail App.";

            // Create an Intent for sending an email
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setData(Uri.parse("mailto:")); // Use the 'mailto:' scheme
            intent.putExtra(Intent.EXTRA_EMAIL, recipientEmails); // Recipient's email addresses
            intent.putExtra(Intent.EXTRA_SUBJECT, subject); // Email subject
            intent.putExtra(Intent.EXTRA_TEXT, message); // Email message

            // Set the type of the intent to indicate it's an email
            intent.setType("message/rfc822");
            intent.setPackage("com.google.android.gm");

            // Start the email client
            startActivity(Intent.createChooser(intent, "Send Email"));
        });


        //Gmail Link 2
        txt_gmail2.setOnClickListener(view -> {
            String[] recipientEmails = {"jagan09@gmail.com"}; // Replace with the recipient's email address
            String subject = "From skillSet Application ..";
            String message = "Hello, Good Morning everyone ,This Is the text email message sent" +
                    " from Our skillSet Application In Gmail App.";

            // Create an Intent for sending an email
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setData(Uri.parse("mailto:")); // Use the 'mailto:' scheme
            intent.putExtra(Intent.EXTRA_EMAIL, recipientEmails); // Recipient's email addresses
            intent.putExtra(Intent.EXTRA_SUBJECT, subject); // Email subject
            intent.putExtra(Intent.EXTRA_TEXT, message); // Email message

            // Set the type of the intent to indicate it's an email
            intent.setType("message/rfc822");
            intent.setPackage("com.google.android.gm");

            // Start the email client
            startActivity(Intent.createChooser(intent, "Send Email"));
        });

        //Whatsapp Link 1
        Linkify.addLinks(txt_whatsapp1, Linkify.WEB_URLS);
        txt_whatsapp1.setOnClickListener(view -> {
            String phone = "+919978108422";
            String msg = "hellow";
            Uri uri_wa1 = Uri.parse("https://api.whatsapp.com/send?phone=" + phone + "&text=" + msg);
            startActivity(new Intent(Intent.ACTION_VIEW, uri_wa1));

        });

        //Whatsapp Link 2
        Linkify.addLinks(txt_whatsapp2, Linkify.WEB_URLS);
        txt_whatsapp2.setOnClickListener(view -> {
            String phone = "+919724869992";
            String msg = "hellow";
            Uri uri_wa2 = Uri.parse("https://api.whatsapp.com/send?phone=" + phone + "&text=" + msg);
            startActivity(new Intent(Intent.ACTION_VIEW, uri_wa2));

        });

        //Insta Link 1
        Linkify.addLinks(txt_insta1, Linkify.WEB_URLS);
        txt_insta1.setOnClickListener(view -> {
            Uri uri_in1 = Uri.parse("https://www.instagram.com/t.t._jikadra/");
            Intent intent_insta1 = new Intent(Intent.ACTION_VIEW, uri_in1);
            intent_insta1.setPackage("com.instagram.android");
            startActivity(intent_insta1);
        });

        //Insta Link 2
        Linkify.addLinks(txt_insta2, Linkify.WEB_URLS);
        txt_insta2.setOnClickListener(view -> {
            Uri uri_in1 = Uri.parse("https://www.instagram.com/jagan_mahanty_/");
            Intent intent_insta2 = new Intent(Intent.ACTION_VIEW, uri_in1);
            intent_insta2.setPackage("com.instagram.android");
            startActivity(intent_insta2);
        });
    }
}