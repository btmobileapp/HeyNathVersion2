package biyaniparker.com.parker.view.homeuser.productdshopping;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import biyaniparker.com.parker.R;
import biyaniparker.com.parker.bal.ModuleProductDetails;
import biyaniparker.com.parker.beans.ProductBeanWithQnty;
import biyaniparker.com.parker.beans.ProductDetailsBean;
import biyaniparker.com.parker.utilities.DownloadUtility;
import biyaniparker.com.parker.view.adapter.ProductDetailsAdapter;

public class ImageClass extends AppCompatActivity implements DownloadUtility, ProductDetailsAdapter.ProductAdaperCallBack {
    ModuleProductDetails moduleProductDetails;
    ProductBeanWithQnty bean;
    List<ProductDetailsBean> productDetailsBeanList;
    ProductDetailsAdapter adapter;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();
    Integer[] colors = null;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_class_view);
        moduleProductDetails = new ModuleProductDetails(this);
        try {
            Gson gson = new Gson();
            bean = gson.fromJson(getIntent().getStringExtra("myjson"), ProductBeanWithQnty.class);
        } catch (Exception e) {
        }
        viewPager = findViewById(R.id.ViewPagerImage);
        sliderDotspanel = (LinearLayout) findViewById(R.id.SliderDotsImage);

        if (bean != null) {
            // To Display Image ON Image view
//            imageLoader = ImageLoader.getInstance();
//            //  ImageLoaderConfiguration.//408, 306, CompressFormat.JPEG, 75, null);
//            imageLoader.displayImage(
//                    bean.iconThumb, image, doption, animateFirstListener);

            productDetailsBeanList = new ArrayList<>();

            try {
                if (bean.getIconThumb() != null) {
                    productDetailsBeanList.add(new ProductDetailsBean(bean.getIconThumb()));
                }
                if (bean.IconFull2 != null) {
                    productDetailsBeanList.add(new ProductDetailsBean(bean.IconFull2));
                }
                if (bean.IconFull3 != null) {
                    productDetailsBeanList.add(new ProductDetailsBean(bean.IconFull3));
                }
                if (bean.IconFull4 != null) {
                    productDetailsBeanList.add(new ProductDetailsBean(bean.IconFull4));
                }
                if (bean.IconFull5 != null) {
                    productDetailsBeanList.add(new ProductDetailsBean(bean.IconFull5));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            adapter = new ProductDetailsAdapter(productDetailsBeanList, getApplicationContext(), ImageClass.this);
            viewPager.setAdapter(adapter);
            //   viewPager.setPadding(130,0,130,0);

            dotscount = adapter.getCount();
            dots = new ImageView[dotscount];

            for (int i = 0; i < dotscount; i++) {

                dots[i] = new ImageView(getApplicationContext());
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
                    if ((position < adapter.getCount() - 1) && position < (colors.length - 1)) {
                        viewPager.setBackgroundColor((Integer) argbEvaluator.evaluate(positionOffset, colors[position], colors[position] + 1));
                    } else {
                        viewPager.setBackgroundColor(colors[colors.length - 1]);
                    }
                }

                @Override
                public void onPageSelected(int position) {
                    for (int i = 0; i < dotscount; i++) {
                        dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
                    }
                    dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

        }
    }
    @Override
    public void onComplete (String str,int requestCode, int responseCode){

    }

    @Override
    public void getImageUrl(String imgUrl) {
        Intent intent=new Intent(ImageClass.this, ViewProductImage.class);
        intent.putExtra("path",imgUrl);
        startActivity(intent);
    }
}
