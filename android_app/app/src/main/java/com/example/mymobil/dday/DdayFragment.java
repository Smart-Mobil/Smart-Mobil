package com.example.mymobil.dday;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mymobil.R;

import java.util.Calendar;
import java.util.Date;


public class DdayFragment extends Fragment {
    private ImageView imageView;
    private TextView ddayStart;
    String dateStart;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dday, container, false);

        //기본 SharedPreferences 환경과 관련된 객체를 얻어옵니다.
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        // SharedPreferences 수정을 위한 Editor 객체를 얻어옵니다.
        editor = preferences.edit();

        imageView=(ImageView) root.findViewById(R.id.image_calendar);
       // imageView.setImageResource(R.drawable.ic_calendar);

        ddayStart=(TextView)root.findViewById(R.id.dday_start);

        ddayStart.setText(preferences.getString("DDAY","달력을 클릭해주세요"));

        imageView.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                final Calendar cal = Calendar.getInstance();
                DatePickerDialog dialog= new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        dateStart = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth);
                    }
                }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
                dialog.getDatePicker().setMaxDate(new Date().getTime());    //입력한 날짜 이후로 클릭 안되게 옵션
                dialog.show();

                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        editor.putString("DDAY", dateStart);
                        editor.commit();
                        ddayStart.setText(dateStart);
                    }
                });
            }
        });

        return root;
    }
}
