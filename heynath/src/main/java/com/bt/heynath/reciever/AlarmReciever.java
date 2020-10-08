package com.bt.heynath.reciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.bt.heynath.R;

import java.util.Calendar;

public class AlarmReciever extends BroadcastReceiver {
    private final String SOMEACTION = "com.bt.heynath.dailyalarm"; //packagename is com.whatever.www
    private final String SOMEACTION455 = "com.bt.heynath.dailyalarm455";
    private final String SOMEACTION500 = "com.bt.heynath.dailyalarm500";
    @Override
    public void onReceive(final Context context, Intent intent)
    {
        NewMessageNotification.notify(context,"Aralm Rec", intent.getAction(), 2, null);

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

            if(!AlramUtility.isMute(context)  && AlramUtility.isStart(context)  && !isAirplaneModeOn(context)  && !isCallActive(context)  && !isSilentMode(context))
            {
                if (calendar.after(startCalendar) && calendar.before(endCalendar))
                {
                    NewMessageNotification.notify(context, "है नाथ की पुकार", "है नाथ की पुकार", 1, null);
                    //  playDialyAlram(context);

                    try
                    {
                        Intent intetentService = new Intent(context, PlayStutiService.class);
                        context.startService(intetentService);
                    }
                    catch (Exception ex)
                    {
                        Intent intetentService=new Intent(context,JobPlayStutiService.class);
                        JobPlayStutiService.enqueueWork(context, intetentService);
                    }
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
            startCalendar.set(Calendar.MINUTE,54);
            Calendar endCalendar= Calendar.getInstance();

            endCalendar.set(Calendar.HOUR_OF_DAY,6);
            endCalendar.set(Calendar.MINUTE,10);

           if( calendar.after(startCalendar)  && calendar.before(endCalendar)  )
           {
             //  NewMessageNotification.notify(context,"Pre Schedule 455", "Pre Schedule 455", 7, null);

               if ( !AlramUtility.isMute(context) && AlramUtility.isStart(context)   && !isAirplaneModeOn(context)  && !isCallActive(context))
               {
                   {
                             /*
                       NewMessageNotification455.notify(context, "Morning Nitya Stuti", "Details", 1, null);
                       new Thread(new Runnable()
                       {
                           @Override
                           public void run() {
                               playDialy455(context);
                           }
                       }).start();

                        */
                   //    NewMessageNotification.notify(context,"455 service call", "455 service call", 2, null);

                       try
                       {
                            Intent intetentService=new Intent(context,PlayMorningStuti.class);
                            context.startService(intetentService);
                       }
                       catch (Exception ex)
                       {
                           Intent intetentService=new Intent(context,JobPlayMorningStuti.class);
                           JobPlayMorningStuti.enqueueWork(context, intetentService);
                       }

                   }
               }
               else
               {
                  // NewMessageNotification.notify(context,"455 Condition Fail", "455 Condition Fail", 2, null);

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


    void playDialyAlram(Context context)
    {
        MediaPlayer mPlayer;
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

        MediaPlayer mPlayer;
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
        MediaPlayer mPlayer;
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
}
