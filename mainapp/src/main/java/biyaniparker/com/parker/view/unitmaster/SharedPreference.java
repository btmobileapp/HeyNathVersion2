package biyaniparker.com.parker.view.unitmaster;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreference {
    public  Context context;

    public SharedPreference(Context context){
        this.context = context;
    }

    public final static String PREFS_NAME = "appname_prefs";

    public  void setStr(String key, String value) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME,0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public  String getStr(String key) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        return prefs.getString(key,"DNF");
    }
}
