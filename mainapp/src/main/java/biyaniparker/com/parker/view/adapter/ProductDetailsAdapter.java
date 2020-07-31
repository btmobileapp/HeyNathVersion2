package biyaniparker.com.parker.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.squareup.picasso.Picasso;

import java.util.List;

import biyaniparker.com.parker.R;
import biyaniparker.com.parker.beans.ProductDetailsBean;

public class ProductDetailsAdapter extends PagerAdapter {
    List<ProductDetailsBean> productDetailsBeanslist;
    LayoutInflater layoutInflater;
    Context context;
    ProductAdaperCallBack productAdaperCallBack;

    public ProductDetailsAdapter(List<ProductDetailsBean> list,Context context,ProductAdaperCallBack productAdaperCallBack)
    {
        this.productDetailsBeanslist = list;
        this.context = context;
        this.productAdaperCallBack = productAdaperCallBack;
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
        if (productDetailsBeanslist.get(position).getImageUrl()!=null)
        {
            Picasso.get().load(productDetailsBeanslist.get(position).getImageUrl()).into(imageView);
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
