package com.example.control;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Control extends Sockets{

    public Control(){
        super();
    }

    // COntrol
    Button up_button, down_button, left_button, right_button;
    TextView score, health, X, Y, Z;

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
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };
}