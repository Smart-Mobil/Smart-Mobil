package com.example.mymobil.operate.motor;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.PopupMenu;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import com.example.mymobil.R;
import com.example.mymobil.databinding.FragmentOperateTab3Binding;
import com.example.mymobil.setting.SettingActivity;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import java.io.IOException;
import static android.content.Context.MODE_PRIVATE;

/*
 * Update by Jinyeob on 2020.04.25
 */

public class Tab3Fragment extends Fragment {
    private FragmentOperateTab3Binding binding; //데이터 바인딩용

    private String motorUrl = "";
    private int motorNum = 1;
    private String currentMotor;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_operate_tab3, container, false);
        binding.setTab2Fragment(this);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("shared", MODE_PRIVATE);
        motorUrl = sharedPreferences.getString("SAVED_URL", "");
        motorUrl = motorUrl.concat(":3000/data");

        /* 올바른 URL인지 검사한다. */
        if (motorUrl.equals("") || !URLUtil.isValidUrl(motorUrl)) {
            Toast.makeText(getActivity(), "URL 주소를 입력해주세요.", Toast.LENGTH_LONG).show();

            Intent it = new Intent(getActivity(), SettingActivity.class);
            startActivity(it);

            getActivity().finish();
        }

        return binding.getRoot();
    }

    public void OnClickRight(View view) {
        new Thread() {
            public void run() {
                postInfo("right" + motorNum);
            }
        }.start();
        Toast.makeText(getActivity(), "시계방향", Toast.LENGTH_SHORT).show();

        currentMotor="시계방향";
        handler.sendMessage(handler.obtainMessage());
    }

    public void OnClickLeft(View view) {
        new Thread() {
            public void run() {
                postInfo("left");
            }
        }.start();
        Toast.makeText(getActivity(), "반시계방향", Toast.LENGTH_SHORT).show();

        currentMotor="반시계방향";
        handler.sendMessage(handler.obtainMessage());
    }

    public void OnClickMotorStop(View view) {
        new Thread() {
            public void run() {
                postInfo("motorStop");
            }
        }.start();
        Toast.makeText(getActivity(), "정지", Toast.LENGTH_SHORT).show();

        currentMotor="정지";
        handler.sendMessage(handler.obtainMessage());
    }

    private void postInfo(String value) {
        try {
            Connection.Response res = Jsoup.connect(motorUrl)
                    .data("data", value)
                    .timeout(5000)
                    .maxBodySize(0)
                    .method(Connection.Method.POST)
                    .execute();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        /* UI 업데이트는 여기서 합니다^^ */
        public void handleMessage(Message msg) {
            binding.textViewCurrentMotor.setText(currentMotor);
        }
    };
}