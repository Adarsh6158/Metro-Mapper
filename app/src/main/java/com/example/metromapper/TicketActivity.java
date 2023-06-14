package com.example.metromapper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TicketActivity extends AppCompatActivity {
    Button idBtnMakePayment;
    EditText idEdtAmount, idEdtUpi, idEdtName, idEdtDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);

        idEdtAmount = findViewById(R.id.idEdtAmount);
        idEdtUpi = findViewById(R.id.idEdtUpi);
        idEdtName = findViewById(R.id.idEdtName);
        idEdtDescription = findViewById(R.id.idEdtDescription);

        idBtnMakePayment = findViewById(R.id.idBtnMakePayment);
        idBtnMakePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if all EditText fields are filled
                if (TextUtils.isEmpty(idEdtAmount.getText()) ||
                        TextUtils.isEmpty(idEdtUpi.getText()) ||
                        TextUtils.isEmpty(idEdtName.getText()) ||
                        TextUtils.isEmpty(idEdtDescription.getText())) {
                    Toast.makeText(TicketActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Check UPI format using regex
                String upiPattern = "([a-zA-Z0-9]+@[a-zA-Z0-9]*)|([a-zA-Z]+[0-9]+@[a-zA-Z0-9]*)";



                String upiText = idEdtUpi.getText().toString().trim();
                if (!upiText.matches(upiPattern)) {
                    Toast.makeText(TicketActivity.this, "Invalid UPI format", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Display payment successful message
                Toast.makeText(TicketActivity.this, "Payment successful", Toast.LENGTH_SHORT).show();

                // Delay redirection to SearchActivity by 2 seconds
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Redirect to SearchActivity
                        Intent intent = new Intent(TicketActivity.this, QrActivity.class);
                        startActivity(intent);
                    }
                }, 2000);
            }
        });
    }
}
