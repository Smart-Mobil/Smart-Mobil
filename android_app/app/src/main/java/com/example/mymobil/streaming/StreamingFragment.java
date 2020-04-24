package com.example.mymobil.streaming;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.mymobil.R;
import com.example.mymobil.setting.SettingActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;

/*
 * Update by Jinyeob on 2020.04.22
 * Add capture button
 */

/*
 * Update by Jinyeob on 2020.04.24
 * Url 셋팅 추가 완료
 * Url 검사하는 코드 추가
 */
public class StreamingFragment extends Fragment {
    private WebView mWebView; // 웹뷰 선언
    private WebSettings mWebSettings; //웹뷰세팅
    private String url = ""; //UV4L 링크
    private Button btn_capture;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_streaming, container, false);

        grantExternalStoragePermission();

        btn_capture = root.findViewById(R.id.button_capture);

        if (btn_capture.isEnabled()) {
            btn_capture.setEnabled(false);
        }
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("shared", MODE_PRIVATE);
        url = sharedPreferences.getString("SAVED_URL", "");
        url = url.concat(":9090/stream");

        /* 저장된 URL이 있는지 먼저 검사한다. */
        if (url.equals("")) {
            Toast.makeText(getActivity(), "URL 주소를 입력해주세요.", Toast.LENGTH_LONG).show();

            Intent it = new Intent(getActivity(), SettingActivity.class);
            startActivity(it);

            getActivity().finish();
        }

        if (URLUtil.isValidUrl(url)) {

            // 웹뷰 시작
            mWebView = root.findViewById(R.id.webView);

            mWebView.setWebViewClient(new WebViewClient()); // 클릭시 새창 안뜨게
            mWebSettings = mWebView.getSettings(); //세부 세팅 등록
            mWebSettings.setJavaScriptEnabled(true); // 웹페이지 자바스클비트 허용 여부
            mWebSettings.setSupportMultipleWindows(false); // 새창 띄우기 허용 여부
            mWebSettings.setJavaScriptCanOpenWindowsAutomatically(false); // 자바스크립트 새창 띄우기(멀티뷰) 허용 여부
            mWebSettings.setLoadWithOverviewMode(true); // 메타태그 허용 여부
            mWebSettings.setUseWideViewPort(true); // 화면 사이즈 맞추기 허용 여부
            mWebSettings.setSupportZoom(true); // 화면 줌 허용 여부
            mWebSettings.setBuiltInZoomControls(false); // 화면 확대 축소 허용 여부
            mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); // 컨텐츠 사이즈 맞추기
            mWebSettings.setCacheMode(WebSettings.LOAD_NO_CACHE); // 브라우저 캐시 허용 여부
            mWebSettings.setDomStorageEnabled(true); // 로컬저장소 허용 여부
            mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

            mWebView.setInitialScale(1);

            mWebView.loadUrl(/*url*/url); // 웹뷰에 표시할 웹사이트 주소, 웹뷰 시작

            btn_capture.setEnabled(true);

            btn_capture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    takeScreenshot();
                }
            });
        } else {
            Toast.makeText(getActivity(), "잘못된 URL 주소입니다. URL 주소를 입력해주세요.", Toast.LENGTH_LONG).show();

            Intent it = new Intent(getActivity(), SettingActivity.class);
            startActivity(it);

            getActivity().finish();
        }
        return root;
    }

    private void takeScreenshot() {
        try {
            // image naming and path  to include sd card  appending name you choose for file
            // 저장할 주소 + 이름
            String mPath = Environment.getExternalStorageDirectory().toString() + "/DCIM/Screenshots/" + "MyMobil_Streaming" + ".jpg";

            // create bitmap screen capture
            // 화면 이미지 만들기
            View v1 = getActivity().getWindow().getDecorView().getRootView();
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);

            // 이미지 파일 생성
            File imageFile = new File(mPath);
            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();
            Toast.makeText(getActivity(), "캡쳐완료" + mPath, Toast.LENGTH_SHORT).show();
        } catch (Throwable e) {
            // Several error may come out with file handling or OOM
            Toast.makeText(getActivity(), "캡쳐실패", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private boolean grantExternalStoragePermission() {
        if (Build.VERSION.SDK_INT >= 23) {

            if (Objects.requireNonNull(getActivity()).checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

                return false;
            }
        } else {
            Toast.makeText(getActivity(), "External Storage Permission is Grant", Toast.LENGTH_SHORT).show();
            return true;
        }
    }
}