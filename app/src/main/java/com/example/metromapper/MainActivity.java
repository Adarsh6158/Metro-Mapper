package com.example.metromapper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ImageView logo;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logo = findViewById(R.id.logo);
        textView = findViewById(R.id.text);

        openingAnimation();

        Handler handler = new Handler();
        Runnable delayedRunnable = new Runnable() {
            @Override
            public void run() {
                // This code will be executed after the delay
                Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                startActivity(intent);
                finish();
            }
        };

        int delayMillis = 1500; // Delay duration in milliseconds
        handler.postDelayed(delayedRunnable, delayMillis);
    }

    public void openingAnimation(){
        logo.animate().scaleXBy(0.5f).scaleYBy(0.5f).setDuration(2000);
        textView.animate().translationY(200).setDuration(2000);
    }
}