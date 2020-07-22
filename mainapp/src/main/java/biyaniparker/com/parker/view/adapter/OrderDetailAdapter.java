package biyaniparker.com.parker.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import biyaniparker.com.parker.R;
import biyaniparker.com.parker.bal.ModuleCategory;
import biyaniparker.com.parker.bal.ModuleProduct;
import biyaniparker.com.parker.beans.OrderDetailBean;
import biyaniparker.com.parker.view.homeuser.productdshopping.ViewProductImage;

/**
 * Created by bt on 01/13/2017.
 */
public class OrderDetailAdapter extends ArrayAdapter {


    //*********************Copy This  ******************
    DisplayImageOptions doption = null;
    private AnimateFirstDisplayListener animateFirstListener;
    private ImageLoader imageLoader;
    //*************************************************

    List<OrderDetailBean> orderDetailsNew;
    List<OrderDetailBean> orderDetails;
    Context context;
    ModuleCategory moduleCategory;
    ModuleProduct moduleProduct;

    public OrderDetailAdapter(Context context, int resource, List<OrderDetailBean> orderDetailsNew, List<OrderDetailBean> orderDetails) {
        super(context, resource, orderDetailsNew);
        this.context=context;
        this.orderDetailsNew=orderDetailsNew;
        this.orderDetails=orderDetails;


        moduleCategory=new ModuleCategory(context);
        moduleProduct=new ModuleProduct(context);

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

    }


    class ViewHolder
    {
        TextView pName;
        TextView cPrice;
        TextView tPrice;
        TextView tStrip;
        LinearLayout l;
        ImageView image;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        convertView=null;
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final OrderDetailBean orderD=orderDetailsNew.get(position);
        ViewHolder holder=null;
        if(convertView==null)
        {
            convertView=inflater.inflate(R.layout.o_item_order_details,null);
            holder=new ViewHolder();
            holder.image= (ImageView) convertView.findViewById(R.id.img);
            holder.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context,ViewProductImage.class);
                    intent.putExtra("path", orderD.getIconThumb());
                    context.startActivity(intent);
                }
            });
            holder.pName= (TextView) convertView.findViewById(R.id.txtName);
            holder.cPrice= (TextView) convertView.findViewById(R.id.txtCPrice);
            holder.tPrice= (TextView) convertView.findViewById(R.id.txtTPrice);
            holder.tStrip= (TextView) convertView.findViewById(R.id.txtStripCode);
            holder.l = (LinearLayout) convertView.findViewById(R.id.linear);
            convertView.setTag(holder);
        }
        else
        {
            holder= (ViewHolder) convertView.getTag();
        }


        imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(
                orderD.getIconThumb()
                ,
                holder.image, doption, animateFirstListener);


        double total=0;


        holder.pName.setText(orderD.getProductName()+"("+orderD.getProductId()+")");
        holder.cPrice.setText("" + (int) Double.parseDouble(orderD.getConsumerPrice()) + "  Rs");

        for(int c=position;c<orderDetails.size();c++)
        {
            if(orderD.getProductId()==orderDetails.get(c).getProductId())
            {

                View sub = inflater.inflate(R.layout.o_activity_two_textviews, null);
                TextView size = (TextView) sub.findViewById(R.id.txtsize);
                TextView qnty = (TextView) sub.findViewById(R.id.txtsqnty);

                //totalQnty+=orderDetails.get(c).getQuantity();
                total = total + (orderDetails.get(c).getQuantity() * (Double.parseDouble(orderDetails.get(c).getDealerPrice())));
                size.setText(orderDetails.get(c).getSizeName());
                qnty.setText(String.valueOf(orderDetails.get(c).getQuantity()));
                holder.l.addView(sub);
            }
        }

        holder.tPrice.setText(getCategoryName(orderD.getProductId()));
        holder.tStrip.setText("Strip code :"+moduleProduct.getProductBeanByProductId(orderD.getProductId()).getStripCode());


        return convertView;
    }

    private String getCategoryName(int productId)
    {
        return moduleCategory.getCategoryName((moduleProduct.getProductBeanByProductId(productId)).getCategoryId());
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
                if (firstDisplay) {
                    FadeInBitmapDisplayer.animate(imageView, 500);
                    displayedImages.add(imageUri);
                }
            }
        }
    }

}
