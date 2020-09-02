package com.bt.heynath;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class NityaStuti extends AppCompatActivity {

    Button button,button1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nitya_stuti);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("नित्य सूचि");

        button=findViewById(R.id.button);
        button1=findViewById(R.id.button1);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sh=getSharedPreferences("Scheduler",MODE_PRIVATE);
                SharedPreferences.Editor editor= sh.edit();
                editor.putBoolean("NityaSuchi",true);

                editor.commit();
                AlertDialog.Builder alert=new AlertDialog.Builder(NityaStuti.this);
                alert.setMessage("नित्या स्तुति शुरू हो चुकी है");
                alert.setPositiveButton("ठीक है", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                alert.show();

            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sh=getSharedPreferences("Scheduler",MODE_PRIVATE);
                SharedPreferences.Editor editor= sh.edit();
                editor.putBoolean("NityaSuchi",true);
                editor.commit();

                AlertDialog.Builder alert=new AlertDialog.Builder(NityaStuti.this);
                alert.setMessage("नित्या स्तुति को रोक दिया गया है");
                alert.setPositiveButton("ठीक है", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                alert.show();
            }
        });


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
