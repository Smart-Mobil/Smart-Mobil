package com.example.mymobil.setting;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mymobil.MainActivity;
import com.example.mymobil.R;
import com.example.mymobil.env.EnvFragment;
import com.example.mymobil.sos.SosActivity;

import java.util.ArrayList;

import static android.text.InputType.TYPE_CLASS_NUMBER;

/**
 * Add by Jinyeob 2020.04.23
 * 셋팅기능
 * 1. 접속 url 설정
 * 2. sos 전송할 번호 설정
 * 3. 알람 설정
 * 3-1. 알람 수치 설정 (미세먼지, 온도, 습도)
 * 3-2. 알람 시간 설정
 * 4. 아기 이름, 디데이 날짜 설정
 */

/**
 * Update by Jinyeob on 2020.04.24
 * onStop 추가
 * 주석 추가
 */
public class SettingActivity extends AppCompatActivity {
    ArrayList<item_list> settingDataList;
    SharedPreferences sharedPreferences;
    private String settingUrl = "";
    private String settingSms = "";
    EnvFragment envFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //뒤로가기

/*
리스트뷰 설정
 */
        this.InitializesettingData();

        ListView listView = (ListView) findViewById(R.id.listView);
        final ListAdapter myAdapter = new ListAdapter(this, settingDataList);

        listView.setAdapter(myAdapter);

        //url용 sharedPreferences
        sharedPreferences = getSharedPreferences("shared", MODE_PRIVATE);

        //리스트뷰 이벤트리스너
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                //터치한 목록 이름 토스트 ㅎ
                //Toast.makeText(getApplicationContext(), myAdapter.getItem(position).getSettingName(), Toast.LENGTH_SHORT).show();

                //리스트뷰 목록에 따라 조건문 실행
                switch (position) {
                    case 0:
                        //url 입력 다이얼로그
                        urlDialog();
                        break;
                    case 1:
                        //전화번호 입력 다이얼로그
                        sosDialog();
                        break;
                    case 2:
                        //문구 입력 다이얼로그
                        sosMessageDialog();
                        break;
                    default:
                }
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: { //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
            case R.id.action_sos:
                Intent intent = new Intent(SettingActivity.this, SosActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /* 리스트뷰 목록 추가시 여기에 */
    public void InitializesettingData() {
        settingDataList = new ArrayList<item_list>();

        settingDataList.add(new item_list(R.drawable.ic_menu_manage, "URL 설정", "접속 URL을 설정합니다."));
        settingDataList.add(new item_list(R.drawable.ic_menu_manage, "SOS 번호 설정", "SOS 전송할 번호를 설정합니다."));
        settingDataList.add(new item_list(R.drawable.ic_menu_manage, "SOS 문구 설정", "SOS 전송할 문구를 설정합니다."));
    }

    /* url 입력 다이얼로그 */
    public void urlDialog() {
        AlertDialog.Builder ad = new AlertDialog.Builder(SettingActivity.this);

        //저장된 url 있습니꽈?
        String text = sharedPreferences.getString("SAVED_URL", "");
        SharedPreferences.Editor editor = sharedPreferences.edit();

        //다이얼로그 설정
        ad.setTitle("URL 설정");
        ad.setMessage("접속 URL을 입력해주세요. (포트번호 제외. 예시: http://1.241.96.225");

        //다이얼로그 에디트텍스트에, 기존입력된 url set
        final EditText et = new EditText(SettingActivity.this);
        ad.setView(et);
        et.setTextColor(Color.parseColor("#000000"));
        et.setText(text);

        //확인 버튼
        ad.setPositiveButton("저장", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                settingUrl = et.getText().toString();
                editor.putString("SAVED_URL", settingUrl);
                editor.commit();
                Toast.makeText(SettingActivity.this, "URL 저장 완료", Toast.LENGTH_SHORT).show();

                dialog.dismiss();
            }
        });

        ad.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        ad.show();
    }

    public void sosDialog() {
        AlertDialog.Builder ad = new AlertDialog.Builder(SettingActivity.this);

        String text = sharedPreferences.getString("SAVED_SMS", "");
        SharedPreferences.Editor editor = sharedPreferences.edit();

        ad.setTitle("SOS 설정");
        ad.setMessage("SOS 전송할 번호를 입력해주세요.");

        final EditText et = new EditText(SettingActivity.this);
        et.setInputType(TYPE_CLASS_NUMBER);
        ad.setView(et);
        et.setTextColor(Color.parseColor("#000000"));
        et.setText(text);

        ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                settingSms = et.getText().toString();
                editor.putString("SAVED_SMS", settingSms);
                editor.commit();
                Toast.makeText(SettingActivity.this, "문구 저장 완료", Toast.LENGTH_SHORT).show();

                dialog.dismiss();
            }
        });

        ad.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        ad.show();
    }

    public void sosMessageDialog() {
        AlertDialog.Builder ad = new AlertDialog.Builder(SettingActivity.this);

        String text = sharedPreferences.getString("SAVED_SMS_MESSAGE", "");
        SharedPreferences.Editor editor = sharedPreferences.edit();

        ad.setTitle("SOS 문구 설정");
        ad.setMessage("SOS 전송할 문구를 입력해주세요.");

        final EditText et = new EditText(SettingActivity.this);
        ad.setView(et);
        et.setTextColor(Color.parseColor("#000000"));
        et.setText(text);

        ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                settingSms = et.getText().toString();
                editor.putString("SAVED_SMS_MESSAGE", settingSms);
                editor.commit();
                Toast.makeText(SettingActivity.this, "문구 저장 완료", Toast.LENGTH_SHORT).show();

                dialog.dismiss();
            }
        });

        ad.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        ad.show();
    }

    @Override
    protected void onStop() {
        super.onStop();

        /*
        //SettingActivity가 쏜 작은 url........ ^^
        envFragment = new EnvFragment();
        Bundle bundle = new Bundle();

        bundle.putString("url",settingUrl);

        envFragment.setArguments(bundle);
        Toast.makeText(getApplicationContext(), "SettingActivity 종료", Toast.LENGTH_LONG).show();
        */
        Intent it = new Intent(this, MainActivity.class);
        startActivity(it);

        finish();
    }
}
