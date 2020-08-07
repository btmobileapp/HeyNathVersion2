package biyaniparker.com.parker.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import biyaniparker.com.parker.R;
import biyaniparker.com.parker.bal.ModuleProductDetails;
import biyaniparker.com.parker.beans.ProductBeanWithQnty;

public class ChangeViewAdapter extends RecyclerView.Adapter<ChangeViewAdapter.ViewHolder> {
    public List<ProductBeanWithQnty> newProductList;
    Context context;
    ChangeViewCallBack changeViewCallBack;


    public  ChangeViewAdapter(Context context,List<ProductBeanWithQnty> list,ChangeViewCallBack ChangeViewCallBack){
        this.context = context;
        this.newProductList = list;
        this.changeViewCallBack = ChangeViewCallBack;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.change_view_adapter_list, parent, false);

        return new ViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull final ChangeViewAdapter.ViewHolder holder, final int position) {
        final ProductBeanWithQnty productBeanWithQnty = newProductList.get(position);

        if (productBeanWithQnty.getIconThumb()==""){
            holder.imageView.setImageResource(R.drawable.bgpaker);
        } else {
            Picasso.get().load(productBeanWithQnty.getIconThumb()).into(holder.imageView);
           // Picasso.get().load(productBeanWithQnty.get(position).get()).placeholder(R.drawable.bgparker).into(holder.imageView);
        }
        holder.tv1.setText(productBeanWithQnty.getProductName());
        try
        {
            double price =  productBeanWithQnty.price;     //itemDAOPrice.getPriceBeanByPriceId(rowItem.getPriceId()).consumerPrice;
            DecimalFormat df = new DecimalFormat("#.##");
            holder.tv2.setText("Rs. " +  df.format(price));
        }
        catch (Exception e)
        {
            holder.tv2.setText("Rs. ");
        }
       // holder.tv2.setText(Integer.toString(productBeanWithQnty.getPriceId()));
        holder.tv3.setText(productBeanWithQnty.getUnitName());
        holder.tv4.setText(productBeanWithQnty.getRemark());
     //   holder.et.setText(holder.et.getText().toString());
       // holder.tv5.setText(moduleProductDetails.stockList.get(position).getQnty() + "");
       // holder.tv5.setText(Integer.toString(productBeanWithQnty.getQnt()));

         final String qty = holder.et.getText().toString();
        holder.addToBag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeViewCallBack.getData(productBeanWithQnty,qty);
            }
        });
    }

    @Override
    public int getItemCount() {
        return newProductList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tv1,tv2,tv3,tv4;
        EditText et;
        Button addToBag;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.ivchange);
            tv1 = itemView.findViewById(R.id.tvProductchange);
            tv2 = itemView.findViewById(R.id.tvPriceChange);
            et = itemView.findViewById(R.id.tvQtyChange);
            tv3 = itemView.findViewById(R.id.tvUnitChange);
            tv4 = itemView.findViewById(R.id.tvRemarkChange);
            addToBag = itemView.findViewById(R.id.btnAddtobags);
        }
    }

    public interface ChangeViewCallBack{
        void getData(ProductBeanWithQnty productBeanWithQnty, String qty);
    }
}
