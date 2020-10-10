package com.bt.heynath.scheduler;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.ListenableWorker;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.bt.heynath.R;
import com.bt.heynath.reciever.AlramUtility;
import com.bt.heynath.reciever.NewMessageNotification;

public class MyWorker extends Worker
{
   public static MediaPlayer mediaPlayer;

    public MyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams)
    {
        super(context, workerParams);

    }

    @NonNull
    @Override
    public Result doWork()
    {
        if(!AlramUtility.isMute(getApplicationContext())  && AlramUtility.isStart(getApplicationContext())  && !isAirplaneModeOn(getApplicationContext())  && !isCallActive(getApplicationContext())  && !isSilentMode(getApplicationContext()))
        {
            NewMessageNotification.notify(getApplicationContext(), "है नाथ की पुकार-", "है नाथ की पुकार-", 1, null);

            playAudio();
        }
        return Result.success();
    }

    private void playAudio()
    {
        try
        {
            this.mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.textnotifi);
            if (!this.mediaPlayer.isPlaying()) {
                this.mediaPlayer.start();
                this.mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    public void onCompletion(MediaPlayer mp)
                    {
                        MyWorker.this.mediaPlayer.reset();
                        MyWorker.this.mediaPlayer.release();
                        MyWorker.this.mediaPlayer = null;
                    }
                });
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }



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
