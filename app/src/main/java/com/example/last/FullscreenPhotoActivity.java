package com.example.last;

/**
 * Класс FullscreenPhotoActivity является активностью (Activity) в приложении Android.
 * Он отображает фотографию в полноэкранном режиме и предоставляет функциональность закрытия активности при клике на фото.
 */

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

public class FullscreenPhotoActivity extends AppCompatActivity {

    /**
     * Этот метод вызывается при создании активности. Внутри него происходит инициализация активности,
     * установка содержимого с помощью setContentView, нахождение и установка обработчика клика на fullscreenImageView.
     * Если в переданных при создании активности данных содержится путь к фотографии (photo_path),
     * то фотография загружается в ImageView с помощью setImageURI.
     */

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
