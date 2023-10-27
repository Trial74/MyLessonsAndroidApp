# Меняем layoutParams в рабочем приложении

### _Изменяем layout-параметры для уже существующих компонентов экрана_

---

Мы умеем создавать экранные компоненты и настраивать для них расположение с помощью __LayoutParams__. В этом уроке разберемся, как изменять __layout__ - параметры уже существующих компонентов.

Менять мы будем вес – __weight__. Нарисуем __SeekBar__ (регулятор или «ползунок») и две кнопки. И будем регулировать пространство занимаемое кнопками, используя параметр веса.

Открываем activity_main.xml и создаем такой экран:

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="vertical"
    android:layout_width="fill_parent"
    android:layout_height="fill_parent">
    <SeekBar
        android:layout_height="wrap_content"
        android:layout_width="match_parent"
        android:max="100"
        android:progress="50"
        android:layout_marginTop="20dp"
        android:id="@+id/sbWeight">
    </SeekBar>
    <LinearLayout
        android:id="@+id/linearLayout1"
        android:layout_width="match_parent"
        android:orientation="horizontal"
        android:layout_height="wrap_content"
        android:layout_marginTop="30dp">
        <Button
            android:layout_height="wrap_content"
            android:id="@+id/btn1"
            android:text="Button1"
            android:layout_weight="1"
            android:layout_width="wrap_content">
        </Button>
        <Button
            android:layout_height="wrap_content"
            android:id="@+id/btn2"
            android:text="Button2"
            android:layout_weight="1"
            android:layout_width="wrap_content">
        </Button>
    </LinearLayout>
</LinearLayout>
```

Мы используем компонент __SeekBar__. Он похож на полосу прокрутки и позволяет задавать какое-либо значение из диапазона. У этого компонента есть свойства __max__ и __progress__. __Max__ – это какое значение выдает __SeekBar__, когда он выкручен на максимум. __Progress__ – это текущее значение ползунка. Максимум сделаем = 100, а текущее значение будет на половине – 50.

Кнопки у нас с шириной по содержимому и вес для обоих = 1. Они поровну делят пространство __LinearLayout__, в котором находятся.

Осталось только написать нужный код, чтобы все заработало. Открываем __MainActivity.java__, опишем и найдем компоненты и получим доступ к их LayoutParams.

```Java
public class MainActivity extends Activity {
 
  SeekBar sbWeight;
  Button btn1;
  Button btn2;
 
  LinearLayout.LayoutParams lParams1;
  LinearLayout.LayoutParams lParams2;
 
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
 
    sbWeight = (SeekBar) findViewById(R.id.sbWeight);
 
    btn1 = (Button) findViewById(R.id.btn1);
    btn2 = (Button) findViewById(R.id.btn2);
 
    lParams1 = (LinearLayout.LayoutParams) btn1.getLayoutParams();
    lParams2 = (LinearLayout.LayoutParams) btn2.getLayoutParams();
  }
}
```

Мы используем метод __getLayoutParams__ для получения __LayoutParams__ компонента. Но этот метод возвращает базовый ViewGroup.LayoutParams, а нам нужен __LinearLayout.LayoutParams__, поэтому делаем преобразование. В результате - __lParams1__ и __lParams2__ теперь являются __LayoutParams__ для компонентов __btn1__ и __btn2__. Т.е. работая, например, с __lParams1__ мы влияем на __btn1__. Сейчас мы это используем.

Для __SeekBar__ нужен будет обработчик, который будет реагировать на изменения. Это мы поручим __Activity__. Для этого надо добавить к описанию класса __implements OnSeekBarChangeListener__:

```Java
public class MainActivity extends Activity implements OnSeekBarChangeListener {
```

А также надо добавить методы обработчика, которые теперь обязана реализовывать __Activity__.

```Java
@Override
public void onProgressChanged(SeekBar seekBar, int progress,
    boolean fromUser) {
 
}
 
@Override
public void onStartTrackingTouch(SeekBar seekBar) {
 
}
 
@Override
public void onStopTrackingTouch(SeekBar seekBar) {
 
}
```

Обработчик содержит три метода. Из названий понятно, что:

- [onStartTrackingTouch](http://developer.android.com/reference/android/widget/SeekBar.OnSeekBarChangeListener.html#onStartTrackingTouch(android.widget.SeekBar)) срабатывает, когда начинаем тащить ползунок
- [onProgressChanged](http://developer.android.com/reference/android/widget/SeekBar.OnSeekBarChangeListener.html#onProgressChanged(android.widget.SeekBar,%20int,%20boolean)) срабатывает все время, пока значение меняется
- [onStopTrackingTouch](http://developer.android.com/reference/android/widget/SeekBar.OnSeekBarChangeListener.html#onStopTrackingTouch(android.widget.SeekBar)) срабатывает, когда отпускаем ползунок

Мы будем использовать метод __onProgressChanged__. Так изменения будут видны во время перетаскивания ползунка.

```Java
@Override
public void onProgressChanged(SeekBar seekBar, int progress,
    boolean fromUser) {
    int leftValue = progress;
    int rightValue = seekBar.getMax() - progress;
    // настраиваем вес
    lParams1.weight = leftValue;
    lParams2.weight = rightValue;
    // в текст кнопок пишем значения переменных
    btn1.setText(String.valueOf(leftValue));
    btn2.setText(String.valueOf(rightValue));
}
```

Переменная __leftValue__ – текущее значение __SeekBar__, т.е. то что слева от ползунка
переменная __rightValue__ – то, что справа от ползунка, т.е. из максимума вычесть текущее значение.

Соответственно эти значения и используем как вес. Чем ползунок левее, тем меньше __leftValue__ и больше __rightValue__, а значит меньше ширина __btn1__ и больше ширина __btn2__. И наоборот.

Также для наглядности в текст кнопок будем записывать значения переменных.

Ну и конечно не забываем, что надо обработчик __(Activity)__ присвоить __View__ - компоненту, события которого необходимо обрабатывать:

```Java
setContentView(R.layout.main);
 
sbWeight = (SeekBar) findViewById(R.id.sbWeight);
sbWeight.setOnSeekBarChangeListener(this);
 
btn1 = (Button) findViewById(R.id.btn1);
```

(Обратите внимание. Я ввожу новый прием подачи кода. То, что подчеркнуто – это новый код, а обычный шрифт – уже существующий код. Вам надо найти существующий код и дописать к нему новый, чтобы получился этот фрагмент.)

Все сохраним и запустим приложение. Перетаскивая ползунок, меняем размеры кнопок

Полный код урока:

```Java
public class MainActivity extends Activity implements OnSeekBarChangeListener {
 
  SeekBar sbWeight;
  Button btn1;
  Button btn2;
 
  LinearLayout.LayoutParams lParams1;
  LinearLayout.LayoutParams lParams2;
 
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
 
    sbWeight = (SeekBar) findViewById(R.id.sbWeight);
    sbWeight.setOnSeekBarChangeListener(this);
 
    btn1 = (Button) findViewById(R.id.btn1);
    btn2 = (Button) findViewById(R.id.btn2);
 
    lParams1 = (LinearLayout.LayoutParams) btn1.getLayoutParams();
    lParams2 = (LinearLayout.LayoutParams) btn2.getLayoutParams();
  }
 
  @Override
  public void onProgressChanged(SeekBar seekBar, int progress,
      boolean fromUser) {
    int leftValue = progress;
    int rightValue = seekBar.getMax() - progress;
    // настраиваем вес
    lParams1.weight = leftValue;
    lParams2.weight = rightValue;
    // в текст кнопок пишем значения переменных
    btn1.setText(String.valueOf(leftValue));
    btn2.setText(String.valueOf(rightValue));
  }
 
  @Override
  public void onStartTrackingTouch(SeekBar seekBar) {
  }
 
  @Override
  public void onStopTrackingTouch(SeekBar seekBar) {
  }
}
```

---

#### [Источник](https://startandroid.ru/ru/uroki/vse-uroki-spiskom/51-urok-18-menjaem-layoutparams-v-rabochem-prilozhenii.html)