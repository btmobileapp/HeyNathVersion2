package com.bt.heynath.shreemukhi;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;
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
        txt.setText("गीता के बारहवे अध्याय के श्लोक 1 से 8 तक के भावों पर गजल गीता नामक यह कविता पूज्यसेठजी जयदयालजी गोयंका द्वारा रचित है। रात्रि में सोते समय इसका पाठ करने से विशेष लाभ होगा, वे ऐसा बताया करते थे।\n" +
                "\n" +
                "प्रथमहिं गुरुको शीश नवाऊँ |\n हरिचरणों में ध्यान लगाऊँ ||१||\n\n" +
                "गजल सुनाऊँ अद्भुत यार |\n धारण से हो बेड़ा पार ||२||\n\n" +
                "अर्जुन कहै सुनो भगवाना |\n अपने रूप बताये नाना ||३||\n\n" +
                "उनका मैं कछु भेद न जाना |\n किरपा कर फिर कहो सुजाना ||४||\n\n" +
                "जो कोई तुमको नित ध्यावे |\n भक्तिभाव से चित्त लगावे ||५||\n\n" +
                "रात दिवस तुमरे गुण गावे |\n तुमसे दूजा मन नहीं भावे ||६||\n\n" +
                "तुमरा नाम जपे दिन रात |\n और करे नहीं दूजी बात ||७||\n\n" +
                "दूजा निराकार को ध्यावे |\n अक्षर अलख अनादि बतावे ||८||\n\n" +
                "दोनों ध्यान लगाने वाला |\n उनमें कुण उत्तम नन्दलाला ||९||\n\n" +
                "अर्जुन से बोले भगवान् |\n सुन प्यारे कछु देकर ध्यान ||१०||\n\n" +
                "मेरा नाम जपै जपवावे |\n नेत्रों में प्रेमाश्रु छावे ||११||\n\n" +
                "मुझ बिनु और कछु नहीं चावे |\n रात दिवस मेरा गुण गावे ||१२||\n\n" +
                "सुनकर मेरा नामोच्चार |\n उठै रोम तन बारम्बार ||१३||\n\n" +
                "जिनका क्षण टूटै नहिं तार |\n उनकी श्रद्घा अटल अपार ||१४||\n\n" +
                "मुझ में जुड़कर ध्यान लगावे |\n ध्यान समय विह्वल हो जावे ||१५||\n\n" +
                "कंठ रुके बोला नहिं जावे |\n मन बुधि मेरे माँही समावे ||१६||\n\n" +
                "लज्जा भय रु बिसारे मान |\n अपना रहे ना तन का ज्ञान ||१७||\n\n" +
                "ऐसे जो मन ध्यान लगावे |\n सो योगिन में श्रेष्ठ कहावे ||१८||\n\n" +
                "जो कोई ध्यावे निर्गुण रूप |\n पूर्ण ब्रह्म अरु अचल अनूप ||१९||\n\n" +
                "निराकार सब वेद बतावे |\n मन बुद्धी जहँ थाह न पावे ||२०||\n\n" +
                "जिसका कबहुँ न होवे नाश |\n ब्यापक सबमें ज्यों आकाश ||२१||\n\n" +
                "अटल अनादि आनन्दघन |\n जाने बिरला योगीजन ||२२||\n\n" +
                "ऐसा करे निरन्तर ध्यान |\n सबको समझे एक समान ||२३||\n\n" +
                "मन इन्द्रिय अपने वश राखे |\n विषयन के सुख कबहुँ न चाखे ||२४||\n\n" +
                "सब जीवों के हित में रत |\n ऐसा उनका सच्चा मत ||२५||\n\n" +
                "वह भी मेरे ही को पाते |\n निश्चय परमा गति को जाते ||२६||\n\n" +
                "फल दोनों का एक समान |\n किन्तु कठिन है निर्गुण ध्यान ||२७||\n\n" +
                "जबतक है मन में अभिमान |\n तबतक होना मुश्किल ज्ञान ||२८||\n\n" +
                "जिनका है निर्गुण में प्रेम |\n उनका दुर्घट साधन नेम ||२९||\n\n" +
                "मन टिकने को नहीं अधार |\n इससे साधन कठिन अपार ||३०||\n\n" +
                "सगुन ब्रह्म का सुगम उपाय |\n सो मैं तुझको दिया बताय ||३१||\n\n" +
                "यज्ञ दानादि कर्म अपारा |\n मेरे अर्पण कर कर सारा ||३२||\n\n" +
                "अटल लगावे मेरा ध्यान |\n समझे मुझको प्राण समान ||३३||\n\n" +
                "सब दुनिया से तोड़े प्रीत |\n मुझको समझे अपना मीत ||३४||\n\n" +
                "प्रे नारायण...\n" +
                "म मग्न हो अति अपार |\n समझे यह संसार असार ||३५||\n\n" +
                "जिसका मन नित मुझमें यार |\n उनसे करता मैं अति प्यार ||३६||\n\n" +
                "केवट बनकर नाव चलाऊँ |\n भव सागर के पार लगाऊँ ||३७||\n\n" +
                "यह है सबसे उत्तम ज्ञान |\n इससे तू कर मेरा ध्यान ||३८||\n\n" +
                "फिर होवेगा मोहिं सामान |\n यह कहना मम सच्चा जान ||३९||\n\n" +
                "जो चाले इसके अनुसार |\n वह भी हो भवसागर पार ||४०||\n\n" +
                "\n" +
                "हे नाथ में आपको भुलू नही\n\n" +
                "नारायण... नारायण... नारायण... नारायण...\n");





        simpleExoPlayerView_Video = findViewById(R.id.exoplayer_learning_video);
        if(menu.equalsIgnoreCase("Gajal"))
        {
            getSupportActionBar().setTitle("गजल गीता");
            getVideoFile("http://btwebservices.biyanitechnologies.com/g1/GeetaApp/gajal.mp4");
               /*
            PDFView pdfView;
            pdfView=findViewById(R.id.pdfv);
            pdfView.fromAsset("imp.pdf").load();*/
        }
        else   if(menu.equalsIgnoreCase("Sar"))
        {
            getSupportActionBar().setTitle("गीता सार");
            getVideoFile("http://btwebservices.biyanitechnologies.com/g1/GeetaApp/sar.mp4");
            txt.setText("1. सांसारिक मोह के कारण ही मनुष्य ‘मैं क्या करूं और क्या नहीं करूं’ इस दुविधामें फंसकर कर्तव्यच्युत हो  जाता है। अतः मोह या सुखासक्ति के वशीभूत नहीं होना चाहिए।\n\n" +
                    "2. शरीर नाशवान् है और उसे जानने वाला शरीरी अविनाशी है- इस विवेकको महत्व देना और अपने कर्तव्यका पालन करना- इन दोनों में से किसी भी एक उपायको काम में लाने से चिन्ता-शोक मिट जाते हैं।\n\n" +
                    "3. निष्कामभावपूर्वक केवल दूसरों के हितके लिए अपने कर्तव्यका तत्परता से पालन करनेमात्रसे कल्याण हो जाता है।\n\n" +
                    "4. कर्मबन्धनसे छूटनेके दो उपाय हैं- कर्मोंके तत्वको जानकर नि:स्वार्थभावसे कर्म करना और तत्वज्ञान का अनुभव करना।\n\n" +
                    "5. मनुष्य को अनुकूल- प्रतिकूल परिस्थितियोंके आनेपर सुखी- दु:खी नहीं होना चाहिए; क्योंकि इनसे सुखी-दु:खी होने वाला मनुष्य संसारसे ऊंचा उठकर परम आनन्दका अनुभव नहीं कर सकता।\n\n" +

                    "6. किसी भी साधनसे अन्त:करणमें समता आनी चाहिए। समता आए बिना मनुष्य सर्वथा निर्विकार नहीं हो सकता।\n\n" +
                    "7. सब कुछ भगवान ही है- ऐसा स्वीकार कर लेना सर्वश्रेष्ठ साधन है।\n\n" +
                    "8. अन्तकालीन चिन्तनके अनुसार ही जीव की गति होती है। अतः मनुष्य को हरदम भगवान् का स्मरण करते हुए अपने कर्तव्य का पालन करना चाहिए, जिससे अन्तकालमें भगवानकी स्मृति बनी रहे।\n\n" +
                    "9. सभी मनुष्य भगवत्प्राप्तिके अधिकारी हैं, चाहे वे किसी भी वर्ण, आश्रम, सम्प्रदाय, देश, वेश आदिके क्यों ना हो।\n\n" +
                    "10. संसार में जहां भी विलक्षणता, विशेषता, सुन्दरता, महत्ता विद्वता आदि दिखे, उसको भगवानका ही मानकर का भगवानका ही चिन्तन करना चाहिए।\n\n" +
                    "11. इस जगतको भगवानका ही स्वरूप मानकर प्रत्येक मनुष्य भगवानके विराटरूपके दर्शन कर सकता है।\n\n" +
                    "12. जो भक्त शरीर- इंद्रियां- मन -बुद्धिसहित अपने -आपको भगवानके अर्पण कर देता है, वह भगवानको प्रिय होता है।\n\n" +

                    "13. संसार में एक परमात्मतत्व  ही जानने योग्य है। उसको जाननेपर अमरता की प्राप्ति हो जाती है।\n\n" +
                    "14. संसार- बन्धनसे छुटने के लिये सत्व, रज और तम- इन तीन गुणोंसे अतीत होना जरूरी है। अनन्य -भक्तिसे मनुष्य इन तीनों गुणोंसे अतीत हो जाता है।\n\n" +
                    "15. इस संसारका मूल आधार और अत्यन्त श्रेष्ठ परमपुरुष एक परमात्मा ही है- ऐसा मानकर अनन्य भाव से उनका भजन करना चाहिए।\n\n" +
                    "16. दुर्गुण-दुराचारोंसे ही मनुष्य चोरासी लाख योनियों एवं नरको में जाता है और दु:ख पाता है। अतः जन्म- मरणके चक्रसे छूटनेके लिए दुर्गुण- दुराचारों का त्याग करना आवश्यक है।\n\n" +
                    "17. मनुष्य श्रद्धापूर्वक जो भी शुभ कार्य करें उसको भगवानका स्मरण करके, उनके नामका उच्चारण करके ही आरम्भ करना चाहिए।\n\n" +
                    "18. सब ग्रंथोंका सार वेद है, वेदोंका सार उपनिषद है, उपनिषदोंका सार गीता है और गीताका सार भगवानकी शरणागति है।जो अनन्यभावसे भगवानकी शरण हो जाता है, उसे भगवान सम्पूर्ण पापों से मुक्त कर देते हैं।\n" +
                    "\n\n" +

                    "-ब्रह्मलीन श्रद्धेय स्वामी श्रीरामसुखदासजी महाराजके गीताप्रेस, गोरखपुर से प्रकाशित गीता- साधक- संजीवनी ग्रन्थके आधार पर।\n");
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
