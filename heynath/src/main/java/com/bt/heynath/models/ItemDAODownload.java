package com.bt.heynath.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by bt18 on 03/18/2017.
 */

public class ItemDAODownload
{
    Context context;
    public ItemDAODownload(Context context)
    {
        this.context=context;
    }


    public long insertRecord(DownloadBean bean)
    {

        SQLiteDatabase db=new NewDBHELPER(context).getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("DownloadId",bean. DownloadId);
        cv.put("Url", bean.Url);
        cv.put("SavedPath",bean.SavedPath);
        cv.put("Status",bean.Status);
        try
        {
          db.delete("Download","DownloadId="+bean.DownloadId,null);
        }
        catch (Exception e)
        {}
        long ln=db.insert("Download", null, cv);
        Log.d("DService","DB insert-"+ln);
        db.close();
        return ln;
    }

    public long updateRecord(DownloadBean bean)
    {

        SQLiteDatabase db=new NewDBHELPER(context).getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("SavedPath",bean.SavedPath);
        cv.put("Status",bean.Status);
      //  long ln=db.update("Download", null, cv);
        long ln=db.update("Download",cv,"DownloadId="+bean.DownloadId,null);
        Log.d("DService","DB Update "+ln);
        db.close();
        return ln;
    }

    public ArrayList<DownloadBean> getRecord()
    {
        ArrayList<DownloadBean> list=new ArrayList<DownloadBean>();
        //list.add(new AppMenuBean(666, 1	, 6, "", "", "", "", "", "", "User"));
        SQLiteDatabase db=new NewDBHELPER(context).getWritableDatabase();
        Cursor c=   db.rawQuery("SELECT * FROM Download", null);
        if(c!=null)
        {
            if(c.moveToFirst())
            {

                int i=0;
                while(i<c.getCount())
                {
					        /*
					        AppMenuBean bean=new AppMenuBean(c.getInt(c.getColumnIndex("MenuId")), c.getInt(c.getColumnIndex("SequenceNo")), c.getInt(c.getColumnIndex("InstituteId")),
							c.getString(c.getColumnIndex("MenuIcon")), c.getString(c.getColumnIndex("MenuName")),
							c.getString(c.getColumnIndex("MenuType")), c.getString(c.getColumnIndex("DynamicPagePath")),
							c.getString(c.getColumnIndex("TextDetail")),c.getString(c.getColumnIndex("ImagePath")) ,
							c.getString(c.getColumnIndex("ModuleName")));
							*/
                     DownloadBean bean=new DownloadBean();
                    bean.setDownloadId( c.getLong(c.getColumnIndex("DownloadId")));
                    bean.setUrl(c.getString(c.getColumnIndex("Url")));
                    bean.setSavedPath(c.getString(c.getColumnIndex("SavedPath")));
                    bean.setStatus(c.getString(c.getColumnIndex("Status")));



                    c.moveToNext();
                    i++;
                }
            }
        }
        db.close();
        return list;
    }


    public DownloadBean getRecordForUrl(String Url)
    {
        ArrayList<DownloadBean> list=new ArrayList<DownloadBean>();
        //list.add(new AppMenuBean(666, 1	, 6, "", "", "", "", "", "", "User"));
        SQLiteDatabase db=new NewDBHELPER(context).getWritableDatabase();
        Cursor c=   db.rawQuery("SELECT * FROM Download where Url='"+Url+"' and Status='success'", null);
        if(c!=null)
        {
            if(c.moveToFirst())
            {

                int i=0;
                while(i<c.getCount())
                {
					        /*
					        AppMenuBean bean=new AppMenuBean(c.getInt(c.getColumnIndex("MenuId")), c.getInt(c.getColumnIndex("SequenceNo")), c.getInt(c.getColumnIndex("InstituteId")),
							c.getString(c.getColumnIndex("MenuIcon")), c.getString(c.getColumnIndex("MenuName")),
							c.getString(c.getColumnIndex("MenuType")), c.getString(c.getColumnIndex("DynamicPagePath")),
							c.getString(c.getColumnIndex("TextDetail")),c.getString(c.getColumnIndex("ImagePath")) ,
							c.getString(c.getColumnIndex("ModuleName")));
							*/
                    DownloadBean bean=new DownloadBean();
                    bean.setDownloadId( c.getLong(c.getColumnIndex("DownloadId")));
                    bean.setUrl(c.getString(c.getColumnIndex("Url")));
                    bean.setSavedPath(c.getString(c.getColumnIndex("SavedPath")));
                    bean.setStatus(c.getString(c.getColumnIndex("Status")));

                    db.close();

                    try
                    {
                        if(Build.VERSION.SDK_INT>=24)
                        {
                           // File mFile = new File(Uri.parse(bean.getSavedPath()).getPath());

                        }


                        File file=new File(bean.getSavedPath());

                        if(file!=null)
                        {
                            if(file.exists())
                            {
                                if(file.length()>8)
                                    db.close();
                                    return  bean;
                            }
                        }
                    }
                    catch (Exception e)
                    {

                    }


                    return   bean;


                }
            }
        }
        db.close();


        return null;
    }

    public DownloadBean getRecordForDownloadId(long id)
    {
        ArrayList<DownloadBean> list=new ArrayList<DownloadBean>();
        //list.add(new AppMenuBean(666, 1	, 6, "", "", "", "", "", "", "User"));
        SQLiteDatabase db=new NewDBHELPER(context).getWritableDatabase();
        Cursor c=   db.rawQuery("SELECT * FROM Download where DownloadId="+id+" ", null);
        if(c!=null)
        {
            if(c.moveToFirst())
            {

                int i=0;
                while(i<c.getCount())
                {
					        /*
					        AppMenuBean bean=new AppMenuBean(c.getInt(c.getColumnIndex("MenuId")), c.getInt(c.getColumnIndex("SequenceNo")), c.getInt(c.getColumnIndex("InstituteId")),
							c.getString(c.getColumnIndex("MenuIcon")), c.getString(c.getColumnIndex("MenuName")),
							c.getString(c.getColumnIndex("MenuType")), c.getString(c.getColumnIndex("DynamicPagePath")),
							c.getString(c.getColumnIndex("TextDetail")),c.getString(c.getColumnIndex("ImagePath")) ,
							c.getString(c.getColumnIndex("ModuleName")));
							*/
                    DownloadBean bean=new DownloadBean();
                    bean.setDownloadId( c.getLong(c.getColumnIndex("DownloadId")));
                    bean.setUrl(c.getString(c.getColumnIndex("Url")));
                    bean.setSavedPath(c.getString(c.getColumnIndex("SavedPath")));
                    bean.setStatus(c.getString(c.getColumnIndex("Status")));

                    db.close();

                    try
                    {
                        if(Build.VERSION.SDK_INT>=24)
                        {
                            // File mFile = new File(Uri.parse(bean.getSavedPath()).getPath());

                        }


                        File file=new File(bean.getSavedPath());
                        if(file!=null)
                        {
                            if(file.exists())
                            {
                                if(file.length()>8)
                                    db.close();
                                return  bean;
                            }
                        }
                    }
                    catch (Exception e)
                    {

                    }


                    return   bean;


                }
            }
        }
        db.close();


        return null;
    }


    public void deleteRecord() {
        // TODO Auto-generated method stub
        SQLiteDatabase db=new NewDBHELPER(context).getWritableDatabase();
        int rows=db.delete("Download", null, null);
        Log.d("Absent Remove Rows:", rows+"");
        db.close();
    }
    public void deleteRecord(long Id) {
        // TODO Auto-generated method stub
        SQLiteDatabase db=new NewDBHELPER(context).getWritableDatabase();
        int rows=db.delete("Download", "DownloadId="+Id, null);
        Log.d("Absent Remove Rows:", rows+"");
        db.close();
    }
}
