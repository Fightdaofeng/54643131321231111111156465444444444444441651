package com.lxb.jyb.fragment;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.lxb.jyb.R;
import com.lxb.jyb.activity.HQXQActivity;
import com.lxb.jyb.activity.adapter.HQItemAdapter;
import com.lxb.jyb.activity.view.MyListView;
import com.lxb.jyb.bean.HQData;
import com.lxb.jyb.bean.HQentity;
import com.lxb.jyb.util.BaseTools;
import com.lxb.jyb.util.FileUtil;
import com.lxb.jyb.util.HQTitleUtil;
import com.lxb.jyb.util.HttpConstant;
import com.lxb.jyb.util.pase.HQDataParse;
import com.lxb.jyb.util.pase.HQListDataParse;
import com.nineoldandroids.view.ViewHelper;

public class ZiHQFragment extends BaseLazyFragment {
    protected WeakReference<View> weakView;
    private View view;
    private String titleName;
    private ArrayList<HQentity> hqentity;
    private ArrayList<HQentity> putentity;
    private ArrayList<HQData> listItemData;
    private ArrayList<HQData> putList;
    private HQItemAdapter adapter;
    private MyListView listview;
    private SharedPreferences sp;
    private SharedPreferences preferences;
    private Editor defEditor;
    private Editor editor;
    private String codes;
    private RequestQueue quene;
    private int isFirst = 0;
    private boolean isPrepared;
    private String tableName;
    private FileUtil fileUtil;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        if (weakView == null || weakView.get() == null) {
            view = inflater.inflate(R.layout.zihq_fragment, null);
            setUserVisibleHint(true);
            preferences = getActivity().getSharedPreferences("defaulthq",
                    Activity.MODE_PRIVATE);
            defEditor = preferences.edit();
            sp = getActivity().getSharedPreferences("hqdata",
                    Activity.MODE_PRIVATE);
            editor = sp.edit();
            quene = Volley.newRequestQueue(getActivity());
            initData();
            isPrepared = true;
            lazyload();
            // addListen();
            weakView = new WeakReference<View>(view);
        } else {
            ViewGroup parent = (ViewGroup) weakView.get().getParent();
            if (parent != null) {
                parent.removeView(weakView.get());
            }
        }
        return weakView.get();
    }

    /**
     * 获得传递过来的数据
     */
    private void initData() {
        // TODO Auto-generated method stub

        fileUtil = new FileUtil(getActivity());

        Bundle bundle = getArguments();
        String str = bundle.getString(HQFragment.ARGUMENTS_NAME);
        if ("".equals(str)) {

        } else {
            titleName = str;
        }

        listItemData = new ArrayList<HQData>();
        hqentity = new ArrayList<HQentity>();
        switch (titleName) {
            case "自选":
                tableName = "zxhq";
                initDefault();
                initDefault(hqentity);
                initPopWindow();
                initCode();
                new RequestData().execute();

                break;
            case "外汇":
                tableName = "qqwh";
                putentity = hqentity = HQTitleUtil.getQQWH();
                initDefault(hqentity);

                initCode();
                // codes = "QQWH";
                new RequestData().execute();
                break;
            case "COMEX":
                tableName = "comex";
                putentity = hqentity = HQTitleUtil.getCOMEX();
                initDefault(hqentity);

                initCode();
                new RequestData().execute();

                break;
            case "黄金":
                tableName = "xhhj";
                putentity = hqentity = HQTitleUtil.getXHHJ();
                initDefault(hqentity);

                initCode();
                // codes = "GJHJ";
                new RequestData().execute();

                break;
            case "原油":
                tableName = "yysc";
                putentity = hqentity = HQTitleUtil.getYY();
                initDefault(hqentity);

                initCode();
                // codes="YYSC";
                new RequestData().execute();
                break;
            case "商品":
                tableName = "spsc";
                putentity = hqentity = HQTitleUtil.getSPSC();
                initDefault(hqentity);

                initCode();
                new RequestData().execute();
                break;
            case "全球股指":
                tableName = "qqgz";
                putentity = hqentity = HQTitleUtil.getQQGZ();
                initDefault(hqentity);

                initCode();
                new RequestData().execute();
                break;
            case "上海金":
                tableName = "shj";
                putentity = hqentity = HQTitleUtil.getSHJ();
                initDefault(hqentity);

                initCode();
                new RequestData().execute();

                break;
            case "天通银":
                tableName = "tty";
                putentity = hqentity = HQTitleUtil.getTTY();
                initDefault(hqentity);

                initCode();
                new RequestData().execute();
                break;
            case "伦敦金属":
                tableName = "ldjs";
                putentity = hqentity = HQTitleUtil.getLDJS();
                initDefault(hqentity);

                initCode();
                new RequestData().execute();

                break;
        }

    }

    @SuppressLint("NewApi")
    private void initDefault() {
        // TODO Auto-generated method stub
        Set<String> stringSet = preferences.getStringSet("hqdef", null);
        if (null == stringSet) {
            hqentity = HQTitleUtil.getDefaultItem();
            ArrayList<String> d = new ArrayList<String>();
            for (int i = 0; i < hqentity.size(); i++) {
                d.add(hqentity.get(i).getName());

            }
            Set<String> newSet = new HashSet<String>();
            newSet.addAll(d);
            defEditor.putStringSet("hqdef", newSet);
            defEditor.commit();
        } else {
            ArrayList<String> names = new ArrayList<String>();
            names.addAll(stringSet);
            hqentity = HQTitleUtil.getZiXuanItem(names);
        }
        putentity = hqentity;
    }

    private void initCode() {

        int i;
        codes = "";
        for (i = 0; i < hqentity.size() - 1; i++) {
            String code = hqentity.get(i).getCode();
            if (code.contains("+")) {
                code = code.replace("+", "%2b");
            }
            codes += code + ",";
        }
        if (hqentity.size() > 0) {
            codes += hqentity.get(i).getCode();
        }
    }

    /**
     * 先初始化一些
     *
     * @param hqentity2
     */
    private void initDefault(ArrayList<HQentity> hqentity2) {
        // TODO Auto-generated method stub
        listItemData = new ArrayList<HQData>();
        for (int i = 0; i < hqentity2.size(); i++) {
            listItemData.add(new HQData(0, 0, 0, "", 0, 0, hqentity2.get(i)
                    .getName(), 0, 0, 0, hqentity2.get(i).getBaoliu()));
        }
        initFind(isFirst);
    }

    private class RequestData extends AsyncTask<Void, Void, Void> {

        private HQData data;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... params) {
            listItemData.clear();
            JsonArrayRequest arrayRequest = new JsonArrayRequest(
                    HttpConstant.HQ_HOST + codes, new Listener<JSONArray>() {

                @Override
                public void onResponse(JSONArray array) {
                    // TODO Auto-generated method stub
                    if (array.length() > 0) {
                        fileUtil.savaData(tableName, array.toString());
                        int j = 0;
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject optJSONObject = array
                                    .optJSONObject(i);
                            if (j < hqentity.size()) {
                                if (optJSONObject != null
                                        && optJSONObject.optString(
                                        "code").equals(
                                        hqentity.get(j)
                                                .getCode())) {
                                    data = new HQData(
                                            optJSONObject,
                                            hqentity.get(j).getBaoliu(),
                                            hqentity.get(j).getName());

                                    float float1 = sp.getFloat(
                                            data.getCode() + "new", 0);
                                    if (!(float1 + "").equals(data
                                            .getNewPrice() + "")) {
                                        editor.putString(
                                                data.getCode(),
                                                data.toJson());
                                        editor.putFloat(data.getCode()
                                                + "new", (float) data
                                                .getNewPrice());
                                        editor.commit();
                                        data.setGflags("new");
                                    } else {
                                        data.setGflags("no");
                                    }

                                    j++;
                                } else {
                                    String string = sp.getString(
                                            hqentity.get(j).getCode(),
                                            "wu");
                                    if ("wu".equals(string)) {
                                        data = new HQData(0, 0, 0,
                                                hqentity.get(j)
                                                        .getCode(), 0,
                                                0, hqentity.get(j)
                                                .getName(), 0,
                                                0, 0, hqentity.get(j)
                                                .getBaoliu());
                                    } else {
                                        try {
                                            data = HQDataParse
                                                    .parseHQData(
                                                            hqentity.get(
                                                                    j)
                                                                    .getName(),
                                                            string);

                                        } catch (JSONException e) {
                                            // TODO Auto-generated catch
                                            // block
                                            e.printStackTrace();
                                        }
                                    }
                                    j++;
                                    i--;

                                }
                            }
                            listItemData.add(data);
                        }
                        if (listItemData.size() < hqentity.size()) {
                            for (int t = listItemData.size(); t < hqentity
                                    .size(); t++) {
                                data = new HQData(0, 0, 0, hqentity
                                        .get(t).getCode(), 0, 0,
                                        hqentity.get(t).getName(), 0,
                                        0, 0, hqentity.get(t)
                                        .getBaoliu());
                                listItemData.add(data);
                            }
                        }

                    }

                    handler.sendEmptyMessage(100);
                }
            }, new ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError arg0) {
                    // TODO Auto-generated method stub
                    handler.sendEmptyMessage(5);
                }
            });
            // arrayRequest.setRetryPolicy(new DefaultRetryPolicy(3000,//
            // // 默认超时时间，应设置一个稍微大点儿的，例如本处的500000
            // DefaultRetryPolicy.DEFAULT_MAX_RETRIES,// 默认最大尝试次数
            // DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            quene.add(arrayRequest);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
        }
    }

    Handler handler = new Handler() {
        private RelativeLayout ref;

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    new Thread(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            listview.post(new Runnable() {

                                @Override
                                public void run() {
                                    // TODO Auto-generated method stub
                                    for (int i = 0; i < listItemData.size(); i++) {
                                        listItemData.get(i).setGflags("no");
                                    }
                                    adapter.setHy(true);
                                    // adapter.notifyDataSetChanged();
                                }
                            });
                        }
                    }).start();

                    break;
                case 100:
                    // if (listItemData.size() > 0) {
                    ref = (RelativeLayout) view
                            .findViewById(R.id.relative_falsh_show);
                    ref.setVisibility(View.GONE);
                    // } else {
                    // }
                    initFind(isFirst);

                    new RunViewRef().start();
                    break;
                case 5:
                    getBenDi();
                    initFind(isFirst);
                    new RunViewRef().start();
                    ref = (RelativeLayout) view
                            .findViewById(R.id.relative_falsh_show);
                    ref.setVisibility(View.GONE);

                    break;
            }

        }

    };

    @SuppressWarnings("unchecked")
    private void getBenDi() {
        // TODO Auto-generated method stub
        String read = fileUtil.read(tableName);
        if ("".equals(read) || read.length() < 10) {
            listItemData.clear();
            for (int i = 0; i < hqentity.size(); i++) {
                listItemData.add(new HQData(0, 0, 0, hqentity.get(i).getCode(),
                        0, 0, hqentity.get(i).getName(), 0, 0, 0, hqentity.get(
                        i).getBaoliu()));
            }
        } else {
            try {
                HashMap<String, Object> parseHQData = HQListDataParse
                        .parseHQData(read, hqentity);
                listItemData = (ArrayList<HQData>) parseHQData.get("hqlist");

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    private void initFind(int t) {
        // TODO Auto-generated method stub

        if (t == 0) {
            isFirst = 2;
            adapter = new HQItemAdapter(listItemData, getActivity());
            listview = (MyListView) view.findViewById(R.id.hq_list);
            adapter.setListview(listview);
            listview.setAdapter(adapter);
        }
        listview.setFlag(false);

        listview.setOnItemClickListener(listener);
//        if (tableName.equals("zxhq")) {
//            listview.setOnItemLongClickListener(new popAction());
//        }
        adapter.setList(listItemData);
        adapter.setHy(false);
        listItemData.clear();
    }
    OnItemClickListener listener = new OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            // TODO Auto-generated method stub

            putList = adapter.getList();
            if (putList.get(position).getChange() == 0
                    && putList.get(position).getNewPrice() == 0) {
                Toast.makeText(getActivity(), "暂无详情数据", Toast.LENGTH_LONG)
                        .show();
            } else {
                Intent intent = new Intent(ZiHQFragment.this.getActivity(),
                        HQXQActivity.class);
                intent.putExtra("bl", putList.get(position).getBaoliu());
                intent.putExtra("code", putList.get(position).getCode());
                intent.putExtra("name", putList.get(position).getName());
                startActivityForResult(intent, 100);
            }

        }
    };

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100 && resultCode == 200) {
            if (tableName.equals("zxhq")) {
                initDefault();
                initCode();
                // new RequestData().execute();
            }
        }
    }

    ;

    class RunViewRef extends Thread {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            super.run();
            try {
                Thread.sleep(1500);
                handler.sendEmptyMessage(1);
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            new RequestData().execute();

        }
    }

    private int itemH;// item高度，用以设置菜单位置

    /**
     * 每个ITEM中more按钮对应的点击动作
     */
    public class popAction implements OnItemLongClickListener {
        @Override
        public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
            // TODO Auto-generated method stub
            int[] arrayOfInt = new int[2];
            // 获取点击按钮的坐标
            arg1.getLocationOnScreen(arrayOfInt);
            int x = arrayOfInt[0];
            int y = arrayOfInt[1];
            View view = adapter.getView(arg2, null, listview);
            view.measure(
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            itemH = view.getMeasuredHeight();
            showPop(arg1, x, y, arg1, arg2);
            return true;
        }
    }

    private PopupWindow popupWindow;
    private TextView delete;

    /**
     * 初始化弹出的pop
     */
    private void initPopWindow() {
        View popView = LayoutInflater.from(this.getActivity()).inflate(
                R.layout.delet_menu, null);
        delete = (TextView) popView.findViewById(R.id.chat_delete_menu);
        popupWindow = new PopupWindow(popView, LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0));
    }

    /**
     * 显示popWindow
     */
    public void showPop(View parent, int x, int y, final View view,
                        final int position) {
        // 设置popwindow显示位置
        popupWindow.showAtLocation(
                parent,
                0,
                BaseTools.getWindowsWidth(this.getActivity())
                        - popupWindow.getWidth() - 150, y + itemH / 2 - 50);
        // 获取popwindow焦点
        popupWindow.setFocusable(true);
        // 设置popwindow如果点击外面区域，便关闭。
        popupWindow.setOutsideTouchable(true);
        // 为按钮绑定事件
        // 删除
        delete.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
                del(position);
                // rightRemoveAnimation(view, position);

            }
        });
        // popupWindow.update();
        // if (popupWindow.isShowing()) {
        //
        // }
    }

    @SuppressLint("NewApi")
    private void del(int position) {
        Set<String> stset = preferences.getStringSet("hqdef", null);
        String name = hqentity.get(position).getName();

        ArrayList<String> names = new ArrayList<String>();
        if (null != stset) {
            names.addAll(stset);
            for (int i = 0; i < names.size(); i++) {
                if (name.equals(names.get(i))) {
                    names.remove(i);
                    break;
                }
            }

            Set<String> newSet = new HashSet<String>();
            newSet.addAll(names);
            defEditor.putStringSet("hqdef", newSet);
            defEditor.commit();
        }
        hqentity.remove(position);
        initDefault();
        initCode();
        new RequestData().execute();
    }

    /**
     * item删除动画
     */
    @SuppressWarnings("unused")
    private void rightRemoveAnimation(final View view, final int position) {
        final Animation animation = (Animation) AnimationUtils.loadAnimation(
                this.getActivity(), R.anim.chatto_remove_anim);
        animation.setAnimationListener(new AnimationListener() {
            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationRepeat(Animation animation) {
            }

            @SuppressLint("NewApi")
            public void onAnimationEnd(Animation animation) {
                view.setAlpha(0);
                performDismiss(view, position);
                animation.cancel();
            }
        });

        view.startAnimation(animation);
    }

    protected long mAnimationTime = 150;

    @SuppressLint("NewApi")
    private void performDismiss(final View dismissView,
                                final int dismissPosition) {
        final ViewGroup.LayoutParams lp = dismissView.getLayoutParams();// 获取item的布局参数
        final int originalHeight = dismissView.getHeight();// item的高度

        ValueAnimator animator = ValueAnimator.ofInt(originalHeight, 0)
                .setDuration(mAnimationTime);
        animator.start();

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                adapter.notifyDataSetChanged();
                // 这段代码很重要，因为我们并没有将item从ListView中移除，而是将item的高度设置为0
                // 所以我们在动画执行完毕之后将item设置回来
                ViewHelper.setAlpha(dismissView, 1f);
                ViewHelper.setTranslationX(dismissView, 0);
                ViewGroup.LayoutParams lp = dismissView.getLayoutParams();
                lp.height = originalHeight;
                dismissView.setLayoutParams(lp);
                // isShowBackground();
            }
        });

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                // 这段代码的效果是ListView删除某item之后，其他的item向上滑动的效果
                lp.height = (Integer) valueAnimator.getAnimatedValue();
                dismissView.setLayoutParams(lp);
            }
        });

    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        if (titleName.equals("自选")) {
            initDefault();
            initCode();
        }
    }

    @Override
    protected void lazyload() {
        // TODO Auto-generated method stub
        if (!isPrepared || !isVisible) {
            return;
        }
        // 给view控件填充数据
        isPrepared = false;// 加载完数据后 设置为false，不然 界面来回切换时，数据会重复性加载。
    }

}
