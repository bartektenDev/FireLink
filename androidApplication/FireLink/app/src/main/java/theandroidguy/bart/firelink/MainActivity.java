package theandroidguy.bart.firelink;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import theandroidguy.bart.firelink.Config.config;


public class MainActivity extends AppCompatActivity {

    Button reveal;

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

}
