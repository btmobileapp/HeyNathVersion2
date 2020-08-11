package biyaniparker.com.parker.view.Notice;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import biyaniparker.com.parker.R;
import biyaniparker.com.parker.bal.ModuleProduct1;
import biyaniparker.com.parker.beans.CreateNoticeBean;
import biyaniparker.com.parker.utilities.BitmapUtilities;
import biyaniparker.com.parker.utilities.CommonUtilities;
import biyaniparker.com.parker.utilities.DownloadUtility;
import biyaniparker.com.parker.utilities.MultifileUploadUtility;
import biyaniparker.com.parker.view.homeadmin.AdminHomeScreen;

public class CreateNoticeView extends AppCompatActivity implements  DownloadUtility, MultifileUploadUtility {
    EditText etTitle,etDescription,etSelectDate;
    Spinner noticeSpinner;
    ImageView img,camera,gallery;
    ModuleProduct1 moduleProduct;
    private int mYear, mMonth, mDay, mHour, mMinute;
    static final int DATE_DIALOG_ID = 0;
    String path="";
    String path1="";
    int ProductId;
    Button save,btnSelectDate;
    String myFormat="dd/MM/yyyy";
    SimpleDateFormat sdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_notice);
        init();
        moduleProduct=new ModuleProduct1(this);
        getSupportActionBar().setTitle("Create Notice");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        CommonUtilities.hideatInItInputBoard(this);

        try {
            Intent intent = getIntent();
            ProductId = intent.getExtras().getInt("ProductId");
        } catch (Exception e) {
            e.printStackTrace();
        }

//        String[] notices = {"General Notice","ProductChange Notice"};
//        ArrayAdapter<String> a =new ArrayAdapter<String>(CreateNoticeView.this,android.R.layout.simple_spinner_item, notices);
//        a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        noticeSpinner.setAdapter(a);

//        etSelectDate.setOnTouchListener(new View.OnTouchListener(){
//            public boolean onTouch(View v, MotionEvent event) {
//                if(v == etSelectDate)
//                    showDialog(DATE_DIALOG_ID);
//                return false;
//            }
//        });

        btnSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(CreateNoticeView.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                etSelectDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
//                                String date_selected = String.valueOf(dayOfMonth)+" /"+String.valueOf(monthOfYear+1)+" /"+String.valueOf(year);
//                                etSelectDate.setText(date_selected);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickPhoto(2);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validation()) {
                    CreateNoticeBean createNoticeBean = new CreateNoticeBean();

                    createNoticeBean.setTitle(etTitle.getText().toString());
                    createNoticeBean.setDescription(etDescription.getText().toString());
                    createNoticeBean.setDeleteStatus(false);

                    try {
                        String dateString = etSelectDate.getText().toString();
                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                        Date date = sdf.parse(dateString);
                        assert date != null;
                        long startDate = date.getTime();
                        createNoticeBean.setExpiryDate(startDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                  //  createNoticeBean.setNoticeType(noticeSpinner.getSelectedItem().toString());
                    createNoticeBean.setProductId(ProductId);
                    createNoticeBean.setAttachment(path);

                    ModuleProduct1 product1 = new ModuleProduct1(CreateNoticeView.this);
                    try {
                        product1.createNotice(createNoticeBean);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void init() {
        etTitle = findViewById(R.id.etTitle);
        etDescription = findViewById(R.id.etDescription);
        etSelectDate = findViewById(R.id.etDate);
     //   noticeSpinner = findViewById(R.id.spinnerNotice);
        img = findViewById(R.id.image_notice);
        camera = findViewById(R.id.cameraNotice);
        gallery = findViewById(R.id.gallaryNotice);
        save = findViewById(R.id.btnCreateNotice);
        btnSelectDate = findViewById(R.id.btnSelectDate);
    }

    private void pickPhoto(int i) {
        Intent in = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(in, i);
    }

    private boolean validation() {
        if (etTitle.getText().toString().isEmpty() ||
                etDescription.getText().toString().isEmpty()
                || etSelectDate.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"please enter all fields",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==2 && resultCode==RESULT_OK) {

            try {
                Uri uri = data.getData();
                String path = "";
                try {
                    path = CommonUtilities.getPath(this, uri);
                } catch (Exception e) {
                }

                File file = new File(path);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
                Bitmap thePic = ThumbnailUtils.extractThumbnail(bitmap, CommonUtilities.Width, CommonUtilities.Height);
                final File bitmapFile = BitmapUtilities.saveToExtenal(thePic, this);
                Bitmap thePic1 = ThumbnailUtils.extractThumbnail(bitmap, 81, 112);
                final File bitmapFile1 = BitmapUtilities.saveToExtenal(thePic1, this);

                // File bitmapFile1 = BitmapUtilities.saveToExtenal(bitmap, this);
                bitmap = BitmapFactory.decodeFile(bitmapFile.getAbsolutePath(), options);
                img.setImageBitmap(bitmap);
                AlertDialog.Builder alerBuilder = new AlertDialog.Builder(this, AlertDialog.THEME_DEVICE_DEFAULT_DARK);
                alerBuilder.setTitle(getString(R.string.app_name));
                alerBuilder.setMessage("Do you want to uplaod File ");
                alerBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        moduleProduct.loadFile(bitmapFile, bitmapFile1);
                    }
                });
                alerBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // moduleCategory.loadFile(bitmapFile);
                        img.setImageResource(R.drawable.bgpaker);
                    }
                });
                alerBuilder.show();

                // img.setImageBitmap(thePic);
            } catch (Exception e) {
                AlertDialog.Builder alerBuilder = new AlertDialog.Builder(this);
                alerBuilder.setTitle(getString(R.string.app_name));
                alerBuilder.setMessage("" + e.toString() + "\n\n" + e.getMessage());
                alerBuilder.show();
            }
        }
    }

    @Override
    public void onComplete(String str, int requestCode, int responseCode) {
        if(requestCode==10)
        {
            path="";
//            Toast.makeText(getApplicationContext(),""+responseCode,Toast.LENGTH_LONG).show();
            if(responseCode==200)
            {
                path=str;
            }
            else
            {
                img.setImageResource(android.R.drawable.ic_menu_camera);
            }
        }
        if (requestCode==7){
            if (responseCode==200){
                Toast.makeText(getApplicationContext(),"saved",Toast.LENGTH_SHORT).show();
                finish();
                Intent intent = new Intent(this, AdminHomeScreen.class);
                startActivity(intent);
            }
        }
    }

    public void onUploadComplete(String str1, String str2, int requestCode, int responseCode)
    {
        // Toast.makeText(getApplicationContext(),""+responseCode,Toast.LENGTH_LONG).show();
        if(requestCode==10)
        {
            path="";
            path1="";
            // Toast.makeText(getApplicationContext(),""+responseCode,Toast.LENGTH_LONG).show();
            if(responseCode==200)
            {
                path=str1;
                path1=str2;
                Toast.makeText(this,"Photo Uploaded",Toast.LENGTH_LONG).show();
            }
            else
            {
                img.setImageResource(android.R.drawable.ic_menu_camera);
            }
        }
        else
        {
            Toast.makeText(getApplicationContext()," Bad request.....",Toast.LENGTH_LONG).show();
            img.setImageResource(android.R.drawable.ic_menu_camera);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
