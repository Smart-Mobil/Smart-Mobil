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

    private String dust = "", temphum = "", tempbody = "";
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

    private String getDust(String dustString) {
        //미세먼지
        String dustString1 = "", dustString2 = ""; //substr용 임시 변수

        if (dustString.length() > 0) {
            dustString = dustString.substring(dustString.lastIndexOf(")") + 1);

            int idx = dustString.indexOf(",");

            if (0 < idx || idx + 1 < dustString.indexOf("/")) {
                dustString1 = "<미세먼지>\nPM10 : " + dustString.substring(0, idx) + "\n";
                dustString2 = "PM2.5 : " + dustString.substring(idx + 1, dustString.indexOf("/")) + "\n\n";

                return dustString1 + dustString2;
            } else {
                return "";
            }
        } else {
            return "";
        }
    }

    private String getTempHum(String THString) {
        //온습도
        String tempString = "", humString = ""; //substr용 임시 변수

        if (THString.length() > 0) {
            THString = THString.substring(THString.lastIndexOf(")") + 1);

            int idx2 = THString.indexOf(",");

            if (0 < idx2 || idx2 + 1 < THString.indexOf("/")) {
                tempString = "<온습도>\n온도 : " + THString.substring(0, idx2) + "\n";
                humString = "습도 : " + THString.substring(idx2 + 1, THString.indexOf("/")) + "\n\n";

                return tempString + humString;
            } else {
                return "";
            }
        } else {
            return "";
        }
    }

    private String getTempBody(String bodyTempString) {
        //체온
        if (bodyTempString.length() > 0) {
            bodyTempString = bodyTempString.substring(bodyTempString.lastIndexOf(")") + 1);
            if (0 < bodyTempString.indexOf("/")) {
                bodyTempString = "체온 : " + bodyTempString.substring(0, bodyTempString.indexOf("/")) + "\n\n";

                return bodyTempString;
            } else {
                return "";
            }
        } else {
            return "";
        }
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

            dust = getDust(ele1.text());
            temphum = getTempHum(ele2.text());
            tempbody = getTempBody(ele3.text());

            allString = dust + temphum + tempbody;

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