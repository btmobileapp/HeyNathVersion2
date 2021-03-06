package com.bt.heynath.scheduler;

import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.work.ListenableWorker;

import com.bt.heynath.DeviceUuidFactory;
import com.bt.heynath.ItemDAOLOg;
import com.bt.heynath.Launch;
import com.bt.heynath.MainActivity;
import com.bt.heynath.ModelLogs;
import com.bt.heynath.MorningStutiWithUiBinber;
import com.bt.heynath.R;
import com.bt.heynath.reciever.AlramUtility;
import com.bt.heynath.reciever.NewMessageNotification;
import com.bt.heynath.reciever.PlayMorningStuti;

import java.util.Calendar;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class MorningJobService extends JobService
{
    public static MediaPlayer mediaPlayer;

    Context context;

    @Override
    public boolean onStartJob(JobParameters params) {
        context=this;
        Toast.makeText(context, "Job Service", Toast.LENGTH_SHORT).show();
        if(AlramUtility.lastPalytime==null)
        {
            try
            {
                AlramUtility.loadLastTime(getApplicationContext());
            }
            catch (Exception ex)
            {}
        }
        if( !AlramUtility.isMute(getApplicationContext())  && AlramUtility.isNityaSuchiStart(getApplicationContext())  && !isAirplaneModeOn(getApplicationContext())  && !isCallActive(getApplicationContext())  && !isSilentMode(getApplicationContext()))
        {
         //   Calendar calendar= Calendar.getInstance();

         //   int startHour=4;
          //  int startMinute=55;
         //   int endHour=6;
         //   int endMinute=00;

         //   Calendar startCalendar= Calendar.getInstance();
          //  startCalendar.set(Calendar.HOUR_OF_DAY,startHour);
           // startCalendar.set(Calendar.MINUTE,startMinute);

          //  Calendar endCalendar= Calendar.getInstance();
         //   endCalendar.set(Calendar.HOUR_OF_DAY,endHour);
         //   endCalendar.set(Calendar.MINUTE,endMinute);

            if ( AlramUtility.isToPlay() )
            {
                PendingIntent contentIntent = PendingIntent.getActivity(context, 1,
                        new Intent(getApplicationContext(), MorningStutiWithUiBinber.class).
                                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK ),
                        PendingIntent.FLAG_CANCEL_CURRENT);

                NewMessageNotification.notify(getApplicationContext(), "??????????????? ?????????????????? - ????????? ???-", "??????????????? ?????????????????? - ????????? ???-", 1, null);
                AlramUtility.updateMorningTime(getApplicationContext());

                playAudio();
                    if(Launch.isLogMaintain)
                    try
                    {
                        ModelLogs l=new ModelLogs();
                        l.Type="Nitya Stuti";
                        l.LogMessage="Morning Job Service Called-Playing Stuti";
                        l.LogDate=System.currentTimeMillis();
                        l.HI1= DeviceUuidFactory.getSimNumber(context);
                        l.HI1= DeviceUuidFactory.getIMENumber(context);
                        l.LogToken=new DeviceUuidFactory(context).getDeviceUuid().toString()+"_"+System.currentTimeMillis();
                        String reqString = Build.MANUFACTURER
                                + "," + Build.MODEL + " " + Build.VERSION.RELEASE
                                + "," + Build.VERSION_CODES.class.getFields()[android.os.Build.VERSION.SDK_INT].getName();
                        l.HD=reqString;
                        ItemDAOLOg itemDAOLOg=new ItemDAOLOg(context);
                        itemDAOLOg.insertRecord(l);
                    }
                    catch (Exception ex){}

            }
        }

        return  true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
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

            if(intent.getAction().equalsIgnoreCase("Delete Stuti"))
            {
                if(mediaPlayer!=null) {
                    try {
                        mediaPlayer.stop();
                    }catch (Exception ex){}
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

            NewMessageNotification.notify(getApplicationContext(), "??????????????? ?????????????????? - ????????? ???-", "??????????????? ?????????????????? - ????????? ???-", 1, contentIntent);

            this.mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.tone455);

            if (!this.mediaPlayer.isPlaying())
            {
                IntentFilter filter=  new IntentFilter();
                filter.addAction("Pause Stuti");
                filter.addAction("Play Stuti");
                filter.addAction("Delete Stuti");
                registerReceiver(receiver,filter);
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