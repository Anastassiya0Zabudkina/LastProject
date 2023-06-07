package com.example.last;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

public class FullscreenPhotoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen_photo);

        ImageView fullscreenImageView = findViewById(R.id.fullscreen_imageview);
        fullscreenImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Закрыть активность при клике на фото в полноэкранном режиме
                finish();
            }
        });

        String photoPath = getIntent().getStringExtra("photo_path");
        if (photoPath != null) {
            // Загрузить фото в ImageView
            File photoFile = new File(photoPath);
            Uri photoUri = Uri.fromFile(photoFile);
            fullscreenImageView.setImageURI(photoUri);
        }
    }
}
