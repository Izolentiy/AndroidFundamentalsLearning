package org.izolentiy.notifyme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    private static final String ACTION_UPDATE_NOTIFICATION =
            "org.izolentiy.notifyme.ACTION_UPDATE_NOTIFICATION";
    private static final String ACTION_DELETE_NOTIFICATION =
            "org.izolentiy.notifyme.ACTION_DELETE_NOTIFICATION";
    private static final int NOTIFICATION_ID = 0;

    private NotificationManager mNotifyManager;
    private NotificationReceiver mReceiver;
    private Button notify_button;
    private Button update_button;
    private Button cancel_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mReceiver = new NotificationReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_UPDATE_NOTIFICATION);
        intentFilter.addAction(ACTION_DELETE_NOTIFICATION);
        registerReceiver(mReceiver, intentFilter);

        notify_button = findViewById(R.id.notify);
        notify_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNotification();
            }
        });
        update_button = findViewById(R.id.update);
        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateNotification();
            }
        });
        cancel_button = findViewById(R.id.cancel);
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelNotification();
            }
        });

        createNotificationChannel();
        setNotificationButtonState(true, false, false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    private NotificationCompat.Builder getNotificationBuilder() {
        // OnUpdate notification
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                NOTIFICATION_ID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        // OnDismiss notification
        Intent deleteIntent = new Intent(ACTION_DELETE_NOTIFICATION);
        PendingIntent deletePendingIntent = PendingIntent.getBroadcast(this,
                NOTIFICATION_ID, deleteIntent, PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder builder;
        builder = new NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID)
                .setContentTitle("You've been notified!")
                .setContentText("This is notification text from the hell")
                .setSmallIcon(R.drawable.ic_android)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setDeleteIntent(deletePendingIntent);
        return builder;
    }

    public void createNotificationChannel() {
        mNotifyManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
            NotificationChannel notificationChannel =
                    new NotificationChannel(PRIMARY_CHANNEL_ID, "Mascot notification",
                            NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Notification from the hell");

            mNotifyManager.createNotificationChannel(notificationChannel);
        }
    }

    public void sendNotification() {
        Intent updateIntent = new Intent(ACTION_UPDATE_NOTIFICATION);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,
                NOTIFICATION_ID, updateIntent, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder builder = getNotificationBuilder();
        builder.addAction(R.drawable.ic_update, "Update notification", pendingIntent);

        mNotifyManager.notify(NOTIFICATION_ID, builder.build());
        setNotificationButtonState(false, true, true);
    }

    public void updateNotification() {
        NotificationCompat.Builder builder = getNotificationBuilder();
        builder.setStyle(new NotificationCompat.InboxStyle()
                .addLine("First string")
                .addLine("Second string")
                .addLine("Third string")
                .setBigContentTitle("Notification updated"));
//        Bitmap androidImage = BitmapFactory.decodeResource(getResources(), R.drawable.mascot_1);
//        builder.setStyle(new NotificationCompat.BigPictureStyle()
//                .bigPicture(androidImage)
//                .setBigContentTitle("Notification updated"));
        mNotifyManager.notify(NOTIFICATION_ID, builder.build());
        setNotificationButtonState(false, false, true);
    }

    public void cancelNotification() {
        mNotifyManager.cancel(NOTIFICATION_ID);
        setNotificationButtonState(true, false, false);
    }

    void setNotificationButtonState(Boolean isNotifyEnabled,
                                    Boolean isUpdateEnabled,
                                    Boolean isCancelEnabled){
        notify_button.setEnabled(isNotifyEnabled);
        update_button.setEnabled(isUpdateEnabled);
        cancel_button.setEnabled(isCancelEnabled);
    }

    class NotificationReceiver extends BroadcastReceiver {
        public NotificationReceiver() {}

        @Override
        public void onReceive(Context context, Intent intent) {
            String intentAction = intent.getAction();
            switch(intentAction) {
                case ACTION_UPDATE_NOTIFICATION:
                    updateNotification();
                    break;
                case ACTION_DELETE_NOTIFICATION:
                    setNotificationButtonState(true,
                            false,
                            false);
                    break;
            }
        }
    }

}
