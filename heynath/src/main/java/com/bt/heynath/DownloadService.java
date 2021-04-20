package com.bt.heynath;

import android.app.DownloadManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.bt.heynath.models.DownloadBean;
import com.bt.heynath.models.ItemDAODownload;

import org.json.JSONArray;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class DownloadService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("DService","Call1");

        return null;
    }
    @Override
    public void onStart(final Intent intent, final int startId) {
        super.onStart(intent, startId);
        //startDownload();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startDownload();
      //  return super.onStartCommand(intent, flags, startId);
        return 1;
    }

    void startDownload()
    {
           try
           {
               Log.d("DService","Call2");
               String s=  getSharedPreferences("links",MODE_PRIVATE).getString("linksStr","");
               Log.d("DService",s);
               JSONArray array=new JSONArray(s);
               for(int i=0;i<array.length();i++)
               {
                   Log.d("DService","loop "+i);
                   downloadAdhay(array.getJSONObject(i).getString("Link"),array.getJSONObject(i).getString("Titile"));
               }

           }
           catch (Exception ex)
           {}
    }


    private DownloadManager dm;

    void downloadAdhay(String Url,String title)
    {
        try {
            Log.d("DService","Download Call-"+Url);
            long enqueue = 0;
            try {
                registerReciever();
                Log.d("DService","Brodcast Registered");
            }
            catch (Exception ex){ Log.d("DService","Brodcast Excecption "+ex.toString());}
            dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(Url)); // "http://www.vogella.de/img/lars/LarsVogelArticle7.png"));
            request.setTitle(title + "");
            request.setDescription("" + title);
            File dir =// Environment.getExternalStorageDirectory();
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            dir.mkdirs();
            Uri downloadLocation = Uri.fromFile(new File(dir, Url.split("/")[Url.split("/").length - 1]));
            request.setDestinationUri(downloadLocation);
            enqueue = dm.enqueue(request);
            //  maps.put(downloadAdhayId,enqueue);
            Log.d("DService","Enque Started-"+Url);

            DownloadBean downloadBean1 = new DownloadBean();
            downloadBean1.setDownloadId(enqueue);
            downloadBean1.setStatus("inrequest");
            downloadBean1.setUrl(Url);
            downloadBean1.setSavedPath("");
            Log.d("DService","Enque Id "+enqueue);

            ItemDAODownload itemDAODownload = new ItemDAODownload(this);
            Log.d("DService","Call Database ");
            itemDAODownload.insertRecord(downloadBean1);
        }
        catch (Exception ex)
        {
            Log.d("DService","Download exception "+ex.toString());
        }
    }

    BroadcastReceiver receiver;
    long iidd;
    public void registerReciever()
    {
        receiver = new BroadcastReceiver()
        {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d("DService","Download Complete ");
                String action = intent.getAction();
                try {
                    if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
                        long downloadId = intent.getLongExtra(
                                DownloadManager.EXTRA_DOWNLOAD_ID, 0);
                        iidd = downloadId;
                        ItemDAODownload itemDAODownload = new ItemDAODownload(DownloadService.this);

                        DownloadBean bean = itemDAODownload.getRecordForDownloadId(iidd);
                        if(bean==null)
                        Log.d("DService","bean isnull");
                        else
                        {
                            Log.d("DService","Bean is not null");
                        }
                        DownloadManager.Query query = new DownloadManager.Query();
                        query.setFilterById(downloadId);// enqueue);
                        Cursor c = dm.query(query);
                        ///DownloadManager.COLUMN_ID;

                        if (c.moveToFirst()) {
                            int columnIndex = c
                                    .getColumnIndex(DownloadManager.COLUMN_STATUS);
                            if (DownloadManager.STATUS_SUCCESSFUL == c
                                    .getInt(columnIndex)) {

                                String uriString = c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));


                                Log.d("Heynath-", uriString);
                                Log.d("DService", "File Path-" + uriString);
                                //Toast.makeText(getApplicationContext(),"DownLoad Complete", Toast.LENGTH_LONG).show();
                                bean.setSavedPath(uriString);
                                bean.setStatus("success");
                                itemDAODownload.updateRecord(bean);
                                try {
                                    String filePath = c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME));
                                    Log.d("Heynath-", filePath);
                                } catch (Exception ex) {
                                }


                            }
                        }
                    }
                }
                catch (Exception ex)
                {
                    Log.d("DService","End Exception-"+ ex.toString());
                }
            }
        };

        registerReceiver(receiver, new IntentFilter(
                DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }
}
