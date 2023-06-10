package com.example.last;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScheduleFragment extends Fragment {

    private View view; // Поле класса для хранения представления фрагмента
    private RecyclerView recyclerView;
    private ScheduleAdapter adapter;
    private List<ScheduleItem> scheduleItems;
    private List<String> dates;
    private Button[] dateButtons;
    private TextView selectedDateTextView;
    private Map<String, List<ScheduleItem>> scheduleMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_schedule, container, false);

        // Инициализация RecyclerView
        recyclerView = view.findViewById(R.id.scheduleRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Инициализация кнопок дат
        Button buttonDate1 = view.findViewById(R.id.buttonDate1);
        Button buttonDate2 = view.findViewById(R.id.buttonDate2);
        Button buttonDate3 = view.findViewById(R.id.buttonDate3);
        Button buttonDate4 = view.findViewById(R.id.buttonDate4);
        Button buttonDate5 = view.findViewById(R.id.buttonDate5);
        Button buttonDate6 = view.findViewById(R.id.buttonDate6);

        dateButtons = new Button[]{buttonDate1, buttonDate2, buttonDate3, buttonDate4, buttonDate5, buttonDate6};
        selectedDateTextView = view.findViewById(R.id.titleTextView);


        // Создание списка дат
        dates = createDates();

        // Установка слушателей для кнопок дат
        for (int i = 0; i < dateButtons.length; i++) {
            final int index = i;
            dateButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Выбор даты и обновление отображения
                    selectDate(index);
                    updateSchedule();
                }
            });
        }

        // Создание списка данных для расписания
        scheduleItems = createScheduleData();

        // Создание карты расписания
        createScheduleMap();

        // Создание и установка адаптера
        adapter = new ScheduleAdapter(scheduleItems);
        recyclerView.setAdapter(adapter);

        // Изначально выбираем первую дату
        selectDate(0);
        updateSchedule();

        return view;
    }

    private List<ScheduleItem> createScheduleData() {
        List<ScheduleItem> data = new ArrayList<>();

        // Добавление данных расписания

        //Понедельник:
        data.add(new ScheduleItem("Python", "15:00 - 17:00", "Offline", "Пн"));
        data.add(new ScheduleItem("Python", "17:00 - 19:00", "Offline", "Пн"));
        //Вторник:
        data.add(new ScheduleItem("Java", "15:00 - 17:00", "Offline", "Вт"));
        data.add(new ScheduleItem("Java", "17:00 - 19:00", "Offline", "Вт"));
        //Среда:
        data.add(new ScheduleItem("Python", "15:00 - 17:00", "Offline", "Ср"));
        data.add(new ScheduleItem("Python", "17:00 - 19:00", "Offline", "Ср"));
        //Четверг:
        data.add(new ScheduleItem("Java", "15:00 - 17:00", "Offline", "Чт"));
        data.add(new ScheduleItem("Java", "17:00 - 19:00", "Offline", "Чт"));
        //Пятница:

        data.add(new ScheduleItem("Java", "17:00 - 19:00", "Online", "Пт"));
        //Суббота:
        data.add(new ScheduleItem("Java", "17:00 - 19:00", "Online", "Сб"));

        return data;
    }

    private List<String> createDates() {
        List<String> dates = new ArrayList<>();
        dates.add("Пн");
        dates.add("Вт");
        dates.add("Ср");
        dates.add("Чт");
        dates.add("Пт");
        dates.add("Сб");

        // Добавьте остальные даты
        return dates;
    }

    private void selectDate(int index) {
        // Отображение выбранного занятия
        String selectedDate = dates.get(index);
        selectedDateTextView.setText(selectedDate);
    }

    private void updateSchedule() {
        // Фильтрация расписания по выбранной дате
        String selectedDate = selectedDateTextView.getText().toString();
        List<ScheduleItem> filteredSchedule = scheduleMap.get(selectedDate);

        // Обновление данных адаптера
        adapter.setData(filteredSchedule);
    }

    private void createScheduleMap() {
        scheduleMap = new HashMap<>();

        for (String date : dates) {
            List<ScheduleItem> filteredSchedule = new ArrayList<>();

            for (ScheduleItem scheduleItem : scheduleItems) {
                if (date.equals(scheduleItem.getDate())) {
                    filteredSchedule.add(scheduleItem);
                }
            }

            scheduleMap.put(date, filteredSchedule);
        }
    }



}



