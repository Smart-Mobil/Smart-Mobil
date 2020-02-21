package com.example.mymobil.operate;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mymobil.R;
import com.skydoves.colorpickerview.ColorEnvelope;
import com.skydoves.colorpickerview.ColorPickerView;
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;

public class Tab1Fragment extends Fragment {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_operate_tab1, container, false);

        Button btnOn = root.findViewById(R.id.btnSend);
        Button btnOff = root.findViewById(R.id.btnSend2);

        btnOn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //On 일때
                new Thread() {
                    public void run() {
                        postInfo("on");
                        Message msg = handler.obtainMessage();
                        handler.sendMessage(msg);
                    }
                }.start();
            }
        });

        btnOff.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Off 일때
                new Thread() {
                    public void run() {
                        postInfo("off");
                        Message msg = handler.obtainMessage();
                        handler.sendMessage(msg);
                    }
                }.start();
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
                        Message msg = handler.obtainMessage();
                        handler.sendMessage(msg);
                    }
                }.start();
            }
        });

        return root;
    }

    private void postInfo(String value) {
        try {
            Connection.Response res = Jsoup.connect("http://172.30.1.8:3000/data")
                    .data("data", value)
                    .timeout(5000)
                    .maxBodySize(0)
                    .method(Connection.Method.POST)
                    .execute();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
        }
    };
}
