package com.example.mymobil.operate.moodlight;

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

        final Button btnOn = root.findViewById(R.id.btnSend);
        final Button btnOff = root.findViewById(R.id.btnSend2);

        if (URLUtil.isValidUrl(moodlightUrl)) {

            btnOn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    //On 일때
                    new Thread() {
                        public void run() {
                            postInfo("on");
                            // Message msg = handler.obtainMessage();
                            // handler.sendMessage(msg);
                        }
                    }.start();
                    btnOn.setEnabled(false);
                    btnOff.setEnabled(true);
                }
            });

            btnOff.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    //Off 일때
                    new Thread() {
                        public void run() {
                            postInfo("off");
                            // Message msg = handler.obtainMessage();
                            //  handler.sendMessage(msg);
                        }
                    }.start();
                    btnOff.setEnabled(false);
                    btnOn.setEnabled(true);
                }
            });

            ColorPickerView colorPickerView = root.findViewById(R.id.colorPickerView);
            colorPickerView.setColorListener(new ColorEnvelopeListener() {
                @Override
                public void onColorSelected(ColorEnvelope envelope, boolean fromUser) {
                    final int color = envelope.getColor();
                    //컬러 보내기
                    new Thread() {
                        public void run() {
                            postInfo(String.valueOf(color));
                            // Message msg = handler.obtainMessage();
                            //  handler.sendMessage(msg);
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
/*
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
        }
    };
    */
}
