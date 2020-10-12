package com.bt.heynath.reciever;
import java.util.concurrent.TimeUnit;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.JobIntentService;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.ListenableWorker;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.bt.heynath.MainActivity;
import com.bt.heynath.scheduler.MyWorker;

public class BootReciever extends BroadcastReceiver
{

    private PeriodicWorkRequest periodicWorkRequest;

    @Override
    public void onReceive(Context context, Intent intent)
    {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
       // throw new UnsupportedOperationException("Not yet implemented");
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
                if( AlramUtility.isStart(context)  && AlramUtility.getIntervalTimeInMinute(context)>10)
                {
                    long interval = AlramUtility.getIntervalTime(context);
                    PeriodicWorkRequest unused = BootReciever.this.periodicWorkRequest = (PeriodicWorkRequest) new PeriodicWorkRequest.Builder((Class<? extends ListenableWorker>) MyWorker.class, (long) interval, TimeUnit.MILLISECONDS).build();
                    WorkManager.getInstance().enqueueUniquePeriodicWork("My Work", ExistingPeriodicWorkPolicy.KEEP, BootReciever.this.periodicWorkRequest);
                }
                Intent mIntent = new Intent(context, JobAlarmService.class);
                JobAlarmService.enqueueWork(context, mIntent);
            }
            else
            {
                //MainActivity.this.startAlarm(repeatInterval);
                context.startService(new Intent(context, AlarmService.class));
            }
        }
        catch (Exception ex)
        {
            Log.d("Heynath", "boot Error-"+ex.toString());
        }

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
}
