package com.bt.heynath.reciever;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.bt.heynath.MainActivity;
import com.bt.heynath.R;

public class PlayMorningStuti extends Service {
    public PlayMorningStuti() {
    }
    private static final String TAG = null;
   public static MediaPlayer player;
    public IBinder onBind(Intent arg0) {

        return null;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        player = MediaPlayer.create(this, R.raw.tone455);
        player.setLooping(false); // Set looping
        player.setVolume(100,100);
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                //  performOnEnd();
                playDialy500(PlayMorningStuti.this);
            }

        });



    }
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(!isSilentMode(this))
        {

           if( AlramUtility.isToPlay())
           {
               PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 1,
                       new Intent(getApplicationContext(), MainActivity.class).
                               addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP),
                       PendingIntent.FLAG_CANCEL_CURRENT);
               NewMessageNotification.notify(this, "नित्य स्तुति - भाग १", "नित्य स्तुति  - भाग १", 1, contentIntent);

               player.start();
               AlramUtility.updateMorningTime(getApplicationContext());
               firstRunnable=0;

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
        }
        return 1;
    }

    public void onStart(Intent intent, int startId) {
        // TO DO
    }
    public IBinder onUnBind(Intent arg0) {
        // TO DO Auto-generated method
        return null;
    }

    public void onStop() {

    }
    public void onPause() {

    }
    @Override
    public void onDestroy() {
      //  player.stop();
       // player.release();
    }

    @Override
    public void onLowMemory() {

    }



    void playDialy500(Context context)
    {
        //MediaPlayer mPlayer;
         this.  player = MediaPlayer.create(context, R.raw.tone500);//Create MediaPlayer object with MP3 file under res/raw folder
          player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                //  performOnEnd();
            }

        });
        if(!isSilentMode(context))
        {
            PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 1,
                    new Intent(getApplicationContext(), MainActivity.class).
                            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP),
                    PendingIntent.FLAG_CANCEL_CURRENT);
            NewMessageNotification.notify(this, "नित्य स्तुति - भाग २", "नित्य स्तुति  - भाग २", 1, contentIntent);
            player.start();
        }

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


    Handler handler=new Handler();
    public static int firstRunnable=0;
    Runnable playerRunnable=new Runnable()
    {
        @Override
        public void run() {
            try
            {
                firstRunnable++;
                if(firstRunnable<1200  && PlayMorningStuti.player!=null)
                    handler.postDelayed(playerRunnable,1000);
                int val= PlayMorningStuti.player.getCurrentPosition();

            }
            catch (Exception ex)
            {

            }
        }
    };
}