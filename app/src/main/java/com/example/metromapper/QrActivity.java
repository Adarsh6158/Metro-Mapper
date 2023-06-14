package com.example.metromapper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class QrActivity extends AppCompatActivity {

    private Button downloadButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);

        downloadButton = findViewById(R.id.downloadButton);
        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform download logic here

                // Display toast message
                Toast.makeText(QrActivity.this, "Download started", Toast.LENGTH_SHORT).show();
            }
        });

        // Delay redirection to SearchActivity by 5 seconds
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Redirect to SearchActivity
                Intent intent = new Intent(QrActivity.this, SearchActivity2.class);
                startActivity(intent);
                finish(); // Optional: If you want to finish the current activity after redirection
            }
        }, 5000); // 5000 milliseconds = 5 seconds
    }
}
