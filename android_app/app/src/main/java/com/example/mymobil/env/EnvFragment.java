package com.example.mymobil.env;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.TextView;
import android.widget.Toast;

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

/**
 * Update by Jinyeob on 2020.04.24
 * Url 셋팅 추가 완료
 * Url 검사하는 코드 추가
 */
public class EnvFragment extends Fragment {
    private TextView textView;
    private String EnvUrl = "";
    private String allString = "";

    //Bundle extra;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_env, container, false);

        /*
        //SettingActivity가 쏜 작은 url........ ^^
        if(extra != null) {
            extra = this.getArguments();
            extra = getArguments();
            EnvUrl= extra.getString("settingUrl");

            Toast.makeText(getActivity(),EnvUrl,Toast.LENGTH_SHORT).show();
        }
        */

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("shared", MODE_PRIVATE);
        EnvUrl = sharedPreferences.getString("SAVED_URL", "");
        EnvUrl = EnvUrl.concat(":3000");

        /* 저장된 URL이 있는지 먼저 검사한다. */
        if (EnvUrl.equals("")) {
            Toast.makeText(getActivity(), "URL 주소를 입력해주세요.", Toast.LENGTH_LONG).show();

            Intent it = new Intent(getActivity(), SettingActivity.class);
            startActivity(it);

            getActivity().finish();
        }

        textView = root.findViewById(R.id.text_env);

        /* URL 검사용 조건문 */
        if (URLUtil.isValidUrl(EnvUrl)) {
            new Thread() {
                public void run() {
                    while (true) {
                        try {
                            Thread.sleep(1000); //1초 주기로 파싱
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        getInfo(); // 파싱 시작
                        handler.sendMessage(handler.obtainMessage());
                    }
                }
            }.start();
        } else {
            Toast.makeText(getActivity(), "잘못된 URL 주소입니다. URL 주소를 입력해주세요.", Toast.LENGTH_LONG).show();

            Intent it = new Intent(getActivity(), SettingActivity.class);
            startActivity(it);

            getActivity().finish();
        }

        return root;
    }

    /*
    @Override
    public void onResume() {
        super.onResume();
    }
*/
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

    /* 파싱 필수 함수 */
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
            /* UI 업데이트는 여기서 합니다^^ */
            if (allString.equals("") || allString.equals(" ") || allString.equals("   ")) {
                textView.setText("Connection Fail!! 연결 확인해주세요.");
            } else {
                textView.setText(allString);
            }
        }
    };

}