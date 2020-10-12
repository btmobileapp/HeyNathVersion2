package com.bt.heynath;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bt.heynath.reciever.AlarmService;
import com.bt.heynath.reciever.AlramUtility;
import com.bt.heynath.reciever.BootReciever;

import java.util.ArrayList;
import java.util.Calendar;

public class Scheduler extends AppCompatActivity {

    Switch switch1,switch2;
    Button btnFromTime,btnToTime,btnSave;
    Spinner ed;
    ImageView ic_MuteRes;
    TextView txtFrom;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduler);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("है नाथ की पुकार");
        getSupportActionBar().setSubtitle("पु. महाराज जी की वाणी में");
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

        txtFrom=findViewById(R.id.txtFrom);
        TextView txt1=findViewById(R.id.txt1);
        TextView txt2=findViewById(R.id.txt2);
        TextView txt3=findViewById(R.id.txt3);
        TextView txtTo=findViewById(R.id.txtTo);
        TextView txt4=findViewById(R.id.txt4);
        TextView txt5=findViewById(R.id.txt5);
        TextView txt6=findViewById(R.id.txt6);


        Typeface tf=Typeface.createFromAsset(getAssets(), "Roboto-Light.ttf");
        txtFrom.setTypeface(tf);
        txtTo.setTypeface(tf);
        txt1.setTypeface(tf);
        txt1.setTypeface(tf);txt2.setTypeface(tf);txt3.setTypeface(tf);txt4.setTypeface(tf);txt5.setTypeface(tf);
        txt6.setTypeface(tf);

        hideatInItInputBoard(this);
        hideSoftKeyBord(this);

        minuteList.add("15 Min.");
        minuteList.add("30 Min.");
        minuteList.add("45 Min.");
        minuteList.add("1 Hour.");
        minuteList.add("2 Hour.");
        minuteList.add("3 Hour.");
        minuteList.add("4 Hour.");

        ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line,minuteList);
        ed.setAdapter(adapter);
        init();
    }

    ArrayList<String>  minuteList=new ArrayList<>();
    @Override
    protected void onResume() {
        super.onResume();

        try {
            long min= AlramUtility.getIntervalTimeInMinute(this);
            if(min==15)
                ed.setSelection(0);
            if(min==30)
                ed.setSelection(1);
            if(min==45)
                ed.setSelection(2);
            if(min==60)
                ed.setSelection(3);
            if(min==120 )
                ed.setSelection(4);
            if(min==180)
                ed.setSelection(5);
            if(min==240)
                ed.setSelection(6);

        }
        catch (Exception ex)
        {}


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
       // ed.setText(AlramUtility.getIntervalTimeInMinute(this)+"");
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
        Minute=15;
        if(ed.getSelectedItemPosition()==0)
            Minute=15;
        if(ed.getSelectedItemPosition()==1)
            Minute=30;
        if(ed.getSelectedItemPosition()==2)
            Minute=45;
        if(ed.getSelectedItemPosition()==3)
            Minute=60;
        if(ed.getSelectedItemPosition()==4)
            Minute=120;
        if(ed.getSelectedItemPosition()==5)
            Minute=180;
        if(ed.getSelectedItemPosition()==6)
            Minute=240;
        SharedPreferences sh=getSharedPreferences("Scheduler",MODE_PRIVATE);
        SharedPreferences.Editor editor= sh.edit();
        editor.putInt("TimeInteval",Minute);
        editor.putString("FromTime",btnFromTime.getText().toString());
        editor.putString("ToTime",btnToTime.getText().toString());
        editor.putBoolean("Mute",switch2.isChecked());
        editor.putBoolean("Start",switch1.isChecked());
        editor.commit();

        AlertDialog.Builder alert=new AlertDialog.Builder(this);
      //  alert.setTitle("है नाथ की पुकार");
        alert.setMessage("है नाथ की पुकार की अनुसूची सफल हुई");
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


    public static void hideatInItInputBoard(Activity context) {
        context.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    public static void hideSoftKeyBord(Activity context) {
        InputMethodManager inputManager = (InputMethodManager)
                context.getSystemService(Context.INPUT_METHOD_SERVICE);

        // check if no view has focus:
        View v = context.getCurrentFocus();
        if (v == null)
            return;


        inputManager.hideSoftInputFromWindow(v.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
