package com.lxb.jyb.activity.adapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lxb.jyb.R;
import com.lxb.jyb.bean.NewsBean;
import com.lxb.jyb.tool.ImageDownLoader;

public class Fragment_All_Adapter extends BaseAdapter implements
		OnScrollListener {
	private Context context;
	private ArrayList<NewsBean> newsBeanlist;
	/** 数据源 */
	private ImageDownLoader loader;
	/** 判定是否第一次加载 */
	private boolean isFirstEnter = true;
	/** 第一张可见Item下标 */
	private int firstVisibleItem;
	/** 每屏Item可见数 */
	private int visibleItemCount;
	private ListView listView;
	private Handler handler;
	private View viewlay;
	private RelativeLayout toTop;

	public Fragment_All_Adapter(Context context, ArrayList<NewsBean> NewsBean,
			ListView listView2, Handler handler, View view) {
		this.context = context;
		this.newsBeanlist = NewsBean;
		this.listView = listView2;
		this.handler = handler;
		loader = new ImageDownLoader(context);
		this.listView.setOnScrollListener(this);
		this.viewlay = view;
	}

	public void removeDuplicateWithOrder() {
		Set set = new HashSet();
		List newList = new ArrayList();
		for (Iterator iter = this.newsBeanlist.iterator(); iter.hasNext();) {
			Object element = iter.next();
			if (set.add(element))
				newList.add(element);
		}
		this.newsBeanlist.clear();
		this.newsBeanlist.addAll(newList);
	}

	

	public List<NewsBean> getNewsBean() {
		return newsBeanlist;
	}

	public void setNewsBean(ArrayList<NewsBean> NewsBean) {
		this.newsBeanlist = NewsBean;
		notifyDataSetChanged();
	}

	@SuppressWarnings("unchecked")
	public void setDeviceList(ArrayList<NewsBean> list) {
		if (list != null) {
			newsBeanlist = (ArrayList<NewsBean>) list.clone();
			notifyDataSetChanged();
		}
	}

	public void clearDeviceList() {
		if (newsBeanlist != null) {
			newsBeanlist.clear();
		}
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return newsBeanlist == null ? 0 : newsBeanlist.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return newsBeanlist.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View contenter, ViewGroup arg2) {
		ViewHolder holder = new ViewHolder();
		if (contenter == null) {
			contenter = LayoutInflater.from(context).inflate(
					R.layout.news_item, null, false);
			holder.addtime = (TextView) contenter
					.findViewById(R.id.tv_frag_jwls_time);
			holder.title = (TextView) contenter
					.findViewById(R.id.tv_frag_jwls_title);
			holder.imageurl = (ImageView) contenter
					.findViewById(R.id.img_frag_jwls);

			contenter.setTag(holder);
		} else {
			holder = (ViewHolder) contenter.getTag();
		}

		// 通过SimpleDateFormat获取24小时制时间
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd",
				Locale.getDefault());
		String currentd = df.format(new Date());
		String updatetime = newsBeanlist.get(position).getUpdatetime();
		String strTime;
		if (currentd.equals(updatetime.substring(0, 10))) {
			strTime = updatetime.substring(11, updatetime.length() - 3);
		} else {
			strTime = updatetime.substring(5, updatetime.length() - 3);
		}
		holder.addtime.setText(strTime);
		holder.title.setText(newsBeanlist.get(position).getNewsTitle());
		holder.imageurl.setTag(newsBeanlist.get(position).getNewsThumbnail());
		String newsThumbnail = newsBeanlist.get(position).getNewsThumbnail();
		if (newsThumbnail.length() > 0) {
			setImageView(holder.imageurl, newsBeanlist.get(position)
					.getNewsThumbnail());
		} else {
			holder.imageurl.setVisibility(View.GONE);
		}
		return contenter;
	}

	private void setImageView(ImageView imageView, String url) {
		Bitmap bitmap = loader.getBitmapCache(url);
		if (bitmap != null) {
			imageView.setImageBitmap(bitmap);
		} else {
			imageView.setImageResource(R.drawable.empty_photo);
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// 当停止滚动时，加载图片
		if (scrollState == SCROLL_STATE_IDLE) {
			if (listView != null
					&& view.getLastVisiblePosition() == view.getCount() - 1) {
				handler.sendEmptyMessage(107);
			} else {
				loadImage(firstVisibleItem, visibleItemCount);
			}
			toTop = (RelativeLayout) viewlay
					.findViewById(R.id.main_rl_listview_to_top_arrow);
			if (view.getFirstVisiblePosition() >= 10) {

				toTop.setVisibility(View.VISIBLE);
				toTop.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						listView.setSelection(0);
						toTop.setVisibility(View.GONE);
					}
				});
			} else {
				toTop.setVisibility(View.GONE);
			}
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		this.firstVisibleItem = firstVisibleItem;
		this.visibleItemCount = visibleItemCount;
		if (isFirstEnter && visibleItemCount > 0) {
			loadImage(firstVisibleItem, visibleItemCount);
			isFirstEnter = false;
		}
	}

	/**
	 * 加载图片，若缓存中没有，则根据url下载
	 * 
	 * @param firstVisibleItem
	 * @param visibleItemCount
	 */
	private void loadImage(int firstVisibleItem, int visibleItemCount) {
		Bitmap bitmap = null;
		try {
			for (int i = firstVisibleItem; i < firstVisibleItem
					+ visibleItemCount; i++) {

				String url = newsBeanlist.get(i).getNewsThumbnail();
				final ImageView imageView = (ImageView) listView
						.findViewWithTag(url);
				bitmap = loader.getBitmapCache(url);
				if (bitmap != null) {
					imageView.setImageBitmap(bitmap);
					notifyDataSetChanged();
				} else {
					// 防止滚动时多次下载
					if (loader.getTaskCollection().containsKey(url)) {
						continue;
					}
					imageView.setImageDrawable(context.getResources()
							.getDrawable(R.drawable.empty_photo));
					loader.loadImage(url, imageView.getWidth(),
							imageView.getHeight(),
							new ImageDownLoader.AsyncImageLoaderListener() {

								@Override
								public void onImageLoader(Bitmap bitmap) {
									if (imageView != null && bitmap != null) {
										imageView.setImageBitmap(bitmap);
										notifyDataSetChanged();
									}
								}
							});
				}

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 取消下载任务
	 */
	public void cancelTasks() {
		loader.cancelTasks();
	}

	class ViewHolder {
		private TextView title;
		private TextView addtime;
		private ImageView imageurl;
	}
}
