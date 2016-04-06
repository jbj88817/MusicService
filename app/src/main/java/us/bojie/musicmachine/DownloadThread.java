package us.bojie.musicmachine;

import android.os.Looper;

/**
 * Created by bjiang on 4/2/16.
 */
public class DownloadThread extends Thread {
    private static final String TAG = DownloadThread.class.getSimpleName();

    public DownloadHandler mHandler;

    @Override
    public void run() {

        Looper.prepare();
        mHandler = new DownloadHandler();
        Looper.loop();

    }

}
