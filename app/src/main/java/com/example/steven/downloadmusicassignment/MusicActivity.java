package com.example.steven.downloadmusicassignment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.DownloadListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.FileOutputStream;


public class MusicActivity extends Activity implements MusicInterface, MediaPlayer.OnPreparedListener{


    private static String TAG = "MusicActivity";
    /**
     * Button to download and play the music
     */
    private Button button;

    /**
     * Player to play the music
     */
    private MediaPlayer player;

    /**
     * Progress dialog which is shown while downloading the music
     */
    private ProgressDialog dialog;

    /**
     * Edit text where the url is entered
     */
    private EditText editText;

    /**
     *
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
			//TODO: add code for the button
       super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        button = (Button)findViewById(R.id.button);
        editText = (EditText)findViewById(R.id.urlText);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                beforeDownload();
            }
        });
	}

    @Override
    public void beforeDownload() {
        //TODO: perform necessary steps for download the music

        DownloadMusic task = new DownloadMusic(this);
        String url = editText.getText().toString();
        task.execute(url);
        dialog = new ProgressDialog(this);
        dialog.setTitle("Downloading music ... ");
        dialog.setMessage("Download progress ... ");
        dialog.setProgressStyle(dialog.STYLE_HORIZONTAL);
        dialog.setProgress(0);
        dialog.setMax(100);
        dialog.show();


    }

    @Override
    public void downloadFinished() {
       dialog.dismiss();
        Log.i(TAG, "Finished downloading and starting playing music");
        playMusic();
    }

    @Override
    public void setProgressStatus(int progress){
        dialog.setProgress(progress);
    }

    private void playMusic(){
        try {
            Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath() + "/test.mp3");
            player = new MediaPlayer();
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
            player.setDataSource(getApplicationContext(),uri);
            player.setOnPreparedListener(this);
            player.prepareAsync();
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    // Once Music is completed playing, enable the button
                    button.setEnabled(true);
                    Toast.makeText(getApplicationContext(), "Music completed playing", Toast.LENGTH_LONG).show();
                }
            });
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public void onPrepared(MediaPlayer p){
        player.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.music, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
