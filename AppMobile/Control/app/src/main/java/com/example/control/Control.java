package com.example.control;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.core.content.ContextCompat;

public class Control extends Sockets{

    public Control(){
        super();
    }

    // COntrol
    Button up, down, left, right;
    TextView score, health, X, Y, Z, direction, last;

    // Giroscopio
    SensorManager sensor_manager;
    Sensor sensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);

        // Giroscopio
        sensor_manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensor_manager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        X = findViewById(R.id.tvx);
        Y = findViewById(R.id.tvy);
        Z = findViewById(R.id.tvz);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);

        // Control
        up = findViewById(R.id.up_button);
        down = findViewById(R.id.down_button);
        left = findViewById(R.id.left_button);
        right = findViewById(R.id.right_button);
        health = findViewById(R.id.health_textview);
        score = findViewById(R.id.score_textview);
        direction = findViewById(R.id.direction_textview);
        last = findViewById(R.id.last_button);

        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendInfo("1");
                last.setText("Last button pressed: Up");
            }
        });
        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendInfo("2");
                last.setText("Last button pressed: Down");
            }
        });
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendInfo("3");
                last.setText("Last button pressed: Left");
            }
        });
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendInfo("4");
                last.setText("Last button pressed:  Right");
            }
        });
    }

    // Metodos para el giroscopio
    public void onResume(){
        super.onResume();
        sensor_manager.registerListener(giroscopio_listener, sensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    public void onStop(){
        super.onStop();
        sensor_manager.unregisterListener(giroscopio_listener);
    }

    public SensorEventListener giroscopio_listener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {

            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            X.setText("X: " + x);
            Y.setText("Y: " + y);
            Z.setText("Z: " + z);

            if (x == 0 && y == 0 && z == 0){direction.setText("Direction: Static");}

            if (x < 0)
            {
                sendInfo("1");
                direction.setText("Direction: Up");
            }
            if (y < 0)
            {
                sendInfo("3");
                direction.setText("Direction: Left");
            }
            if (x > 0)
            {
                sendInfo("2");
                direction.setText("Direction: Down");
            }
            if (y > 0)
            {
                sendInfo("4");
                direction.setText("Direction: Right");
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };
}