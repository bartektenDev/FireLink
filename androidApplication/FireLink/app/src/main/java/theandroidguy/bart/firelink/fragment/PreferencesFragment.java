package theandroidguy.bart.firelink.fragment;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import theandroidguy.bart.firelink.QRScanActivity;
import theandroidguy.bart.firelink.R;

public class PreferencesFragment extends Fragment {

    String secret;
    Button showToken;

    public PreferencesFragment() {
        // Required empty public constructor
    }

    public static PreferencesFragment newInstance(String param1, String param2) {
        PreferencesFragment fragment = new PreferencesFragment();
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
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_pref, container, false);

        secret = getArguments().getString("deviceKey");

        showToken = rootView.findViewById(R.id.showToke);

        showToken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                //Get new key button clicked
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                //Copy
                                    ClipboardManager clipMan = (ClipboardManager) getActivity().getSystemService(getActivity().getApplicationContext().CLIPBOARD_SERVICE);
                                    ClipData clip = ClipData.newPlainText("key", secret);
                                    clipMan.setPrimaryClip(clip);
                                    break;

                                case DialogInterface.BUTTON_NEUTRAL:
                                    //Hide
                                    break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Device Key");
                builder.setMessage(secret).setPositiveButton("Generate New Key", dialogClickListener)
                        .setNeutralButton("Hide", dialogClickListener)
                        .setNegativeButton("Copy", dialogClickListener).show();
            }
        });

        return rootView;
    }
}
