package com.bt.heynath.reciever;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Calendar;

public class AlramUtility
{

    public static Calendar lastPalytime=null;
    public static  boolean isToPlay ()
    {

        Calendar calendar= Calendar.getInstance();
        Calendar startCalendar= Calendar.getInstance();

        startCalendar.set(Calendar.HOUR_OF_DAY,4);
        startCalendar.set(Calendar.MINUTE,54);
        Calendar endCalendar= Calendar.getInstance();

        endCalendar.set(Calendar.HOUR_OF_DAY,6);
        endCalendar.set(Calendar.MINUTE,10);

        if( calendar.after(startCalendar)  && calendar.before(endCalendar)  )
        {

        }
        else
        {
            return  false;
        }

        if(lastPalytime==null)
        {
            return  true;
        }
        long l=System.currentTimeMillis()- lastPalytime.getTimeInMillis();

        if(l>1000*60*60)
        {
            return  true;
        }



        return false;
    }
    public static void   updateMorningTime()
    {
        lastPalytime=Calendar.getInstance();
    }
   static public long getIntervalTime(Context context)
    {
        SharedPreferences sh=context.getSharedPreferences("Scheduler",context.MODE_PRIVATE);
        int intVal=  sh.getInt("TimeInteval",10);

        return  (intVal*60000);
    }
    static public long getIntervalTimeInMinute(Context context)
    {
        SharedPreferences sh=context.getSharedPreferences("Scheduler",context.MODE_PRIVATE);
        int intVal=  sh.getInt("TimeInteval",5);

        return  (intVal);
    }
    static   public String getFromTime(Context context)
    {
        SharedPreferences sh=context.getSharedPreferences("Scheduler",context.MODE_PRIVATE);
        String hh= sh.getString("FromTime","8:0");
        return  hh;
    }
  static   public int getFromTimeHours(Context context)
    {
        SharedPreferences sh=context.getSharedPreferences("Scheduler",context.MODE_PRIVATE);
        String hh= sh.getString("FromTime","8:0").split(":")[0];
        return  Integer.parseInt(hh);
    }
  static   public int getFromTimeMinute(Context context)
    {
        SharedPreferences sh=context.getSharedPreferences("Scheduler",context.MODE_PRIVATE);
        String hh= sh.getString("FromTime","8:0").split(":")[1];
        return  Integer.parseInt(hh);
    }

    static   public String getToTime(Context context)
    {
        SharedPreferences sh=context.getSharedPreferences("Scheduler",context.MODE_PRIVATE);
        String hh= sh.getString("ToTime","20:0");
        return hh;
    }
  static   public int getToTimeHours(Context context)
    {
        SharedPreferences sh=context.getSharedPreferences("Scheduler",context.MODE_PRIVATE);
        String hh= sh.getString("ToTime","20:0").split(":")[0];
        return  Integer.parseInt(hh);
    }
   static public int getToTimeMinute(Context context)
    {
        SharedPreferences sh=context.getSharedPreferences("Scheduler",context.MODE_PRIVATE);
        String hh= sh.getString("ToTime","20:0").split(":")[1];
        return  Integer.parseInt(hh);
    }
    static public boolean isMute(Context context)
    {
        SharedPreferences sh=context.getSharedPreferences("Scheduler",context.MODE_PRIVATE);
        boolean hh= sh.getBoolean("Mute",false);
        return  hh;
    }
    static public boolean isStart(Context context)
    {
        SharedPreferences sh=context.getSharedPreferences("Scheduler",context.MODE_PRIVATE);
        boolean hh= sh.getBoolean("Start",true);
        return  hh;
    }
    static public boolean isNityaSuchiStart(Context context)
    {
        SharedPreferences sh=context.getSharedPreferences("Scheduler",context.MODE_PRIVATE);
        boolean hh= sh.getBoolean("NityaSuchi",false);
        return  hh;
    }

}
