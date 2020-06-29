package com.example.mymobil.operate.moodlight;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mymobil.R;
import com.example.mymobil.setting.SettingActivity;
import com.skydoves.colorpickerview.ColorEnvelope;
import com.skydoves.colorpickerview.ColorPickerView;
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;

import static android.content.Context.MODE_PRIVATE;

/**
 * Update by Jinyeob on 2020. 04. 25
 * Url 검사 조건문 추가
 */
public class Tab1Fragment extends Fragment {
    private String moodlightUrl = "";
    private int color[];
    TextView textView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_operate_tab1, container, false);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("shared", MODE_PRIVATE);
        moodlightUrl = sharedPreferences.getString("SAVED_URL", "");
        moodlightUrl = moodlightUrl.concat(":3000/data");

        /* 저장된 URL이 있는지 먼저 검사한다. */
        if (moodlightUrl.equals("")) {
            Toast.makeText(getActivity(), "URL 주소를 입력해주세요.", Toast.LENGTH_LONG).show();

            Intent it = new Intent(getActivity(), SettingActivity.class);
            startActivity(it);

            getActivity().finish();
        }

        //final Button btnOn = root.findViewById(R.id.btnSend);
        final Button btnOff = root.findViewById(R.id.btnSend2);
        textView = root.findViewById(R.id.brightnessText);
        ColorPickerView colorPickerView = root.findViewById(R.id.colorPickerView);

        if (URLUtil.isValidUrl(moodlightUrl)) {
/*
            btnOn.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("SetTextI18n")
                public void onClick(View v) {
                    //On 일때
                    textView.setText("ON 상태");
                    btnOn.setEnabled(false);
                    btnOff.setEnabled(true);

                    new Thread() {
                        public void run() {
                            postInfo("on");
                        }
                    }.start();

                }
            });
*/
            btnOff.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("SetTextI18n")
                public void onClick(View v) {
                    //Off 일때
                    textView.setText("OFF 상태");
                    btnOff.setEnabled(false);
                    //btnOn.setEnabled(true);

                    new Thread() {
                        public void run() {
                            postInfo("off");
                        }
                    }.start();

                }
            });

            colorPickerView.setColorListener(new ColorEnvelopeListener() {
                @Override
                public void onColorSelected(ColorEnvelope envelope, boolean fromUser) {
                    color = envelope.getArgb();
                    int r=color[1];
                    int g=color[2];
                    int b=color[3];
                    String rgb="on"+String.valueOf(r)+"a"+String.valueOf(g)+"a"+String.valueOf(b);
                    textView.setText(rgb);
                    btnOff.setEnabled(true);

                    //컬러 보내기
                    new Thread() {
                        public void run() {
                            postInfo(String.valueOf(rgb));
                        }
                    }.start();
                }
            });
        } else {
            Toast.makeText(getActivity(), "잘못된 URL 주소입니다. URL 주소를 입력해주세요.", Toast.LENGTH_LONG).show();

            Intent it = new Intent(getActivity(), SettingActivity.class);
            startActivity(it);

            getActivity().finish();
        }

        return root;
    }

    private void postInfo(String value) {
        try {
            Connection.Response res = Jsoup.connect(moodlightUrl)
                    .data("data", value)
                    .timeout(5000)
                    .maxBodySize(0)
                    .method(Connection.Method.POST)
                    .execute();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
