
package com.example.androidassignments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
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
    ArrayList<Integer> textID;
    ChatAdapter textAdapter;
    SQLiteDatabase db;
    ChatDatabaseHelper db_helper;
    FrameLayout fl;
    public static Boolean flag;
    Cursor cursor;
    Bundle bundle=new Bundle();
    public static String DATA_BUNDLE="DATA BUNDLE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_chat_window);

        btnChat = findViewById(R.id.sent);

        userText = findViewById(R.id.editText);

        chatView = findViewById(R.id.listChat);

        textMessage = new ArrayList<>();

        textID=new ArrayList<Integer>();

        db_helper=new ChatDatabaseHelper(this);
        db=db_helper.getWritableDatabase();
        String[] col={ChatDatabaseHelper.KEY_MESSAGE,ChatDatabaseHelper.KEY_ID};
        cursor = db.query(ChatDatabaseHelper.TABLE_NAME, col,null, null, null, null, null);
        textAdapter = new ChatAdapter(this);
        chatView.setAdapter(textAdapter);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Log.i(ACTIVITY_NAME,"SQL MESSAGE: "+ cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));

            textMessage.add(cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));

            textID.add(cursor.getInt(cursor.getColumnIndex(ChatDatabaseHelper.KEY_ID)));

            cursor.moveToNext();

        }


        for(int i=0; i<cursor.getColumnCount();i++){
            Log.i(ACTIVITY_NAME,cursor.getColumnName(i));
        }


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
        flag=false;
        fl=findViewById(R.id.lay_frame);
        if (fl !=null){
            flag=true;

        }
        chatView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("p: ",String.valueOf(position));
                Log.i("M: ",textAdapter.getString(position));
                String message=textAdapter.getString(position);

                bundle.putString(ChatDatabaseHelper.KEY_MESSAGE,message);
                bundle.putLong(ChatDatabaseHelper.KEY_ID,textAdapter.getItemId(position));

                if(flag){

                    MessageFragment fragment=new MessageFragment();
                    FragmentTransaction transaction= getSupportFragmentManager().beginTransaction();
                    fragment.setArguments(bundle);
                    transaction.replace(R.id.lay_frame,fragment);
                    transaction.commit();
                }else{
                    Intent intent=new Intent(chatWindow.this,MessageDetails.class);
                    intent.putExtra(DATA_BUNDLE,bundle);
                    startActivityForResult(intent,2);
                }
            }
        });

    }
    public void notifyUpdate(long value){
        db_helper=new ChatDatabaseHelper(this);
        db=db_helper.getWritableDatabase();
        textMessage = new ArrayList<>();
        textAdapter = new ChatAdapter(this); //class var
        chatView.setAdapter(textAdapter);
        String[] col={ChatDatabaseHelper.KEY_MESSAGE,ChatDatabaseHelper.KEY_ID};
        final Cursor cursor = db.query(ChatDatabaseHelper.TABLE_NAME, col,null, null, null, null, null);
        db.delete(ChatDatabaseHelper.TABLE_NAME,value+"="+ChatDatabaseHelper.KEY_ID,null);
        textAdapter.notifyDataSetChanged();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            textMessage.add(cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));
            cursor.moveToNext();
        }


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==2){
            Log.i("test:","if statement");
            assert data !=null;
            Bundle ID=data.getExtras().getBundle(MessageFragment.DATA_BACK);
            long value=ID.getLong("ID");
            db.delete(ChatDatabaseHelper.TABLE_NAME,value+"="+ChatDatabaseHelper.KEY_ID,null);
            Log.i("test:","text been deleted");
            textAdapter.notifyDataSetChanged();
            String[] col={ChatDatabaseHelper.KEY_MESSAGE,ChatDatabaseHelper.KEY_ID};
            cursor = db.query(ChatDatabaseHelper.TABLE_NAME, col,null, null, null, null, null);
            cursor.moveToFirst();
            textMessage = new ArrayList<>();
            while(!cursor.isAfterLast()){
                Log.i(ACTIVITY_NAME,"SQL MESSAGE"+ cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));
                textMessage.add(cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));
                textID.add(cursor.getInt(cursor.getColumnIndex(ChatDatabaseHelper.KEY_ID)));
                cursor.moveToNext();

            }


        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
        cursor.close();
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

        public long getItemId(int position){
            long ID_position;
            String[] col={ChatDatabaseHelper.KEY_MESSAGE,ChatDatabaseHelper.KEY_ID};
            cursor = db.query(ChatDatabaseHelper.TABLE_NAME, col,null, null, null, null, null);
            cursor.moveToPosition(position);
            ID_position=cursor.getLong(cursor.getColumnIndex(ChatDatabaseHelper.KEY_ID));
            return ID_position;
        }
    }


}
