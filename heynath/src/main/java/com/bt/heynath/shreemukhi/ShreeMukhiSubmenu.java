package com.bt.heynath.shreemukhi;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bt.heynath.AdhayList;
import com.bt.heynath.NityaStuti;
import com.bt.heynath.PrathnaImportant;
import com.bt.heynath.R;
import com.bt.heynath.SubmenuAdapter;
import com.bt.heynath.Viewpdf;
import com.bt.heynath.pdf.Viewpdf1;

import java.util.ArrayList;

public class ShreeMukhiSubmenu extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ArrayList<String> menus=new ArrayList<>();
    ListView listview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nitya_stusti_submenu);
        listview=findViewById(R.id.listview);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("भगवान् के श्रीमुखी वाणी श्रीमद्भगिद्गीता");
//
        menus.add("गीता जी की महिमा");
        menus.add("गीता जी के १८ नाम");

        menus.add("नित्य पठनिय पांच श्लोक");
        menus.add("गीताजी के उच्चारण की विधि");
        menus.add("संपूर्ण गीता जी का सरल विधि से पठन पाठन");

        menus.add("गजल गीता");
        menus.add("विष्णु सहस्रनाम");
        menus.add("गीता सार");
        menus.add("महापुरुषो के विचार गीताजी के बारे में");
        menus.add("साधक संजीवनी एक प्रासादिक जी ग्रंथ ");
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
            /*
            ViewDetails.menu="Mahima";
            startActivity(new Intent(this, ViewDetails.class));
            */

            Viewpdf1.title=menus.get(i);
            Viewpdf1.no=8;
            startActivity(new Intent(this, Viewpdf1.class));
        }
        if(i==1)
        {
            /*
            ViewDetails.menu="18 Names";
            startActivity(new Intent(this, ViewDetails.class));
            */
            Viewpdf1.title=menus.get(i);
            Viewpdf1.no=11;
            startActivity(new Intent(this, Viewpdf1.class));
        }
        if(i==2)
        {
            /*
            ViewDetails.menu="5 Shlok";
            startActivity(new Intent(this, ViewDetails.class));
            */
            Viewpdf1.title=menus.get(i);
            Viewpdf1.no=12;
            startActivity(new Intent(this, Viewpdf1.class));
        }
        if(i==4)
        {    //!s6Kwjzw
             startActivity(new Intent(this, AdhayList.class));
        }
        if(i==5)
        {    //!s6Kwjzw
            GajalActivity.menu="Gajal";
            startActivity(new Intent(this, GajalActivity.class));
        }
        if(i==7)
        {
            GajalActivity.menu="Sar";
            startActivity(new Intent(this, GajalActivity.class));
        }
    }
}
