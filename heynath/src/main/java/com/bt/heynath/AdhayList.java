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
        menus.add("अधयाय १");
        menus.add("अधयाय २");
        menus.add("अधयाय ३");
        menus.add("अधयाय ४");
        menus.add("अधयाय ५");
        menus.add("अधयाय ६");
        menus.add("अधयाय 7");
        menus.add("अधयाय 8");
        menus.add("अधयाय 9");
        menus.add("अधयाय 10");
        menus.add("अधयाय 11");
        menus.add("अधयाय 12");
        menus.add("अधयाय 13");
        menus.add("अधयाय 14");
        menus.add("अधयाय 15");
        menus.add("अधयाय 16");
        menus.add("अधयाय 17");
        menus.add("अधयाय 18");

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
        getSupportActionBar().setTitle("नित्य सूचि");

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
