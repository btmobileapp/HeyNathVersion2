package com.bt.heynath.reciever;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class AlarmService extends Service {
    public AlarmService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }



    void setPendingIntent(Context context)
    {

        Intent intent = new Intent(context, AlarmReciever.class);
        intent.setAction("com.bt.heynath.dailyalarm");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                1, intent, PendingIntent.FLAG_CANCEL_CURRENT);

       /*---------------------   SAtrt Time --------*/
        Calendar calStart = new GregorianCalendar();
        calStart.setTime(new Date());
        calStart.set(Calendar.HOUR_OF_DAY, 0);
        calStart.set(Calendar.MINUTE, 0);
        calStart.set(Calendar.SECOND, 0);
        calStart.set(Calendar.MILLISECOND, 0);

        calStart.set(Calendar.HOUR_OF_DAY, AlramUtility.getFromTimeHours(context));
        calStart.set(Calendar.MINUTE, AlramUtility.getFromTimeMinute(context));
       // Calendar calendar = Calendar.getInstance();
       // calendar.setTimeInMillis(   1598313780000l );//ystem.currentTimeMillis() + 5000);
        setAlarm(pendingIntent,context,calStart);
    }
    void setAlarm(PendingIntent pendingIntent,Context context,Calendar calStart )
    {

        long interval= AlramUtility.getIntervalTime(context);
        AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarm.cancel(pendingIntent);
        if(Build.VERSION.SDK_INT<19)
        {
            alarm.setRepeating(AlarmManager.RTC_WAKEUP, calStart.getTimeInMillis(),interval/* AlarmManager.INTERVAL_DAY*/, pendingIntent);
        }
        else
        {
            alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, calStart.getTimeInMillis(), interval/*AlarmManager.INTERVAL_DAY*/, pendingIntent);
        }
    }
    void cancelAlarm(PendingIntent pendingIntent,Context context,Calendar calStart )
    {
        AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarm.cancel(pendingIntent);
    }

    void setPendingIntent455(Context context)
    {

        Intent intent = new Intent(context, AlarmReciever.class);
        intent.setAction("com.bt.heynath.dailyalarm455");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                1, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        /*---------------------   SAtrt Time --------*/
        Calendar calStart = new GregorianCalendar();
        calStart.setTime(new Date());
        calStart.set(Calendar.HOUR_OF_DAY, 12);
        calStart.set(Calendar.MINUTE, 0);
        calStart.set(Calendar.SECOND, 0);
        calStart.set(Calendar.MILLISECOND, 0);

         //  calStart.set(Calendar.HOUR_OF_DAY, AlramUtility.getFromTimeHours(context));
        //  calStart.set(Calendar.MINUTE, AlramUtility.getFromTimeMinute(context));


        long interval= AlramUtility.getIntervalTime(context);
        AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarm.cancel(pendingIntent);
        if(Build.VERSION.SDK_INT<19)
        {
            alarm.setRepeating(AlarmManager.RTC_WAKEUP, calStart.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
        else
        {
            alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, calStart.getTimeInMillis(),AlarmManager.INTERVAL_DAY, pendingIntent);
        }
    }
    void setPendingIntent500(Context context)
    {

        Intent intent = new Intent(context, AlarmReciever.class);
        intent.setAction("com.bt.heynath.dailyalarm500");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                1, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        /*---------------------   SAtrt Time --------*/
        Calendar calStart = new GregorianCalendar();
        calStart.setTime(new Date());
        calStart.set(Calendar.HOUR_OF_DAY, 17);
        calStart.set(Calendar.MINUTE, 0);
        calStart.set(Calendar.SECOND, 0);
        calStart.set(Calendar.MILLISECOND, 0);




        AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarm.cancel(pendingIntent);
        if(Build.VERSION.SDK_INT<19)
        {
            alarm.setRepeating(AlarmManager.RTC_WAKEUP, calStart.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
        else
        {
            alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, calStart.getTimeInMillis(),AlarmManager.INTERVAL_DAY, pendingIntent);
        }
    }

    @Override
    public void onStart(final Intent intent, final int startId) {
        super.onStart(intent, startId);
        setPendingIntent(this);
        setPendingIntent455(this);
        //setPendingIntent500(this);
    }
}
