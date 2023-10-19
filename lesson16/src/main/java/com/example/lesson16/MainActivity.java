package com.example.lesson16;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // создание LinearLayout
        LinearLayout linLayout = new LinearLayout(this);
        // установим вертикальную ориентацию
        linLayout.setOrientation(LinearLayout.VERTICAL);
        // создаем LayoutParams
        ViewGroup.LayoutParams linLayoutParam = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        // устанавливаем linLayout как корневой элемент экрана
        setContentView(linLayout, linLayoutParam);

        //Создаём параметры нового слоя
        ViewGroup.LayoutParams lpView = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        TextView tv = new TextView(this); //Создаём текстовое поле
        tv.setText("TextView"); //Добавляем текст в текстовое поле
        tv.setLayoutParams(lpView); //Добавляем текстовое поле на слой lpView
        linLayout.addView(tv); //Добавляем текстовое поле на экран

        Button btn = new Button(this); //Создаём кнопку
        btn.setText("Button"); //Присваиваем кнопке текст
        linLayout.addView(btn, lpView); //Добавляем слой с текстовым полем и кнопку на экран

        //Создаём параметры нового слоя с отступом
        LinearLayout.LayoutParams leftMarginParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        leftMarginParams.leftMargin = 50;

        Button btn1 = new Button(this); //Создаём ещё одну кнопку
        btn1.setText("Button1"); //Добавляем кнопке текст
        linLayout.addView(btn1, leftMarginParams); //Добавляем слой с отступом и кнопкой на экран

        //Создаём новый слой с выравниванием
        LinearLayout.LayoutParams rightGravityParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rightGravityParams.gravity = Gravity.RIGHT;

        Button btn2 = new Button(this); //Создаём ещё одну кнопку
        btn2.setText("Button2"); //Добавляем кнопке текст
        linLayout.addView(btn2, rightGravityParams); //Добавляем кнопку и слой с выравниванием на экран
    }
}