package com.example.last;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ChatAdapter chatAdapter;
    private EditText editTextMessage;
    private Button buttonSend;
    private List<ChatMessage> chatMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        recyclerView = findViewById(R.id.recyclerView);
        editTextMessage = findViewById(R.id.editTextMessage);
        buttonSend = findViewById(R.id.buttonSend);

        chatMessages = new ArrayList<>();
        chatAdapter = new ChatAdapter(chatMessages);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(chatAdapter);

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = editTextMessage.getText().toString().trim();
                if (!message.isEmpty()) {
                    ChatMessage chatMessage = new ChatMessage("Me", message);
                    chatMessages.add(chatMessage);
                    chatAdapter.notifyItemInserted(chatMessages.size() - 1);
                    recyclerView.scrollToPosition(chatMessages.size() - 1);
                    editTextMessage.setText("");
                }
            }
        }
        );
    }
}