package com.example.last;

/**
 * Импорты:
 *
 * androidx.annotation.NonNull: Аннотация, указывающая, что аргумент, поле или возвращаемое значение не может быть равно null.
 * androidx.appcompat.app.AppCompatActivity: Базовый класс для активностей, использующих функции библиотеки поддержки AppCompat.
 * android.content.Intent: Класс, представляющий намерение для запуска активностей или служб.
 * android.os.Bundle: Класс, используемый для передачи данных между компонентами Android.
 * android.util.Log: Класс, используемый для вывода отладочных сообщений в системный журнал.
 * android.view.View: Класс, представляющий компонент пользовательского интерфейса.
 * android.widget.Button: Класс, представляющий кнопку пользовательского интерфейса.
 * android.widget.EditText: Класс, представляющий поле ввода текста.
 * android.widget.Toast: Класс, используемый для отображения всплывающих сообщений.
 *
 * Поля:
 *
 * private EditText mEditFN: Поле для ввода имени пользователя.
 * private EditText mEditTelephone: Поле для ввода номера телефона пользователя.
 * private EditText mEditPassword: Поле для ввода пароля пользователя.
 * private Button mButtonSave: Кнопка для сохранения данных пользователя.
 * private DatabaseReference users: Ссылка на базу данных Firebase для работы с таблицей "users".
 * FirebaseDatabase db: Ссылка на объект базы данных Firebase.
 */

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * Класс RegistrationContinue:
 * Этот класс представляет активность продолжения регистрации в приложении.
 */

public class RegistrationContinue extends AppCompatActivity {

    private EditText mEditFN;
    private EditText mEditTelephone;
    private EditText mEditPassword;
    private Button mButtonSave;

    private DatabaseReference users;
    FirebaseDatabase db;

    /**
     * Метод, вызываемый при создании активности.
     * Инициализирует компоненты интерфейса и устанавливает обработчики событий.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_continue);

        mEditFN = findViewById(R.id.editFN);
        mEditTelephone = findViewById(R.id.editTelephone);
        mEditPassword = findViewById(R.id.editTextPassword);
        mButtonSave = findViewById(R.id.buttonSave);

        db = FirebaseDatabase.getInstance("https://endingproject2023-default-rtdb.europe-west1.firebasedatabase.app/");
        users = db.getReference("users");


        /**
         * Метод, вызываемый при нажатии на кнопку "Сохранить".
         * Собирает данные из полей ввода, проверяет их на валидность и сохраняет в базе данных Firebase.
         */
        mButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mEditFN.getText().toString().trim();
                String telephone = mEditTelephone.getText().toString().trim();
                String password = mEditPassword.getText().toString().trim();

                if (name.isEmpty() || telephone.isEmpty() || password.isEmpty()) {
                    Toast.makeText(RegistrationContinue.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                /**
                 *  Метод, вызываемый при изменении данных в таблице "users" базы данных Firebase.
                 *  Проверяет наличие пользователя с введенным номером телефона.
                 */

                Query query = users.orderByChild("telephone").equalTo(telephone);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            Toast.makeText(RegistrationContinue.this, "User with this telephone already exists", Toast.LENGTH_SHORT).show();
                        } else {
                            User user = new User(name, telephone, password);

                            String userId = users.push().getKey(); // Получаем уникальный идентификатор (userId)
                            user.setUserId(userId); // Устанавливаем уникальный идентификатор для пользователя

// Сохраняем пользователя в базе данных Firebase
                            users.child(userId).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(RegistrationContinue.this, "User created successfully", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(RegistrationContinue.this, Registration.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(RegistrationContinue.this, "Failed to create user", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }

                    /**
                     * Метод, вызываемый при ошибке чтения данных из базы данных Firebase.
                     */

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e("RegistrationContinue", "Database error: " + error.getMessage());
                    }
                });
            }
        });
    }
}