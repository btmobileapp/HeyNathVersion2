package com.bt.heynath;

import android.os.Build;
import android.os.StrictMode;

import androidx.multidex.MultiDexApplication;

public class MyApplication extends MultiDexApplication {
    private boolean DEVELOPER_MODE=false;

    @Override
    public void onCreate() {
        if (DEVELOPER_MODE
                && Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectAll().penaltyDialog().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectAll().penaltyDeath().build());
        }

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        super.onCreate();
    }
}
