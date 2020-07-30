package biyaniparker.com.parker.view.unitmaster;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import biyaniparker.com.parker.R;
import biyaniparker.com.parker.bal.ModuleUser;
import biyaniparker.com.parker.beans.ShopMaster;
import biyaniparker.com.parker.beans.UnitMasterBean;
import biyaniparker.com.parker.beans.UserBean;
import biyaniparker.com.parker.utilities.CommonUtilities;
import biyaniparker.com.parker.utilities.DownloadUtility;
import biyaniparker.com.parker.utilities.serverutilities.AsyncUtilities;
import biyaniparker.com.parker.utilities.serverutilities.ConnectionDetector;

public class CreateUnitMaster extends AppCompatActivity implements View.OnClickListener, DownloadUtility {
    UnitMasterBean unitMasterBean;
    EditText etUnitName,etUnitType,etRemark;
    Button btnSaveUnitMaster;
  //  Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_unit_master);
        init();
        getSupportActionBar().setTitle("Create Unit Master");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        CommonUtilities.hideatInItInputBoard(this);
    }

    private void init() {
        etUnitName = findViewById(R.id.etUnitName);
        etUnitType = findViewById(R.id.etUnitType);
        etRemark = findViewById(R.id.etRemark);
        btnSaveUnitMaster = findViewById(R.id.btnsaveUnit);
        btnSaveUnitMaster.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        CommonUtilities.hideSoftKeyBord(this);
        if (!new ConnectionDetector(this).isConnectingToInternet()) {
            Toast.makeText(this, "Check Internet Connection", Toast.LENGTH_LONG).show();
        } else {
            if (validation()){
                unitMasterBean = new UnitMasterBean();
                unitMasterBean.setUnitName(etUnitName.getText().toString());
                unitMasterBean.setUnitType(etUnitType.getText().toString());
                unitMasterBean.setRemark(etRemark.getText().toString());

                insertUnitMaster(unitMasterBean);
            }
        }
    }

    private void insertUnitMaster(UnitMasterBean unitMasterBean) {
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("UnitName", unitMasterBean.getUnitName());
            jsonObject.put("UnitType", unitMasterBean.getUnitType());
            jsonObject.put("Remark", unitMasterBean.getRemark());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AsyncUtilities asyncUtilities=new AsyncUtilities(CreateUnitMaster.this,true, CommonUtilities.URL+"ProductService.svc/SaveUnitMaster",jsonObject.toString(),1,this);
        asyncUtilities.execute();
    }

    private boolean validation() {
        if (etUnitName.getText().toString().isEmpty() ||
               etRemark.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"Please enter all required fields",Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            return true;
        }
    }

    @Override
    public void onComplete(String str, int requestCode, int responseCode) {
        if (requestCode==1){
            //DownloadUtility downloadUtility = (DownloadUtility) getApplicationContext();
            if (responseCode == 200){
                Toast.makeText(getApplicationContext(),"Saved",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CreateUnitMaster.this,UnitMasterListView.class);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(),"failed to save",Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(),"Server Communication failed",Toast.LENGTH_SHORT).show();
        }
    }
}
