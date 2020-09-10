package biyaniparker.com.parker.view.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import biyaniparker.com.parker.R;
import biyaniparker.com.parker.beans.ProductDetailsBean;

public class ProductDetailsAdapter extends PagerAdapter {
    List<ProductDetailsBean> productDetailsBeanslist;
    LayoutInflater layoutInflater;
    Context context;
    ProductAdaperCallBack productAdaperCallBack;




    //*********************Copy This  ******************
    DisplayImageOptions doption = null;
    private AnimateFirstDisplayListener animateFirstListener;
    private ImageLoader imageLoader;





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
    //*************************************************


    public ProductDetailsAdapter(List<ProductDetailsBean> list,Context context,ProductAdaperCallBack productAdaperCallBack)
    {
        this.productDetailsBeanslist = list;
        this.context = context;
        this.productAdaperCallBack = productAdaperCallBack;

        doption = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.bgpaker1)
                .showImageOnFail(R.drawable.bgpaker1)
                .showStubImage(R.drawable.bgpaker1).cacheInMemory(true)
                .cacheOnDisc(true).displayer(new RoundedBitmapDisplayer(5)) // 100
                // for
                // Rounded
                // Image
                .cacheOnDisc(true)
                //.imageScaleType(10)
                .build();
    }

    @Override
    public int getCount() {
        return productDetailsBeanslist.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item,container,false);

        ImageView imageView = view.findViewById(R.id.imgProductDetails);

        if (productDetailsBeanslist.get(position).getImageUrl().equalsIgnoreCase("")){
            imageView.setImageResource(R.drawable.bgpaker1);
        }
        else
        {
            imageLoader = ImageLoader.getInstance();
            imageLoader.displayImage(
                    productDetailsBeanslist.get(position).getImageUrl()
                    ,
                    imageView, doption, animateFirstListener);
           // Picasso.get().load(productDetailsBeanslist.get(position).getImageUrl()).placeholder(R.drawable.bgpaker1).into(imageView);
        }
        imageView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
               String imgUrl =  productDetailsBeanslist.get(position).getImageUrl();
               productAdaperCallBack.getImageUrl(imgUrl);
            }
        });
        container.addView(view,0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

    public interface  ProductAdaperCallBack{
        void getImageUrl(String imgUrl);
    }
}
