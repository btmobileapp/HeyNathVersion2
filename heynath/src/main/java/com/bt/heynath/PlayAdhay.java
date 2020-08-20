package com.bt.heynath;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.VideoView;

public class PlayAdhay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_adhay);
        VideoView videoView = findViewById(R.id.vd);
        videoView.setVideoPath("http://btwebservices.biyanitechnologies.com/dealerapp/rm.mp4");
        videoView.start();
    }
}
