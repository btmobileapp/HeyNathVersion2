package com.bt.heynath;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class NathKiPukarSubmenu extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ArrayList<String> menus=new ArrayList<>();
    ListView listview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nitya_stusti_submenu);
        listview=findViewById(R.id.listview);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("हे नाथ की पुकार");
        getSupportActionBar().setSubtitle("पू. महाराज जी की वाणी में");
//
        menus.add("पुकार की महिमा ");
        menus.add("हे नाथ की पुकार -समयबध्दक");
        SubmenuAdapter adapter=new SubmenuAdapter(this,menus);
        listview.setAdapter(new ArrayAdapter(this,android.R.layout.simple_list_item_1,menus));
        listview.setOnItemClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if(i==0)
        {
            startActivity(new Intent(this, Viewpdf.class));
        }
        if(i==1)
        {
            startActivity(new Intent(this, Scheduler.class));
        }
    }
}
