package com.lxb.jyb.activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lxb.jyb.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/5/4 0004.
 */
public class BDMT4LieBiaoListAdapter extends BaseAdapter {
    private ArrayList<String> list;
    private Context context;

    public BDMT4LieBiaoListAdapter(ArrayList<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.stringlayout, null);
            holder.string_tv = (TextView) convertView.findViewById(R.id.string_tv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.string_tv.setText(list.get(position));
        return convertView;
    }

    public ArrayList<String> getList() {
        return list;
    }

    public void setList(ArrayList<String> list) {
        this.list = list;
    }

    class ViewHolder {
        TextView string_tv;
    }
}
