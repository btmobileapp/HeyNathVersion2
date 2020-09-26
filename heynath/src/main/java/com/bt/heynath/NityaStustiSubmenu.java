package com.bt.heynath;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.PrecomputedText;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class NityaStustiSubmenu extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ArrayList<String> menus=new ArrayList<>();
    ListView listview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nitya_stusti_submenu);
        listview=findViewById(R.id.listview);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("नित्य स्तुति");
//
        menus.add("नित्य स्तुति एवं प्रार्थना का महत्व");
        menus.add(" प्रार्थना पूज्य महाराज  जी श्री रामसुखदास जी की वाणी में (गीता जी श्लोक व प्रवचन)");
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
            startActivity(new Intent(this, PrathnaImportant.class));
        }
        if(i==1)
        {
            startActivity(new Intent(this, NityaStuti.class));
        }
    }
}
