package com.uazfanapp.lesson12;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "myLogs";
    TextView tvOut;
    Button btnOk;
    Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // найдем View-элементы
        Log.d(TAG, "найдем View-элементы");
        tvOut = (TextView) findViewById(R.id.tvOut);
        btnOk = (Button) findViewById(R.id.btnOk);
        btnCancel = (Button) findViewById(R.id.btnCancel);

        View.OnClickListener oclBtn = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // по id определеяем кнопку, вызвавшую этот обработчик
                Log.d(TAG, "по id определяем кнопку, вызвавшую этот обработчик");
                if(v.getId() == R.id.btnOk) {
                    Log.d(TAG, "кнопка ОК");
                    tvOut.setText("Нажата кнопка ОК");
                    Toast.makeText(MainActivity.this, "Нажата кнопка ОК", Toast.LENGTH_LONG).show();
                }else if(v.getId() == R.id.btnCancel) {
                    Log.d(TAG, "кнопка Cancel");
                    tvOut.setText("Нажата кнопка Cancel");
                    Toast.makeText(MainActivity.this, "Нажата кнопка Cancel", Toast.LENGTH_LONG).show();
                }
            }
        };

        // присваиваем обработчик кнопкам
        Log.d(TAG, "присваиваем обработчик кнопкам");
        btnOk.setOnClickListener(oclBtn);
        btnCancel.setOnClickListener(oclBtn);
    }
}