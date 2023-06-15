package com.example.last;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class CreateGroupChatActivity extends AppCompatActivity {
    private EditText chatNameEditText;
    private Button selectMembersButton;
    private ArrayList<User> selectedMembers;

    private static final int REQUEST_CODE_MEMBER_SELECTION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group_chat);

        chatNameEditText = findViewById(R.id.chatNameEditText);
        selectMembersButton = findViewById(R.id.selectMembersButton);

        selectMembersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the MemberSelectionActivity to select members
                Intent intent = new Intent(CreateGroupChatActivity.this, MemberSelectionActivity.class);
                startActivityForResult(intent, REQUEST_CODE_MEMBER_SELECTION);
            }
        });

        Button createChatButton = findViewById(R.id.createChatButton);
        createChatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create the group chat with the provided information
                String chatName = chatNameEditText.getText().toString().trim();
                String chatAvatar = ""; // Set the chat avatar as per your implementation

                // Store the group chat information in Firebase
                DatabaseReference chatRef = FirebaseDatabase.getInstance().getReference().child("chats").push();
                String chatId = chatRef.getKey();

                // Create the group chat object
                GroupChat groupChat = new GroupChat(chatId, chatName, chatAvatar, selectedMembers);

                // Save the group chat object to Firebase
                chatRef.setValue(groupChat);

                // Open the chat activity with the created group chat
                Intent intent = new Intent(CreateGroupChatActivity.this, ChatActivity.class);
                intent.putExtra("chatId", chatId);
                startActivity(intent);
                finish();
            }
        });
    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_MEMBER_SELECTION && resultCode == RESULT_OK) {
            selectedMembers = data.getParcelableArrayListExtra("selectedMembers");

             }*/
    }

