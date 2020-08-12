package biyaniparker.com.parker.view.adapter;

import android.content.Context;
import android.text.Html;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.squareup.picasso.Picasso;

import java.util.List;

import biyaniparker.com.parker.R;
import biyaniparker.com.parker.beans.CreateNoticeBean;

public class NoticeAdapter extends PagerAdapter {
    List<CreateNoticeBean> list;
    LayoutInflater layoutInflater;
    Context context;
    ViewMoreCallBack viewMoreCallBack;

    public NoticeAdapter(List<CreateNoticeBean> list,Context context,ViewMoreCallBack viewMoreCallBack)
    {
        this.list = list;
        this.context = context;
        this.viewMoreCallBack = viewMoreCallBack;
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
        final View view = layoutInflater.inflate(R.layout.notice_view_pager,container,false);

        ImageView imageView = view.findViewById(R.id.ivNotice);
        TextView title = view.findViewById(R.id.tvNoticeTitle);
        TextView des = view.findViewById(R.id.tvNoticeDes);
        TextView tv = view.findViewById(R.id.tvViewMore);

        if (list.get(position).getViewMore().equalsIgnoreCase("View More"))
        {
            tv.setVisibility(View.VISIBLE);
            //tv.setText("View More");
            SpannableString content = new SpannableString("View More");
            content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
            tv.setText(content);
            imageView.setVisibility(View.GONE);
            title.setVisibility(View.GONE);
            des.setVisibility(View.GONE);

            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewMoreCallBack.getViewMore();
                }
            });
        } else {
            title.setText(list.get(position).getTitle());
            des.setText(list.get(position).getDescription());
           // makeTextViewResizable(des,2 , "View More", true);

            if (list.get(position).getAttachment().equalsIgnoreCase("") || list.get(position).getAttachment().equalsIgnoreCase(null)) {
                imageView.setImageResource(R.drawable.bgchoice);
            } else {
                Picasso.get().load(list.get(position).getAttachment()).into(imageView);
            }
//        imageView.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view) {
//                String imgUrl =  productDetailsBeanslist.get(position).getImageUrl();
//                productAdaperCallBack.getImageUrl(imgUrl);
//            }
//        });
        }
        view.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if (list.get(position).equals(3)){
                    Toast.makeText(context,""+position,Toast.LENGTH_SHORT).show();
                }
                else {
                    viewMoreCallBack.getPagePosition(list.get(position).getNoticeId());
                }
                //this will log the page number that was click
                //Log.i("TAG", "This page was clicked: " + pos);
//                Toast.makeText(context,"position is"+position,Toast.LENGTH_SHORT).show();
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewMoreCallBack.getImageUrl(list.get(position).getAttachment());
            }
        });
        container.addView(view,0);
        return view;
    }
    private static void makeTextViewResizable(final TextView tvDescription, final int maxLine, final String expandText, final boolean viewMore) {
        if (tvDescription.getTag() == null) {
            tvDescription.setTag(tvDescription.getText());
        }
        ViewTreeObserver vto = tvDescription.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {
                String text;
                int lineEndIndex;
                ViewTreeObserver obs = tvDescription.getViewTreeObserver();
                obs.removeGlobalOnLayoutListener(this);
                if (maxLine == 0) {
                    lineEndIndex = tvDescription.getLayout().getLineEnd(0);
                    text = tvDescription.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                } else if (maxLine > 0 && tvDescription.getLineCount() >= maxLine) {
                    lineEndIndex = tvDescription.getLayout().getLineEnd(maxLine - 1);
                    text = tvDescription.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                } else {
                    lineEndIndex = tvDescription.getLayout().getLineEnd(tvDescription.getLayout().getLineCount() - 1);
                    text = tvDescription.getText().subSequence(0, lineEndIndex) + " " + expandText;
                }
                tvDescription.setText(text);
                tvDescription.setMovementMethod(LinkMovementMethod.getInstance());
                tvDescription.setText(addClickablePartTextViewResizable(Html.fromHtml(tvDescription.getText().toString()), tvDescription, lineEndIndex, expandText,
                        viewMore), TextView.BufferType.SPANNABLE);
            }
        });
    }

    private static SpannableStringBuilder addClickablePartTextViewResizable(final Spanned strSpanned, final TextView tv,
                                                                            final int maxLine, final String spanableText, final boolean viewMore) {
        String str = strSpanned.toString();
        SpannableStringBuilder ssb = new SpannableStringBuilder(strSpanned);

        if (str.contains(spanableText)) {
            ssb.setSpan(new ClickableSpan() {

                @Override
                public void onClick(View widget) {
                    tv.setLayoutParams(tv.getLayoutParams());
                    tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);
                    tv.invalidate();
                    if (viewMore) {
                        makeTextViewResizable(tv, -1, "View Less", false);
                    } else {
                        makeTextViewResizable(tv, 1, "View More", true);
                    }
                }
            }, str.indexOf(spanableText), str.indexOf(spanableText) + spanableText.length(), 0);

        }
        return ssb;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

    public interface ViewMoreCallBack{
        void getViewMore();
        void getPagePosition(int noticeId);
        void getImageUrl(String attachment);
    }
}
