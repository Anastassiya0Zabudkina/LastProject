package com.example.last;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ChatActivity extends AppCompatActivity {
    private TextView chatIdTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        chatIdTextView = findViewById(R.id.chatIdTextView);

        String chatId = getIntent().getStringExtra("chatId");
        if (chatId != null) {
            chatIdTextView.setText("Chat ID: " + chatId);
        }
    }
}