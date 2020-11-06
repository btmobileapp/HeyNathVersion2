package com.bt.heynath.scheduler;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.bt.heynath.MainActivity;
import com.bt.heynath.MorningStutiWithUiBinber;
import com.bt.heynath.R;
import com.bt.heynath.reciever.AlramUtility;
import com.bt.heynath.reciever.NewMessageNotification;
import com.bt.heynath.reciever.PlayMorningStuti;

import java.util.Calendar;

public class MyMorningWorker extends Worker
{

    public static MediaPlayer mediaPlayer;

    Context context;
    public MyMorningWorker(@NonNull Context context, @NonNull WorkerParameters workerParams)
    {
        super(context, workerParams);
        this.context=context;
    }

    @NonNull
    @Override
    public Result doWork()
    {
        if(AlramUtility.lastPalytime==null)
        {
            try {
                AlramUtility.loadLastTime(getApplicationContext());
            }
            catch (Exception ex)
            {}
        }
        if(!AlramUtility.isMute(getApplicationContext())  && AlramUtility.isNityaSuchiStart(getApplicationContext())  && !isAirplaneModeOn(getApplicationContext())  && !isCallActive(getApplicationContext())  && !isSilentMode(getApplicationContext()))
        {
            Calendar calendar= Calendar.getInstance();

            int startHour=4;
            int startMinute=55;
            int endHour=6;
            int endMinute=00;

            Calendar startCalendar= Calendar.getInstance();
            startCalendar.set(Calendar.HOUR_OF_DAY,startHour);
            startCalendar.set(Calendar.MINUTE,startMinute);

            Calendar endCalendar= Calendar.getInstance();
            endCalendar.set(Calendar.HOUR_OF_DAY,endHour);
            endCalendar.set(Calendar.MINUTE,endMinute);

            if ( AlramUtility.isToPlay() )
            {

                PendingIntent contentIntent = PendingIntent.getActivity(context, 1,
                        new Intent(getApplicationContext(), MorningStutiWithUiBinber.class).
                                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK ),
                        PendingIntent.FLAG_CANCEL_CURRENT);

                NewMessageNotification.notify(getApplicationContext(), "नित्य स्तुति - भाग १-", "नित्य स्तुति - भाग १-", 1, null);

                AlramUtility.updateMorningTime(getApplicationContext());

                playAudio();
            }
        }
        return Result.success();
    }

    BroadcastReceiver receiver =new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
             if(intent.getAction().equalsIgnoreCase("Pause Stuti"))
             {
                 if(mediaPlayer!=null) {
                     if (mediaPlayer.isPlaying()) {
                            mediaPlayer.pause();
                     }
                 }
             }
            if(intent.getAction().equalsIgnoreCase("Play Stuti"))
            {
                if(mediaPlayer!=null) {
                    if (!mediaPlayer.isPlaying()) {
                        mediaPlayer.start();
                    }
                }
            }
        }
    };
    private void playAudio()
    {
        try
        {
            PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 1,
                    new Intent(getApplicationContext(), MainActivity.class).
                            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP),
                    PendingIntent.FLAG_CANCEL_CURRENT);

            NewMessageNotification.notify(getApplicationContext(), "नित्य स्तुति - भाग १-", "नित्य स्तुति - भाग १-", 1, contentIntent);

            this.mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.tone455);

            if (!this.mediaPlayer.isPlaying())
            {
                IntentFilter filter=  new IntentFilter();
                filter.addAction("Pause Stuti");
                filter.addAction("Play Stuti");
                context.registerReceiver(receiver,filter);
                this.mediaPlayer.start();
                this.mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    public void onCompletion(MediaPlayer mp)
                    {
                       // playAudio1();
                        /*
                        MyMorningWorker.this.mediaPlayer.reset();
                        MyMorningWorker.this.mediaPlayer.release();
                        MyMorningWorker.this.mediaPlayer = null;*/
                    }
                });
                init();
                try {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(2000);
                                playerRunnable.run();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
                catch (Exception ex){}
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

    void init()
    {
        handler=new Handler();
        playerRunnable  =new Runnable()
        {
          @Override
          public void run() {
            try
            {
                firstRunnable++;
                if(firstRunnable<1200  && PlayMorningStuti.player!=null)
                    handler.postDelayed(playerRunnable,1000);
                   // int val= PlayMorningStuti.player.getCurrentPosition();

            }
            catch (Exception ex)
            {

            }
        }
    };
    }



    Handler handler;
    public static int firstRunnable=0;
    Runnable playerRunnable;
}
