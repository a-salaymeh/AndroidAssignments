package com.example.androidassignments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class chatWindow extends AppCompatActivity {
    protected static String ACTIVITY_NAME="activity_chat_window";


    ListView lstView;
    EditText et;
    Button btnChat;
    ArrayList<String> textMessage;
    ChatAddapter textAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

    }
    private class ChatAddapter extends ArrayAdapter<String>{

        public ChatAddapter(@NonNull Context context, int resource) {
            super(context, resource);
        }
        public int getCount(){
            int count=0;
            count=textMessage.size();
            return count;
        }
        public String getString(int index){
            String textStr;
            textStr=textMessage.get(index);
            return textStr;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(ACTIVITY_NAME,"In OnResume()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(ACTIVITY_NAME,"In onStart()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(ACTIVITY_NAME,"In onPause()");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(ACTIVITY_NAME,"In onStop()");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(ACTIVITY_NAME,"In onDestroy()");

    }
}