package biyaniparker.com.parker.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.api.Api;
import com.squareup.picasso.Picasso;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import biyaniparker.com.parker.R;
import biyaniparker.com.parker.beans.ProductBeanWithQnty;

public class ChangeViewAdapter extends RecyclerView.Adapter<ChangeViewAdapter.ViewHolder> {
    public List<ProductBeanWithQnty> newProductList;
    Context context;
    ChangeViewCallBack changeViewCallBack;
    int Position;
    ArrayList<View> viewList = new ArrayList<View>();


    public  ChangeViewAdapter(Context context,List<ProductBeanWithQnty> list,ChangeViewCallBack ChangeViewCallBack){
        this.context = context;
        this.newProductList = list;
        this.changeViewCallBack = ChangeViewCallBack;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.change_view_adapter_list, null, false);
       viewList.add(itemView);

        return new ViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull final ChangeViewAdapter.ViewHolder holder, final int position) {
        final ProductBeanWithQnty productBeanWithQnty = newProductList.get(position);

        if (position>0){
            holder.cbSelectAll.setVisibility(View.GONE);
        }else {
            holder.cbSelectAll.setVisibility(View.VISIBLE);
        }
        if (newProductList.size()==1){
            holder.cbSelectAll.setVisibility(View.GONE);
        }
        if (productBeanWithQnty.getIconThumb()==""){
            holder.imageView.setImageResource(R.drawable.bgchoice);
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
        holder.tv5.setText(productBeanWithQnty.getProductName());
        //   holder.et.setText(holder.et.getText().toString());
        // holder.tv5.setText(moduleProductDetails.stockList.get(position).getQnty() + "");
        // holder.tv5.setText(Integer.toString(productBeanWithQnty.getQnt()));

        // final String qty = holder.et.getText().toString();
         productBeanWithQnty.setQnt(Integer.parseInt(holder.et.getText().toString()));
       //  newProductList.add(productBeanWithQnty);

//        holder.addToBag.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                changeViewCallBack.getData(productBeanWithQnty,holder.et.getText().toString());
//            }
//        });


        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //is chkIos checked?
                if (((CheckBox) v).isChecked()) {
                    try {
                        int qty = Integer.parseInt(holder.et.getText().toString());
                        if (qty==0){
                            Toast.makeText(context,"please enter valid stock quantity",Toast.LENGTH_SHORT).show();
                            holder.checkBox.setChecked(false);
                        }
                        else {
                            //newProductList.add(productBeanWithQnty);
                            changeViewCallBack.getData(newProductList,position,holder.et.getText().toString());
                        }
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
                else{

                }
            }
        });

        holder.cbSelectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //is chkIos checked?
                if(holder.cbSelectAll.isChecked())
                {
                    for(int i=0;i<viewList.size();i++)
                    {
                       // newProductList.add(productBeanWithQnty);
//                        if (productBeanWithQnty.getQnt()==0){
//                            Toast.makeText(context,"please enter valid stock quantity",Toast.LENGTH_SHORT).show();
//                            holder.cbSelectAll.setChecked(false);
//                        } else {
                          //  v = holder.getAdapterPosition();
                            v = viewList.get(i);
                            holder.checkBox = v.findViewById(R.id.chk);
                            holder.et = v.findViewById(R.id.tvQtyChange);
                            if (Integer.parseInt(holder.et.getText().toString())==0){
                                Toast.makeText(context,"please enter valid stock quantity",Toast.LENGTH_SHORT).show();
                                holder.cbSelectAll.setChecked(false);
                            } else {
                                holder.checkBox.setChecked(true);
                                changeViewCallBack.getList(newProductList.get(i),holder.et.getText().toString());
                            }
                       // }
                       // v= newProductList.get(i);
                       //CheckBox c= (CheckBox) holder.itemView.findViewById(R.id.chk);
                    }
                }
                else
                {
                    for(int i=0;i<newProductList.size();i++)
                    {
                        // v=newProductList.get(i);
                        //CheckBox c= (CheckBox) v.findViewById(R.id.chk);
                        holder.checkBox.setChecked(false);
                    }
                }
            }
        });

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeViewCallBack.getPosition(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return newProductList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView tv1,tv2,tv3,tv4,tv5;
        EditText et;
        Button addToBag;
        CheckBox checkBox,cbSelectAll;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.ivchange);
            tv1 = itemView.findViewById(R.id.tvProductchange);
            tv2 = itemView.findViewById(R.id.tvPriceChange);
            et = itemView.findViewById(R.id.tvQtyChange);
            tv3 = itemView.findViewById(R.id.tvUnitChange);
            tv4 = itemView.findViewById(R.id.tvRemarkChange);
            tv5 = itemView.findViewById(R.id.txtName);
            checkBox = itemView.findViewById(R.id.chk);
            cbSelectAll = itemView.findViewById(R.id.chkAll);
            //  addToBag = itemView.findViewById(R.id.btnAddtobags);
          //  Position = getAdapterPosition();
            itemView.setOnClickListener(this);
           //  viewList.add(itemView);
        }

        @Override
        public void onClick(View view) {
            changeViewCallBack.getPosition(getAdapterPosition());
        }
    }

    public interface ChangeViewCallBack{
        void getData(List<ProductBeanWithQnty> productBeanWithQnty,int position,String qty);
        void getPosition(int adapterPosition);
        void getList(ProductBeanWithQnty newProductList,String qty);
    }
}
