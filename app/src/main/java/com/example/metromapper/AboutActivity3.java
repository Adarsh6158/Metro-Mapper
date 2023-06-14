package com.example.metromapper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AboutActivity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about3);

        Button buttonAbout = findViewById(R.id.buttonAbout);
        Button buttonSearch = findViewById(R.id.buttonSearch);
        Button buttonFare = findViewById(R.id.buttonFare);
        Button buttonMaps = findViewById(R.id.buttonMaps);


        buttonAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the AboutActivity
                Intent intent = new Intent(AboutActivity3.this, NoidaActivity.class);
                startActivity(intent);
            }
        });

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the SearchActivity
                Intent intent = new Intent(AboutActivity3.this, SearchActivity3.class);
                startActivity(intent);
            }
        });

        buttonFare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the FareActivity
                Intent intent = new Intent(AboutActivity3.this, FareActivity3.class);
                startActivity(intent);
            }
        });

        buttonMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the MapsActivity
                Intent intent = new Intent(AboutActivity3.this, MapsActivity3.class);
                startActivity(intent);
            }
        });
    }
}
