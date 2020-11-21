package com.example.mymobil.info;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mymobil.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;
/**
 * Create by Jinyeob on 2020. 04. 28.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private List<NewsData> mDataset;
    private static View.OnClickListener onClickListener;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    //한 줄에 들어가는 요소
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextViewTitle;
        public TextView mTextViewContent;
        public SimpleDraweeView mImageView;
        public View rootView;

        public MyViewHolder(View v) {
            super(v);
            mTextViewTitle = v.findViewById(R.id.TextView_title);
            //mTextViewContent = v.findViewById(R.id.TextView_content);
            mImageView = (SimpleDraweeView) v.findViewById(R.id.ImageView_title);

            rootView=v;

            v.setClickable(true);
            v.setEnabled(true);
            v.setOnClickListener(onClickListener);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(List<NewsData> myDataset, Context context, View.OnClickListener onClick) {
        mDataset = myDataset;
        onClickListener = onClick;
        Fresco.initialize(context);
    }

    // Create new views (invoked by the layout manager)
    //전체화면
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_info, parent, false);
        //text 리스트 하나하나 xml필요

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        NewsData news = mDataset.get(position);

        //타이틀
        holder.mTextViewTitle.setText(news.getTitle());

/*
        //링크
        String content = news.getContent();
        if(news.getContent()!=null && content.length()>0){
            holder.mTextViewContent.setText(content);
        }
        else{
            holder.mTextViewContent.setText(" ");
        }
*/
        //이미지
        Uri uri = Uri.parse(news.getUrlToImage());
        holder.mImageView.setImageURI(uri);

        holder.rootView.setTag(position);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset == null ? 0 : mDataset.size();
    }
    public NewsData getNews(int position) {
        return mDataset != null ? mDataset.get(position) : null;
    }

}