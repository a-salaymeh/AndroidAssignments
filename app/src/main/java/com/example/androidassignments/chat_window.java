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

import org.w3c.dom.Text;

import java.util.ArrayList;

public class chat_window extends AppCompatActivity {

    ListView chatView;
    EditText userMessage;
    Button  btnUser;
    ArrayList<String> textMessage;
    ChatAdapter textAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        btnUser=findViewById(R.id.sent);
        userMessage=findViewById(R.id.usertext);
        chatView=findViewById(R.id.chatbox);
        textMessage=new ArrayList<String>();

        textAdapter= new ChatAdapter(this);
        chatView.setAdapter(textAdapter);


        btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String value= userMessage.getText().toString();
                Log.w("here",value);
                textMessage.add(value);
                Log.w("array", textMessage.get(0));
                textAdapter.notifyDataSetChanged();
                userMessage.setText("");
            }
        });


    }

    private class ChatAdapter extends ArrayAdapter<String>{
        public ChatAdapter(@NonNull Context context){
            super(context,0);
        }
        public int getCount(){
            return textMessage.size();
        }
        public String getItem(int position){
            return textMessage.get(position);
        }
        public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater= chat_window.this.getLayoutInflater();
            View result;
            if (position % 2 == 0){
                result = inflater.inflate(R.layout.chat_incoming, null);
            }else {
                result = inflater.inflate(R.layout.chat_out_going, null);
            }
            TextView message=result.findViewById(R.id.messageText);
            message.setText(getItem(position));

            return result;
        }
    }
}