# Extras - передаем данные с помощью Intent

---

На прошлых уроках мы узнали, что такое __Intent__ и как им пользоваться. Из одного __Activity__ мы просто вызывали другое, передавая __action__. Теперь научимся передавать данные. Сделаем простейшее приложение. На первом экране мы будем вводить наше имя и фамилию, а второй экран будет эти данные отображать. Передавать данные будем внутри __Intent__.

Открываем __activity_main.xml__ и рисуем экран с полями и кнопкой отправки:

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="fill_parent"
    android:layout_height="fill_parent"
    android:orientation="vertical">
    <TextView
        android:layout_width="fill_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="10dp"
        android:gravity="center_horizontal"
        android:text="Input your Name">
    </TextView>
    <TableLayout
        android:id="@+id/tableLayout1"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_margin="10dp"
        android:stretchColumns="1">
        <TableRow
            android:id="@+id/tableRow1"
            android:layout_width="match_parent"
            android:layout_height="wrap_content">
            <TextView
                android:id="@+id/textView1"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:text="First Name">
            </TextView>
            <EditText
                android:id="@+id/etFName"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_marginLeft="5dp">
                <requestFocus>
                </requestFocus>
            </EditText>
        </TableRow>
        <TableRow
            android:id="@+id/tableRow2"
            android:layout_width="match_parent"
            android:layout_height="wrap_content">
            <TextView
                android:id="@+id/textView2"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:text="Last Name">
            </TextView>
            <EditText
                android:id="@+id/etLName"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_marginLeft="5dp">
            </EditText>
        </TableRow>
    </TableLayout>
    <Button
        android:id="@+id/btnSubmit"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center_horizontal"
        android:text="Submit">
    </Button>
</LinearLayout>
```

В __EditText__ будем вводить имя и фамилию, а кнопка __Submit__ будет вызывать другой экран и передавать ему эти данные.

Пишем код для __MainActivity.java__:

```Java
package ru.startandroid.develop.p0281intentextras;
 
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
 
public class MainActivity extends Activity implements OnClickListener {
   
  EditText etFName;
  EditText etLName;
   
  Button btnSubmit;
   
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
         
        etFName = (EditText) findViewById(R.id.etFName);
        etLName = (EditText) findViewById(R.id.etLName);
         
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);
    }
 
  @Override
  public void onClick(View v) {
    Intent intent = new Intent(this, ViewActivity.class); 
    intent.putExtra("fname", etFName.getText().toString());
    intent.putExtra("lname", etLName.getText().toString());
    startActivity(intent);
  } 
}
```

Определяем поля ввода и кнопку. Кнопке присваиваем обработчик – __Activity (this)__. Рассмотрим реализацию метода __onClick__. Мы создаем __Intent__ с использованием класса, а не __action__. Если помните, с такого способа мы начинали знакомство с __Intent__. Напомню - это означает, что система просмотрит манифест файл нашего приложения, и если найдет __Activity__ с таким классом – отобразит его. __ViewActivity__ пока не создан, поэтому код будет подчеркнут красным. Это не мешает нам сохранить файл. Чуть позже мы создадим это __Activity__ и ошибка исчезнет.

Итак, __Intent__ создан, смотрим код дальше. Используется метод __putExtra__. Он имеет множество вариаций и аналогичен методу put для __Map__, т.е. добавляет к объекту пару. Первый параметр – это ключ(имя), второй - значение.

Мы поместили в __Intent__ два объекта с именами: __fname__ и __lname__. __fname__ содержит значение поля __etFName__, __lname__ – значение поля __etLName__. Остается только отправить укомплектованный __Intent__ с помощью метода __startActivity__.

Теперь создадим второе __Activity__. Назовем его __ViewActivity__.

Создаем для него __layout - файл view.xml__:

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">
    <TextView
        android:id="@+id/tvView"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center_horizontal"
        android:layout_marginTop="20dp"
        android:text="TextView"
        android:textSize="20sp">
    </TextView>
</LinearLayout>
```

Здесь просто __TextView__, который будет отображать пришедшие данные.

Создаем класс __ViewActivity__. И пишем код:

```Java
package ru.startandroid.develop.p0281intentextras;
 
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
 
public class ViewActivity extends Activity {
   
  TextView tvView;
   
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.view);
     
    tvView = (TextView) findViewById(R.id.tvView);
     
    Intent intent = getIntent();
     
    String fName = intent.getStringExtra("fname");
    String lName = intent.getStringExtra("lname");
     
    tvView.setText("Your name is: " + fName + " " + lName);
  }
}
```

Находим __TextView__, затем получаем __Intent__ и извлекаем из него __String - объекты__ с именами __fname__ и __lname__. Это те самые значения, которые мы помещали в коде __MainActivity.java__. Формируем строку вывода в __TextView__ с использованием полученных данных.

Не забудьте прописать __ViewActivity__ в манифесте. На этот раз никаких __Intent Filter__ не нужно, т.к. мы точно знаем имя класса __Activity__ и используем явный вызов.
Все сохраним и запустим.

Заполняете поля как пожелаете. Я напишу __John__ в поле __First Name__ и __Smith__ в поле __Last Name__. Жмем __Submit__

__ViewActivity__ отобразилось, считало данные из __Intent__ и вывело их на экран.

Поместить в __Intent__ можно данные не только типа __String__. В списке методов __Intent__ можно посмотреть все многообразие типов, которые умеет принимать на вход метод __putExtra__.

---

#### [Источник](https://startandroid.ru/ru/uroki/vse-uroki-spiskom/67-urok-28-extras-peredaem-dannye-s-pomoschju-intent.html)