package theandroidguy.bart.firelink.Service;

import android.Manifest;
import android.annotation.TargetApi;
import android.media.session.MediaSession;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import theandroidguy.bart.firelink.Config.config;

/*
to handle the creation, rotation, and updating of registration tokens.
This is required for sending to specific devices or for creating device groups.
*/
public class MyFirebaseInstanceService extends FirebaseInstanceIdService{

    private static final String TAG = "MyFirebaseInstService";
    private final static String TOKENSTR = "token.txt";

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        FirebaseMessaging.getInstance().subscribeToTopic("all");
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        /* If you want to send messages to this application instance or manage this apps subscriptions on the server side, send the Instance ID token to your app server.*/

        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String refreshedToken) {
        Log.d("TOKEN ", refreshedToken);
        writeDownTOKEN(refreshedToken);
    }

    public void writeDownTOKEN(String TOKEN){
        try {
            OutputStreamWriter out = new OutputStreamWriter(openFileOutput(TOKENSTR, 0));
            out.write(TOKEN);
            config.readTOKEN = TOKEN;
            out.close();
        }
        catch (Throwable t) {
        }
    }

    public void push(){
        FirebaseMessaging fm = FirebaseMessaging.getInstance();
        fm.send(new RemoteMessage.Builder("926362946477" + "@gcm.googleapis.com")
                .setMessageId(Integer.toString(1))
                .addData("my_message", "Hello World")
                .addData("my_action","SAY_HELLO")
                .build());
    }

}