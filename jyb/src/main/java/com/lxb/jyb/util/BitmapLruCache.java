package com.lxb.jyb.util;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.util.Log;
import com.android.volley.toolbox.ImageLoader.ImageCache;

public class BitmapLruCache extends LruCache<String, Bitmap> implements
		ImageCache {
	public BitmapLruCache(int maxSize) {
		super(maxSize);
	}

	@Override
	protected int sizeOf(String key, Bitmap bitmap) {
		return bitmap.getRowBytes() * bitmap.getHeight();
	}

	public Bitmap getBitmap(String url) {
		return get(url);
	}

	// @Override
	// protected void entryRemoved(boolean evicted, String key, Bitmap oldValue,
	// Bitmap newValue) {
	// Logger.d("BitmapLruCache","bitmap recycle");
	// if(evicted)
	// oldValue.recycle();
	// super.entryRemoved(evicted, key, oldValue, newValue);
	// }

	@SuppressLint("NewApi")
	@Override
	public void putBitmap(String url, Bitmap bitmap) {
		put(url, bitmap);
		Log.d("BitmapLruCache", "bitmap size1>>" + bitmap.getRowBytes()
				* bitmap.getHeight() + "size2>>" + bitmap.getByteCount());
	}
}
