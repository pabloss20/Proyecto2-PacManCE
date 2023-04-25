package com.example.tarea2dii;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.tarea2dii.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

    }

    public void onBtnClick (View view){
        TextView textView = findViewById(R.id.finalstring);
        EditText editTextO = findViewById(R.id.stringO);
        EditText editTextT = findViewById(R.id.stringT);
        String newText = editTextO.getText().toString() + editTextT.getText().toString();
        textView.setText(newText);
    }
}