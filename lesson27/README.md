# Читаем action из Intent

---

На [прошлом уроке](https://github.com/Trial74/MyLessonsAndroidApp/tree/master/lesson26) мы сделали наглядный пример, показывающий, как связаны между собой __action, Intent и Intent Filter__. На этом уроке продолжим тему.

Первая новость. __Intent Filter__ может содержать в себе несколько __action__. Тем самым __Activity__ дает понять, что она способна на несколько действий. Например, не только просмотр картинки, но и редактирование. Получается, что __Activity__ может подойти разным __Intent__ с разными __action__.

Вторая новость. __Activity__, которое было вызвано с помощью __Intent__, имеет доступ к этому __Intent__ и может прочесть его атрибуты. Т.е. может узнать какой __action__ использовался.

Мы сделаем следующее: создадим __Activity__ и настроим __Intent Filter__ на __action = ru.startandroid.intent.action.showtime__ и __action = ru.startandroid.intent.action.showdate__. Тем самым мы обозначаем, что это __Activity__ способно и время показать, и дату. Далее мы будем создавать __Intent__ либо с __action = ru.startandroid.intent.action.showtime__, либо с __ru.startandroid.intent.action.showdate__. Они оба будут вызывать одно __Activity__. А чтобы __Activity__ знало показывать ему дату или время, мы будем читать action из __Intent__ и по нему определять.

В общем, сейчас начнем делать и все станет понятно

Открываем activity_main.xml и рисуем две кнопки:

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="fill_parent"
    android:layout_height="fill_parent"
    android:orientation="horizontal">
    <Button
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:id="@+id/btnTime"
        android:text="Show time">
    </Button>
    <Button
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:id="@+id/btnDate"
        android:text="Show date">
    </Button>
</LinearLayout>
```

Экран точно такой же как и в прошлом уроке.

Код для __MainActivity.java__:

```Java
package ru.startandroid.develop.p0271getintentaction;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
 
public class MainActivity extends Activity implements OnClickListener {
   
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
         
        Button btnTime = (Button) findViewById(R.id.btnTime);
        Button btnDate = (Button) findViewById(R.id.btnDate);
         
        btnTime.setOnClickListener(this);
        btnDate.setOnClickListener(this);
    }
 
  @Override
  public void onClick(View v) {
    Intent intent;
 
    switch(v.getId()) {
    case R.id.btnTime:
      intent = new Intent("ru.startandroid.intent.action.showtime");
      startActivity(intent);
      break;
    case R.id.btnDate:
      intent = new Intent("ru.startandroid.intent.action.showdate");
      startActivity(intent);
      break;
    }
  }
}
```

Код тоже полностью из прошлого урока. Определяем кнопки, присваиваем обработчик – __Activity__, и вызываем __Intent__ по нажатиям. Теперь мы сделаем __Activity__, которая будет ловить оба этих __Intent__.

Для начала создадим __layout__ - файл __info.xml__:

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">
    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:id="@+id/tvInfo"
        android:text="TextView"
        android:layout_gravity="center_horizontal"
        android:layout_marginTop="20dp"
        android:textSize="30sp">
    </TextView>
</LinearLayout>
```

На экране у нас один __TextView__.

Создаем __Activity__, назовем его просто __Info__.

Код __Info.java__:

```Java
package ru.startandroid.develop.p0271getintentaction;
 
import java.sql.Date;
import java.text.SimpleDateFormat;
 
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
 
public class Info extends Activity {
 
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.info);
     
    // получаем Intent, который вызывал это Activity
    Intent intent = getIntent();
    // читаем из него action
    String action = intent.getAction();
     
    String format = "", textInfo = "";
     
    // в зависимости от action заполняем переменные
    if (action.equals("ru.startandroid.intent.action.showtime")) { 
      format = "HH:mm:ss";
      textInfo = "Time: ";
    }
    else if (action.equals("ru.startandroid.intent.action.showdate")) { 
      format = "dd.MM.yyyy";
      textInfo = "Date: ";
    }
     
    // в зависимости от содержимого переменной format 
    // получаем дату или время в переменную datetime
    SimpleDateFormat sdf = new SimpleDateFormat(format);
    String datetime = sdf.format(new Date(System.currentTimeMillis()));
     
    TextView tvDate = (TextView) findViewById(R.id.tvInfo);
    tvDate.setText(textInfo + datetime);
  }
}
```

Мы получаем __Intent__ с помощью метода __getIntent()__, читаем из него __action__ и в зависимости от значения формируем и выводим на экран текст.

Не забываем прописать новое __Activity__ в манифесте и создать ему __Intent Filter__ с двумя __action__ и __category__. И __label__ укажите __Date/time info__.

__Intent Filter__ для __Info__ содержит два __action__. А значит если придет __Intent__ c любым из них – то __Activity__ будет вызвана.

Все сохраним и запустим.
Жмем кнопку __Show time__.

Система предлагает нам выбор. Т.е. __Intent__ с __action = ru.startandroid.intent.action.showtime__ нашел два подходящих __Activity__. То, которое __Date/time info__ мы создали только что, тут все понятно. Вопрос – откуда второе с названием __IntentFilter__. Ответ – это __ActivityTime__ из прошлого урока. Называется оно __IntentFilter__ потому, что на прошлом уроке мы не прописали в манифесте __label__ для этого __Activity__ и система по умолчанию отображает название приложения.

Если же система не отобразила диалог выбора, значит вы, либо не создавали приложение с прошлого урока, либо пересоздали __AVD__, либо где-то очепятка в коде.

Выбираем __Date/time info__ и видим то, что только что кодили. Activity определило, что __Intent__ был с __action = ru.startandroid.intent.action.showtime__ и показало время с текстом __Time__

Если же выбрать __IntentFilter__ увидим __Activity__ с прошлого урока

Теперь попробуем нажать кнопку __Show date__

Снова видим наше __Date/time info__ и два __Activity__ с прошлого урока. Они все содержат __action = ru.startandroid.intent.action.showdate__ в __Intent Filter__ и нам надо выбирать. Выберем __Date/time info__ и видим дату с текстом __Date__

Если же выбирать __Date basic__ или __Date extended__ увидим то, что делали на [прошлом уроке](https://github.com/Trial74/MyLessonsAndroidApp/tree/master/lesson26) обычную дату и расширенную

Мы увидели, что одно __Activity__ может быть вызвано с помощью __Intent__ с разными __action__. __Activity__ может прочесть __action__ и выполнить необходимые действия.

Также мы убедились, что __Intent__ ищет __Activity__ по всем приложениям в системе. В этот раз он нашел __Activity__ из приложения, которое мы делали на [прошлом уроке](https://github.com/Trial74/MyLessonsAndroidApp/tree/master/lesson26).

---

#### [Источник](https://startandroid.ru/ru/uroki/vse-uroki-spiskom/65-urok-27-chitaem-action-iz-intent.html)