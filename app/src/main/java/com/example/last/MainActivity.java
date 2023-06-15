package com.example.last;
/**
 * MainActivity является главной активностью приложения.
 * Она служит входной точкой и предоставляет основной пользовательский интерфейс.
 */

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AlertDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class MainActivity extends BaseActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private DrawerLayout drawerLayout;
    private TextView textViewUsername;
    private ImageView userPhoto;
    private String userId;
    private DatabaseReference databaseReference;

    private static final String PREF_NAME = "login_pref";
    private static final String KEY_USER_ID = "user_id";

    /**
     * Метод, который возвращает идентификатор ресурса макета активности
     * (R.layout.activity_main)
     */

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_main;
    }

    /**
     * Метод, который обрабатывает выбор элемента нижней навигации.
     * Он принимает идентификатор выбранного элемента и в зависимости от этого идентификатора выполняет соответствующие действия.
     * Например, для элемента "Расписание" вызывается фрагмент ScheduleFragment,
     * для элемента "Тема" происходит переключение между светлой и темной темами приложения.
     */

    @Override
    protected void handleBottomNavigationItemSelected(int itemId) {
        switch (itemId) {
            case R.id.action_news:
                // Обработка нажатия на элемент "Новости"
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new NewsFragment())
                        .commit();
                break;
            case R.id.action_schedule:
                // Обработка нажатия на элемент "Расписание"
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new ScheduleFragment())
                        .commit();
                return;
            case R.id.action_chat:
                // Обработка нажатия на элемент "Чат"
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new ChatListFragment())
                        .commit();
                break;
            case R.id.action_graduates:
                // Обработка нажатия на элемент "Работы выпускников"
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new HomeworkFragment())
                        .commit();
                break;
            case R.id.action_more:
                openDrawer();
                break;
        }
    }

    /**
     * Метод, вызываемый при создании активности.
     * Здесь происходит инициализация и настройка элементов пользовательского интерфейса,
     * загрузка данных пользователя, установка обработчиков событий и другие дополнительные операции.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        userId = sharedPreferences.getString(KEY_USER_ID, "");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();

        // Загрузка данных пользователя
        loadUserData();

        drawerLayout = findViewById(R.id.drawerLayout);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        NavigationView navigationView = findViewById(R.id.navigationView);

        View headerView = navigationView.getHeaderView(0);
        textViewUsername = headerView.findViewById(R.id.username_textview);
        userPhoto = headerView.findViewById(R.id.user_photo);
        loadUserPhoto();

//        String username = getIntent().getStringExtra("username");
//        if (username != null) {
//            textViewUsername.setText(username);
//        }

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                handleBottomNavigationItemSelected(item.getItemId());
                return true;
            }
        });

        userPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFullscreenPhoto();
            }
        });

        userPhoto.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // Создание и настройка AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setItems(new CharSequence[]{"Удалить фото", "Обновить фото"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                // Действие "Удалить фото"
                                deletePhoto();
                                break;
                            case 1:
                                // Действие "Обновить фото"
                                openGallery();
                                break;
                        }
                    }
                });

                // Отображение AlertDialog
                builder.create().show();

                return true;
            }
        });


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_account:
                        // Обработка нажатия на элемент "Личный кабинет"
                        Intent accountIntent = new Intent(MainActivity.this, ProfileActivity.class);
                        startActivity(accountIntent);
                        return true;

                    case R.id.nav_students:
                        // Обработка нажатия на элемент "Список учащихся"
                        Intent usersIntent = new Intent(MainActivity.this, UsersActivity.class);
                        startActivity(usersIntent);
                        return true;
                    case R.id.nav_contacts:
                        // Обработка нажатия на элемент "Контакты преподавателей"
                        Intent teachersIntent = new Intent(MainActivity.this, TeachersActivity.class);
                        startActivity(teachersIntent);
                        return true;
                    case R.id.nav_theme:
                        // Обработка нажатия на элемент "Тема"
                        toggleTheme();
                        return true;
                    case R.id.nav_logout:
                        // Обработка нажатия на элемент "Выход"
                        logout();
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    /**
     * Метод, который открывает выдвижную панель (drawer) путем вызова drawerLayout.openDrawer(GravityCompat.END).
     */

    private void openDrawer() {
        drawerLayout.openDrawer(GravityCompat.END);
    }

    /**
     * Метод, который закрывает выдвижную панель путем вызова drawerLayout.closeDrawer(GravityCompat.END).
     */

    private void closeDrawer() {
        drawerLayout.closeDrawer(GravityCompat.END);
    }

    /**
     * Метод, вызываемый при выборе элемента меню.
     * Здесь обрабатывается выбор элемента "Домой" (стрелка вверх) на панели действий (action bar), чтобы закрыть выдвижную панель.
     */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            closeDrawer();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Метод, который переопределяет обработку события нажатия на кнопку "Назад".
     * Если выдвижная панель открыта, она закрывается; в противном случае, вызывается метод onBackPressed() суперкласса.
     */

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
            closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Метод, который открывает галерею устройства для выбора изображения пользователя.
     */

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    /**
     * Метод, который открывает активность FullscreenPhotoActivity, отображающую выбранное изображение в полноэкранном режиме.
     */

    private void openFullscreenPhoto() {
        SharedPreferences preferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        String savedFilePath = preferences.getString("user_photo_path", null);
        if (savedFilePath != null) {
            Intent intent = new Intent(MainActivity.this, FullscreenPhotoActivity.class);
            intent.putExtra("photo_path", savedFilePath);
            startActivity(intent);
        } else {
            Toast.makeText(MainActivity.this, "No photo selected", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Метод, который вызывается после завершения активности выбора изображения.
     * Здесь обрабатывается выбранное изображение, загружается на сервер и сохраняется путь к файлу фотографии.
     */

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

    /**
     * Метод, который загружает выбранное изображение на сервер и сохраняет путь к файлу фотографии.
     */

    private void uploadPhoto(Uri imageUri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(imageUri);
            File storageDir = getExternalFilesDir(null);
            File outputFile = new File(storageDir, "user_photo.jpg");

            FileOutputStream outputStream = new FileOutputStream(outputFile);

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            outputStream.close();
            inputStream.close();

            String filePath = outputFile.getAbsolutePath();
            saveUserPhoto(filePath);
            userPhoto.setImageURI(imageUri); // Отображаем новую фотографию
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, "Ошибка при сохранении фото", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Метод, который сохраняет путь к файлу фотографии в настройках приложения.
     */


    private void saveUserPhoto(String filePath) {
        // Сохранение пути к файлу фотографии в настройках приложения
        SharedPreferences preferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("user_photo_path", filePath);
        editor.apply();
    }

    /**
     * Метод, который загружает фотографию пользователя из сохраненного пути и отображает ее в элементе userPhoto.
     */


    private void loadUserPhoto() {
        SharedPreferences preferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        String savedFilePath = preferences.getString("user_photo_path", null);
        if (savedFilePath != null) {
            File photoFile = new File(savedFilePath);
            if (photoFile.exists()) {
                Uri photoUri = Uri.fromFile(photoFile);
                userPhoto.setImageURI(photoUri);
            }
        }
    }

    /**
     * Метод, который переключает тему приложения между светлой и темной.
     */


    private void toggleTheme() {
        int currentNightMode = getResources().getConfiguration().uiMode & android.content.res.Configuration.UI_MODE_NIGHT_MASK;
        if (currentNightMode == android.content.res.Configuration.UI_MODE_NIGHT_YES) {
            // Если текущая тема - темная, переключаем на светлую
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            // Если текущая тема - светлая, переключаем на темную
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
    }

    /**
     * Метод, который выполняет выход из учетной записи пользователя,
     * удаляет сохраненные данные пользователя и перенаправляет пользователя на экран авторизации.
     */

    private void logout() {
        // Удаляем сохраненные данные пользователя и переходим на экран авторизации
        SharedPreferences preferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.apply();

        loadUserPhoto();

        Intent intent = new Intent(MainActivity.this, Registration.class);
        startActivity(intent);
        finish();
    }

    /**
     * Метод, который вызывается при возобновлении активности.
     * Здесь происходит обновление фотографии пользователя при каждом отображении активности.
     */

    @Override
    protected void onResume() {
        super.onResume();
        loadUserPhoto(); // Обновление фотографии при каждом отображении активности
    }

    /**
     * Метод, который удаляет сохраненную фотографию пользователя,
     * обновляет соответствующий элемент пользовательского интерфейса
     * и загружает фотографию пользователя (или использует фото-заглушку).
     */
    private void deletePhoto() {
        SharedPreferences preferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove("user_photo_path");
        editor.apply();
        userPhoto.setImageResource(R.drawable.user_photo);

        loadUserPhoto();
    }


    /**
     * Метод, который загружает данные пользователя из базы данных Firebase и отображает имя пользователя в элементе textViewUsername.
     */


    // Метод для загрузки данных пользователя из базы данных
    private void loadUserData() {
        DatabaseReference userReference = databaseReference.child("Users").child(userId);

        DatabaseReference userRef = databaseReference.child("users").child(userId);
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String name = dataSnapshot.child("name").getValue(String.class);
                    if (name != null) {
                        textViewUsername.setText(name);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "Ошибка загрузки данных", Toast.LENGTH_SHORT).show();
            }
        });
    }


}



