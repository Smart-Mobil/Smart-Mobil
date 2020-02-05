package com.project.mobilapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.Map;

public class Input extends AppCompatActivity {
    private JsoupAsyncTask2 jsoupAsyncTask2;
    private TextView text;
    private ToggleButton toggleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        text=(TextView)findViewById(R.id.textView2);
  //      toggleButton = (ToggleButton) findViewById(R.id.toggleButton);
        jsoupAsyncTask2 = new JsoupAsyncTask2();
        jsoupAsyncTask2.execute();
/*
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    Toast.makeText(Input.this, "토글클릭-ON", Toast.LENGTH_SHORT).show();
                    jsoupAsyncTask1 = new JsoupAsyncTask1();
                    jsoupAsyncTask1.execute();
                } else {
                    Toast.makeText(Input.this, "토글클릭-OFF", Toast.LENGTH_SHORT).show();

                }
            }
        });*/
    }

    private class JsoupAsyncTask2 extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Connection.Response res = Jsoup.connect("http://172.30.1.8:3000/data")
                        .data("data", "hhihiihihihihhihihihihihihihihihihihihihihihihihihihihihihihihi")
                        .timeout(5000)
                        .maxBodySize(0)
                        .method(Connection.Method.POST)
                        .execute();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

        }
    }
}
