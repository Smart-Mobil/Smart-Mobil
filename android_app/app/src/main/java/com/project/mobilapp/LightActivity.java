package com.project.mobilapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import com.skydoves.colorpickerview.ColorEnvelope;
import com.skydoves.colorpickerview.ColorPickerView;
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;

public class LightActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light);

        Button btnOn = findViewById(R.id.btnSend);
        Button btnOff = findViewById(R.id.btnSend2);

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

        ColorPickerView colorPickerView = findViewById(R.id.colorPickerView);
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

    }
    private void postInfo(String value){
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
