package theandroidguy.bart.firelink;

import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import static android.content.Context.CLIPBOARD_SERVICE;

public class NotificationReceiver extends BroadcastReceiver {

    ClipData.Item item;

    @Override
    public void onReceive(Context context, Intent intent) {
        String siteUrl = intent.getStringExtra("urlText");

        if(siteUrl.startsWith("http://") || siteUrl.startsWith("https://")){
            ClipboardManager clipboard = context.getSystemService(CLIPBOARD_SERVICE);
            item = clipboard.getPrimaryClip().getItemAt(0);
            Toast.makeText(context, "Copied Link!", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "The link shared is not supported.", Toast.LENGTH_SHORT).show();
        }
    }
}
