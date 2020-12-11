package biyaniparker.com.parker.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import biyaniparker.com.parker.R;
import biyaniparker.com.parker.beans.CreateNoticeBean;

public class NoticeListViewAdapter extends RecyclerView.Adapter<NoticeListViewAdapter.ViewHolder> {
    List<CreateNoticeBean> list;
    Context context;
    NoticeAdapterCallBack noticeAdapterCallBack;

    public NoticeListViewAdapter(List<CreateNoticeBean> noticeBeanList, Context context, NoticeAdapterCallBack callBack){
        this.list = noticeBeanList;
        this.context = context;
        this.noticeAdapterCallBack = callBack;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notice_list, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CreateNoticeBean createNoticeBean = list.get(position);

        holder.title.setText(createNoticeBean.getTitle());
        holder.description.setText(createNoticeBean.getDescription());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView title,description;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvnoticetitle);
            description = itemView.findViewById(R.id.tvnoticedescription);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            noticeAdapterCallBack.getNoticeId(list.get(position).getNoticeId());
        }
    }
    public interface NoticeAdapterCallBack{
        void getNoticeId(int noticeId);
    }
}
