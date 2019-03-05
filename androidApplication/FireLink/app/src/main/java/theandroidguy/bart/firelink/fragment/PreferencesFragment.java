package theandroidguy.bart.firelink.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import theandroidguy.bart.firelink.QRScanActivity;
import theandroidguy.bart.firelink.R;

public class PreferencesFragment extends Fragment {

    Button register, showToken;

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

        register = rootView.findViewById(R.id.registerDevice);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //launch qr code scanner activity
                Intent myIntent = new Intent(getActivity(), QRScanActivity.class);
                PreferencesFragment.this.startActivity(myIntent);
            }
        });

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
                                //Hide key button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Device Key");
                builder.setMessage("iou3028ruj40f93f2-8uf4320-4f32n09fc43-c2").setPositiveButton("Generate New Key", dialogClickListener)
                        .setNegativeButton("Hide", dialogClickListener).show();
            }
        });

        return rootView;
    }
}
