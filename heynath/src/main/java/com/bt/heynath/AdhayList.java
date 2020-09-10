package com.bt.heynath;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;

import java.util.ArrayList;

public class AdhayList extends AppCompatActivity {

    ArrayList<String> menus=new ArrayList<>();
    GridView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adhay_list);
        listView=findViewById(R.id.gridview);
        menus.add("अध्याय १");
        menus.add("अध्याय २");
        menus.add("अध्याय ३");
        menus.add("अध्याय ४");
        menus.add("अध्याय ५");
        menus.add("अध्याय ६");
        menus.add("अध्याय ७");
        menus.add("अध्याय ८");
        menus.add("अध्याय ९");
        menus.add("अध्याय १०");
        menus.add("अध्याय ११");
        menus.add("अध्याय १२");
        menus.add("अध्याय १३");
        menus.add("अध्याय १४");
        menus.add("अध्याय १५");
        menus.add("अध्याय १६");
        menus.add("अध्याय १७");
        menus.add("अध्याय १८");

        SubmenuAdapter adapter=new SubmenuAdapter(this,menus);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(AdhayList.this,PlayAdhay.class));
            }
        });

        //Intent intent=new Intent();
       // intent.setAction("com.bt.heynath.Check");
       // sendBroadcast(intent);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("अधयाय");



    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
