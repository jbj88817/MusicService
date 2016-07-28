package us.bojie.musicmachine;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by bjiang on 7/27/16.
 */
public class NetworkConnectionReceiver extends BroadcastReceiver {
    private static final String TAG = NetworkConnectionReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, intent.getAction());
    }
}
