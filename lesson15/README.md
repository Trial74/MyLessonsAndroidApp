# Контекстное меню

### _Создадим контекстное меню_

---

Контекстное меню вызывается в Андроид длительным нажатием на каком-либо экранном компоненте. Обычно оно используется в списках, когда на экран выводится список однородных объектов (например письма в почт.ящике) и, чтобы выполнить действие с одним из этих объектов, мы вызываем контекстное меню для него. Но т.к. списки мы еще не проходили, сделаем пример попроще и будем вызывать контекстное меню для __TextView__.

#### Код файла activity_main.xml :

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="vertical"
    android:layout_width="fill_parent"
    android:layout_height="fill_parent">
    <TextView
        android:layout_height="wrap_content"
        android:textSize="26sp"
        android:layout_width="wrap_content"
        android:id="@+id/tvColor"
        android:layout_marginBottom="50dp"
        android:layout_marginTop="50dp"
        android:text="Text color">
    </TextView>
    <TextView
        android:layout_width="fill_parent"
        android:layout_height="wrap_content"
        android:textSize="22sp"
        android:id="@+id/tvSize"
        android:text="Text size">
    </TextView>
</LinearLayout>
```

Для первого TextView мы сделаем контекстное меню, с помощью которого будем менять цвет текста. Для второго – будем менять размер текста.

Принцип создания контекстного меню похож на создание обычного меню. Но есть и отличия.

Метод создания [onCreateContextMenu](http://developer.android.com/reference/android/app/Activity.html#onCreateContextMenu(android.view.ContextMenu,%20android.view.View,%20android.view.ContextMenu.ContextMenuInfo)) вызывается каждый раз перед показом меню. На вход ему передается:

- __ContextMenu__, в который мы будем добавлять пункты
- __View__ - элемент экрана, для которого вызвано контекстное меню
- __ContextMenu.ContextMenuInfo__ – содержит доп.информацию, когда контекстное меню вызвано для элемента списка. Пока мы это не используем, но, когда будем изучать списки, увидим, что штука полезная.

Метод обработки [onContextItemSelected](http://developer.android.com/reference/android/app/Activity.html#onContextItemSelected(android.view.MenuItem)) аналогичный методу __onOptionsItemSelected__ для обычного меню. На вход передается __MenuItem__ – пункт меню, который был нажат.

Также нам понадобится третий метод [registerForContextMenu](http://developer.android.com/reference/android/app/Activity.html#registerForContextMenu(android.view.View)). На вход ему передается View и это означает, что для этой __View__ необходимо создавать контекстное меню. Если не выполнить этот метод, контекстное меню для View создаваться не будет.

Давайте кодить, открываем __MainActivity.java__. Опишем и найдем __TextView__ и укажем, что необходимо создавать для них контекстное меню.

```Java
TextView tvColor, tvSize;
 
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.main);
       
      tvColor = (TextView) findViewById(R.id.tvColor);
      tvSize = (TextView) findViewById(R.id.tvSize);
       
      // для tvColor и tvSize необходимо создавать контекстное меню
      registerForContextMenu(tvColor);
      registerForContextMenu(tvSize);
  }
```

Теперь опишем создание контекстных меню. Используем константы для хранения __ID__ пунктов меню.

```Java
final int MENU_COLOR_RED = 1;
final int MENU_COLOR_GREEN = 2;
final int MENU_COLOR_BLUE = 3;
 
final int MENU_SIZE_22 = 4;
final int MENU_SIZE_26 = 5;
final int MENU_SIZE_30 = 6;
```

И создаем

```Java
@Override
public void onCreateContextMenu(ContextMenu menu, View v,
    ContextMenuInfo menuInfo) {
    // TODO Auto-generated method stub
    switch (v.getId()) {
        case R.id.tvColor:
          menu.add(0, MENU_COLOR_RED, 0, "Red");
          menu.add(0, MENU_COLOR_GREEN, 0, "Green");
          menu.add(0, MENU_COLOR_BLUE, 0, "Blue");
          break;
        case R.id.tvSize:
          menu.add(0, MENU_SIZE_22, 0, "22");
          menu.add(0, MENU_SIZE_26, 0, "26");
          menu.add(0, MENU_SIZE_30, 0, "30");
          break;
    }
}
```

Обратите внимание, что мы по __ID__ определяем __View__, для которого вызвано __контекстное меню__ и в зависимости от этого создаем определенное меню. Т.е. если контекстное меню вызвано для tvColor, то мы создаем __меню с перечислением цветов__, а если для __tvSize__ – __с размерами шрифта__.

В качестве __ID__ пунктов мы использовали константы. Группировку и сортировку не используем, поэтому используем нули в качестве соответствующих параметров.

Можно все сохранить и запустить. При долгом нажатии на TextView должны появляться контекстные меню.

Но нажатие на них ничего не дает, т.к. мы не прописали обработку в методе __onContextItemSelected__. Давайте пропишем:

```Java
@Override
public boolean onContextItemSelected(MenuItem item) {
    // TODO Auto-generated method stub
    switch (item.getItemId()) {
    // пункты меню для tvColor
        case MENU_COLOR_RED:
          tvColor.setTextColor(Color.RED);
          tvColor.setText("Text color = red");
          break;
        case MENU_COLOR_GREEN:
          tvColor.setTextColor(Color.GREEN);
          tvColor.setText("Text color = green");
          break;
        case MENU_COLOR_BLUE:
          tvColor.setTextColor(Color.BLUE);
          tvColor.setText("Text color = blue");
          break;
        // пункты меню для tvSize
        case MENU_SIZE_22:
          tvSize.setTextSize(22);
          tvSize.setText("Text size = 22");
          break;
        case MENU_SIZE_26:
          tvSize.setTextSize(26);
          tvSize.setText("Text size = 26");
          break;
        case MENU_SIZE_30:
          tvSize.setTextSize(30);
          tvSize.setText("Text size = 30");
          break;
    }
    return super.onContextItemSelected(item);
}
```

В этом методе мы определяем по __ID__, какой пункт меню был нажат. И выполняем соответствующие действия: меняем цвет текста для __tvColor__ или размер шрифта для __tvSize__. Сохраняем, запускаем и проверяем, что контекстные меню теперь реагируют на нажатия и делают то, что от них требуется.

Для расширения кругозора я хотел бы еще кое-что написать по этой теме. Возможно, это покажется пока сложноватым, так что если вдруг будет непонятно, ничего страшного. Итак, мысли вслух.

Мы использовали метод __registerForContextMenu (View view)__ для включения контекстного меню для определенного __View__. Этот метод принадлежит классу __Activity__. Я посмотрел исходники этого метода, там написано следующее:

```Java
public void registerForContextMenu(View view) {
  view.setOnCreateContextMenuListener(this);
}
```

Вспоминаем наш [урок по обработчикам](https://github.com/Trial74/MyLessonsAndroidApp/tree/master/lesson9) и смотрим хелп по методу [setOnCreateContextMenuListener (View.OnCreateContextMenuListener l)](http://developer.android.com/reference/android/view/View.html#setOnCreateContextMenuListener(android.view.View.OnCreateContextMenuListener)). Получается, что __View__ в качестве обработчика создания контекстного меню использует объект __this__. В данном случае, этот код в __Activity__, значит __this__ – это __Activity__ и есть. Т.е. когда __View__ хочет показать контекстное меню, оно обращается к обработчику __(Activity)__, а он уже выполняет свой метод __onCreateContextMenu__. Т.е. тот же самый принцип, что и при обычном нажатии __(Click)__.

И строка в __MainActivity.java__:

```Java
registerForContextMenu(tvColor);
```
абсолютно равнозначна этой строке:

```Java
tvColor.setOnCreateContextMenuListener(this);
```

Вообще мы можем создать свой объект, реализующий интерфейс __View.OnCreateContextMenuListener__ и использовать его вместо __Activity__ в качестве обработчика создания контекстного меню.

Не забывайте, что для контекстного меню вы также можете использовать __XML__

Полный код урока:

```Java
public class MainActivity extends Activity {
 
  final int MENU_COLOR_RED = 1;
  final int MENU_COLOR_GREEN = 2;
  final int MENU_COLOR_BLUE = 3;
 
  final int MENU_SIZE_22 = 4;
  final int MENU_SIZE_26 = 5;
  final int MENU_SIZE_30 = 6;
 
  TextView tvColor, tvSize;
 
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
 
    tvColor = (TextView) findViewById(R.id.tvColor);
    tvSize = (TextView) findViewById(R.id.tvSize);
 
    // для tvColor и tvSize необходимо создавать контекстное меню
    registerForContextMenu(tvColor);
    registerForContextMenu(tvSize);
  }
 
  @Override
  public void onCreateContextMenu(ContextMenu menu, View v,
      ContextMenuInfo menuInfo) {
    // TODO Auto-generated method stub
    switch (v.getId()) {
    case R.id.tvColor:
      menu.add(0, MENU_COLOR_RED, 0, "Red");
      menu.add(0, MENU_COLOR_GREEN, 0, "Green");
      menu.add(0, MENU_COLOR_BLUE, 0, "Blue");
      break;
    case R.id.tvSize:
      menu.add(0, MENU_SIZE_22, 0, "22");
      menu.add(0, MENU_SIZE_26, 0, "26");
      menu.add(0, MENU_SIZE_30, 0, "30");
      break;
    }
  }
 
  @Override
  public boolean onContextItemSelected(MenuItem item) {
    // TODO Auto-generated method stub
    switch (item.getItemId()) {
    // пункты меню для tvColor
    case MENU_COLOR_RED:
      tvColor.setTextColor(Color.RED);
      tvColor.setText("Text color = red");
      break;
    case MENU_COLOR_GREEN:
      tvColor.setTextColor(Color.GREEN);
      tvColor.setText("Text color = green");
      break;
    case MENU_COLOR_BLUE:
      tvColor.setTextColor(Color.BLUE);
      tvColor.setText("Text color = blue");
      break;
    // пункты меню для tvSize
    case MENU_SIZE_22:
      tvSize.setTextSize(22);
      tvSize.setText("Text size = 22");
      break;
    case MENU_SIZE_26:
      tvSize.setTextSize(26);
      tvSize.setText("Text size = 26");
      break;
    case MENU_SIZE_30:
      tvSize.setTextSize(30);
      tvSize.setText("Text size = 30");
      break;
    }
    return super.onContextItemSelected(item);
  }
}
```