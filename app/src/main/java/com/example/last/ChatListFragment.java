package com.example.last;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatListFragment extends Fragment {
    private RecyclerView recyclerView;
    private EditText searchBar;
    private Button createGroupChatButton;
    private ChatListAdapter adapter;
    private List<Chat> chatList;
    private List<Chat> filteredChatList;
    private DatabaseReference chatRef;
    private ChildEventListener chatEventListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_list, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        searchBar = view.findViewById(R.id.searchBar);
        createGroupChatButton = view.findViewById(R.id.createGroupChatButton);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        chatList = new ArrayList<>();
        filteredChatList = new ArrayList<>();
        adapter = new ChatListAdapter(filteredChatList);
        recyclerView.setAdapter(adapter);

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filterChatList(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        createGroupChatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createGroupChat();
            }
        });

        setupChatListener();

        return view;
    }

    private void setupChatListener() {
        chatRef = FirebaseDatabase.getInstance().getReference().child("chats");
        chatEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String previousChildName) {
                Chat chat = dataSnapshot.getValue(Chat.class);
                chatList.add(chat);
                filteredChatList.add(chat);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, String previousChildName) {
                Chat updatedChat = dataSnapshot.getValue(Chat.class);
                int index = -1;
                for (int i = 0; i < chatList.size(); i++) {
                    if (chatList.get(i).getId().equals(updatedChat.getId())) {
                        index = i;
                        break;
                    }
                }
                if (index != -1) {
                    chatList.set(index, updatedChat);
                    filteredChatList.set(index, updatedChat);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Chat removedChat = dataSnapshot.getValue(Chat.class);
                int index = -1;
                for (int i = 0; i < chatList.size(); i++) {
                    if (chatList.get(i).getId().equals(removedChat.getId())) {
                        index = i;
                        break;
                    }
                }
                if (index != -1) {
                    chatList.remove(index);
                    filteredChatList.remove(index);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, String previousChildName) {}

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        };
        chatRef.addChildEventListener(chatEventListener);
    }

    private void filterChatList(String query) {
        filteredChatList.clear();
        if (TextUtils.isEmpty(query)) {
            filteredChatList.addAll(chatList);
        } else {
            for (Chat chat : chatList) {
                if (chat.getTitle().toLowerCase().contains(query.toLowerCase())) {
                    filteredChatList.add(chat);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }

    private void createGroupChat() {
        // Logic to create a new group chat
        // Generate a unique chat ID
        String chatId = generateChatId();

        // Retrieve necessary data from your Firebase Realtime Database
        // and create the chat object
        String chatTitle = "Group Chat";
        List<String> participants = new ArrayList<>();
        participants.add("user1"); // Example participant
        participants.add("user2"); // Example participant

        // Create the chat object
        Chat chat = new Chat(chatId, chatTitle, participants);

        // Store the chat object in your Firebase Realtime Database
        chatRef.child(chatId).setValue(chat);

        // Start the chat activity with the created chat
        startChatActivity(chat);
    }

    private void startChatActivity(Chat chat) {
        Intent intent = new Intent(getActivity(), ChatActivity.class);
        intent.putExtra("chatId", chat.getId());
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (chatRef != null && chatEventListener != null) {
            chatRef.removeEventListener(chatEventListener);
        }
    }

    private class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ChatViewHolder> {
        private List<Chat> chatList;

        public ChatListAdapter(List<Chat> chatList) {
            this.chatList = chatList;
        }

        @NonNull
        @Override
        public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat, parent, false);
            return new ChatViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
            Chat chat = chatList.get(position);
            holder.bind(chat);
        }

        @Override
        public int getItemCount() {
            return chatList.size();
        }

        public class ChatViewHolder extends RecyclerView.ViewHolder {
            private TextView chatTitleTextView;

            public ChatViewHolder(@NonNull View itemView) {
                super(itemView);
                chatTitleTextView = itemView.findViewById(R.id.chatTitleTextView);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            Chat clickedChat = chatList.get(position);
                            startChatActivity(clickedChat);
                        }
                    }
                });
            }

            public void bind(Chat chat) {
                chatTitleTextView.setText(chat.getTitle());
            }
        }
    }

    private String generateChatId() {
        // Generate a unique chat ID based on your preferred logic
        // For example, you can use a combination of timestamp and a unique identifier
        String timestamp = String.valueOf(System.currentTimeMillis());
        String uniqueId = "your_unique_id_here"; // Replace with your unique identifier
        return timestamp + "_" + uniqueId;
    }
    private void startNewChat() {
        // Open a new activity or fragment for creating a new chat
        Intent intent = new Intent(getActivity(), ChatActivity.class);
        startActivity(intent);
    }
}

