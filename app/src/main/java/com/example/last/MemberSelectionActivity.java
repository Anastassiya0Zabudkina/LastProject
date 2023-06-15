package com.example.last;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MemberSelectionActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MemberSelectionAdapter adapter;
    private List<User> userList;
    private ArrayList<User> selectedMembers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_selection);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        userList = new ArrayList<>();
        selectedMembers = new ArrayList<>();



        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("users");
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    if (user != null) {
                        userList.add(user);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
            }
        });

        adapter = new MemberSelectionAdapter((Context) MemberSelectionActivity.this, (ArrayList<User>) userList, selectedMembers);
        recyclerView.setAdapter(adapter);

        Button addButton = findViewById(R.id.addButton);
        /*addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add selected members to the group chat
                // You can pass the selectedMembers list back to the CreateGroupChatActivity
                Intent resultIntent = new Intent();
                ArrayList<? extends Parcelable> selectedMembersList = new ArrayList<>(selectedMembers);
                resultIntent.putParcelableArrayListExtra("selectedMembers", (ArrayList<? extends Parcelable>) selectedMembers);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });*/
    }
}
