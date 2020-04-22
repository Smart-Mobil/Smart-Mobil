package com.example.mymobil.operate.music;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.mymobil.R;
import com.example.mymobil.databinding.FragmentOperateTab2Binding;


//import butterknife.ButterKnife;
//import butterknife.OnClick;

/*
 * Update by Jinyeob on 2020.04.22
 * Data binding 적용
 */

public class Tab2Fragment extends Fragment {
    private FragmentOperateTab2Binding binding; //데이터 바인딩용
    private static MediaPlayer mediaPlayer;
    private int time = 3000;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_operate_tab2, container, false);
        binding.setTab2Fragment(this);

        return binding.getRoot();
    }

    public void OnClickStart1(View view) {
        start(R.raw.a);
        time=3000;
    }

    public void OnClickStart2(View view) {
        start(R.raw.b);
        time=3000;
    }

    public void OnClickStart3(View view) {
        start(R.raw.c);
        time=3000;
    }

    public void OnClickStop1(View view) {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }

    public void OnClickStop2(View view) {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }

    public void OnClickStop3(View view) {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }

    public void start(int r) {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        mediaPlayer = MediaPlayer.create(getActivity(), r);
        mediaPlayer.start();

        Toast.makeText(getActivity(), "재생", Toast.LENGTH_SHORT).show();
/*
        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            public void run() {
                if (mediaPlayer != null) {
                    mediaPlayer.release();
                }
            }
        }, time);

 */
    }
}