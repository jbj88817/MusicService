package us.bojie.musicmachine;

import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;

/**
 * Created by bjiang on 7/22/16.
 */
public class PlayerHandler extends Handler {

    private PlayerService mPlayerService;

    public PlayerHandler(PlayerService playerService) {
        mPlayerService = playerService;
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.arg1) {
            case 0: //play
                mPlayerService.play();
                break;
            case 1: // pause
                mPlayerService.pause();
                break;
            case 2: // isPlaying
                int isPlaying = mPlayerService.isPlaying() ? 1 : 0;
                Message message = Message.obtain();
                message.arg1 = isPlaying;
                try {
                    msg.replyTo.send(message);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}