package biyaniparker.com.parker.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import biyaniparker.com.parker.R;
import biyaniparker.com.parker.beans.UnitMasterBean;
import biyaniparker.com.parker.beans.UserShopBean;

public class UnitMasterAdapter extends ArrayAdapter {
    Context context;
    ArrayList<UnitMasterBean> arrayList;


    public UnitMasterAdapter( Context context, int resource,ArrayList<UnitMasterBean> objects) {
        super(context, resource);
        this.context=context;
        arrayList=objects;
    }

    class ViewHolder
    {
        TextView unitname, unitType,remark;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        try {
            ViewHolder viewHolder=new ViewHolder();
            if(convertView==null)
            {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.unit_master_adapter, null);
                convertView.setTag(viewHolder );
            }
            else
            {
                convertView.setTag(viewHolder);
            }

            viewHolder.unitname=(TextView)convertView.findViewById(R.id.textUnitName);
            viewHolder.unitType=(TextView)convertView.findViewById(R.id.txtUnitType);
            viewHolder.remark=(TextView)convertView.findViewById(R.id.textRemark);

            viewHolder.unitname.setText(arrayList.get(position).getUnitName());

            if (arrayList.get(position).getUnitType().equals("")){
                viewHolder.unitType.setText("-");
            } else {
                viewHolder.unitType.setText(arrayList.get(position).getUnitType());
            }
            if (arrayList.get(position).getRemark().equals("")){
                viewHolder.remark.setText("-");
            } else {
                viewHolder.remark.setText(arrayList.get(position).getRemark());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }
}
