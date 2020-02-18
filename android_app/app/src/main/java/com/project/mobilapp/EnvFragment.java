package com.project.mobilapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class EnvFragment extends Fragment {
    private Document doc;

    private TextView textView;

    private String dustString = "", dustString1 = "", dustString2 = "";
    private String THString = "", tempString = "", humString = "";
    private String bodyTempString = "";
    private String allString = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_env, container, false);

        textView = (TextView) v.findViewById(R.id.textView);

        new Thread() {
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    getInfo();
                    Message msg = handler.obtainMessage();
                    handler.sendMessage(msg);
                }
            }
        }.start();
        return v;
    }

    private void getInfo() {
        allString = "";

        try {
            doc = Jsoup.connect("http://172.30.1.8:3000/hello").get();

            System.out.println(doc);

            Element ele1, ele2, ele3;

            ele1 = doc.select("h1").first();
            ele2 = doc.select("p").first();
            ele3 = doc.select("h2").first();

            //미세먼지
            dustString = ele1.text();

            if (dustString.length() > 0) {
                dustString = dustString.substring(dustString.lastIndexOf(")") + 1);

                int idx = dustString.indexOf(",");
                if (0 < idx) {
                    dustString1 = "<미세먼지>\nPM10 : " + dustString.substring(0, idx) + "\n";
                    if (idx + 1 < dustString.indexOf("/")) {
                        dustString2 = "PM2.5 : " + dustString.substring(idx + 1, dustString.indexOf("/")) + "\n\n";
                    }
                }

            }

            //온습도
            THString = ele2.text();

            if (THString.length() > 0) {
                THString = THString.substring(THString.lastIndexOf(")") + 1);

                int idx2 = THString.indexOf(",");
                if (0 < idx2) {
                    tempString = "<온습도>\n온도 : " + THString.substring(0, idx2) + "\n";
                    if (idx2 + 1 < THString.indexOf("/")) {
                        humString = "습도 : " + THString.substring(idx2 + 1, THString.indexOf("/")) + "\n\n";
                    }
                }
            }

            //체온
            bodyTempString = ele3.text();

            if (bodyTempString.length() > 0) {
                bodyTempString = bodyTempString.substring(bodyTempString.lastIndexOf(")") + 1);
                if (0 < bodyTempString.indexOf("/")) {
                    bodyTempString = "체온 : " + bodyTempString.substring(0, bodyTempString.indexOf("/")) + "\n\n";
                }
            }

            allString = dustString1 + dustString2 + tempString + humString + bodyTempString;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            textView.setText(allString);
        }
    };
}