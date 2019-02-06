package theandroidguy.bart.firelink;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Random;

import theandroidguy.bart.firelink.Config.config;

public class MainActivity extends AppCompatActivity {

    private static final Random rand = new Random();
    private final static String PINSTR = "pin.txt";
    TextView globalPIN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        globalPIN = findViewById(R.id.pinText);

        Button savePIN = findViewById(R.id.applybtn);
        savePIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sharePIN = globalPIN.getText().toString();
                //writeDownPIN(sharePIN);
            }
        });
    }

    public void writeDownPIN(String PIN){
        try {
            OutputStreamWriter out = new OutputStreamWriter(openFileOutput(PINSTR, 0));
            out.write(PIN);
            out.close();
            FirebaseMessaging.getInstance().subscribeToTopic(PIN);
            Toast.makeText(this, "Ready to recieve link!", Toast.LENGTH_LONG).show();
            readPINNow();
        }

        catch (Throwable t) {
            Toast.makeText(getApplicationContext(), "Exception: "+t.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void readPINNow(){
        try {
            InputStream in = openFileInput(PINSTR);
            if (in != null) {
                InputStreamReader tmp=new InputStreamReader(in);
                BufferedReader reader=new BufferedReader(tmp);
                String str;
                StringBuilder buf=new StringBuilder();
                while ((str = reader.readLine()) != null) {
                    buf.append(str);
                }
                in.close();
                config.readPIN = buf.toString();
                Toast.makeText(getApplicationContext(), config.readPIN, Toast.LENGTH_LONG).show();
            }
        }
        catch (java.io.FileNotFoundException e) {

        }
        catch (Throwable t) {
            Toast.makeText(this, "Exception: "+t.toString(), Toast.LENGTH_LONG).show();
        }
    }
}

