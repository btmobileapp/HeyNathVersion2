package com.bt.heynath;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.RawResourceDataSource;
import com.google.android.exoplayer2.util.Util;

public class PlayAudio1 extends AppCompatActivity {

    TextView txt;
    private SimpleExoPlayerView simpleExoPlayerView_Video;
    private SimpleExoPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_audio1);
        txt=findViewById(R.id.txt);
       // txt.setText("");
        txt.setText("??????????????? ?????????????????? ???????????? - ????????? ???");

      //  simpleExoPlayerView_Video = findViewById(R.id.playerView);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("??????????????? ?????????????????? ???????????? - ????????? ??? ");
       // getVideoFile("http://btwebservices.biyanitechnologies.com/dealerapp/rm.mp4");
       // initializePlayer();
        playNormal();
    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        try {
//
//            //player.stop();
//        }
//        catch (Exception ex)
//        {}
//
//    }

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
    protected void onPause() {
        super.onPause();
        //if(android.os)
        if (android.os.Build.VERSION.SDK_INT >21)
        {
            requestVisibleBehind(true);
        }
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


        //videoView.set
        videoView.setMediaController(mediaController);
        //videoView.setMe
        videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" +
                R.raw.tone455));
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
               // startActivity(new Intent(PlayAudio1.this,PlayAudio2.class));
            }
        });

        try {
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                   // mediaPlayer.setScreenOnWhilePlaying(true);
                    mediaPlayer.setScreenOnWhilePlaying(true);
                }
            });
        }
        catch (Exception ex){}
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

       }
}
