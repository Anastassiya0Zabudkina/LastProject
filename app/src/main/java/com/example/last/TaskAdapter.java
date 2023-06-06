package com.example.last;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private List<HomeworkTask> taskList;

    public TaskAdapter(List<HomeworkTask> taskList) {
        this.taskList = taskList;
    }

    @Override
    public TaskViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder( TaskViewHolder holder, int position) {
        HomeworkTask task = taskList.get(position);
        holder.titleTextView.setText(task.getTitle());
        holder.dueDateTextView.setText(task.getDueDate());
        holder.descriptionTextView.setText(task.getDescription());
        holder.markTextView.setText(task.getMark());
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView dueDateTextView;
        TextView descriptionTextView;
        TextView markTextView;

        public TaskViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            dueDateTextView = itemView.findViewById(R.id.dueDateTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            markTextView = itemView.findViewById(R.id.markTextView);
        }
    }
}

