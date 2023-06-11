package com.example.last;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScheduleFragment extends Fragment {

    private View view;
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

        recyclerView = view.findViewById(R.id.scheduleRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        Button buttonDate1 = view.findViewById(R.id.buttonDate1);
        Button buttonDate2 = view.findViewById(R.id.buttonDate2);
        Button buttonDate3 = view.findViewById(R.id.buttonDate3);
        Button buttonDate4 = view.findViewById(R.id.buttonDate4);
        Button buttonDate5 = view.findViewById(R.id.buttonDate5);
        Button buttonDate6 = view.findViewById(R.id.buttonDate6);

        dateButtons = new Button[]{buttonDate1, buttonDate2, buttonDate3, buttonDate4, buttonDate5, buttonDate6};
        selectedDateTextView = view.findViewById(R.id.titleTextView);

        dates = createDates();

        for (int i = 0; i < dateButtons.length; i++) {
            final int index = i;
            dateButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectDate(index);
                    updateSchedule();
                }
            });
        }

        scheduleItems = createScheduleData();
        createScheduleMap();

        adapter = new ScheduleAdapter(scheduleItems);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new ScheduleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ScheduleItem item = scheduleItems.get(position);
                adapter.setOnItemClickListener(new ScheduleAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        ScheduleItem item = scheduleItems.get(position);
                        showEnrollmentStatusDialog(item.getTitle(), item.getStatus(), position);
                    }
                });

            }
        });

        selectDate(0);
        updateSchedule();

        return view;
    }

    private void showEnrollmentStatusDialog(String course, String status, int position) {
        String message = "Курс \"" + course + "\".";

        if (status.equals("Offline")) {
            message += "\n\noffline";
        } else {
            message += "\n\nonline";
        }

        new MaterialAlertDialogBuilder(requireContext())
                .setTitle("Статус записи")
                .setMessage(message)
                .setPositiveButton("Записаться", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ScheduleItem item = scheduleItems.get(position);
                        item.setStatus("Записан");
                        adapter.notifyItemChanged(position);
                    }
                })
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }


    private List<ScheduleItem> createScheduleData() {
        List<ScheduleItem> data = new ArrayList<>();

        data.add(new ScheduleItem("Python", "15:00 - 17:00", "Offline", "Пн"));
        data.add(new ScheduleItem("Python", "17:00 - 19:00", "Offline", "Пн"));
        data.add(new ScheduleItem("Java", "15:00 - 17:00", "Offline", "Вт"));
        data.add(new ScheduleItem("Java", "17:00 - 19:00", "Offline", "Вт"));
        data.add(new ScheduleItem("Python", "15:00 - 17:00", "Offline", "Ср"));
        data.add(new ScheduleItem("Python", "17:00 - 19:00", "Offline", "Ср"));
        data.add(new ScheduleItem("Java", "15:00 - 17:00", "Offline", "Чт"));
        data.add(new ScheduleItem("Java", "17:00 - 19:00", "Offline", "Чт"));
        data.add(new ScheduleItem("Java", "17:00 - 19:00", "Online", "Пт"));
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
        return dates;
    }

    private void selectDate(int index) {
        String selectedDate = dates.get(index);
        selectedDateTextView.setText(selectedDate);
    }

    private void updateSchedule() {
        String selectedDate = selectedDateTextView.getText().toString();
        List<ScheduleItem> filteredSchedule = scheduleMap.get(selectedDate);
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



