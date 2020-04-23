package com.example.mymobil.setting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mymobil.R;
import com.example.mymobil.sos.SosActivity;

import java.util.ArrayList;

/*
 * Add by Jinyeob 2020.04.23
 * 셋팅기능
 * 1. 접속 url 설정
 * 2. sos 전송할 번호 설정
 * 3. 알람 설정
 * 3-1. 알람 수치 설정 (미세먼지, 온도, 습도)
 * 3-2. 알람 시간 설정
 * 4. 아기 이름, 디데이 날짜 설정
 */
public class SettingActivity extends AppCompatActivity {
    ArrayList<item_list> settingDataList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //뒤로가기


        this.InitializesettingData();

        ListView listView = (ListView)findViewById(R.id.listView);
        final ListAdapter myAdapter = new ListAdapter(this,settingDataList);

        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id){
                Toast.makeText(getApplicationContext(), myAdapter.getItem(position).getSettingName(), Toast.LENGTH_SHORT).show();

                switch(position){
                    case 0:
                    case 1:
                    case 2:
                    default:
                }
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
            case R.id.action_sos :
                Intent intent = new Intent(SettingActivity.this, SosActivity.class);
                startActivity(intent);
                return true;
            default :
                return super.onOptionsItemSelected(item) ;
        }
    }

    public void InitializesettingData()
    {
        settingDataList = new ArrayList<item_list>();

        settingDataList.add(new item_list(R.drawable.ic_menu_manage, "URL 설정","접속 url을 설정합니다."));
        settingDataList.add(new item_list(R.drawable.ic_menu_manage, "SOS 수신번호 설정","sos 송신할 번호를 설정합니다."));
        settingDataList.add(new item_list(R.drawable.ic_menu_manage, "아기 정보 설정","아기 이름과 생일을 입력합니다"));
    }
}
