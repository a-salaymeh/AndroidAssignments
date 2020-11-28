package com.example.androidassignments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MessageDetails extends AppCompatActivity {

  //  Button delete;

    //public static String DATA_BACK="DATA BACK";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_message_details);
        MessageFragment fragment = new MessageFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.phoneLay,fragment);
        transaction.commit();

 //       delete=findViewById(R.id.btnDelete);
//        delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//
//            public void onClick(View v) {
//                long value=bundle.getLong(ChatDatabaseHelper.KEY_ID);
//                Intent intent = new Intent(MessageDetails.this,chatWindow.class);
//
//                Bundle passBackBundle = new Bundle();
//
//                passBackBundle.putLong("ID",value);
//
//                intent.putExtra(DATA_BACK,passBackBundle);
//                setResult(2,intent);
//                finish();
//
//            }
//        });

    }

}
