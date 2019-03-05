package theandroidguy.bart.firelink.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import theandroidguy.bart.firelink.R;

public class RecievedFragment extends Fragment {

    private static final String TAG = RecievedFragment.class.getSimpleName();
    //saved devices json
    private final static String DEVICES = "devices.txt";

    public RecievedFragment() {
        // Required empty public constructor
    }

    public static RecievedFragment newInstance(String param1, String param2) {
        RecievedFragment fragment = new RecievedFragment();
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
        View view = inflater.inflate(R.layout.fragment_recieved, container, false);


        return view;
    }

}
