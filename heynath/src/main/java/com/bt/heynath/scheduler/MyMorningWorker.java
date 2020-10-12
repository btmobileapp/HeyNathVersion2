package com.bt.heynath.scheduler;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.bt.heynath.R;
import com.bt.heynath.reciever.AlramUtility;
import com.bt.heynath.reciever.NewMessageNotification;

import java.util.Calendar;

public class MyMorningWorker extends Worker
{

   public static MediaPlayer mediaPlayer;

    public MyMorningWorker(@NonNull Context context, @NonNull WorkerParameters workerParams)
    {
        super(context, workerParams);

    }

    @NonNull
    @Override
    public Result doWork()
    {
        if(!AlramUtility.isMute(getApplicationContext())  && AlramUtility.isNityaSuchiStart(getApplicationContext())  && !isAirplaneModeOn(getApplicationContext())  && !isCallActive(getApplicationContext())  && !isSilentMode(getApplicationContext()))
        {
            NewMessageNotification.notify(getApplicationContext(), "नित्य स्तुति - भाग १-", "नित्य स्तुति - भाग १-", 1, null);
            Calendar calendar= Calendar.getInstance();

            int startHour=4;
            int startMinute=55;
            int endHour=15;
            int endMinute=55;

            Calendar startCalendar= Calendar.getInstance();
            startCalendar.set(Calendar.HOUR_OF_DAY,startHour);
            startCalendar.set(Calendar.MINUTE,startMinute);

            Calendar endCalendar= Calendar.getInstance();
            endCalendar.set(Calendar.HOUR_OF_DAY,endHour);
            endCalendar.set(Calendar.MINUTE,endMinute);

            if ( AlramUtility.isToPlay() )
            {
                AlramUtility.updateMorningTime();
                playAudio();
            }
        }
        return Result.success();
    }

    private void playAudio()
    {
        try
        {
            NewMessageNotification.notify(getApplicationContext(), "नित्य स्तुति - भाग १-", "नित्य स्तुति - भाग १-", 1, null);

            this.mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.tone455);
            if (!this.mediaPlayer.isPlaying())
            {
                this.mediaPlayer.start();
                this.mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    public void onCompletion(MediaPlayer mp)
                    {
                        playAudio1();
                        /*
                        MyMorningWorker.this.mediaPlayer.reset();
                        MyMorningWorker.this.mediaPlayer.release();
                        MyMorningWorker.this.mediaPlayer = null;*/
                    }
                });
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    private void playAudio1()
    {
        try
        {
            NewMessageNotification.notify(getApplicationContext(), "नित्य स्तुति - भाग २-", "नित्य स्तुति - भाग २-", 1, null);

            this.mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.tone500);
            if (!this.mediaPlayer.isPlaying())
            {
                this.mediaPlayer.start();
                this.mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    public void onCompletion(MediaPlayer mp)
                    {

                        MyMorningWorker.this.mediaPlayer.reset();
                        MyMorningWorker.this.mediaPlayer.release();
                        MyMorningWorker.this.mediaPlayer = null;
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
