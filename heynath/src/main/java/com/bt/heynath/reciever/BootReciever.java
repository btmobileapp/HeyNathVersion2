package com.bt.heynath.reciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class BootReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent)
    {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
       // throw new UnsupportedOperationException("Not yet implemented");

        NewMessageNotification.notify(context, intent.getAction(), intent.getAction(), 1, null);
        Toast.makeText(context, "Alarm Scheduled", Toast.LENGTH_SHORT).show();
        context.startService(new Intent(context, AlarmService.class));
    }
}
