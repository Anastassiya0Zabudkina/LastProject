package com.example.last;

/**
 * Импорты:
 *
 * android.view.LayoutInflater: Класс, используемый для создания макета пользовательского интерфейса из XML-файла.
 * android.view.View: Класс, представляющий компонент пользовательского интерфейса.
 * android.view.ViewGroup: Класс, представляющий контейнер для компонентов пользовательского интерфейса.
 * android.widget.TextView: Класс, представляющий текстовое поле пользовательского интерфейса.
 * androidx.annotation.NonNull: Аннотация, указывающая, что аргумент, поле или возвращаемое значение не может быть равно null.
 * androidx.recyclerview.widget.RecyclerView: Класс, представляющий виджет RecyclerView, используемый для отображения списков.
 * Класс ScheduleAdapter:
 * Этот класс является адаптером для отображения списка элементов расписания в RecyclerView.
 *
 * Поля:
 *
 * private List<ScheduleItem> scheduleItems: Список элементов расписания.
 * private OnItemClickListener itemClickListener: Слушатель события нажатия на элемент списка.
 * Интерфейс OnItemClickListener:
 * Интерфейс, определяющий метод onItemClick, который будет вызываться при нажатии на элемент списка.
 *
 * Методы:
 *
 * public void setOnItemClickListener(OnItemClickListener listener): Метод для установки слушателя события нажатия на элемент списка.
 * public ScheduleAdapter(List<ScheduleItem> scheduleItems): Конструктор класса ScheduleAdapter, принимающий список элементов расписания.
 * @NonNull public ScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType): Метод, вызываемый при создании нового ViewHolder для элемента списка. Создает новый объект ScheduleViewHolder.
 * public void onBindViewHolder(@NonNull ScheduleViewHolder holder, int position): Метод, вызываемый при привязке данных к ViewHolder. Устанавливает значения полей TextView на основе данных элемента расписания.
 * public int getItemCount(): Метод, возвращающий количество элементов в списке расписания.
 * public void setData(List<ScheduleItem> scheduleItems): Метод для обновления данных списка элементов расписания и уведомления адаптера об изменениях.
 * Класс ScheduleViewHolder:
 * Вложенный класс ScheduleViewHolder, представляющий ViewHolder для элемента списка расписания.
 *
 * Поля:
 *
 * private TextView titleTextView: Текстовое поле для отображения заголовка элемента расписания.
 * private TextView timeTextView: Текстовое поле для отображения времени элемента расписания.
 * private TextView locationTextView: Текстовое поле для отображения местоположения элемента расписания.
 * private TextView enrollmentStatusTextView: Текстовое поле для отображения статуса записи на элемент расписания.
 * Методы:
 *
 * public ScheduleViewHolder(@NonNull View itemView): Конструктор класса ScheduleViewHolder, принимающий представление элемента списка расписания.
 * public void bind(ScheduleItem scheduleItem): Метод, связывающий данные элемента расписания с соответствующими полями в ViewHolder. Устанавливает значения полей TextView на основе данных элемента расписания.
 */

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
    public ScheduleItem getItem(int position) {
        return scheduleItems.get(position);
    }
}




