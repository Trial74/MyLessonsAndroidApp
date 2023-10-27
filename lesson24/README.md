# Activity Lifecycle, пример смены состояний с двумя Activity

## Изучаем смену состояния на примере двух Activity

---

На [прошлом уроке](https://github.com/Trial74/MyLessonsAndroidApp/tree/master/lesson23) мы рассмотрели, какие состояния проходит __Activity__ за время своего существования и какие методы при этом вызываются. Но мы видели __Activity__ только в состоянии __Resumed__ (т.е. его видно, и оно в фокусе). На этом уроке на примере двух __Activity__ попробуем понять, в каком случае __Activity__ может остаться в состоянии __Stopped__, т.е. не видно и не в фокусе, но существует в памяти.

В __activity_main.xml__ пишем следующее:

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="vertical"
    android:layout_width="fill_parent"
    android:layout_height="fill_parent">
    <TextView
        android:layout_width="fill_parent"
        android:layout_height="wrap_content"
        android:text="@string/hello">
    </TextView>
    <Button
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Go to Activity Two"
        android:id="@+id/btnActTwo">
    </Button>
</LinearLayout> 
```

Кнопка __“Go to Activity Two”__ будет вызывать второе __Activity__.

Откроем __MainActivity.java__ и пишем туда все методы, на этот раз, включая __onRestart__, и в методах прописываем запись в логи. Также описываем и находим кнопку, присваиваем ей обработчик. В методе __onClick__ пока ничего не пишем.

```Java
package ru.startandroid.develop.p0241twoactivitystate;
 
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
 
public class MainActivity extends Activity implements OnClickListener {
 
  final String TAG = "States";
 
  Button btnActTwo;
 
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
 
    btnActTwo = (Button) findViewById(R.id.btnActTwo);
    btnActTwo.setOnClickListener(this);
 
    Log.d(TAG, "MainActivity: onCreate()");
  }
 
  @Override
  protected void onRestart() {
    super.onRestart();
    Log.d(TAG, "MainActivity: onRestart()");
  }
 
  @Override
  protected void onStart() {
    super.onStart();
    Log.d(TAG, "MainActivity: onStart()");
  }
 
  @Override
  protected void onResume() {
    super.onResume();
    Log.d(TAG, "MainActivity: onResume()");
  }
 
  @Override
  protected void onPause() {
    super.onPause();
    Log.d(TAG, "MainActivity: onPause()");
  }
 
  @Override
  protected void onStop() {
    super.onStop();
    Log.d(TAG, "MainActivity: onStop()");
  }
 
  @Override
  protected void onDestroy() {
    super.onDestroy();
    Log.d(TAG, "MainActivity: onDestroy()");
  }
 
  @Override
  public void onClick(View v) {
  }
}
```

Какие методы и в каком порядке выполняются при работе одного __Activity__, мы видели на прошлом уроке. Сейчас нам интересно поведение при двух __Activity__, поэтому создаем второе __Activity__. Назовем ее __ActivityTwo__. Вспоминаем прошлые уроки: надо создать класс с таким именем и с суперклассом __android.app.Activity__, и прописать новое __Activity__ в __манифест-файле__. Также надо создать __layout-файл__, назовем его __two.xml__ и заполним этим кодом:

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

Просто __TextView__ с текстом, чтобы было понятно, что это __ActivityTwo__.

Создаем класс. Код __ActivityTwo.java__:

```Java
package ru.startandroid.develop.p0241twoactivitystate;
 
 
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
 
public class ActivityTwo extends Activity {
 
  final String TAG = "States";
 
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.two);
    Log.d(TAG, "ActivityTwo: onCreate()");
  }
 
  @Override
  protected void onRestart() {
    super.onRestart();
    Log.d(TAG, "ActivityTwo: onRestart()");
  }
 
  @Override
  protected void onStart() {
    super.onStart();
    Log.d(TAG, "ActivityTwo: onStart()");
  }
 
  @Override
  protected void onResume() {
    super.onResume();
    Log.d(TAG, "ActivityTwo: onResume()");
  }
 
  @Override
  protected void onPause() {
    super.onPause();
    Log.d(TAG, "ActivityTwo: onPause()");
  }
 
  @Override
  protected void onStop() {
    super.onStop();
    Log.d(TAG, "ActivityTwo: onStop()");
  }
 
  @Override
  protected void onDestroy() {
    super.onDestroy();
    Log.d(TAG, "ActivityTwo: onDestroy()");
  }
}
```

Не забудьте добавить запись об __ActivityTwo__ в манифест.  И теперь мы можем дописать код метода __onClick__ в __MainActivity.java__, прописав там вызов __ActivityTwo__

```Java
@Override
public void onClick(View v) {
  Intent intent = new Intent(this, ActivityTwo.class);
  startActivity(intent);
}
```

Фильтр логов должен был остаться с прошлого урока. Используем его. Если нет, то создайте фильтр по тегу __States__.

Все сохраним и приступим к испытаниям.

__Шаг1. Запускаем приложение. Появилось MainActivity__.

#### Логи:

- __MainActivity: onCreate()__
- __MainActivity: onStart()__
- __MainActivity: onResume()__

Все, как и в прошлый раз - вызываются три метода. __Activity__ проходит через состояния __Stopped, Paused__ и остается в состоянии __Resumed__.

__Шаг 2. Жмем кнопку «Go to Activity Two» на экране и появляется ActivityTwo.__

#### Логи:

- __MainActivity: onPause()__
- __ActivityTwo: onCreate()__
- __ActivityTwo: onStart()__
- __ActivityTwo: onResume()__
- __MainActivity: onStop()__

Давайте разбираться. Вызов __MainActivity.onPause__ означает, что __MainActivity__ теряет фокус и переходит в состояние Paused. Затем создается __(onCreate)__, отображается __(onStart)__ и получает фокус __(onResume)__ __ActivityTwo__. Затем перестает быть видно __(onStop) MainActivity__. Обратите внимание, что не вызывается __onDestroy__ для __MainActivity__, а значит, оно не уничтожается. __MainActivity__ остается в памяти, в состоянии __Stopped__. А __ActivityTwo__ – находится в состоянии __Resumed__. Его видно и оно в фокусе, с ним можно взаимодействовать.

__Шаг 3. Жмем кнопку Назад (Back) на эмуляторе. Мы вернулись в MainActivity.__

#### Логи:

- __ActivityTwo: onPause()__
- __MainActivity: onRestart()__
- __MainActivity: onStart()__
- __MainActivity: onResume()__
- __ActivityTwo: onStop()__
- __ActivityTwo: onDestroy()__

__ActivityTwo.onPause__ означает, что __ActivityTwo__ теряет фокус и переходит в состояние __Paused__. __MainActivity__ теперь должна восстановиться из статуса __Stopped__. В конце прошлого урока я написал: «Метод __onRestart__ вызывается перед методом __onStart__, если __Activity__ не создается с нуля, а восстанавливается из состояния __Stopped__» – это как раз наш случай, __MainActivity__ не было уничтожено системой, оно висело в памяти. Поэтому вызывается __MainActivity.onRestart__.  Далее вызываются методы __MainActivity.onStart__ и __MainActivity.onResume__ – значит __MainActivity__ перешло в состояние __Paused__ (отобразилось) и __Resumed__ (получило фокус). Ну и вызов методов __onStop__ и __onDestroy__ означает, что __ActivityTwo__ было переведено в статус __Stopped__ (потеряло видимость) и было уничтожено.

__Шаг 4. Жмем еще раз Назад и наше приложение закрылось.__

#### Логи:

- __MainActivity: onPause()__
- __MainActivity: onStop()__
- __MainActivity: onDestroy()__

Логи показывают, что __MainActivity__ перешло в состояние __Paused, Stopped__ и было уничтожено.

Если с первого раза непонятно, попробуйте прогнать алгоритм несколько раз и сверяйтесь со схемой с прошлого урока. Она достаточно наглядная и поможет разобраться. Попробуйте расписать всю схему на бумаге и нарисовать смену статусов __Activity__. Я здесь тоже приведу схему шагов для наглядности.

![ImageAlt](https://lh5.googleusercontent.com/-MMX3o4pdsd0/ToybUtq-EFI/AAAAAAAAAbw/ri5MQ1Jg5sI/s800/20111005_L0024_L_TwoActSchema.jpg)

Мы увидели, что __Activity__ не обязательно уничтожается, когда его не видно, а может оставаться в памяти. В связи с этим, думаю, наверняка возник вопрос: почему на шаге 2 __MainActivity__ исчезло с экрана, но осталось висеть в памяти и не было уничтожено? Ведь на шаге 3 было уничтожено __ActivityTwo__ после того, как оно пропало с экрана. А на шаге 4 было в итоге уничтожено и __MainActivity__. Почему шаг 2 стал исключением?

Об этом мы поговорим на следующем уроке, т.к. этот и так получился слишком заумным. Но тема очень важная и одна из ключевых для понимания принципов работы __Android__.

---

#### [Источник](https://startandroid.ru/ru/uroki/vse-uroki-spiskom/61-urok-24-activity-lifecycle-primer-smeny-sostojanij-s-dvumja-activity.html)