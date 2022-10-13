package com.example.chatbot;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.internal.EverythingIsNonNull;

public class MainActivity extends AppCompatActivity {
    private RecyclerView chats;
    private EditText message;
    private FloatingActionButton send;
    private final String BOT_KEY = "bot";
    private final String USER_KEY = "user";
    private ArrayList<ChatsModal> chatsModalArrayList;
    private ChatAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chats = findViewById(R.id.idChats);
        message = findViewById(R.id.editMessage);
        send = findViewById(R.id.send);
        chatsModalArrayList = new ArrayList<>();
        adapter = new ChatAdapter(chatsModalArrayList, this);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        chats.setLayoutManager(manager);
        chats.setAdapter(adapter);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (message.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Enter a message", Toast.LENGTH_SHORT).show();
                }
                getResponse(message.getText().toString());
                message.setText("");
            }
        });
    }

    private void getResponse(String message) {
        chatsModalArrayList.add(new ChatsModal(message, USER_KEY));
        adapter.notifyDataSetChanged();
        String url = "http://api.brainshop.ai/get?bid=162398&key=bMYEB23vxB3aYnQk&uid=[uid]&msg=" + message;
        String baseUrl = "http://api.brainshop.ai/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);
        Call<MsgModal> call = retrofitApi.getMessage(url);
        call.enqueue(new Callback<MsgModal>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<MsgModal> call, Response<MsgModal> response) {
                if (response.isSuccessful()) {
                    MsgModal modal = response.body();
                    chatsModalArrayList.add(new ChatsModal(modal.getCnt(), BOT_KEY));
                    adapter.notifyDataSetChanged();
                }
            }

            @EverythingIsNonNull
            @Override
            public void onFailure(Call<MsgModal> call, Throwable t) {
                chatsModalArrayList.add(new ChatsModal("Failure occurred", BOT_KEY));
                adapter.notifyDataSetChanged();
            }
        });
    }
}