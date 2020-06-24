package com.example.mymobil.record;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.mymobil.R;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;

/**
 * Create by Jinyeob on 2020. 06. 24.
 */

public class UploadActivity extends Fragment {

    final String uploadFilePath = "/storage/emulated/0/mobilrecord/";
    String uploadFileName = "recorded.mp3";
    TextView messageText;
    Button uploadButton;
    int serverResponseCode = 0;
    ProgressDialog dialog = null;
    String upLoadServerUri = "http://172.20.10.11:3000/api/photo";


    MediaPlayer player;
    MediaRecorder recorder;

    int playbackPosition = 0;
    @SuppressLint("SdCardPath")
    String RECORDED_FILE = "/sdcard/mobilrecord/";

    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.activity_upload, container, false);

        uploadButton = (Button) root.findViewById(R.id.uploadButton);
        messageText = (TextView) root.findViewById(R.id.messageText);

        //폴더 생성. 있으면 넘어감
        String sdPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        sdPath += "/mobilrecord";
        File file = new File(sdPath);
        file.mkdirs();

        //IP가져옴
        SharedPreferences sharedPreferences = Objects.requireNonNull(getActivity()).getSharedPreferences("shared", MODE_PRIVATE);
        upLoadServerUri = sharedPreferences.getString("SAVED_URL", "");
        assert upLoadServerUri != null;
        upLoadServerUri = upLoadServerUri.concat(":3000/api/photo"); //업로드 포트 붙이기

        //업로드 버튼 클릭이벤트
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = ProgressDialog.show(getActivity(), "", "Uploading file...", true);

                new Thread(new Runnable() {
                    public void run() {
                        Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                            public void run() {
                                messageText.setText("uploading started.....");
                            }
                        });

                        uploadFile(uploadFilePath + "" + uploadFileName);

                    }
                }).start();
            }
        });

        //재생
        Button recordBtn = (Button) root.findViewById(R.id.recordBtn);
        Button recordStopBtn = (Button) root.findViewById(R.id.recordStopBtn);
        Button playBtn = (Button) root.findViewById(R.id.playBtn);
        Button playStopBtn = (Button) root.findViewById(R.id.playStopBtn);

        recordBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //파일 이름 입력하는 다이얼로그
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                EditText input = new EditText(getActivity());

                builder.setTitle("녹음 제목");
                builder.setView(input);
                builder.setPositiveButton(getString(R.string.ok),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                uploadFileName=(input.getText().toString()).concat(".mp3");
                                RECORDED_FILE=RECORDED_FILE.concat(uploadFileName);

                                //파일경로 표시 (/storage/emulated/0/mobilrecord/)
                                messageText.setText("Uploading file path :\n" + RECORDED_FILE);

                                if (recorder != null) {
                                    recorder.stop();
                                    recorder.release();
                                    recorder = null;
                                }// TODO Auto-generated method stub
                                recorder = new MediaRecorder();
                                recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                                recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
                                recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
                                recorder.setOutputFile(RECORDED_FILE);
                                try {
                                    Toast.makeText(getActivity(),
                                            "녹음을 시작합니다.", Toast.LENGTH_SHORT).show();
                                    recorder.prepare();
                                    recorder.start();
                                } catch (Exception ex) {
                                    Log.e("SampleAudioRecorder", "Exception : ", ex);
                                }
                            }
                        });
                builder.setNegativeButton(getString(R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();

            }
        });

        recordStopBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (recorder == null)
                    return;

                recorder.stop();
                recorder.release();
                recorder = null;

                Toast.makeText(getActivity(),
                        "녹음이 중지되었습니다.", Toast.LENGTH_SHORT).show();
                // TODO Auto-generated method stub

            }
        });


        playBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            public void onClick(View view) {
                try {
                    playAudio(RECORDED_FILE);

                    Toast.makeText(getActivity(), "음악파일 재생 시작됨.", 2000).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        playStopBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            public void onClick(View view) {
                if (player != null) {
                    playbackPosition = player.getCurrentPosition();
                    player.pause();
                    Toast.makeText(getActivity(), "음악 파일 재생 중지됨.", 2000).show();
                }
            }
        });

        return root;
    }

    public int uploadFile(String sourceFileUri) {


        String fileName = sourceFileUri;

        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File sourceFile = new File(sourceFileUri);

        if (!sourceFile.isFile()) {

            dialog.dismiss();

            Log.e("uploadFile", "Source File not exist :"
                    + uploadFilePath + "" + uploadFileName);

            Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                @SuppressLint("SetTextI18n")
                public void run() {
                    messageText.setText("Source File not exist :"
                            + uploadFilePath + "" + uploadFileName);
                }
            });

            return 0;

        } else {
            try {

                // open a URL connection to the Servlet
                FileInputStream fileInputStream = new FileInputStream(sourceFile);
                URL url = new URL(upLoadServerUri);

                // Open a HTTP  connection to  the URL
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true); // Allow Inputs
                conn.setDoOutput(true); // Allow Outputs
                conn.setUseCaches(false); // Don't use a Cached Copy
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                conn.setRequestProperty("uploaded_file", fileName);

                dos = new DataOutputStream(conn.getOutputStream());

                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
                        + fileName + "\"" + lineEnd);

                dos.writeBytes(lineEnd);

                // create a buffer of  maximum size
                bytesAvailable = fileInputStream.available();

                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];

                // read file and write it into form...
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                while (bytesRead > 0) {

                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                }

                // send multipart form data necesssary after file data...
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                // Responses from the server (code and message)
                serverResponseCode = conn.getResponseCode();
                String serverResponseMessage = conn.getResponseMessage();

                Log.i("uploadFile", "HTTP Response is : "
                        + serverResponseMessage + ": " + serverResponseCode);

                if (serverResponseCode == 200) {
                    BufferedReader input = new BufferedReader(new InputStreamReader(conn.getInputStream()), 8192);
                    final StringBuilder response = new StringBuilder();
                    String strLine = null;
                    while ((strLine = input.readLine()) != null) {
                        response.append(strLine);
                    }
                    input.close();

                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {

                            String msg = "File Upload Completed.\n\n See uploaded file here : \n\n"
                                    + response.toString();

                            messageText.setText(msg);
                            Toast.makeText(getActivity(), "File Upload Complete.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                //close the streams //
                fileInputStream.close();
                dos.flush();
                dos.close();

            } catch (MalformedURLException ex) {

                dialog.dismiss();
                ex.printStackTrace();

                Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                    @SuppressLint("SetTextI18n")
                    public void run() {
                        messageText.setText("MalformedURLException Exception : check script url.");
                        Toast.makeText(getActivity(), "MalformedURLException",
                                Toast.LENGTH_SHORT).show();
                    }
                });

                Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
            } catch (Exception e) {

                dialog.dismiss();
                e.printStackTrace();

                getActivity().runOnUiThread(new Runnable() {
                    @SuppressLint("SetTextI18n")
                    public void run() {
                        messageText.setText("Got Exception : see logcat ");
                        Toast.makeText(getActivity(), "Got Exception : see logcat ",
                                Toast.LENGTH_SHORT).show();
                    }
                });
                Log.e("Upload file Exception", "Exception : " + e.getMessage(), e);
            }
            dialog.dismiss();
            return serverResponseCode;

        } // End else block
    }

    private void playAudio(String url) throws Exception {
        killMediaPlayer();

        player = new MediaPlayer();
        player.setDataSource(url);
        player.prepare();
        player.start();
    }

    public void onDestroy() {
        super.onDestroy();
        killMediaPlayer();
    }

    private void killMediaPlayer() {
        if (player != null) {
            try {
                player.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void onPause() {
        if (recorder != null) {
            recorder.release();
            recorder = null;
        }
        if (player != null) {
            player.release();
            player = null;
        }

        super.onPause();

    }


}
