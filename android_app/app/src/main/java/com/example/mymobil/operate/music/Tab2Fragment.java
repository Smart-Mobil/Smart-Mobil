package com.example.mymobil.operate.music;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.mymobil.R;
import com.example.mymobil.databinding.FragmentOperateTab2Binding;
import com.example.mymobil.setting.SettingActivity;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;

/**
 * Update by Jinyeob on 2020. 04. 22
 * Data binding 적용
 */

public class Tab2Fragment extends Fragment {
    private FragmentOperateTab2Binding binding; //데이터 바인딩용

    private String musicUrl = "";
    private int musicNum = 1;
    private String currentMusic;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_operate_tab2, container, false);
        binding.setTab2Fragment(this);
        View root = inflater.inflate(R.layout.fragment_operate_tab2, container, false);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("shared", MODE_PRIVATE);
        musicUrl = sharedPreferences.getString("SAVED_URL", "");
        musicUrl = musicUrl.concat(":3000/data");

        /* 올바른 URL인지 검사한다. */
        if (musicUrl.equals("") || !URLUtil.isValidUrl(musicUrl)) {
            Toast.makeText(getActivity(), "URL 주소를 입력해주세요.", Toast.LENGTH_LONG).show();

            Intent it = new Intent(getActivity(), SettingActivity.class);
            startActivity(it);

            getActivity().finish();
        }

        return binding.getRoot();
    }

    public void OnClickSelect(View view) {
        PopupMenu popup = new PopupMenu(getActivity(), view);
        MenuInflater inflater = popup.getMenuInflater();
        Menu menu = popup.getMenu();
        inflater.inflate(R.menu.context_menu, menu);

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.music1:
                        musicNum=1;
                        currentMusic=musicNum+"번 노래";
                        binding.buttonSelect.setText(currentMusic);
                        return true;
                    case R.id.music2:
                        musicNum=2;
                        currentMusic=musicNum+"번 노래";
                        binding.buttonSelect.setText(currentMusic);
                        return true;
                    case R.id.music3:
                        musicNum=3;
                        currentMusic=musicNum+"번 노래";
                        binding.buttonSelect.setText(currentMusic);
                        return true;
                    case R.id.music4:
                        musicNum=4;
                        currentMusic=musicNum+"번 노래";
                        binding.buttonSelect.setText(currentMusic);
                        return true;
                    case R.id.music5:
                        musicNum=5;
                        currentMusic=musicNum+"번 노래";
                        binding.buttonSelect.setText(currentMusic);
                        return true;
                    case R.id.music6:
                        musicNum=6;
                        currentMusic=musicNum+"번 노래";
                        binding.buttonSelect.setText(currentMusic);
                        return true;
                    case R.id.music7:
                        musicNum=7;
                        currentMusic=musicNum+"번 노래";
                        binding.buttonSelect.setText(currentMusic);
                        return true;
                    case R.id.music8:
                        musicNum=8;
                        currentMusic=musicNum+"번 노래";
                        binding.buttonSelect.setText(currentMusic);
                        return true;
                    case R.id.music9:
                        musicNum=9;
                        currentMusic=musicNum+"번 노래";
                        binding.buttonSelect.setText(currentMusic);
                        return true;
                    case R.id.music10:
                        musicNum=10;
                        currentMusic=musicNum+"번 노래";
                        binding.buttonSelect.setText(currentMusic);
                        return true;
                }
                return false;
            }
        });
        popup.show();
    }

    public void OnClickStart(View view) {
        new Thread() {
            public void run() {
                postInfo("start" + musicNum);
            }
        }.start();
        Toast.makeText(getActivity(), "재생", Toast.LENGTH_SHORT).show();
        binding.textViewCurrentMusic.setText(currentMusic);
    }

    public void OnClickStop(View view) {
        new Thread() {
            public void run() {
                postInfo("stop");
            }
        }.start();
        Toast.makeText(getActivity(), "정지", Toast.LENGTH_SHORT).show();
        binding.textViewCurrentMusic.setText("정지");
    }

    private void postInfo(String value) {
        try {
            Connection.Response res = Jsoup.connect(musicUrl)
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