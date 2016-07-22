package us.bojie.musicmachine;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by bojiejiang on 7/21/16.
 */
public class PlayerService extends Service {

    private MediaPlayer mPlayer;

    @Override
    public void onCreate() {
        mPlayer = MediaPlayer.create(this, R.raw.jingle);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
