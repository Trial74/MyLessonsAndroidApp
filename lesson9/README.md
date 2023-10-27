# Обработчики событий
### _Обработка нажатия кнопок и что такое обработчик_

---

#### Код файла activity_main.xml :
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="horizontal">
    <LinearLayout
        android:id="@+id/linearLayout1"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_margin="30dp"
        android:orientation="vertical">
        <TextView
            android:id="@+id/tvOut"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="center_horizontal"
            android:layout_marginBottom="50dp"
            android:text="TextView">
        </TextView>
        <Button
            android:id="@+id/btnOk"
            android:layout_width="100dp"
            android:layout_height="wrap_content"
            android:layout_gravity="center_horizontal"
            android:text="OK">
        </Button>
        <Button
            android:id="@+id/btnCancel"
            android:layout_width="100dp"
            android:layout_height="wrap_content"
            android:layout_gravity="center_horizontal"
            android:text="Cancel">
        </Button>
    </LinearLayout>
</LinearLayout> 
```

Есть TextView с текстом и две кнопки: OK и Cancel. По нажатию кнопки должно меняться содержимое TextView. По нажатию кнопки OK – будем выводить текст: «Нажата кнопка ОК», по нажатию Cancel – «Нажата кнопка Cancel».

Описание объектов вынесем за пределы метода onCreate. Это сделано для того, чтобы мы могли из любого метода обращаться к ним. В onCreate мы эти объекты заполним с помощью уже пройденного нами метода findViewById.
В итоге в файле MainActivity должен получиться такой код:

```Java
public class MainActivity extends Activity {
 
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
 
    }
}
```

Объекты tvOut, btnOk и btnCancel соответствуют View-элементам экрана и мы можем с ними работать. Нужно научить кнопку реагировать на нажатие. Для этого у кнопки есть метод setOnClickListener (View.OnClickListener l). На вход подается объект с интерфейсом View.OnClickListener. Именно этому объекту кнопка поручит обрабатывать нажатия.

```Java
OnClickListener oclBtnOk = new OnClickListener() {
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
 
    }
};
```

Eclipse подчеркивает OnClickListener красной линией

![Image alt](https://lh6.googleusercontent.com/-dlfJ7aoALt0/Tk9wBNgKgPI/AAAAAAAAAOw/rPIKJOe5MqM/s800/20110820_L0009_L_onClickListenerRedUnderline.JPG)

т.к. пока не знает его. Необходимо обновить секцию import. Жмем CTRL+SHIFT+O, Eclipse показывает нам, что он знает два интерфейса с именем onClickListener и предлагает выбрать. Нам нужен View.OnClickListener, т.к. метод кнопки setOnClickListener принимает на вход именно его.

Итак, мы создали объект oclBtnOk, который реализует интерфейс View.OnClickListener. Объект содержит метод onClick – это как раз то, что нам нужно. Именно этот метод будет вызван при нажатии кнопки. Мы решили, что по нажатию будем выводить текст: «Нажата кнопка ОК» в TextView (tvOut). Реализуем это.

В методе onClick:

```Java
tvOut.setText("Нажата кнопка ОК");
```

Обработчик нажатия готов. Осталось присвоить его кнопке с помощью метода setOnClickListener.

```Java
btnOk.setOnClickListener(oclBtnOk);
```

В итоге должен получится такой код:

```Java
public class MainActivity extends Activity {
 
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
 
        // создаем обработчик нажатия
        OnClickListener oclBtnOk = new OnClickListener() {
            @Override
            public void onClick(View v) {
                // Меняем текст в TextView (tvOut)
                tvOut.setText("Нажата кнопка ОК");
            }
        };
 
        // присвоим обработчик кнопке OK (btnOk)
        btnOk.setOnClickListener(oclBtnOk);
    }
}
```

Нажатие на Cancel пока ни к чему не приводит, т.к. для нее мы обработчик не создали и не присвоили. Давайте сделаем это аналогично, как для кнопки OK. Сначала мы создаем обработчик:

```Java
OnClickListener oclBtnCancel = new OnClickListener() {
    @Override
    public void onClick(View v) {
        // Меняем текст в TextView (tvOut)
        tvOut.setText("Нажата кнопка Cancel");
    }
};
```

Потом присваиваем его кнопке:

```Java
btnCancel.setOnClickListener(oclBtnCancel);
```

Сохраняем, запускаем, проверяем. Обе кнопки теперь умеют обрабатывать нажатия.

Давайте еще раз проговорим механизм обработки событий на примере нажатия кнопки. Сама кнопка обрабатывать нажатия не умеет, ей нужен обработчик (его также называют слушателем - listener), который присваивается с помощью метода setOnClickListener. Когда на кнопку нажимают, обработчик реагирует и выполняет код из метода onClick. Это можно изобразить так:

![Image alt](https://lh3.googleusercontent.com/-CnLJa0Ou9-g/Tk9wA9KY26I/AAAAAAAAAOs/EG89rGEWaVs/s800/20110820_L0009_L_ListenerSchema.JPG)