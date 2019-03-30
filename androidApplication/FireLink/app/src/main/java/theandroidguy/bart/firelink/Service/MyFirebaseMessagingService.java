package theandroidguy.bart.firelink.Service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;
import java.util.Random;

import theandroidguy.bart.firelink.Config.config;
import theandroidguy.bart.firelink.MainActivity;
import theandroidguy.bart.firelink.NotificationReceiver;
import theandroidguy.bart.firelink.R;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    Bitmap largeIcon;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if(remoteMessage.getData() != null)
            sendNotification(remoteMessage);

    }

    private void sendNotification(RemoteMessage remoteMessage){
        Map<String, String> data = remoteMessage.getData();
        config.title = data.get("title");
        config.content = data.get("content");

        Uri defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(config.content));
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,0);

        Intent broadcastIntent = new Intent(this, NotificationReceiver.class);
        broadcastIntent.putExtra("titleText", config.title);
        broadcastIntent.putExtra("urlText", config.content);

        PendingIntent actionIntent = PendingIntent.getBroadcast(this, 0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID = "firelistdefault";

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            @SuppressLint("WrongConstant") NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "Notification", NotificationManager.IMPORTANCE_MAX);

            //Configure Notification Channel
            notificationChannel.setDescription("Shared Link Notification");
            notificationChannel.enableLights(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            notificationChannel.enableVibration(false);
            notificationChannel.setLightColor(Color.WHITE);

            notificationManager.createNotificationChannel(notificationChannel);
        }

        if(config.content.startsWith("https://") || config.content.startsWith("http://")){
            if(config.content.startsWith("https://www.google.com")){
                largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.google);
            }else if(config.content.startsWith("https://www.youtube.com")){
                largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.youtube);
            }else if(config.content.startsWith("https://github.com")){
                largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.github);
            }else if(config.content.startsWith("https://mail.yahoo.com")){
                largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.yahoo);
            }else if(config.content.startsWith("https://stackoverflow.com")){
                largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.stackoverflow);
            }else if(config.content.startsWith("https://twitter.com")){
                largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.twitter);
            }else{
                largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.defaultbrowse);
            }
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                    .setContentTitle(config.title)
                    .setSmallIcon(R.drawable.ic_launcher)
                    .setLargeIcon(largeIcon)
                    .setAutoCancel(true)
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText(config.content))
                    .setSound(defaultSound)
                    .setContentText(config.content)
                    .setContentIntent(pendingIntent)
                    .addAction(R.mipmap.ic_launcher, "Copy Link", actionIntent)
                    .setColor(Color.CYAN)
                    .setWhen(System.currentTimeMillis())
                    .setAutoCancel(true)
                    .setPriority(Notification.PRIORITY_DEFAULT);

            Random random = new Random();
            int m = random.nextInt(9999 - 1000) + 1000;

            notificationManager.notify(m, notificationBuilder.build());
        }
    }

    public void copylink(){

    }
}
