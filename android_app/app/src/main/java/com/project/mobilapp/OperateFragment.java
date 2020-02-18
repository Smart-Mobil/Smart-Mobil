package com.project.mobilapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
public class OperateFragment extends Fragment {
    private Button lightbutton;
    private Button musicbutton;
    private Button motorbutton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_operate, container, false);

        lightbutton=(Button)v.findViewById(R.id.lightbutton);
        musicbutton=(Button)v.findViewById(R.id.musicbutton);
        motorbutton=(Button)v.findViewById(R.id.motorbutton);



        lightbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent = new Intent(getActivity(),LightActivity.class);
                startActivity(myintent);
            }
        });
        musicbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent = new Intent(getActivity(),MusicActivity.class);
                startActivity(myintent);
            }
        });
        motorbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent = new Intent(getActivity(),MotorActivity.class);
                startActivity(myintent);
            }
        });
        return v;
    }

}
