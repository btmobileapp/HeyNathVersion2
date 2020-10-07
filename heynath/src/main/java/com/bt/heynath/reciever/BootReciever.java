package com.bt.heynath.reciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.JobIntentService;

public class BootReciever extends BroadcastReceiver
{

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
        try {
            NewMessageNotification.notify(context, intent.getAction(), intent.getAction(), 1, null);
            Toast.makeText(context, "Alarm Scheduled", Toast.LENGTH_SHORT).show();
         //   context.startService(new Intent(context, AlarmService.class));

            Intent mIntent = new Intent(context, JobAlarmService.class);
            JobAlarmService.enqueueWork(context, mIntent);
            Log.d("Heynath", "Service Called");
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
