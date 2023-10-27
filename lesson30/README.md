# Подробнее про onActivityResult. Зачем нужны requestCode и resultCode

## разбираемся, зачем нужны requestCode и resultCode в onActivityResult

---

На прошлом уроке мы поверхностно рассмотрели, как вызвать __Activity__, и как сделать так, чтобы она вернула результат. Рассмотрим немного подробней этот механизм. Создадим приложение, которое будет вызывать два разных __Activity__ и получать от них результат. Как мы помним, результат приходит в метод [__onActivityResult__](http://developer.android.com/reference/android/app/Activity.html#onActivityResult(int,%20int,%20android.content.Intent)). И __requestCode__ используется, чтобы отличать друг от друга пришедшие результаты. А __resultCode__ – позволяет определить успешно прошел вызов или нет.

Нарисуем экран в __activity_main.xml__:

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="fill_parent"
    android:layout_height="fill_parent"
    android:orientation="vertical">
    <TextView
        android:id="@+id/tvText"
        android:layout_width="fill_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="20dp"
        android:gravity="center_horizontal"
        android:text="Hello World"
        android:textSize="20sp">
    </TextView>
    <LinearLayout
        android:id="@+id/linearLayout1"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_margin="20dp"
        android:orientation="horizontal">
        <Button
            android:id="@+id/btnColor"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginRight="5dp"
            android:layout_weight="1"
            android:text="Color">
        </Button>
        <Button
            android:id="@+id/btnAlign"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginLeft="5dp"
            android:layout_weight="1"
            android:text="Alignment">
        </Button>
    </LinearLayout>
</LinearLayout>
```

На экране __TextView__ с текстом. И две кнопки для выбора цвета шрифта и выравнивания текста в __TextView__. Нажатие на кнопку будет вызывать __Activity__ для выбора и получать обратно результат.

Давайте начнем кодить в __MainActivity.java__:

```Java
package ru.startandroid.develop.p0301activityresult;
 
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
 
public class MainActivity extends Activity implements OnClickListener {
   
  TextView tvText;
  Button btnColor;
  Button btnAlign;
   
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
         
        tvText = (TextView) findViewById(R.id.tvText);
         
        btnColor = (Button) findViewById(R.id.btnColor);
        btnAlign = (Button) findViewById(R.id.btnAlign);
         
        btnColor.setOnClickListener(this);
        btnAlign.setOnClickListener(this);
    }
 
  @Override
  public void onClick(View v) {
    // TODO Auto-generated method stub
     
  }
}
```

Определили экранные элементы, прописали обработчик кнопкам и пока остановимся на этом.

Создадим два других __Activity__. Начнем с __Activity__ для выбора цвета. Создадим __layout-файл color.xml__:

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="horizontal">
    <Button
        android:id="@+id/btnRed"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_margin="5dp"
        android:layout_weight="1"
        android:text="Red">
    </Button>
    <Button
        android:id="@+id/btnGreen"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_margin="5dp"
        android:layout_weight="1"
        android:text="Green">
    </Button>
    <Button
        android:id="@+id/btnBlue"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_margin="5dp"
        android:layout_weight="1"
        android:text="Blue">
    </Button>
</LinearLayout>
```

Создаем класс __ColorActivity. ColorActivity.java__:

```Java
package ru.startandroid.develop.p0301activityresult;
 
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
 
public class ColorActivity extends Activity implements OnClickListener {
   
  Button btnRed;
  Button btnGreen;
  Button btnBlue;
   
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.color);
     
    btnRed = (Button) findViewById(R.id.btnRed);
    btnGreen = (Button) findViewById(R.id.btnGreen);
    btnBlue = (Button) findViewById(R.id.btnBlue);
     
    btnRed.setOnClickListener(this);
    btnGreen.setOnClickListener(this);
    btnBlue.setOnClickListener(this);
  }
 
  @Override
  public void onClick(View v) {
    Intent intent = new Intent();
    switch (v.getId()) {
    case R.id.btnRed:
      intent.putExtra("color", Color.RED);
      break;
    case R.id.btnGreen:
      intent.putExtra("color", Color.GREEN);
      break;
    case R.id.btnBlue:
      intent.putExtra("color", Color.BLUE);
      break;
    }
    setResult(RESULT_OK, intent);
    finish();
  }
}
```

Как обычно определяем элементы, присваиваем обработчик кнопкам и реализуем __onClick__. В __onClick__ мы создаем __Intent__, затем определяем, кнопка с каким цветом была нажата и помещаем в __Intent__ объект с именем __color__ и значением цвета. Ставим статус __RESULT_OK__, указываем, что надо вернуть объект intent в качестве результата и закрываем __Activity__. Для значения цветов используем системные константы.

Аналогично создаем __Activity__ для выбора выравнивания.

__Layout-файл align.xml__:

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="horizontal">
    <Button
        android:id="@+id/btnLeft"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_margin="5dp"
        android:layout_weight="1"
        android:text="Left">
    </Button>
    <Button
        android:id="@+id/btnCenter"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_margin="5dp"
        android:layout_weight="1"
        android:text="Center">
    </Button>
    <Button
        android:id="@+id/btnRight"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_margin="5dp"
        android:layout_weight="1"
        android:text="Right">
    </Button>
</LinearLayout>
```

__AlignActivity.java__:

```Java
package ru.startandroid.develop.p0301activityresult;
 
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
 
public class AlignActivity extends Activity implements OnClickListener {
   
  Button btnLeft;
  Button btnCenter;
  Button btnRight;
   
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.align);
     
    btnLeft = (Button) findViewById(R.id.btnLeft);
    btnCenter = (Button) findViewById(R.id.btnCenter);
    btnRight = (Button) findViewById(R.id.btnRight);
     
    btnLeft.setOnClickListener(this);
    btnCenter.setOnClickListener(this);
    btnRight.setOnClickListener(this);
  }
 
  @Override
  public void onClick(View v) {
    Intent intent = new Intent();
    switch (v.getId()) {
    case R.id.btnLeft:
      intent.putExtra("alignment", Gravity.LEFT);
      break;
    case R.id.btnCenter:
      intent.putExtra("alignment", Gravity.CENTER);
      break;
    case R.id.btnRight:
      intent.putExtra("alignment", Gravity.RIGHT);
      break;
    }
    setResult(RESULT_OK, intent);
    finish();
  }
}
```

Здесь все аналогично, как и в __ColorActivity__. Только работаем не с цветами, а с выравниванием. Не забудьте прописать оба __Activity__ в манифесте.

Теперь можем завершить код в __MainActivity.java__. Добавим пару своих констант в класс для удобства:

```Java
final int REQUEST_CODE_COLOR = 1;
final int REQUEST_CODE_ALIGN = 2;
```

Эти константы далее будем использовать в качестве __requestCode__.

Допишем метод __onClick__:

```Java
@Override
public void onClick(View v) {
  Intent intent;
  switch (v.getId()) {
  case R.id.btnColor:
    intent = new Intent(this, ColorActivity.class);
    startActivityForResult(intent, REQUEST_CODE_COLOR);
    break;
  case R.id.btnAlign:
    intent = new Intent(this, AlignActivity.class);
    startActivityForResult(intent, REQUEST_CODE_ALIGN);
    break;
  }
}
```

Мы определяем, какая кнопка была нажата и посылаем __Intent__ с ожиданием возврата результата. Два вызова отличаются классом вызываемого __Activity__ и параметром requestCode в методе startActivityForResult. При вызове __ColorActivity__ используем константу __REQUEST_CODE_COLOR__, а при вызове __AlignActivity - REQUEST_CODE_ALIGN__. Эту константу мы обратно получим в методе обработки результата – __onActivityResult__, и по ней сможем определить из какого именно __Activity__ пришел результат.

Давайте реализуем метод __onActivityResult__ в __MainActivity.java__:

```Java
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
  // запишем в лог значения requestCode и resultCode
  Log.d("myLogs", "requestCode = " + requestCode + ", resultCode = " + resultCode);
  // если пришло ОК
  if (resultCode == RESULT_OK) {
    switch (requestCode) {
    case REQUEST_CODE_COLOR:
      int color = data.getIntExtra("color", Color.WHITE);
      tvText.setTextColor(color);
      break;
    case REQUEST_CODE_ALIGN:
      int align = data.getIntExtra("alignment", Gravity.LEFT);
      tvText.setGravity(align);
      break;
    }
  // если вернулось не ОК
  } else {
    Toast.makeText(this, "Wrong result", Toast.LENGTH_SHORT).show();
  }
}
```

Для наглядности пишем в лог значения переменных.

Вспоминаем, что в __ColorActivity__ и __AlignActivity__ в методе __setResult__ мы ставили статус __RESULT_OK__ при отправке результата. Значит в __onActivityResult__ нам надо ожидать этот статус, как обозначение успешного окончания вызова.

Если вызов прошел успешно __(resultCode = RESULT_OK)__, то мы смотрим значение __requestCode__. Если оно равно константе __REQUEST_CODE_COLOR__, то вспоминаем, что мы использовали эту константу в методе __startActivityForResult__, когда отправляли запрос на выбор цвета. Значит, нам пришел результат этого выбора. Мы берем __Intent (data)__ и извлекаем из него значение объекта с именем __color__ и присваиваем это значение цвету текста в __TextView__. Константа __Color.WHITE__ в методе __getIntExtra__ означает значение по умолчанию. Т.е. если в __Intent__ не найдется объекта с именем __color__, то метод вернет белый __(white)__ цвет.

Аналогично для __REQUEST_CODE_ALIGN__. Эту константу мы использовали для запроса выбора выравнивания. И если в методе __onActivityResult__ параметр __requestCode__ = этой константе, значит пришел ответ на запрос выравнивания. И мы считываем это значение из __Intent__ и присваиваем его атрибуту __Gravity__ для __TextView__.

Если __resultCode__ не равен __RESULT_OK__, значит что-то пошло не так. Выводим на экран соответствующее сообщение. Этот случай может наступить, например, если на экране выбора не делать выбор, а нажать кнопку Назад.

Давайте все сохраним и запустим приложение.
- Нажмем Color
- и выберем, например Red

Цвет изменился

смотрим лог:

_requestCode = 1, resultCode = -1_

__requestCode__ пришедший в метод __onActivityResult__ равен 1. Все верно, это значение константы __REQUEST_CODE_COLOR__, которое мы использовали при вызове.

__resultCode = -1__ – это значение системной константы __RESULT_OK__

Т.е. все верно, пришел ответ на запрос цвета, и его статус = __RESULT_OK__.

Теперь жмем __Alignment__ и выбираем __Right__, получаем выравнивание вправо:

Смотрим лог:

_requestCode = 2, resultCode = -1_

__requestCode = 2__, что равно константе __REQUEST_CODE_ALIGN__. Значит пришел ответ на запрос выравнивания.

__resultCode = -1__, т.е. __RESULT_OK__.

Теперь снова жмем __Color__
но вместо того, чтобы выбрать цвет нажмем кнопку назад

Отобразилось наше сообщение об ошибке. Смотрим логи:

_requestCode = 1, resultCode = 0_

__requestCode = 1__ – все верно, мы запрашивали цвет __REQUEST_CODE_COLOR__

__resultCode = 0__, это значение константы __RESULT_CANCELED__, значит вызов прошел неудачно

Ограничений на значение статуса в методе __setResult__ нет. __RESULT_OK__ и __RESULT_CANCELED__ – системные общепринятые константы. Но вы можете свободно использовать свои значения, если в этом есть необходимость.

Итак, подведем итог.

__requestCode__ – это в некотором роде __ID__ запроса. Задается в методе __startActivityForResult__, и проверяется потом в __onActivityResult__, чтобы точно знать, на какой вызов пришел ответ.

__resultCode__ – статус вызова. Задается в методе __setResult__, и проверяется в __onActivityResult__, чтобы понять насколько успешно прошел вызов. Если при вызове что-то пошло не так, то вернется системная константа __RESULT_CANCELED__.

Полный код __MainActivity.java__:

```Java
package ru.startandroid.develop.p0301activityresult;
 
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
 
public class MainActivity extends Activity implements OnClickListener {
 
  final int REQUEST_CODE_COLOR = 1;
  final int REQUEST_CODE_ALIGN = 2;
 
  TextView tvText;
  Button btnColor;
  Button btnAlign;
 
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
 
    tvText = (TextView) findViewById(R.id.tvText);
 
    btnColor = (Button) findViewById(R.id.btnColor);
    btnAlign = (Button) findViewById(R.id.btnAlign);
 
    btnColor.setOnClickListener(this);
    btnAlign.setOnClickListener(this);
  }
 
  @Override
  public void onClick(View v) {
    Intent intent;
    switch (v.getId()) {
    case R.id.btnColor:
      intent = new Intent(this, ColorActivity.class);
      startActivityForResult(intent, REQUEST_CODE_COLOR);
      break;
    case R.id.btnAlign:
      intent = new Intent(this, AlignActivity.class);
      startActivityForResult(intent, REQUEST_CODE_ALIGN);
      break;
    }
  }
 
  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    // запишем в лог значения requestCode и resultCode
    Log.d("myLogs", "requestCode = " + requestCode + ", resultCode = " + resultCode);
    // если пришло ОК
    if (resultCode == RESULT_OK) {
      switch (requestCode) {
      case REQUEST_CODE_COLOR:
        int color = data.getIntExtra("color", Color.WHITE);
        tvText.setTextColor(color);
        break;
      case REQUEST_CODE_ALIGN:
        int align = data.getIntExtra("alignment", Gravity.LEFT);
        tvText.setGravity(align);
        break;
      }
      // если вернулось не ОК
    } else {
      Toast.makeText(this, "Wrong result", Toast.LENGTH_SHORT).show();
    }
  }
}
```

---

#### [Источник](https://startandroid.ru/ru/uroki/vse-uroki-spiskom/69-urok-30-podrobnee-pro-onactivityresult-zachem-nuzhny-requestcode-i-resultcode.html)