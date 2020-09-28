package com.bt.heynath;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class PrathnaImportant extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prathna_important);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("*नित्य स्तुति एवं का महत्व  प्रार्थना*");
        TextView txt;
        txt=findViewById(R.id.txt);
        txt.setText("\n" +
                "नारायण नारायण \n" +
                "सभी सत्संगी भाई-बहनों को बच्चों से सप्रेम जय श्री कृष्णा हम  मे से बहुत से सज्जन बहनों को ज्ञात ही है ,कि जब तक पूज्य  महाराज जी सशरीर धरा धाम पर रहे तब तक वह जहां कहीं भी किसी भी स्थान पर विराजते उनके सानिध्य में प्रातः कालीन  5:00 बजे की प्रार्थना अवश्य होती थी यहां तक की रेलयात्रा में भी प्रार्थना सुबह अवश्य होती थी ।पूज्य  महाराज जी के सानिध्य प्राप्त ब्रहमचारी व अन्य लोगों से जानकारी ली गई तो उन्होंने बताया कि यह प्रार्थना का क्रम लगभग  सन् 1970 के पहले से निर्बाध  गति से चल रहा है। आज तक एक दिन भी प्रार्थना का क्रम खंडित नहीं हुआ है,उपरोक्त सभी बातों से  ज्ञात होता कि ,पूज्य  महाराज जी के जीवन में प्रार्थना का कितना महत्व था ।वे प्रार्थना के विषय में कहते भी थे, हर भाई बहन अपने-अपने घरों में स्तुति प्रार्थना  करें ,रोजाना  ठीक 5:00 बजे, तो दुनिया भर में पांच बजे जो बात होती है,वो सब एक हो जाती है। इस वास्ते अपनी दृष्टि से ठीक 5:00 बजे शुरू करना सब जगह। अपने-अपने घरों पर भाई-बहन सब इस तरह से करें। यह एक बड़ा बल होता है।\n" +
                " \"संघे शक्ति कलियुगे\"\n" +
                "\n" +
                " पूज्य महाराज जी का प्रार्थना आरंभ करने का उद्देश्य ऐसा भी था ,कि जो सज्जन नित्य स्तुति, प्रार्थना करेंगे उनको गीता जी के कुछ श्र्लोक अवश्य याद हो जाएंगे एवं प्रार्थना क्रम के अनुसार प्रतिदिन महाराज जी की वाणी से बहुत ही मार्मिक भाव प्रकट होते थे ।लगभग 45 मिनिट का यह सत्र सुबह के सात्विक वातावरण में आध्यात्मिक ऊर्जा  से भरपूर होता था ।\n" +
                "इस नित्य स्तूति प्रार्थना का क्रम इस प्रकार है।\n" +
                "1. प्रार्थना स्तूति के 21 श्लोक तदउपरांत \n" +
                "2.गीता जी 10 श्लोक (लगभग) 3.हरि शरणम् कीर्तन ।\n" +
                "इसके पश्चात पूज्य महाराज जी का 15 से 20 मिनिट का बहुत ही सारगर्भित प्रवचन होता था।\n" +
                "\n" +
                " अतः आप सभी से विनम्र निवेदन है कि अभी तक जो साधक ,साधिकाएं , बच्चे  इस लाभ से वंचित है, वे लोग पूज्य  महाराज जी की इस कृपा का पूरा पुरा लाभ उठाकर अपने जीवन को कल्याण पथ अनुगामी बनाने का भरकस प्रयत्न करें। \n" +
                "नारायण नारायण नारायण\n"

              );
        Typeface tf=Typeface.createFromAsset(this. getAssets(), "Roboto-Light.ttf");
        txt.setTypeface(tf);

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
