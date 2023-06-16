package com.example.last;

/**
 * Импорты:
 *
 * androidx.annotation.NonNull: Аннотация, указывающая, что аргумент, поле или возвращаемое значение не может быть равно null.
 * androidx.appcompat.app.AppCompatActivity: Базовый класс для активностей, использующих функции библиотеки поддержки AppCompat.
 * android.content.Intent: Класс, представляющий намерение для запуска активностей или служб.
 * android.content.SharedPreferences: Класс, который позволяет сохранять и извлекать данные настроек.
 * android.os.Bundle: Класс, используемый для передачи данных между компонентами Android.
 * Класс Registration:
 * Этот класс представляет активность регистрации в приложении.
 *
 * Поля:
 *
 * private EditText editTextUsername: Поле для ввода имени пользователя.
 * private EditText editTextPassword: Поле для ввода пароля.
 * private Button buttonLogin: Кнопка для выполнения входа.
 * private Button buttonRegister: Кнопка для перехода к процессу регистрации.
 * private CheckBox checkBoxRememberMe: Флажок для запоминания данных пользователя.
 * private DatabaseReference databaseReference: Ссылка на базу данных Firebase.
 * private SharedPreferences sharedPreferences: Объект для работы с настройками приложения.
 * private static final String PREF_NAME: Константа, определяющая имя файла настроек.
 * private static final String KEY_USERNAME: Константа, определяющая ключ для имени пользователя в настройках.
 * private static final String KEY_PASSWORD: Константа, определяющая ключ для пароля в настройках.
 * private static final String KEY_REMEMBER_ME: Константа, определяющая ключ для флажка "Запомнить меня" в настройках.
 * private static final String KEY_USER_ID: Константа, определяющая ключ для идентификатора пользователя в настройках.
 * SharedPreferences.Editor editor: Редактор для изменения настроек.
 */

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Registration extends AppCompatActivity {

    private EditText editTextUsername, editTextPassword;
    private Button buttonLogin, buttonRegister;
    private CheckBox checkBoxRememberMe;
    private DatabaseReference databaseReference;
    private SharedPreferences sharedPreferences;

    private static final String PREF_NAME = "login_pref";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_REMEMBER_ME = "remember_me";
    private static final String KEY_USER_ID = "user_id";

    SharedPreferences.Editor editor;

    /**
     * Метод, вызываемый при создании активности.
     * Инициализирует компоненты интерфейса и настройки.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonRegister = findViewById(R.id.buttonRegister);
        checkBoxRememberMe = findViewById(R.id.checkBoxRememberMe);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Registration.this, RegistrationContinue.class);
                startActivity(intent);
                finish();
            }
        });

        // Проверяем, сохранены ли данные пользователя
        boolean rememberMe = sharedPreferences.getBoolean(KEY_REMEMBER_ME, false);
        checkBoxRememberMe.setChecked(rememberMe);
        if (rememberMe) {
            String username = sharedPreferences.getString(KEY_USERNAME, "");
            String password = sharedPreferences.getString(KEY_PASSWORD, "");

            editTextUsername.setText(username);
            editTextPassword.setText(password);
        }
    }


    /**
     * Метод для выполнения входа пользователя.
     * Проверяет введенные данные и проверяет их на соответствие данным в базе данных Firebase.
     */

    private void login() {
        String name = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString().replaceAll("\\s", "");

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "Please enter name", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if user exists in the database
        databaseReference.child("users").orderByChild("name").equalTo(name).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                        String storedPassword = userSnapshot.child("password").getValue(String.class);
                        if (storedPassword.equals(password)) {
                            String userId = userSnapshot.child("userId").getValue(String.class);
                            editor.putString(KEY_USER_ID, userId);
                            editor.apply();
                            // Сохраняем данные пользователя, если выбрана опция "Запомнить меня"
                            if (checkBoxRememberMe.isChecked()) {
                                editor.putString(KEY_USERNAME, name);
                                editor.putString(KEY_PASSWORD, password);
                                editor.putBoolean(KEY_REMEMBER_ME, true);
                                editor.apply();
                            } else {
                                // Если опция не выбрана, очищаем сохраненные данные
                                editor.remove(KEY_USERNAME);
                                editor.remove(KEY_PASSWORD);
                                editor.remove(KEY_REMEMBER_ME);
                                editor.apply();
                            }

                            // Передаем имя пользователя в MainActivity
                            Intent intent = new Intent(Registration.this, MainActivity.class);
                            intent.putExtra("username", name);
                            startActivity(intent);
                            finish();
                            return;
                        }
                    }
                    Toast.makeText(Registration.this, "Wrong password", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Registration.this, "User not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Registration.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}









