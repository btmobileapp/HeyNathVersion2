package biyaniparker.com.parker.view.homeuser.productdshopping;

import android.animation.ArgbEvaluator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import biyaniparker.com.parker.R;
import biyaniparker.com.parker.bal.ModuleCategory;
import biyaniparker.com.parker.bal.ModuleProduct;
import biyaniparker.com.parker.bal.ModuleProductDetails;
import biyaniparker.com.parker.beans.ProductBeanWithQnty;
import biyaniparker.com.parker.beans.ProductDetailsBean;
import biyaniparker.com.parker.beans.ProductStockBean;
import biyaniparker.com.parker.beans.SizeMaster;
import biyaniparker.com.parker.beans.StockMasterBean;
import biyaniparker.com.parker.beans.UnitMasterBean;
import biyaniparker.com.parker.database.ItemDAOSizeMaster;
import biyaniparker.com.parker.utilities.CommonUtilities;
import biyaniparker.com.parker.utilities.DownloadUtility;
import biyaniparker.com.parker.utilities.UserUtilities;
import biyaniparker.com.parker.utilities.serverutilities.AsyncUtilities;
import biyaniparker.com.parker.utilities.serverutilities.ConnectionDetector;
import biyaniparker.com.parker.view.adapter.ProductDetailsAdapter;
import biyaniparker.com.parker.view.homeuser.UserHomeScreen;
import biyaniparker.com.parker.view.product.ProductCreateViewNew;
import biyaniparker.com.parker.view.product.ProductEditViewNew;
import biyaniparker.com.parker.view.product.ProductListView;
import biyaniparker.com.parker.view.unitmaster.SharedPreference;

public class ProductDetailView extends AppCompatActivity implements DownloadUtility, View.OnClickListener, ProductDetailsAdapter.ProductAdaperCallBack {
    TextView txtproductname,txtprice;
   // ImageView image;
    Button btnAdd;
    HorizontalScrollView horizontal;
    ProductBeanWithQnty bean;
    LinearLayout linearSizeMain,lhorizontal;
    ArrayList<StockMasterBean> stockList=new ArrayList<>();
    ModuleProductDetails moduleProductDetails;
    ModuleCategory moduleCategory;
    ImageView btnplus;
    ViewPager viewPager;
    ProductDetailsAdapter adapter;
    List<ProductDetailsBean> productDetailsBeanList;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();
    Integer[] colors = null;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;
    EditText etRemark,etUnitName;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail_view);
        moduleProductDetails=new ModuleProductDetails(this);
        try
        {
             Gson gson = new Gson();
             bean = gson.fromJson(getIntent().getStringExtra("myjson"), ProductBeanWithQnty.class);
        }
        catch (Exception e)
        {}
        inItUi();
        if (bean != null)
        {

            txtproductname.setText(bean.getProductName());
            try {
                double price=bean.price ;//moduleProductDetails.getPriceFromPriceId(bean.getPriceId());
                DecimalFormat df = new DecimalFormat("#.##");
                txtprice.setText( df.format( price)+" Rs");
            }
            catch (Exception e){ txtprice.setText(" Rs");}

            // To Display Image ON Image view
//            imageLoader = ImageLoader.getInstance();
//            //  ImageLoaderConfiguration.//408, 306, CompressFormat.JPEG, 75, null);
//            imageLoader.displayImage(
//                    bean.iconThumb, image, doption, animateFirstListener);

            productDetailsBeanList = new ArrayList<>();

            try {
                if (bean.getIconThumb()!=null){
                    productDetailsBeanList.add(new ProductDetailsBean(bean.getIconThumb()));
                }
                if (bean.IconFull2!=null){
                    productDetailsBeanList.add(new ProductDetailsBean(bean.IconFull2));
                }
                if (bean.IconFull3!=null){
                    productDetailsBeanList.add(new ProductDetailsBean(bean.IconFull3));
                }
                if (bean.IconFull4!=null){
                    productDetailsBeanList.add(new ProductDetailsBean(bean.IconFull4));
                }
                if (bean.IconFull5!=null){
                    productDetailsBeanList.add(new ProductDetailsBean(bean.IconFull5));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            adapter = new ProductDetailsAdapter(productDetailsBeanList,getApplicationContext(),ProductDetailView.this);
            viewPager.setAdapter(adapter);
         //   viewPager.setPadding(130,0,130,0);

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
                    getResources().getColor(R.color.color1),
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

        moduleProductDetails.setProductId(bean.getProductId());
        moduleCategory=new ModuleCategory(this);
        if(new ConnectionDetector(this).isConnectingToInternet()) {
            moduleProductDetails.syncProduct();
        }
        else
        {
            moduleProductDetails.loadFromDb(bean.getStripCode());
            addStripCodeProducts();
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        getSupportActionBar().setTitle("Product Details");
        getSupportActionBar().setSubtitle(""+moduleCategory.getCategoryName(bean.getCategoryId()));

        CommonUtilities.hideatInItInputBoard(this);

      //  String url=CommonUtilities.URL+"ProductService.svc/GetProductDetailsForAdmin?ProductId="+bean.getProductId();
     //   AsyncUtilities utilities=new AsyncUtilities(this,false,url,null,150,this);
     //   utilities.execute();

        intitMultipleImages("");
        //addSizeView();
    }

    ImageView image1,image2,image3,image4;
    private void intitMultipleImages(String str)
    {
        image1 = findViewById(R.id.image1);
        image2 = findViewById(R.id.image2);
        image3 = findViewById(R.id.image3);
        image4 = findViewById(R.id.image4);

        try {
           // JSONArray  array=new JSONArray(str);
            final String url1= bean.IconFull2; //array.getJSONObject(0).getString("IconFull2");
            final String url2=  bean.IconFull3;  //array.getJSONObject(0).getString("IconFull3");
            final String url3= bean.IconFull4;  // array.getJSONObject(0).getString("IconFull4");
            final String url4=  bean.IconFull5;  //array.getJSONObject(0).getString("IconFull5");
            try {
                  if(url1==null ||url1.length()<5)
                  {
                      image1.setVisibility(View.GONE);
                  }
                if(url2==null ||url2.length()<5)
                {
                    image2.setVisibility(View.GONE);
                }
                if(url3==null ||url3.length()<5)
                {
                    image3.setVisibility(View.GONE);
                }
                if(url4==null ||url4.length()<5)
                {
                    image4.setVisibility(View.GONE);
                }
            }
            catch (Exception ex)
            {}
            imageLoader = ImageLoader.getInstance();
            //  ImageLoaderConfiguration.//408, 306, CompressFormat.JPEG, 75, null);
            imageLoader.displayImage(url1,image1, doption, animateFirstListener);
            imageLoader.displayImage(url2,image2, doption, animateFirstListener);
            imageLoader.displayImage(url3,image3, doption, animateFirstListener);
            imageLoader.displayImage(url4,image4, doption, animateFirstListener);

            image1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //   CommonUtilities.alert(UserBagView.this,"String : "+str);
                    Intent intent=new Intent(ProductDetailView.this, ViewProductImage.class);
                    intent.putExtra("path",url1);
                    startActivity(intent);
                }
            });
            image4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //   CommonUtilities.alert(UserBagView.this,"String : "+str);
                    Intent intent=new Intent(ProductDetailView.this, ViewProductImage.class);
                    intent.putExtra("path",url4);
                    startActivity(intent);
                }
            });
            image2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //   CommonUtilities.alert(UserBagView.this,"String : "+str);
                    Intent intent=new Intent(ProductDetailView.this, ViewProductImage.class);
                    intent.putExtra("path",url2);
                    startActivity(intent);
                }
            });
            image3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //   CommonUtilities.alert(UserBagView.this,"String : "+str);
                    Intent intent=new Intent(ProductDetailView.this, ViewProductImage.class);
                    intent.putExtra("path",url3);
                    startActivity(intent);
                }
            });

        }
        catch (Exception ec)
        {}
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }


    void inItUi()
    {
        btnAdd=(Button)findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);
        txtproductname=(TextView)findViewById(R.id.txtproductname);
        txtprice=(TextView)findViewById(R.id.txtprice);
        //image=(ImageView)findViewById(R.id.image);
        linearSizeMain=(LinearLayout)findViewById(R.id.linearSizeMain);
        horizontal=(HorizontalScrollView)findViewById(R.id.horizontal);
        lhorizontal=(LinearLayout)findViewById(R.id.lhorizontal);
        viewPager = findViewById(R.id.ViewPager);
        sliderDotspanel = (LinearLayout) findViewById(R.id.SliderDots);
        etRemark = findViewById(R.id.etProductDetailsRemark);
        etUnitName = findViewById(R.id.etProductDetailsUnitName);

        etUnitName.setText(bean.getUnitName());
        etRemark.setText(bean.getRemark());

        doption = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.bgpaker)
                .showImageOnFail(R.drawable.bgpaker)
                .showStubImage(R.drawable.bgpaker).cacheInMemory(true)
                .cacheOnDisc(true).displayer(new RoundedBitmapDisplayer(5)) // 100
                        // for
                        // Rounded
                        // Image
                .cacheOnDisc(true)
                        //.imageScaleType(10)
                .build();
        btnplus=(ImageView)findViewById(R.id.btnplus);
       // btnplus.setVisibility(View.INVISIBLE);
        btnplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ProductDetailView.this,ViewProductImage.class);
                intent.putExtra("path",bean.getIconThumb());
                startActivity(intent);
            }
        });
    }

    //-------------------------Image Code---------------------------
    DisplayImageOptions doption = null;
    private AnimateFirstDisplayListener animateFirstListener;
    private ImageLoader imageLoader;

    @Override
    public void onClick(View v)
    {
        CommonUtilities.hideSoftKeyBord(this);
        stockList.clear();
        if(validation())
        {
            if(stockList.isEmpty())
            {
                Toast.makeText(getApplicationContext()," Please enter stock quantity ..  ",Toast.LENGTH_SHORT).show();
            }
            else
            {
                AlertDialog.Builder alertDialog=new AlertDialog.Builder(this);
                alertDialog.setTitle(getString(R.string.app_name));
                alertDialog.setMessage("Do you want to add these products in bags");
                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try
                        {
                            moduleProductDetails.addToBag(stockList);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
              alertDialog.setNegativeButton("No", null);
                /*alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {

                    }
                })*/
                alertDialog.show();
            }
        }
        else
        {
            Toast.makeText(this,"Please enter valid quantity ...  ",Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validation()
    {
        int valid;

        for(int i=0;i<moduleProductDetails.stockList.size();i++)
        {
            ProductStockBean stock=moduleProductDetails.stockList.get(i);
            View view=sizeView.get(i);
            EditText ed=(EditText)view.findViewById(R.id.edOrderQnty);
            StockMasterBean s=new StockMasterBean();
            String val=ed.getText().toString();
            if(val.equals("") || val.equals("0"))
            {
                int c=0;
            }
            else if(stock.getQnty()<Integer.parseInt(val))
            {
                return false;
            }
            else
            {
                s.setProductId(bean.getProductId());
                s.setInBagQty(Integer.parseInt(val));
                s.setSizeId(stock.getSizeId());
                s.setTransactionType("inbag");
                s.setDeleteStatus("false");
                s.setUserId(UserUtilities.getUserId(this));
                s.setChangedBY(UserUtilities.getUserId(this));
                s.setEnterBy(UserUtilities.getUserId(this));
                s.setClientId(UserUtilities.getClientId(this));
                s.setUnitName(etUnitName.getText().toString());
                s.setRemark(etRemark.getText().toString());
                stockList.add(s);
            }
        }
        return true;
    }


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
                if (firstDisplay)
                {
                    FadeInBitmapDisplayer.animate(imageView, 0);
                    displayedImages.add(imageUri);
                }
                btnplus.setVisibility(View.VISIBLE);
            }
        }
    }
    //---------------------------------------------------------------------------------




    @Override
    public void onComplete(String str, int requestCode, int responseCode)
    {
        if(requestCode==1 && responseCode==200)
        {
              // Toast.makeText(this,""+str,Toast.LENGTH_LONG).show();
               addSizeView();
               // addStripCodeProducts();
        }
        else if(requestCode==150 && responseCode==200)
        {
            //intitMultipleImages( str);
        }

        else if(requestCode==2 && responseCode==200)
        {
            if(str.equals("Success"))
            {
                Toast.makeText(getApplicationContext(),"Products Succesfully added to bag ",Toast.LENGTH_SHORT).show();
               // finish();
              //  callRefresh();
                Intent intent = new Intent(this, UserHomeScreen.class);
                startActivity(intent);
            }
            else if(str.equals("Failed"))
            {
                AlertDialog.Builder alertDialog=new AlertDialog.Builder(this);
                alertDialog.setTitle(getString(R.string.app_name));
                alertDialog.setMessage("Required quantity not available in stock ..Try Again !!");
                alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callRefresh();
                    }
                });
            }
        }

    }


    //------------------------------   Add Size and Available qnty and -------------------


    ArrayList<View>  sizeView=new ArrayList<>();

    void addSizeView()
    {
        sizeView.clear();
        for(int i=0;i<moduleProductDetails.stockList.size();i++)
        {
            LayoutInflater inflater=(LayoutInflater)  getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v= inflater.inflate(R.layout.itemaddsizetoproductdetail, null);
            v.setTag(i);
            //TextView txtSizeName=(TextView)v.findViewById(R.id.txtSizeName);
            TextView txtAvaibleQnt=(TextView)v.findViewById(R.id.txtAvaibleQnt);
            txtAvaibleQnt.setVisibility(View.INVISIBLE);
            EditText edOrderQnty=(EditText)v.findViewById(R.id.edOrderQnty);
           // txtSizeName.setText(moduleProductDetails.getSizeNameBySizeId(moduleProductDetails.stockList.get(i).getSizeId()));
            txtAvaibleQnt.setText(moduleProductDetails.stockList.get(i).getQnty() + "");

            sizeView.add(v);
            if( moduleProductDetails.stockList.get(i).getQnty()!=0)
            {
                edOrderQnty.setText(""+1);
            }
            linearSizeMain.addView(v);
        }
    }

    /*
    void addSizeView()
    {
        sizeView.clear();
        ItemDAOSizeMaster itemDAOSizeMaster=new ItemDAOSizeMaster(this);
        ArrayList<SizeMaster>  sList= itemDAOSizeMaster.getAllSize(1);
        for(int i=0;i<sList.size();i++)
        {
            LayoutInflater inflater=(LayoutInflater)  getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v= inflater.inflate(R.layout.itemaddsizetoproductdetail, null);
            v.setTag(i);
            //TextView txtSizeName=(TextView)v.findViewById(R.id.txtSizeName);
            TextView txtAvaibleQnt=(TextView)v.findViewById(R.id.txtAvaibleQnt);
            txtAvaibleQnt.setVisibility(View.INVISIBLE);
            EditText edOrderQnty=(EditText)v.findViewById(R.id.edOrderQnty);
            // txtSizeName.setText(moduleProductDetails.getSizeNameBySizeId(moduleProductDetails.stockList.get(i).getSizeId()));
            txtAvaibleQnt.setText(1 + "");

            sizeView.add(v);
            if( moduleProductDetails.stockList.get(i).getQnty()!=0)
            {
                edOrderQnty.setText(""+1);
            }
            linearSizeMain.addView(v);
        }
    }*/

    void addStripCodeProducts()
    {

        if(moduleProductDetails.stripCodeProducts!=null)
        {
            for(int i=0;i<moduleProductDetails.stripCodeProducts.size();i++)
            {
                LayoutInflater inflater=(LayoutInflater)  getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View v= inflater.inflate(R.layout.itemimageview, null);
                ImageView im=(ImageView)v.findViewById(R.id.im1);
                imageLoader.displayImage(
                        moduleProductDetails.stripCodeProducts.get(i).getIconThumb(), im, doption, animateFirstListener);
                v.setTag(i);
                if(bean.getProductId()!=  moduleProductDetails.stripCodeProducts.get(i).getProductId())
                         lhorizontal.addView(v);

                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent=new Intent(ProductDetailView.this, ProductDetailView.class);
                        Gson gson = new Gson();
                        String myJson = gson.toJson(moduleProductDetails.stripCodeProducts.get(
                                Integer.parseInt((v.getTag().toString()))));
                        finish();
                        intent.putExtra("myjson",myJson);
                        startActivity(intent);
                    }
                });
            }
        }
    }

    void callRefresh()
    {
        Intent intent=new Intent(ProductDetailView.this, ProductDetailView.class);
        Gson gson = new Gson();
        String myJson = gson.toJson(bean);
        finish();
        intent.putExtra("myjson",myJson);
        startActivity(intent);
    }

    @Override
    public void getImageUrl(String imgUrl) {
        Intent intent=new Intent(ProductDetailView.this, ViewProductImage.class);
        intent.putExtra("path",imgUrl);
        startActivity(intent);
    }
}
