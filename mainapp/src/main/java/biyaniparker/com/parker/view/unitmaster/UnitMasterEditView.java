package biyaniparker.com.parker.view.unitmaster;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import biyaniparker.com.parker.R;
import biyaniparker.com.parker.beans.UnitMasterBean;
import biyaniparker.com.parker.utilities.CommonUtilities;
import biyaniparker.com.parker.utilities.DownloadUtility;
import biyaniparker.com.parker.utilities.serverutilities.AsyncUtilities;
import biyaniparker.com.parker.utilities.serverutilities.ConnectionDetector;

public class UnitMasterEditView extends AppCompatActivity implements View.OnClickListener, DownloadUtility {
      UnitMasterBean bean;
      EditText etUnitName,etUnitType,etRemark;
      Button btnUpdateUnit,btnDeleteUnit;
      UnitMasterBean unitMasterBean;
      int unitId;
      Context context;
      int flag = 0;
      public final static String PREFS_NAME = "appname_prefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.o_activity_edit_unit_master);
        init();
        getSupportActionBar().setTitle("Edit Unit Master");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        renderView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    private void init() {
        etUnitName = findViewById(R.id.ed_editUnitName);
      //  etUnitType = findViewById(R.id.ed_editUnitType);
        etRemark = findViewById(R.id.ed_editRemark);
        btnUpdateUnit = findViewById(R.id.btnUpdateUnit);
        btnDeleteUnit = findViewById(R.id.btnDeleteUnit);
        btnUpdateUnit.setOnClickListener(this);
        btnDeleteUnit.setOnClickListener(this);
    }

    private void renderView() {
        Intent intent = getIntent();
        unitId = intent.getIntExtra("UnitId", 0);

        SharedPreference sharedPreference = new SharedPreference(UnitMasterEditView.this);
        String response = sharedPreference.getStr("Response");

        try {
            JSONArray jsonarray = new JSONArray(response);
            for (int i=0;i<jsonarray.length();i++){
                JSONObject jsonobject = jsonarray.getJSONObject(i);
                if (unitId == jsonobject.getInt("UnitId")){
                    etUnitName.setText(jsonobject.getString("UnitName"));
                   // etUnitType.setText(jsonobject.getString("UnitType"));
                    etRemark.setText(jsonobject.getString("Remark"));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        CommonUtilities.hideSoftKeyBord(this);

        if(!new ConnectionDetector(this).isConnectingToInternet())
        {
            Toast.makeText(this,"Check Internet Connection",Toast.LENGTH_LONG).show();
        } else {

            if (btnUpdateUnit.isPressed()){
                if (validation()){
                    unitMasterBean = new UnitMasterBean();
                    unitMasterBean.setUnitId(unitId);
                    unitMasterBean.setUnitName(etUnitName.getText().toString());
//                    unitMasterBean.setUnitType(etUnitType.getText().toString());
                    unitMasterBean.setRemark(etRemark.getText().toString());
                    unitMasterBean.setDeleteStatus("false");
                    updateUnitmaster(unitMasterBean);
                }
            }  else if(btnDeleteUnit.getId()==view.getId())
            {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                alertDialog.setTitle(getString(R.string.app_name));
                alertDialog.setMessage("Are you sure to delete ? ");
                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        unitMasterBean = new UnitMasterBean();
                        SharedPreference sharedPreference = new SharedPreference(UnitMasterEditView.this);
                        String response = sharedPreference.getStr("Response");

                        try {
                            JSONArray jsonarray = new JSONArray(response);
                            for (int i=0;i<jsonarray.length();i++){
                                JSONObject jsonobject = jsonarray.getJSONObject(i);
                                if (unitId == jsonobject.getInt("UnitId")){
                                    unitMasterBean.setUnitId(jsonobject.getInt("UnitId"));
                                    unitMasterBean.setUnitName(jsonobject.getString("UnitName"));
                                    unitMasterBean.setUnitType(jsonobject.getString("UnitType"));
                                    unitMasterBean.setRemark(jsonobject.getString("Remark"));
                                    unitMasterBean.setDeleteStatus("true");
                                    updateUnitmaster(unitMasterBean);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });


                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.setCancelable(true);
                    }
                });

                alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                    }
                });
                alertDialog.show();
            }
        }
    }

    private void updateUnitmaster(UnitMasterBean unitMasterBean) {
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("UnitId",unitMasterBean.getUnitId());
            jsonObject.put("UnitName",unitMasterBean.getUnitName());
//            jsonObject.put("UnitType",unitMasterBean.getUnitType());
            jsonObject.put("Remark",unitMasterBean.getRemark());
            jsonObject.put("DeleteStatus",unitMasterBean.getDeleteStatus());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //update api here
        AsyncUtilities asyncUtilities=new AsyncUtilities(UnitMasterEditView.this,true, CommonUtilities.URL+"ProductService.svc/updateUnitMaster",jsonObject.toString(),1,this);
        asyncUtilities.execute();
    }

    private boolean validation() {
        if(etUnitName.getText().toString().isEmpty() ||
                etRemark.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"please enter all required fields",Toast.LENGTH_SHORT).show();
            return  false;
        }
        else
        {
            return true;
        }
    }

    @Override
    public void onComplete(String str, int requestCode, int responseCode) {
                if (requestCode==1){
                    if (responseCode == 200){
                        Toast.makeText(getApplicationContext(),"succesfull",Toast.LENGTH_SHORT).show();
                        finish();
                        Intent intent = new Intent(this,UnitMasterListView.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(),"failed",Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(),"Server Communication failed",Toast.LENGTH_SHORT).show();
                }
            }
        }
