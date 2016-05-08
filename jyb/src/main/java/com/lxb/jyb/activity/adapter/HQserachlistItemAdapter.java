package com.lxb.jyb.activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lxb.jyb.R;
import com.lxb.jyb.bean.HQentity;
import com.lxb.jyb.tool.MyClickListener;


import java.util.ArrayList;

/**
 * Created by Liuxiaobin on 2016/5/6 0006.
 */
public class HQserachlistItemAdapter extends BaseAdapter {
    private ArrayList<HQentity> list;
    private Context context;
    private MyClickListener clickListener;

    public HQserachlistItemAdapter(ArrayList<HQentity> list, Context context, MyClickListener clickListener) {
        this.list = list;
        this.context = context;
        this.clickListener = clickListener;
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
        HQentity hQentity = list.get(position);
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.hqsreach_item, null);
            holder.tv1 = (TextView) convertView.findViewById(R.id.tv1);
            holder.tv2 = (TextView) convertView.findViewById(R.id.tv2);
            holder.jiaview = (ImageView) convertView.findViewById(R.id.jiajian_img);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.jiaview.setSelected(false);
        holder.tv1.setText(hQentity.getName());
        holder.tv2.setText(hQentity.getCode().replace("IXFX",""));
        if (hQentity.getIsCheck()) {
            holder.jiaview.setSelected(true);
        } else {
            holder.jiaview.setSelected(false);
        }
        holder.jiaview.setTag(position);
        holder.jiaview.setOnClickListener(clickListener);
        return convertView;
    }


    class ViewHolder {
        private TextView tv1;
        private TextView tv2;
        private ImageView jiaview;
    }

    public void setList(ArrayList<HQentity> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public ArrayList<HQentity> getList() {
        return list;
    }
}
