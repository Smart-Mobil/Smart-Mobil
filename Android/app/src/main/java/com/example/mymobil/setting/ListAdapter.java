package com.example.mymobil.setting;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mymobil.R;

import java.util.ArrayList;

/**
 * Add by Jinyeob on 2020.04.23
 * ListAdapter
 *
 */

public class ListAdapter extends BaseAdapter {
    private Context mContext = null;
    private LayoutInflater mLayoutInflater = null;
    private ArrayList<item_list> array_item_list;

    public ListAdapter(Context context, ArrayList<item_list> data) {
        mContext = context;
        array_item_list = data;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return array_item_list.size();
    }

    @Override
    public item_list getItem(int position) {
        return array_item_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.setting_item, null);

        ImageView imageView = (ImageView) view.findViewById(R.id.image_setting);
        TextView settingName = (TextView) view.findViewById(R.id.text_setting);
        TextView settingExplain = (TextView) view.findViewById(R.id.text_setting_explain);

        imageView.setImageResource(array_item_list.get(position).getSettingIcon());
        settingName.setText(array_item_list.get(position).getSettingName());
        settingExplain.setText(array_item_list.get(position).getSettingExplain());

        return view;
    }

}
