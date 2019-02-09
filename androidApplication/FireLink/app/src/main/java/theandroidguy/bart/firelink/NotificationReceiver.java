package theandroidguy.bart.firelink;

import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String siteTitle = intent.getStringExtra("titleText");
        String siteUrl = intent.getStringExtra("urlText");

        if(siteUrl.startsWith("http://") || siteUrl.startsWith("https://")){
            Intent browserIntent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(siteUrl));
            browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(browserIntent);
        }else{
            Toast.makeText(context, "The link shared is not supported.", Toast.LENGTH_SHORT).show();
        }
    }
}
