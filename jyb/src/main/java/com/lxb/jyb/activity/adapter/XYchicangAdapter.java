package com.lxb.jyb.activity.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lxb.jyb.R;
import com.lxb.jyb.bean.OrderSelect;
import com.lxb.jyb.tool.MyClickListener;

import java.util.ArrayList;

public class XYchicangAdapter extends BaseAdapter {
    private ArrayList<OrderSelect> list;
    private Context context;
    private MyClickListener clicklistener;
    private boolean isHy;
    private ListView listView;

    public XYchicangAdapter(ArrayList<OrderSelect> nowOrder, Context context,
                            MyClickListener clicklistener) {
        super();
        this.list = nowOrder;
        this.context = context;
        this.clicklistener = clicklistener;
    }

    public ArrayList<OrderSelect> getList() {
        if (list != null) {
            this.list = (ArrayList<OrderSelect>) list.clone();
        }
        return list;
    }

    public void setList(ArrayList<OrderSelect> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        if (list.size() > 0) {
            return list.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder holder = null;
        if (position < list.size()) {
            OrderSelect bean = list.get(position);
            if (null == convertView) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(context).inflate(
                        R.layout.cclist_item, null);
                holder.order_code = (TextView) convertView
                        .findViewById(R.id.order_code);
                holder.order_name = (TextView) convertView
                        .findViewById(R.id.order_name);
                holder.order_type = (TextView) convertView
                        .findViewById(R.id.order_type);
                holder.order_num = (TextView) convertView
                        .findViewById(R.id.order_num);
                holder.order_zhisun = (TextView) convertView
                        .findViewById(R.id.order_zhisun);
                holder.order_zhiying = (TextView) convertView
                        .findViewById(R.id.order_zhiying);
                holder.order_buy = (TextView) convertView
                        .findViewById(R.id.order_buy);
                holder.order_this = (TextView) convertView
                        .findViewById(R.id.order_this);
                holder.order_price = (TextView) convertView
                        .findViewById(R.id.order_price);
                holder.order_pingcang = (ImageView) convertView
                        .findViewById(R.id.pingcang_btn);
                holder.zhixun_tv = (TextView) convertView
                        .findViewById(R.id.zhisun_tv);
                holder.zhiying_tv = (TextView) convertView
                        .findViewById(R.id.zhiying_tv);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.order_code.setText(bean.getTicket());
            holder.order_name.setText(bean.getSymbol());
            int ordertype = Integer.parseInt(bean.getOrdertype());
            if (ordertype == 0) {
                holder.order_type.setText("做多");
                holder.order_type.setBackground(context.getResources().getDrawable(
                        R.drawable.hq_item_btn_red_shap));
            } else {
                holder.order_type.setText("做空");
                holder.order_type.setBackground(context.getResources().getDrawable(
                        R.drawable.hq_item_btn_green_shap));
            }
            holder.order_num.setText(bean.getLots());
            String sl = bean.getSl();
            double parseDouble = Double.parseDouble(sl);
            if (parseDouble == 0 || parseDouble == 0.0) {
                holder.order_zhisun.setText("未设置！");
                TextPaint paint = holder.zhixun_tv.getPaint();
                paint.setFakeBoldText(true);
                TextPaint paint2 = holder.order_zhisun.getPaint();
                paint2.setFakeBoldText(true);
                holder.order_zhisun.setTextColor(context.getResources().getColor(
                        R.color.red));
                holder.zhixun_tv.setTextColor(context.getResources().getColor(
                        R.color.red));

            } else {
                holder.order_zhisun.setText(sl);
            }
            String tp = bean.getTp();
            double parseDouble2 = Double.parseDouble(tp);
            if (parseDouble2 == 0 || parseDouble2 == 0.0) {
                holder.order_zhiying.setText("未设置！");
//			TextPaint paint = holder.zhiying_tv.getPaint();
//			paint.setFakeBoldText(true);
//			TextPaint paint2 = holder.order_zhiying.getPaint();
//			paint2.setFakeBoldText(true);
//			holder.order_zhiying.setTextColor(context.getResources().getColor(
//					R.color.red));
//			holder.zhiying_tv.setTextColor(context.getResources().getColor(
//					R.color.red));
            } else {
                holder.order_zhiying.setText(bean.getTp());
            }
            holder.order_buy.setText(bean.getOpenprice());
            holder.order_this.setText("");
            holder.order_price.setText(bean.getProfit());
            String substring = bean.getProfit().substring(0, 1);
            if ("-".equals(substring)) {
                holder.order_price.setTextColor(context.getResources().getColor(
                        R.color.green));
            } else {
                holder.order_price.setTextColor(context.getResources().getColor(
                        R.color.hq_red));
            }
            holder.order_pingcang.setTag(position);
            if (bean.getTicket().equals("0")) {

            } else {
                holder.order_pingcang.setOnClickListener(clicklistener);
            }
        }
        return convertView;
    }

    public void setIsHy(boolean isHy) {
        this.isHy = isHy;
        notifyDataSetChanged();
    }

    public void setListView(ListView listView) {
        this.listView = listView;
    }

    public ListView getListView() {
        return listView;
    }

    class ViewHolder {
        private TextView order_code;
        private TextView order_name;
        private TextView order_type;
        private TextView order_num;
        private TextView order_zhisun;
        private TextView order_zhiying;
        private TextView order_buy;
        private TextView order_this;
        private TextView order_price;
        private TextView zhiying_tv;
        private TextView zhixun_tv;
        private ImageView order_pingcang;
    }
}
