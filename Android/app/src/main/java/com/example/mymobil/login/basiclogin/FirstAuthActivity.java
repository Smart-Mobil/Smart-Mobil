package com.example.mymobil.login.basiclogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.mymobil.MainActivity;
import com.example.mymobil.R;

public class FirstAuthActivity extends AppCompatActivity {

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_auth);

        if(SaveSharedPreference.getUserName(FirstAuthActivity.this).length() == 0 || SaveSharedPreference.getUserPasswd(FirstAuthActivity.this).length() == 0) {
            // call Login Activity
            intent = new Intent(FirstAuthActivity.this, LoginActivity.class);

            startActivity(intent);
            this.finish();
        } else {
            // Call Next Activity
            intent = new Intent(FirstAuthActivity.this, MainActivity.class);
            intent.putExtra("id", SaveSharedPreference.getUserName(this));
            intent.putExtra("passwd", SaveSharedPreference.getUserPasswd(this));

            startActivity(intent);
            this.finish();
        }
    }
}