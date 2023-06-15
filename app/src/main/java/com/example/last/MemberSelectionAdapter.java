package com.example.last;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MemberSelectionAdapter extends RecyclerView.Adapter<MemberSelectionAdapter.MemberSelectionViewHolder> {

    private Context context;
    private List<User> userList;
    private List<User> selectedMembers;

    public MemberSelectionAdapter(Context context, ArrayList<User> userList, ArrayList<User> selectedMembers) {
        this.context = context;
        this.userList = userList;
        this.selectedMembers = selectedMembers;
    }

    @NonNull
    @Override
    public MemberSelectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_member_selection, parent, false);
        return new MemberSelectionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberSelectionViewHolder holder, int position) {
        User user = userList.get(position);

        holder.checkBox.setText(user.getName());
        holder.checkBox.setChecked(selectedMembers.contains(user));

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selectedMembers.add(user);
                } else {
                    selectedMembers.remove(user);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class MemberSelectionViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;

        public MemberSelectionViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkbox_member);
        }
    }
}

