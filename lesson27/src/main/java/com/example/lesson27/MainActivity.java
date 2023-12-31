package com.example.lesson27;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
public class MainActivity extends AppCompatActivity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnTime = (Button) findViewById(R.id.btnTime);
        Button btnDate = (Button) findViewById(R.id.btnDate);

        btnTime.setOnClickListener(this);
        btnDate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;

        if(v.getId() == R.id.btnTime){
            intent = new Intent("ru.startandroid.intent.action.showtime");
            startActivity(intent);
        } else if (v.getId() == R.id.btnDate) {
            intent = new Intent("ru.startandroid.intent.action.showdate");
            startActivity(intent);
        }
    }
}