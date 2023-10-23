package com.example.lesson21;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btnActTwo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnActTwo = (Button) findViewById(R.id.btnActTwo);
        btnActTwo.setOnClickListener(this::onClick);
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnActTwo:
                // TODO Call second activity
                Intent intent = new Intent(this, TwoActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}