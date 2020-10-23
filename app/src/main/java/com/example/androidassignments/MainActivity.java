package com.example.androidassignments;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    protected static String ACTIVITY_NAME="MainActivity";

    Button btnRef;
    Button btnChat;
    public static final int LIST_VIEW=10;
    public static final int CHAT_VIEW=10;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnRef=findViewById(R.id.mainButton);
        btnRef.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                Intent listIntent=new Intent(MainActivity.this,list_items.class);
                startActivityForResult(listIntent,LIST_VIEW);
            }

        });
        btnChat=findViewById(R.id.chat);
        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chatIntent=new Intent(MainActivity.this,chatWindow.class);
            }
        });


    }
    protected void onActivityResult(int request_code, int response_code, Intent data) {
        super.onActivityResult(request_code, response_code, data);
        if(request_code==LIST_VIEW){
            Log.i(ACTIVITY_NAME,"Go Back to main page");
        }
        if(response_code==Activity.RESULT_OK){
            String msgPassed=data.getStringExtra("my information to share");
            Toast toast = Toast.makeText(this, "list_items passed: " + msgPassed, Toast.LENGTH_LONG);
            toast.show();
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