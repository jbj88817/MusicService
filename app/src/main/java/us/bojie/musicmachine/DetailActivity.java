package us.bojie.musicmachine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import us.bojie.musicmachine.models.Song;

/**
 * Created by bjiang on 7/26/16.
 */
public class DetailActivity extends AppCompatActivity{


    private Song mSong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView titleLabel = (TextView)findViewById(R.id.songTitleLabel);
        final CheckBox favoriteCheckbox = (CheckBox)findViewById(R.id.checkBox);

        Intent intent = getIntent();
        if (intent.getStringExtra(MainActivity.SONG_TITLE) != null) {
            String songTitle = intent.getStringExtra(MainActivity.SONG_TITLE);
            if (titleLabel != null) {
                titleLabel.setText(songTitle);
            }
        }

        if (favoriteCheckbox != null) {
            favoriteCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra(MainActivity.EXTRA_FAVORITE, isChecked);
                    setResult(RESULT_OK, resultIntent);
                    finish();
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        return super.onOptionsItemSelected(item);
    }
}
