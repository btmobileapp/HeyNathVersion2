package biyaniparker.com.parker.view.unitmaster;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import biyaniparker.com.parker.R;
import biyaniparker.com.parker.beans.UnitMasterBean;
import biyaniparker.com.parker.utilities.CommonUtilities;
import biyaniparker.com.parker.utilities.DownloadUtility;
import biyaniparker.com.parker.utilities.serverutilities.AsyncUtilities;
import biyaniparker.com.parker.view.adapter.UnitMasterAdapter;
import biyaniparker.com.parker.view.adapter.UserAdapter;
import biyaniparker.com.parker.view.adapter.masteradapter;


public class UnitMasterListView extends AppCompatActivity implements DownloadUtility, masteradapter.AdapterCallBack {
  //  ListView listView;
    FloatingActionButton floatingActionButton;
    UnitMasterBean unitMasterBean;
    //UnitMasterAdapter unitMasterAdapter;
    RecyclerView recyclerView;
    Context context;
    //ArrayList<UnitMasterBean> unitMasterBeanList;
    public final static String PREFS_NAME = "rajashri_prefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.unit_master_list);
        getSupportActionBar().setTitle("Unit Master");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        CommonUtilities.hideatInItInputBoard(this);
        context = getApplicationContext();
       // listView = findViewById(R.id.listViewUserMaster);
        floatingActionButton = findViewById(R.id.fab_User);
        recyclerView = findViewById(R.id.rvUnitMaster);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
       // listView.setOnItemClickListener(this);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),CreateUnitMaster.class);
                startActivity(intent);
            }
        });
        getUnitMasterList();
    }

    public void getUnitMasterList() {
        AsyncUtilities serverAsync=new AsyncUtilities(UnitMasterListView.this,false, CommonUtilities.URL+"ProductService.svc/GetUnitMaster","",1,this);
        serverAsync.execute();
    }

    @Override
    public void onComplete(String str, int requestCode, int responseCode) {

        if (requestCode==1){
            List<UnitMasterBean> unitMasterBeanList = new ArrayList<>();
            if (responseCode == 200){
                try {
                    JSONArray jsonarray = new JSONArray(str);

                    SharedPreference sharedPreference = new SharedPreference(this);
                    sharedPreference.setStr("Response",str);

                    for (int i = 0; i < jsonarray.length(); i++) {
                        JSONObject jsonobject = jsonarray.getJSONObject(i);
                        int unitId = jsonobject.getInt("UnitId");
                        String unitName = jsonobject.getString("UnitName");
                        String unitType = jsonobject.getString("UnitType");
                        String remark = jsonobject.getString("Remark");

                        unitMasterBean = new UnitMasterBean();
                        unitMasterBean.setUnitId(unitId);
                        unitMasterBean.setUnitName(unitName);
                        unitMasterBean.setUnitType(unitType);
                        unitMasterBean.setRemark(remark);

                        unitMasterBeanList.add(unitMasterBean);
                    }
                    masteradapter master = new masteradapter(context,unitMasterBeanList,UnitMasterListView.this);
                    recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
                    recyclerView.setAdapter(master);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    public void getId(int unitId) {
        finish();
        Intent intent=new Intent(this,UnitMasterEditView.class);
        intent.putExtra("UnitId", unitId);
        startActivity(intent);
    }

//    @Override
//    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//        UnitMasterBean bean=new UnitMasterBean();
//        bean=(UnitMasterBean) unitMasterAdapter.getItem(position);
//        Intent intent=new Intent(this,UnitMasterEditView.class);
//        intent.putExtra("UnitId", bean.getUnitId());
//        startActivity(intent);
//    }
}
