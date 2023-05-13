package com.example.control;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class Control extends Sockets {

    public Control(){
        super();
    }

    Button up_button;
    Button down_button;
    Button left_button;
    Button right_button;
    TextView health;
    TextView score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);

        up_button = findViewById(R.id.up_button);
        down_button = findViewById(R.id.down_button);
        left_button = findViewById(R.id.left_button);
        right_button = findViewById(R.id.right_button);
        health = findViewById(R.id.health_textview);
        score = findViewById(R.id.score_textview);

        up_button.setOnClickListener(view -> super.sendInfo("1"));
        down_button.setOnClickListener(view -> super.sendInfo("2"));
        left_button.setOnClickListener(view -> super.sendInfo("3"));
        right_button.setOnClickListener(view -> super.sendInfo("4"));
    }
}