package com.yamichi.speechrecognizer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private SpeechRecognizer mRecognizer;
    private static String TAG = "Sample";
    private TextView output;
    private TextView particalText;
    private Intent intent;
    private RecognitionListener mRecognitionListener = new RecognitionListener() {
        @Override
        public void onError(int error) {
            if (error == SpeechRecognizer.ERROR_NO_MATCH) {
                Log.d(TAG, "NO MATCH Error");
                output.setText("NO MATCH Error");
            }
            else if(error == SpeechRecognizer.ERROR_SPEECH_TIMEOUT){
                Log.d(TAG, "TIMEOUT Error");
                output.setText("TIMEOUT Error");
            }else{
                Log.d(TAG, "Recognition Error: " + error);
                output.setText("Recognition Error: " + error);
            }
        }

        @Override
        public void onResults(Bundle results) {
            ArrayList<String> values = results
                    .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            String val = values.get(0);
            Log.d(TAG, "認識結果: " + val);
            output.setText("認識結果: " + val);
        }

        @Override public void onBeginningOfSpeech() {}
        @Override public void onBufferReceived(byte[] arg0) {}
        @Override public void onEndOfSpeech() {}
        @Override public void onEvent(int arg0, Bundle arg1) {}
        @Override public void onPartialResults(Bundle arg0) {
            ArrayList<String> values = arg0
                    .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            String val = values.get(0);
            Log.d(TAG, "認識中: " + val);
            particalText.setText("認識中: " + val);
        }
        @Override public void onReadyForSpeech(Bundle arg0) {}
        @Override public void onRmsChanged(float arg0) {}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btStart = findViewById(R.id.btStart);
        startListener startListener = new startListener();
        btStart.setOnClickListener(startListener);

        Button btStop = findViewById(R.id.btStop);
        stopListener stopListener = new stopListener();
        btStop.setOnClickListener(stopListener);

        output = findViewById(R.id.textView);

        particalText = findViewById(R.id.textView2);

        intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
                getPackageName());
        intent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);

        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.RECORD_AUDIO)
            != PackageManager.PERMISSION_GRANTED){
            String[] permissions = {Manifest.permission.RECORD_AUDIO};
            ActivityCompat.requestPermissions(MainActivity.this, permissions, 1000);
        }
        mRecognizer = SpeechRecognizer.createSpeechRecognizer(MainActivity.this);
        mRecognizer.setRecognitionListener(mRecognitionListener);
    }

    private class startListener implements View.OnClickListener{
        @Override
        public void onClick(View view){
//            if (mRecognizer != null) {
//                mRecognizer.destroy();
//            }
            mRecognizer.startListening(intent);
            output.setText("start");
        }
    }

    private class stopListener implements View.OnClickListener{
        @Override
        public void onClick(View view){
            mRecognizer.stopListening();
            output.setText("stop");
        }
    }
}