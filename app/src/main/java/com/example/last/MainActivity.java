package com.example.last;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private TextView textViewUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawerLayout);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        // Получение ссылки на NavigationView
        NavigationView navigationView = findViewById(R.id.navigationView);

        // Получение ссылки на HeaderView
        View headerView = navigationView.getHeaderView(0);

        // Найдите TextView внутри HeaderView
        textViewUsername = headerView.findViewById(R.id.username_textview);

        // Получаем переданное имя пользователя из активности входа
        String username = getIntent().getStringExtra("username");
        if (username != null) {
            textViewUsername.setText(username);
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
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
}





