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
import biyaniparker.com.parker.beans.UnitMasterBean;

public class masteradapter extends RecyclerView.Adapter<masteradapter.ViewHolder> {
    List<UnitMasterBean> unitMasterBeanList;
    Context context;
    UnitMasterBean unitMasterBean;
    AdapterCallBack adapterCallBack;


    public  masteradapter(Context context,List<UnitMasterBean> list,AdapterCallBack adapterCallBack){
        this.context = context;
        this.unitMasterBeanList = list;
        this.adapterCallBack = adapterCallBack;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.unit_master_adapter, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull masteradapter.ViewHolder holder, int position) {
            unitMasterBean = unitMasterBeanList.get(position);

             holder.unitid.setText(Integer.toString(unitMasterBean.getUnitId()));
             holder.unitname.setText(unitMasterBean.getUnitName());
             holder.unitType.setText(unitMasterBean.getUnitType());
             holder.remark.setText(unitMasterBean.getRemark());
    }

    @Override
    public int getItemCount() {
        return unitMasterBeanList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView unitid,unitname, unitType,remark;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            unitid = itemView.findViewById(R.id.textUnitId);
            unitname = itemView.findViewById(R.id.textUnitName);
            unitType = itemView.findViewById(R.id.txtUnitType);
            remark = itemView.findViewById(R.id.textRemark);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int pos = getAdapterPosition();
            adapterCallBack.getId(unitMasterBeanList.get(pos).getUnitId());
        }
    }

    public interface AdapterCallBack{
        void getId(int unitId);
    }
}
