package com.example.last;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    private String userId;
    private TextView textViewProfileName;
    private EditText editProfileTextName, editProfileTextPhone, editProfileTextPassword;
    private Button buttonProfileEditName, buttonProfileEditPhone, buttonProfileChangePassword, buttonUpdate;

    private DatabaseReference databaseReference;
    private static final String PREF_NAME = "login_pref";
    private static final String KEY_USER_ID = "user_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        userId = sharedPreferences.getString(KEY_USER_ID, "");

        textViewProfileName = findViewById(R.id.textViewProfileName);
        editProfileTextName = findViewById(R.id.editProfileTextName);
        editProfileTextPhone = findViewById(R.id.editProfileTextPhone);
        editProfileTextPassword = findViewById(R.id.editProfileTextPassword);
        buttonProfileEditName = findViewById(R.id.buttonProfileEditName);
        buttonProfileEditPhone = findViewById(R.id.buttonProfileEditPhone);
        buttonProfileChangePassword = findViewById(R.id.buttonProfileChangePassword);
        buttonUpdate = findViewById(R.id.buttonUpdate);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        // Загружаем данные пользователя
        loadUserData();

        // Обработчик нажатия кнопки "Изменить" для имени
        buttonProfileEditName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newName = editProfileTextName.getText().toString().trim();
                if (!TextUtils.isEmpty(newName)) {
                    updateName(newName);
                } else {
                    Toast.makeText(ProfileActivity.this, "Введите новое имя", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Обработчик нажатия кнопки "Изменить" для номера телефона
        buttonProfileEditPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newPhone = editProfileTextPhone.getText().toString().trim();
                if (!TextUtils.isEmpty(newPhone)) {
                    updatePhone(newPhone);
                } else {
                    Toast.makeText(ProfileActivity.this, "Введите новый номер телефона", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Обработчик нажатия кнопки "Изменить" для пароля
        buttonProfileChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String oldPassword = editProfileTextPassword.getText().toString().trim();
                if (!TextUtils.isEmpty(oldPassword)) {
                    // Проверяем старый пароль перед открытием формы изменения пароля
                    DatabaseReference userRef = databaseReference.child("users").child(userId);
                    userRef.child("password").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                String storedPassword = dataSnapshot.getValue(String.class);
                                if (storedPassword.equals(oldPassword)) {
                                    openChangePasswordActivity(userId);
                                } else {
                                    Toast.makeText(ProfileActivity.this, "Неправильный текущий пароль", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(ProfileActivity.this, "Ошибка при получении текущего пароля", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(ProfileActivity.this, "Ошибка при получении текущего пароля", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(ProfileActivity.this, "Введите текущий пароль", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Обработчик нажатия кнопки "Обновить данные"
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Здесь можно добавить код для обновления других данных пользователя
                Toast.makeText(ProfileActivity.this, "Данные обновлены", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }

    // Метод для загрузки данных пользователя из базы данных
    private void loadUserData() {
        DatabaseReference userRef = databaseReference.child("users").child(userId);
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String name = dataSnapshot.child("name").getValue(String.class);
                    String phone = dataSnapshot.child("telephone").getValue(String.class);
                    String password = dataSnapshot.child("password").getValue(String.class);
                    // Обновляем текстовые поля
                    editProfileTextName.setText(name);
                    editProfileTextPhone.setText(phone);
                    editProfileTextPassword.setText(password);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ProfileActivity.this, "Ошибка загрузки данных", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Метод для обновления имени пользователя в базе данных
    private void updateName(String newName) {
        DatabaseReference userRef = databaseReference.child("users").child(userId);
        userRef.child("name").setValue(newName)
                .addOnCompleteListener(ProfileActivity.this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ProfileActivity.this, "Имя обновлено", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ProfileActivity.this, "Ошибка обновления имени", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    // Метод для обновления номера телефона пользователя в базе данных
    private void updatePhone(String newPhone) {
        DatabaseReference userRef = databaseReference.child("users").child(userId);
        userRef.child("telephone").setValue(newPhone)
                .addOnCompleteListener(ProfileActivity.this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ProfileActivity.this, "Номер телефона обновлен", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ProfileActivity.this, "Ошибка обновления номера телефона", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    // Метод для открытия активности изменения пароля
    private void openChangePasswordActivity(String userId) {
        // Здесь можно добавить код для открытия активности изменения пароля
        Intent changePasswordIntent = new Intent(ProfileActivity.this, ChangePasswordActivity.class);
        startActivity(changePasswordIntent);
        Toast.makeText(ProfileActivity.this, "Открыта форма изменения пароля", Toast.LENGTH_SHORT).show();
    }
}


