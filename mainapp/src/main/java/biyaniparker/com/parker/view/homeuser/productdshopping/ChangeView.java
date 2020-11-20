package biyaniparker.com.parker.view.homeuser.productdshopping;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import biyaniparker.com.parker.R;
import biyaniparker.com.parker.bal.ModuleCategory;
import biyaniparker.com.parker.bal.ModuleProductDetails;
import biyaniparker.com.parker.bal.ModuleSizeMaster;
import biyaniparker.com.parker.bal.ModuleUserProduct;
import biyaniparker.com.parker.beans.ProductBeanWithQnty;
import biyaniparker.com.parker.beans.ProductStockBean;
import biyaniparker.com.parker.beans.SizeMaster;
import biyaniparker.com.parker.beans.StockMasterBean;
import biyaniparker.com.parker.database.ItemDAOSizeMaster;
import biyaniparker.com.parker.utilities.CommonUtilities;
import biyaniparker.com.parker.utilities.DownloadUtility;
import biyaniparker.com.parker.utilities.NotifyCallback;
import biyaniparker.com.parker.utilities.UserUtilities;
import biyaniparker.com.parker.utilities.serverutilities.ConnectionDetector;
import biyaniparker.com.parker.view.adapter.ChangeViewAdapter;
import biyaniparker.com.parker.view.homeuser.UserHomeScreen;
                                                                 //,SearchView.OnQueryTextListener
public class ChangeView extends AppCompatActivity implements DownloadUtility, NotifyCallback, ChangeViewAdapter.ChangeViewCallBack, CompoundButton.OnCheckedChangeListener,SearchView.OnQueryTextListener
{


  RecyclerView recyclerView;
  ModuleUserProduct moduleUserProduct;
  ModuleCategory moduleCategory;
  int catId;
  ChangeViewAdapter adapter;
  ArrayList<StockMasterBean> stockList=new ArrayList<>();
  ModuleProductDetails moduleProductDetails;
  ArrayList<SizeMaster> list;
  CheckBox checkAll;
  Button  btnAddToBag;
  AlertDialog.Builder alertDialog;

  //19-11-20
  SearchView editsearch;


  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_change_view);

    Toast.makeText(this, "ChangeView", Toast.LENGTH_SHORT).show();

    moduleProductDetails = new ModuleProductDetails(this);
    checkAll = (CheckBox) findViewById(R.id.chkAll);
    // linearLayout =(LinearLayout)findViewById(R.id.lmainchange);
    btnAddToBag = (Button) findViewById(R.id.btnAddtobags);
    // checkAll.setOnCheckedChangeListener(this);
    recyclerView = findViewById(R.id.recycleview_change);
    recyclerView.setLayoutManager(new LinearLayoutManager(ChangeView.this));
    Intent intent = getIntent();
    moduleCategory = new ModuleCategory(this);
    catId = intent.getIntExtra("CategoryId", 0);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setSubtitle(moduleCategory.getCategoryBeanById(catId).getCategoryName());
    getSupportActionBar().setHomeButtonEnabled(true);
    moduleUserProduct = new ModuleUserProduct(this);
    adapter = new ChangeViewAdapter(ChangeView.this, moduleUserProduct.newProductList, ChangeView.this);
    recyclerView.addItemDecoration(new DividerItemDecoration(ChangeView.this, LinearLayoutManager.VERTICAL));
    recyclerView.setAdapter(adapter);
    // recyclerView.getAdapter().getItemCount();

    //19-11-20
    editsearch = (SearchView) findViewById(R.id.simpleSearchView);
    editsearch.setOnQueryTextListener(this);



    if (new ConnectionDetector(this).isConnectingToInternet())
    {
      //moduleUserProduct.loadFromDb(catId);
      // moduleUserProduct.getUserProducts(catId);
      moduleUserProduct.getUserProductsWithNotify(catId);
    }
    else{

      moduleUserProduct.loadFromDb(catId);
    }

    checkAll.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (checkAll.isChecked()) {
          adapter.selectAll();
        } else {
          adapter.unselectAll();
        }
      }
    });


//        btnAddToBag.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                    for (int i = 0; i < moduleUserProduct.newProductList.size(); i++) {
//                        if (!getOneCheckBox() && !checkAll.isChecked()){
//                            Toast.makeText(getApplicationContext(), "please select", Toast.LENGTH_SHORT).show();
//                        }
//                        else if (getOneCheckBox() && moduleUserProduct.newProductList.get(i).getQuantity() == null) {
//                            Toast.makeText(getApplicationContext(), "please enter valid stock quantity", Toast.LENGTH_SHORT).show();
//                        }
//                       else if (moduleUserProduct.newProductList.get(i).getQuantity() == null) {
//                           // Toast.makeText(getApplicationContext(), "please enter valid stock quantity", Toast.LENGTH_SHORT).show();
//                        } else {
//                                getData(moduleUserProduct.newProductList.get(i), moduleUserProduct.newProductList.get(i).getQuantity());
//                                alertDialog = new AlertDialog.Builder(ChangeView.this);
//                                alertDialog.setTitle(getString(R.string.app_name));
//                                alertDialog.setMessage("Do you want to add these products in bags");
//                                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        try {
//                                            moduleProductDetails.addToBag1(stockList);
//                                        } catch (JSONException e) {
//                                            e.printStackTrace();
//                                        }
//                                    }
//                                });
//                                alertDialog.setNegativeButton("No ", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        dialog.cancel();
//                                    }
//                                });
//                                alertDialog.show();
//                            }
//                    }
//            }
//        });
//    }

    btnAddToBag.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view)
      {
        for (int i = 0; i < moduleUserProduct.newProductList.size(); i++) {
          if (moduleUserProduct.newProductList.get(i).getQuantity() == null) {

          }
          else {
            getData(moduleUserProduct.newProductList.get(i), moduleUserProduct.newProductList.get(i).getQuantity());

          }
        }

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ChangeView.this);
        alertDialog.setTitle(getString(R.string.app_name));
        alertDialog.setMessage("Do you want to add these products in bags");
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            try
            {
              moduleProductDetails.addToBag1(stockList);
            } catch (JSONException e)
            {
              e.printStackTrace();
            }
          }
        });
        //   alertDialog.setNegativeButton("No", null);
        alertDialog.setNegativeButton("No ", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
          }
        });

        alertDialog.show();
      }
    });
  }

  //19-11-20 2 method
  @Override
  public boolean onQueryTextSubmit(String query) {

    return false;
  }

  @Override
  public boolean onQueryTextChange(String newText) {
    String text = newText;
    adapter.filter(text);
    return false;
  }



  @Override
  public void onComplete(String str, int requestCode, int responseCode) {
    if(requestCode==2 &&responseCode==200)
    {
      try
      {
        adapter.notifyDataSetChanged();
      }
      catch (Exception e)
      {
      }
    }
    else if(requestCode==4 && responseCode==200)
    {
      if(str.equals("Success"))
      {
        Toast.makeText(getApplicationContext(),"Products Succesfully added to bag ",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, UserHomeScreen.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
            //callRefresh();
          }
        });
      }
    }
  }

  public boolean onOptionsItemSelected(MenuItem item)
  {
    if(item.getItemId()==android.R.id.home)
      onBackPressed();
    return super.onOptionsItemSelected(item);
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

  public void getData(ProductBeanWithQnty productBeanWithQnty,String qty) {
    CommonUtilities.hideSoftKeyBord(this);

    if(validation(productBeanWithQnty,qty))
    {

    }
  }


  @Override
  public void getPosition(int adapterPosition) {
    Intent intent=new Intent(ChangeView.this, ImageClass.class);
    Gson gson = new Gson();
    String myJson = gson.toJson(moduleUserProduct.newProductList.get(adapterPosition));
    intent.putExtra("myjson",myJson);
    startActivity(intent);
  }

  @Override
  public void getList(ProductBeanWithQnty productBeanWithQnty,String qty) {
    list = new ArrayList<>();
    ItemDAOSizeMaster itemDAOSizeMaster = new ItemDAOSizeMaster(getApplicationContext());
    list = itemDAOSizeMaster.getAllSize(UserUtilities.getClientId(getApplicationContext()));

    for (int i = 0; i < list.size(); i++)
    {
      StockMasterBean s = new StockMasterBean();
      int sizeId = list.get(i).getSizeId();
      s.setProductId(productBeanWithQnty.getProductId());
      s.setInBagQty(Integer.parseInt(qty));
      s.setSizeId(sizeId);
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
    btnAddToBag.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if(stockList.isEmpty())
        {
          Toast.makeText(getApplicationContext()," Please enter stock quantity ..  ",Toast.LENGTH_SHORT).show();
        }
        else
        {
          AlertDialog.Builder alertDialog=new AlertDialog.Builder(ChangeView.this);
          alertDialog.setTitle(getString(R.string.app_name));
          alertDialog.setMessage("Do you want to add these products in bags");
          alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              try
              {
                moduleProductDetails.addToBag1(stockList);
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
    });
  }

  @Override
  public boolean getCheckBoxes() {
    {
    }
    return false;
  }

  @Override
  public boolean getOneCheckBox() {
    return false;
  }


  private boolean validation(ProductBeanWithQnty productBeanWithQnty,String qty) {
    list = new ArrayList<>();
    ItemDAOSizeMaster itemDAOSizeMaster = new ItemDAOSizeMaster(getApplicationContext());
    list = itemDAOSizeMaster.getAllSize(UserUtilities.getClientId(getApplicationContext()));

    for (int i=0;i<list.size();i++){
      StockMasterBean s = new StockMasterBean();
      int sizeId = list.get(i).getSizeId();
      s.setProductId(productBeanWithQnty.getProductId());
      s.setInBagQty(Integer.parseInt(qty));
      s.setSizeId(sizeId);
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
    return true;
  }

  @Override
  public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

  }


}
