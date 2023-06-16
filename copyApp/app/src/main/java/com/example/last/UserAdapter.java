package com.example.last;

/**
 * Импорты:
 *
 * android.view.LayoutInflater: Класс, используемый для создания экземпляра макета из XML-файла.
 * android.view.View: Класс, представляющий базовый компонент пользовательского интерфейса.
 * android.view.ViewGroup: Класс, представляющий контейнер для размещения дочерних представлений.
 * android.widget.TextView: Класс, представляющий виджет текстового поля.
 * androidx.annotation.NonNull: Аннотация, указывающая, что параметр метода не может быть null.
 * androidx.recyclerview.widget.RecyclerView: Класс, представляющий виджет RecyclerView, используемый для отображения списков.
 * java.util.List: Интерфейс, представляющий список элементов.
 *
 * Поля:
 *
 * private List<User> userList: Список объектов User для отображения.
 * Методы:
 *
 * public UserAdapter(List<User> userList): Конструктор класса UserAdapter.
 * @NonNull
 * @Override
 * public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType): Метод, вызываемый при создании нового элемента списка.
 * @Override
 * public void onBindViewHolder(@NonNull UserViewHolder holder, int position): Метод, вызываемый для привязки данных пользователя к элементу списка.
 * @Override
 * public int getItemCount(): Метод, возвращающий количество элементов в списке.
 * public static class UserViewHolder extends RecyclerView.ViewHolder: Вложенный статический класс UserViewHolder, представляющий элемент списка.
 * public void bind(User user, int position): Метод для привязки данных пользователя к элементу списка.
 * Класс UserViewHolder:
 * Этот вложенный статический класс представляет элемент списка RecyclerView для отображения данных пользователя.
 *
 * Поля:
 *
 * private TextView textViewSerialNumber: Виджет TextView для отображения серийного номера пользователя.
 * private TextView textViewName: Виджет TextView для отображения имени пользователя.
 * private TextView textViewPhoneNumber: Виджет TextView для отображения номера телефона пользователя.
 * Методы:
 *
 * public UserViewHolder(@NonNull View itemView): Конструктор класса UserViewHolder.
 * public void bind(User user, int position): Метод для привязки данных пользователя к элементу списка.
 */

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

/**
 * Класс UserAdapter:
 * Этот класс является адаптером для связывания данных пользователя с RecyclerView и отображения элементов списка.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<User> userList;

    public UserAdapter(List<User> userList) {
        this.userList = userList;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.bind(user, position);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewSerialNumber;
        private TextView textViewName;
        private TextView textViewPhoneNumber;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewSerialNumber = itemView.findViewById(R.id.textViewSerialNumber);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewPhoneNumber = itemView.findViewById(R.id.textViewPhoneNumber);
        }

        public void bind(User user, int position) {
            textViewSerialNumber.setText(String.valueOf(position + 1));
            textViewName.setText(user.getName());
            textViewPhoneNumber.setText(user.getTelephone());
        }
    }
}

