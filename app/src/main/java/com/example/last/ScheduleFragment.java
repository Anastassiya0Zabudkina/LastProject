package com.example.last;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ScheduleFragment extends Fragment {

    private View view; // Поле класса для хранения представления фрагмента
    private RecyclerView recyclerView;
    private ScheduleAdapter adapter;
    private List<ScheduleItem> scheduleItems;
    private List<String> dates;
    private Button[] dateButtons;
    private TextView selectedDateTextView;

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
        data.add(new ScheduleItem("Занятие 1", "9:00 - 10:30", "Место 1"));
        data.add(new ScheduleItem("Занятие 2", "11:00 - 12:30", "Место 2"));
        data.add(new ScheduleItem("Занятие 3", "9:30 - 11:00", "Место 3"));
        // Добавьте остальные занятия
        data.add(new ScheduleItem("Занятие 4", "13:00 - 14:30", "Место 4"));
        data.add(new ScheduleItem("Занятие 5", "15:00 - 16:30", "Место 5"));
        data.add(new ScheduleItem("Занятие 6", "15:00 - 16:30", "Место 6"));

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
        ScheduleItem selectedSchedule = scheduleItems.get(index);

        // Обновление отображения выбранного занятия
        TextView titleTextView = view.findViewById(R.id.titleTextView);
        TextView timeTextView = view.findViewById(R.id.timeTextView);
        TextView locationTextView = view.findViewById(R.id.locationTextView);

        titleTextView.setText(selectedSchedule.getTitle());
        timeTextView.setText(selectedSchedule.getTime());
        locationTextView.setText(selectedSchedule.getLocation());
    }


    private void updateSchedule() {
        // Фильтрация расписания по выбранной дате
        List<ScheduleItem> filteredSchedule = new ArrayList<>();
        String selectedDate = selectedDateTextView.getText().toString();

        if (selectedDate != null) {
            for (ScheduleItem scheduleItem : scheduleItems) {
                if (selectedDate.equals(scheduleItem.getDate())) {
                    filteredSchedule.add(scheduleItem);
                }
            }
        }

        // Обновление данных адаптера
        adapter.setData(filteredSchedule);
    }
}

