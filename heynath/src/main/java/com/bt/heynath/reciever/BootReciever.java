package com.bt.heynath.reciever;
import java.util.concurrent.TimeUnit;

import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.JobIntentService;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.ListenableWorker;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.bt.heynath.DeviceUuidFactory;
import com.bt.heynath.ItemDAOLOg;
import com.bt.heynath.Launch;
import com.bt.heynath.MainActivity;
import com.bt.heynath.ModelLogs;
import com.bt.heynath.R;
import com.bt.heynath.scheduler.Minute_10_JobService;
import com.bt.heynath.scheduler.Minute_3_JobService;
import com.bt.heynath.scheduler.Minute_5_JobService;
import com.bt.heynath.scheduler.MorningJobService;
import com.bt.heynath.scheduler.MyMorningWorker;
import com.bt.heynath.scheduler.MyWorker;

public class BootReciever extends BroadcastReceiver
{
    void initLastTime(Context context)
    {
        try {
            if (AlramUtility.lastPalytime == null) {
                try {
                    AlramUtility.loadLastTime(context);
                } catch (Exception ex) {
                }
            }
        }
        catch (Exception ex){}
    }

    private PeriodicWorkRequest periodicWorkRequest;
    private PeriodicWorkRequest periodicMorningWorkRequest;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onReceive(Context context, Intent intent)
    {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
       // throw new UnsupportedOperationException("Not yet implemented");
        if(Launch.isLogMaintain)
        try
        {
            ModelLogs l=new ModelLogs();
            l.Type="Boot";
            l.LogMessage="Boot Completed";
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
        try
        {
            initLastTime(context);
        }
        catch (Exception ex){}

        try {
            registerAlarmReciever(context);
        }
        catch (Exception ex)
        {}
        Log.d("Heynath",intent.getAction());
        try
        {
         //   NewMessageNotification.notify(context, intent.getAction(), intent.getAction(), 1, null);
            /*
         // NewMessageNotification.notify(context, intent.getAction(), intent.getAction(), 1, null);
            Toast.makeText(context, "Alarm Scheduled", Toast.LENGTH_SHORT).show();
         //   context.startService(new Intent(context, AlarmService.class));

            Intent mIntent = new Intent(context, JobAlarmService.class);
            JobAlarmService.enqueueWork(context, mIntent);
            Log.d("Heynath", "Service Called");

             */

            if (Build.VERSION.SDK_INT >= 26)
            {
                Log.d("Heynath",Build.VERSION.SDK_INT+"_ Vesion >=26");
                if( AlramUtility.isStart(context)  && AlramUtility.getIntervalTimeInMinute(context)>10)
                {
                    long interval = AlramUtility.getIntervalTime(context);
                    PeriodicWorkRequest unused = BootReciever.this.periodicWorkRequest = (PeriodicWorkRequest) new PeriodicWorkRequest.Builder((Class<? extends ListenableWorker>) MyWorker.class, (long) interval, TimeUnit.MILLISECONDS).build();
                    WorkManager.getInstance().enqueueUniquePeriodicWork("My Work", ExistingPeriodicWorkPolicy.KEEP, BootReciever.this.periodicWorkRequest);
                }

                if( AlramUtility.isNityaSuchiStart(context))
                {
                    Log.d("Heynath","Inside Condition");
                    if(Launch.isLogMaintain)
                        try
                        {
                            ModelLogs l=new ModelLogs();
                            l.Type="Nitya Stuti";
                            l.LogMessage="Schedule for 15 Min started at boot";
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
                    PeriodicWorkRequest unused1 = BootReciever.this.periodicMorningWorkRequest = (PeriodicWorkRequest) new PeriodicWorkRequest.Builder((Class<? extends ListenableWorker>) MyMorningWorker.class, 1000*60*15,  TimeUnit.MILLISECONDS).build();
                    WorkManager.getInstance().enqueueUniquePeriodicWork("My Morning Work", ExistingPeriodicWorkPolicy.KEEP, BootReciever.this.periodicMorningWorkRequest);
                   try
                   {
                       call3MinuteJobService(context);
                       call5MinuteJobService(context);
                       call10MinuteJobService(context);
                   }
                   catch (Exception ex){}
                }
                Intent mIntent = new Intent(context, JobAlarmService.class);
                JobAlarmService.enqueueWork(context, mIntent);
            }
            else
            {
                //MainActivity.this.startAlarm(repeatInterval);
                context.startService(new Intent(context, AlarmService.class));

                try
                {
                 //   call3MinuteJobService(context);
                //    call5MinuteJobService(context);
                //    call10MinuteJobService(context);
                }
                catch (Exception ex){}
            }
        }
        catch (Exception ex)
        {
            Log.d("Heynath", "boot Error-"+ex.toString());
        }

        try {
            PendingIntent contentIntent = PendingIntent.getActivity(context, 1,
                    new Intent(context, MainActivity.class).
                            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP),
                    PendingIntent.FLAG_CANCEL_CURRENT);
            com.bt.heynath.NewMessageNotification.notify(context, context.getString(R.string.app_name), context.getString(R.string.app_name), 786, contentIntent);
        }
        catch (Exception ex)
        {}

    }

    AlarmReciever reciever;
    void registerAlarmReciever(Context context)
    {
        reciever=new AlarmReciever();

        try
        {
            context.unregisterReceiver(reciever);
        }
        catch (Exception ex)
        {}
        context.registerReceiver(reciever,new IntentFilter("com.bt.heynath.dailyalarm455"));
    }




    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    void call5MinuteJobService(Context context)
    {
            int minute=5;
            ComponentName serviceComponent = new ComponentName(context, Minute_5_JobService.class);
            JobInfo.Builder builder = new JobInfo.Builder(1860, serviceComponent);
            builder.setMinimumLatency(minute*60 * 1000); // wait at least
            builder.setOverrideDeadline((minute*60 * 1000)+10000); // maximum delay
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
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    void call10MinuteJobService(Context context)
    {
        int minute=10;
        ComponentName serviceComponent = new ComponentName(context, Minute_10_JobService.class);
        JobInfo.Builder builder = new JobInfo.Builder(1858, serviceComponent);
        builder.setMinimumLatency((minute*60 * 1000)+(AlramUtility.nityaS*1000)); // wait at least
        builder.setOverrideDeadline((minute*60 * 1000)+(AlramUtility.nityaS*1000)+5000);//maximum delay
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    void call3MinuteJobService(Context context)
    {
        int minute=3;
        ComponentName serviceComponent = new ComponentName(context, Minute_3_JobService.class);
        JobInfo.Builder builder = new JobInfo.Builder(1857, serviceComponent);
        builder.setMinimumLatency((minute*60 * 1000)+(AlramUtility.nityaS*1000)); // wait at least
        builder.setOverrideDeadline((minute*60 * 1000)+(AlramUtility.nityaS*1000)+5000);
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
