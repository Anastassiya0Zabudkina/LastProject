package com.example.last;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

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

