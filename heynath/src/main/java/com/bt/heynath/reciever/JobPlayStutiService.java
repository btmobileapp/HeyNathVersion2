package com.bt.heynath.reciever;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;

import com.bt.heynath.R;

public class JobPlayStutiService extends JobIntentService {
    public JobPlayStutiService() {
    }

    public static void enqueueWork(Context context, Intent intetentService) {
        enqueueWork(context, JobPlayStutiService.class, 56, intetentService);
      //  NewMessageNotification.notify(context,"Job Scheduled- Morning", "Job Scheduled- Morning", 11, null);
    }

   public static   MediaPlayer player;
    void playMusic()
    {

        player = MediaPlayer.create(this, R.raw.textnotifi);
        player.setLooping(false); // Set looping
        player.setVolume(100,100);

        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                //  performOnEnd();
                // playDialy500(context);

                //play500(PlayStutiService.this);
            }

        });
        player.start();
        NewMessageNotification.notify(this, "है नाथ की पुकार", "है नाथ की पुकार", 1, null);

    }

    private static final String TAG = null;



    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        if ( !AlramUtility.isMute(this) && AlramUtility.isStart(this)   )
                 playMusic();
    }

    @Override
    public void onLowMemory() {

    }

    void play500(Context context)
    {
        MediaPlayer mPlayer;
        mPlayer = MediaPlayer.create(context, R.raw.tone500);//Create MediaPlayer object with MP3 file under res/raw folder
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                //  performOnEnd();
            }

        });
        if(!isSilentMode(context))
            mPlayer.start();

    }


    private static boolean isAirplaneModeOn(Context context) {
        return Settings.System.getInt(context.getContentResolver(), Settings.Global.AIRPLANE_MODE_ON, 0) != 0;
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

    public boolean isCallActive(Context context){
        AudioManager manager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        return manager.getMode() == AudioManager.MODE_IN_CALL;
    }

}