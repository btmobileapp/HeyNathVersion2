package com.bt.heynath.reciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.util.Calendar;

public class AlarmReciever extends BroadcastReceiver {
    private final String SOMEACTION = "com.bt.heynath.dailyalarm"; //packagename is com.whatever.www
    private final String SOMEACTION455 = "com.bt.heynath.dailyalarm455";
    private final String SOMEACTION500 = "com.bt.heynath.dailyalarm500";
    @Override
    public void onReceive(Context context, Intent intent)
    {

        String action = intent.getAction();
        if (SOMEACTION.equals(action))
        {
           // setNotice(context);
            Toast.makeText(context, "Alrm On", Toast.LENGTH_SHORT).show();
            int startHour=AlramUtility.getFromTimeHours(context);
            int startMinute=AlramUtility.getFromTimeMinute(context);
            int endHour=AlramUtility.getToTimeHours(context);
            int endMinute=AlramUtility.getToTimeMinute(context);
            Calendar calendar= Calendar.getInstance();
            Calendar startCalendar= Calendar.getInstance();
            startCalendar.set(Calendar.HOUR_OF_DAY,startHour);
            startCalendar.set(Calendar.MINUTE,startMinute);

            Calendar endCalendar= Calendar.getInstance();
            endCalendar.set(Calendar.HOUR_OF_DAY,endHour);
            endCalendar.set(Calendar.MINUTE,endMinute);

            if(!AlramUtility.isMute(context)  && AlramUtility.isStart(context))
            {
                if (calendar.after(startCalendar) && calendar.before(endCalendar)) {
                    NewMessageNotification.notify(context, "Boot Notofication", "Details", 1, null);
                }
            }
        }
        if (SOMEACTION455.equals(action))
        {
            // setNotice(context);
            Toast.makeText(context, "Alrm On", Toast.LENGTH_SHORT).show();
            int startHour=AlramUtility.getFromTimeHours(context);
            int startMinute=AlramUtility.getFromTimeMinute(context);
            int endHour=AlramUtility.getToTimeHours(context);
            int endMinute=AlramUtility.getToTimeMinute(context);
            Calendar calendar= Calendar.getInstance();
            Calendar startCalendar= Calendar.getInstance();
            startCalendar.set(Calendar.HOUR_OF_DAY,5);
            startCalendar.set(Calendar.MINUTE,2);


           if( calendar.before(startCalendar)) {
               if (!AlramUtility.isMute(context) && AlramUtility.isStart(context)) {
                   {
                       NewMessageNotification455.notify(context, "Boot Notofication", "Details", 1, null);
                   }
               }
           }

        }

        if (SOMEACTION500.equals(action))
        {
            // setNotice(context);
            Toast.makeText(context, "Alrm On", Toast.LENGTH_SHORT).show();
            int startHour=AlramUtility.getFromTimeHours(context);
            int startMinute=AlramUtility.getFromTimeMinute(context);
            int endHour=AlramUtility.getToTimeHours(context);
            int endMinute=AlramUtility.getToTimeMinute(context);
            Calendar calendar= Calendar.getInstance();
            Calendar startCalendar= Calendar.getInstance();
            startCalendar.set(Calendar.HOUR_OF_DAY,4);
            startCalendar.set(Calendar.MINUTE,59);

            if( calendar.after(startCalendar)  )
            {

                if (!AlramUtility.isMute(context) && AlramUtility.isStart(context)) {
                    {
                        NewMessageNotification500.notify(context, "Boot Notofication", "Details", 1, null);
                    }
                }
            }
        }
    }
}
