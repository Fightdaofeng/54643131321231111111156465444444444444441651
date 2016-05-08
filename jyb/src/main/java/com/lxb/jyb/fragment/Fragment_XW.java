package com.lxb.jyb.fragment;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.view.slidingmenu.SlidingMenu;
import com.lxb.jyb.MyApplication;
import com.lxb.jyb.R;
import com.lxb.jyb.activity.CalShaiXuanActivity;
import com.lxb.jyb.activity.XinWenFLAcitivity;
import com.lxb.jyb.activity.adapter.DingyueTextAdapter;
import com.lxb.jyb.activity.adapter.NewsFragmentPagerAdapter;
import com.lxb.jyb.activity.view.ColumTitleZDY;
import com.lxb.jyb.activity.view.ColumnHorizontalScrollView;
import com.lxb.jyb.bean.CalSXItem;
import com.lxb.jyb.bean.NewsCategory;
import com.lxb.jyb.tool.MyClickListener;
import com.lxb.jyb.util.BaseTools;

public class Fragment_XW extends Fragment {
    static final String ARGUMENTS_NAME = "新闻标题";

    private View view;
    private ArrayList<NewsCategory> categories;
    private MyApplication application;
    private ColumnHorizontalScrollView mColumnHorizontalScrollView;
    private LinearLayout mRadioGroup_content;
    private LinearLayout ll_more_columns;
    RelativeLayout rl_column;
    private ViewPager mViewPager;
    private TextView button_more_columns;
    /**
     * 当前选中的栏目
     */
    private int columnSelectIndex = 1;
    /**
     * 屏幕宽度
     */
    private int mScreenWidth = 0;
    /**
     * Item宽度
     */
    private int mItemWidth = 0;
    protected SlidingMenu side_drawer;
    private ColumTitleZDY titleZDY;
    private ArrayList<ColumTitleZDY> columTitleZDYs = new ArrayList<ColumTitleZDY>();
    public static final String FIRST_JR = "NEWSFIRST";
    private PopupWindow popu1;
    private ArrayList<CalSXItem> clsItem;
    private String[] arrays = {"美元", "欧元", "英镑", "澳元", "日元", "瑞郎", "加元",
            "人民币", "黄金", "白银", "商品", "股指"};


    /**
     * jw_fragment_item
     */
    private ArrayList<Fragment> fragments = new ArrayList<Fragment>();
    protected WeakReference<View> mRootView;


    private SharedPreferences sp;

    private Handler xwhandler;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        // view = inflater.inflate(R.layout.fragment_xw, null, false);
        mScreenWidth = BaseTools.getWindowsWidth(getActivity());
        mItemWidth = mScreenWidth / 25;// 一个Item宽度为屏幕的1/16
        if (mRootView == null || mRootView.get() == null) {
            view = inflater.inflate(R.layout.fragment_xw, null);
            handler.sendEmptyMessage(1);
            mRootView = new WeakReference<View>(view);
        } else {
            ViewGroup parent = (ViewGroup) mRootView.get().getParent();
            if (parent != null) {
                parent.removeView(mRootView.get());
            }
        }
        return mRootView.get();
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }

    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    // 请求新闻分类数据
                    initObject();
                    break;

                case 2:
                    // 初始化标题栏
                    initView();
                    break;
            }
        }

        ;
    };

    /**
     * 初始化对象
     */
    private void initObject() {
        // TODO Auto-generated method stub
        sp = this.getActivity().getSharedPreferences("setup",
                Activity.MODE_PRIVATE);

        application = (MyApplication) getActivity().getApplication();
        String stringSet = sp.getString("xwdef", null);

        categories = new ArrayList<NewsCategory>();
        if (null != stringSet) {
            String substring = stringSet.substring(1, stringSet.length() - 1);
            String[] split = substring.split(",");
            for (int i = 0; i < split.length; i++) {
                NewsCategory newsCategory = new NewsCategory(split[i].replace(
                        " ", ""));
                categories.add(newsCategory);
            }
            handler.sendEmptyMessage(2);
        }
    }

    private void initView() {
        // TODO Auto-generated method stub
        mColumnHorizontalScrollView = (ColumnHorizontalScrollView) view
                .findViewById(R.id.mColumnHorizontalScrollView);
        mRadioGroup_content = (LinearLayout) view
                .findViewById(R.id.mRadioGroup_content);
        ll_more_columns = (LinearLayout) view
                .findViewById(R.id.ll_more_columns);
        rl_column = (RelativeLayout) view.findViewById(R.id.rl_column);
        button_more_columns = (TextView) view
                .findViewById(R.id.button_more_columns);
        mViewPager = (ViewPager) view.findViewById(R.id.mViewPager);
        ll_more_columns.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivityForResult(
                        new Intent(Fragment_XW.this.getActivity(),
                                XinWenFLAcitivity.class), 100);
            }
        });
        setChangelView();
    }

    private void setChangelView() {
        // TODO Auto-generated method stub

        initTabColumn();
        initFragment();
    }

    /**
     * 初始化fragment
     */
    private void initFragment() {
        // TODO Auto-generated method stub
        fragments.clear();// 清空
        int count = categories.size();
        for (int i = 0; i < count; i++) {
            Bundle data = new Bundle();
            data.putString("tagName", categories.get(i).getNewsCategory());
            if (i == 0) {
                XinWenDYFragment XinWenDYFragment = new XinWenDYFragment();
                XinWenDYFragment.setArguments(data);
                fragments.add(XinWenDYFragment);
            } else {
                XinWenFragment newfragment = new XinWenFragment();
                newfragment.setArguments(data);
                fragments.add(newfragment);
            }
        }
        NewsFragmentPagerAdapter mAdapetr = new NewsFragmentPagerAdapter(
                getChildFragmentManager(), fragments);
        mViewPager.setOffscreenPageLimit(0);
        mViewPager.setAdapter(mAdapetr);
        mViewPager.setOnPageChangeListener(pageListener);
        mViewPager.setCurrentItem(columnSelectIndex);
    }

    /**
     * 初始化Column栏目项
     */
    private void initTabColumn() {
        // TODO Auto-generated method stub
        mRadioGroup_content.removeAllViews();
        int count = categories.size();
        mColumnHorizontalScrollView.setParam(getActivity(), mScreenWidth,
                mRadioGroup_content, null, null, ll_more_columns, rl_column);
        for (int i = 0; i < count; i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            params.leftMargin = 1;
            params.rightMargin = mItemWidth;
            titleZDY = new ColumTitleZDY(getActivity());
            titleZDY.setTextViewText(categories.get(i).getNewsCategory());
            titleZDY.setTextSize(16);
            titleZDY.setTextColor(getResources().getColorStateList(
                    R.color.black));
            if (columnSelectIndex == i) {
                titleZDY.setSelected(true);
                titleZDY.setTextSize(18);
                titleZDY.setTextColor(getResources().getColorStateList(
                        R.color.xinwen_lan));

            }
            titleZDY.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    for (int i = 0; i < mRadioGroup_content.getChildCount(); i++) {
                        View localView = mRadioGroup_content.getChildAt(i);
                        if (localView != v)
                            localView.setSelected(false);
                        else {
                            localView.setSelected(true);
                            mViewPager.setCurrentItem(i);
                        }
                    }
                }
            });
            columTitleZDYs.add(titleZDY);
            mRadioGroup_content.addView(titleZDY, i, params);
        }
    }

    /**
     * ViewPager切换监听方法
     */
    public OnPageChangeListener pageListener = new OnPageChangeListener() {

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int position) {
            // TODO Auto-generated method stub
            application.setCunrentfragment(position);
            mViewPager.setCurrentItem(position);
            selectTab(position);
        }
    };


    /**
     * 选择的Column里面的Tab
     */
    private void selectTab(int tab_postion) {
        columnSelectIndex = tab_postion;

        for (int i = 0; i < mRadioGroup_content.getChildCount(); i++) {
            View checkView = mRadioGroup_content.getChildAt(tab_postion);
            int k = checkView.getMeasuredWidth();
            int l = checkView.getLeft();
            int i2 = l + k / 2 - mScreenWidth / 2;
            mColumnHorizontalScrollView.smoothScrollTo(i2, 0);

        }
        if (tab_postion == 0) {
            initPopupwindow();
        }
        // 判断是否选中
        for (int j = 0; j < mRadioGroup_content.getChildCount(); j++) {
            ColumTitleZDY checkView = (ColumTitleZDY) mRadioGroup_content
                    .getChildAt(j);
            boolean ischeck;
            if (j == tab_postion) {
                ischeck = true;
            } else {
                ischeck = false;
            }
//		

            checkView.setSelected(ischeck);
            if (ischeck) {
                checkView.setTextColor(getResources().getColorStateList(
                        R.color.xinwen_lan));
                checkView.setTextSize(18);
            } else {
                checkView.setTextColor(getResources().getColorStateList(
                        R.color.black));
                checkView.setTextSize(16);
            }
        }
    }

    private DingyueTextAdapter txtAdapter;

    private void initPopupwindow() {
        // TODO Auto-generated method stube
        LayoutInflater inflater = (LayoutInflater) this.getActivity()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View pp = inflater.inflate(R.layout.xinwen_popupwindow, null);
        GridView gridview = (GridView) pp.findViewById(R.id.item_add);
        popu1 = new PopupWindow(pp, LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        popu1.setFocusable(true);
        popu1.setOutsideTouchable(false);
        clsItem = new ArrayList<CalSXItem>();
        for (int i = 0; i < arrays.length; i++) {
            clsItem.add(new CalSXItem(arrays[i]));
        }

        txtAdapter = new DingyueTextAdapter(clsItem, getActivity(), clickListener);
        gridview.setAdapter(txtAdapter);
        pp.findViewById(R.id.close_pp).setOnClickListener(
                new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        popu1.dismiss();
                        xwhandler.sendEmptyMessage(120);
                    }
                });
        popu1.showAtLocation(view, Gravity.CENTER, 0, 0);
        // editor.putBoolean(FIRST_JR, false);
    }

    MyClickListener clickListener = new MyClickListener() {

        @Override
        public void myOnClick(int position, View v) {
            // TODO Auto-generated method stub
            if (v.isSelected()) {
                v.setSelected(false);
            } else {
                v.setSelected(true);
            }
            int tag = (int) v.getTag();

            String string = clsItem.get(tag).getName();
            Toast.makeText(Fragment_XW.this.getActivity(), string, Toast.LENGTH_LONG).show();
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (resultCode == 200) {
                handler.sendEmptyMessage(1);
            }
        }
    }

    public Handler getXwhandler() {
        return xwhandler;
    }

    public void setXwhandler(Handler xwhandler) {
        this.xwhandler = xwhandler;
    }
}
