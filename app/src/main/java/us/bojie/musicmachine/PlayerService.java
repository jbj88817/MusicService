package us.bojie.musicmachine;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.Messenger;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by bojiejiang on 7/21/16.
 */
public class PlayerService extends Service {

    private static final String TAG = PlayerService.class.getSimpleName();
    private MediaPlayer mPlayer;
    private Messenger mMessenger = new Messenger(new PlayerHandler(this));

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate");
        mPlayer = MediaPlayer.create(this, R.raw.jingle);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                stopSelf();
            }
        });
        return Service.START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        return mMessenger.getBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        mPlayer.release();
    }


    // Client Methods
    public boolean isPlaying() {
        return mPlayer.isPlaying();
    }
    public void play() {
        mPlayer.start();
    }

    public void pause() {
        mPlayer.pause();
    }
}
