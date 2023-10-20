package com.example.lesson17;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    LinearLayout llMain;
    RadioGroup rgGravity;
    EditText etName;
    Button btnCreate;
    Button btnClear;

    int wrapContent = LinearLayout.LayoutParams.WRAP_CONTENT;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        llMain = (LinearLayout) findViewById(R.id.llMain);
        rgGravity = (RadioGroup) findViewById(R.id.rgGravity);
        etName = (EditText) findViewById(R.id.etName);

        btnCreate = (Button) findViewById(R.id.btnCreate);
        btnCreate.setOnClickListener(this::onClick);

        btnClear = (Button) findViewById(R.id.btnClear);
        btnClear.setOnClickListener(this::onClick);
    }

    public void onClick(View v) {

        if(v.getId() == R.id.btnCreate){
            // Создание LayoutParams c шириной и высотой по содержимому
            LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(
                    wrapContent, wrapContent);

            // определяем, какой RadioButton "чекнут" и
            // соответственно заполняем btnGravity
            if(rgGravity.getCheckedRadioButtonId() == R.id.rbLeft) lParams.gravity = Gravity.LEFT;
            else if (rgGravity.getCheckedRadioButtonId() == R.id.rbCenter) lParams.gravity = Gravity.CENTER_HORIZONTAL;
            else if (rgGravity.getCheckedRadioButtonId() == R.id.rbRight) lParams.gravity = Gravity.RIGHT;

            //Проверяем введено ли название кнопки
            if(etName.getText().toString().isEmpty()){
                Toast.makeText(this, "Введите название кнопки", Toast.LENGTH_SHORT).show();
            }else {
                // создаем Button, пишем текст и добавляем в LinearLayout
                Button btnNew = new Button(this);
                btnNew.setText(etName.getText().toString());
                llMain.addView(btnNew, lParams);
            }
        } else if (v.getId() == R.id.btnClear) {
            llMain.removeAllViews(); //Очистка элементов с динамического слоя
            Toast.makeText(this, "Удалено", Toast.LENGTH_SHORT).show();
        }
    }
}