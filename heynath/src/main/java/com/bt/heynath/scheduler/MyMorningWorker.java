package com.bt.heynath.scheduler;

import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.bt.heynath.DeviceUuidFactory;
import com.bt.heynath.ItemDAOLOg;
import com.bt.heynath.Launch;
import com.bt.heynath.MainActivity;
import com.bt.heynath.ModelLogs;
import com.bt.heynath.MorningStutiWithUiBinber;
import com.bt.heynath.R;
import com.bt.heynath.reciever.AlarmReciever;
import com.bt.heynath.reciever.AlramUtility;
import com.bt.heynath.reciever.NewMessageNotification;
import com.bt.heynath.reciever.PlayMorningStuti;
import com.bt.heynath.reciever.UpdateLogToServer;

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
        Log.d("Heynath","Morning Periodic Worker");

        if(AlramUtility.lastPalytime==null)
        {
            try {
                AlramUtility.loadLastTime(getApplicationContext());
            }
            catch (Exception ex)
            {}
        }

        if(Launch.isLogMaintain)
            try
            {
                ModelLogs l=new ModelLogs();
                l.Type="Nitya Stuti";
                l.LogMessage="loop 15-NityaStuti-MyMorningWorker -Last Play Time-"+AlramUtility.lastPalytime.getTimeInMillis();
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
        callJobScheduler();

        if(!AlramUtility.isMute(getApplicationContext())  && AlramUtility.isNityaSuchiStart(getApplicationContext())  && !isAirplaneModeOn(getApplicationContext())  && !isCallActive(getApplicationContext())  && !isSilentMode(getApplicationContext()))
        {
            Log.d("Heynath","Morning Periodic Worker-ConditionMatch");

            Calendar calendar= Calendar.getInstance();

            int startHour=AlramUtility.nityaH;
            int startMinute=AlramUtility.nityaM;
            int endHour= AlramUtility.nityaHTo;
            int endMinute=AlramUtility.nityaMTo;

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
                if(Launch.isLogMaintain)
                    try
                    {
                        ModelLogs l=new ModelLogs();
                        l.Type="Nitya Stuti";
                        l.LogMessage="MyMorningWorker Playing Audio";
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

        try
        {
            UpdateLogToServer logToServer=new UpdateLogToServer();
          //  logToServer.saveLogs(context);
        }
        catch (Exception ex)
        {}

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

            NewMessageNotification.notify(getApplicationContext(), "नित्य स्तुति - भाग १-", "नित्य स्तुति - भाग १-", 1, contentIntent);

            this.mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.tone455);

            if (!this.mediaPlayer.isPlaying())
            {
                IntentFilter filter=  new IntentFilter();
                filter.addAction("Pause Stuti");
                filter.addAction("Play Stuti");
                filter.addAction("Delete Stuti");
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



    void callJobScheduler()
    {
        Calendar calendar= Calendar.getInstance();
        int h=  calendar.get(Calendar.HOUR_OF_DAY);
        int min=calendar.get(Calendar.MINUTE);

        if(h==AlramUtility.nityaH && min>30 && min<59)
        {

            int minuteRemaining= 0;// 55-min;
            if(AlramUtility.nityaM>min)
            {
                minuteRemaining= AlramUtility.nityaM-min;
            }

            if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP) {
                callJobService(context, minuteRemaining);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    void callJobService(Context context,int minute)
    {
        int latencyTime= minute*60 * 1000+(AlramUtility.nityaS*1000);
        if(Launch.isLogMaintain)
            try
            {
                ModelLogs l=new ModelLogs();
                l.Type="Nitya Stuti";
                l.LogMessage="Job Schedular with latency-"+latencyTime+"-MyMorningWorker";
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
        ComponentName serviceComponent = new ComponentName(context, MorningJobService.class);
        JobInfo.Builder builder = new JobInfo.Builder(1857, serviceComponent);
        builder.setMinimumLatency((minute*60 * 1000)+(AlramUtility.nityaS*1000)); // wait at least
        builder.setOverrideDeadline((minute*60 * 1000)+(AlramUtility.nityaS*1000)+5000); // maximum delay
        //builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED); // require unmetered network
        //builder.setRequiresDeviceIdle(true); // device should be idle
        //builder.setRequiresCharging(false); // we don't care if the device is charging or not
        JobScheduler jobScheduler = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M)
        {
            jobScheduler = context.getSystemService(JobScheduler.class);
            jobScheduler.schedule(builder.build());
        }

    }
}
