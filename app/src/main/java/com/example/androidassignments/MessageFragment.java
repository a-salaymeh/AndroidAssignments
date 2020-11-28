package com.example.androidassignments;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class MessageFragment extends Fragment {

    Button btnD;
    Bundle bundle;
    public static String DATA_BACK="DATA BACK";
    Intent intent;
    TextView idView, msgeView;
    Cursor cursor;
    SQLiteDatabase db;
    ChatDatabaseHelper db_helper;
    long value;
    ArrayList<String> textMessage;


    public MessageFragment() {
        // Required empty public constructor



    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("flag: ",Boolean.toString(chatWindow.flag));
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_message, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        idView= view.findViewById(R.id.messageID);
        msgeView= view.findViewById(R.id.messageTxt);
        if(chatWindow.flag){
            MessageFragment fragment = new MessageFragment();

            bundle=getArguments();
            fragment.setArguments(bundle);
            btnD=view.findViewById(R.id.btnDelete);
            value=bundle.getLong(ChatDatabaseHelper.KEY_ID);

            idView.setText(Long.toString(value));

            msgeView.setText(bundle.getString(ChatDatabaseHelper.KEY_MESSAGE));
            intent = new Intent(getContext(), chatWindow.class);
            Bundle passBackBundle = new Bundle();
            passBackBundle.putLong("ID",value);

            intent.putExtra(DATA_BACK,passBackBundle);
            btnD.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(chatWindow.flag){
                        Log.i("test:","text been deleted");
                        ((chatWindow) getActivity()).notifyUpdate(value);

                    }else{
                        getActivity().setResult(2,intent);
                        getActivity().finish();
                    }


                }
            });

        }else{
            bundle = getActivity().getIntent().getExtras().getBundle(chatWindow.DATA_BUNDLE);

            btnD=view.findViewById(R.id.btnDelete);
            value=bundle.getLong(ChatDatabaseHelper.KEY_ID);

            idView.setText(Long.toString(value));

            msgeView.setText(bundle.getString(ChatDatabaseHelper.KEY_MESSAGE));
            intent = new Intent(getContext(), chatWindow.class);
            Bundle passBackBundle = new Bundle();
            passBackBundle.putLong("ID",value);

            intent.putExtra(DATA_BACK,passBackBundle);
            btnD.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(chatWindow.flag){
                        Log.i("test:","text been deleted");
                        ((chatWindow) getActivity()).notifyUpdate(value);

                    }else{
                        getActivity().setResult(2,intent);
                        getActivity().finish();
                    }


                }
            });

        }

    }
}