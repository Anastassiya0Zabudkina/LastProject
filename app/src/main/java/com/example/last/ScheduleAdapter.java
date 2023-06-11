package com.example.last;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder> {

    private List<ScheduleItem> scheduleItems;
    private OnItemClickListener itemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        itemClickListener = listener;
    }

    public ScheduleAdapter(List<ScheduleItem> scheduleItems) {
        this.scheduleItems = scheduleItems;
    }

    @NonNull
    @Override
    public ScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_schedule, parent, false);
        return new ScheduleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleViewHolder holder, int position) {
        ScheduleItem scheduleItem = scheduleItems.get(position);
        holder.bind(scheduleItem);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(holder.getAdapterPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return scheduleItems.size();
    }

    public void setData(List<ScheduleItem> scheduleItems) {
        this.scheduleItems = scheduleItems;
        notifyDataSetChanged();
    }

    public class ScheduleViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView;
        private TextView timeTextView;
        private TextView locationTextView;
        private TextView enrollmentStatusTextView;


        public ScheduleViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
            locationTextView = itemView.findViewById(R.id.locationTextView);
            enrollmentStatusTextView = itemView.findViewById(R.id.enrollmentStatusTextView);
        }

        public void bind(ScheduleItem scheduleItem) {
            titleTextView.setText(scheduleItem.getTitle());
            timeTextView.setText(scheduleItem.getTime());
            locationTextView.setText(scheduleItem.getLocation());
            enrollmentStatusTextView.setText(scheduleItem.getStatus());
        }
    }
}




