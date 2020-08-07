package biyaniparker.com.parker.view.homeuser;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import biyaniparker.com.parker.LaunchActivity;
import biyaniparker.com.parker.R;
import biyaniparker.com.parker.bal.ModuleCategory;
import biyaniparker.com.parker.bal.ModuleLogin;
import biyaniparker.com.parker.bal.ModuleSync;
import biyaniparker.com.parker.bal.ModuleUser;
import biyaniparker.com.parker.bal.ModuleUserHomeScreen;
import biyaniparker.com.parker.beans.RowItem;
import biyaniparker.com.parker.utilities.CommonUtilities;
import biyaniparker.com.parker.utilities.Constants;
import biyaniparker.com.parker.utilities.DownloadUtility;
import biyaniparker.com.parker.utilities.NotifyCallback;
import biyaniparker.com.parker.utilities.UserUtilities;
import biyaniparker.com.parker.utilities.serverutilities.AsyncUtilities;
import biyaniparker.com.parker.utilities.serverutilities.ConnectionDetector;
import biyaniparker.com.parker.view.Notice.NoticeListView;
import biyaniparker.com.parker.view.adapter.CustomAdapter;
import biyaniparker.com.parker.view.adapter.ProductRandomAdapter;
import biyaniparker.com.parker.view.category.CategoryListView;
import biyaniparker.com.parker.view.homeuser.dispatch.UserDispatchListView;
import biyaniparker.com.parker.view.homeuser.productdshopping.DynamicCategories;
import biyaniparker.com.parker.view.homeuser.productdshopping.ProductDetailView;
import biyaniparker.com.parker.view.homeuser.userbag.UserBagView;
import biyaniparker.com.parker.view.homeuser.userorders.OrderListView;
import biyaniparker.com.parker.view.unitmaster.SharedPreference;
import biyaniparker.com.parker.view.user.PasswordUpdateView;

public class UserHomeScreen extends AppCompatActivity implements AdapterView.OnItemClickListener,DownloadUtility, NotifyCallback
 {
     Button btnshirt,btntshirt,btnpants,btnaccessories,btn5,btn6,btn7,btn8,btn9;
     GridView gridView;
     ListView slider_list;
     ProductRandomAdapter productRandomAdapter;
     NavigationView navigationView;
     //---- Temp-----
     ModuleSync moduleSync;
     TextView tvTitle,tvDescription;
     CardView cardView;

    ModuleUserHomeScreen moduleUserHomeScreen;
     void callPermission()
     {
         ActivityCompat.requestPermissions(this,
                 new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.READ_PHONE_STATE}
                 ,1947 );
     }
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home_screen);
        callPermission();
        moduleUserHomeScreen=new ModuleUserHomeScreen(this);
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

        productRandomAdapter=new ProductRandomAdapter(this,1,moduleUserHomeScreen.randomList);
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

        if(new ConnectionDetector(this).isConnectingToInternet())
        {
            //moduleUserHomeScreen.loadRandomProduct();
            moduleUserHomeScreen.loadRandomProductWithNotify();
        }
        else
        {
            moduleUserHomeScreen.getOffLineRandomProducts();
              //  onComplete("",1,200);
        }


        getSupportActionBar().setTitle(getString(R.string.app_name));
       // getSupportActionBar().setSubtitle("The New You");
        getSupportActionBar().setSubtitle(CommonUtilities.Slogan);
        checkSDCardsWrite();

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                finish();
                Intent intent = new Intent(UserHomeScreen.this, NoticeListView.class);
                startActivity(intent);
            }
        });
        getNoticeList();
    }

     private void getNoticeList() {
         AsyncUtilities serverAsync=new AsyncUtilities(UserHomeScreen.this,false, CommonUtilities.URL+"ProductService.svc/GetNotice","",2,this);
         serverAsync.execute();
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


      tvTitle = findViewById(R.id.title);
      tvDescription = findViewById(R.id.description);

      btnshirt=(Button)findViewById(R.id.btnshirt);
      btntshirt=(Button)findViewById(R.id.btntshirt);
      btnpants=(Button)findViewById(R.id.btnpants);
      btnaccessories=(Button)findViewById(R.id.btnaccessories);

      btn5=(Button)findViewById(R.id.btn5);
      btn6=(Button)findViewById(R.id.btn6);
      btn7 = findViewById(R.id.btn7);
      btn8 = findViewById(R.id.btn8);
      btn9 = findViewById(R.id.btn9);


      gridView=(GridView)findViewById(R.id.gridView);
      cardView = findViewById(R.id.cv_notice);

      //------------------- In it Button With Parente Category------------------------//
      try
        {
              btnshirt.setText(items.get(0).getTitle());
              btntshirt.setText(items.get(1).getTitle());
              btnpants.setText(items.get(2).getTitle());
              btnaccessories.setText(items.get(3).getTitle());
              btn5.setText(items.get(4).getTitle());
              btn6.setText(items.get(5).getTitle());
              btn7.setText(items.get(6).getTitle());
              btn8.setText(items.get(7).getTitle());
              btn9.setText(items.get(8).getTitle());
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
     public void onComplete(String str, int requestCode, int responseCode)
     {
             if(requestCode==1)
             {
                 if(responseCode==200)
                 {
                     //productRandomAdapter.notifyDataSetChanged();
                 }
             }
             if(requestCode==2)
             {
                 if(responseCode==200)
                 {
                     try {
                         SharedPreference sharedPreference = new SharedPreference(getApplicationContext());
                         sharedPreference.setStr("NoticesResponse",str);

                         JSONArray jsonArray = new JSONArray(str);
                         JSONObject jsonObject = jsonArray.getJSONObject(0);
                         String title = jsonObject.getString("Title");
                         tvTitle.setText(title);
                         String description = jsonObject.getString("Decription");
//                         tvDescription.setText(description);
                         tvDescription.setText("Hi how r you?\"Hi how r you?\"Hi how r you?\"Hi how r you?\"Hi how r you?\"Hi how r you?\"Hi how r you?\"Hi how r you?\"Hi how r you?\"Hi how r you?\"Hi how r you?\"Hi how r you?\"Hi how r you?\"Hi how r you?\"Hi how r you?\"Hi how r you?");
                         makeTextViewResizable(tvDescription, 1, "View More", true);
                     } catch (JSONException e) {
                         e.printStackTrace();
                     }
                 }
             }
        }

     private static void makeTextViewResizable(final TextView tvDescription, final int maxLine, final String expandText, final boolean viewMore) {
         if (tvDescription.getTag() == null) {
             tvDescription.setTag(tvDescription.getText());
         }
         ViewTreeObserver vto = tvDescription.getViewTreeObserver();
         vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

             @SuppressWarnings("deprecation")
             @Override
             public void onGlobalLayout() {
                 String text;
                 int lineEndIndex;
                 ViewTreeObserver obs = tvDescription.getViewTreeObserver();
                 obs.removeGlobalOnLayoutListener(this);
                 if (maxLine == 0) {
                     lineEndIndex = tvDescription.getLayout().getLineEnd(0);
                     text = tvDescription.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                 } else if (maxLine > 0 && tvDescription.getLineCount() >= maxLine) {
                     lineEndIndex = tvDescription.getLayout().getLineEnd(maxLine - 1);
                     text = tvDescription.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                 } else {
                     lineEndIndex = tvDescription.getLayout().getLineEnd(tvDescription.getLayout().getLineCount() - 1);
                     text = tvDescription.getText().subSequence(0, lineEndIndex) + " " + expandText;
                 }
                 tvDescription.setText(text);
                 tvDescription.setMovementMethod(LinkMovementMethod.getInstance());
                 tvDescription.setText(addClickablePartTextViewResizable(Html.fromHtml(tvDescription.getText().toString()), tvDescription, lineEndIndex, expandText,
                                 viewMore), TextView.BufferType.SPANNABLE);
             }
         });
     }

     private static SpannableStringBuilder addClickablePartTextViewResizable(final Spanned strSpanned, final TextView tv,
                                                                             final int maxLine, final String spanableText, final boolean viewMore) {
         String str = strSpanned.toString();
         SpannableStringBuilder ssb = new SpannableStringBuilder(strSpanned);

         if (str.contains(spanableText)) {
             ssb.setSpan(new ClickableSpan() {

                 @Override
                 public void onClick(View widget) {
                     tv.setLayoutParams(tv.getLayoutParams());
                     tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);
                     tv.invalidate();
                     if (viewMore) {
                         makeTextViewResizable(tv, -1, "View Less", false);
                     } else {
                         makeTextViewResizable(tv, 1, "View More", true);
                     }
                 }
             }, str.indexOf(spanableText), str.indexOf(spanableText) + spanableText.length(), 0);

         }
         return ssb;
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
 }
