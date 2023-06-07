package com.example.last;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private DrawerLayout drawerLayout;
    private TextView textViewUsername;
    private ImageView userPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawerLayout);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        NavigationView navigationView = findViewById(R.id.navigationView);

        View headerView = navigationView.getHeaderView(0);
        textViewUsername = headerView.findViewById(R.id.username_textview);
        userPhoto = headerView.findViewById(R.id.user_photo);

        String username = getIntent().getStringExtra("username");
        if (username != null) {
            textViewUsername.setText(username);
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_news:
                        // Обработка нажатия на элемент "Новости"
                        return true;
                    case R.id.action_schedule:
                        // Обработка нажатия на элемент "Расписание"
                        return true;
                    case R.id.action_chat:
                        // Обработка нажатия на элемент "Чат"
                        return true;
                    case R.id.action_graduates:
                        // Обработка нажатия на элемент "Работы выпускников"
                        return true;
                    case R.id.action_more:
                        openDrawer();
                        return true;
                    default:
                        return false;
                }
            }
        });

        userPhoto.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                openGallery();
                return true;
            }
        });
    }

    private void openDrawer() {
        drawerLayout.openDrawer(GravityCompat.END);
    }

    private void closeDrawer() {
        drawerLayout.closeDrawer(GravityCompat.END);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            closeDrawer();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
            closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            if (imageUri != null) {
                uploadPhoto(imageUri);
            }
        }
    }

    private void uploadPhoto(Uri imageUri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(imageUri);
            File storageDir = getFilesDir(); // Получаем директорию внутреннего хранилища приложения
            File outputFile = new File(storageDir, "user_photo.jpg"); // Указываем имя файла

            FileOutputStream outputStream = new FileOutputStream(outputFile);

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            outputStream.close();
            inputStream.close();

            // Фотография успешно сохранена во внутреннем хранилище приложения.
            // Теперь вы можете использовать этот файл по вашему усмотрению.
            String filePath = outputFile.getAbsolutePath();
            saveUserPhoto(filePath);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, "Ошибка при сохранении фото", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveUserPhoto(String filePath) {
        // Здесь вы можете выполнить необходимые действия с сохраненной фотографией,
        // например, отобразить ее в ImageView или выполнить другую обработку.
        // В этом примере мы просто загрузим фотографию на место предыдущей.
        File photoFile = new File(filePath);
        if (photoFile.exists()) {
            Uri photoUri = Uri.fromFile(photoFile);
            userPhoto.setImageURI(photoUri);
            Toast.makeText(MainActivity.this, "Фотография успешно сохранена", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "Ошибка при сохранении фотографии", Toast.LENGTH_SHORT).show();
        }
    }
}
















