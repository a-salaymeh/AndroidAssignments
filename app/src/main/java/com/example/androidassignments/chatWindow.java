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

public class chatWindow extends AppCompatActivity {
    protected static String ACTIVITY_NAME="activity_chat_window";

    int count=0;
    String chatStr;

    ListView lstView;
    EditText et;
    Button btnChat;
    private class ChatAddapter extends ArrayAdapter<String>{

        public ChatAddapter(@NonNull Context context, int resource) {
            super(context, resource);
        }
        public int getCount(){
            int i=0;
            return count;
        }
        public String getString(){
            return chatStr;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
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