package com.example.last;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
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
                    // Обработка нажатия на элемент "Еще"
                    return true;
                default:
                    return false;
            }
        });
    }
}