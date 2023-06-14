package com.example.last;

/**
 * Этот класс реализует функционал изменения пароля пользователя в приложении.
 * При вызове метода changePassword(),
 * введенные пользователем значения проверяются на соответствие требованиям,
 * а затем происходит обновление пароля в базе данных Firebase.
 */


import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChangePasswordActivity extends AppCompatActivity {

    private String userId;
    private EditText editOldPassword, editNewPassword, editConfirmPassword;
    private Button buttonChangePassword;

    private DatabaseReference databaseReference;
    private static final String PREF_NAME = "login_pref";
    private static final String KEY_USER_ID = "user_id";

    /**
     * Метод, вызываемый при создании активности.
     * Здесь происходит инициализация и настройка элементов пользовательского интерфейса, чтение значения userId из SharedPreferences, инициализация полей класса, установка обработчика событий для кнопки изменения пароля и подготовка базы данных databaseReference для взаимодействия с Firebase.
     * savedInstanceState: Объект Bundle, который содержит ранее сохраненное состояние активности (если таковое было).
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_change);

        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        userId = sharedPreferences.getString(KEY_USER_ID, "");

        editOldPassword = findViewById(R.id.editOldPassword);
        editNewPassword = findViewById(R.id.editNewPassword);
        editConfirmPassword = findViewById(R.id.editConfirmPassword);
        buttonChangePassword = findViewById(R.id.buttonChangePassword);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        buttonChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePassword();
            }
        });
    }

    /**
     * Метод, вызываемый при нажатии на кнопку изменения пароля.
     * В этом методе выполняется проверка введенных значений старого пароля,
     * нового пароля и подтверждения нового пароля.
     * Если все значения введены корректно, то происходит обновление пароля в базе данных Firebase для текущего пользователя.
     */

    private void changePassword() {
        String oldPassword = editOldPassword.getText().toString().trim();
        String newPassword = editNewPassword.getText().toString().trim();
        String confirmPassword = editConfirmPassword.getText().toString().trim();

        if (TextUtils.isEmpty(oldPassword)) {
            Toast.makeText(this, "Введите текущий пароль", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(newPassword)) {
            Toast.makeText(this, "Введите новый пароль", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(confirmPassword)) {
            Toast.makeText(this, "Подтвердите новый пароль", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            Toast.makeText(this, "Новый пароль и подтверждение не совпадают", Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseReference userRef = databaseReference.child("users").child(userId);
        userRef.child("password").setValue(newPassword)
                .addOnCompleteListener(ChangePasswordActivity.this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ChangePasswordActivity.this, "Пароль успешно изменен", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(ChangePasswordActivity.this, "Ошибка изменения пароля", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
