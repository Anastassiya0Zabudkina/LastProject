package com.example.last;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HomeworkActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TaskAdapter taskAdapter;
    private List<HomeworkTask> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homework);

        taskList = new ArrayList<>();
        taskList.add(new HomeworkTask("Java project 1", "01-06-2023", "description", "passed" ));
        taskList.add(new HomeworkTask("Java project 2", "01-06-2023", "description", "passed"));
        taskList.add(new HomeworkTask("Java project 3", "01-06-2023", "description", "not passed"));


        // Set up RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        taskAdapter = new TaskAdapter(taskList);
        recyclerView.setAdapter(taskAdapter);
    }
}
