package us.bojie.musicmachine;

import android.os.Handler;
import android.os.Message;

/**
 * Created by bojiejiang on 4/5/16.
 */
public class DownloadHandler extends Handler {

    private static final String TAG = DownloadHandler.class.getSimpleName();
    private DownloadService mService;

    @Override
    public void handleMessage(Message msg) {
        String message = msg.obj.toString();
        mService.stopSelf(msg.arg1);
    }

    public void setService(DownloadService service) {
        mService = service;
    }
}
