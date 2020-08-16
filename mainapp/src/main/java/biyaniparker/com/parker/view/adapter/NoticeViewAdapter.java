package biyaniparker.com.parker.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import com.squareup.picasso.Picasso;
import java.util.List;
import biyaniparker.com.parker.R;
import biyaniparker.com.parker.beans.CreateNoticeBean;

public class NoticeViewAdapter extends PagerAdapter {
    List<CreateNoticeBean> list;
    LayoutInflater layoutInflater;
    Context context;
    ProductAdaperCallBack productAdaperCallBack;

    public NoticeViewAdapter(List<CreateNoticeBean> list,Context context,ProductAdaperCallBack productAdaperCallBack)
    {
        this.list = list;
        this.context = context;
        this.productAdaperCallBack = productAdaperCallBack;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.notice_view,container,false);

        ImageView imageView = view.findViewById(R.id.iv_notice);
        TextView title,des,date;
        title = view.findViewById(R.id.tvsetTitle);
        des = view.findViewById(R.id.tvsetDes);
       // date = view.findViewById(R.id.tvsetDate);

        if (list.get(position).getAttachment().equalsIgnoreCase("")){
            //imageView.setImageResource(R.drawable.bgpaker);
            imageView.setVisibility(View.GONE);
        }
        else
        {
            Picasso.get().load(list.get(position).getAttachment()).placeholder(R.drawable.bgpaker1).into(imageView);
        }
        title.setText(list.get(position).getTitle());
        des.setText(list.get(position).getDescription());
      //  date.setText(list.get(position).getDate());

        imageView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                String imgUrl =  list.get(position).getAttachment();
                productAdaperCallBack.getImageUrl(imgUrl);
            }
        });
        container.addView(view,0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

    public interface  ProductAdaperCallBack{
        void getImageUrl(String imgUrl);
    }
}
