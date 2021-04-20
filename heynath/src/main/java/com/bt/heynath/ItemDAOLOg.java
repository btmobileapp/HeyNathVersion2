package com.bt.heynath;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

public class ItemDAOLOg
{
    Context context;

    public ItemDAOLOg(Context context) {
        this.context = context;
    }

    public long insertRecord(ModelLogs bean) {

        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("DeviceName", bean.DeviceName);
        cv.put("HD", bean.HD);
        cv.put("HI1", bean.HI1);
        cv.put("HI2", bean.HI2);
        cv.put("HS1", bean.HS1);
        cv.put("HS2", bean.HS2);
        cv.put("LogDate", bean.LogDate);
        cv.put("LogMessage", bean.LogMessage);
        cv.put("LogToken", bean.LogToken);
        cv.put("Type", bean.Type);
        cv.put("RecId", 0);



       // cv.put("SelectedEquipment", bean.SelectedEquipment);

        long ln = db.insert("Logs", null, cv);
        Log.d("APG", "Record Inserted:-" + ln);
        db.close();
        return ln;
    }

    public int updateComplaintNo(int RecId,String Token)
    {
        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();


        ContentValues cv=new ContentValues();
        cv.put("RecId", RecId);

        int returnValue= db.update("Logs", cv,"LogToken='"+Token+"'",null);
        db.close();
       // Log.d("APG","StartPhotoUpdate");
        return returnValue ;
    }

    public ArrayList<ModelLogs> getNotSyncLogs() {
        ArrayList<ModelLogs> list = new ArrayList<>();
        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();


        Cursor c = db.rawQuery("SELECT * FROM Logs where RecId=0", null);
        if (c != null) {
            if (c.moveToFirst()) {

                int i = 0;
                while (i < c.getCount()) {
                    ModelLogs model = new ModelLogs();
                    model.LogDate = c.getLong(c.getColumnIndex("LogDate"));
                    model.HD = c.getString(c.getColumnIndex("HD"));
                    model.HI1 = c.getString(c.getColumnIndex("HI1"));
                    model.HI2 = c.getString(c.getColumnIndex("HI2"));
                    model.HS1 = c.getString(c.getColumnIndex("HS1"));
                    model.HS2 = c.getString(c.getColumnIndex("HS2"));
                    model.LogMessage = c.getString(c.getColumnIndex("LogMessage"));
                    model.LogToken = c.getString(c.getColumnIndex("LogToken"));
                    model.Type=c.getString(c.getColumnIndex("Type"));
                    model.RecId=c.getInt(c.getColumnIndex("RecId"));

                    list.add(model);
                    c.moveToNext();
                    i++;
                }
            }

        }
        return list;
    }
}
