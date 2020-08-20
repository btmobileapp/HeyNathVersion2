package com.bt.heynath;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private GridView gv;

    ArrayList<String>  menus=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gv = (GridView) findViewById(R.id.gridview);
        menus.add("नित्य स्तुति");
        menus.add("समयबद्धक");
        menus.add("परिचय");

        menus.add("अधयाय १");
        menus.add("अधयाय २");
        menus.add("अधयाय ३");
        menus.add("अधयाय ४");
        menus.add("अधयाय ५");
        menus.add("अधयाय ६");



        MenuAdapter adapter=new MenuAdapter(this,menus);
        gv.setAdapter(adapter);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0)
                {
                    startActivity(new Intent(MainActivity.this,NityaStuti.class));
                }
                else if(i==1)
                {
                    startActivity(new Intent(MainActivity.this,Scheduler.class));
                }
                else if(i==3)
                {
                    startActivity(new Intent(MainActivity.this,  AdhayList.class));
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu1,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.action_settings)
        {
            startActivity(new Intent(this,LoginActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}
