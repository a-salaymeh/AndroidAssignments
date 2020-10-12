package com.example.androidassignments;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

public class list_items extends AppCompatActivity {
    protected static final String ACTIVITY_NAME="list_items";
    public static final int CAMERA=1;
    ImageButton btnCamera;
    Switch btnSwitch;
    CheckBox btnCheck;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);
        Log.i(ACTIVITY_NAME,"In OnCreate()");
        btnCamera=findViewById(R.id.imageButton);
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent listIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(listIntent,CAMERA);
            }
        });
    }
    protected void onActivityResult(int request_code,int result_code, Intent data) {
        super.onActivityResult(request_code, result_code, data);
        if(request_code==CAMERA && result_code==RESULT_OK){
            Bundle extras=data.getExtras();
            Bitmap imageBitmap=(Bitmap) extras.get("data");
            btnCamera.setImageBitmap(imageBitmap);
        }
    }
    public void setOnCheckedChanged(View v){
        btnSwitch=findViewById(R.id.switchID);
        CharSequence text = "";// "Switch is Off"
        int duration = Toast.LENGTH_SHORT; //= Toast.LENGTH_LONG if Off
        if(btnSwitch.isChecked()){
            text="Switch is on";
            duration=Toast.LENGTH_SHORT;
        }
        else if(!btnSwitch.isChecked()){
            text="Switch is off";
            duration=Toast.LENGTH_LONG;

        }
        Toast toast = Toast.makeText(this , text, duration); //this is the ListActivity
        toast.show(); //display your message box

    }

    public void OnCheckedChanged (View v){
        btnCheck=findViewById(R.id.checkBox);
        String dialog_message="";
        AlertDialog.Builder builder = new AlertDialog.Builder(list_items.this);
        // 2. Chain together various setter methods to set the dialog characteristics
        builder.setMessage(R.string.dialog_message) //Add a dialog message to strings.xml
                .setTitle(R.string.dialog_title)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("Response", "Here is my response");
                        setResult(Activity.RESULT_OK, resultIntent);
                        finish();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        dialog.dismiss();
                    }
                })
                .show();
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