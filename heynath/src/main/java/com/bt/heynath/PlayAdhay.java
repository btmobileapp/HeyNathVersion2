package com.bt.heynath;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.VideoView;

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
import com.google.android.exoplayer2.ui.PlaybackControlView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class PlayAdhay extends AppCompatActivity {

    private SimpleExoPlayerView simpleExoPlayerView_Video;
    private SimpleExoPlayer player;
    private PlaybackControlView simpleExoPlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_adhay);
       // VideoView videoView = findViewById(R.id.vd);
      ///  videoView.setVideoPath("http://btwebservices.biyanitechnologies.com/dealerapp/rm.mp4");
      //  videoView.start();
        simpleExoPlayerView_Video = findViewById(R.id.exoplayer_learning_video);
        //simpleExoPlayerView=simpleExoPlayerView_Video;
        getVideoFile("http://btwebservices.biyanitechnologies.com/dealerapp/rm.mp4");

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("नित्य सूचि");

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

    private void getVideoFile(String uploadFile)
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
        Uri audioUri = Uri.parse(uploadFile);
        MediaSource audioSource = new ExtractorMediaSource(audioUri,
                dataSourceFactory, extractorsFactory, null, null);
        // Prepare the player with the source.
        player.prepare(audioSource);
        player.setPlayWhenReady(true);
    }
}
