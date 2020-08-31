package com.bt.heynath.reciever;

import android.content.Context;
import android.content.SharedPreferences;

public class AlramUtility
{

   static public long getIntervalTime(Context context)
    {
        SharedPreferences sh=context.getSharedPreferences("Scheduler",context.MODE_PRIVATE);
        int intVal=  sh.getInt("TimeInteval",30);

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


}
