package com.bt.heynath.reciever;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;

import com.bt.heynath.R;

public class JobPlayMorningStuti extends JobIntentService {
    public JobPlayMorningStuti() {
    }
    MediaPlayer player;


    public static void enqueueWork(Context context, Intent intent) {
        enqueueWork(context, JobPlayMorningStuti.class, 1, intent);
        NewMessageNotification.notify(context,"Job Scheduled- Morning", "Job Scheduled- Morning", 11, null);
    }


    public void onStop() {

    }
    public void onPause() {

    }
    @Override
    public void onDestroy() {
        player.stop();
        player.release();
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        NewMessageNotification.notify(JobPlayMorningStuti.this,"Morning-Work Started", "Morning-Work Started", 12, null);

        if(!isSilentMode(this))
        {
            player = MediaPlayer.create(this, R.raw.tone455);
            player.setLooping(false); // Set looping
            player.setVolume(100,100);
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mp) {
                    //  performOnEnd();
                    playDialy500(JobPlayMorningStuti.this);
                }

            });
            NewMessageNotification.notify(this, "त्य स्तुति - भाग १", "त्य स्तुति  - भाग १", 1, null);
            player.start();
            Log.d("Heynath","Player Started");
        }
    }

    @Override
    public void onLowMemory() {

    }



    void playDialy500(Context context)
    {
        //MediaPlayer mPlayer;
        player = MediaPlayer.create(context, R.raw.tone500);//Create MediaPlayer object with MP3 file under res/raw folder
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                //  performOnEnd();
            }

        });
        if(!isSilentMode(context))
        {
            NewMessageNotification.notify(this, "त्य स्तुति - भाग २", "त्य स्तुति  - भाग २", 1, null);
            player.start();
        }
    }

    boolean isSilentMode(Context context)
    {
        AudioManager am = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);

        switch (am.getRingerMode()) {
            case AudioManager.RINGER_MODE_SILENT:
            {
                Log.i("MyApp", "Silent mode");
                return true;
                //  break;
            }
            case AudioManager.RINGER_MODE_VIBRATE:
            {
                Log.i("MyApp", "Vibrate mode");
                return  true;
                // break;
            }
            case AudioManager.RINGER_MODE_NORMAL:
            {
                Log.i("MyApp", "Normal mode");
                break;
            }
        }

        return  false;
    }
}