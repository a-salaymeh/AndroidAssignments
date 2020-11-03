
package com.example.androidassignments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class chatWindow extends AppCompatActivity {
    protected static String ACTIVITY_NAME = "activity_chat_window";


    ListView chatView;
    EditText userText;
    Button btnChat;
    ArrayList<String> textMessage;
    ChatAdapter textAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        btnChat = findViewById(R.id.sent);
        userText = findViewById(R.id.editText);
        chatView = findViewById(R.id.listChat);
        textMessage = new ArrayList<>();


        textAdapter = new ChatAdapter(this);
        chatView.setAdapter(textAdapter);


        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textMessage.add(userText.getText().toString());
                textAdapter.notifyDataSetChanged();
                userText.setText("");
            }
        });


    }

    private class ChatAdapter extends ArrayAdapter<String> {

        public ChatAdapter(@NonNull Context context) {

            super(context, 0);
        }

        public int getCount() {
            return textMessage.size();
        }

        public String getString(int index) {

            return textMessage.get(index);
        }


        public View getView(int index, View convertView, ViewGroup parent) {
            LayoutInflater inflater = chatWindow.this.getLayoutInflater();

            View result;
            if (index % 2 == 0)
                result = inflater.inflate(R.layout.inchat, null);
            else
                result = inflater.inflate(R.layout.outgoing, null);

            TextView message = result.findViewById(R.id.messageText);
            message.setText(getItem(index));
            return result;

        }
    }
}
