package theandroidguy.bart.firelink;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import theandroidguy.bart.firelink.Config.config;


public class MainActivity extends AppCompatActivity {

    Button reveal, sendNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        readToken();

        //define what items do
        reveal = findViewById(R.id.revealToken);
        reveal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Do not share this token:")
                        .setMessage(config.readTOKEN)
                        .setCancelable(false)
                        .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Whatever...
                            }
                        }).show();
            }
        });
        sendNotification = findViewById(R.id.sendBtn);
        sendNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendPost();
            }
        });
    }

    public void readToken(){
        try {
            InputStream in = openFileInput("token.txt");
            InputStreamReader tmp = new InputStreamReader(in);
            BufferedReader reader = new BufferedReader(tmp);
            String str;
            StringBuilder buf = new StringBuilder();
            while((str = reader.readLine()) != null){
                buf.append(str);
            }
            in.close();
            config.readTOKEN = buf.toString();
        } catch (java.io.FileNotFoundException e){

        }
        catch (Throwable t){

        }
    }

    public void sendPost() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("https://fcm.googleapis.com/fcm/send");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setRequestProperty("Authorization", "key=AAAA16-Iy60:APA91bHw6cYpz8coElaSpv_y4WeIlXq_BVeAJ65BJOm1nb2PVQV310BUN_Ng4mnMqftT7XbGTCGtwOrLSIhYQ1lhi7wAm24d5xOa1qYbRVQhX-JqxpODlL1GsHTzGMzcy01HMp__C3-v");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);

                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("title", "firelink");
                    jsonParam.put("to", "dV8UabEm9U0:APA91bHzT0Wyx2X8EhZWbzCl6z_4ukgw6q5pXzwhLJ2Yx1I1erAqGFPcyqR7VM3WvoPrsocegAoKW7yUdYWx-6I5seVd32F5AzVos9T8CiS1Siy5Xs9utfEdckWPOWIoTsq2xeetDAmH");

                    Log.i("JSON", jsonParam.toString());
                    DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                    //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
                    os.writeBytes(jsonParam.toString());

                    os.flush();
                    os.close();

                    Log.i("STATUS", String.valueOf(conn.getResponseCode()));
                    Log.i("MSG", conn.getResponseMessage());

                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
