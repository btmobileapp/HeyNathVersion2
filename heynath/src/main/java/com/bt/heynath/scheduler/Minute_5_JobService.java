package com.bt.heynath.scheduler;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.ListenableWorker;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.bt.heynath.reciever.AlramUtility;
import com.bt.heynath.reciever.BootReciever;
import com.bt.heynath.reciever.JobAlarmService;

import java.util.concurrent.TimeUnit;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class Minute_5_JobService extends JobService
{

    private PeriodicWorkRequest periodicMorningWorkRequest;
    Context context;

    @Override
    public boolean onStartJob(JobParameters params)
    {
        context = this;

        if( AlramUtility.isNityaSuchiStart(context))
        {
            Log.d("Heynath","Inside Condition");
            PeriodicWorkRequest unused1 =this.periodicMorningWorkRequest = (PeriodicWorkRequest) new PeriodicWorkRequest.Builder((Class<? extends ListenableWorker>) MyMorningWorker.class, 1000*60*15,  TimeUnit.MILLISECONDS).build();
            WorkManager.getInstance().enqueueUniquePeriodicWork("My Morning Work5", ExistingPeriodicWorkPolicy.KEEP, this.periodicMorningWorkRequest);
        }
        Intent mIntent = new Intent(context, JobAlarmService.class);
        JobAlarmService.enqueueWork(context, mIntent);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}
