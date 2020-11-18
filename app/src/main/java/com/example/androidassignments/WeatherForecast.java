package com.example.androidassignments;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class WeatherForecast extends AppCompatActivity {
    protected static String ACTIVITY_NAME="weatherActivity";


    ProgressBar prgBar;
    TextView current_temp;
    TextView min_temp;
    TextView max_temp;
    TextView wind_speed;
    TextView cityName;
    ImageView imageView;
    List<String> cityList;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);
        prgBar= findViewById(R.id.weatherBar);
        current_temp=findViewById(R.id.currentTemperature);
        max_temp=findViewById(R.id.maxTemp);
        min_temp=findViewById(R.id.minTemp);
        cityName=findViewById(R.id.cityName);
        imageView=findViewById(R.id.imageWeather);
        prgBar.setVisibility(View.VISIBLE);
        get_a_city();


    }

    private void get_a_city() {
        cityList = Arrays.asList(getResources().getStringArray(R.array.cities));
        final Spinner citySpinner = findViewById(R.id.spinnerCity);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.cities, android.R.layout.simple_spinner_dropdown_item);
        citySpinner.setAdapter(adapter);

        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    new ForecastQuery(cityList.get(position)).execute();
                    cityName.setText(String.format("%s", cityList.get(position)));
                }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
    }


        public class ForecastQuery extends AsyncTask<String,Integer,String> {
            protected final String city;
            private String minTemp, maxTemp, currentTemp;
            private Bitmap picture;

            public ForecastQuery(String s) {
                this.city = s;
            }

            @Override
            protected String doInBackground(String... strings) {
                try{
                    URL url = new URL("https://api.openweathermap.org/" +
                            "data/2.5/weather?q=" + this.city  +"," +"ca&APPID=79cecf493cb6e52d25bb7b7050ff723c&" +"mode=xml&units=metric");

                    HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
                    conn.setReadTimeout(10000);
                    conn.setConnectTimeout(15000);
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream in = conn.getInputStream();

                    try{
                        XmlPullParser parser = Xml.newPullParser();
                        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                        parser.setInput(in, null);
                        int type;
                        while ((type = parser.getEventType()) != XmlPullParser.END_DOCUMENT) {
                            if (parser.getEventType() == XmlPullParser.START_TAG) {
                                if (parser.getName().equals("temperature")) {
                                    publishProgress(25);
                                    currentTemp = parser.getAttributeValue(null, "value");
                                    publishProgress(50);
                                    minTemp = parser.getAttributeValue(null, "min");
                                    publishProgress(75);
                                    maxTemp = parser.getAttributeValue(null, "max");
                                }

                                if(parser.getName().equals("weather")){
                                    String iconName = parser.getAttributeValue(null,"icon");
                                    String fileName = iconName + ".png";
                                    Log.i(ACTIVITY_NAME, "Looking for file: " + fileName);

                                    if(fileExistance(fileName)){
                                        FileInputStream fis = null;
                                        try {
                                            fis = openFileInput(fileName);

                                        }catch (FileNotFoundException e){
                                            e.printStackTrace();
                                        }
                                        Log.i(ACTIVITY_NAME, "Found the file locally");
                                        picture = BitmapFactory.decodeStream(fis);
                                    }

                                    String iconURL ="https://openweathermap.org/img/w/" + fileName;
                                    picture = getImage(new URL(iconURL));
                                    FileOutputStream outputStream = openFileOutput( fileName, Context.MODE_PRIVATE);
                                    picture.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                                    Log.i(ACTIVITY_NAME, "Downloaded the file from the Internet");
                                    outputStream.flush();
                                    outputStream.close();

                                }
                                publishProgress(100);
                            }
                            parser.next();
                        }


                    }catch (Exception e){
                        e.printStackTrace();
                    }finally {
                        in.close();
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }

                return "";
            }

            public boolean fileExistance(String fName) {
                File file = getBaseContext().getFileStreamPath(fName);
                return file.exists();
            }

            public Bitmap getImage(URL url) {

                HttpsURLConnection connection = null;
                try {
                    connection = (HttpsURLConnection) url.openConnection();
                    connection.connect();
                    int responseCode = connection.getResponseCode();
                    if (responseCode == 200) {
                        return BitmapFactory.decodeStream(connection.getInputStream());
                    } else
                        return null;
                } catch (Exception e) {
                    return null;
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
                prgBar.setProgress(values[0]);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                prgBar.setVisibility(View.INVISIBLE);
                imageView.setImageBitmap(picture);
                current_temp.setText(String.format("Current Temperature: %s°C", currentTemp));
                max_temp.setText(String.format("Max Temperature: %s°C", maxTemp));
                min_temp.setText(String.format("Min Temperature: %s°C", minTemp));


            }
        }
}