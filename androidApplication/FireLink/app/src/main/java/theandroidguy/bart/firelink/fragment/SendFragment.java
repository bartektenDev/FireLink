package theandroidguy.bart.firelink.fragment;


import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import theandroidguy.bart.firelink.QRScanActivity;
import theandroidguy.bart.firelink.R;


public class SendFragment extends Fragment {

    ListView listView;
    private final static String devicesFile1 = "devicesData1.txt";
    private final static String devicesFile2 = "devicesData2.txt";
    private final static String devicesFile3 = "devicesData3.txt";
    String devName, devKey, devIcon;
    TextView card1devname, card2devname, card3devname, card1os, card2os, card3os;
    ImageView os1, os2, os3;
    CardView card1, card2, card3;

    public SendFragment() {
        // Required empty public constructor
    }

    public static SendFragment newInstance(String param1, String param2) {
        SendFragment fragment = new SendFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_send, container, false);

        listView = rootView.findViewById(R.id.listview);

        os1 = rootView.findViewById(R.id.card1image);
        os2 = rootView.findViewById(R.id.card2image);
        os3 = rootView.findViewById(R.id.card3image);
        card1devname = rootView.findViewById(R.id.card1devicename);
        card2devname = rootView.findViewById(R.id.card2devicename);
        card3devname = rootView.findViewById(R.id.card3devicename);
        card1os = rootView.findViewById(R.id.card1os);
        card2os = rootView.findViewById(R.id.card2os);
        card3os = rootView.findViewById(R.id.card3os);

        os1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getActivity(), QRScanActivity.class);
                myIntent.putExtra("whichpos", "firstpos");
                SendFragment.this.startActivity(myIntent);
            }
        });

        os2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getActivity(), QRScanActivity.class);
                myIntent.putExtra("whichpos", "secondpos");
                SendFragment.this.startActivity(myIntent);
            }
        });

        os3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getActivity(), QRScanActivity.class);
                myIntent.putExtra("whichpos", "thirdpos");
                SendFragment.this.startActivity(myIntent);
            }
        });

        os1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                resetFirstDevice();
                return true;
            }
        });

        os2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                resetSecondDevice();
                return true;
            }
        });

        os3.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                resetThirdDevice();
                return true;
            }
        });

        card1 = rootView.findViewById(R.id.cardview1);
        card2 = rootView.findViewById(R.id.cardview2);
        card3 = rootView.findViewById(R.id.cardview3);

        checkDevicesFile();
        // Inflate the layout for this fragment
        return rootView;
    }

    public void resetFirstDevice(){
        try {
            OutputStreamWriter out = new OutputStreamWriter(getActivity().openFileOutput(devicesFile1, 0));
            //default site
            out.write("<devicename></devicename>\n" +
                    "<devicetoken></devicetoken>\n" +
                    "<deviceimg></deviceimg>");
            out.close();
        }
        catch (Throwable t) {
            Toast.makeText(getActivity().getApplicationContext(), "Failed to save device!", Toast.LENGTH_LONG).show();
        }
        checkDevicesFile();
        card1.setBackground(null);
    }

    public void resetSecondDevice(){
        try {
            OutputStreamWriter out = new OutputStreamWriter(getActivity().openFileOutput(devicesFile2, 0));
            //default site
            out.write("<devicename></devicename>\n" +
                    "<devicetoken></devicetoken>\n" +
                    "<deviceimg></deviceimg>");
            out.close();
        }
        catch (Throwable t) {
            Toast.makeText(getActivity().getApplicationContext(), "Failed to save device!", Toast.LENGTH_LONG).show();
        }
        checkDevicesFile();
        card2.setBackground(null);
    }

    public void resetThirdDevice(){
        try {
            OutputStreamWriter out = new OutputStreamWriter(getActivity().openFileOutput(devicesFile3, 0));
            //default site
            out.write("<devicename></devicename>\n" +
                    "<devicetoken></devicetoken>\n" +
                    "<deviceimg></deviceimg>");
            out.close();
        }
        catch (Throwable t) {
            Toast.makeText(getActivity().getApplicationContext(), "Failed to save device!", Toast.LENGTH_LONG).show();
        }
        checkDevicesFile();
        card3.setBackground(null);
    }

    public void checkDevicesFile(){
        File file = new File(getActivity().getApplicationContext().getFilesDir() + "/" + devicesFile1);
        if(file.exists()){
            //read file
            try {
                InputStream in = new FileInputStream(getActivity().getApplicationContext().getFilesDir() + "/" + devicesFile1);
                if (in != null) {
                    InputStreamReader tmp=new InputStreamReader(in);
                    BufferedReader reader=new BufferedReader(tmp);
                    String str;
                    StringBuilder buf=new StringBuilder();
                    while ((str = reader.readLine()) != null) {
                        buf.append(str);
                    }
                    in.close();
                    //read the deviceData.txt file to see what we can display the list like

                    //set the global string the device data contents
                    String data = buf.toString();
                    //find the amount of devices in the
                    Matcher deviceNameFound = Pattern.compile(
                            Pattern.quote("<devicename>")
                                    + "(.*?)" + Pattern.quote("</devicename>")).matcher(data);

                    while(deviceNameFound.find()){
                        devName = deviceNameFound.group(1);
                        card1devname.setText(devName);
                    }

                    Matcher deviceKeyFound = Pattern.compile(
                            Pattern.quote("<devicetoken>")
                                    + "(.*?)" + Pattern.quote("</devicetoken>")).matcher(data);

                    while(deviceKeyFound.find()){
                        devKey = deviceKeyFound.group(1);

                    }

                    Matcher deviceIconFound = Pattern.compile(
                            Pattern.quote("<deviceimg>")
                                    + "(.*?)" + Pattern.quote("</deviceimg>")).matcher(data);

                    while(deviceIconFound.find()){
                        devIcon = deviceIconFound.group(1);
                        if(devIcon.equals("windowsicon")){
                            card1os.setText("Windows");
                            os1.setImageResource(R.drawable.windowsicon);
                            card1.setBackgroundResource(R.drawable.windowsbg);
                        }
                        else if(devIcon.equals("macicon")){
                            card1os.setText("Mac");
                            os1.setImageResource(R.drawable.macicon);
                            card1.setBackgroundResource(R.drawable.macbg);
                        }
                        else if(devIcon.equals("linuxicon")){
                            card1os.setText("Linux");
                            os1.setImageResource(R.drawable.linuxicon);
                            card1.setBackgroundResource(R.drawable.linuxbg);
                        }
                        else if(devIcon.equals("androidicon")){
                            card1os.setText("Android");
                            os1.setImageResource(R.drawable.androidicon);
                            card1.setBackgroundResource(R.drawable.androidbg);
                        }
                        else if(devIcon.equals("othericon")){
                            card1os.setText("Other");
                            os1.setImageResource(R.drawable.othericon);
                        }else{
                            card1os.setText("Empty");
                            os1.setImageResource(R.drawable.add);
                        }
                    }

                }
            }
            catch (java.io.FileNotFoundException e) {
// that's OK, we probably haven't created it yet
            }
            catch (Throwable t) {
                Toast.makeText(getActivity().getApplicationContext(), "Exception: "+t.toString(), Toast.LENGTH_LONG).show();
            }
        }else if(file.exists() == false){
            Toast.makeText(getActivity().getApplicationContext(), "File doesnt exists!", Toast.LENGTH_SHORT).show();
            //file doesnt exist so lets make it and that means we are adding our first device so devices = 1
        }

        File file2 = new File(getActivity().getApplicationContext().getFilesDir() + "/" + devicesFile2);
        if(file2.exists()){
            //read file
            try {
                InputStream in = new FileInputStream(getActivity().getApplicationContext().getFilesDir() + "/" + devicesFile2);
                if (in != null) {
                    InputStreamReader tmp=new InputStreamReader(in);
                    BufferedReader reader=new BufferedReader(tmp);
                    String str;
                    StringBuilder buf=new StringBuilder();
                    while ((str = reader.readLine()) != null) {
                        buf.append(str);
                    }
                    in.close();
                    //read the deviceData.txt file to see what we can display the list like

                    //set the global string the device data contents
                    String data = buf.toString();
                    //find the amount of devices in the
                    Matcher deviceNameFound = Pattern.compile(
                            Pattern.quote("<devicename>")
                                    + "(.*?)" + Pattern.quote("</devicename>")).matcher(data);

                    while(deviceNameFound.find()){
                        devName = deviceNameFound.group(1);
                        card2devname.setText(devName);
                    }

                    Matcher deviceKeyFound = Pattern.compile(
                            Pattern.quote("<devicetoken>")
                                    + "(.*?)" + Pattern.quote("</devicetoken>")).matcher(data);

                    while(deviceKeyFound.find()){
                        devKey = deviceKeyFound.group(1);

                    }

                    Matcher deviceIconFound = Pattern.compile(
                            Pattern.quote("<deviceimg>")
                                    + "(.*?)" + Pattern.quote("</deviceimg>")).matcher(data);

                    while(deviceIconFound.find()){
                        devIcon = deviceIconFound.group(1);
                        if(devIcon.equals("windowsicon")){
                            card2os.setText("Windows");
                            os2.setImageResource(R.drawable.windowsicon);
                            card2.setBackgroundResource(R.drawable.windowsbg);
                        }
                        else if(devIcon.equals("macicon")){
                            card2os.setText("Mac");
                            os2.setImageResource(R.drawable.macicon);
                            card2.setBackgroundResource(R.drawable.macbg);
                        }
                        else if(devIcon.equals("linuxicon")){
                            card2os.setText("Linux");
                            os2.setImageResource(R.drawable.linuxicon);
                            card2.setBackgroundResource(R.drawable.linuxbg);
                        }
                        else if(devIcon.equals("androidicon")){
                            card2os.setText("Android");
                            os2.setImageResource(R.drawable.androidicon);
                            card2.setBackgroundResource(R.drawable.androidbg);
                        }
                        else if(devIcon.equals("othericon")){
                            card2os.setText("Other");
                            os2.setImageResource(R.drawable.othericon);
                        }else{
                            card2os.setText("Empty");
                            os2.setImageResource(R.drawable.add);
                        }
                    }

                }
            }
            catch (java.io.FileNotFoundException e) {
// that's OK, we probably haven't created it yet
            }
            catch (Throwable t) {
                Toast.makeText(getActivity().getApplicationContext(), "Exception: "+t.toString(), Toast.LENGTH_LONG).show();
            }
        }else if(file2.exists() == false){
            Toast.makeText(getActivity().getApplicationContext(), "File doesnt exists!", Toast.LENGTH_SHORT).show();
            //file doesnt exist so lets make it and that means we are adding our first device so devices = 1
        }
        File file3 = new File(getActivity().getApplicationContext().getFilesDir() + "/" + devicesFile3);
        if(file3.exists()){
            //read file
            try {
                InputStream in = new FileInputStream(getActivity().getApplicationContext().getFilesDir() + "/" + devicesFile3);
                if (in != null) {
                    InputStreamReader tmp=new InputStreamReader(in);
                    BufferedReader reader=new BufferedReader(tmp);
                    String str;
                    StringBuilder buf=new StringBuilder();
                    while ((str = reader.readLine()) != null) {
                        buf.append(str);
                    }
                    in.close();
                    //read the deviceData.txt file to see what we can display the list like

                    //set the global string the device data contents
                    String data = buf.toString();
                    //find the amount of devices in the
                    Matcher deviceNameFound = Pattern.compile(
                            Pattern.quote("<devicename>")
                                    + "(.*?)" + Pattern.quote("</devicename>")).matcher(data);

                    while(deviceNameFound.find()){
                        devName = deviceNameFound.group(1);
                        card3devname.setText(devName);
                    }

                    Matcher deviceKeyFound = Pattern.compile(
                            Pattern.quote("<devicetoken>")
                                    + "(.*?)" + Pattern.quote("</devicetoken>")).matcher(data);

                    while(deviceKeyFound.find()){
                        devKey = deviceKeyFound.group(1);

                    }

                    Matcher deviceIconFound = Pattern.compile(
                            Pattern.quote("<deviceimg>")
                                    + "(.*?)" + Pattern.quote("</deviceimg>")).matcher(data);

                    while(deviceIconFound.find()){
                        devIcon = deviceIconFound.group(1);
                        if(devIcon.equals("windowsicon")){
                            card3os.setText("Windows");
                            os3.setImageResource(R.drawable.windowsicon);
                            card3.setBackgroundResource(R.drawable.windowsbg);
                        }
                        else if(devIcon.equals("macicon")){
                            card3os.setText("Mac");
                            os3.setImageResource(R.drawable.macicon);
                            card3.setBackgroundResource(R.drawable.macbg);
                        }
                        else if(devIcon.equals("linuxicon")){
                            card3os.setText("Linux");
                            os3.setImageResource(R.drawable.linuxicon);
                            card3.setBackgroundResource(R.drawable.linuxbg);
                        }
                        else if(devIcon.equals("androidicon")){
                            card3os.setText("Android");
                            os3.setImageResource(R.drawable.androidicon);
                            card3.setBackgroundResource(R.drawable.androidbg);
                        }
                        else if(devIcon.equals("othericon")){
                            card3os.setText("Other");
                            os3.setImageResource(R.drawable.othericon);
                        }else{
                            card3os.setText("Empty");
                            os3.setImageResource(R.drawable.add);
                        }
                    }

                }
            }
            catch (java.io.FileNotFoundException e) {
// that's OK, we probably haven't created it yet
            }
            catch (Throwable t) {
                Toast.makeText(getActivity().getApplicationContext(), "Exception: "+t.toString(), Toast.LENGTH_LONG).show();
            }
        }else if(file3.exists() == false){
            Toast.makeText(getActivity().getApplicationContext(), "File doesnt exists!", Toast.LENGTH_SHORT).show();
            //file doesnt exist so lets make it and that means we are adding our first device so devices = 1
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        checkDevicesFile();
    }

}
