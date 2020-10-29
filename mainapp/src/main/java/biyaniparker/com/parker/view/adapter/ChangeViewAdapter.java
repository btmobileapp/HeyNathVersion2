package biyaniparker.com.parker.view.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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
    boolean isSelectedAll;
    boolean select;

    public  ChangeViewAdapter(Context context,ArrayList<ProductBeanWithQnty> list,ChangeViewCallBack ChangeViewCallBack){
        this.context = context;
        this.newProductList = list;
        this.changeViewCallBack = ChangeViewCallBack;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.change_view_adapter_list, null, false);
      //  viewList.add(itemView);
        return new ViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull final ChangeViewAdapter.ViewHolder holder, final int position) {
        final ProductBeanWithQnty productBeanWithQnty = newProductList.get(position);
        holder.setIsRecyclable(false);
//
//        if (position > 0) {
//            holder.cbSelectAll.setVisibility(View.GONE);
//        } else {
//            holder.cbSelectAll.setVisibility(View.VISIBLE);
//        }
        try {
            if (newProductList.size() == 1) {
                holder.cbSelectAll.setVisibility(View.GONE);
            }
            if (productBeanWithQnty.getIconThumb() == "") {
                holder.imageView.setImageResource(R.drawable.bgchoice);
            } else {
                Picasso.get().load(productBeanWithQnty.getIconThumb()).into(holder.imageView);
                // Picasso.get().load(productBeanWithQnty.get(position).get()).placeholder(R.drawable.bgparker).into(holder.imageView);
            }
        }
        catch (Exception ex){}

        try
        {
            double price = productBeanWithQnty.price;     //itemDAOPrice.getPriceBeanByPriceId(rowItem.getPriceId()).consumerPrice;
            DecimalFormat df = new DecimalFormat("#.##");
            holder.tv2.setText("₹. " + df.format(price));
        } catch (Exception e) {
            holder.tv2.setText("₹. ");
        }
        // holder.tv2.setText(Integer.toString(productBeanWithQnty.getPriceId()));
        holder.tv3.setText(productBeanWithQnty.getUnitName());
        holder.tv4.setText(productBeanWithQnty.getRemark());
        holder.tv5.setText(productBeanWithQnty.getProductName());
        //   holder.et.setText(holder.et.getText().toString());
        // holder.tv5.setText(moduleProductDetails.stockList.get(position).getQnty() + "");
        // holder.tv5.setText(Integer.toString(productBeanWithQnty.getQnt()));

        if (productBeanWithQnty.getCheckValue()==true){
            holder.checkBox.setChecked(true);
        }
//        else if (productBeanWithQnty.getCheckValue()==false){
//            holder.checkBox.setChecked(false);
//        }
        else if (!isSelectedAll){
            holder.checkBox.setChecked(false);
        } else
            holder.checkBox.setChecked(true);

        holder.et.setText(productBeanWithQnty.getQuantity());
        holder.et.setTag(productBeanWithQnty);
        holder.et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                AlertDialog.Builder alert=new AlertDialog.Builder(context);
                alert.setTitle("Enter Order Quantity");
                LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View mView=inflater.inflate(R.layout.dialogue_order_qty,null);
                final EditText editText=mView.findViewById(R.id.editText);

                alert.setView(mView);

                alert.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String q=  editText.getText().toString();
                        if(q.length()>0)
                        {
                            try
                            {
                                int qty = Integer.parseInt(q);
                                if(qty>0) {
                                    ProductBeanWithQnty obj = (ProductBeanWithQnty) v.getTag();
                                    obj.quantity = qty + "";
                                    obj.checkValue = true;
                                    notifyDataSetChanged();
                                }
                                else
                                {
                                    ProductBeanWithQnty obj = (ProductBeanWithQnty) v.getTag();
                                    obj.quantity = qty + "";
                                    obj.checkValue = false;
                                    notifyDataSetChanged();
                                }
                            }
                            catch (Exception ex){}
                        }
                    }
                });
                alert.setNegativeButton("Cancel",null);
                alert.show();
                editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        editText.post(new Runnable() {
                            @Override
                            public void run() {
                                InputMethodManager inputMethodManager= (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                                inputMethodManager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
                            }
                        });
                    }
                });
                editText.requestFocus();

            }
        });

        // newProductList.add(productBeanWithQnty);

//        holder.addToBag.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                changeViewCallBack.getData(productBeanWithQnty,holder.et.getText().toString());
//            }
//        });


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

//        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                {
//                    //holder.checkBox.setChecked(true);
//                    // write here your code for example ...
//                    //int qty = Integer.parseInt(holder.et.getText().toString());
////                            if (qty == 0) {
////                                Toast.makeText(context, "please enter valid stock quantity", Toast.LENGTH_SHORT).show();
////                                // changeViewCallBack.getData(newProductList,position,holder.et.getText().toString());
////                                //holder.checkBox.setChecked(false);
////                                // holder.checkBox.setOnCheckedChangeListener(this);
////                            }
//
//                    //newProductList.add(productBeanWithQnty);
//                    // changeViewCallBack.getData(newProductList, position, holder.et.getText().toString());
//
//                        if (holder.checkBox.isChecked()){
//                            productBeanWithQnty.setCheckValue(true);
//                        }
//                }
//            }
//});

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.checkBox.isChecked()){
                    productBeanWithQnty.setCheckValue(true);
                } else {
                    productBeanWithQnty.setCheckValue(false);
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

//        holder.et.addTextChangedListener(new TextWatcher() {
//     @Override
//     public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//     }

//     @Override
//     public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (holder.checkBox.isChecked()) {
//                     changeViewCallBack.getData(newProductList,position,holder.et.getText().toString());
//
//                }else {
//                        for (int i=0;i<newProductList.size();i++){
//                        changeViewCallBack.getList(newProductList.get(i), holder.et.getText().toString());
//                        }
//                }
//             if (holder.cbSelectAll.isChecked()) {
//                 {
//                     for (int i = 0; i < viewList.size(); i++) {
//                         View v = viewList.get(i);
//                         holder.checkBox = v.findViewById(R.id.chk);
//                         // holder.checkBox.setChecked(true);
//                         holder.et = v.findViewById(R.id.tvQtyChange);
//                         int qty = Integer.parseInt(holder.et.getText().toString());
//
//                         if (qty > 0) {
//                             // holder.checkBox.setChecked(true);
//                             changeViewCallBack.getList(newProductList.get(i), holder.et.getText().toString());
//                         } else {
//
//                         }
//                     }
//                 }
//             }
//         }
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });

//        holder.cbSelectAll.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        //is chkIos checked?
//                         {
////                             for (int i = 0; i <viewList.size(); i++)
////                            {
////                                v = viewList.get(i);
////                                holder.checkBox = v.findViewById(R.id.chk);
////                                holder.et = v.findViewById(R.id.tvQtyChange);
////                                if (Integer.parseInt(holder.et.getText().toString()) == 0) {
////                                    Toast.makeText(context, "please enter valid stock quantity", Toast.LENGTH_SHORT).show();
////                                   holder.checkBox.setChecked(true);
////                                  // holder.cbSelectAll.setChecked(false);
////                                }
////                                else {
////                                    holder.checkBox.setChecked(true);
////                                    changeViewCallBack.getList(newProductList.get(i), holder.et.getText().toString());
////                                }
////                            }
//                 if(holder.cbSelectAll.isChecked()){
//                     selectAll();
//                 }else {
//                     isSelectedAll = false;
//                     notifyDataSetChanged();
//                     if (!isSelectedAll){
//                         holder.checkBox.setChecked(true);
//                     } else
//                         holder.checkBox.setChecked(false);
//                 }
////                             holder.et.addTextChangedListener(new TextWatcher() {
////                                 @Override
////                                 public void beforeTextChanged(CharSequence s, int start, int count, int after) {
////
////                                 }
////
////                                 @Override
////                                 public void onTextChanged(CharSequence s, int start, int before, int count) {
////                                     holder.tv6.setText(s);
////                                     productBeanWithQnty.setQuantity(String.valueOf(s));
////                                 }
////
////                                 @Override
////                                 public void afterTextChanged(Editable s) {
////                                     if (s.length()!=0){
////                                         // changeViewCallBack.getData(newProductList, position, String.valueOf(s));
////                                     }
////                                 }
////                             });
//                         }
//                    }
//                });

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeViewCallBack.getPosition(position);
            }
        });

        if (!holder.checkBox.isChecked()){
            changeViewCallBack.getCheckBoxes();
        }
        
        if (holder.checkBox.isChecked()){
            changeViewCallBack.getOneCheckBox();
        }
        /*
        holder.et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //  holder.tv6.setText(s);
                if (s.length()!=0)
                {
                  productBeanWithQnty.setQuantity(String.valueOf(s));

                }
            }

            @Override
            public void afterTextChanged(Editable s)
            {
                if (s.length()!=0){
                    // changeViewCallBack.getData(newProductList, position, String.valueOf(s));
                    try
                    {
                        if( true )//startTime-System.currentTimeMillis()>1000)
                        {
                            if (Integer.parseInt(s.toString()) > 0) {
                                productBeanWithQnty.checkValue = true;
                                productBeanWithQnty.qnt = Integer.parseInt(s.toString());
                                notifyDataSetChanged();
                            } else {
                                productBeanWithQnty.checkValue = false;
                                productBeanWithQnty.qnt = 0;
                                notifyDataSetChanged();
                            }
                        }
                        startTime=System.currentTimeMillis();
                    }
                    catch (Exception ex){}
                }
            }
        });*/
    }

    long startTime=System.currentTimeMillis();

    @Override
    public int getItemCount() {
        return newProductList.size();
    }

    public void selectAll() {
        isSelectedAll = true;
        notifyDataSetChanged();
    }

    public void unselectAll() {
        isSelectedAll = false;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {
        ImageView imageView;
        TextView tv2,tv3,tv4,tv5,tv6;
        TextView et;
        Button addToBag;
        CheckBox checkBox,cbSelectAll;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.ivchange);

            tv2 = itemView.findViewById(R.id.tvPriceChange);
            et = itemView.findViewById(R.id.tvQtyChange);
            tv3 = itemView.findViewById(R.id.tvUnitChange);
            tv4 = itemView.findViewById(R.id.tvRemarkChange);
            tv5 = itemView.findViewById(R.id.txtName);
            checkBox = itemView.findViewById(R.id.chk);
          //  cbSelectAll = itemView.findViewById(R.id.chkAll);
           // tv6 = itemView.findViewById(R.id.s);
          }
        }

public interface ChangeViewCallBack{
    //void getData(List<ProductBeanWithQnty> productBeanWithQnty,int position,String qty);
    void getPosition(int adapterPosition);
    void getList(ProductBeanWithQnty newProductList,String qty);
    boolean getCheckBoxes();

    boolean getOneCheckBox();
}
}
