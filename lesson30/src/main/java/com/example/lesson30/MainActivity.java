package com.example.lesson30;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements OnClickListener {
    TextView tvText;
    Button btnColor;
    Button btnAlign;

    final int REQUEST_CODE_COLOR = 1;
    final int REQUEST_CODE_ALIGN = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvText = (TextView) findViewById(R.id.tvText);

        btnColor = (Button) findViewById(R.id.btnColor);
        btnAlign = (Button) findViewById(R.id.btnAlign);

        btnColor.setOnClickListener(this);
        btnAlign.setOnClickListener(this);
    }

    @Override
    @Deprecated
    public void onClick(View v) {
        // TODO Auto-generated method stub
        Intent intent;

        if(v.getId() == R.id.btnColor){
            intent = new Intent(this, ColorActivity.class);
            startActivityForResult(intent, REQUEST_CODE_COLOR);
        } else if (v.getId() == R.id.btnAlign) {
            intent = new Intent(this, AlignActivity.class);
            startActivityForResult(intent, REQUEST_CODE_ALIGN);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // запишем в лог значения requestCode и resultCode
        Log.d("myLogs", "requestCode = " + requestCode + ", resultCode = " + resultCode);
        // если пришло ОК

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_COLOR){
                int color = data.getIntExtra("color", Color.WHITE);
                tvText.setTextColor(color);
            } else if (requestCode == REQUEST_CODE_ALIGN) {
                int align = data.getIntExtra("alignment", Gravity.LEFT);
                tvText.setGravity(align);
            }

            // если вернулось не ОК
        } else {
            Toast.makeText(this, "Wrong result", Toast.LENGTH_SHORT).show();
        }
    }
}