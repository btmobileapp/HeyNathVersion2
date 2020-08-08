package biyaniparker.com.parker.view.homeuser.productdshopping;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import org.json.JSONException;

import java.util.ArrayList;

import biyaniparker.com.parker.R;
import biyaniparker.com.parker.bal.ModuleCategory;
import biyaniparker.com.parker.bal.ModuleProductDetails;
import biyaniparker.com.parker.bal.ModuleUserProduct;
import biyaniparker.com.parker.beans.ProductBeanWithQnty;
import biyaniparker.com.parker.beans.ProductStockBean;
import biyaniparker.com.parker.beans.StockMasterBean;
import biyaniparker.com.parker.utilities.CommonUtilities;
import biyaniparker.com.parker.utilities.DownloadUtility;
import biyaniparker.com.parker.utilities.NotifyCallback;
import biyaniparker.com.parker.utilities.UserUtilities;
import biyaniparker.com.parker.utilities.serverutilities.ConnectionDetector;
import biyaniparker.com.parker.view.adapter.ChangeViewAdapter;
import biyaniparker.com.parker.view.adapter.ProductRandomAdapter;
import biyaniparker.com.parker.view.homeuser.UserHomeScreen;

public class ChangeView extends AppCompatActivity implements DownloadUtility, NotifyCallback, ChangeViewAdapter.ChangeViewCallBack, ChangeViewAdapter.CallBack {
    RecyclerView recyclerView;
    ModuleUserProduct moduleUserProduct;
    ModuleCategory moduleCategory;
    int catId;
    ChangeViewAdapter adapter;
    ArrayList<StockMasterBean> stockList=new ArrayList<>();
    ModuleProductDetails moduleProductDetails;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_view);
        moduleProductDetails=new ModuleProductDetails(this);
        recyclerView = findViewById(R.id.recycleview_change);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        Intent intent=getIntent();
        moduleCategory=new ModuleCategory(this);
        catId=intent.getIntExtra("CategoryId",0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setSubtitle(moduleCategory.getCategoryBeanById(catId).getCategoryName());
        getSupportActionBar().setHomeButtonEnabled(true);
        moduleUserProduct=new ModuleUserProduct(this) ;
       // gridView = findViewById(R.id.gridView1);
        adapter = new ChangeViewAdapter(this,moduleUserProduct.newProductList,ChangeView.this,ChangeView.this);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);

        if(new ConnectionDetector(this).isConnectingToInternet())
        {
            //moduleUserProduct.loadFromDb(catId);
            // moduleUserProduct.getUserProducts(catId);
            moduleUserProduct.getUserProductsWithNotify(catId);

        }
        else
        {
            moduleUserProduct.loadFromDb(catId);
        }
    }

    @Override
    public void onComplete(String str, int requestCode, int responseCode) {
        if(requestCode==2&&responseCode==200)
        {
            try
            {
                adapter.notifyDataSetChanged();
            }
            catch (Exception e)
            {
            }
        }
    }

    @Override
    public void finish() {
        super.finish();
        moduleUserProduct.stopAsyncWork();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void notifyToActivity() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });
        int size=  moduleUserProduct.newProductList.size();
    }

    @Override
    public void getData(ProductBeanWithQnty productBeanWithQnty, String qty) {
//        if(validation(qty,productBeanWithQnty))
//        {
//            if(stockList.isEmpty())
//            {
//                Toast.makeText(getApplicationContext()," Please enter stock quantity ..  ",Toast.LENGTH_SHORT).show();
//            }
//            else
//            {
//                AlertDialog.Builder alertDialog=new AlertDialog.Builder(this);
//                alertDialog.setTitle(getString(R.string.app_name));
//                alertDialog.setMessage("Do you want to add these products in bags");
//                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        try
//                        {
//                            moduleProductDetails.addToBag1(stockList);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
//                alertDialog.setNegativeButton("No", null);
//                /*alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
//                    @Override
//                    public void onCancel(DialogInterface dialog) {
//
//                    }
//                })*/
//                alertDialog.show();
//            }
//        }
//        else
//        {
//            Toast.makeText(this,"Please enter valid quantity ...  ",Toast.LENGTH_SHORT).show();
//        }
    }

    @Override
    public void getPosition(int position) {
        Intent intent=new Intent(ChangeView.this, ImageClass.class);
        Gson gson = new Gson();
        String myJson = gson.toJson(moduleUserProduct.newProductList.get(position));
        intent.putExtra("myjson",myJson);
        startActivity(intent);
    }

    private boolean validation(String qty, ProductBeanWithQnty productBeanWithQnty) {
        if(new ConnectionDetector(this).isConnectingToInternet()) {
            moduleProductDetails.syncProduct();
        }
        for(int i=0;i<moduleProductDetails.stockList.size();i++) {
            ProductStockBean stock = moduleProductDetails.stockList.get(i);
            StockMasterBean s = new StockMasterBean();
            String val = qty;
            if (val.equals("") || val.equals("0")) {
                int c = 0;
            }
            else if (stock.getQnty() < Integer.parseInt(val)) {
                return false;
            }
            else {
                s.setProductId(productBeanWithQnty.getProductId());
                s.setInBagQty(Integer.parseInt(val));
                s.setSizeId(stock.getSizeId());
                s.setTransactionType("inbag");
                s.setDeleteStatus("false");
                s.setUserId(UserUtilities.getUserId(this));
                s.setChangedBY(UserUtilities.getUserId(this));
                s.setEnterBy(UserUtilities.getUserId(this));
                s.setClientId(UserUtilities.getClientId(this));
                s.setUnitName(productBeanWithQnty.getUnitName());
                s.setRemark(productBeanWithQnty.getRemark());
                stockList.add(s);
            }
        }
        return true;
    }
}
