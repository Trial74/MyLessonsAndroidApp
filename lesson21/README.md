# Создание и вызов Activity

## Создадим и вызовем второе Activity в приложении

---

Мы подобрались к очень интересной теме. На всех предыдущих уроках мы создавали приложения, которые содержали только один экран __(Activity)__. Но если вы пользуетесь смартфоном с __Android__, то вы замечали, что экранов в приложении обычно больше. Если рассмотреть, например, почтовое приложение, то в нем есть следующие экраны: список аккаунтов, список писем, просмотр письма, создание письма, настройки и т.д.  Пришла и нам пора научиться создавать многоэкранные приложения.

Откроем __activity_main.xml__ и создадим такой экран:

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="vertical"
    android:layout_width="fill_parent"
    android:layout_height="fill_parent">
    <Button
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Go to Activity Two"
        android:id="@+id/btnActTwo">
    </Button>
</LinearLayout>
```

На экране одна кнопка, по нажатию которой будем вызывать второй экран.

Открываем __MainActivity.java__ и пишем код:

```Java
package ru.startandroid.develop.p0211twoactivity;
 
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
 
public class MainActivity extends AppCompatActivity implements OnClickListener {
 
  Button btnActTwo;
 
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
 
    btnActTwo = (Button) findViewById(R.id.btnActTwo);
    btnActTwo.setOnClickListener(this);
  }
 
  @Override
  public void onClick(View v) {
    switch (v.getId()) {
    case R.id.btnActTwo:
      // TODO Call second activity
      break;
    default:
      break;
    }
  }
}
```

Мы определили кнопку __btnActTwo__ и присвоили ей __Activity__ в качестве обработчика. Реализация метода __onClick__ для кнопки пока заполнена частично - определяем, какая кнопка была нажата. Чуть позже здесь мы будем вызывать второй экран. Но сначала этот второй экран надо создать.

Если помните, при создании проекта у нас по умолчанию создается __Activity__.

От нас требуется только указать имя этого __Activity__ – обычно мы пишем здесь __MainActivity__. Давайте разбираться, что при этом происходит.

Мы уже знаем, что создается одноименный класс __MainActivity.java__ – который отвечает за поведение __Activity__. Но, кроме этого, __Activity__ «регистрируется» в системе с помощью манифест-файла - __AndroidManifest.xml__.

Давайте откроем этот файл:

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="ru.startandroid.p0211twoactivity">
 
    <application android:allowBackup="true" android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name" android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true" android:theme="@style/AppTheme">
        <activity android:name=".MainActivity">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
 
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>
 
</manifest>
```

Нас интересует тег __application__. В нем мы видим тег __activity__ с атрибутом __name = MainActivity__. В __activity__ находится тег __intent-filter__ с определенными параметрами. Пока мы не знаем что это и зачем, сейчас нам это не нужно. Забегая вперед, скажу, что __android.intent.action.MAIN__ показывает системе, что __Activity__ является основной и будет первой отображаться при запуске приложения. А __android.intent.category.LAUNCHER__ означает, что приложение будет отображено в общем списке приложений __Android__.

Т.е. этот манифест-файл - это что-то типа конфигурации. В нем мы можем указать различные параметры отображения и запуска __Activity__ или целого приложения. Если в этом файле не будет информации об __Activity__, которое вы хотите запустить в приложении, то вы получите ошибку.

__Android Studio__ при создании модуля создала __MainActivity__ и поместила в манифест данные о нем. Если мы надумаем сами создать новое __Activity__, то студия также предоставит нам визард, который автоматически добавит создаваемое __Activity__ в манифест.

Давайте создадим новое __Activity__

Жмем правой кнопкой на package __ru.startandroid.p0211twoactivity__ в папке проекта и выбираем __New -> Activity -> Empty Activity__

В появившемся окне вводим имя класса – __ActivityTwo__, и __layout – activity_two__.

Жмем Finish

Класс __ActivityTwo__ создан.

```Java
package ru.startandroid.p0211twoactivity;
 
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
 
public class ActivityTwo extends AppCompatActivity {
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
    }
}
```

В __setContentView__ сразу указан __layout__ - файл __activty_two__.

Он был создан визардом

Откройте __activty_two.xml__ и заполните следующим кодом:

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="vertical"
    android:layout_width="match_parent"
    android:layout_height="match_parent">
    <TextView
        android:id="@+id/textView1"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="This is Activity Two">
    </TextView>
</LinearLayout>
```

Экран будет отображать __TextView__ с текстом __"This is Activity Two"__.

Сохраните все. Класс __ActivityTwo__ готов, при отображении он выведет на экран то, что мы настроили в __layout-файле two.xml__.

Давайте снова заглянем в файл манифеста

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="ru.startandroid.p0211twoactivity">
 
    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/AppTheme">
        <activity android:name=".MainActivity">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
 
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
        <activity android:name=".ActivityTwo"></activity>
    </application>
 
</manifest>
```

Появился тег __activity__ с атрибутом __name = .ActivityTwo__. Этот тег совершенно пустой, без каких либо параметров и настроек. Но даже пустой, он необходим здесь.

Нам осталось вернуться в __MainActivity.java__ и довершить реализацию метода __onClick__ (нажатие кнопки), а именно - прописать вызов __ActivityTwo__. Открываем __MainActivity.java__ и добавляем строки:

```Java
case R.id.btnActTwo:
  Intent intent = new Intent(this, ActivityTwo.class);
  startActivity(intent);
  break;
```

Обновите импорт, сохраните все и можем всю эту конструкцию запускать. При запуске появляется __MainActivity__

Нажимаем на кнопку и переходим на __ActivityTwo__

Код вызова __Activity__ пока не объясняю и теорией не гружу, урок и так получился сложным. Получилось много текста и скриншотов, но на самом деле процедура минутная. Поначалу, возможно, будет непонятно, но постепенно втянемся. Создадим штук 5-6 новых __Activity__ в разных проектах и тема уляжется в голове.

Пока попробуйте несколько раз пройти мысленно эту цепочку действий и усвоить, что для создания __Activity__ необходимо создать класс (который наследует __android.app.Activity__) и создать соответствующую запись в манифест-файле.

---

#### [Источник](https://startandroid.ru/ru/uroki/vse-uroki-spiskom/58-urok-21-sozdanie-i-vyzov-activity.html)