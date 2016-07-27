package us.bojie.musicmachine;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import us.bojie.musicmachine.adapters.PlaylistAdapter;
import us.bojie.musicmachine.models.Song;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String KEY_SONG = "song";
    public static final String SONG_TITLE = "SONG_TITLE";
    private static final int REQUEST_FAVORITE = 0;
    public static final String EXTRA_FAVORITE = "EXTRA_FAVORITE";
    private boolean mBound = false;
    private Button mDownloadButton;
    private Button mPlayButton;
    private Messenger mServiceMessenger;
    private Messenger mActivityMessenger = new Messenger(new ActivityHandler(this));
    private PlaylistAdapter mAdapter;
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBound = true;
            mServiceMessenger = new Messenger(service);
            Message message = Message.obtain();
            message.arg1 = 2;
            message.arg2 = 1;
            message.replyTo = mActivityMessenger;
            try {
                mServiceMessenger.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBound = false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDownloadButton = (Button) findViewById(R.id.downloadButton);
        mPlayButton = (Button) findViewById(R.id.playButton);

        mDownloadButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //downloadSongs();
                testIntents();
            }
        });

        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBound) {
                    Intent intent = new Intent(MainActivity.this, PlayerService.class);
                    startService(intent);
                    Message message = Message.obtain();
                    message.arg1 = 2;
                    message.replyTo = mActivityMessenger;
                    try {
                        mServiceMessenger.send(message);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        mAdapter = new PlaylistAdapter(this, Playlist.songs);
        recyclerView.setAdapter(mAdapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);


    }

    private void testIntents() {
        // Explicit intent
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra(SONG_TITLE, "HAHAHA");
        startActivityForResult(intent, REQUEST_FAVORITE);
    }

    private void downloadSongs() {
        Toast.makeText(MainActivity.this, "Downloading", Toast.LENGTH_SHORT).show();

        // Send message to handler for processing
        for (Song song : Playlist.songs) {
            Intent intent = new Intent(MainActivity.this, DownloadIntentService.class);
            intent.putExtra(KEY_SONG, song);
            startService(intent);
        }
    }

    public void changePlayButtonText(String text) {
        mPlayButton.setText(text);
    }


    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, PlayerService.class);
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mBound) {
            unbindService(mServiceConnection);
            mBound = false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_FAVORITE) {
            if (resultCode == RESULT_OK) {
                boolean result = data.getBooleanExtra(EXTRA_FAVORITE, false);
                Log.i(TAG, "Is favorite?" + result);
            }
        }
    }
}
