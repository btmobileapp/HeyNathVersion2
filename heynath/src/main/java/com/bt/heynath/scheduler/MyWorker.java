package com.bt.heynath.scheduler;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.ListenableWorker;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.bt.heynath.MainActivity;
import com.bt.heynath.R;
import com.bt.heynath.reciever.AlramUtility;
import com.bt.heynath.reciever.NewMessageNotification;

import java.util.Calendar;

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

            PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 1,
                    new Intent(getApplicationContext(), MainActivity.class).
                            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP),
                    PendingIntent.FLAG_CANCEL_CURRENT);

            NewMessageNotification.notify(getApplicationContext(), "है नाथ की पुकार-", "है नाथ की पुकार-", 1, contentIntent);

            int startHour=AlramUtility.getFromTimeHours(getApplicationContext());
            int startMinute=AlramUtility.getFromTimeMinute(getApplicationContext());
            int endHour=AlramUtility.getToTimeHours(getApplicationContext());
            int endMinute=AlramUtility.getToTimeMinute(getApplicationContext());
            Calendar calendar= Calendar.getInstance();
            Calendar startCalendar= Calendar.getInstance();
            startCalendar.set(Calendar.HOUR_OF_DAY,startHour);
            startCalendar.set(Calendar.MINUTE,startMinute);
            Calendar endCalendar= Calendar.getInstance();
            endCalendar.set(Calendar.HOUR_OF_DAY,endHour);
            endCalendar.set(Calendar.MINUTE,endMinute);
            if (calendar.after(startCalendar) && calendar.before(endCalendar))
            {
                playAudio();
            }
        }
        return Result.success();
    }

    private void playAudio()
    {
        try
        {
            this.mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.textnotifi);
            if (!this.mediaPlayer.isPlaying())
            {
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
