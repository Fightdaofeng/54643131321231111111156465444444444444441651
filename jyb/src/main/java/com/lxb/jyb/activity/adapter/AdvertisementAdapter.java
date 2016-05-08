package com.lxb.jyb.activity.adapter;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import com.lxb.jyb.R;
import com.lxb.jyb.tool.ImageDownLoader;
import com.lxb.jyb.tool.MyClickListener;
import com.lxb.jyb.util.ImageLoaderUtil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.Toast;

public class AdvertisementAdapter extends PagerAdapter {
	private Context context;
	private List<View> views;
	ArrayList<String> advertiseArray;
	private ImageDownLoader loader;

	public AdvertisementAdapter() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AdvertisementAdapter(Context context, List<View> views,
			ArrayList<String> advertiseArray) {
		this.context = context;
		this.views = views;
		loader = new ImageDownLoader(context);
		this.advertiseArray = advertiseArray;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return views.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return (arg0 == arg1);
	}

	@Override
	public void destroyItem(View container, int position, Object object) {
		((ViewPager) container).removeView(views.get(position));
	}

	@Override
	public Object instantiateItem(View container, int position) {
		((ViewPager) container).addView(views.get(position), 0);
		View view = views.get(position);
		String head_img = advertiseArray.get(position);
		System.out.println("图片地址："+head_img);
		ImageView ivAdvertise = (ImageView) view.findViewById(R.id.ivAdvertise);
		setImageView(ivAdvertise, head_img);
	
		ivAdvertise.setTag(position);
		ivAdvertise.setOnTouchListener(new OnTouchListener() {
			
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					Toast.makeText(context, "按下了"+v.getTag(), Toast.LENGTH_LONG).show();
					break;

				default:
					break;
				}
				return false;
			}
		});
		return view;
	}

	private void setImageView(ImageView imageView, String url) {
		Bitmap bitmap = loader.downloadImage(url);
		if (bitmap != null) {
			imageView.setImageBitmap(bitmap);
		} else {
			imageView.setImageResource(R.drawable.empty_photo);
		}
	}
}
