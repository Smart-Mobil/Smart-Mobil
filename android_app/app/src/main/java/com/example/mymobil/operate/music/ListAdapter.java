package com.example.mymobil.operate.music;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.mymobil.R;

import java.util.ArrayList;
import android.os.Handler;

/*
* 2020.04.04 김진엽
* bug: fragment 전환 시에도 노래가 나옴. 다시 돌아와서 중단 누르면 앱 중단됨

* 2020.04.22 김진엽
* bug fix: 3초 딜레이 후 멈추도록 수정.
*/

public class ListAdapter extends BaseAdapter {
    private Context mContext = null;
    private LayoutInflater mLayoutInflater = null;
    private ArrayList<item_list> item_list;
    static MediaPlayer mediaPlayer;
    private int val;

    public ListAdapter(Context context, ArrayList<item_list> data) {
        mContext = context;
        item_list = data;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return item_list.size();
    }

    @Override
    public Object getItem(int position) {
        return item_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = mLayoutInflater.inflate(R.layout.item, null);
        TextView soundName = (TextView) convertView.findViewById(R.id.text_sound);
        soundName.setText(item_list.get(position).getSoundName());

        Button buttonStart = (Button) convertView.findViewById(R.id.btnStart);
        mediaDestory();

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaDestory();

                if (position == 0) val = R.raw.a;
                else if (position == 1) val = R.raw.b;
                else val = R.raw.c;

                mediaPlayer = MediaPlayer.create(mContext, val);
                mediaPlayer.start();

                (new Handler()).postDelayed(new Runnable() {
                    public void run() {
                        mediaPlayer.stop();
                    }
                }, 3000);//3초간 실행 후 음악 멈춤
            }
        });

        Button buttonStop = (Button) convertView.findViewById(R.id.btnStop);

        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mediaPlayer.reset();
            }
        });

        return convertView;
    }

    public void mediaDestory() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
