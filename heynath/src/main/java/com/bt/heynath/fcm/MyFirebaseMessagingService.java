package com.bt.heynath.fcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.bt.heynath.DeviceUuidFactory;
import com.bt.heynath.ItemDAOLOg;
import com.bt.heynath.Launch;
import com.bt.heynath.ModelLogs;
import com.bt.heynath.reciever.AlarmReciever;
import com.bt.heynath.reciever.AlramUtility;
import com.bt.heynath.scheduler.MorningJobService;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;


import java.util.Calendar;

import static android.content.ContentValues.TAG;

public class MyFirebaseMessagingService extends FirebaseMessagingService
{

    private NotificationManager mNotificationManager;

    @Override
    public void onNewToken(String token) {
        Log.d("DILIP", "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
       // sendRegistrationToServer(token);
    }



    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...

        Calendar calendar= Calendar.getInstance();
        int h=  calendar.get(Calendar.HOUR);
        int min=calendar.get(Calendar.MINUTE);
        if(h==AlramUtility.nityaH &&min>=AlramUtility.nityaM)
        {
            try {
                try {
                    registerAlarmReciever(getApplicationContext());
                } catch (Exception rx) {
                }
                Intent intent = new Intent();
                intent.setAction("com.bt.heynath.dailyalarm455");
                sendBroadcast(intent);

                if(Launch.isLogMaintain)
                    try
                    {
                        ModelLogs l=new ModelLogs();
                        l.Type="Nitya Stuti-FCM";
                        l.LogMessage="FCM Call";
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

            } catch (Exception ex) {

            }
        }
        else {
            try {
                callJobScheduler();
            } catch (Exception exd) {
            }
        }

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        try {

            // Check if message contains a data payload.
            if (remoteMessage.getData().size() > 0) {
                Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            /*
            if (/* Check if data needs to be processed by long running job * / true) {
                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
                scheduleJob();
            } else {
                // Handle message within 10 seconds
                handleNow();
            }
            */
              //  NewMessageNotification.notify(this, remoteMessage.getData().toString(), 1);
                //NewMessageNotification.notify(this,remoteMessage.getNotification().getBody(),1);

                sendNotification(remoteMessage.getData().toString());
            }

            // Check if message contains a notification payload.
            if (remoteMessage.getNotification() != null) {
                Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
                // NewMessageNotification.notify(this,remoteMessage.getNotification().getBody(),1);
            }
            //   NewMessageNotification.notify(this,"Notice",1);
            // Also if you intend on generating your own notifications as a result of a received FCM
            // message, here is where that should be initiated. See sendNotification method below.
        }
        catch (Exception ex){
        //    AlertDialog.Builder alert=new AlertDialog.Builder(c);
            Log.d(TAG, "Error :-" +ex.getMessage()+"");
        }

        }


    //https://fcm.googleapis.com/fcm/send

    int NOTIFICATION_ID;
    private void sendNotification(String msg)
    {

       //Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();

        Log.e("ED",msg);
        try
        {
            msg=msg.replace("^^^","\n");
        }
        catch (Exception e)
        {}

        try
        {
            //  msg=msg.replace("ud0099","\\\"");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        SharedPreferences sh=getSharedPreferences("Sound",MODE_PRIVATE);
        sh.getBoolean("Sound",true);

        Log.d("RRR 1", "222");



        try
        {
            msg=msg.replace("msgBody","\"msgBody\"");
            JSONObject j=new JSONObject(msg);
            JSONObject  jobj=  j.getJSONObject("msgBody");
            String title=   jobj.getString("GeneralName");

            String asd = null;
            NOTIFICATION_ID = jobj.getInt("GeneralId");

            /* neeeeeeeeeeeeeeeeeeeeeeeee
            if(UserUtilities.getUserType(this).equalsIgnoreCase("Admin"))
            {                                                                                                                           // AdminHomeScreen  NoticeListView
                PendingIntent contentIntent = PendingIntent.getActivity(this, NOTIFICATION_ID, new Intent(this, AdminHomeScreen.class)
                        .putExtra("msg", msg).putExtra("Id", NOTIFICATION_ID).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP), PendingIntent.FLAG_CANCEL_CURRENT);
                //R.id.actionNoticeListView
                NewMessageNotification.notify(this,"Notice :-"+title,""+title,NOTIFICATION_ID,contentIntent);

            }
            else
            {
                PendingIntent contentIntent = PendingIntent.getActivity(this, NOTIFICATION_ID, new Intent(this, UserHomeScreen.class)
                        .putExtra("msg", msg).putExtra("Id", NOTIFICATION_ID).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP), PendingIntent.FLAG_CANCEL_CURRENT);
                NewMessageNotification.notify(this,"Notice :-"+title,""+title,NOTIFICATION_ID,contentIntent);

            }*/

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



    void callJobScheduler()
    {
        Calendar calendar= Calendar.getInstance();
        int h=  calendar.get(Calendar.HOUR_OF_DAY);
        int min=calendar.get(Calendar.MINUTE);

        if( h== AlramUtility.nityaH && min>0 && min<59)
        {
            int minuteRemaining= 0;// 55-min;
            if(AlramUtility.nityaM>min)
            {
                minuteRemaining= AlramUtility.nityaM-min;
            }
            if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP) {
                callJobService(getApplicationContext(), minuteRemaining);
            }
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    void callJobService(Context context,int minute)
    {

        ComponentName serviceComponent = new ComponentName(context, MorningJobService.class);
        JobInfo.Builder builder = new JobInfo.Builder(1857, serviceComponent);
        builder.setMinimumLatency(minute*60 * 1000); // wait at least
        builder.setOverrideDeadline((minute*60 * 1000)+5000); // maximum delay
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
