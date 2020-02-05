package com.project.mobilapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {
    private JsoupAsyncTask1 jsoupAsyncTask1;

    private Document doc;

    private TextView textView;
    private Button VideoButton;
    private Button LedNmoterButton;

    private String dustString = "", dustString1 = "", dustString2 = "";
    private String THString = "", tempString = "", humString = "";
    private String bodyTempString = "";
    private String allString = "";
private boolean bool = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);
        VideoButton = (Button) findViewById(R.id.button);
        LedNmoterButton = (Button) findViewById(R.id.button2);

        class NewRunnable implements Runnable {
            @Override
            public void run() {
                while (bool) {
                    allString="";
                    //파싱 시작
                    jsoupAsyncTask1 = new JsoupAsyncTask1();
                    jsoupAsyncTask1.execute();
                    try {
                        Thread.sleep(2000) ;
                    } catch (Exception e) {
                        e.printStackTrace() ;
                    }
                }
            }
        }

        VideoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_ = new Intent(getApplicationContext(), VideoActivity.class);
                startActivity(intent_);
                jsoupAsyncTask1.cancel(true);
                bool=false;
            }
        });

        LedNmoterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_ = new Intent(getApplicationContext(), Input.class);
                startActivity(intent_);
                jsoupAsyncTask1.cancel(true);
                bool=false;
            }
        });

        NewRunnable nr = new NewRunnable() ;
        Thread t = new Thread(nr) ;
        t.start() ;
    }

    private class JsoupAsyncTask1 extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                doc = Jsoup.connect("http://172.30.1.8:3000/hello").get();

                System.out.println(doc);

                Element ele1, ele2, ele3;

                ele1 = doc.select("h1").first();
                ele2 = doc.select("p").first();
                ele3 = doc.select("h2").first();

                //미세먼지
                dustString = ele1.text();

                if(dustString.length()>0) {
                    dustString = dustString.substring(dustString.lastIndexOf(")") + 1);

                    int idx = dustString.indexOf(",");
                    if(0<idx) {
                        dustString1 = "<미세먼지>\nPM10 : " + dustString.substring(0, idx) + "\n";
                        if(idx + 1<dustString.indexOf("/")) {
                            dustString2 = "PM2.5 : " + dustString.substring(idx + 1, dustString.indexOf("/")) + "\n\n";
                        }
                    }

                }

                //온습도
                THString = ele2.text();

                if(THString.length()>0) {
                    THString = THString.substring(THString.lastIndexOf(")") + 1);

                    int idx2 = THString.indexOf(",");
                    if(0<idx2) {
                        tempString = "<온습도>\n온도 : " + THString.substring(0, idx2) + "\n";
                        if(idx2 + 1<THString.indexOf("/")) {
                            humString = "습도 : " + THString.substring(idx2 + 1, THString.indexOf("/")) + "\n\n";
                        }
                    }
                }

                //체온
                bodyTempString = ele3.text();

                if(bodyTempString.length()>0) {
                    bodyTempString = bodyTempString.substring(bodyTempString.lastIndexOf(")") + 1);
                    if(0<bodyTempString.indexOf("/")) {
                        bodyTempString = "체온 : " + bodyTempString.substring(0, bodyTempString.indexOf("/")) + "\n\n";
                    }
                }

                allString = dustString1 + dustString2 + tempString + humString + bodyTempString;

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            textView.setText(allString);
        }
    }
}