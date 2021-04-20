package com.bt.heynath;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Instructions extends AppCompatActivity {

    ArrayList<String> menus = new ArrayList<>();
    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nitya_stusti_submenu);
        listview = findViewById(R.id.listview);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("सुचना");
            //
        menus.add("मोबाइल रीस्टार्ट होने पे अप्प को एक बार खोलिये !");
        menus.add("रात को सोते वक्त अप्प को एक बार जरूर खोलिये !");
        menus.add("आपके मोबाइल में अप्प को ऑटोस्टार्ट की सूचि में अल्लोव करें !");

       // menus.add("नित्य पठनीय पांच श्लोक");

        SubmenuAdapter adapter = new SubmenuAdapter(this, menus);
        listview.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, menus));

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}