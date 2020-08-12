package biyaniparker.com.parker.view.homeuser;

import android.Manifest;
import android.animation.ArgbEvaluator;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import biyaniparker.com.parker.LaunchActivity;
import biyaniparker.com.parker.R;
import biyaniparker.com.parker.bal.ModuleLogin;
import biyaniparker.com.parker.bal.ModuleSync;
import biyaniparker.com.parker.bal.ModuleUserHomeScreen;
import biyaniparker.com.parker.beans.CreateNoticeBean;
import biyaniparker.com.parker.beans.RowItem;
import biyaniparker.com.parker.utilities.CommonUtilities;
import biyaniparker.com.parker.utilities.Constants;
import biyaniparker.com.parker.utilities.DownloadUtility;
import biyaniparker.com.parker.utilities.NotifyCallback;
import biyaniparker.com.parker.utilities.UserUtilities;
import biyaniparker.com.parker.utilities.serverutilities.AsyncUtilities;
import biyaniparker.com.parker.utilities.serverutilities.ConnectionDetector;
import biyaniparker.com.parker.view.notice.NoticeListView;
import biyaniparker.com.parker.view.notice.NoticeView;
import biyaniparker.com.parker.view.adapter.CustomAdapter;
import biyaniparker.com.parker.view.adapter.NoticeAdapter;
import biyaniparker.com.parker.view.adapter.ProductRandomAdapter;
import biyaniparker.com.parker.view.homeuser.dispatch.UserDispatchListView;
import biyaniparker.com.parker.view.homeuser.productdshopping.DynamicCategories;
import biyaniparker.com.parker.view.homeuser.productdshopping.ProductDetailView;
import biyaniparker.com.parker.view.homeuser.productdshopping.ViewProductImage;
import biyaniparker.com.parker.view.homeuser.userbag.UserBagView;
import biyaniparker.com.parker.view.homeuser.userorders.OrderListView;
import biyaniparker.com.parker.view.unitmaster.SharedPreference;
import biyaniparker.com.parker.view.user.PasswordUpdateView;

public class UserHomeScreen extends AppCompatActivity implements AdapterView.OnItemClickListener,DownloadUtility, NotifyCallback, NoticeAdapter.ViewMoreCallBack
 {
     Button btnshirt,btntshirt,btnpants,btnaccessories,btn5,btn6,btn7,btn8,btn9;
     GridView gridView;
     ListView slider_list;
     ProductRandomAdapter productRandomAdapter;
     NavigationView navigationView;
     //---- Temp-----
     ModuleSync moduleSync;
     ViewPager viewPager;
     List<CreateNoticeBean> list;
     ModuleUserHomeScreen moduleUserHomeScreen;
     NoticeAdapter adapter;
     ArgbEvaluator argbEvaluator = new ArgbEvaluator();
     Integer[] colors = null;
     LinearLayout sliderDotspanel;
     private int dotscount;
     private ImageView[] dots;

     void callPermission()
     {
         ActivityCompat.requestPermissions(this,
                 new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.READ_PHONE_STATE}
                 ,1947 );
     }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home_screen);
        callPermission();
        moduleUserHomeScreen = new ModuleUserHomeScreen(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);


        inItUI();
      /*  productRandomAdapter=new ProductRandomAdapter(this,1,moduleUserHomeScreen.randomList);
        gridView.setAdapter(productRandomAdapter);*/

        productRandomAdapter = new ProductRandomAdapter(this, 1, moduleUserHomeScreen.randomList);
        gridView.setAdapter(productRandomAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Gson gson = new Gson();
                String myJson = gson.toJson(moduleUserHomeScreen.randomList.get(position));
                Intent intent = new Intent(UserHomeScreen.this, ProductDetailView.class);
                intent.putExtra("myjson", myJson);
                startActivity(intent);

            }
        });

        if (new ConnectionDetector(this).isConnectingToInternet()) {
            //moduleUserHomeScreen.loadRandomProduct();
            moduleUserHomeScreen.loadRandomProductWithNotify();
        } else {
            moduleUserHomeScreen.getOffLineRandomProducts();
            //  onComplete("",1,200);
        }


        getSupportActionBar().setTitle(getString(R.string.app_name));
        // getSupportActionBar().setSubtitle("The New You");
        getSupportActionBar().setSubtitle(CommonUtilities.Slogan);
        checkSDCardsWrite();

       // getNoticeList();
    }

     public void getNoticeList() {
         AsyncUtilities serverAsync=new AsyncUtilities(UserHomeScreen.this,false, CommonUtilities.URL+"ProductService.svc/GetNotice","",2,this);
         serverAsync.execute();
     }

     private void getNoticeOnView() {
         try {
             SharedPreference sharedPreference = new SharedPreference(getApplicationContext());
             String str = sharedPreference.getStr("NoticesResponse");

             JSONArray jsonArray = new JSONArray(str);

             CreateNoticeBean createNoticeBean = new CreateNoticeBean();
             JSONObject jsonObject = jsonArray.getJSONObject(0);
             createNoticeBean.setTitle(jsonObject.getString("Title"));
             createNoticeBean.setNoticeId(jsonObject.getInt("NoticeId"));
             createNoticeBean.setDescription(jsonObject.getString("Decription"));
//                         createNoticeBean.setDescription("Hi how r u?/Hi how r u?/Hi how r u?/Hi how r u?/Hi how r u?/Hi how r u?/Hi how r u?/Hi how r u?/Hi how r u?/Hi how r u?/Hi how r u?/Hi how r u?");
             createNoticeBean.setAttachment(jsonObject.getString("Attachment"));
             createNoticeBean.setViewMore("View");
             list.add(createNoticeBean);

             JSONObject jsonObject1 = jsonArray.getJSONObject(1);

             createNoticeBean = new CreateNoticeBean();
             createNoticeBean.setTitle(jsonObject1.getString("Title"));
             createNoticeBean.setNoticeId(jsonObject1.getInt("NoticeId"));
             createNoticeBean.setDescription(jsonObject1.getString("Decription"));
             createNoticeBean.setAttachment(jsonObject1.getString("Attachment"));
             createNoticeBean.setViewMore("View");
             list.add((createNoticeBean));

             JSONObject jsonObject2 = jsonArray.getJSONObject(2);

             createNoticeBean = new CreateNoticeBean();
             createNoticeBean.setTitle(jsonObject2.getString("Title"));
             createNoticeBean.setNoticeId(jsonObject2.getInt("NoticeId"));
             createNoticeBean.setDescription(jsonObject2.getString("Decription"));
             createNoticeBean.setAttachment(jsonObject2.getString("Attachment"));
             createNoticeBean.setViewMore("View");
             list.add((createNoticeBean));

             createNoticeBean = new CreateNoticeBean();
             createNoticeBean.setViewMore("View More");
             list.add((createNoticeBean));

         } catch (Exception e) {
             e.printStackTrace();
         }
         adapter = new NoticeAdapter(list,getApplicationContext(),UserHomeScreen.this);
         viewPager.setAdapter(adapter);

         dotscount = adapter.getCount();
         dots = new ImageView[dotscount];

         for(int i = 0; i < dotscount; i++){

             dots[i] = new ImageView(this);
             dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));

             LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

             params.setMargins(8, 0, 8, 0);

             sliderDotspanel.addView(dots[i], params);
         }

         dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

         Integer[] colors_temp = {
                 getResources().getColor(R.color.color5),
                 getResources().getColor(R.color.color2),
                 getResources().getColor(R.color.color3),
                 getResources().getColor(R.color.color4)
         };

         colors = colors_temp;

         viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
             @Override
             public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                 if ((position<adapter.getCount()-1) && position<(colors.length-1)){
                     viewPager.setBackgroundColor((Integer)argbEvaluator.evaluate(positionOffset,colors[position],colors[position]+1));
                 }
                 else {
                     viewPager.setBackgroundColor(colors[colors.length -1]);
                 }
             }

             @Override
             public void onPageSelected(int position) {
                 for(int i = 0; i< dotscount; i++){
                     dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
                 }
                 dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
             }

             @Override
             public void onPageScrollStateChanged(int state) {

             }
         });
     }

     void inItUI()
  {
      items.addAll(moduleUserHomeScreen.getRowItems());
      RowItem bean=new RowItem("Recent Orders",R.drawable.iconorder);
      bean.isLocal=true;
      RowItem bean1=new RowItem("Logout",R.drawable.ic_logout);
      bean1.isLocal=true;
      RowItem bean2=new RowItem("Change Password",R.drawable.ic_changepass);
      RowItem bean3=new RowItem("Recent Dispatch",R.drawable.iconorder);
      bean3.isLocal=true;
      bean2.isStartSection=true;
      bean2.sectionName="Account";
      bean2.isLocal=true;

      items.add(bean);
     // items.add(bean3);
      items.add(bean2);
      items.add(bean1);



      slider_list =(ListView)findViewById(R.id.slider_list);
      customAdapter=new CustomAdapter(this,items);
      slider_list.setAdapter(customAdapter);
      slider_list.setOnItemClickListener(this);
      //  ModuleSync moduleSync=new ModuleSync(this);


     // tvTitle = findViewById(R.id.title);
    //  tvDescription = findViewById(R.id.description);

      btnshirt=(Button)findViewById(R.id.btnshirt);
      btntshirt=(Button)findViewById(R.id.btntshirt);
      btnpants=(Button)findViewById(R.id.btnpants);
      btnaccessories=(Button)findViewById(R.id.btnaccessories);

      btn5=(Button)findViewById(R.id.btn5);
      btn6=(Button)findViewById(R.id.btn6);
    //  btn7 = findViewById(R.id.btn7);
    //  btn8 = findViewById(R.id.btn8);
    //  btn9 = findViewById(R.id.btn9);


      gridView=(GridView)findViewById(R.id.gridView);
     // viewPager= findViewById(R.id.ViewPagerUser);
    //  sliderDotspanel = findViewById(R.id.SliderDots);

      //cardView = findViewById(R.id.cv_notice);

      //------------------- In it Button With Parente Category------------------------//
      try
        {
              btnshirt.setText(items.get(0).getTitle());
              btntshirt.setText(items.get(1).getTitle());
              btnpants.setText(items.get(2).getTitle());
              btnaccessories.setText(items.get(3).getTitle());
              btn5.setText(items.get(4).getTitle());
              btn6.setText(items.get(5).getTitle());
            //  btn7.setText(items.get(6).getTitle());
            //  btn8.setText(items.get(7).getTitle());
            //  btn9.setText(items.get(8).getTitle());
        }
        catch (Exception e)
        {

        }
      //------------------- In it Button Click Listner with Parente Category------------------------//
      try
      {
          btnshirt.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  onItemClick(null,null,0,0);
              }
          });
          btntshirt.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  onItemClick(null, null, 1, 0);
              }
          });
          btnpants.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  onItemClick(null, null, 2, 0);
              }
          });
          btnaccessories.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  onItemClick(null, null, 3, 0);

                     }
          });
          btn5.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  onItemClick(null, null, 4, 0);

              }
          });
          btn6.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  onItemClick(null, null, 5, 0);

              }
          });
          btn7.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  onItemClick(null, null, 6, 0);

              }
          });
          btn8.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  onItemClick(null, null, 7, 0);

              }
          });
          btn9.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  onItemClick(null, null, 8, 0);

              }
          });
      }
      catch (Exception e)
      {}

      TextView txtShop=(TextView)navigationView.getHeaderView(0).findViewById(R.id.txtshop);
      TextView txtWelcome=(TextView)navigationView.getHeaderView(0). findViewById(R.id.txtwelcome);

      txtShop.setText(UserUtilities.getShopName(this));
      txtWelcome.setText("Welcome : "+UserUtilities.getName(this));
     // list = new ArrayList<>();
  }

    ArrayList<RowItem> items=new ArrayList<>();
    CustomAdapter customAdapter;
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

     @Override
     protected void onResume() {
         super.onResume();
         ModuleLogin moduleUser=new ModuleLogin(this);
         moduleUser.loginInBackground();
     }

     @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
      //  if (id == R.id.actionnew) {
       //    startActivity(new Intent(this,NewArrival.class));
       // }
        if(id==R.id.actionbag)
        {
            startActivity(new Intent(this, UserBagView.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
      //  startActivity(new Intent(this, CategoryListView.class));
       if( items.get(position).isLocal)
       {
           if(items.get(position).getTitle().equalsIgnoreCase("Logout"))
           {
               AlertDialog.Builder alBuilder=new AlertDialog.Builder(this);
               alBuilder.setTitle(getString(R.string.app_name));
               alBuilder.setMessage("Do you want to Logout this app??");
               alBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {

                       UserUtilities.clearUser(UserHomeScreen.this);
                       finish();
                       startActivity(new Intent(UserHomeScreen.this, LaunchActivity.class));
                   }
               });
               alBuilder.setNegativeButton("No", null);
               alBuilder.show();
           }  //
           if(items.get(position).getTitle().equalsIgnoreCase("Change Password"))
           {
               startActivity(new Intent(this, PasswordUpdateView.class));
           }

           if(items.get(position).getTitle().equalsIgnoreCase("Recent Orders"))
           {
               startActivity(new Intent(this, OrderListView.class));
           }
           if(items.get(position).getTitle().equalsIgnoreCase("Recent Dispatch"))
           {
               startActivity(new Intent(this, UserDispatchListView.class));
           }
       }
        else
        {
            int categoryId = moduleUserHomeScreen.parentList.get(position).getCategoryId();
            Intent intent = new Intent(this, DynamicCategories.class);
            intent.putExtra("CategoryId", categoryId);
            startActivity(intent);
        }
        overridePendingTransition(R.animator.pull_in_right, R.animator.push_out_left);
    }


     @Override
     public void onComplete(String str, int requestCode, int responseCode) {
         if (requestCode == 1) {
             if (responseCode == 200) {
                 //productRandomAdapter.notifyDataSetChanged();
             }
         }
         if (requestCode == 2) {
             if (responseCode == 200) {
                 list = new ArrayList<>();
                 try {
                     SharedPreference sharedPreference = new SharedPreference(getApplicationContext());
                     sharedPreference.setStr("NoticesResponse", str);

                     getNoticeOnView();
                 } catch (Exception e) {
                     e.printStackTrace();
                 }
             }

//                         JSONArray jsonArray = new JSONArray(str);
//
//                         CreateNoticeBean createNoticeBean = new CreateNoticeBean();
//                         JSONObject jsonObject = jsonArray.getJSONObject(0);
//                         createNoticeBean.setTitle(jsonObject.getString("Title"));
//                         createNoticeBean.setNoticeId(jsonObject.getInt("NoticeId"));
//                         createNoticeBean.setDescription(jsonObject.getString("Decription"));
////                         createNoticeBean.setDescription("Hi how r u?/Hi how r u?/Hi how r u?/Hi how r u?/Hi how r u?/Hi how r u?/Hi how r u?/Hi how r u?/Hi how r u?/Hi how r u?/Hi how r u?/Hi how r u?");
//                         createNoticeBean.setAttachment(jsonObject.getString("Attachment"));
//                         createNoticeBean.setViewMore("View");
//                         list.add(createNoticeBean);
//
//                         JSONObject jsonObject1 = jsonArray.getJSONObject(1);
//
//                         createNoticeBean = new CreateNoticeBean();
//                         createNoticeBean.setTitle(jsonObject1.getString("Title"));
//                         createNoticeBean.setNoticeId(jsonObject1.getInt("NoticeId"));
//                         createNoticeBean.setDescription(jsonObject1.getString("Decription"));
//                         createNoticeBean.setAttachment(jsonObject1.getString("Attachment"));
//                         createNoticeBean.setViewMore("View");
//                             list.add((createNoticeBean));
//
//                         JSONObject jsonObject2 = jsonArray.getJSONObject(2);
//
//                         createNoticeBean = new CreateNoticeBean();
//                         createNoticeBean.setTitle(jsonObject2.getString("Title"));
//                         createNoticeBean.setNoticeId(jsonObject2.getInt("NoticeId"));
//                         createNoticeBean.setDescription(jsonObject2.getString("Decription"));
//                         createNoticeBean.setAttachment(jsonObject2.getString("Attachment"));
//                         createNoticeBean.setViewMore("View");
//                         list.add((createNoticeBean));
//
//                         createNoticeBean = new CreateNoticeBean();
//                         createNoticeBean.setViewMore("View More");
//                         list.add((createNoticeBean));
//
//                     } catch (Exception e) {
//                         e.printStackTrace();
//                     }
//                     adapter = new NoticeAdapter(list,getApplicationContext(),UserHomeScreen.this);
//                     viewPager.setAdapter(adapter);
//
//                     dotscount = adapter.getCount();
//                     dots = new ImageView[dotscount];
//
//                     for(int i = 0; i < dotscount; i++){
//
//                         dots[i] = new ImageView(this);
//                         dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
//
//                         LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//
//                         params.setMargins(8, 0, 8, 0);
//
//                         sliderDotspanel.addView(dots[i], params);
//                     }
//
//                     dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
//
//                     Integer[] colors_temp = {
//                             getResources().getColor(R.color.color5),
//                             getResources().getColor(R.color.color2),
//                             getResources().getColor(R.color.color3),
//                             getResources().getColor(R.color.color4)
//                     };
//
//                     colors = colors_temp;
//
//                     viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//                         @Override
//                         public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                             if ((position<adapter.getCount()-1) && position<(colors.length-1)){
//                                 viewPager.setBackgroundColor((Integer)argbEvaluator.evaluate(positionOffset,colors[position],colors[position]+1));
//                             }
//                             else {
//                                 viewPager.setBackgroundColor(colors[colors.length -1]);
//                             }
//                         }
//
//                         @Override
//                         public void onPageSelected(int position) {
//                             for(int i = 0; i< dotscount; i++){
//                                 dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
//                             }
//                             dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
//                         }
//
//                         @Override
//                         public void onPageScrollStateChanged(int state) {
//
//                         }
//                       });
//                     }
//                  }
         }
     }

     void checkSDCardsWrite()
     {

         if(Build.VERSION.SDK_INT>=23)
             if (ContextCompat.checkSelfPermission(this,
                     Manifest.permission.WRITE_EXTERNAL_STORAGE)
                     != PackageManager.PERMISSION_GRANTED)
             {

                 ActivityCompat.requestPermissions(this,
                         new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                         Constants.MY_PERMISSIONS_REQUEST_Write_SD_Card);
             }

     }



     @Override
     public void onRequestPermissionsResult(int requestCode,
                                            String permissions[], int[] grantResults) {
         switch (requestCode) {
             case Constants.MY_PERMISSIONS_REQUEST_Write_SD_Card: {
                 // If request is cancelled, the result arrays are empty.
                 if (grantResults.length > 0
                         && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                     // permission was granted, yay! Do the
                     // contacts-related task you need to do.

                 } else {

                     // permission denied, boo! Disable the
                     // functionality that depends on this permission.
                 }
                 return;
             }

             // other 'case' lines to check for other
             // permissions this app might request
         }


     }

     @Override
     public void notifyToActivity()
     {
         runOnUiThread(new Runnable() {
             @Override
             public void run() {
                 productRandomAdapter.notifyDataSetChanged();
                 Log.e("notify:", "Adapter dataset changed");
             }
         });
         int size=  moduleUserHomeScreen.randomList.size();
     }

     @Override
     public void finish() {
         super.finish();
         moduleUserHomeScreen.stopAsyncWork();
     }

     @Override
     public void getViewMore() {
       //  finish();
         Intent intent = new Intent(this,NoticeListView.class);
         startActivity(intent);
     }

     @Override
     public void getPagePosition(int noticeId) {
       //  finish();
         Intent intent = new Intent(this, NoticeView.class);
         intent.putExtra("noticeId",noticeId);
         startActivity(intent);
     }

     @Override
     public void getImageUrl(String attachment) {
         Intent intent=new Intent(this, ViewProductImage.class);
         intent.putExtra("path",attachment);
         startActivity(intent);
     }
 }
