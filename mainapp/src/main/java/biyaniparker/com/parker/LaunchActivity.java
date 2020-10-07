package biyaniparker.com.parker;

import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Environment;
import android.os.Handler;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;


import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.internal.service.Common;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import java.io.File;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import biyaniparker.com.parker.fcm.FcmUtility;
import biyaniparker.com.parker.services.ProductSyncService;
import biyaniparker.com.parker.utilities.CommonUtilities;
import biyaniparker.com.parker.utilities.DownloadUtility;
import biyaniparker.com.parker.utilities.UserUtilities;
import biyaniparker.com.parker.utilities.serverutilities.AsyncUtilities;
import biyaniparker.com.parker.utilities.serverutilities.ConnectionDetector;
import biyaniparker.com.parker.view.homeadmin.AdminHomeScreen;
import biyaniparker.com.parker.view.homeuser.UserHomeScreen;
import biyaniparker.com.parker.view.login.LoginActivity;
import biyaniparker.com.parker.view.login.SyncActivity;
import biyaniparker.com.parker.view.unitmaster.SharedPreference;

public class LaunchActivity extends AppCompatActivity implements DownloadUtility {

    Handler h;
    ImageView imageView3;
    ImageView imageView2;
    public static  String appName;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        if(getString(R.string.app_name).contains("Choice"))
        {
           setContentView(R.layout.activity_launch_choice);
        }
        if(getString(R.string.app_name).contains("Rajashsree"))
        {
            setContentView(R.layout.activity_launch_choice);
        }
        imageView2=findViewById(R.id.imageView2);
        doption = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.rajashri)
                .showImageOnFail(R.drawable.rajashri)
                .showStubImage(R.drawable.rajashri).cacheInMemory(true)
                .cacheOnDisc(true).displayer(new RoundedBitmapDisplayer(5)) // 100
                        // for
                        // Rounded
                        // Image
                .cacheOnDisc(true)
                        //.imageScaleType(10)
                .build();

       // getUnitMasterList();

        imageView3=(ImageView)findViewById(R.id.imageView3);
        animateFirstListener=new AnimateFirstDisplayListener();

        imageLoader = ImageLoader.getInstance();
        //  ImageLoaderConfiguration.//408, 306, CompressFormat.JPEG, 75, null);
        imageLoader.displayImage(CommonUtilities.URL+"l1.png"

                ,
                imageView3, doption, animateFirstListener);


        h=new Handler();


        if(new ConnectionDetector(this).isConnectingToInternet())
        {
            h.postDelayed(r,2000);
        }
        else
        {
            if(UserUtilities.getUserId(LaunchActivity.this)==0)
            {
                AlertDialog.Builder app = new AlertDialog.Builder(this);
                app.setTitle(getString(R.string.app_name));
                app.setMessage("Check Internet Connection ");
                app.setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        startActivity(new Intent(LaunchActivity.this, LaunchActivity.class));
                        overridePendingTransition(R.animator.pull_in_right, R.animator.push_out_left);
                    }
                });
                app.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                app.show();
            }
            else
            {
                AlertDialog.Builder app = new AlertDialog.Builder(this);
                app.setTitle(getString(R.string.app_name));
                app.setMessage("\nCheck Internet Connection\nDo you want to work  offline?");
                app.setPositiveButton("Work Offline", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // finish();
                        //  startActivity(new Intent(LaunchActivity.this, LaunchActivity.class));
                        h.postDelayed(r, 1000);
                    }
                });
                app.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
              //  app.show();
                h.postDelayed(r, 1000);
            }
        }



        //    Run Service To Sync Data
        if(UserUtilities.getClientId(this)!=0)
        {
         //   startService(new Intent(this, SyncServiceBg.class));
        }



        String root = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES).toString();


        File myDir = new File(root+"/parkerreport" );
        myDir.mkdirs();

        try
        {
            FcmUtility fcmUtility = new FcmUtility();
            fcmUtility.callProcedure(this);
        }
        catch (Exception ex){
            CommonUtilities.alert(this,ex.toString());
        }

        appName=getString(R.string.app_name);


        getSupportActionBar().hide();

        try {
            new FcmUtility().callProcedure(this);
        }
        catch (Exception ex){}
    }

    private void getUnitMasterList() {
        AsyncUtilities serverAsync=new AsyncUtilities(LaunchActivity.this,false, CommonUtilities.URL+"ProductService.svc/GetUnitMaster","",1,this);
        serverAsync.execute();
    }

    Runnable r=new Runnable()
    {
        @Override
        public void run()
        {
            SharedPreferences sh=getSharedPreferences("Sync",MODE_PRIVATE);
            if(!UserUtilities.isVerified(LaunchActivity.this) ||UserUtilities.getDeleteStatus(LaunchActivity.this))
            {
                CommonUtilities.deleteAll(LaunchActivity.this);
                finish();
                startActivity(new Intent(LaunchActivity.this, LoginActivity.class));
                overridePendingTransition(R.animator.pull_in_right, R.animator.push_out_left);
            }
            else if(UserUtilities.getUserId(LaunchActivity.this)<1  )
            {

                CommonUtilities.deleteAll(LaunchActivity.this);
                finish();
                startActivity(new Intent(LaunchActivity.this, LoginActivity.class));
                overridePendingTransition(R.animator.pull_in_right, R.animator.push_out_left);
            }
            else if(UserUtilities.getUserType(LaunchActivity.this).equalsIgnoreCase("Admin"))
            {
                if(new ConnectionDetector(LaunchActivity.this).isConnectingToInternet())
                {
                    final Intent intent=new Intent(getApplicationContext(),ProductSyncService.class);
                     //startActivity(new Intent(LaunchActivity.this, SyncActivity.class));
                   startActivity(new Intent(LaunchActivity.this, AdminHomeScreen.class));
                    overridePendingTransition(R.animator.pull_in_right, R.animator.push_out_left);

                    finish();
                    startService(intent);
                }
                else
                {
                    finish();
                    startActivity(new Intent(LaunchActivity.this, AdminHomeScreen.class));
                    overridePendingTransition(R.animator.pull_in_right, R.animator.push_out_left);
                }

               // startActivity(new Intent(LaunchActivity.this, AdminHomeScreen.class));
                /*if(  !sh.getBoolean("Sync",false))
                {

                    startActivity(new Intent(LaunchActivity.this, SyncActivity.class));
                    overridePendingTransition(R.animator.pull_in_right, R.animator.push_out_left);
                }
                else
                {
                    startActivity(new Intent(LaunchActivity.this, AdminHomeScreen.class));
                    overridePendingTransition(R.animator.pull_in_right, R.animator.push_out_left);
                }*/
            }
            else
            {
                overridePendingTransition(R.animator.pull_in_right, R.animator.push_out_left);
                finish();
                // startActivity(new Intent(LaunchActivity.this, UserHomeScreen.class));
                if(  !sh.getBoolean("Sync",false))
                {
                    startActivity(new Intent(LaunchActivity.this, SyncActivity.class));

                    overridePendingTransition(R.animator.pull_in_right, R.animator.push_out_left);
                }
                else
                {
                    startActivity(new Intent(LaunchActivity.this, UserHomeScreen.class));
                    overridePendingTransition(R.animator.pull_in_right, R.animator.push_out_left);
                }
            }

        }
    };













    //*********************Copy This  ******************
    DisplayImageOptions doption = null;
    private AnimateFirstDisplayListener animateFirstListener;
    private ImageLoader imageLoader;

    @Override
    public void onComplete(String str, int requestCode, int responseCode) {
        if (requestCode==1){
            if (responseCode == 200){
                try {
                    SharedPreference sharedPreference = new SharedPreference(this);
                    sharedPreference.setStr("Response",str);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    //*************************************************


    private  class AnimateFirstDisplayListener extends
            SimpleImageLoadingListener {
        final List<String> displayedImages = Collections
                .synchronizedList(new LinkedList<String>());

        @Override
        public void onLoadingComplete(String imageUri, View view,
                                      Bitmap loadedImage) {
            if (loadedImage != null)
            {
                ImageView imageView = (ImageView) view;
                boolean firstDisplay = !displayedImages.contains(imageUri);
                if (firstDisplay) {
                    FadeInBitmapDisplayer.animate(imageView, 500);
                    displayedImages.add(imageUri);
                }
            }
        }
    }
}
