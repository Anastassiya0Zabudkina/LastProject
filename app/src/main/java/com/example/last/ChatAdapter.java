package com.example.last;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    private List<ChatMessage> chatMessages;
    public ChatAdapter(List<ChatMessage> chatMessages) {
        this.chatMessages = chatMessages;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chat_message, parent, false);
        return new ChatViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        ChatMessage chatMessage = chatMessages.get(position);
        holder.senderTextView.setText(chatMessage.getSender());
        holder.contentTextView.setText(chatMessage.getContent());
    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }

    public static class ChatViewHolder extends RecyclerView.ViewHolder {
        public TextView senderTextView;
        public TextView contentTextView;

        public ChatViewHolder(View view) {
            super(view);
            senderTextView = view.findViewById(R.id.senderTextView);
            contentTextView = view.findViewById(R.id.contentTextView);
        }
    }
}
