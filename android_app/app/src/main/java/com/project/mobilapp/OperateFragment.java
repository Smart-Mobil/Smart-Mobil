package com.project.mobilapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
public class OperateFragment extends Fragment {
    private JsoupAsyncTask2 jsoupAsyncTask2;
    private TextView text;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_operate, container, false);

        text=(TextView)v.findViewById(R.id.textView2);
        jsoupAsyncTask2 = new JsoupAsyncTask2();
        jsoupAsyncTask2.execute();

        return v;
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
