package com.moji.lee.moji.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.moji.lee.moji.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class VoiceCommandFragment extends SherlockFragment {
    View v;
    private Intent i;
    private TextView smartMirror;
    private ImageButton micBtn;
    private Button stopBtn;
    private SpeechRecognizer mRecognizer;

    private String input_sst; // 명령 결과
    private String enCommand;

    private static String smartmirror_ip = "192.168.58.75"; // 스마트 미러의 할당받은 ip 주소

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.voicecommand, container, false);

        smartMirror = (TextView) v.findViewById(R.id.smartMirror);
        micBtn = (ImageButton) v.findViewById(R.id.micBtn);
        stopBtn = (Button) v.findViewById(R.id.stopBtn);


        micBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.micBtn) {

                    mRecognizer = SpeechRecognizer.createSpeechRecognizer(getSherlockActivity());
                    mRecognizer.setRecognitionListener(listener);
                    i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH); // Intent 생성
                    i.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getSherlockActivity().getPackageName()); // 호출한 패키지
                    i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR"); // 인식한 언어 설정
                    i.putExtra(RecognizerIntent.EXTRA_PROMPT, "말해주세요"); // 유저에게 보여줄 문자
                    mRecognizer.startListening(i);

                }
            }
        });

        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mRecognizer != null) {
                    mRecognizer.destroy();
                }
            }
        });

        return v;
    }

    private RecognitionListener listener = new RecognitionListener() {
        /** 음성인식 준비 완료 */
        @Override
        public void onReadyForSpeech(Bundle params) {
            //Toast.makeText(getSherlockActivity(),"음성 인식 시작",Toast.LENGTH_SHORT).show();
        }

        /** 음성인식 시작 */
        @Override
        public void onBeginningOfSpeech() {
            Toast.makeText(getSherlockActivity(),"음성 인식 시작",Toast.LENGTH_SHORT).show();
        }

        /** 입력 소리 변경 시 */
        @Override
        public void onRmsChanged(float rmsdB) {
        }

        /** 더 많은 소리를 받을 경우 */
        @Override
        public void onBufferReceived(byte[] buffer) {
        }

        /** 음성인식 끝남 */
        @Override
        public void onEndOfSpeech() {
            Toast.makeText(getSherlockActivity(),"음성 인식 종료",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(int error) {
            if(mRecognizer != null) {
                mRecognizer.destroy();
            }
            mRecognizer = SpeechRecognizer.createSpeechRecognizer(getActivity());
            mRecognizer.setRecognitionListener(listener);
            mRecognizer.startListening(i);
        }

        /** 음성인식 결과 받음 */
        @Override
        public void onResults(Bundle results) {
            String key = "";
            key = SpeechRecognizer.RESULTS_RECOGNITION;
            input_sst = results.getStringArrayList(key).get(0);
            if(input_sst.equals("정지")) {
                Toast.makeText(getSherlockActivity(),"음성 명령이 정지 되었습니다",Toast.LENGTH_LONG).show();
                mRecognizer.destroy();
                input_sst = "";
                return;
            }
            smartMirror.setText("" + input_sst);

            Toast.makeText(getSherlockActivity(),"말씀하신 명령 : "+input_sst,Toast.LENGTH_SHORT).show();

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            try {
                enCommand = URLEncoder.encode(input_sst, "UTF-8");
                try {

                /* 라즈베리 파이 IP : 9090 (port) */
                    String urlRasp = "http://"+smartmirror_ip+":9090/android.do?command="+enCommand;
                    URL url = new URL(urlRasp);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                    BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

                    String response = null;

                    while (true) {
                        response = br.readLine();
                        if (response == null) break;
                    }

                    urlConnection.disconnect();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            mRecognizer.destroy();

            mRecognizer = SpeechRecognizer.createSpeechRecognizer(getActivity());
            mRecognizer.setRecognitionListener(listener);
            mRecognizer.startListening(i);


        }

        /** 음성인식 결과 일부 유효 */
        @Override
        public void onPartialResults(Bundle partialResults) {
            String key = "";
            key = SpeechRecognizer.RESULTS_RECOGNITION;
            String input_sst = partialResults.getStringArrayList(key).get(0);
            smartMirror.setText("" + input_sst);
            Toast.makeText(getSherlockActivity(),"You said : "+input_sst,Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onEvent(int eventType, Bundle params) {

        }
    };



}