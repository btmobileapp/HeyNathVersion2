package com.bt.heynath;

import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

public class PlayAudio2 extends AppCompatActivity {

    TextView txt;
    private SimpleExoPlayerView simpleExoPlayerView_Video;
    private SimpleExoPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_audio1);
        txt=findViewById(R.id.txt);
       // txt.setText("");
        txt.setText("नित्य स्तुति सुने - भाग २ ");

      //  simpleExoPlayerView_Video = findViewById(R.id.playerView);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("नित्य स्तुति सुने - भाग २ ");
       // getVideoFile("http://btwebservices.biyanitechnologies.com/dealerapp/rm.mp4");
       // initializePlayer();
        playNormal();
    }

    @Override
    public void finish() {
        super.finish();
        try {

            player.stop();
        }
        catch (Exception ex)
        {}
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }



    void playNormal()
    {
      //  View lm=getLayoutInflater().inflate(R.layout.exoview,null);

        final VideoView videoView =findViewById(R.id.vd);
        final MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);

        videoView.setMediaController(mediaController);
        //videoView.setMe
        videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" +
                R.raw.tone500));

        videoView.start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                mediaController.show(0);
                            }
                            catch (Exception ex){}
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();
        // mediaController.show(0);
        // mediaController.setEnabled(true);

    }




}
