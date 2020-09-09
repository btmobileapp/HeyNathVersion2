package com.bt.heynath;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bt.heynath.reciever.AlarmService;
import com.bt.heynath.reciever.AlramUtility;
import com.bt.heynath.reciever.BootReciever;

import java.util.Calendar;

public class Scheduler extends AppCompatActivity {

    Switch switch1,switch2;
    Button btnFromTime,btnToTime,btnSave;
    EditText ed;
    ImageView ic_MuteRes;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduler);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("हे नाथ समयबद्धक");
        switch1=findViewById(R.id.switch1);
        switch2=findViewById(R.id.switch2);
        btnToTime=findViewById(R.id.btnToTime);
        btnFromTime=findViewById(R.id.btnFromTime);
        btnSave=findViewById(R.id.btnSave);
        btnFromTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTime(btnFromTime);
            }
        });
        btnToTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTime(btnToTime);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });
        ed=findViewById(R.id.ed);
        ic_MuteRes=findViewById(R.id.ic_MuteRes);
        init();

        switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {
                    ic_MuteRes.setImageResource(R.drawable.ic_mute);
                }
                else
                {
                    ic_MuteRes.setImageResource(R.drawable.inmute);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
    void setMuteIcon()
    {
        if(AlramUtility.isMute(this))
        {

        }
    }

    void init()
    {
        switch1.setChecked(AlramUtility.isStart(this));
        switch2.setChecked(AlramUtility.isMute(this));
        btnFromTime.setText(AlramUtility.getFromTime(this));
        btnToTime.setText(AlramUtility.getToTime(this));
        ed.setText(AlramUtility.getIntervalTimeInMinute(this)+"");
       if( AlramUtility.isMute(this))
       {
           ic_MuteRes.setImageResource(R.drawable.ic_mute);
       }
       else
       {
           ic_MuteRes.setImageResource(R.drawable.inmute);
       }
    }
    void save()
    {
        int Minute=0;
         if(ed.getText().toString().trim().equalsIgnoreCase(""))
         {
             Toast.makeText(this,
                     "कृपया समय अंतराल भरें", Toast.LENGTH_SHORT).show();
             return;
         }
         if(btnFromTime.getText().toString().equalsIgnoreCase("समय चुनें"))
         {

             Toast.makeText(this,
                     "कृपया समय से भरें", Toast.LENGTH_SHORT).show();
             return;
         }
        if(btnToTime.getText().toString().equalsIgnoreCase("समय चुनें"))
        {

            Toast.makeText(this,
                    "कृपया समय तक भरें", Toast.LENGTH_SHORT).show();
            return;
        }
        Minute=Integer.parseInt(ed.getText().toString());

        SharedPreferences sh=getSharedPreferences("Scheduler",MODE_PRIVATE);
        SharedPreferences.Editor editor= sh.edit();
        editor.putInt("TimeInteval",Minute);
        editor.putString("FromTime",btnFromTime.getText().toString());
        editor.putString("ToTime",btnToTime.getText().toString());
        editor.putBoolean("Mute",switch2.isChecked());
        editor.putBoolean("Start",switch1.isChecked());
        editor.commit();

        AlertDialog.Builder alert=new AlertDialog.Builder(this);
        alert.setTitle("हे नाथ समयबद्धक");
        alert.setMessage("नित्य स्तुति अनुसूची सफल हुई");
        alert.setCancelable(false);
        alert.setPositiveButton("ठीक है", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                registerBootReciever();
                Intent intent=new Intent();
                intent.setAction("com.bt.heynath.Check");
                sendBroadcast(intent);
                finish();
            }
        });
        alert.show();

    }
    BootReciever reciever;
    void registerBootReciever()
    {
        reciever=new BootReciever();
        registerReceiver(reciever,new IntentFilter("com.bt.heynath.Check"));
    }

    void setTime(final Button btnTime)
    {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(Scheduler.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                btnTime.setText( selectedHour + ":" + selectedMinute);
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();

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
