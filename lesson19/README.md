# Пишем простой калькулятор

## _Попробуем написать простейший калькулятор, который берет два числа и проводит с ними операции_

---

Откроем activity_main.xml и нарисуем экран:

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="vertical"
    android:layout_width="fill_parent"
    android:layout_height="fill_parent">
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:id="@+id/linearLayout1"
        android:layout_marginLeft="10pt"
        android:layout_marginRight="10pt"
        android:layout_marginTop="3pt">
        <EditText
            android:layout_weight="1"
            android:layout_height="wrap_content"
            android:layout_marginRight="5pt"
            android:id="@+id/etNum1"
            android:layout_width="match_parent"
            android:inputType="numberDecimal">
        </EditText>
        <EditText
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:layout_marginLeft="5pt"
            android:id="@+id/etNum2"
            android:layout_width="match_parent"
            android:inputType="numberDecimal">
        </EditText>
    </LinearLayout>
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:id="@+id/linearLayout2"
        android:layout_marginTop="3pt"
        android:layout_marginLeft="5pt"
        android:layout_marginRight="5pt">
        <Button
            android:layout_height="wrap_content"
            android:layout_width="match_parent"
            android:layout_weight="1"
            android:text="+"
            android:textSize="8pt"
            android:id="@+id/btnAdd">
        </Button>
        <Button
            android:layout_height="wrap_content"
            android:layout_width="match_parent"
            android:layout_weight="1"
            android:text="-"
            android:textSize="8pt"
            android:id="@+id/btnSub">
        </Button>
        <Button
            android:layout_height="wrap_content"
            android:layout_width="match_parent"
            android:layout_weight="1"
            android:text="*"
            android:textSize="8pt"
            android:id="@+id/btnMult">
        </Button>
        <Button
            android:layout_height="wrap_content"
            android:layout_width="match_parent"
            android:layout_weight="1"
            android:text="/"
            android:textSize="8pt"
            android:id="@+id/btnDiv">
        </Button>
    </LinearLayout>
    <TextView
        android:layout_height="wrap_content"
        android:layout_width="match_parent"
        android:layout_marginLeft="5pt"
        android:layout_marginRight="5pt"
        android:textSize="12pt"
        android:layout_marginTop="3pt"
        android:id="@+id/tvResult"
        android:gravity="center_horizontal">
    </TextView>
</LinearLayout>
```

Тут есть два поля ввода, 4 кнопки и текстовое поле для вывода. Обратите внимание на атрибут __inputType__ для __EditText__. Он задает тип содержимого. Я указал __numberDecimal__ – т.е. в поле получится ввести только цифры и запятую, буквы он не пропустит. Это удобно, не надо самому кодить различные проверки.

Для __TextView__ указан атрибут __gravity__. Он указывает, как будет расположен текст в __TextView__. Не путайте с __layout_gravity__, который отвечает за размещение __TextView__ в __ViewGroup__.

Теперь нам надо читать содержимое полей, определять какую кнопку нажали и выводить нужный результат. Открываем __MainActivity.java__ и пишем код

```Java
public class MainActivity extends Activity implements OnClickListener {
 
  EditText etNum1;
  EditText etNum2;
 
  Button btnAdd;
  Button btnSub;
  Button btnMult;
  Button btnDiv;
 
  TextView tvResult;
 
  String oper = "";
 
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
 
    // находим элементы
    etNum1 = (EditText) findViewById(R.id.etNum1);
    etNum2 = (EditText) findViewById(R.id.etNum2);
 
    btnAdd = (Button) findViewById(R.id.btnAdd);
    btnSub = (Button) findViewById(R.id.btnSub);
    btnMult = (Button) findViewById(R.id.btnMult);
    btnDiv = (Button) findViewById(R.id.btnDiv);
 
    tvResult = (TextView) findViewById(R.id.tvResult);
 
    // прописываем обработчик
    btnAdd.setOnClickListener(this);
    btnSub.setOnClickListener(this);
    btnMult.setOnClickListener(this);
    btnDiv.setOnClickListener(this);
 
  }
 
  @Override
  public void onClick(View v) {
    // TODO Auto-generated method stub
    float num1 = 0;
    float num2 = 0;
    float result = 0;
 
    // Проверяем поля на пустоту
    if (TextUtils.isEmpty(etNum1.getText().toString())
        || TextUtils.isEmpty(etNum2.getText().toString())) {
      return;
    }
 
    // читаем EditText и заполняем переменные числами
    num1 = Float.parseFloat(etNum1.getText().toString());
    num2 = Float.parseFloat(etNum2.getText().toString());
 
    // определяем нажатую кнопку и выполняем соответствующую операцию
    // в oper пишем операцию, потом будем использовать в выводе
    switch (v.getId()) {
    case R.id.btnAdd:
      oper = "+";
      result = num1 + num2;
      break;
    case R.id.btnSub:
      oper = "-";
      result = num1 - num2;
      break;
    case R.id.btnMult:
      oper = "*";
      result = num1 * num2;
      break;
    case R.id.btnDiv:
      oper = "/";
      result = num1 / num2;
      break;
    default:
      break;
    }
 
    // формируем строку вывода
    tvResult.setText(num1 + " " + oper + " " + num2 + " = " + result);
  }
}
```

Читаем значения, определяем кнопку, выполняем операцию и выводим в текстовое поле. Обработчиком нажатий на кнопки выступает __Activity__.

Все сохраним и запустим.
Давайте для большего функционала сделаем меню с пунктами очистки полей и выхода из приложения. Пункты будут называться __Reset__ и __Quit__.

Добавим две константы – это будут __ID__ пунктов меню.

```Java
public class MainActivity extends Activity implements OnClickListener {
 
final int MENU_RESET_ID = 1;
final int MENU_QUIT_ID = 2;
 
EditText etNum1;
```

Напишем код создания и обработки меню:

```Java
@Override
public boolean onCreateOptionsMenu(Menu menu) {
    menu.add(0, MENU_RESET_ID, 0, "Reset");
    menu.add(0, MENU_QUIT_ID, 0, "Quit");
    return super.onCreateOptionsMenu(menu);
}
 
// обработка нажатий на пункты меню
@Override
public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
        case MENU_RESET_ID:
            // очищаем поля
            etNum1.setText("");
            etNum2.setText("");
            tvResult.setText("");
            break;
        case MENU_QUIT_ID:
            // выход из приложения
            finish();
            break;
    }
    return super.onOptionsItemSelected(item);
}
```

Сохраним все, запустим. Появилось два пункта меню:
- __Reset__ – очищает все поля
- __Quit__ – закрывает приложение

Ещё доработаем немного: в условие деления ставим проверку деления на ноль:

```Java
if(num2 == 0){ Toast.makeText(this, "Делить на ноль нельзя!", Toast.LENGTH_LONG).show(); return; }
```

---

#### [Источник](https://startandroid.ru/ru/uroki/vse-uroki-spiskom/54-urok-19-pishem-prostoj-kalkuljator.html)