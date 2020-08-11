package biyaniparker.com.parker.view.notice;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import biyaniparker.com.parker.R;
import biyaniparker.com.parker.beans.CreateNoticeBean;
import biyaniparker.com.parker.utilities.CommonUtilities;
import biyaniparker.com.parker.utilities.DownloadUtility;
import biyaniparker.com.parker.view.adapter.NoticeListViewAdapter;
import biyaniparker.com.parker.view.unitmaster.SharedPreference;

public class NoticeListView extends AppCompatActivity implements DownloadUtility, NoticeListViewAdapter.NoticeAdapterCallBack {
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notice_list_view);
        recyclerView = findViewById(R.id.rvnotices);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getSupportActionBar().setTitle("Notices");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        CommonUtilities.hideatInItInputBoard(this);

        getNoticeList();
    }

    private void   getNoticeList() {
        SharedPreference sharedPreference = new SharedPreference(getApplicationContext());
        String res = sharedPreference.getStr("NoticesResponse");
        List<CreateNoticeBean> noticeBeanList = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(res);
            for (int i=0;i<jsonArray.length();i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                CreateNoticeBean createNoticeBean = new CreateNoticeBean();

                createNoticeBean.setNoticeId(jsonObject.getInt("NoticeId"));
                createNoticeBean.setTitle(jsonObject.getString("Title"));
                createNoticeBean.setDescription(jsonObject.getString("Decription"));
                createNoticeBean.setAttachment(jsonObject.getString("Attachment"));
                //createNoticeBean.setExpiryDate(Long.parseLong(jsonObject.getString("ExpiryDate")));
               // createNoticeBean.setProductId(jsonObject.getInt("ProductId"));

                noticeBeanList.add(createNoticeBean);
            }
            NoticeListViewAdapter adapter = new NoticeListViewAdapter(noticeBeanList,getApplicationContext(),NoticeListView.this);
            recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
            recyclerView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onComplete(String str, int requestCode, int responseCode) {

    }

    @Override
    public void getNoticeId(int noticeId) {
        Intent intent = new Intent(NoticeListView.this,NoticeView.class);
        intent.putExtra("noticeId",noticeId);
        startActivity(intent);
    }
}
