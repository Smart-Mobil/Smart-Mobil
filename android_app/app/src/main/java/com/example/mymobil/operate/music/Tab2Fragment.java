package com.example.mymobil.operate;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.mymobil.R;

import java.util.ArrayList;

public class Tab2Fragment extends Fragment {
     ArrayList<item_list> sound_list;
   ListAdapter listAdapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_operate_tab2, container, false);

        /*
        Button buttonStart = (Button) root.findViewById(R.id.btnStart);

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer = MediaPlayer.create(getActivity(), R.raw.a);
                mediaPlayer.start();
            }
        });

        Button buttonStop = (Button) root.findViewById(R.id.btnStop);
        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mediaPlayer.reset();
            }
        });

         */
        ListView listView = root.findViewById(R.id.listview);

        sound_list = new ArrayList<item_list>();

        InitializeData();
        listAdapter = new ListAdapter(getActivity(), sound_list);

        listView.setAdapter(listAdapter);

        return root;
    }


        public void InitializeData() {
            sound_list.add(new item_list("노래 1번"));
            sound_list.add(new item_list("노래 2번"));
            sound_list.add(new item_list("노래 3번"));
        }
}
