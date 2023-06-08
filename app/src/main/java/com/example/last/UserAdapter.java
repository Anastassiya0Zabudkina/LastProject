package com.example.last;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

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

