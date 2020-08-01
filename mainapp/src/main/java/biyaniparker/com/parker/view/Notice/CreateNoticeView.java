package biyaniparker.com.parker.view.Notice;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import biyaniparker.com.parker.R;
import biyaniparker.com.parker.utilities.CommonUtilities;
import biyaniparker.com.parker.utilities.DownloadUtility;

public class CreateNoticeView extends AppCompatActivity implements View.OnClickListener, DownloadUtility {
    EditText etTitle,etDescription,etSelectDate;
    Spinner noticeSpinner;
    ImageView img,camera,gallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_notice);
        init();
        getSupportActionBar().setTitle("Create Notice");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        CommonUtilities.hideatInItInputBoard(this);

        String[] notices = {"General Notice","ProductChange Notice"};
        ArrayAdapter<String> a =new ArrayAdapter<String>(CreateNoticeView.this,android.R.layout.simple_spinner_item, notices);
        a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        noticeSpinner.setAdapter(a);
    }

    private void init() {
        etTitle = findViewById(R.id.etTitle);
        etDescription = findViewById(R.id.etDescription);
        etSelectDate = findViewById(R.id.etDate);
        noticeSpinner = findViewById(R.id.spinnerNotice);
        img = findViewById(R.id.image_notice);
        camera = findViewById(R.id.cameraNotice);
        gallery = findViewById(R.id.gallaryNotice);
    }


    @Override
    public void onClick(View view) {

    }

    @Override
    public void onComplete(String str, int requestCode, int responseCode) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
