package com.example.customintenttester;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import us.bojie.musicmachine.models.Song;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = (TextView) findViewById(R.id.textView);
        Intent intent = getIntent();
        if ("us.bojie.intent.action.SHARE_SONG".equals(intent.getAction())) {
            Song song = intent.getParcelableExtra("EXTRA_SONG");
            if (textView != null) {
                textView.setText(song.toString());
            }
        }
    }
}
