package com.bt.heynath;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.ListenableWorker;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.bt.heynath.reciever.BootReciever;
import com.bt.heynath.reciever.JobPlayMorningStuti;
import com.bt.heynath.reciever.PlayMorningStuti;
import com.bt.heynath.scheduler.MorningJobService;
import com.bt.heynath.scheduler.MyMorningWorker;
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

import java.util.concurrent.TimeUnit;

public class NityaStuti extends AppCompatActivity {

    Button button,button1,button2,button3,button4;
    private MediaPlayer player;
    private PeriodicWorkRequest periodicMorningWorkRequest;


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
                       // finish();
                      //  PeriodicWorkRequest unused1 = NityaStuti.this.periodicMorningWorkRequest = (PeriodicWorkRequest) new PeriodicWorkRequest.Builder((Class<? extends ListenableWorker>) MyMorningWorker.class, 1000*60*15,  TimeUnit.MILLISECONDS).build();
                      //  WorkManager.getInstance().enqueueUniquePeriodicWork("My Morning Work", ExistingPeriodicWorkPolicy.KEEP, NityaStuti.this.periodicMorningWorkRequest);

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
                editor.putBoolean("NityaSuchi",false);
                editor.commit();

                AlertDialog.Builder alert=new AlertDialog.Builder(NityaStuti.this);
                alert.setMessage("नित्य स्तुति को रोक दिया गया है");
                alert.setPositiveButton("ठीक है", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                       // finish();
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


        button4=findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player.pause();
                button4.setVisibility(View.GONE);
            }
        });

        chekcIsPlayingStuti();

        if(player!=null&& player.isPlaying())
        {
            button4.setVisibility(View.VISIBLE);
        }
        else
        {
            button4.setVisibility(View.INVISIBLE);
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




    void chekcIsPlayingStuti()
    {
        if(MyMorningWorker.mediaPlayer!=null)
        {
            if(MyMorningWorker.mediaPlayer.isPlaying())
            {
                player=MyMorningWorker.mediaPlayer;
            }
        }
        try {

            if(Build.VERSION.SDK_INT>21) {
                if (MorningJobService.mediaPlayer != null) {
                    if (MorningJobService.mediaPlayer.isPlaying()) {
                        player = MorningJobService.mediaPlayer;
                    }
                }
            }
        }
        catch (Exception ex)
        {}

        if(PlayMorningStuti.player!=null)
        {
            if(PlayMorningStuti.player.isPlaying())
            {
                player=PlayMorningStuti.player;
            }
        }
        if(JobPlayMorningStuti.player!=null)
        {
            if(JobPlayMorningStuti.player.isPlaying())
            {
                player=JobPlayMorningStuti.player;
            }
        }

    }
}
