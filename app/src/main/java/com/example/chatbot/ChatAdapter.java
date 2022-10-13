package com.example.chatbot;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter {
    private ArrayList<ChatsModal> chatsModalArrayList;
    private Context context;

    public ChatAdapter(ArrayList<ChatsModal> chatsModalArrayList, Context context) {
        this.chatsModalArrayList = chatsModalArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case 0:
                view = LayoutInflater.from(context).inflate(R.layout.user_msg_item, parent, false);
                return new UserViewHolder(view);
            case 1:
                view = LayoutInflater.from(context).inflate(R.layout.bot_msg_item, parent, false);
                return new BotViewHolder(view);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        switch (chatsModalArrayList.get(position).getSender()) {
            case "user":
                return 0;
            case "bot":
                return 1;
            default:
                return -1;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChatsModal chatsModal = chatsModalArrayList.get(position);
        switch (chatsModal.getSender()) {
            case "user":
                ((UserViewHolder) holder).userText.setText(chatsModal.getMessage());
                break;
            case "bot":
                ((BotViewHolder) holder).botText.setText(chatsModal.getMessage());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return chatsModalArrayList.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView userText;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            userText = itemView.findViewById(R.id.idTVUser);
        }
    }

    public static class BotViewHolder extends RecyclerView.ViewHolder {
        TextView botText;

        public BotViewHolder(@NonNull View itemView) {
            super(itemView);
            botText = itemView.findViewById(R.id.idTVBot);
        }
    }

}
