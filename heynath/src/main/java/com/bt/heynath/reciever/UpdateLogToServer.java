package com.bt.heynath.reciever;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.bt.heynath.ConnectServer;
import com.bt.heynath.ItemDAOLOg;
import com.bt.heynath.ModelLogs;
import com.bt.heynath.ResponseBody;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Console;
import java.util.ArrayList;

public class UpdateLogToServer
{
    public ArrayList<ModelLogs>  getNotSyncLog(Context context)
    {
        ItemDAOLOg    itemDAOLOg=new ItemDAOLOg(context);
        ArrayList<ModelLogs>  list= itemDAOLOg.getNotSyncLogs();
        return list;
    }

  public   void saveLogs(final Context context)
    {
        try
        {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        ArrayList<ModelLogs> list=getNotSyncLog(context);
                        for (ModelLogs log:list)
                        {

                            String url = "http://btwebservices.biyanitechnologies.com/galaxyservices/Galaxy1.svc/HeynathLog";
                            Gson gson=new Gson();
                             try
                             {
                                 String json=     gson.toJson(log);
                                 ResponseBody res=   new ConnectServer().performPostCallJson(url,json);
                                 JSONArray ja=new JSONArray(res.getResponseString());
                                 JSONObject j=ja.getJSONObject(0);
                                 if(j.getInt("RecId")>0)
                                 {
                                     ItemDAOLOg itemDAOLOg=new ItemDAOLOg(context);
                                     itemDAOLOg.updateComplaintNo(j.getInt("RecId"),log.LogToken);
                                 }
                             }
                             catch (Exception rx){
                                 Log.d("rx",rx.toString());
                             }

                        }
                    }
                    catch (Exception rc){

                        Log.d("Errr",rc.toString());
                    }
                }
            }).start();


        }
        catch (Exception ex)
        {
        }
    }

}
