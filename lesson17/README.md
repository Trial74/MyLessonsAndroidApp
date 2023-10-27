# Создание View-компонент в рабочем приложении

## Добавляем компоненты на экран прямо из приложения

---

На [прошлом уроке](https://github.com/Trial74/MyLessonsAndroidApp/tree/master/lesson16) мы создавали компоненты в методе __Activity.onCreate__, т.е. при создании приложения. На этом уроке будем создавать уже в работающем приложении. Создавать будем __Button-ы__, т.к. они наглядней всего отображаются. Будем указывать текст, который будет отображен на кнопке и выравнивание: слева, по центру или справа. Также предусмотрим возможность удаления созданных элементов.

Создадим экран, который поможет нам создавать __View__ - компоненты. Открываем __activity_main.xml__ и пишем там следующее:

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="vertical"
    android:layout_width="fill_parent"
    android:layout_height="fill_parent">
    <RadioGroup
        android:layout_height="wrap_content"
        android:layout_width="match_parent"
        android:orientation="horizontal"
        android:id="@+id/rgGravity">
        <RadioButton
            android:layout_height="wrap_content"
            android:layout_width="wrap_content"
            android:checked="true"
            android:text="Left"
            android:id="@+id/rbLeft">
        </RadioButton>
        <RadioButton
            android:layout_height="wrap_content"
            android:layout_width="wrap_content"
            android:text="Center"
            android:id="@+id/rbCenter">
        </RadioButton>
        <RadioButton
            android:layout_height="wrap_content"
            android:layout_width="wrap_content"
            android:text="Right"
            android:id="@+id/rbRight">
        </RadioButton>
    </RadioGroup>
    <LinearLayout
        android:id="@+id/linearLayout1"
        android:layout_width="match_parent"
        android:orientation="horizontal"
        android:layout_height="wrap_content">
        <EditText
            android:layout_height="wrap_content"
            android:layout_width="wrap_content"
            android:layout_weight="1"
            android:id="@+id/etName"
            android:fadeScrollbars="true">
        </EditText>
        <Button
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Create"
            android:id="@+id/btnCreate">
        </Button>
        <Button
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Clear"
            android:id="@+id/btnClear">
        </Button>
    </LinearLayout>
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:id="@+id/llMain"
        android:orientation="vertical">
    </LinearLayout>
</LinearLayout>
```

Рассмотрим подробно экран.

- __rgGravity__ – это __RadioGroup__, с тремя __RadioButton (rbLeft, rbCenter, rbRight)__. Этот компонент мы используем для выбора выравнивания создаваемого компонента
- __etName__ – текстовое поле, здесь будем указывать текст, который будет отображаться на созданном компоненте
- __btnCreate__ – кнопка, запускающая процесс создания.
- __btnClear__ – кнопка, стирающая все, что создали
- __llMain__ – вертикальный __LinearLayout__, в котором будут создаваться компоненты

Экран готов, давайте кодить реализацию. Открываем __MainActivity.java__. Начнем с того, что опишем и найдем все необходимые нам компоненты. Кстати, у нас есть пара кнопок, которые мы будем использовать, значит им нужен обработчик. В качестве обработчика назначим __Activity__ (т.е. необходимо дописать: __implements OnClickListener)__ и создадим пустой пока метод обработки __onClick__:

```Java
public class MainActivity extends Activity implements OnClickListener{
 
  LinearLayout llMain;
  RadioGroup rgGravity;
  EditText etName;
  Button btnCreate;
  Button btnClear;
 
int wrapContent = LinearLayout.LayoutParams.WRAP_CONTENT;
 
   
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
     
    llMain = (LinearLayout) findViewById(R.id.llMain);
    rgGravity = (RadioGroup) findViewById(R.id.rgGravity);
    etName = (EditText) findViewById(R.id.etName);
 
    btnCreate = (Button) findViewById(R.id.btnCreate);
    btnCreate.setOnClickListener(this);
 
    btnClear = (Button) findViewById(R.id.btnClear);
    btnClear.setOnClickListener(this);
  }
 
  @Override
  public void onClick(View v) {
    // TODO Auto-generated method stub
     
  }
}
```

Я также создал переменную wrapContent и буду хранить в ней значение __LinearLayout.LayoutParams.WRAP_CONTENT__. Делаю это только для снижения громоздкости кода.

Теперь опишем процесс создания __Button__ - компонента заполнив метод __onClick__:

```Java
@Override
public void onClick(View v) {
    if(v.getId() == R.id.btnCreate){
        // Создание LayoutParams c шириной и высотой по содержимому
        LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(
                wrapContent, wrapContent);

        // определяем, какой RadioButton "чекнут" и
        // соответственно заполняем btnGravity
        if(rgGravity.getCheckedRadioButtonId() == R.id.rbLeft) lParams.gravity = Gravity.LEFT;
        else if (rgGravity.getCheckedRadioButtonId() == R.id.rbCenter) lParams.gravity = Gravity.CENTER_HORIZONTAL;
        else if (rgGravity.getCheckedRadioButtonId() == R.id.rbRight) lParams.gravity = Gravity.RIGHT;

        //Проверяем введено ли название кнопки
        if(etName.getText().toString().isEmpty()){
            Toast.makeText(this, "Введите название кнопки", Toast.LENGTH_SHORT).show();
        }else {
            // создаем Button, пишем текст и добавляем в LinearLayout
            Button btnNew = new Button(this);
            btnNew.setText(etName.getText().toString());
            llMain.addView(btnNew, lParams);
        }
    }
}
```

Разберем написанное. Для начала мы проверяем, что была нажата кнопка __btnCreate__ – т.е. кнопка создания. Затем создаем __LayoutParams__ с высотой и шириной по содержанию. Здесь я использовал переменную, про которую писал выше – __wrapContent__. Иначе получилось бы довольно громоздко.

Далее создаем переменную __btnGravity__, в которую по умолчанию запишем значение выравнивания __LEFT__. Для определения, какой __RadioButton__ выделен в данный момент, используем метод __getCheckedRadioButtonId__ – он для __RadioGroup__ возвращает __ID__ «чекнутого» __RadioButton-а__. Мы его сравниваем с нашими тремя __ID__ и заносим соответствующее значение в переменную __btnGravity__. Скидываем это значение в __gravity__ у __LayoutParams__.

Далее создаем кнопку и присваиваем ей текст из __etName__. Обратите внимание, что недостаточно написать __getText__, т.к. это не даст текста. Необходимо еще вызвать метод __toString__. Ну и в конце добавляем созданный __Button__ в наш __LinearLayout__.

Сохраним все и запустим приложение. Добавим несколько кнопок.

Кнопки должны появляться с указанным выравниванием и текстом.

Когда вводите текст, снизу появляется клавиатура и закрывает обзор. Чтобы она исчезла, надо нажать кнопку __Back__ (Назад) на эмуляторе или __ESC__ на обычной клавиатуре. Если клавиатура появляется японская с иероглифами, вызовите контекстное меню для поля ввода (долгое нажатие левой кнопкой мыши), нажмите __Input method__ и выберите из списка __Android Keyboard__.

Осталось нереализованной кнопка __Clear__, которая призвана удалять все созданное. Для этого нам необходимо дополнить метод __onClick__:

```Java
    else if (v.getId() == R.id.btnClear) {
        llMain.removeAllViews(); //Очистка элементов с динамического слоя
        Toast.makeText(this, "Удалено", Toast.LENGTH_SHORT).show();
}
```

Метод __removeAllViews__ удаляет все дочерние __View__ - компоненты с нашего LinearLayout. С помощью Toast выводим на экран сообщение об успехе. Сохраним, запустим и проверим. Добавляем несколько кнопок, жмем кнопку __Clear__ и наблюдаем результат.

В итоге у нас получилось очень даже динамическое приложение, которое умеет менять само себя.

На форуме задают вопрос: как потом получить доступ к этим созданным компонентам. Тут есть пара простых вариантов.

1) При создании вы можете сами присваивать компонентам __ID__.  Это делается методом __setId__. И потом по этим __ID__ просто вызываете __findViewById__.

2) Вы можете сохранять созданные компоненты в свой массив или список. Либо можете воспользоваться методом __getChildAt__. Вызов этого метода для __llMain__ позволит получить его дочерние компоненты по индексу. Получить кол-во дочерних элементов позволит метод __getChildCount__.

Полный код урока:

```Java
public class MainActivity extends AppCompatActivity {

    LinearLayout llMain;
    RadioGroup rgGravity;
    EditText etName;
    Button btnCreate;
    Button btnClear;

    int wrapContent = LinearLayout.LayoutParams.WRAP_CONTENT;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        llMain = (LinearLayout) findViewById(R.id.llMain);
        rgGravity = (RadioGroup) findViewById(R.id.rgGravity);
        etName = (EditText) findViewById(R.id.etName);

        btnCreate = (Button) findViewById(R.id.btnCreate);
        btnCreate.setOnClickListener(this::onClick);

        btnClear = (Button) findViewById(R.id.btnClear);
        btnClear.setOnClickListener(this::onClick);
    }

    public void onClick(View v) {

        if(v.getId() == R.id.btnCreate){
            // Создание LayoutParams c шириной и высотой по содержимому
            LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(
                    wrapContent, wrapContent);

            // определяем, какой RadioButton "чекнут" и
            // соответственно заполняем btnGravity
            if(rgGravity.getCheckedRadioButtonId() == R.id.rbLeft) lParams.gravity = Gravity.LEFT;
            else if (rgGravity.getCheckedRadioButtonId() == R.id.rbCenter) lParams.gravity = Gravity.CENTER_HORIZONTAL;
            else if (rgGravity.getCheckedRadioButtonId() == R.id.rbRight) lParams.gravity = Gravity.RIGHT;

            //Проверяем введено ли название кнопки
            if(etName.getText().toString().isEmpty()){
                Toast.makeText(this, "Введите название кнопки", Toast.LENGTH_SHORT).show();
            }else {
                // создаем Button, пишем текст и добавляем в LinearLayout
                Button btnNew = new Button(this);
                btnNew.setText(etName.getText().toString());
                llMain.addView(btnNew, lParams);
            }
        } else if (v.getId() == R.id.btnClear) {
            llMain.removeAllViews(); //Очистка элементов с динамического слоя
            Toast.makeText(this, "Удалено", Toast.LENGTH_SHORT).show();
        }
    }
}
```

---

#### [Источник](https://startandroid.ru/ru/uroki/vse-uroki-spiskom/50-urok-17-sozdanie-view-komponent-v-rabochem-prilozhenii.html)