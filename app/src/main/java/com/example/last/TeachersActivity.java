package com.example.last;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class TeachersActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TeacherAdapter teacherAdapter;
    private List<Teacher> teacherList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachers);

        recyclerView = findViewById(R.id.recyclerViewTeachers);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        teacherList = new ArrayList<>();
        teacherAdapter = new TeacherAdapter(teacherList);
        recyclerView.setAdapter(teacherAdapter);

        // Создание списка преподавателей
        teacherList.add(new Teacher("Крутиков Илья   ", "+77772145121"));
        teacherList.add(new Teacher("Берик Умаров   ", "+77012188153"));


        // Обновление адаптера
        teacherAdapter.notifyDataSetChanged();
    }
}
