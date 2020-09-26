package com.bt.heynath;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class NitytaSubmenuAdapter extends ArrayAdapter
{
    Context context;
	public ArrayList<String> list;
	@SuppressLint("ResourceType")
	public NitytaSubmenuAdapter(Context context, ArrayList<String> list)
	{
		super(context, R.id.textView1,list);
		// TODO Auto-generated constructor stub
		this.list=list;
		this.context=context;
	}
	
	//#

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
	            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	            convertView = mInflater.inflate(R.layout.item_submenu, null);
	            TextView t1=(TextView)convertView.findViewById(R.id.textView1);
	            t1.setText(""+list.get(position));
                Typeface tf=Typeface.createFromAsset(context. getAssets(), "Roboto-Light.ttf");
                t1.setTypeface(tf);
		        ImageView img=convertView.findViewById(R.id.picture);

	            if(  position==0)//list.get(position).equalsIgnoreCase("त्नित्य स्तुति"))
				{
					// img.setImageResource(R.drawable.nitya);
                     Picasso.get().load(R.drawable.shree1).transform(new CircleTransform()).into(img);
				}
		        else if((position)==1)
		        {
		         	//img.setImageResource(R.drawable.hand);
                    Picasso.get().load(R.drawable.shree5).transform(new CircleTransform()).into(img);
		        }

	            return convertView;  
	}

}
