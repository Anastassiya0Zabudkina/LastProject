package com.example.last;

/**
 * Импорты:
 *
 * android.os.Bundle: Класс, представляющий набор данных ключ-значение.
 * androidx.appcompat.app.AppCompatActivity: Класс, представляющий базовую активность в стиле Material Design.
 * androidx.recyclerview.widget.LinearLayoutManager: Класс, который располагает элементы в RecyclerView в виде линейного списка.
 * androidx.recyclerview.widget.RecyclerView: Класс, представляющий виджет RecyclerView, используемый для отображения списков.
 * java.util.ArrayList: Класс, реализующий динамический массив.
 * java.util.List: Интерфейс, представляющий список элементов.
 *
 * Поля:
 *
 * private RecyclerView recyclerView: Виджет RecyclerView для отображения списка преподавателей.
 * private TeacherAdapter teacherAdapter: Адаптер для связывания данных преподавателей с RecyclerView.
 * private List<Teacher> teacherList: Список объектов Teacher для отображения.
 * Методы:
 *
 * protected void onCreate(Bundle savedInstanceState): Метод, вызываемый при создании активности.
 * private void createTeacherList(): Метод для создания списка преподавателей.
 * private void updateAdapter(): Метод для обновления адаптера.
 * Методы активности:
 *
 * protected void onCreate(Bundle savedInstanceState): Метод, вызываемый при создании активности.
 * Метод onCreate(Bundle savedInstanceState):
 * Метод, вызываемый при создании активности. В этом методе инициализируются RecyclerView, TeacherAdapter и список преподавателей. Затем создается список преподавателей, а адаптер обновляется для отображения данных.
 */

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс TeachersActivity:
 * Этот класс является активностью, отображающей список преподавателей с помощью RecyclerView и TeacherAdapter.
 */

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
