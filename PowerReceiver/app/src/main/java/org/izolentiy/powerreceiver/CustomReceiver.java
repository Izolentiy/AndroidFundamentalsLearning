package org.izolentiy.powerreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class CustomReceiver extends BroadcastReceiver {
    public static final String ACTION_CUSTOM_BROADCAST =
            BuildConfig.APPLICATION_ID + ".ACTION_CUSTOM_BROADCAST";
    public boolean isFirstLaunch = true;

    @Override
    public void onReceive(Context context, Intent intent) {
        String intentAction = intent.getAction();
        if (intentAction != null) {
            String toastMessage = "Unknown intent action";
            switch (intentAction) {
                case Intent.ACTION_POWER_CONNECTED:
                    toastMessage = "Power connected!";
                    break;
                case Intent.ACTION_POWER_DISCONNECTED:
                    toastMessage = "Power disconnected";
                    break;
                case Intent.ACTION_HEADSET_PLUG:
                    if (intent.getIntExtra("state", 0) == 0){
                        toastMessage = "Headphones disconnected";
                    } else if (intent.getIntExtra("state", 0) == 1) {
                        toastMessage = "Headphones connected";
                    }
                    break;
            }
            if(isFirstLaunch) {
                isFirstLaunch = false;
            } else {
                Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
