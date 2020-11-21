package com.example.mymobil.record;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioFormat;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.mymobil.MainActivity;
import com.example.mymobil.R;
import com.example.mymobil.setting.SettingActivity;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Objects;

import omrecorder.AudioChunk;
import omrecorder.AudioRecordConfig;
import omrecorder.OmRecorder;
import omrecorder.PullTransport;
import omrecorder.PullableSource;
import omrecorder.Recorder;

import static android.content.Context.MODE_PRIVATE;

/**
 * Create by Jinyeob on 2020. 06. 24.
 * Update by Jinyeob on 2020. 06. 28.
 */

public class RecordFragment extends Fragment {

    final String uploadFilePath = "/storage/emulated/0/mobilrecord/";
    String uploadFileName = "recorded.wav";
    TextView messageText;
    Button uploadButton;
    int serverResponseCode = 0;
    ProgressDialog dialog = null;
    String upLoadServerUri = "http://172.20.10.11:3000/api/photo";
    String upLoadServerUriPost = "http://172.20.10.11";

    Recorder recorder;

    int playbackPosition = 0;
    @SuppressLint("SdCardPath")
    String RECORDED_FILE = "/sdcard/mobilrecord/";

    ArrayList<item_list> recordDataList;
    private File file;

    ListAdapter myAdapter;
    Button recordBtn;
    Button recordStopBtn;
    Button voiceStopBtn;

    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.activity_upload, container, false);

        //uploadButton = (Button) root.findViewById(R.id.uploadButton);
        messageText = (TextView) root.findViewById(R.id.messageText);
        TextView current_record_textView = root.findViewById(R.id.current_record);

        //폴더 생성. 있으면 넘어감
        String sdPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        sdPath += "/mobilrecord";
        File file = new File(sdPath);
        file.mkdirs();

        //IP가져옴
        SharedPreferences sharedPreferences = Objects.requireNonNull(getActivity()).getSharedPreferences("shared", MODE_PRIVATE);
        upLoadServerUri = sharedPreferences.getString("SAVED_URL", "");
        assert upLoadServerUri != null;
        upLoadServerUriPost = upLoadServerUri.concat(":3000/data"); //post용
        upLoadServerUri = upLoadServerUri.concat(":3000/api/photo"); //업로드 포트 붙이기

        /* 올바른 URL인지 검사한다. */
        if (upLoadServerUri.equals("") || !URLUtil.isValidUrl(upLoadServerUri)) {
            Toast.makeText(getActivity(), "URL 주소를 입력해주세요.", Toast.LENGTH_LONG).show();

            Intent it = new Intent(getActivity(), SettingActivity.class);
            startActivity(it);

            getActivity().finish();
        }

        //재생
        recordBtn = (Button) root.findViewById(R.id.recordBtn);
        recordStopBtn = (Button) root.findViewById(R.id.recordStopBtn);
        voiceStopBtn = (Button) root.findViewById(R.id.voicestop);

        recordBtn.setEnabled(true);
        recordStopBtn.setEnabled(false);
        voiceStopBtn.setEnabled(false);

        recordBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                recordBtn.setEnabled(false);
                recordStopBtn.setEnabled(true);

                //파일 이름 입력하는 다이얼로그
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                EditText input = new EditText(getActivity());

                builder.setTitle("녹음 제목");
                builder.setView(input);
                builder.setPositiveButton(getString(R.string.ok),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                uploadFileName = (input.getText().toString()).concat(".wav");
                                Log.e("filename", "filename: " + uploadFileName);

                                RECORDED_FILE = "/sdcard/mobilrecord/";
                                RECORDED_FILE = RECORDED_FILE.concat(uploadFileName);
                                Log.e("RECORDED_FILE", "RECORDED_FILE: " + RECORDED_FILE);

                                //파일경로 표시 (/storage/emulated/0/mobilrecord/)
                                messageText.setText("Uploading file path :\n" + RECORDED_FILE);

                                setupRecorder();

                                assert recorder != null;
                                recorder.startRecording();

                                try {
                                    Toast.makeText(getActivity(),
                                            "녹음을 시작합니다.", Toast.LENGTH_SHORT).show();

                                    current_record_textView.setText("-------------- 녹음 중 ------------");
                                    messageText.setText("");
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

                recordStopBtn.setEnabled(false);
                recordBtn.setEnabled(true);

                current_record_textView.setText("-------------- 녹음 정지 --------------");
                messageText.setText("");

                if (recorder == null)
                    return;

                try {
                    recorder.stopRecording();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Toast.makeText(getActivity(),
                        "녹음이 중지되었습니다.", Toast.LENGTH_SHORT).show();

                refresh();

            }
        });

        voiceStopBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                voiceStopBtn.setEnabled(false);
                new Thread() {
                    public void run() {
                        postInfo("voicestop");
                    }
                }.start();
            }
        });

        /**
         리스트뷰 설정
         */
        this.InitializesettingData();

        ListView listView = (ListView) root.findViewById(R.id.listView_record);
        myAdapter = new ListAdapter(getActivity(), recordDataList);

        listView.setAdapter(myAdapter);

        //리스트뷰 이벤트리스너
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                voiceStopBtn.setEnabled(true);
                String recordName = "default";
                recordName = myAdapter.getItem(position).getRecordName();
                //터치한 목록 이름 토스트 ㅎ
                Toast.makeText(getActivity(), recordName, Toast.LENGTH_SHORT).show();

                uploadEvent(recordName);
                try {
                    Thread.sleep(1000) ;
                } catch (Exception e) {
                    e.printStackTrace() ;
                }
                new Thread() {
                    public void run() {
                        postInfo("voice");
                    }
                }.start();

            }
        });
        return root;
    }

    public void refresh() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();
    }

    private void postInfo(String value) {
        try {
            Connection.Response res = Jsoup.connect(upLoadServerUriPost)
                    .data("data", value)
                    .timeout(5000)
                    .maxBodySize(0)
                    .method(Connection.Method.POST)
                    .execute();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setupRecorder() {
        recorder = OmRecorder.wav(
                new PullTransport.Default(mic(), new PullTransport.OnAudioChunkPulledListener() {
                    @Override public void onAudioChunkPulled(AudioChunk audioChunk) {
                        //animateVoice((float) (audioChunk.maxAmplitude() / 200.0));
                    }
                }), file());
    }

    private void animateVoice(final float maxPeak) {
        recordBtn.animate().scaleX(1 + maxPeak).scaleY(1 + maxPeak).setDuration(10).start();
    }

    private PullableSource mic() {
        return new PullableSource.Default(
                new AudioRecordConfig.Default(
                        MediaRecorder.AudioSource.MIC, AudioFormat.ENCODING_PCM_16BIT,
                        AudioFormat.CHANNEL_IN_MONO, 44100
                )
        );
    }

    @NonNull
    private File file() {
        return new File(uploadFilePath, uploadFileName);
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

                            String msg = "File Upload Completed."/*\n\n See uploaded file here : \n\n"
                                    + response.toString()*/;

                            messageText.setText(msg);
                            //Toast.makeText(getActivity(), "File Upload Complete.",Toast.LENGTH_SHORT).show();
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
                        messageText.setText("Got Exception");
                        //Toast.makeText(getActivity(), "Got Exception",Toast.LENGTH_SHORT).show();
                    }
                });
                Log.e("Upload file Exception", "Exception : " + e.getMessage(), e);
            }
            dialog.dismiss();
            return serverResponseCode;

        } // End else block
    }

    /* 리스트뷰 목록 추가시 여기에 */
    public void InitializesettingData() {
        recordDataList = new ArrayList<item_list>();

        String rootSD = Environment.getExternalStorageDirectory().toString();
        file = new File(rootSD + "/mobilrecord");
        File list[] = file.listFiles();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Log.d("TAG", "파일 수정일시 : " + simpleDateFormat.format(file.lastModified()));

        for (int i = 0; i < list.length; i++) {
            recordDataList.add(new item_list(list[i].getName(), simpleDateFormat.format(list[i].lastModified())));
        }
    }

    public void uploadEvent(String recordFileName) {
        dialog = ProgressDialog.show(getActivity(), "", "Uploading file...", true);

        new Thread(new Runnable() {
            public void run() {
                Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                    public void run() {
                        messageText.setText("uploading started.....");
                    }
                });

                uploadFile(uploadFilePath + "" + recordFileName);

            }
        }).start();
    }
}
