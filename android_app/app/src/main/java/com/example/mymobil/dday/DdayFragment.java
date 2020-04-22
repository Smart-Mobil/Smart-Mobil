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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mymobil.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.content.ContentValues.TAG;


public class DdayFragment extends Fragment {
    private ImageView imageView;
    private TextView ddayStartView, ddayView;
    private String dateStart;
    private long ddayDate;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dday, container, false);

        //기본 SharedPreferences 환경과 관련된 객체를 얻어옵니다.
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        // SharedPreferences 수정을 위한 Editor 객체를 얻어옵니다.
        editor = preferences.edit();

        imageView = root.findViewById(R.id.image_calendar);
        // imageView.setImageResource(R.drawable.ic_calendar);
        ddayStartView = root.findViewById(R.id.dday_start);
        ddayView = root.findViewById(R.id.dday);

        ddayStartView.setText(preferences.getString("DDAY_START", "달력을 클릭해주세요"));
        ddayView.setText("+ "+preferences.getString("DDAY", "0")+"일");

        imageView.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                final Calendar cal = Calendar.getInstance();
                DatePickerDialog dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
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
                        ddayCalculate();
                        ddayStartView.setText(dateStart);
                        ddayView.setText("+ "+ ddayDate +" 일");
                        editor.putString("DDAY_START", dateStart);
                        editor.putString("DDAY", String.valueOf(ddayDate));
                        editor.commit();
                    }
                });
            }
        });

        return root;
    }

    public void ddayCalculate() {
        try {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

            Calendar todaCal = Calendar.getInstance();
            Calendar ddayCal = Calendar.getInstance();

            String[] date;
            date = dateStart.split("-");
            int year = Integer.parseInt(date[0]);
            int month = Integer.parseInt(date[1]) - 1;
            int day = Integer.parseInt(date[2]);

            ddayCal.set(year, month, day);

            long today = todaCal.getTimeInMillis() / (24 * 60 * 60 * 1000);
            long dday = ddayCal.getTimeInMillis() / (24 * 60 * 60 * 1000);

            long count = today - dday;

            ddayDate = count;
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }
    }
}
