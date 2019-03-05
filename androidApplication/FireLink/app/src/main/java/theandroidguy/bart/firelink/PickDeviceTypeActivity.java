package theandroidguy.bart.firelink;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PickDeviceTypeActivity extends AppCompatActivity {

    private final static String devicesFile1 = "devicesData1.txt";
    private final static String devicesFile2 = "devicesData2.txt";
    private final static String devicesFile3 = "devicesData3.txt";
    ListView listView;
    String[] deviceType = {"Windows", "Mac", "Linux", "Android", "Other"};
    Integer[] deviceimg = {R.drawable.windowsicon, R.drawable.macicon, R.drawable.linuxicon,R.drawable.androidicon,R.drawable.othericon};
    String selectedDevice, deviceName, deviceKey, devicesDataFileContents;
    Integer MaxDevices, posi;
    String positioning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_device_type);

        MaxDevices = 5;
        deviceKey = getIntent().getStringExtra("deviceScannedKey");
        positioning = getIntent().getStringExtra("positioning");
        if(positioning.equals("firstpos")){
            posi = 1;
        }else if(positioning.equals("secondpos")){
            posi = 2;
        }else if(positioning.equals("thirdpos")){
            posi = 3;
        }

        listView = findViewById(R.id.listview);
        CustomListview customListview = new CustomListview(PickDeviceTypeActivity.this, deviceType, deviceimg);
        listView.setAdapter(customListview);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {

                if(position == 0){
                    selectedDevice = "windowsicon";
                }else if(position == 1){
                    selectedDevice = "macicon";
                }else if(position == 2){
                    selectedDevice = "linuxicon";
                }else if(position == 3){
                    selectedDevice = "androidicon";
                }else if(position == 4){
                    selectedDevice = "othericon";
                }else{

                }

                AlertDialog.Builder builder = new AlertDialog.Builder(PickDeviceTypeActivity.this);
                builder.setTitle("Device Name");

                final EditText input = new EditText(PickDeviceTypeActivity.this);

                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deviceName = input.getText().toString();
                        if(posi == 1){
                            firstDevice();
                        }else if(posi == 2){
                            secondDevice();
                        }else if(posi == 3){
                            thirdDevice();
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });
    }

    public void firstDevice(){
        try {
            OutputStreamWriter out = new OutputStreamWriter(openFileOutput(devicesFile1, 0));
            //default site
            out.write("<devicename>" + deviceName + "</devicename>\n" +
                    "<devicetoken>" + deviceKey + "</devicetoken>\n" +
                    "<deviceimg>" + selectedDevice + "</deviceimg>");
            out.close();
            Toast.makeText(getApplicationContext(), "Saved Successfully  Device!", Toast.LENGTH_LONG).show();
        }
        catch (Throwable t) {
            Toast.makeText(getApplicationContext(), "Failed to save device!", Toast.LENGTH_LONG).show();
        }
        finish();
    }

    public void secondDevice(){
        try {
            OutputStreamWriter out = new OutputStreamWriter(openFileOutput(devicesFile2, 0));
            //default site
            out.write("<devicename>" + deviceName + "</devicename>\n" +
                    "<devicetoken>" + deviceKey + "</devicetoken>\n" +
                    "<deviceimg>" + selectedDevice + "</deviceimg>");
            out.close();
            Toast.makeText(getApplicationContext(), "Saved Successfully  Device!", Toast.LENGTH_LONG).show();
        }
        catch (Throwable t) {
            Toast.makeText(getApplicationContext(), "Failed to save device!", Toast.LENGTH_LONG).show();
        }
        finish();
    }


    public void thirdDevice(){
        try {
            OutputStreamWriter out = new OutputStreamWriter(openFileOutput(devicesFile3, 0));
            //default site
            out.write("<devicename>" + deviceName + "</devicename>\n" +
                    "<devicetoken>" + deviceKey + "</devicetoken>\n" +
                    "<deviceimg>" + selectedDevice + "</deviceimg>");
            out.close();
            Toast.makeText(getApplicationContext(), "Saved Successfully  Device!", Toast.LENGTH_LONG).show();
        }
        catch (Throwable t) {
            Toast.makeText(getApplicationContext(), "Failed to save device!", Toast.LENGTH_LONG).show();
        }
        finish();
    }

}
