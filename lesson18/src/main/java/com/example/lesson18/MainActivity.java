package com.example.lesson18;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    SeekBar sbWeight; //Ползунок
    Button btn1;
    Button btn2;
    LinearLayout.LayoutParams lParams1; //Первый слой с кнопкой
    LinearLayout.LayoutParams lParams2; //Второй слой с кнопкой

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sbWeight = (SeekBar) findViewById(R.id.sbWeight); //Элемент ползунка
        sbWeight.setOnSeekBarChangeListener(this); //Присваиваем обработчики событий ползунку

        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);

        lParams1 = (LinearLayout.LayoutParams) btn1.getLayoutParams(); //Размещаем первую кнопку на левый слой
        lParams2 = (LinearLayout.LayoutParams) btn2.getLayoutParams(); //Размещаем вторую кнопку на правый слой

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress,
                                  boolean fromUser) {

        int leftValue = progress; //Текущее значение
        int rightValue = seekBar.getMax() - progress; //Максимальное значение минус текущее
        // настраиваем вес
        lParams1.weight = leftValue; //Ширина левого слоя с кнопкой
        lParams2.weight = rightValue; //Ширина правого слоя с кнопкой

        // в текст кнопок пишем значения переменных
        btn1.setText(String.valueOf(leftValue));
        btn2.setText(String.valueOf(rightValue));

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

}