package biyaniparker.com.parker.fcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;

import biyaniparker.com.parker.utilities.UserUtilities;
import biyaniparker.com.parker.view.homeadmin.AdminHomeScreen;
import biyaniparker.com.parker.view.homeadmin.OrderDetailView;
import biyaniparker.com.parker.view.homeuser.UserHomeScreen;
import biyaniparker.com.parker.view.notice.NoticeListView;

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

        }

        }


    //https://fcm.googleapis.com/fcm/send

    int NOTIFICATION_ID;
    private void sendNotification(String msg)
    {

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

            }

        }
        catch (Exception ex)
        {}


    }
}
