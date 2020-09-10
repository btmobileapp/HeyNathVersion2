package com.bt.heynath;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;


public class MenuAdapter extends ArrayAdapter
{
    Context context;
	public ArrayList<String> list;
	@SuppressLint("ResourceType")
	public MenuAdapter(Context context, ArrayList<String> list)
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
	            convertView = mInflater.inflate(R.layout.item_menu, null);
	            TextView t1=(TextView)convertView.findViewById(R.id.textView1);
	            t1.setText(""+list.get(position));
		        ImageView img=convertView.findViewById(R.id.picture);

	            if(  position==0)//list.get(position).equalsIgnoreCase("त्नित्य स्तुति"))
				{
					// img.setImageResource(R.drawable.nitya);
                     Picasso.get().load(R.drawable.shree1).transform(new CircleTransform()).into(img);
				}
		        else if((position)==1)
		        {
		         	//img.setImageResource(R.drawable.hand);
                    Picasso.get().load(R.drawable.hand).transform(new CircleTransform()).into(img);
		        }
				else if((position)==2)
				{
					//img.setImageResource(R.drawable.hand1);
                    Picasso.get().load(R.drawable.adhaymain2).transform(new CircleTransform()).into(img);
				}
				else if((position)==3)
				{
                    Picasso.get().load(R.drawable.auto).transform(new CircleTransform()).into(img);
					//img.setImageResource(R.drawable.shrre4);
				}
				else if(list.get(position).equalsIgnoreCase("अधयाय ४"))
                {
                    Picasso.get().load(R.drawable.shree2).transform(new CircleTransform()).into(img);
                }
				else if((position)==4)
				{
					//img.setImageResource(R.drawable.shree5);
                    Picasso.get().load(R.drawable.shrre4).transform(new CircleTransform()).into(img);
				}
				else if((position)==5)
				{
					//img.setImageResource(R.drawable.shree7);
                    Picasso.get().load(R.drawable.shree5).transform(new CircleTransform()).into(img);
				}
				else if((position)==6)
				{
                    Picasso.get().load(R.drawable.shree6).transform(new CircleTransform()).into(img);
				}
				else if((position)==7)
				{
                    Picasso.get().load(R.drawable.shree7).transform(new CircleTransform()).into(img);
				}
				else if((position)==8)
				{
                    Picasso.get().load(R.drawable.shree2).transform(new CircleTransform()).into(img);
				}
                else if((position)==8)
                {
                    Picasso.get().load(R.drawable.shree6).transform(new CircleTransform()).into(img);
                }

	            return convertView;  
	}

}
 class RoundedCornersTransform implements Transformation {
    @Override
    public Bitmap transform(Bitmap source) {
        int size = Math.min(source.getWidth(), source.getHeight());

        int x = (source.getWidth() - size) / 2;
        int y = (source.getHeight() - size) / 2;

        Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
        if (squaredBitmap != source) {
            source.recycle();
        }

        Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        BitmapShader shader = new BitmapShader(squaredBitmap, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
        paint.setShader(shader);
        paint.setAntiAlias(true);

        float r = size / 8f;
        canvas.drawRoundRect(new RectF(10, 10, source.getWidth(), source.getHeight()), r, r, paint);
        squaredBitmap.recycle();
        return bitmap;
    }

    @Override
    public String key() {
        return "rounded_corners";
    }
}

class CircleTransform implements Transformation {
    @Override
    public Bitmap transform(Bitmap source) {
        int size = Math.min(source.getWidth(), source.getHeight());

        int x = (source.getWidth() - size) / 2;
        int y = (source.getHeight() - size) / 2;

        Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
        if (squaredBitmap != source) {
            source.recycle();
        }

        Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        BitmapShader shader = new BitmapShader(squaredBitmap, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
        paint.setShader(shader);
        paint.setAntiAlias(true);

        float r = size/2f;
        canvas.drawCircle(r, r, r, paint);

        squaredBitmap.recycle();
        return bitmap;
    }

    @Override
    public String key() {
        return "circle";
    }
}

