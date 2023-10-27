# Программное создание экрана. LayoutParams

## _Рисуем экран программно, а не через layout-файл_

---

До этого мы создавали экран с помощью layout-файлов. Но то же самое мы можем делать и программно.

Открываем __MainActivity.java__ и обратим внимание на строку:

```Java
setContentView(R.layout.main);
```

Напомню, что в этой строке мы указываем, что __Activity__ в качестве экрана будет использовать __layout-файл activity_main.xml__. Есть другая реализация этого метода, которая на вход принимает не __layout - файл__, а __View - элемент__ и делает его корневым. В layout-файлах корневой элемент обычно LinearLayout, мы тоже используем его.

```Java
@Override
public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // создание LinearLayout
    LinearLayout linLayout = new LinearLayout(this);
    // установим вертикальную ориентацию
    linLayout.setOrientation(LinearLayout.VERTICAL);
    // создаем LayoutParams  
    LayoutParams linLayoutParam = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT); 
    // устанавливаем linLayout как корневой элемент экрана 
    setContentView(linLayout, linLayoutParam);
}
```

Обновим импорт – __CTRL+SHIFT+O__. __Eclipse__ предложит нам выбрать, какой именно __LayoutParams__ мы будем использовать. Тут надо остановиться подробнее. Вспомним теорию про экраны. Экран [состоит](http://developer.android.com/guide/topics/ui/index.html#ViewHierarchy) из __ViewGroup__ и вложенных в них View.

![ImageAlt](https://lh4.googleusercontent.com/-F91ssebAJlo/Tm-ZlYN7LzI/AAAAAAAAAUE/noad5YNMnds/s800/20110913_L0016_L_ViewGroups.png)

Известные нам примеры __ViewGroup__ – это __LinearLayout__, __TableLayout__, __RelativeLayout__ и т.д. Каждая из этих __ViewGroup__ [имеет вложенный класс __LayoutParams__](http://developer.android.com/guide/topics/ui/declaring-layout.html#layout-params). Базовым для этих __LayoutParams__ является [__ViewGroup.LayoutParams__](http://developer.android.com/reference/android/view/ViewGroup.LayoutParams.html).

__ViewGroup.LayoutParams__ имеет всего два атрибута: __height__ и __width__. Его подкласс [__ViewGroup.MarginLayoutParams__](http://developer.android.com/reference/android/view/ViewGroup.MarginLayoutParams.html) наследует два этих атрибута и имеет свои четыре: __bottomMargin__, __leftMargin__, __rightMargin__, __topMargin__. Класс [__LinearLayout.LayoutParams__](http://developer.android.com/reference/android/widget/LinearLayout.LayoutParams.html) в свою очередь является подклассом __ViewGroup.MarginLayoutParams__, наследует от него уже 6 аттрибутов и добавляет свои два: __gravity__ и __weight__.

Т.е. объект __LinearLayout__ имеет вложенный класс __LinearLayout.LayoutParams__ с __layout__ - аттрибутами. И эти аттрибуты распространяются на все дочерние __View__ и __ViewGroup__.

![ImageAlt](https://lh4.googleusercontent.com/-xz2bRjH39vw/Tm-ZkRBvNMI/AAAAAAAAATw/ht4jwihDyJE/s800/20110913_L0016_L_LayoutParams.png)

Т.е. View, находящаяся в __LinearLayout__ имеет один набор __layout__ - параметров:

![ImageAlt](https://lh4.googleusercontent.com/-ZPUWatnuYjY/Tm-ZkZZ7mQI/AAAAAAAAAT0/w9bZt3ar-MQ/s800/20110913_L0016_L_LinLayoutParams.jpg)

а __View__ из __RelativeLayout__ – другой:

![ImageAlt](https://lh5.googleusercontent.com/-t_UkqqNzvVs/Tm-Zkp1rrdI/AAAAAAAAAT4/3R1oFlO6ZyU/s800/20110913_L0016_L_RelLayoutParams.jpg)

Есть и общие элементы, т.к. родители у этих ViewGroup одни.

Вернемся в __Eclipse__, он ждет нашего выбора. Используем базовый класс __ViewGroup.LayoutParams__

![ImageAlt](https://lh3.googleusercontent.com/-n2e7EEZK-94/Tm-ZkUHe6kI/AAAAAAAAATs/iYryeP2PpLk/s800/20110913_L0016_L_LayoutParamsChoice.jpg)

Давайте разберем код. Мы создаем __LinearLayout__ и ставим вертикальную ориентацию. Далее создаем __LayoutParams__. Конструктор на вход принимает два параметра: __width__ и __height__. Мы оба ставим __MATCH_PARENT__. Далее вызывается метод [__setContentView__](http://developer.android.com/reference/android/app/Activity.html#setContentView(android.view.View,%20android.view.ViewGroup.LayoutParams)). На вход ему подается __LinearLayout__ и __LayoutParams__. Это означает, что корневым элементом __Activity__ будет __LinearLayout__ с __layout__ - свойствами из __LayoutParams__.

Если мы сейчас запустим приложение, то ничего не увидим, т.к. __LinearLayout__ – прозрачен. Давайте добавлять в __LinearLayout__ __View__ - компоненты.

```Java
LayoutParams lpView = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
 
TextView tv = new TextView(this);
tv.setText("TextView");
tv.setLayoutParams(lpView);
linLayout.addView(tv);
 
Button btn = new Button(this);
btn.setText("Button");
linLayout.addView(btn, lpView);
```

Мы снова создаем объект __LayoutParams__ с атрибутами __width__ = __wrap_content__ и __height__ = __wrap_content__. Теперь если мы присвоим этот объект какому-либо __View__, то это __View__ будет иметь ширину и высоту по содержимому.

Далее мы создаем __TextView__, настраиваем его текст, присваиваем ему выше созданный __LayoutParams__ и добавляем в __LinearLayout__ с помощью метода [__addView (View child)__](http://developer.android.com/reference/android/view/ViewGroup.html#addView(android.view.View)).

С __Button__ аналогично – создаем, правим текст, а затем используем другую реализацию метода [__addView(View child, ViewGroup.LayoutParams params)__](http://developer.android.com/reference/android/view/ViewGroup.html#addView(android.view.View,%20android.view.ViewGroup.LayoutParams)), которая одновременно добавляет __Button__ в __LinearLayout__ и присваивает для __Button__ указанный __LayoutParams__. Результат будет тот же, что и с __TextView__, но вместо двух строк кода получилась одна.

Обратите внимание, что для двух объектов __View__ я использовал один объект __LayoutParams__ - __lpView__. Оба __View__ - объекта считают параметры из __LayoutParams__ и используют их.

Сохраним и запустим приложение. Видим, что компоненты на экране появились. И видно, что их высота и ширина определена по содержимому __(wrap_content)__.

Объект __lpView__ имеет базовый тип __android.view.ViewGroup.LayoutParams__. А значит позволит настроить только ширину и высоту. Но для __View__ в __LinearLayout__ доступны, например, отступ слева или выравнивание по правому краю. И если мы хотим их задействовать, значит надо использовать __LinearLayout.LayoutParams__:

```Java
LinearLayout.LayoutParams leftMarginParams = new LinearLayout.LayoutParams(
        LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
leftMarginParams.leftMargin = 50;
 
Button btn1 = new Button(this);
btn1.setText("Button1");
linLayout.addView(btn1, leftMarginParams);
```

Смотрим код. Мы создаем объект типа __LinearLayout.LayoutParams__ с помощью такого же конструктора, как и для обычного __LayoutParams__, указывая __width__ и __height__. Затем мы указываем отступ слева = 50. Отступ здесь указывается в пикселах. Далее схема та же: создаем объект, настраиваем текст и добавляем его в __LinearLayout__ c присвоением __LayoutParams__.

Аналогично добавим компонент с выравниванием:

```Java
LinearLayout.LayoutParams rightGravityParams = new LinearLayout.LayoutParams(
        LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
rightGravityParams.gravity = Gravity.RIGHT;
 
Button btn2 = new Button(this);
btn2.setText("Button2");
linLayout.addView(btn2, rightGravityParams);
```

Сохраним и запустим. __Button1__ имеет отступ 50px. А __Button2__ выравнена по правому краю:

Полный код урока:

```Java
public class MainActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // создание LinearLayout
        LinearLayout linLayout = new LinearLayout(this);
        // установим вертикальную ориентацию
        linLayout.setOrientation(LinearLayout.VERTICAL);
        // создаем LayoutParams  
        LayoutParams linLayoutParam = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT); 
        // устанавливаем linLayout как корневой элемент экрана 
        setContentView(linLayout, linLayoutParam);
         
        LayoutParams lpView = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
         
        TextView tv = new TextView(this);
        tv.setText("TextView");
        tv.setLayoutParams(lpView);
        linLayout.addView(tv);
         
        Button btn = new Button(this);
        btn.setText("Button");
        linLayout.addView(btn, lpView);
 
        LinearLayout.LayoutParams leftMarginParams = new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        leftMarginParams.leftMargin = 50;
         
        Button btn1 = new Button(this);
        btn1.setText("Button1");
        linLayout.addView(btn1, leftMarginParams);
 
        LinearLayout.LayoutParams rightGravityParams = new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        rightGravityParams.gravity = Gravity.RIGHT;
         
        Button btn2 = new Button(this);
        btn2.setText("Button2");
        linLayout.addView(btn2, rightGravityParams);
    }
}
```

#### [Источник](https://startandroid.ru/ru/uroki/vse-uroki-spiskom/49-16-layoutparams.html)