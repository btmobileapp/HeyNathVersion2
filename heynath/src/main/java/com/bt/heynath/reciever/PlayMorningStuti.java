package com.bt.heynath.reciever;

import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.bt.heynath.DeviceUuidFactory;
import com.bt.heynath.ItemDAOLOg;
import com.bt.heynath.Launch;
import com.bt.heynath.MainActivity;
import com.bt.heynath.ModelLogs;
import com.bt.heynath.MorningStutiWithUiBinber;
import com.bt.heynath.R;

public class PlayMorningStuti extends Service {
    public PlayMorningStuti()
    {
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
              //  playDialy500(PlayMorningStuti.this);
            }

        });



    }
    public int onStartCommand(Intent intent, int flags, int startId) {

        if(Launch.isLogMaintain)
            try
            {
                ModelLogs l=new ModelLogs();
                l.Type="Nitya Stuti<26";
                l.LogMessage="Play Morning Stuti <26";
                l.LogDate=System.currentTimeMillis();
                l.HI1= DeviceUuidFactory.getSimNumber(this);
                l.HI1= DeviceUuidFactory.getIMENumber(this);
                l.LogToken=new DeviceUuidFactory(this).getDeviceUuid().toString()+"_"+System.currentTimeMillis();
                String reqString = Build.MANUFACTURER
                        + "," + Build.MODEL + " " + Build.VERSION.RELEASE
                        + "," + Build.VERSION_CODES.class.getFields()[android.os.Build.VERSION.SDK_INT].getName();
                l.HD=reqString;
                ItemDAOLOg itemDAOLOg=new ItemDAOLOg(this);
                itemDAOLOg.insertRecord(l);
            }
            catch (Exception ex){}

        if(!isSilentMode(this))
        {

           if( AlramUtility.isToPlay())
           {
               PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 1,
                       new Intent(getApplicationContext(), MorningStutiWithUiBinber.class).
                               addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP),
                       PendingIntent.FLAG_CANCEL_CURRENT);
               NewMessageNotification.notify(this, "??????????????? ?????????????????? - ????????? ???", "??????????????? ??????????????????  - ????????? ???", 1, null);

               player.start();

               IntentFilter filter=  new IntentFilter();
               filter.addAction("Pause Stuti");
               filter.addAction("Play Stuti");
               filter.addAction("Delete Stuti");
               registerReceiver(receiver,filter);
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
      //   this.  player = MediaPlayer.create(context, R.raw.tone500);//Create MediaPlayer object with MP3 file under res/raw folder
        String  RES_PREFIX = "android.resource://com.bt.heynath/";
       // player.setDataSource(getApplicationContext(),
       //         Uri.parse(RES_PREFIX + R.raw.tone500));
        try {
            player.setDataSource(context,  Uri.parse(RES_PREFIX + R.raw.tone500));
        }
        catch (Exception ex){}
          player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                //  performOnEnd();
            }

        });
        if(!isSilentMode(context))
        {
            PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 1,
                    new Intent(getApplicationContext(), MorningStutiWithUiBinber.class).
                            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP),
                    PendingIntent.FLAG_CANCEL_CURRENT);
            NewMessageNotification.notify(this, "??????????????? ?????????????????? - ????????? ???", "??????????????? ??????????????????  - ????????? ???", 1, contentIntent);
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


    BroadcastReceiver receiver =new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equalsIgnoreCase("Pause Stuti"))
            {
                if(player!=null) {
                    if (player.isPlaying()) {
                        player.pause();
                    }
                }
            }
            if(intent.getAction().equalsIgnoreCase("Play Stuti"))
            {
                if(player!=null) {
                    if (!player.isPlaying()) {
                        player.start();
                    }
                }
            }
            if(intent.getAction().equalsIgnoreCase("Delete Stuti"))
            {
                if(player!=null) {
                    try {
                        player.stop();
                    }catch (Exception ex){}
                }
            }

        }
    };
}