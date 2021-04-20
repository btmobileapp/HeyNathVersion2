package com.bt.heynath.models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by bt18 on 10/17/2016.
 */
public class NewDBHELPER extends SQLiteOpenHelper
{





    //String timeTable=" Create table TimeTable ()";


    String Download="Create table Download " +
            "(DownloadId Integer Primary key, " +
            "Url Text, " +
            "SavedPath Text, " +
            "Status Text)";







    public NewDBHELPER(Context context)
    {

        super(context,"newGalaxy", null, 9);
        // TODO Auto-generated constructor stub
    }


    @Override
    public void onCreate(SQLiteDatabase db)
    {

        db.execSQL(Download);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
