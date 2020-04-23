package com.example.mymobil.env;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.mymobil.R;
import com.example.mymobil.setting.SettingActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.regex.PatternSyntaxException;

import static android.content.Context.MODE_PRIVATE;

public class EnvFragment extends Fragment {
    private TextView textView;
    private SharedPreferences sharedPreferences;

    private String EnvUrl = " ";
    private String allString = "";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_env, container, false);

        sharedPreferences = getActivity().getSharedPreferences("shared", MODE_PRIVATE);

        textView = root.findViewById(R.id.text_env);

        try {
            if (EnvUrl.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*")) {
                Intent intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
            }
            else{

            }
        } catch (PatternSyntaxException e) {
            System.err.println("An Exception Occured");
            e.printStackTrace();
        }
        new Thread() {
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    EnvUrl = sharedPreferences.getString("SAVED_URL", "");

                    getInfo();
                    handler.sendMessage(handler.obtainMessage());
                }
            }
        }.start();
        return root;
    }

    private String getDust(String dustString) {
        //미세먼지
        String dustString1 = "", dustString2 = ""; //substr용 임시 변수

        if (dustString.length() > 0) {
            dustString = dustString.substring(dustString.lastIndexOf(")") + 1);

            int idx = dustString.indexOf(",");


            if (0 < idx && idx + 1 < dustString.indexOf("/")) {
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

            if (0 < idx2 && idx2 + 1 < THString.indexOf("/")) {
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
            Document doc = Jsoup.connect(EnvUrl).get();

            System.out.println(doc);

            Element ele1, ele2, ele3;

            ele1 = doc.select("h1").first();
            ele2 = doc.select("p").first();
            ele3 = doc.select("i").first();


            String dust = getDust(ele1.text());
            String temphum = getTempHum(ele2.text());
            String tempbody = getTempBody(ele3.text());

            allString = dust + temphum + tempbody;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("HandlerLeak")
    private
    Handler handler = new Handler() {
        @SuppressLint("SetTextI18n")
        public void handleMessage(Message msg) {
            if (allString.equals("") || allString.equals(" ") || allString.equals("   ")) {
                textView.setText("Connection Fail!! 연결 확인해주세요.");
            } else {
                textView.setText(allString);
            }
        }
    };

}