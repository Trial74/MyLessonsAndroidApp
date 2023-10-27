# Intent Filter - практика

## Вызываем Activity, используя неявный вызов и Intent Filter

---

Последние уроки получились перегруженными теорией. Эту теорию необходимо понять, чтобы не было проблем с практикой. Эти темы являются основой – __Task, Lifecycle, Intent__. Если что-либо осталось непонятно, то вы всегда можете снова открыть и перечитать материал. А далее мы будем реализовывать примеры, которые будут эту теорию подтверждать и все станет понятнее.

На прошлых уроках мы научились вызывать __Activity__ с помощью __Intent__ и явного указания класса. Также мы знаем, что есть и другой способ вызова __Activity__ – неявный. Он основан на том, что __Activity__ вызывается не по имени, а по функционалу. Т.е. мы хотим выполнить определенные действия, создаем и настраиваем соответствующий __Intent__ и отправляем его искать те __Activity__, которые могли бы справиться с нашей задачей.

Давайте посмотрим, как это реализуется на практике. Мы создадим приложение, которое будет отображать нам текущее время или дату. Сделаем мы это с помощью трех __Activity__:
- первое будет содержать две кнопки: __Show time__ и __Show date__
- второе будет отображать время
- третье будет отображать дату

Нажатие на кнопку __Show time__ будет вызывать второе __Activity__, а нажатие на кнопку __Show date__ – третье __Activity__. Но реализуем мы это не через прямое указание классов __Activity__ в __Intent__, а через __Intent Filter__.

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

Пишем реализацию __MainActivity.java__:

```java
package ru.startandroid.develop.p0261intentfilter;
 
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

В коде мы определили кнопки и присвоили им __Activity__ как обработчик нажатий. В методе __onCilck__ мы определяем какая кнопка была нажата и создаем __Intent__.

Для создания __Intent__ используем конструктор: [__Intent (String action)__](http://developer.android.com/reference/android/content/Intent.html#Intent(java.lang.String)). Т.е. мы при создании заполняем атрибут объекта __Intent__, который называется __action__. Это обычная строковая константа. __Action__ обычно указывает действие, которое мы хотим произвести. Например, есть следующие системные __action__ - константы: [__ACTION_VIEW__](http://developer.android.com/reference/android/content/Intent.html#ACTION_VIEW) - просмотр, [__ACTION_EDIT__](http://developer.android.com/reference/android/content/Intent.html#ACTION_EDIT) – редактирование, [__ACTION_PICK__](http://developer.android.com/reference/android/content/Intent.html#ACTION_PICK) – выбор из списка, [__ACTION_DIAL__](http://developer.android.com/reference/android/content/Intent.html#ACTION_DIAL) – сделать звонок.

Если действие производится с чем-либо, то в пару к __action__ идет еще один __Intent__ - атрибут – data. В нем мы можем указать какой-либо объект: пользователь в адресной книге, координаты на карте, номер телефона и т.п. Т.е. __action__ указывает что делать, а __data__ – с чем делать.

Про data мы еще поговорим на следующих уроках, а пока будем использовать только __action__. Выше я уже перечислил некоторые системные __action__ - константы, но мы можем использовать и свой __action__.

Как вы видите из кода, я придумал и использую такие action:
- _ru.startandroid.intent.action.showtime_
- _ru.startandroid.intent.action.showdate_

Первый будет означать, что я хочу вызвать __Activity__, которое мне покажет текущее время. Второй – __Activity__ с датой.

Здесь надо четко понимать следующее: __action__ – это просто текст. И я мог с таким же успехом придумать __action abcdefg123456__. Но текст __showtime__ – отражает то, что я хочу сделать, он нагляднее и понятнее. А префикс __ru.startandroid.intent.action__ я использую, чтобы не было коллизий. В системе может быть приложение, которое уже использует __action showtime__ - я не хочу с ним пересекаться. Поэтому мой action – это __ru.startandroid.intent.action.showtime__.

Итак, мы создали __Intent__ с __action__ и запустили его в систему искать __Activity__. Чтобы __Activity__ подошла, надо чтобы ее __Intent Filter__ содержал атрибут __action__ с тем же значением, что и __action__ в __Intent__. Значит нам осталось создать две __Activity__, настроить их __Intent Filter__ и реализовать отображение времени и даты.

__Activity__ создается как обычно - создаем класс __ActivityTime__ с суперклассом __android.app.Activity__ и прописываем его в манифесте как __Activity__. После того, как прописали в манифесте, надо будет там же создать __Intent Filter__. Для этого выделяем __ActivityTime__, жмем __Add__, выбираем __Intent Filter__ и жмем __ОК__.

Далее в __Intent Filter__ аналогично создаем __Action__ и в поле __Name__ прописываем _ru.startandroid.intent.action.showtime_

Также в __Intent Filter__ надо создать __Category__ и в поле name выбрать из списка __android.intent.category.DEFAULT__. Пока не будем разбираться детально зачем она нужна. Но без этого вызов __startActivity(Intent)__ не найдет __Activity__.

Создадим __layout__ для нового __Activity__, назовем его __time.xml__:

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
        android:id="@+id/tvTime"
        android:text="TextView"
        android:layout_gravity="center_horizontal"
        android:layout_marginTop="20dp"
        android:textSize="30sp">
    </TextView>
</LinearLayout>
```

Пишем код в __ActivityTime.java__:

```Java
package ru.startandroid.develop.p0261intentfilter;
 
import java.sql.Date;
import java.text.SimpleDateFormat;
 
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
 
public class ActivityTime extends Activity {
 
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.time);
     
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    String time = sdf.format(new Date(System.currentTimeMillis()));
     
    TextView tvTime = (TextView) findViewById(R.id.tvTime);
    tvTime.setText(time);
  }
}
```

Тут все просто - вычисляем текущее время и показываем его в __TextView__.

Все сохраним и запустим приложение.

Жмем кнопку __Show time__:

отобразилось время. Т.е. __Intent__ с __action = ru.startandroid.intent.action.showtime__ нашел и отобразил __Activity__, у которого __action__ также равен _ru.startandroid.intent.action.showtime_ в __Intent Filter__.

Вернемся назад (кнопка Back) и нажмем теперь кнопку __Show date__. Приложение выдаст ошибку, т.к. оно не смогло найти __Activity__, которое соответствовало бы __Intent__ с __action = ru.startandroid.intent.action.showdate__ (мы создали только для __showtime__).

Давайте создадим такое __Activity__, назовем его __ActivityDate__. Действия все те же самые, что и при создании __ActivityTime__:
- создание класса
- создание __Activity__ в манифесте и создание для него __Intent Filter (c action = ru.startandroid.intent.action.showdate и category = android.intent.category.DEFAULT)__

Layout-файл назовем __date.xml__:

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
        android:id="@+id/tvDate"
        android:text="TextView"
        android:layout_gravity="center_horizontal"
        android:layout_marginTop="20dp"
        android:textSize="30sp">
    </TextView>
</LinearLayout>
```

Код __ActivityDate.java__:

```Java
package ru.startandroid.develop.p0261intentfilter;
 
import java.sql.Date;
import java.text.SimpleDateFormat;
 
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
 
public class ActivityDate extends Activity {
 
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.date);
     
    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
    String date = sdf.format(new Date(System.currentTimeMillis()));
     
    TextView tvDate = (TextView) findViewById(R.id.tvDate);
    tvDate.setText(date);
  }
}
```

Все сохраняем, запускаем приложение, жмем __Show date__ и видим дату. Это значит, что __Intent__ с __action = ru.startandroid.intent.action.showdate__ нашел и отобразил __ActivityDate__ подходящее ему по __Intent Filter__.

Чтобы закрепить тему, проделаем еще кое-что. Если помните, [в уроке](https://github.com/Trial74/MyLessonsAndroidApp/tree/master/lesson22), где я рассказывал про __Intent Filter__, я говорил, что один __Intent__ может найти несколько подходящих __Activity__. В этом случае пользователю предоставляется выбор, какое __Activity__ использовать. Давайте сами спровоцируем такой случай. Мы сделаем еще одно __Activity__, которое будет реагировать на __Intent__ с __action = ru.startandroid.intent.action.showdate__. И будет отображать текущую дату аналогично __ActivityDate__. Но формат отображения даты будет немного другой.

Давайте создадим такое __Activity__, назовем его __ActivityDateEx__. Действия все те же самые, что и при создании __ActivityDate__:
- создание класса
- создание __Activity__ в манифесте и создание для него __Intent Filter__ (с __action = ru.startandroid.intent.action.showdate__ и __category = android.intent.category.DEFAULT__)

Новый __layout__ - файл создавать не будем, используем уже существующий __date.xml__. В принципе, все три __Activity__ у нас могли использовать один __layout__, т.к. они совершенно одинаковы – один __TextView__.

Код __ActivityDateEx.java__:

```Java
package ru.startandroid.develop.p0261intentfilter;
 
import java.sql.Date;
import java.text.SimpleDateFormat;
 
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
 
public class ActivityDateEx extends Activity {
 
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.date);
     
    SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM d, yyyy");
    String date = sdf.format(new Date(System.currentTimeMillis()));
     
    TextView tvDate = (TextView) findViewById(R.id.tvDate);
    tvDate.setText(date);
  }
}
```

Как видим, отличие от __ActivityDate__ только в формате даты.

Сохраним все и запустим. Жмем __Show date__

__Intent__ нашел два __Activity__, но показал для каждого из них название родительского приложения и package. В нашем случае – оба __Activity__ из нашего приложения, поэтому текст одинаков и не разберешь, какое из них какое. Давайте пофиксим это, прописав нормальные имена.

Нажмите __Back__, чтобы закрыть диалог выбора. Идем в манифест и для __Activity__ пропишем __label__:

_Date basic_ для __ActivityDate__
_Date extended_ для __ActivityDateEx__

Сохраняем и запускаем. Жмем __Show date__

Так значительно лучше. Жмем __Date extended__ и видим дату в расширенном формате на __ActivityDateEx__.

Итак, мы создавали и посылали __Intent__ с __action__. Этот __Intent__ находил __Activity__ с подходящим __Intent Filter__ и отображал его. Если находил несколько – давал выбор. Примеры отлично показывают механизм.

Если запутались, чего и где создавать, привожу скрин проекта и содержимое манифеста.

Содержимое манифеста (вкладка __AndroidManifest.xml__):

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android" package="ru.startandroid.develop.p0261intentfilter" android:versionCode="1" android:versionName="1.0">
    <uses-sdk android:minSdkVersion="10"></uses-sdk>
    <application android:icon="@drawable/icon" android:label="@string/app_name">
        <activity android:name=".MainActivity" android:label="@string/app_name">
            <intent-filter>
                <action android:name="android.intent.action.MAIN"></action>
                <category android:name="android.intent.category.LAUNCHER"></category>
            </intent-filter>
        </activity>
        <activity android:name="ActivityTime">
            <intent-filter>
                <action android:name="ru.startandroid.intent.action.showtime"></action>
                <category android:name="android.intent.category.DEFAULT"></category>
            </intent-filter>
        </activity>
        <activity android:name="ActivityDate" android:label="Date basic">
            <intent-filter>
                <action android:name="ru.startandroid.intent.action.showdate"></action>
                <category android:name="android.intent.category.DEFAULT"></category>
            </intent-filter>
        </activity>
        <activity android:name="ActivityDateEx" android:label="Date extended">
            <intent-filter>
                <action android:name="ru.startandroid.intent.action.showdate"></action>
                <category android:name="android.intent.category.DEFAULT"></category>
            </intent-filter>
        </activity>
    </application>
</manifest>
```

---

#### [Источник](https://startandroid.ru/ru/uroki/vse-uroki-spiskom/64-urok-26-intent-filter-praktika.html)