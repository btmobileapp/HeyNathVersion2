package biyaniparker.com.parker.view.notice;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import biyaniparker.com.parker.R;
import biyaniparker.com.parker.beans.CreateNoticeBean;
import biyaniparker.com.parker.utilities.CommonUtilities;
import biyaniparker.com.parker.utilities.DownloadUtility;
import biyaniparker.com.parker.view.adapter.NoticeViewAdapter;
import biyaniparker.com.parker.view.homeuser.productdshopping.ViewProductImage;
import biyaniparker.com.parker.view.unitmaster.SharedPreference;

public class NoticeView extends AppCompatActivity implements DownloadUtility, NoticeViewAdapter.ProductAdaperCallBack {
    int noticeId;
    ViewPager viewPager;
    NoticeViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notice);
        viewPager = findViewById(R.id.ViewPagerNotice);
        getSupportActionBar().setTitle("Notice");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        CommonUtilities.hideatInItInputBoard(this);

        try {
            Intent intent = getIntent();
            noticeId = intent.getIntExtra("noticeId", 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        getNotice(noticeId);


        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                Toast.makeText(getApplicationContext(),""+position,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void getNotice(int noticeId) {
        SharedPreference sharedPreference = new SharedPreference(getApplicationContext());
        String res = sharedPreference.getStr("NoticesResponse");

        List<CreateNoticeBean> noticeBeanList = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(res);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (jsonObject.getInt("NoticeId") == noticeId) {
                    CreateNoticeBean createNoticeBean = new CreateNoticeBean();
                    createNoticeBean.setTitle(jsonObject.getString("Title"));
                    createNoticeBean.setDescription(jsonObject.getString("Decription"));
                    createNoticeBean.setAttachment(jsonObject.getString("Attachment"));
                    String date = jsonObject.getString("ExpiryDate");
                    String v = date.replaceAll("/","");
                    String v1 = v.replaceAll("Date","");
                    String v2 = v1.replace("(","").replace(")","");

                    String longV = v2;
                    long millisecond = Long.parseLong(longV);
                    String dateString = getDate(millisecond, "dd/MM/yyyy");
                    //String dateString = String.valueOf(DateFormat.format("dd/MM/yyyy", new Date(millisecond)));
                    createNoticeBean.setDate(dateString);
                    noticeBeanList.add(createNoticeBean);
                }
                adapter = new NoticeViewAdapter(noticeBeanList,getApplicationContext(),NoticeView.this);
                viewPager.setAdapter(adapter);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private  static String getDate(long milliSeconds, String dateFormat) {
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
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

    @Override
    public void getImageUrl(String imgUrl) {
        Intent intent=new Intent(NoticeView.this, ViewProductImage.class);
        intent.putExtra("path",imgUrl);
        startActivity(intent);
    }
}
