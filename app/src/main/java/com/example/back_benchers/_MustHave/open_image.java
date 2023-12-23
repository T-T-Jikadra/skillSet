package com.example.back_benchers._MustHave;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.back_benchers.R;
import com.squareup.picasso.Picasso;

public class open_image extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_image);

        ImageView imageView = findViewById(R.id.largerImageView);

        Intent intent = getIntent();
        if (intent != null) {
            String imageUrl = intent.getStringExtra("image_url");
            Picasso.get().load(imageUrl).into(imageView);
        }
    }
}
