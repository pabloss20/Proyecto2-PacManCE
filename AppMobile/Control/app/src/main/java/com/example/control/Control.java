package com.example.control;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.core.content.ContextCompat;

public class Control extends Sockets{

    public Control()
    {
        super();
    }

    // Control
    Button up, down, left, right;
    TextView score, health, X, Y, Z, direction, last;

    // Giroscopio
    SensorManager sensor_manager;
    Sensor sensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);

        server_ip = getIntent().getStringExtra("Server_IP");
        server_port = 5001;

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

        Log.d("DIRECCION IP", server_ip);

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
        sensor_manager.registerListener(giroscopio_listener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void onStop(){
        super.onStop();
        sensor_manager.unregisterListener(giroscopio_listener);
    }

    public SensorEventListener giroscopio_listener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {

            // Toma los valores x, y, z del giroscopio
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            // Actualiza el texto de los text_view con el redondeo
            X.setText("X: " + Math.round(x * 100.0) / 100.0);
            Y.setText("Y: " + Math.round(y * 100.0) / 100.0);
            Z.setText("Z: " + Math.round(y * 100.0) / 100.0);

            // Si los valores de x, y, z son 0 actualiza el text_view como Static
            if (x == 0 && y == 0 && z == 0){direction.setText("Direction: Static");}

            // Mover hacia arriba
            if (x < -1)
            {
                sendInfo("1");
                direction.setText("Direction: Up");
            }
            // Mover hacia la izquierda
            if (y < -1)
            {
                sendInfo("3");
                direction.setText("Direction: Left");
            }
            // Mover hacia abajo
            if (x > 1)
            {
                sendInfo("2");
                direction.setText("Direction: Down");
            }
            // Mover hacia la derecha
            if (y > 1)
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