# Создание простого меню

---

![ImageAlt](https://startandroid.ru/images/stories/lessons/L0013/001.gif.pagespeed.ce.U1mHth45WQ.gif)

Откроем __MainActivity.java__. За создание меню отвечает метод [onCreateOptionsMenu](http://developer.android.com/reference/android/app/Activity.html#onCreateOptionsMenu(android.view.Menu)). На вход ему подается объект типа __Menu__, в который мы и будем добавлять свои пункты.

Добавьте в __Activity__ этот метод:

```Java
public boolean onCreateOptionsMenu(Menu menu) {
  // TODO Auto-generated method stub
   
  menu.add("menu1");
  menu.add("menu2");
  menu.add("menu3");
  menu.add("menu4");
   
  return super.onCreateOptionsMenu(menu);
}
```

Пункты меню добавляются методом [add](http://developer.android.com/reference/android/view/Menu.html#add(java.lang.CharSequence)). На вход методу подается __текст__ пункта меню. Добавим 4 пункта.

Метод onCreateOptionsMenu должен вернуть результат типа __boolean__. __True__ – меню показывать, __False__ – не показывать. Т.е. можно было бы накодить проверку какого-либо условия, и по итогам этой проверки не показывать меню передавая False. Пока нам это не нужно, поэтому поручаем этот выбор методу суперкласса, по умолчанию он возвращает True.

Появилось 4 пункта меню. Нажатие на них ни к чему не приводит, т.к. не реализован обработчик. Обработчиком является Activity, а метод зовется [onOptionsItemSelected](http://developer.android.com/reference/android/app/Activity.html#onOptionsItemSelected(android.view.MenuItem)). На вход ему передается пункт меню, который был нажат – __MenuItem__. Определить, какое именно меню было нажато можно по методу [getTitle](http://developer.android.com/reference/android/view/MenuItem.html#getTitle()). Давайте выводить всплывающее сообщение с текстом нажатого пункта меню. На выходе метода надо возвращать __boolean__. И мы снова предоставляем это суперклассу.

```Java
public boolean onOptionsItemSelected(MenuItem item) {
  // TODO Auto-generated method stub
  Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
  return super.onOptionsItemSelected(item);
}
```

Полный код:

```Java
public class MainActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
     
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
      // TODO Auto-generated method stub
       
      menu.add("menu1");
      menu.add("menu2");
      menu.add("menu3");
      menu.add("menu4");
       
      return super.onCreateOptionsMenu(menu);
    }
     
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
      // TODO Auto-generated method stub
      Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
      return super.onOptionsItemSelected(item);
    }
     
}
```

Определять нажатый пункт меню по тексту – это не самый лучший вариант. Далее будем делать это по __ID__. Но для этого надо немного по другому создавать меню.