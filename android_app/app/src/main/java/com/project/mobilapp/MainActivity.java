package com.project.mobilapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private FragmentManager fragmentManager = getSupportFragmentManager();

    private EnvFragment envFragment = new EnvFragment();
    private OperateFragment operateFragment = new OperateFragment();
    private VideoFragment videoFragment = new VideoFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigationView);

        // 첫 화면 지정
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameLayout, envFragment).commitAllowingStateLoss();

        // bottomNavigationView의 아이템이 선택될 때 호출될 리스너 등록
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                switch (item.getItemId()) {
                    case R.id.bn1: {
                        transaction.replace(R.id.frameLayout, envFragment).commitAllowingStateLoss();
                        break;
                    }
                    case R.id.bn2: {
                        transaction.replace(R.id.frameLayout, operateFragment).commitAllowingStateLoss();
                        break;
                    }
                    case R.id.bn3: {
                        transaction.replace(R.id.frameLayout, videoFragment).commitAllowingStateLoss();
                        break;
                    }
                }
                return true;
            }
        });
    }
}