package com.bt.heynath;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper
{



    String leaveDetailsTable="create table Logs(LogToken text,DeviceId text,LogDate integer,LogMessage text," +
            "DeviceName text,HS1 text,HS2 text,HD text,HI1 text ,HI2 text,RecId integer,Type text)";

    // String complaint="create table Complaint(Token text primary key,ComplaintId integer,CustomerId integer,SerialNumber text,EquipmentName text,EquipmentType text,ModelName text,ModelId text,ComplaintType text,ComplaintDetails text,EnterDate integer,EnterBy text,IsBookedByClient text )";


    public DBHelper(@Nullable Context context) {
        super(context,"heynath", null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        sqLiteDatabase.execSQL(leaveDetailsTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {

    }
}
