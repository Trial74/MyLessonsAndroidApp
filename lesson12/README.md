# Логи и всплывающие сообщения
## _Рассмотрим логи приложения и всплывающие сообщения_

---

#### Код файла activity_main.xml :

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_height="match_parent"
    android:layout_width="match_parent"
    android:orientation="horizontal">
    <LinearLayout
        android:id="@+id/linearLayout1"
        android:layout_height="match_parent"
        android:orientation="vertical"
        android:layout_width="match_parent"
        android:layout_margin="30dp">
        <TextView
            android:layout_width="wrap_content"
            android:text="TextView"
            android:layout_height="wrap_content"
            android:id="@+id/tvOut"
            android:layout_gravity="center_horizontal"
            android:layout_marginBottom="50dp">
        </TextView>
        <Button
            android:layout_height="wrap_content"
            android:layout_gravity="center_horizontal"
            android:id="@+id/btnOk"
            android:text="OK"
            android:layout_width="100dp">
        </Button>
        <Button
            android:layout_height="wrap_content"
            android:layout_gravity="center_horizontal"
            android:id="@+id/btnCancel"
            android:text="Cancel"
            android:layout_width="100dp">
        </Button>
    </LinearLayout>
</LinearLayout>
```

Алгоритм приложения будет тот же. По нажатию кнопок меняется текст. Обработчик  - Activity.

```Java
public class MainActivity extends Activity implements OnClickListener {
 
  TextView tvOut;
  Button btnOk;
  Button btnCancel;
 
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
 
    // найдем View-элементы
    tvOut = (TextView) findViewById(R.id.tvOut);
    btnOk = (Button) findViewById(R.id.btnOk);
    btnCancel = (Button) findViewById(R.id.btnCancel);
 
    // присваиваем обработчик кнопкам
    btnOk.setOnClickListener(this);
    btnCancel.setOnClickListener(this);
  }
 
  @Override
  public void onClick(View v) {
    // по id определяем кнопку, вызвавшую этот обработчик
    switch (v.getId()) {
    case R.id.btnOk:
      // кнопка ОК
      tvOut.setText("Нажата кнопка ОК");
      break;
    case R.id.btnCancel:
      // кнопка Cancel
      tvOut.setText("Нажата кнопка Cancel");
      break;
    }
  }
}
```

#### Логи приложения
Когда вы тестируете работу приложения, вы можете видеть логи работы. Они отображаются в окне __LogCat__. Чтобы отобразить окно откройте меню __Window > Show View > Other …__ В появившемся окне выберите __Android > LogCat__

Должна появится вкладка __LogCat__:

![Image alt](https://lh4.googleusercontent.com/-T-Gwah3y6K4/TlPrht1yYZI/AAAAAAAAAQU/W88NSUQ59mc/s800/20110823_L0012_L_Logcat.jpg)

Рассмотрим эту вкладку подробней. [Логи](http://developer.android.com/reference/android/util/Log.html) имеют разные уровни важности: __ERROR, WARN, INFO, DEBUG, VERBOSE__ (по убыванию). Кнопки __V D I W E__ (в кружках) – это фильтры и соответствуют типам логов. Опробуйте их и обратите внимание, что фильтр показывает логи не только своего уровня, но и уровней более высокой важности. Также вы можете создавать, редактировать и удалять свои фильтры – это мы рассмотрим чуть дальше.

Давайте смотреть, как самим писать логи. Делается это совсем несложно с помощью класса [Log](http://developer.android.com/reference/android/util/Log.html) и его методов __Log.v() Log.d() Log.i() Log.w()__ и __Log.e()__. Названия методов соответствуют уровню логов, которые они запишут.

Изменим код __MainActivity.java__. Возьмем все каменты из кода и добавим в DEBUG-логи с помощью метода __Log.d__. Метод требует на вход тэг и текст сообщения. Тэг – это что-то типа метки, чтобы легче было потом в куче системных логов найти именно наше сообщение. Добавим описание тега __(TAG)__ и запишем все тексты каментов в лог.

```Java
public class MainActivity extends Activity implements OnClickListener {
 
  TextView tvOut;
  Button btnOk;
  Button btnCancel;
 
  private static final String TAG = "myLogs";
 
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
 
    // найдем View-элементы
    Log.d(TAG, "найдем View-элементы");
    tvOut = (TextView) findViewById(R.id.tvOut);
    btnOk = (Button) findViewById(R.id.btnOk);
    btnCancel = (Button) findViewById(R.id.btnCancel);
 
    // присваиваем обработчик кнопкам
    Log.d(TAG, "присваиваем обработчик кнопкам");
    btnOk.setOnClickListener(this);
    btnCancel.setOnClickListener(this);
  }
 
  @Override
  public void onClick(View v) {
    // по id определяем кнопку, вызвавшую этот обработчик
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
}
```

#### Всплывающие сообщения

Приложение может показывать всплывающие сообщения с помощью класса Toast. Давайте подредактируем метод onClick. Сделаем так, чтобы всплывало сообщение о том, какая кнопка была нажата.

```Java
public void onClick(View v) {
// по id определяем кнопку, вызвавшую этот обработчик
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
```

Разберем синтаксис вызова. Статический метод [makeText](http://developer.android.com/reference/android/widget/Toast.html#makeText(android.content.Context,%20java.lang.CharSequence,%20int)) создает View-элемент [Toast](http://developer.android.com/reference/android/widget/Toast.html).
Параметры метода:
- __context__ – пока не будем вдаваться в подробности, что это такое и используем текущую Activity, т.е. this.
- __text__ – текст, который надо показать
- __duration__ – продолжительность показа ([__Toast.LENGTH_LONG__](http://developer.android.com/reference/android/widget/Toast.html#LENGTH_LONG) - длинная, [__Toast.LENGTH_SHORT__](http://developer.android.com/reference/android/widget/Toast.html#LENGTH_SHORT) - короткая)

[Toast](http://developer.android.com/reference/android/widget/Toast.html) создан и чтобы он отобразился на экране, вызывается метод __show()__. Сохраняем, запускаем, проверяем.