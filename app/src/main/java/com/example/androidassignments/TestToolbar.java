package com.example.androidassignments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class TestToolbar extends AppCompatActivity {


    String str1;
    EditText userText;
    View v;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toolbar);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        str1="Option 1";
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "hello world!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu m){
        getMenuInflater().inflate(R.menu.toolbar_menu,m);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem mi){

        //mi.getItemId();
        switch(mi.getItemId()){
            case R.id.action_one:
                String baseCase="option 1";
                Log.d("toolbar","option 1 selected");
                Toolbar tbar=getWindow().getDecorView().findViewById((R.id.toolbar));
                Snackbar.make(tbar,str1,Snackbar.LENGTH_LONG).show();
                break;
            case R.id.option2:
                //Start an activity…
                AlertDialog.Builder builder= new AlertDialog.Builder(this);
                builder.setTitle("Do you want to exit? ");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog dialog=builder.create();
                dialog.show();
                Log.d("toolbar","option 2 selected");
                break;
            case R.id.option3:
                AlertDialog.Builder cBuilder= new AlertDialog.Builder(TestToolbar.this);
                cBuilder.setTitle("testing toolbar");
                LayoutInflater  inflater=this.getLayoutInflater();
                v =inflater.inflate(R.layout.content_test_toolbar,null);
                cBuilder.setView(v);
                cBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        userText = v.findViewById(R.id.option3Text);
                        str1 = userText.getText().toString();

                    }
                });
                AlertDialog d2=cBuilder.create();
                d2.show();
                break;
            case R.id.option4:
                Context context = getApplicationContext();
                CharSequence text = "Version 1.0 By Ahmad Salaymeh!";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
        }

        return  true;
    }


}