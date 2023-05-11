package com.example.control;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class Sockets extends AppCompatActivity {

    Button send_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sockets);

        send_button = findViewById(R.id.send_button);

        send_button.setOnClickListener(view -> {
            Intent gotoactivitycontrol = new Intent(getApplicationContext(), Control.class);
            startActivity(gotoactivitycontrol);
        });
    }
}