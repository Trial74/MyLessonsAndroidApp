package com.example.lesson30;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
public class ColorActivity extends AppCompatActivity implements OnClickListener {
    Button btnRed;
    Button btnGreen;
    Button btnBlue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color);

        btnRed = (Button) findViewById(R.id.btnRed);
        btnGreen = (Button) findViewById(R.id.btnGreen);
        btnBlue = (Button) findViewById(R.id.btnBlue);

        btnRed.setOnClickListener(this);
        btnGreen.setOnClickListener(this);
        btnBlue.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();

        if(v.getId() == R.id.btnRed) intent.putExtra("color", Color.RED);
        else if (v.getId() == R.id.btnGreen) intent.putExtra("color", Color.GREEN);
        else if (v.getId() == R.id.btnBlue) intent.putExtra("color", Color.BLUE);

        setResult(RESULT_OK, intent);
        finish();
    }
}