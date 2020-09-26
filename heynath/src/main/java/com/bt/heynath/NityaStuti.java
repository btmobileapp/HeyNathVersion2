package com.bt.heynath;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.Toast;
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

public class NityaStuti extends AppCompatActivity {

    Button button,button1,button2,button3;
    private SimpleExoPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nitya_stuti);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("नित्य स्तुति");

        button=findViewById(R.id.button);
        button1=findViewById(R.id.button1);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sh=getSharedPreferences("Scheduler",MODE_PRIVATE);
                SharedPreferences.Editor editor= sh.edit();
                editor.putBoolean("NityaSuchi",true);

                editor.commit();
                AlertDialog.Builder alert=new AlertDialog.Builder(NityaStuti.this);
               // alert.setTitle("नित्य स्तुति शुरू हो चुकी है");
                alert.setMessage("नित्य स्तुति कल सुबह ४.५५ पर शुरू होगी");
                alert.setPositiveButton("ठीक है", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                alert.show();

            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sh=getSharedPreferences("Scheduler",MODE_PRIVATE);
                SharedPreferences.Editor editor= sh.edit();
                editor.putBoolean("NityaSuchi",true);
                editor.commit();

                AlertDialog.Builder alert=new AlertDialog.Builder(NityaStuti.this);
                alert.setMessage("नित्य स्तुति को रोक दिया गया है");
                alert.setPositiveButton("ठीक है", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                alert.show();
            }
        });


        button2=findViewById(R.id.button2);
        button3=findViewById(R.id.button3);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playTune();
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   playNormal1();
                startActivity(new Intent(NityaStuti. this,PlayAudio2.class));
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private SimpleExoPlayerView simpleExoPlayerView_Video;

    void playTune()
    {
        /*
        simpleExoPlayerView_Video=new SimpleExoPlayerView(this);

        AlertDialog.Builder alert=new AlertDialog.Builder(this);
        alert.setView(simpleExoPlayerView_Video);
        alert.setCancelable(false);
        //alert.show();
        //initializePlayer();
       // getAudioFile();
        playNormal();*/
        startActivity(new Intent(this,PlayAudio1.class));

    }
    /*
    private void initializePlayer() {

        SimpleExoPlayer player = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(this),
                new DefaultTrackSelector(), new DefaultLoadControl());

        String videoPath = RawResourceDataSource.buildRawResourceUri(R.raw.tone455).toString();

        Uri uri = RawResourceDataSource.buildRawResourceUri(R.raw.tone455);

        ExtractorMediaSource audioSource = new ExtractorMediaSource(
                uri,
                new DefaultDataSourceFactory(this, "MyExoplayer"),
                new DefaultExtractorsFactory(),
                null,
                null
        );

        player.prepare(audioSource);
        simpleExoPlayerView_Video.setPlayer(player);
        simpleExoPlayerView_Video.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_ZOOM);
        player.setPlayWhenReady(true);

    }*/

    private void getAudioFile()
    {
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory audioTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector =
                new DefaultTrackSelector(audioTrackSelectionFactory);
        //Initialize the player
        player = ExoPlayerFactory.newSimpleInstance(this, trackSelector);
        //Initialize simpleExoPlayerView
        SimpleExoPlayerView simpleExoPlayerView = simpleExoPlayerView_Video;
        simpleExoPlayerView.setPlayer((player));
        // Produces DataSource instances through which media data is loaded.
        DataSource.Factory dataSourceFactory =
                new DefaultDataSourceFactory(this, Util.getUserAgent(this, "CloudinaryExoplayer"));
        // Produces Extractor instances for parsing the media data.
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        // This is the MediaSource representing the media to be played.
        Uri uri = RawResourceDataSource.buildRawResourceUri(R.raw.tone455);
        Uri audioUri =  uri;// Uri.parse(uploadFile);
        MediaSource audioSource = new ExtractorMediaSource(audioUri,
                dataSourceFactory, extractorsFactory, null, null);
        // Prepare the player with the source.
        player.prepare(audioSource);
        player.setPlayWhenReady(true);
    }

    void playNormal()
    {
        View lm=getLayoutInflater().inflate(R.layout.exoview,null);

        final VideoView videoView =lm.findViewById(R.id.vd);
        final MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);

        videoView.setMediaController(mediaController);
        //videoView.setMe
        videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" +
                R.raw.tone455));
        AlertDialog.Builder alert=new AlertDialog.Builder(this);
        alert.setView(lm);
        alert.setCancelable(false);
//        mediaController.show();
        alert.setTitle("ट्यून शूरु हो चुकी");
        alert.setPositiveButton("बंद करे", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //videoView.pause();
                videoView.stopPlayback();
                dialogInterface.cancel();
            }
        });
        alert.show();
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
    void playNormal1()
    {
        View lm=getLayoutInflater().inflate(R.layout.exoview,null);

        final VideoView videoView =lm.findViewById(R.id.vd);
        final MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);

        videoView.setMediaController(mediaController);
        //videoView.setMe
        videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" +
                R.raw.tone500));
        AlertDialog.Builder alert=new AlertDialog.Builder(this);
        alert.setView(lm);
        alert.setCancelable(false);
//        mediaController.show();
        alert.setTitle("नित्य स्तुति शूरु हो चुकी");
        alert.setPositiveButton("बंद करे", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //videoView.pause();
                videoView.stopPlayback();
                dialogInterface.cancel();
            }
        });
        alert.show();
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
