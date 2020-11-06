package com.bt.heynath;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bt.heynath.reciever.JobPlayMorningStuti;
import com.bt.heynath.reciever.PlayMorningStuti;
import com.bt.heynath.scheduler.MyMorningWorker;

public class MorningStutiWithUiBinber extends AppCompatActivity {

    ImageView imgPlayer;
    public static MediaPlayer player;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_morning_stuti_with_ui_binber);
        TextView txt = findViewById(R.id.txt);
        txt.setText("नित्य स्तुति सुने - भाग १");

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("नित्य स्तुति सुने");

        imgPlayer=findViewById(R.id.imgPlayer);


        player= PlayMorningStuti.player;
        try {
            if(player==null  || !player.isPlaying())
            {
                player= MyMorningWorker.mediaPlayer;
                if(player==null  || !player.isPlaying())
                {
                    player= JobPlayMorningStuti.player;
                }
            }

        }
        catch (Exception ex)
        {}

        if(player.isPlaying())
        {
            imgPlayer.setImageResource(R.drawable.pause);
        }
        else
            {
                imgPlayer.setImageResource(R.drawable.play);
        }


        imgPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playPause();
            }
        });

    }
    void playPause()
    {
        if(player.isPlaying())
        {
            imgPlayer.setImageResource(R.drawable.start);
            player.pause();
        }
        else
        {
            imgPlayer.setImageResource(R.drawable.pause);
            player.start();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        //Handle intent here...
    }

}