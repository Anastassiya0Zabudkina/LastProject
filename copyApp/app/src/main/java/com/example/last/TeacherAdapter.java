package com.example.last;

/**
 * Импорты:
 *
 * android.view.LayoutInflater: Класс, используемый для создания макета пользовательского интерфейса из XML-файла.
 * android.view.View: Класс, представляющий компонент пользовательского интерфейса.
 * android.view.ViewGroup: Класс, представляющий контейнер для компонентов пользовательского интерфейса.
 * android.widget.TextView: Класс, представляющий текстовое поле пользовательского интерфейса.
 * androidx.annotation.NonNull: Аннотация, указывающая, что аргумент метода не может быть null.
 * androidx.recyclerview.widget.RecyclerView: Класс, представляющий виджет RecyclerView, используемый для отображения списков.
 * java.util.List: Интерфейс, представляющий список элементов.
 *
 *
 * Поля:
 *
 * private List<Teacher> teacherList: Список объектов Teacher для отображения.
 * Конструкторы:
 *
 * public TeacherAdapter(List<Teacher> teacherList): Конструктор класса, принимающий список объектов Teacher.
 * Методы:
 *
 * @NonNull
 * public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType): Метод, вызываемый при создании нового экземпляра ViewHolder.
 * public void onBindViewHolder(@NonNull ViewHolder holder, int position): Метод, вызываемый для связывания данных учителя с ViewHolder.
 * public int getItemCount(): Метод, возвращающий общее количество элементов в списке.
 * public class ViewHolder extends RecyclerView.ViewHolder: Внутренний класс ViewHolder, представляющий элемент списка в RecyclerView.
 * Класс ViewHolder:
 * Внутренний класс ViewHolder, представляющий элемент списка в RecyclerView.
 *
 * Поля:
 *
 * public TextView textViewFullName: Текстовое поле для отображения полного имени учителя.
 * public TextView textViewPhoneNumber: Текстовое поле для отображения номера телефона учителя.
 * Конструкторы:
 *
 * public ViewHolder(@NonNull View itemView): Конструктор класса, принимающий представление элемента списка.
 */

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

/**
 * Класс TeacherAdapter:
 * Этот класс является адаптером для связывания данных учителей с RecyclerView.
 */

public class TeacherAdapter extends RecyclerView.Adapter<TeacherAdapter.ViewHolder> {

    private List<Teacher> teacherList;

    public TeacherAdapter(List<Teacher> teacherList) {
        this.teacherList = teacherList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_teacher, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Teacher teacher = teacherList.get(position);
        holder.textViewFullName.setText(teacher.getFullName());
        holder.textViewPhoneNumber.setText(teacher.getPhoneNumber());
    }

    @Override
    public int getItemCount() {
        return teacherList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewFullName;
        public TextView textViewPhoneNumber;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewFullName = itemView.findViewById(R.id.textViewFullName);
            textViewPhoneNumber = itemView.findViewById(R.id.textViewPhoneNumber);
        }
    }
}

