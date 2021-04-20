package com.bt.heynath;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.VideoView;

import com.bt.heynath.models.DownloadBean;
import com.bt.heynath.models.ItemDAODownload;
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

import java.io.File;

public class PlayAdhay extends AppCompatActivity {
//  api/SystemUser/GetGenerateOTPForOEMobApp?LoginName=9172350423
    private SimpleExoPlayerView simpleExoPlayerView_Video;
    private SimpleExoPlayer player;
    private PlaybackControlView simpleExoPlayerView;
  //http://btwebservices.biyanitechnologies.com/galaxybackupservices/galaxy1.svc/HeynathAdhay
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_adhay);
       // VideoView videoView = findViewById(R.id.vd);
      ///  videoView.setVideoPath("http://btwebservices.biyanitechnologies.com/dealerapp/rm.mp4");
      //  videoView.start();
        simpleExoPlayerView_Video = findViewById(R.id.exoplayer_learning_video);
        //simpleExoPlayerView=simpleExoPlayerView_Video;


        String url="http://btwebservices.biyanitechnologies.com/dealerapp/rm.mp4";
        try {
            ItemDAODownload itemDAODownload=new ItemDAODownload(this);
            DownloadBean bean= itemDAODownload.getRecordForUrl(url);
            if(bean!=null)
            {
                Log.d("Heynath-savedpath",bean.SavedPath);
                if(bean.SavedPath!=null&& bean.SavedPath.length()>5);
                {
                    url=bean.getSavedPath();
                }
            }
        }
        catch (Exception ex)
        {}
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("अध्याय");
        getVideoFile(url);

       // downloadAdhay(url);
    }

    private DownloadManager dm;
    private long enqueue;
    void downloadAdhay(String Url)
    {
        registerReciever();
        dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(Url)); // "http://www.vogella.de/img/lars/LarsVogelArticle7.png"));
        request.setTitle("Adhay1");
        request.setDescription("Adhay1");
        File dir =// Environment.getExternalStorageDirectory();
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        dir.mkdirs();
        Uri downloadLocation = Uri.fromFile(new File(dir, Url.split("/")[Url.split("/").length - 1]));
        request.setDestinationUri(downloadLocation);
        enqueue = dm.enqueue(request);


    }

    BroadcastReceiver receiver;
    long iidd;
    public void registerReciever()
    {
        receiver = new BroadcastReceiver()
        {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d("Heynath-",intent.getAction());
                String action = intent.getAction();
                if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action))
                {
                    long downloadId = intent.getLongExtra(
                            DownloadManager.EXTRA_DOWNLOAD_ID, 0);
                    iidd=downloadId;
                    DownloadManager.Query query = new DownloadManager.Query();
                    query.setFilterById(downloadId);// enqueue);
                    Cursor c = dm.query(query);
                    ///DownloadManager.COLUMN_ID;

                    if (c.moveToFirst())
                    {
                        int columnIndex = c
                                .getColumnIndex(DownloadManager.COLUMN_STATUS);
                        if (DownloadManager.STATUS_SUCCESSFUL == c
                                .getInt(columnIndex)) {

                            String uriString = c .getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));

                            Log.d("Heynath-",uriString);
                            Toast.makeText(getApplicationContext(),"DownLoad Complete", Toast.LENGTH_LONG).show();

                            try {
                                String filePath = c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME));
                                Log.d("Heynath-",filePath);
                            }
                            catch (Exception ex){}


                            }
                    }
                }
            }
        };

        registerReceiver(receiver, new IntentFilter(
                DownloadManager.ACTION_DOWNLOAD_COMPLETE));
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
