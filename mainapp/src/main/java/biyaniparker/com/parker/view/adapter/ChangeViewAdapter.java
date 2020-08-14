package biyaniparker.com.parker.view.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
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
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import biyaniparker.com.parker.R;
import biyaniparker.com.parker.beans.ProductBeanWithQnty;

public class ChangeViewAdapter extends RecyclerView.Adapter<ChangeViewAdapter.ViewHolder> {
    public ArrayList<ProductBeanWithQnty> newProductList;
    Context context;
    ChangeViewCallBack changeViewCallBack;
    int Position;
    ArrayList<View> viewList = new ArrayList<View>();


    public  ChangeViewAdapter(Context context,ArrayList<ProductBeanWithQnty> list,ChangeViewCallBack ChangeViewCallBack){
        this.context = context;
        this.newProductList = list;
        this.changeViewCallBack = ChangeViewCallBack;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.change_view_adapter_list, parent, false);
        viewList.add(itemView);
        return new ViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull final ChangeViewAdapter.ViewHolder holder, final int position) {
        final ProductBeanWithQnty productBeanWithQnty = newProductList.get(position);

        if (position > 0) {
            holder.cbSelectAll.setVisibility(View.GONE);
        } else {
            //holder.cbSelectAll.setVisibility(View.VISIBLE);
        }
        if (newProductList.size() == 1) {
            holder.cbSelectAll.setVisibility(View.GONE);
        }
        if (productBeanWithQnty.getIconThumb() == "") {
            holder.imageView.setImageResource(R.drawable.bgchoice);
        } else {
            Picasso.get().load(productBeanWithQnty.getIconThumb()).into(holder.imageView);
            // Picasso.get().load(productBeanWithQnty.get(position).get()).placeholder(R.drawable.bgparker).into(holder.imageView);
        }
        holder.tv1.setText(productBeanWithQnty.getProductName());
        try {
            double price = productBeanWithQnty.price;     //itemDAOPrice.getPriceBeanByPriceId(rowItem.getPriceId()).consumerPrice;
            DecimalFormat df = new DecimalFormat("#.##");
            holder.tv2.setText("Rs. " + df.format(price));
        } catch (Exception e) {
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

        if (holder.cbSelectAll.isChecked() && holder.checkBox.isChecked()) {
            Toast.makeText(context, "please select any one of these", Toast.LENGTH_SHORT).show();
        }

//        holder.checkBox.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //is chkIos checked?
//                if (((CheckBox) v).isChecked()) {
//                    try {
//                        int qty = Integer.parseInt(holder.et.getText().toString());
//                        if (qty==0){
//                            Toast.makeText(context,"please enter valid stock quantity",Toast.LENGTH_SHORT).show();
//                           // changeViewCallBack.getData(newProductList,position,holder.et.getText().toString());
//                            holder.checkBox.setChecked(false);
//                        }
//                        else {
//                            //newProductList.add(productBeanWithQnty);
//                            changeViewCallBack.getData(newProductList,position,holder.et.getText().toString());
//                        }
//                    } catch (NumberFormatException e) {
//                        e.printStackTrace();
//                    }
//                }
//                else{
//
//                }
//            }
//        });



        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isPressed()) {
                    // write here your code for example ...
                    if (isChecked) {
                        try {
                            int qty = Integer.parseInt(holder.et.getText().toString());
                            if (qty == 0) {
                                Toast.makeText(context, "please enter valid stock quantity", Toast.LENGTH_SHORT).show();
                                // changeViewCallBack.getData(newProductList,position,holder.et.getText().toString());
                                //holder.checkBox.setChecked(false);
                                // holder.checkBox.setOnCheckedChangeListener(this);
                            } else {
                                //newProductList.add(productBeanWithQnty);
                                changeViewCallBack.getData(newProductList, position, holder.et.getText().toString());
                            }
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    } else {
                        // do somthing when is removed the check**strong text**
                    }
                }
            }
        });

//        holder.cbSelectAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (buttonView.isPressed()) {
//                    if (isChecked) {
//                        for (int i = 0; i < viewList.size(); i++) {
//                            View v = viewList.get(i);
//                            holder.checkBox = v.findViewById(R.id.chk);
//                            holder.et = v.findViewById(R.id.tvQtyChange);
//                            if (Integer.parseInt(holder.et.getText().toString()) == 0) {
//                                Toast.makeText(context, "please enter valid stock quantity", Toast.LENGTH_SHORT).show();
//                                holder.checkBox.setChecked(true);
//                                // holder.cbSelectAll.setChecked(false);
//                            } else {
//                                holder.checkBox.setChecked(true);
//                                changeViewCallBack.getList(newProductList.get(i), holder.et.getText().toString());
//                            }
//                        }
//                    } else {
//                        for (int i = 0; i < newProductList.size(); i++) {
//                            holder.checkBox.setChecked(false);
//                        }
//                    }
//                }
//            }
//        });

        holder.et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (holder.checkBox.isChecked()) {
                    try {
                        int qty = Integer.parseInt(holder.et.getText().toString());
                        if (qty > 0) {
                            changeViewCallBack.getData(newProductList, position, holder.et.getText().toString());
                        } else {
                            //newProductList.add(productBeanWithQnty);
                            // changeViewCallBack.getData(newProductList, position, holder.et.getText().toString());
                        }
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
                else if (holder.cbSelectAll.isChecked()){
                     {
                        for (int i = 0; i < viewList.size(); i++) {
                            View v = viewList.get(i);
                            holder.checkBox = v.findViewById(R.id.chk);
                           // holder.checkBox.setChecked(true);
                            holder.et = v.findViewById(R.id.tvQtyChange);
                            int qty = Integer.parseInt(holder.et.getText().toString());

                            if (qty>0) {
                               // holder.checkBox.setChecked(true);
                                changeViewCallBack.getList(newProductList.get(i), holder.et.getText().toString());
                            } else {

                            }
                        }
                    }
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        holder.cbSelectAll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //is chkIos checked?
                         {
                            for (int i = 0; i <viewList.size(); i++)
                            {
                                v = viewList.get(i);
                                holder.checkBox = v.findViewById(R.id.chk);
                                holder.et = v.findViewById(R.id.tvQtyChange);
                                if (Integer.parseInt(holder.et.getText().toString()) == 0) {
                                    Toast.makeText(context, "please enter valid stock quantity", Toast.LENGTH_SHORT).show();
                                   holder.checkBox.setChecked(true);
                                  // holder.cbSelectAll.setChecked(false);
                                }
                                else {
                                    holder.checkBox.setChecked(true);
                                    changeViewCallBack.getList(newProductList.get(i), holder.et.getText().toString());
                                }
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

    public class ViewHolder extends RecyclerView.ViewHolder  {
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
            viewList.addAll(Collections.singleton(itemView));
            //  addToBag = itemView.findViewById(R.id.btnAddtobags);
          //  Position = getAdapterPosition();
            // viewList.add(itemView);
        }
    }

    public interface ChangeViewCallBack{
        void getData(List<ProductBeanWithQnty> productBeanWithQnty,int position,String qty);
        void getPosition(int adapterPosition);
        void getList(ProductBeanWithQnty newProductList,String qty);
    }
}
