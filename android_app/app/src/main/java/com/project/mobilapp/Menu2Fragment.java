package com.project.mobilapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
public class Menu2Fragment extends AppCompatActivity {
    private JsoupAsyncTask2 jsoupAsyncTask2;
    private TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu2_fragment);

        text=(TextView)findViewById(R.id.textView2);
        jsoupAsyncTask2 = new JsoupAsyncTask2();
        jsoupAsyncTask2.execute();
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
