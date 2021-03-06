package com.bt.heynath.reciever;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.bt.heynath.MainActivity;
import com.bt.heynath.MorningStutiWithUiBinber;
import com.bt.heynath.R;



/**
 * Helper class for showing and canceling new message
 * notifications.
 * <p>
 * This class makes heavy use of the {@link NotificationCompat.Builder} helper
 * class to create notifications in a backward-compatible way.
 */
public class NewMessageNotification {
    /**
     * The unique identifier for this type of notification.
     */
    private static final String NOTIFICATION_TAG = "NewMessage";

    /**
     * Shows the notification, or updates a previously shown notification of
     * this type, with the given parameters.
     * <p>
     * TODO: Customize this method's arguments to present relevant content in
     * the notification.
     * <p>
     * TODO: Customize the contents of this method to tweak the behavior and
     * presentation of new message notifications. Make
     * sure to follow the
     * <a href="https://developer.android.com/design/patterns/notifications.html">
     * Notification design guidelines</a> when doing so.
     *
     * @see #cancel(Context)
     */
    public static void notify(final Context context,
                              final String newtitle,String details, final int number, PendingIntent pintent) {
         //-----------------------------------------------------------------------------
        //Action Methods Intents ----
       //   1.-----------
        Intent pauseIntent = new Intent();
        pauseIntent.setAction("Pause Stuti");
        PendingIntent pausePendingIntent =
                PendingIntent.getBroadcast(context, 0, pauseIntent, 0);
              //------------------------//
        //   2.-----------
        Intent playIntent = new Intent();
        playIntent.setAction("Play Stuti");
        PendingIntent playPendingIntent =
                PendingIntent.getBroadcast(context, 0, playIntent, 0);
         //----------------------------------------------------------------------
        final Resources res = context.getResources();

        // This image is used as the notification's large icon (thumbnail).
        // TODO: Remove this if your notification has no relevant thumbnail.
        final Bitmap picture ;
       //
        if(  newtitle.equalsIgnoreCase("??????????????? ?????????????????? - ????????? ???-")
           || newtitle.equalsIgnoreCase("??????????????? ?????????????????? - ????????? ???")
           )
        {
            picture=     BitmapFactory.decodeResource(res, R.drawable.shree1);
        }
        else  if(newtitle.equalsIgnoreCase("?????? ????????? ?????? ???????????????-") || newtitle.equalsIgnoreCase("?????? ????????? ?????? ???????????????")   )
        {
            picture=     BitmapFactory.decodeResource(res, R.drawable.pukar);
        }
        else
        {
            picture=     BitmapFactory.decodeResource(res, R.drawable.shree1);
        }


        final String ticker = newtitle;
        final String title = newtitle;
        final String text = details;



       /* PendingIntent contentIntent = PendingIntent.getActivity(context, number,
                new Intent(context, activity.getClass()).

                        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP),
                PendingIntent.FLAG_CANCEL_CURRENT);*/


        int notifyID = 1;
        String CHANNEL_ID = "my_channel_01";// The id of the channel.
        CharSequence name = "BootChannle";// The user-visible name of the channel.
        int importance = NotificationManager.IMPORTANCE_HIGH;




        final NotificationCompat.Builder builder = new NotificationCompat.Builder(context,CHANNEL_ID);

        // Set appropriate defaults for the notification light, sound,
        // and vibration.   .defaults|= Notification.DEFAULT_VIBRATE;
        builder .setDefaults(Notification.DEFAULT_VIBRATE)

                // Set required fields, including the small icon, the
                // notification title, and text.
                .setSmallIcon(R.drawable.pukar)


                // All fields below this line are optional.

                // Use a default priority (recognized on devices running Android
                // 4.1 or later)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

                // Provide a large icon, shown with the notification in the
                // notification drawer on devices running Android 3.0 or later.
                .setLargeIcon(picture)

                // Set ticker text (preview) information for this notification.
                .setTicker(ticker)

                // Show a number. This is useful when stacking notifications of
                // a single type.
                .setNumber(number)

                // If this notification relates to a past or upcoming event, you
                // should set the relevant time information using the setWhen
                // method below. If this call is omitted, the notification's
                // timestamp will by set to the time at which it was shown.
                // TODO: Call setWhen if this notification relates to a past or
                // upcoming event. The sole argument to this method should be
                // the notification timestamp in milliseconds.
                //.setWhen(...)

                // Set the pending intent to be initiated when the user touches
                // the notification.
                .setContentIntent(pintent);

                // Show expanded text content on devices running Android 4.1 or
                // later.
        if(  newtitle.equalsIgnoreCase("??????????????? ?????????????????? - ????????? ???-")
                || newtitle.equalsIgnoreCase("??????????????? ?????????????????? - ????????? ???")
        )
        {
            builder.setStyle(new NotificationCompat.BigTextStyle()
                    .bigText("??????????????? ??????????????????")
                    .setBigContentTitle("??????????????? ??????????????????")
                    .setSummaryText(""))
                    .setAutoCancel(true);

        }
        else
        {
            builder.setStyle(new NotificationCompat.BigTextStyle()
                    .bigText(text)
                    .setBigContentTitle(title)
                    .setSummaryText(details))
                    .setAutoCancel(true);
        }

              //  .setSound(Uri.parse("android.resource://"+context.getPackageName()+"/" + R.raw.textnotifi));


        // Example additional actions for this notification. These will
        // only show on devices running Android 4.1 or later, so you
        // should ensure that the activity in this notification's
        // content intent provides access to the same actions in
        // another way.
        if(  newtitle.equalsIgnoreCase("??????????????? ?????????????????? - ????????? ???-")
                || newtitle.equalsIgnoreCase("??????????????? ?????????????????? - ????????? ???")
        ) {
            builder .setContentTitle("??????????????? ??????????????????");
            builder  .setContentText("??????????????? ??????????????????");

            try
            {
              Intent intentToOpen=  new Intent(context, MainActivity.class).
                        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
              intentToOpen.putExtra("IsPlaying",true);

                PendingIntent contentIntent = PendingIntent.getActivity(context, 1,
                        intentToOpen  ,
                        PendingIntent.FLAG_CANCEL_CURRENT);
                builder.setContentIntent(contentIntent);
            }
            catch (Exception ex)
            {}
        }
        else
        {
            builder  .setContentTitle(title)
                    .setContentText(text);
        }
        //notify(context, builder.build(),number);
        if(  newtitle.equalsIgnoreCase("??????????????? ?????????????????? - ????????? ???-")
                || newtitle.equalsIgnoreCase("??????????????? ?????????????????? - ????????? ???")
        )
        {
            builder.addAction(R.drawable.pause, "????????????",
                    pausePendingIntent);
            builder.addAction(R.drawable.play, "???????????? ?????????",
                    playPendingIntent);


        }
        try
        {
            Intent deleteIntent = new Intent();
            deleteIntent.setAction("Delete Stuti");
            PendingIntent deletePendingIntent =
                    PendingIntent.getBroadcast(context, number, deleteIntent, 0);
            builder.setDeleteIntent(deletePendingIntent);
        }
        catch (Exception ex){}
        notify(context, builder.build(),number,CHANNEL_ID,name,importance);
    }

    @TargetApi(Build.VERSION_CODES.ECLAIR)
    private static void notify(final Context context, final Notification notification,int number,String CHANNEL_ID,CharSequence  name,int importance)
    {
        final NotificationManager nm = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR)
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            {
                NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
                // builder.setChannelId(CHANNEL_ID);
                nm.createNotificationChannel(mChannel);
            }
            nm.notify(NOTIFICATION_TAG, number, notification);

        } else {
            nm.notify(number, notification);
        }
    }



    /*
     * Cancels any notifications of this type previously shown using
     * {@link #notify(Context, String, int,long)}.
     */

    @TargetApi(Build.VERSION_CODES.ECLAIR)
    public static void cancel(final Context context) {
        final NotificationManager nm = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
            nm.cancel(NOTIFICATION_TAG, 0);
        } else {
            nm.cancel(NOTIFICATION_TAG.hashCode());
        }
    }
}
