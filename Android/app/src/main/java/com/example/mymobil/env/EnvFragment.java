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
import android.widget.ProgressBar;
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
 * Update by Jinyeob on 2020. 04. 24.
 * Url 셋팅 추가 완료
 * Url 검사하는 코드 추가
 */

/**
 * Update by Jinyeob on 2020. 05. 01.
 * 원형 프로그레스바 업데이트
 */
public class EnvFragment extends Fragment {
    private TextView textView;
    private String EnvUrl = "";

    String dust_ = "";
    String temp_ = "";
    String hum_ = "";
    String tempBody_ = "";

    private String allString = "";
    private ProgressBar progressBar_dust;
    private ProgressBar progressBar_temp;
    private ProgressBar progressBar_hum;

    private TextView textView_dust;
    private TextView textView_temp;
    private TextView textView_hum;
    private TextView textView_body;

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
        textView_dust = root.findViewById(R.id.text_dust_percentage);
        textView_temp = root.findViewById(R.id.text_temp_percentage);
        textView_hum = root.findViewById(R.id.text_hum_percentage);
        textView_body = root.findViewById(R.id.text_body);

        progressBar_dust = (ProgressBar) root.findViewById(R.id.cpb1);
        progressBar_temp = (ProgressBar) root.findViewById(R.id.cpb2);
        progressBar_hum = (ProgressBar) root.findViewById(R.id.cpb3);

        progressBar_dust.setProgress(20);
        progressBar_temp.setProgress(25);
        progressBar_hum.setProgress(40);

        /* URL 검사용 조건문 */
        if (URLUtil.isValidUrl(EnvUrl)) {
            new Thread() {
                public void run() {
                    while (true) {
                        try {
                            Thread.sleep(3000); //3초 주기로 파싱
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        getInfo(); // 파싱 시작
                        handler.sendMessage(handler.obtainMessage());
                    }
                }
            }.start();
        } else {
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
                dust_ = dustString.substring(idx + 1, dustString.indexOf("/"));
                dustString1 = "<미세먼지>\nPM10 : " + dustString.substring(0, idx) + "\n";
                dustString2 = "PM2.5 : " + dust_ + "\n\n";

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
                temp_ = THString.substring(0, idx2);
                hum_ = THString.substring(idx2 + 1, THString.indexOf("/"));

                tempString = "<온습도>\n온도 : " + temp_ + "\n";
                humString = "습도 : " + hum_ + "\n\n";

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
                tempBody_ = bodyTempString.substring(0, bodyTempString.indexOf("/"));
                bodyTempString = "체온 : " + tempBody_ + "\n\n";

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
                textView.setText("연결됨");

                System.out.println(dust_);
                System.out.println(temp_);
                System.out.println(hum_);

                int dust = Integer.parseInt(dust_);
                int temp = Integer.parseInt(temp_.substring(0, temp_.indexOf(".")));
                int hum = Integer.parseInt(hum_.substring(0, hum_.indexOf(".")));
                int tempBody = Integer.parseInt(tempBody_.substring(0, tempBody_.indexOf(".")))+2;

                progressBar_dust.setProgress(dust);
                progressBar_temp.setProgress(temp);
                progressBar_hum.setProgress(hum);

                textView_dust.setText(dust_);
                textView_temp.setText(temp_);
                textView_hum.setText(hum_);
                textView_body.setText(String.valueOf(tempBody)+"*C");

                textView.setText("");
            }
        }
    };

}