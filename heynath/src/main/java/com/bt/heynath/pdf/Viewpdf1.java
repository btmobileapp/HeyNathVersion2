package com.bt.heynath.pdf;

import android.os.Bundle;
import android.view.MenuItem;

import com.bt.heynath.R;
import com.github.barteksc.pdfviewer.PDFView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Viewpdf1 extends AppCompatActivity
{

    public static String title;
    public  static  int no=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpdf);
        PDFView pdfView;
        pdfView=findViewById(R.id.pdfv);
       // pdfView.fromAsset("filename.pdf").load();
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(title);
        //getSupportActionBar().setSubtitle(title);

        if(no==2) {
            pdfView.fromAsset("filename.pdf").load();
        }
        if(no==3) {
            pdfView.fromAsset("filename.pdf").load();
        }
        if(no==5) {
            pdfView.fromAsset("filename.pdf").load();
        }
        if(no==8) {
            pdfView.fromAsset("mahima.pdf").load();
        }
        if(no==11) {
            pdfView.fromAsset("nam.pdf").load();
        }
        if(no==12)
        {
            pdfView.fromAsset("shlok.pdf").load();
        }
        if(no==16)
        {
            pdfView.fromAsset("filename.pdf").load();
        }
        if(no==34) {
            pdfView.fromAsset("filename.pdf").load();
        }
        if(no==1) {
            pdfView.fromAsset("filename.pdf").load();
        }
        if(no==6) {
            pdfView.fromAsset("filename.pdf").load();
        }
        if(no==41) {
            pdfView.fromAsset("filename.pdf").load();
        }
        if(no==42) {
            pdfView.fromAsset("filename.pdf").load();
        }
        if(no==100) {
            pdfView.fromAsset("ucharan.pdf").load();
        }
        if(no==101) {
            pdfView.fromAsset("sadhak.pdf").load();
        }
        if(no==102) {
            pdfView.fromAsset("meta_human.pdf").load();
        }
        if(no==786) {
            pdfView.fromAsset("imp_nitya.pdf").load();
        }
        if(no==156)
        {
            pdfView.fromAsset("manuals.pdf").load();
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

}
