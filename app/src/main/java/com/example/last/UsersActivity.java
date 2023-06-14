package com.example.last;


/**
 * Импорты:
 *
 * android.os.Bundle: Класс, используемый для передачи данных между компонентами Android.
 * android.widget.Toast: Класс, используемый для отображения всплывающих сообщений.
 * androidx.annotation.NonNull: Аннотация, указывающая, что параметр метода не может быть null.
 * androidx.appcompat.app.AppCompatActivity: Базовый класс для создания активностей Android.
 * androidx.recyclerview.widget.LinearLayoutManager: Класс, представляющий менеджер компоновки RecyclerView с вертикальной прокруткой.
 * androidx.recyclerview.widget.RecyclerView: Класс, представляющий виджет RecyclerView, используемый для отображения списков.
 * com.google.firebase.database.DataSnapshot: Класс, представляющий снимок данных Firebase Realtime Database.
 * com.google.firebase.database.DatabaseError: Класс, представляющий ошибку базы данных Firebase Realtime Database.
 * com.google.firebase.database.FirebaseDatabase: Класс, представляющий базу данных Firebase Realtime Database.
 * com.google.firebase.database.Query: Класс, представляющий запрос к базе данных Firebase Realtime Database.
 * com.google.firebase.database.ValueEventListener: Интерфейс, используемый для прослушивания событий значения Firebase Realtime Database.
 * java.util.ArrayList: Класс, представляющий список элементов.
 * java.util.List: Интерфейс, представляющий список элементов.
 *
 * Поля:
 *
 * private RecyclerView recyclerView: Виджет RecyclerView для отображения списка пользователей.
 * private UserAdapter userAdapter: Адаптер для связывания данных пользователя с RecyclerView.
 * private List<User> userList: Список объектов User для отображения.
 * Методы:
 *
 * @Override
 * protected void onCreate(Bundle savedInstanceState): Метод, вызываемый при создании активности.
 * private void getUsersFromDatabase(): Метод для получения списка пользователей из базы данных Firebase Realtime Database.
 * Методы активности:
 *
 * @Override
 * protected void onCreate(Bundle savedInstanceState): Метод, вызываемый при создании активности.
 * super.onCreate(savedInstanceState): Вызов родительского метода onCreate.
 * setContentView(R.layout.activity_users): Установка макета для активности.
 * recyclerView = findViewById(R.id.recyclerViewUsers): Инициализация RecyclerView из макета.
 * recyclerView.setLayoutManager(new LinearLayoutManager(this)): Установка менеджера компоновки LinearLayoutManager для RecyclerView.
 * userList = new ArrayList<>(): Инициализация пустого списка пользователей.
 * userAdapter = new UserAdapter(userList): Инициализация адаптера UserAdapter с пустым списком пользователей.
 * recyclerView.setAdapter(userAdapter): Установка адаптера для RecyclerView.
 * getUsersFromDatabase(): Вызов метода для получения списка пользователей из базы данных Firebase Realtime Database.
 * Приватный метод getUsersFromDatabase():
 * Этот метод выполняет запрос к базе данных Firebase Realtime Database для получения списка пользователей и обновляет список пользователей и адаптер.
 *
 * Query query = FirebaseDatabase.getInstance().getReference().child("users"): Создание запроса к базе данных Firebase Realtime Database для получения данных из узла "users".
 * query.addValueEventListener(new ValueEventListener() { ... }): Установка прослушивателя значений для выполнения действий при изменении данных.
 * public void onDataChange(@NonNull DataSnapshot dataSnapshot) { ... }: Метод, вызываемый при изменении данных в базе данных.
 * userList.clear(): Очистка списка пользователей.
 * for (DataSnapshot snapshot : dataSnapshot.getChildren()) { ... }: Перебор всех дочерних снимков данных.
 * User user = snapshot.getValue(User.class): Преобразование снимка данных в объект User.
 * userList.add(user): Добавление пользователя в список.
 * userAdapter.notifyDataSetChanged(): Уведомление адаптера об изменении данных.
 * public void onCancelled(@NonNull DatabaseError databaseError) { ... }: Метод, вызываемый при возникновении ошибки в базе данных.
 * String errorMessage = databaseError.toException().getMessage(): Получение сообщения об ошибке.
 * Toast.makeText(UsersActivity.this, "Ошибка: " + errorMessage, Toast.LENGTH_SHORT).show(): Отображение всплывающего сообщения с ошибкой.
 */

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * Класс UsersActivity:
 * Этот класс является активностью Android для отображения списка пользователей из базы данных Firebase Realtime Database.
 */
public class UsersActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        recyclerView = findViewById(R.id.recyclerViewUsers);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        userList = new ArrayList<>();
        userAdapter = new UserAdapter(userList);
        recyclerView.setAdapter(userAdapter);

        // Запрос к базе данных для получения списка пользователей
        Query query = FirebaseDatabase.getInstance().getReference().child("users");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    userList.add(user);
                }
                userAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Обработка ошибки
                String errorMessage = databaseError.toException().getMessage();
                Toast.makeText(UsersActivity.this, "Ошибка: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
