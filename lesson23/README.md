# Activity Lifecycle. В каких состояниях может быть Activity

## Activity LifeCycle – поведение Activity при создании, вызове, закрытии

---

#### Теория
При работе приложения, мы создаем новые __Activity__ и закрываем старые, сворачиваем приложение, снова открываем и т.д. __Activity__ умеет обрабатывать все эти движения. Это необходимо, например, для освобождения ресурсов или сохранения данных. В хелпе достаточно подробно это описано.

Созданное при работе приложения __Activity__ может быть в одном из трех состояний:

- __Resumed - Activity__ видно на экране, оно находится в фокусе, пользователь может с ним взаимодействовать. Это состояние также иногда называют __Running__.
- __Paused - Activity__ не в фокусе, пользователь не может с ним взаимодействовать, но его видно (оно перекрыто другим __Activity__, которое занимает не весь экран или полупрозрачно).
- __Stopped - Activity__ не видно (полностью перекрывается другим __Activity__), соответственно оно не в фокусе и пользователь не может с ним взаимодействовать.

Когда __Activity__ переходит из одного состояния в другое, система вызывает различные его методы, которые мы можем заполнять своим кодом. Схематично это можно изобразить так:

![ImageAlt](https://lh6.googleusercontent.com/-cRgfIY8Y0pE/ToyPWqE3L4I/AAAAAAAAAbM/mfpvj5yabiA/s800/20111005_L0023_L_StatesSchema.jpg)

Для упрощения понимания я дал краткое описание состояний в скобках под названиями. А крестом обозначил отсутствие __Activity__.

Итак, мы имеем следующие методы __Activity__, которые вызывает система:

- __onCreate()__ – вызывается при первом создании __Activity__
- __onStart()__ – вызывается перед тем, как __Activity__ будет видно пользователю
- __onResume()__ – вызывается перед тем как будет доступно для активности пользователя (взаимодействие)
- __onPause()__ – вызывается перед тем, как будет показано другое __Activity__
- __onStop()__ – вызывается когда __Activity__ становится не видно пользователю
- __onDestroy()__ – вызывается перед тем, как __Activity__ будет уничтожено

Т.е. эти методы __НЕ__ вызывают смену состояния. Наоборот, смена состояния __Activity__ является триггером, который вызывает эти методы. Тем самым нас уведомляют о смене, и мы можем реагировать соответственно. Посмотрим на практике, когда и в каком порядке вызываются эти методы.

#### Практика

Layout не меняем, нам он сейчас не важен. Открываем MainActivity.java, там как обычно код по умолчанию:

```Java
package ru.startandroid.develop.p0231oneactivitystate;
 
import android.app.Activity;
import android.os.Bundle;
 
public class MainActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
}
```

Мы видим, что реализован уже знакомый нам по схеме метод __onCreate__. Повторюсь, важно понимать, что этот метод __НЕ__ создает __Activity__. Создание – это дело системы. Т.е. система сама создает __Activity__, а нам дает возможность немного поучаствовать и выполнить свой код в методе __onCreate()__. Мы этой возможностью пользуемся и говорим системе, что __Activity__ должна отобразить экран из __R.layout.main__.

Добавим все остальные методы из схемы, и в каждый добавим запись в лог.

```Java
package ru.startandroid.develop.p0231oneactivitystate;
 
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
 
public class MainActivity extends Activity {
   
  final String TAG = "States";
   
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Log.d(TAG, "MainActivity: onCreate()");
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
}
```

Теперь, когда методы будут вызываться, мы будем видеть это в логах. Настроим фильтр на тег «States», чтобы не искать свои сообщения в общей куче логов. Как это делается мы проходили в [этом уроке](https://github.com/Trial74/MyLessonsAndroidApp/tree/master/lesson12)

Все сохраним и запустим приложение. После того, как запустилось, смотрим лог:

- __MainActivity: onCreate()__
- __MainActivity: onStart()__
- __MainActivity: onResume()__

__Activity__ создалось, прошло два состояния __(Stopped, Paused)__ и теперь находится в третьем состоянии - __Resumed__. Т.е. оно создалось __(onCreate)__, отобразилось __(onStart)__ и получило возможность взаимодействовать с пользователем __(onResume)__.

Теперь нажмем кнопку Back на эмуляторе. __Activity__ закрылось. Смотрим лог:

- __MainActivity: onPause()__
- __MainActivity: onStop()__
- __MainActivity: onDestroy()__

__Activity__ проделывает путь, обратный созданию. Сначала теряет фокус __(onPause)__, затем исчезает с экрана __(onStop)__, затем полностью уничтожается __(onDestroy)__.

#### Смена ориентации экрана

Посмотрим, как ведет себя __Activity__, когда происходит смена ориентации экрана. Запустите снова приложение (либо найдите его в списке приложений в системе на эмуляторе, либо снова нажмите __CTRL+F11 в Eclipse__ ). В логах снова отобразились три метода, вызванные при создании. Теперь в эмуляторе нажмите __CTRL+F12__, ориентация сменилась. Кажется, что ничего особенного не произошло, но смотрим логи и видим:

- __MainActivity: onPause()__
- __MainActivity: onStop()__
- __MainActivity: onDestroy()__
- __MainActivity: onCreate()__
- __MainActivity: onStart()__
- __MainActivity: onResume()__

__Activity__ полностью уничтожается и снова создается. При этом обычно выполняются процедуры сохранения и восстановления данных, чтобы не потерялись данные, и приложение сохранило свой вид. Про то, как это делается, мы будем говорить в последующих уроках.

Также есть еще метод __onRestart__. Он вызывается перед методом __onStart__, если __Activity__ не создается с нуля, а восстанавливается из состояния __Stoped__. Его мы рассмотрим в следующем уроке.

Обычно в учебниках эта тема дается по-другому. Но мне это шаблонное объяснение кажется недостаточно понятным, поэтому я написал свое. Как всегда, надеюсь, что у меня получилось раскрыть тему )

Советую вам после этого урока прочитать хелп, ссылку на который я дал в самом начале урока. Там все очень хорошо написано. И знания лучше усвоятся. Пока что, главное – это понять в какой момент, какой метод вызывается. А уже дальше мы будем разбираться, как это можно использовать и что там кодить.

---

#### [Источник](https://startandroid.ru/ru/uroki/vse-uroki-spiskom/60-urok-23-activity-lifecycle-v-kakih-sostojanijah-mozhet-byt-activity.html)