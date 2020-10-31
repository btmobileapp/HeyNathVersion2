package com.bt.heynath.shreemukhi;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlaybackControlView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bt.heynath.R;

public class GajalActivity extends AppCompatActivity {

    public static String menu;
    private SimpleExoPlayerView simpleExoPlayerView_Video;
    private SimpleExoPlayer player;
    private PlaybackControlView simpleExoPlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gajal);
       // Toolbar toolbar = findViewById(R.id.toolbar);
      //  setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView txt;
        txt=findViewById(R.id.txt);
        Typeface tf=Typeface.createFromAsset(this. getAssets(), "Roboto-Light.ttf");
        txt.setTypeface(tf);
        txt.setText("\t\tगीता के बारहवें अध्याय के एलोक से 8 तक के भावों पर गजल गीता नामक यह कविता पूज्य सेठजी\n" +
                "जयदयालजी गोयंका द्वारा रचित है ।रात्रि में सोते समय इसका पाठ करने से विशेष लाभ होगा, वे ऐसा\n" +
                "बताया करते थे।\n" +
                "\n" +
                "प्रथमहिं गुरू को शीश नवाऊं।\n" +
                "\n" +
                "हरि चरणों में ध्यान लगाऊँ ।। ।।\n" +
                "गजल सुनारऊँ अद्भुत यार।\n" +
                "\n" +
                "धारण से हो बेड़ा पार ।। 2।।\n" +
                "अर्जुन कहे सुनो भगवाना\n" +
                "\n" +
                "अपने रूप बताये नाना ।। 3।।\n" +
                "उनका मैं कछु भेद न जाना\n" +
                "\n" +
                "किरपा कर फिर कहो सुजाना ।। 4॥।\n" +
                "जो कोई तुमको नित ध्यावे\n" +
                "\n" +
                "भक्तिभाव से चित्त लगावे ।। 5।।\n" +
                "रात दिवस तुमरे गुण गावे\n" +
                "\n" +
                "तुमसे दूजा मन नहिं भावे ।। 6।।\n" +
                "तुमरा नाम जपे दिन रात ।\n" +
                "\n" +
                "और करे नहिं दूजी बात ।।7।।\n" +
                "दूजा निराकार को ध्यावे।\n" +
                "\n" +
                "अक्षर अलखा अनादि बतावे ।। 8।\n" +
                "दोनों ध्यान लगाने वाला ।\n" +
                "\n" +
                "उनमें कुण उत्तम नंदलाला ।। 9।।\n" +
                "अर्जुन से बोले भगवान ।\n" +
                "\n" +
                "सुन प्यारे कछु देकर ध्यान ।। 10।।\n" +
                "मेरा नाम जपै जपवाबे\n" +
                "\n" +
                "नेन्रों में प्रेमाभ्रू छावे ।। ॥।॥\n" +
                "मुझ बिन और कछू नहिं चावे ।");





        simpleExoPlayerView_Video = findViewById(R.id.exoplayer_learning_video);
        if(menu.equalsIgnoreCase("Gajal"))
        {
            getSupportActionBar().setTitle("गजल गीता");
            getVideoFile("http://btwebservices.biyanitechnologies.com/g1/GeetaApp/gajal.mp4");
        }
        else   if(menu.equalsIgnoreCase("Sar"))
        {
            getSupportActionBar().setTitle("गीता सार");
            getVideoFile("http://btwebservices.biyanitechnologies.com/g1/GeetaApp/sar.mp4");
            txt.setText("\t\tसांसारिक मोहके कारण ही मनुष्य 'मैं क्\u200Dया करूँ और क्या नहीं करूँ इस दुविधा\n" +
                    "में फँसकर कर्तव्यच्युत हो जाता है। अत: मोह या सुखासक्ति के वशीभूत नहीं होना\n" +
                    "चाहिए |\n" +
                    "2. शरीर नाशवान्\u200C हैं और उसे जानने वाला शरीरी अविनाशी है- इस विवेक को महत्व\n" +
                    "देना और अपने कर्तव्यका पालन करना- इन दोनों में से किसी भी एक उपाय को\n" +
                    "काम में लाने से चिन्ता-शोक मिट जाते हैं।\n" +
                    "3. निष्कामभावपूर्वक केवल दूसरों के हित के लिये अपने कर्तव्य का तत्परता से पालन\n" +
                    "करनेमात्रसे कल्याण हो जाता हैं|\n" +
                    "4. कर्मबन्धनसे छूटनेके दो उपाय हैं- कर्मोके तत्वको जानकर निःस्वार्थभावसे कर्म करना\n" +
                    "और तत्वज्ञान का अनुभव करना |\n" +
                    "5. मनुष्य को अनुकूल-प्रतिकूल परिस्थितियों के आने पर सुखी-दुःखी नहीं होना चाहिये\n" +
                    "क्योंकि इनसे सुखी-दुखी होने वाला मनुष्य संसार से ऊँचा उठकर परम आनन्दका\n" +
                    "अनुभव नहीं कर सकता |\n" +
                    "6. किसी भी साधन से अन्तःकरण में समता आनी चाहिये। समता आये बिना मनुष्य\n" +
                    "सर्वथा निर्विकार नहीं हो सकता |\n" +
                    "7. सब कुछ भगवान्\u200C ही हैं- ऐसा स्वीकार कर लेना सर्वश्रेष्ठ साधन हैं|\n" +
                    "8. अन्तकालीन चिन्तन के अनुसार ही जीव की गति होती है। अत: मनुष्य को हर दम\n" +
                    "भगवान्\u200C का स्मरण करते हुए अपने कर्तव्यका पालन करना चाहिये, जिससे\n" +
                    "अन्तकाल में भगवान्\u200C की स्मृति बनी रहे।\n" +
                    "9. सभी मनुष्य भगवत्प्राप्तिके अधिकारी हैं, चाहे वे किसी भी वर्ण, आश्रम, सम्प्रदाय\n" +
                    "देश, वेश आदि के क्\u200Dयों न हो |\n" +
                    "0. संसार में जहाँ भी विलक्षणता, विशेषता, सुन्दरता, महता, विद्वता आदि दिखे, उसको\n" +
                    "भगवान्\u200Cका ही मानकर भगवानका ही चिन्तन करना चाहिये |\n" +
                    "4. इस जगत्\u200Cको भगवान का ही स्वरूप मानकर प्रत्येक मनुष्य भगवान्\u200Cके विराटरूपके\n" +
                    "दर्शन कर सकता है|\n" +
                    "2. जो भक्त शरीर-इन्द्रियाँ-गन-बुद्धिसहित अपने आपको भगवान्\u200C के अर्पण कर देता है,\n" +
                    "वह भगवानूको प्रिय होता है।\n" +
                    "3. संसार में एक परमात्मतत्व ही जानने योग्य है। उसको जानने पर अमरता की प्राप्ति हो\n" +
                    "जाती है।\n");
        }

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
    public void finish() {
        super.finish();
        try {
            player.stop();
        }
        catch (Exception ex)
        {}
    }

    ProgressDialog pd;
    private void getVideoFile(String uploadFile)
    {
        pd=new ProgressDialog(this);
        pd.setTitle("कृपया वीडियो लोड करने की प्रतीक्षा करें");
        pd.show();
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory audioTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector =
                new DefaultTrackSelector(audioTrackSelectionFactory);
        //Initialize the player
        player = ExoPlayerFactory.newSimpleInstance(this, trackSelector);
        //Initialize simpleExoPlayerView
        SimpleExoPlayerView simpleExoPlayerView = simpleExoPlayerView_Video;
        simpleExoPlayerView.setPlayer((player));
        // Produces DataSource instances through which media data is loaded.
        DataSource.Factory dataSourceFactory =
                new DefaultDataSourceFactory(this, Util.getUserAgent(this, "CloudinaryExoplayer"));
        // Produces Extractor instances for parsing the media data.
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        // This is the MediaSource representing the media to be played.
        Uri audioUri = Uri.parse(uploadFile);
        MediaSource audioSource = new ExtractorMediaSource(audioUri,
                dataSourceFactory, extractorsFactory, null, null);
        // Prepare the player with the source.
        player.prepare(audioSource);
        player.setPlayWhenReady(true);


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(pd.isShowing())
                                pd.dismiss();

                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

}
