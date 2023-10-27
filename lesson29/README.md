# Вызываем Activity и получаем результат. Метод startActivityForResult

## Вызываем Activity с возвратом результата

---

Бывает необходимость вызвать __Activity__, выполнить на нем какое-либо действие и вернуться с результатом. Например – при создании __SMS__. Вы жмете кнопку «добавить адресата», система показывает экран со списком из адресной книги, вы выбираете нужного вам абонента и возвращаетесь в экран создания __SMS__. Т.е. вы вызвали экран выбора абонента, а он вернул вашему экрану результат.

Об этом можно почитать здесь.

Давайте посмотрим на практике. Создадим приложение с двумя экранами. С первого экрана будем вызывать второй экран, там вводить данные, нажимать кнопку и возвращаться на первый экран с введенными данными. Например, будем таким образом запрашивать имя.

Открываем __activity_main.xml__ и нарисуем такой экран:

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="fill_parent"
    android:layout_height="fill_parent"
    android:orientation="vertical">
    <Button
        android:id="@+id/btnName"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center_horizontal"
        android:layout_margin="20dp"
        android:text="Input name">
    </Button>
    <TextView
        android:id="@+id/tvName"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center_horizontal"
        android:text="Your name is ">
    </TextView>
</LinearLayout>
```

На экране __TextView__, который будет отображать имя, и кнопка, которая будет вызывать экран для ввода.

Кодим __MainActivity.java__:

```Java
package ru.startandroid.develop.p0291simpleactivityresult;
 
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
 
public class MainActivity extends Activity implements OnClickListener {
   
  TextView tvName;
  Button btnName;
   
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
         
        tvName = (TextView) findViewById(R.id.tvName);
        btnName = (Button) findViewById(R.id.btnName);
        btnName.setOnClickListener(this);
         
    }
 
  @Override
  public void onClick(View v) {
    Intent intent = new Intent(this, NameActivity.class);
    startActivityForResult(intent, 1);
  }
   
  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (data == null) {return;}
    String name = data.getStringExtra("name");
    tvName.setText("Your name is " + name);
  }
}
```

Определяем __TextView__ и кнопку, настраиваем обработчик. В методе обработчика __onClick__ создаем __Intent__, указываем класс второго __Acivity__ (которое создадим чуть позже, на ошибку не обращайте внимания). Для отправки используем [__startActivityForResult__](http://developer.android.com/reference/android/app/Activity.html#startActivityForResult(android.content.Intent,%20int)). Отличие от обычного __startActivity__ в том, что __MainActivity__ становится «родителем» для __NameActivity__. И когда __NameActivity__ закрывается, вызывается метод [__onActivityResult__](http://developer.android.com/reference/android/app/Activity.html#onActivityResult(int,%20int,%20android.content.Intent)) в __MainActivity__, тем самым давая нам знать, что закрылось __Activity__, которое мы вызывали методом __startActivityForResult__.

В __startActivityForResult__ в качестве параметров мы передаем __Intent__ и __requestCode__. __requestCode__ – необходим для идентификации. В этом уроке мы его укажем, но не будем использовать по назначению. В следующем же уроке разберемся подробнее, зачем он нужен.

В __onActivityResult__ мы видим следующие параметры:
__requestCode__ – тот же идентификатор, что и в __startActivityForResult__. По нему определяем, с какого __Activity__ пришел результат.
__resultCode__ – код возврата. Определяет успешно прошел вызов или нет.
__data – Intent__, в котором возвращаются данные

__requestCode__ и __resultCode__ мы пока использовать не будем, подробнее рассмотрим их на следующем уроке. А из __data__ мы будем получать объект по имени name и выводить значение в __TextView__.

Если мы извлекаем из __Intent__ объект с именем name, значит надо, чтобы кто-то его туда положил. Этим займется __NameActivity__.

Создадим экран __name.xml__:

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">
    <LinearLayout
        android:id="@+id/linearLayout1"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_margin="10dp">
        <TextView
            android:id="@+id/textView1"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Name">
        </TextView>
        <EditText
            android:id="@+id/etName"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginLeft="10dp"
            android:layout_weight="1">
            <requestFocus>
            </requestFocus>
        </EditText>
    </LinearLayout>
    <Button
        android:id="@+id/btnOK"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center_horizontal"
        android:text="OK">
    </Button>
</LinearLayout>
```

В поле ввода будем вводить имя и жать кнопку __OK__.

Создаем класс __NameActivity__ и прописываем его в манифесте:

```Java
package ru.startandroid.develop.p0291simpleactivityresult;
 
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
 
public class NameActivity extends Activity implements OnClickListener {
   
  EditText etName;
  Button btnOK;
   
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    // TODO Auto-generated method stub
    super.onCreate(savedInstanceState);
    setContentView(R.layout.name);
     
    etName = (EditText) findViewById(R.id.etName);
    btnOK = (Button) findViewById(R.id.btnOK);
    btnOK.setOnClickListener(this);
  }
 
  @Override
  public void onClick(View v) {
    Intent intent = new Intent();
    intent.putExtra("name", etName.getText().toString());
    setResult(RESULT_OK, intent);
    finish();
  }
}
```

Определяем поле ввода и кнопку, прописываем обработчик. В методе __onClick__ мы создаем __Intent__ и помещаем в него данные из поля ввода под именем __name__. Обратите внимание, мы никак не адресуем этот __Intent__. Т.е. ни класс, ни __action__ мы не указываем. И получается, что непонятно куда пойдет этот __Intent__. Но метод __setResult__ знает, куда его адресовать - в «родительское» __Activity__, в котором был вызван метод __startActivityForResult__. Также в [__setResult__](http://developer.android.com/reference/android/app/Activity.html#setResult(int,%20android.content.Intent)) мы передаем константу __RESULT_OK__, означающую успешное завершение вызова. И именно она передастся в параметр __resultCode__ метода __onActivityResult__ в __MainActivity.java__. Это мы подробнее разберем на следующем уроке. Далее методом __finish__ мы завершаем работу __NameActivity__, чтобы результат ушел в __MainActivity__.

Все сохраним и запустим приложение.
- Жмем кнопку, чтобы попасть на экран ввода имени.
- Вводим имя и жмем ОК
- Снова первый экран, отобразивший полученные данные.

Попробуем подытожить. В __MainActivity__ мы создали __Intent__ с явным указанием на класс __NameActivity__. Запустили этот __Intent__ с помощью метода __startActivityForResult__. __NameActivity__ отобразилось, мы ввели имя и нажали кнопку. Создался __Intent__, в который поместилось введенное нами имя. Метод __setResult__ знает, что __Intent__ надо вернуть в __Activity__, которое выполнило вызов __startActivityForResult__, т.е. – __MainActivity__. В __MainActivity__ за прием результатов с вызванных __Activity__ отвечает метод __onActivityResult__. В нем мы распаковали __Intent__ и отобразили полученные данные в __TextView__.

Пока необходимо просто понять схему вызова и возврата.
![ImageAlt](https://startandroid.ru/images/stories/lessons/L0029/L0029_040.jpg)

---

#### [Источник](https://startandroid.ru/ru/uroki/vse-uroki-spiskom/68-urok-29-vyzyvaem-activity-i-poluchaem-rezultat-metod-startactivityforresult.html)