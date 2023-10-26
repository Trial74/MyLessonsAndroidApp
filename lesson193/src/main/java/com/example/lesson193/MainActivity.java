package com.example.lesson193;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.math.BigInteger;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements OnClickListener{

    EditText cryptString;
    EditText stringSalt;
    TextView outputString;
    Button encrypt;
    String resultStr = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cryptString = (EditText) findViewById(R.id.cryptString);
        stringSalt = (EditText) findViewById(R.id.stringSalt);
        outputString = (TextView) findViewById(R.id.outputString);
        encrypt = (Button) findViewById(R.id.encrypt);

        encrypt.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.encrypt){
            if(cryptString.getText().toString().isEmpty()) {
                Toast.makeText(this, R.string.toastErrString, Toast.LENGTH_LONG).show();
                return;
            }
            if(stringSalt.getText().toString().isEmpty()){
                Toast.makeText(this, R.string.toastErrAdditional, Toast.LENGTH_LONG).show();
                return;
            }

            try {
                resultStr = getHmac(cryptString.getText().toString(), stringSalt.getText().toString());
                outputString.setText(resultStr);
            } catch (Exception e) {
                outputString.setText(R.string.errorThrow);
                throw new RuntimeException(e);
            }
        }
    }

    public static String getHmac(String entity, String salt) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(salt.getBytes(), "HmacSHA1"));
        byte[] bs = mac.doFinal(entity.getBytes());
        return String.format("%0" + (bs.length * 2) + 'x', new BigInteger(1, bs));
    }
}