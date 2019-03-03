package theandroidguy.bart.firelink;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import theandroidguy.bart.firelink.Config.config;


public class MainActivity extends AppCompatActivity {

    Button reveal, sendNotification;
    final String[] Options = {"Delete Key", "Hide"};
    AlertDialog.Builder window;
    private ActionBar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = getSupportActionBar();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // attaching bottom sheet behaviour - hide / show on scroll
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) navigation.getLayoutParams();
        layoutParams.setBehavior(new info.androidhive.bottomnavigation.helper.BottomNavigationBehavior());

        // load the store fragment by default
        toolbar.setTitle("Shop");
        loadFragment(new theandroidguy.bart.firelink.fragment.StoreFragment());

        readToken();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_recieved:
                    toolbar.setTitle("Recieved");
                    fragment = new theandroidguy.bart.firelink.fragment.StoreFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_send:
                    toolbar.setTitle("Send");
                    fragment = new theandroidguy.bart.firelink.fragment.GiftsFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_pref:
                    toolbar.setTitle("Preferences");
                    fragment = new theandroidguy.bart.firelink.fragment.CartFragment();
                    loadFragment(fragment);
                    return true;
            }

            return false;
        }
    };

    /**
     * loading fragment into FrameLayout
     *
     * @param fragment
     */
    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id==R.id.scannerActivityItem1){
            //launch qr code scanner activity
            Intent myIntent = new Intent(MainActivity.this, QRScanActivity.class);
            MainActivity.this.startActivity(myIntent);
        }
        if(id==R.id.scannerActivityItem2){
            //show token
            window = new AlertDialog.Builder(this);
            window.setTitle("Pick a color");
            window.setItems(Options, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(which == 0){

                    }else if(which == 1){

                    }else{
                        Toast.makeText(getApplicationContext(), "Hmmm I messed up. I detected that you clicked on : " + which + "?", Toast.LENGTH_LONG).show();
                    }
                }
            });

            window.show();
        }
        return super.onOptionsItemSelected(item);
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
            Toast.makeText(getApplicationContext(), "Found Existing Token", Toast.LENGTH_LONG).show();
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
                    conn.setRequestProperty("Authorization", "key=");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);

                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("title", "firelink");
                    jsonParam.put("to", "");

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
