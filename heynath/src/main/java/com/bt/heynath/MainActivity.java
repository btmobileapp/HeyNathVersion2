package com.bt.heynath;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.bt.heynath.shreemukhi.ShreeMukhiSubmenu;
import com.judemanutd.autostarter.AutoStartPermissionHelper;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{

    private GridView gv;

    ArrayList<String>  menus=new ArrayList<>();

    @Override
    protected void onResume() {
        super.onResume();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // Toolbar toolbar=findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);
       // getSupportActionBar().setTitle("|| श्री हरि: ||");
       // getSupportActionBar().setSubtitle(getString(R.string.app_name));
       // getSupportActionBar().setIcon(R.drawable.ic);
       // getSupportActionBar().hide();
        //getSupportActionBar()
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
      //  TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
      //  mTitle.setText("|| श्री हरि: ||");
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //getSupportActionBar().setSubtitle(getString(R.string.app_name));


        gv = (GridView) findViewById(R.id.gridview);
      /*  menus.add("लोक कल्याणार्थ पधारे महापरुषों का पररचय");
        menus.add("महापुरुषों के वचनामृत विध्यार्तीयो लिए  (सत्संग)");
        menus.add("भगवान् के श्रीमुखी वाणी श्रीमद्भगिद्गीता");

        menus.add("नित्य स्तुति");
        menus.add("नाम जप");
        menus.add("भजन और प्रार्थना");
        menus.add("गीता बाल संस्कार शिविर");

        menus.add("भजन और प्रार्थना");
        menus.add("गीता बाल संस्कार शिविर");
        menus.add("सनातन संस्कृति की रक्षक गीता प्रेस (परिचय )");
        menus.add("संतो की  विशेष कृपा से जोधपरु नगर में आयोजित आध्यात्मिक कार्यक्रम");
        menus.add("संतो की कृपा से चल रहा विशेष प्रकल्प");
        menus.add("नोटिफिकेशन ");
        //
        //सनातन  संस्कृति की रक्षक गीता प्रेस (परिचय )
        */

          menus.add("नित्य स्तुति");
          menus.add("है नाथ की पुकार");
          menus.add("भगवान के श्रीमुखकी वाणी श्रीमद्भगवद् गीता");
       // menus.add("Play");
       // menus.add("Pause");
       // menus.add("गीता अध्याय");


        try
        {
            boolean isPermissionAvaialbe=  AutoStartPermissionHelper.getInstance().isAutoStartPermissionAvailable(this)  ;
            if(isPermissionAvaialbe)
            {
               // menus.add("ऑटोस्टार्ट की जाँच करें");
            }
        }
        catch (Exception ex){}

        /*
        menus.add("अधयाय १");
        menus.add("अधयाय २");
        menus.add("अधयाय ३");
        menus.add("अधयाय ४");
        menus.add("अधयाय ५");
        menus.add("अधयाय ६");*/



        MenuAdapter adapter=new MenuAdapter(this,menus);
        gv.setAdapter(adapter);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0)
                {
                     startActivity(new Intent(MainActivity.this,NityaStustiSubmenu.class));
                   // startActivity(new Intent(MainActivity.this,NityaStuti.class));
                }
                else if(i==1)
                {
                    startActivity(new Intent(MainActivity.this,NathKiPukarSubmenu.class));
                }
                else if(i==2)
                {
                  //  startActivity(new Intent(MainActivity.this,  AdhayList.class));
                   startActivity(new Intent(MainActivity.this,  ShreeMukhiSubmenu.class));
                }
                else if(i==3)
                {
                 //   filter.addAction("Pause Stuti");
                //    filter.addAction("Play Stuti");
                   Intent intent=new Intent("Play Stuti");
                   sendBroadcast(intent);
                }
                else if(i==4)
                {
                    Intent intent=new Intent("Pause Stuti");
                    sendBroadcast(intent);
                }
                else if(menus.get(i).equalsIgnoreCase("ऑटोस्टार्ट की जाँच करें"))
                {
                   // startActivity(new Intent(MainActivity.this,  AdhayList.class));
                    //                   // openAutoStart();
                }
            }
        });


        findViewById(R.id.dots).setOnClickListener(new View.OnClickListener()
          {
            @Override
            public void onClick(View view) {
                try
                {
                    alertMenu();
               }
                catch (Exception ex){}
          }
              });

    }
    void alertMenu()
    {
        ArrayList<String> mList=new ArrayList<>();
        mList.add("लॉग इन करें");
        boolean isPermissionAvaialbe=  AutoStartPermissionHelper.getInstance().isAutoStartPermissionAvailable(this)  ;
        if(isPermissionAvaialbe)
        {
            mList.add("ऑटो स्टार्ट");
        }
        AlertDialog.Builder alBuilder=new AlertDialog.Builder(this);
        alBuilder.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, mList), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if(i==0)
                {
                    startActivity(new Intent(MainActivity.this,LoginActivity.class));
                }
                else
                {
                    openAutoStart();
                }
                dialogInterface.cancel();
            }
        });
        alBuilder.show();
    }
    Bitmap bt=null;
    void loadImage()
    {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    bt=  getBitmap("");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public Bitmap getBitmap(String Url) throws Exception
    {
        URL url1 = null;
        url1 = new URL(Url);
        BitmapFactory.Options bfo = new BitmapFactory.Options();
        bfo.outWidth = 620;
        bfo.outHeight = 350;
        return BitmapFactory.decodeStream(url1.openConnection()
                .getInputStream(), null, bfo);
    }
    void openAutoStart()
    {
        try
        {
            boolean isPermissionAvaialbe=  AutoStartPermissionHelper.getInstance().isAutoStartPermissionAvailable(this)  ;
            AlertDialog.Builder alert=new AlertDialog.Builder(MainActivity.this);
            alert.setMessage(isPermissionAvaialbe+"");
            //alert.show();
            if(isPermissionAvaialbe)
                AutoStartPermissionHelper.getInstance().getAutoStartPermission(MainActivity.this);
        }
        catch (Exception ex)
        {

        }

    }


    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        try {
            boolean isPermissionAvaialbe=  AutoStartPermissionHelper.getInstance().isAutoStartPermissionAvailable(this)  ;
            if(isPermissionAvaialbe)
            {
              //  menus.add("ऑटोस्टार्ट की जाँच करें");
                getMenuInflater().inflate(R.menu.menu1,menu);
            }
            else
            {
                getMenuInflater().inflate(R.menu.menu2,menu);
            }
        }
        catch (Exception ex){}

        return super.onCreateOptionsMenu(menu);
    }

     */

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.action_settings)
        {
            startActivity(new Intent(this,LoginActivity.class));
        }
        if(item.getItemId()==R.id.action_autostart)
        {
            openAutoStart();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    // Shows the system bars by removing all the flags
// except for the ones that make the content appear under the system bars.
    private void showSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }





}
