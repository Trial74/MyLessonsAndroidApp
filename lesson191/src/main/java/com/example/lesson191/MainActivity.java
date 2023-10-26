package com.example.lesson191;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
    TextView contentView;
    Button btnFetch;
    EditText passUs;
    JSONObject jObject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contentView = findViewById(R.id.content);
        btnFetch = findViewById(R.id.downloadBtn);
        passUs = findViewById(R.id.passUs);
        btnFetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contentView.setText("Загрузка...");
                new Thread(new Runnable() {
                    public void run() {
                        try{
                            String content = getContent("http://update.extreme-look.ru/api/fin/");

                            /** В файле обработчике массив кодированный в JSON
                             *
                             * $arr = array(
                             *     'Первый ключ' => 'Первое значение',
                             *     'Второй ключ' => 'Второе значение',
                             *     'Третий ключ' => array(
                             *         1 => 123,
                             *         2 => 321
                             *     ),
                             *     'Четвёртый ключ' => 3213123,
                             *     6 => function(){},
                             *     7 => null
                             * );
                             *
                             * echo json_encode($arr);
                             *
                             */

                            jObject = new JSONObject(content);
                            StringBuilder arrRes = new StringBuilder();

                            Iterator<String> temp = jObject.keys();
                            while (temp.hasNext()) {
                                String key = temp.next();
                                Object value = jObject.get(key);

                                arrRes.append("Ключ => ").append(key).append(" Значение => ").append(value).append("\n");
                            }

                            contentView.post(new Runnable() {
                                public void run() {
                                    contentView.setText(arrRes);
                                }
                            });
                        }
                        catch (IOException ex){
                            contentView.post(new Runnable() {
                                public void run() {
                                    contentView.setText("Ошибка: " + ex.getMessage());
                                    Toast.makeText(getApplicationContext(), "Ошибка", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                }).start();
            }
        });
    }

    private String getContent(String path) throws IOException {
        BufferedReader reader = null;
        InputStream stream = null;
        HttpsURLConnection connectionHTTPS = null;
        HttpURLConnection connectionHTTP = null;
        try {
            URL url = new URL(path);
            connectionHTTP = (HttpURLConnection)url.openConnection();
            connectionHTTP.setRequestMethod("GET");
            connectionHTTP.setReadTimeout(10000);
            connectionHTTP.connect();
            stream = connectionHTTP.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder buf=new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                buf.append(line).append("\n");
            }

            return(buf.toString());
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (stream != null) {
                stream.close();
            }
            if (connectionHTTP != null) {
                connectionHTTP.disconnect();
            }
        }
    }
}