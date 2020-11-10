
package com.example.androidassignments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
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
import android.database.Cursor;

import java.util.ArrayList;

public class chatWindow extends AppCompatActivity {
    protected static String ACTIVITY_NAME = "activity_chat_window";


    ListView chatView;
    EditText userText;
    Button btnChat;
    ArrayList<String> textMessage;
    ChatAdapter textAdapter;
    SQLiteDatabase db;
    ChatDatabaseHelper db_helper;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        btnChat = findViewById(R.id.sent);
        userText = findViewById(R.id.editText);
        chatView = findViewById(R.id.listChat);
        textMessage = new ArrayList<>();
        db_helper=new ChatDatabaseHelper(this);
        db=db_helper.getWritableDatabase();
        String[] col={ChatDatabaseHelper.KEY_MESSAGE};
        Cursor cursor = db.query(ChatDatabaseHelper.TABLE_NAME, col,null, null, null, null, null);


        textAdapter = new ChatAdapter(this);
        chatView.setAdapter(textAdapter);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Log.i(ACTIVITY_NAME,"SQL MESSAGE"+ cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));
            cursor.getColumnCount();
            textMessage.add(cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));
            cursor.moveToNext();
        }
        for(int i=0; i<cursor.getColumnCount();i++){
            Log.i(ACTIVITY_NAME,cursor.getColumnName(i));
        }
        cursor.close();


        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textMessage.add(userText.getText().toString());
                ContentValues Cale=new ContentValues();
                Cale.put(ChatDatabaseHelper.KEY_MESSAGE,userText.getText().toString());
                db.insert(ChatDatabaseHelper.TABLE_NAME,null,Cale);
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
            message.setText(getString(index));
            return result;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }
}
