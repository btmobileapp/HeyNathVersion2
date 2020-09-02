package com.bt.heynath.reciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;
import android.widget.Toast;

import com.bt.heynath.R;

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
                    NewMessageNotification.notify(context, "Hey Nath Stuti", "Hey Nath Stuti", 1, null);
                    playDialyAlram(context);
                }
            }
        }
        if (SOMEACTION455.equals(action))
        {
            // setNotice(context);
            Toast.makeText(context, "Alrm On", Toast.LENGTH_SHORT).show();

            Calendar calendar= Calendar.getInstance();
            Calendar startCalendar= Calendar.getInstance();

            startCalendar.set(Calendar.HOUR_OF_DAY,4);
            startCalendar.set(Calendar.MINUTE,55);
            Calendar endCalendar= Calendar.getInstance();

            endCalendar.set(Calendar.HOUR_OF_DAY,5);
            endCalendar.set(Calendar.MINUTE,10);

           if( calendar.after(startCalendar)  && calendar.before(endCalendar)  )
           {
               if (!AlramUtility.isMute(context) && AlramUtility.isStart(context)) {
                   {
                       NewMessageNotification455.notify(context, "Morning Nitya Stuti", "Details", 1, null);
                       playDialy455(context);
                   }
               }
           }

        }

        /*
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
            startCalendar.set(Calendar.HOUR_OF_DAY,17);
            startCalendar.set(Calendar.MINUTE,00);

            if( calendar.after(startCalendar)  )
            {

                if (!AlramUtility.isMute(context) && AlramUtility.isStart(context)) {
                    {
                        NewMessageNotification500.notify(context, "Boot Notofication", "Details", 1, null);
                    }
                }
            }
        }*/
    }

    MediaPlayer mPlayer;
    void playDialyAlram(Context context)
    {
        mPlayer = MediaPlayer.create(context, R.raw.textnotifi);//Create MediaPlayer object with MP3 file under res/raw folder
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
              //  performOnEnd();
            }

        });

        if(!isSilentMode(context))
            mPlayer.start();
    }
    void playDialy455(final Context context)
    {
        mPlayer = MediaPlayer.create(context, R.raw.tone455);//Create MediaPlayer object with MP3 file under res/raw folder
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                //  performOnEnd();
                playDialy500(context);
            }

        });
        if(!isSilentMode(context)  && AlramUtility.isNityaSuchiStart(context))
                mPlayer.start();
    }
    void playDialy500(Context context)
    {
        mPlayer = MediaPlayer.create(context, R.raw.tone500);//Create MediaPlayer object with MP3 file under res/raw folder
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                //  performOnEnd();
            }

        });
        if(!isSilentMode(context))
          mPlayer.start();
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
}
