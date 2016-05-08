package com.lxb.jyb.activity;

import java.io.File;

import com.lxb.jyb.R;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.util.AbFileUtil;
import com.ab.util.AbImageUtil;
import com.ab.util.AbStrUtil;
import com.ab.util.AbToastUtil;
import com.ab.view.cropimage.CropImage;
import com.ab.view.cropimage.CropImageView;

/**
 * 裁剪界面
 * 
 */
public class CropImageActivity extends FragmentActivity implements
		OnClickListener {
	private static final String TAG = "CropImageActivity";
	private CropImageView mImageView;
	private Bitmap mBitmap;
	private CropImage mCrop;
	private String mPath = "CropImageActivity";
	public int screenWidth = 0;
	public int screenHeight = 0;

	/* 用来标识请求照相功能的activity */
	private static final int CAMERA_WITH_DATA = 3023;
	/* 用来标识请求gallery的activity */
	private static final int PHOTO_PICKED_WITH_DATA = 3021;
	/* 用来标识请求裁剪图片后的activity */
	private static final int CAMERA_CROP_DATA = 3022;

	private File mCurrentPhotoFile;
	private String mFileName;
	private File PHOTO_DIR = null;
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {

			switch (msg.what) {
			case 1:
				break;

			}

		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow()
				.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		getWindow().addFlags(
				WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.crop_image);
		init();
	}

	@Override
	protected void onStop() {
		super.onStop();
		if (mBitmap != null) {
			mBitmap = null;
		}
	}

	private void init() {
		TextView tv_title = (TextView) findViewById(R.id.top_title);
		tv_title.setText("照片裁剪");
		findViewById(R.id.top_msg).setVisibility(View.INVISIBLE);
		findViewById(R.id.top_return).setOnClickListener(this);
		getWindowWH();
		mPath = getIntent().getStringExtra("PATH");
		mImageView = (CropImageView) findViewById(R.id.crop_image);
		findViewById(R.id.btn_cancel).setOnClickListener(this);
		findViewById(R.id.btn_take_photo).setOnClickListener(this);
		findViewById(R.id.btn_gallry).setOnClickListener(this);
		findViewById(R.id.btn_select).setOnClickListener(this);
		setImage(mPath);
		initFolderDir();
	}

	private void initFolderDir() {
		String photo_dir = AbFileUtil.getImageDownloadDir(this);
		if (AbStrUtil.isEmpty(photo_dir)) {
			AbToastUtil.showToast(this, "存储卡不存在");
		} else {
			PHOTO_DIR = new File(photo_dir);
		}
	}

	private void setImage(String path) {
		File mFile = new File(path);
		try {
			mBitmap = AbFileUtil.getBitmapFromSD(mFile, AbImageUtil.SCALEIMG,
					500, 500);
			if (mBitmap == null) {
				Toast.makeText(CropImageActivity.this, "没有找到图片", 0).show();
				finish();
			} else {
				resetImageView(mBitmap);
			}
		} catch (Exception e) {
			Toast.makeText(CropImageActivity.this, "没有找到图片", 0).show();
			finish();
		}
	}

	/**
	 * 获取屏幕的高和宽
	 */
	private void getWindowWH() {
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		screenWidth = dm.widthPixels;
		screenHeight = dm.heightPixels;
	}

	private void resetImageView(Bitmap b) {
		mImageView.clear();
		mImageView.setImageBitmap(b);
		mImageView.setImageBitmapResetBase(b, true);
		mCrop = new CropImage(this, mImageView, mHandler);
		mCrop.crop(b);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_cancel:
			finish();
			break;
		case R.id.btn_take_photo:
			doPickPhotoAction();
			break;
		case R.id.btn_gallry:
			selectImageFromGallry();
			break;
		case R.id.btn_select:
			dealCropResult();
			break;
		case R.id.top_return:
			this.finish();
			break;
		}
	}

	protected void dealCropResult() {
		String path = mCrop.saveToLocal(mCrop.cropAndSave());
		Log.i(TAG, "裁剪后图片的路径是 = " + path);
		Intent intent = new Intent();
		intent.putExtra("PATH", path);
		setResult(RESULT_OK, intent);
		finish();
	}

	protected void selectImageFromGallry() {
		try {
			Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
			intent.setType("image/*");
			startActivityForResult(intent, PHOTO_PICKED_WITH_DATA);
		} catch (ActivityNotFoundException e) {
			AbToastUtil.showToast(this, "没有找到照片");
		}
	}

	/**
	 * 从照相机获取
	 */
	private void doPickPhotoAction() {
		String status = Environment.getExternalStorageState();
		// 判断是否有SD卡,如果有sd卡存入sd卡在说，没有sd卡直接转换为图片
		if (status.equals(Environment.MEDIA_MOUNTED)) {
			doTakePhoto();
		} else {
			AbToastUtil.showToast(this, "没有可用的存储卡");
		}
	}

	/**
	 * 拍照获取图片
	 */

	protected void doTakePhoto() {
		try {
			mFileName = System.currentTimeMillis() + ".png";
			mCurrentPhotoFile = new File(PHOTO_DIR, mFileName);
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);
			intent.putExtra(MediaStore.EXTRA_OUTPUT,
					Uri.fromFile(mCurrentPhotoFile));
			startActivityForResult(intent, CAMERA_WITH_DATA);
		} catch (Exception e) {
			AbToastUtil.showToast(this, "未找到系统相机程序");
		}
	}

	/**
	 * 描述：因为调用了Camera和Gally所以要判断他们各自的返回情况, 他们启动时是这样的startActivityForResult
	 */
	protected void onActivityResult(int requestCode, int resultCode,
			Intent mIntent) {
		if (resultCode != RESULT_OK) {
			return;
		}
		switch (requestCode) {
		case PHOTO_PICKED_WITH_DATA:
			Uri uri = mIntent.getData();
			String currentFilePath = getPath(uri);
			setImage(currentFilePath);
			break;
		case CAMERA_WITH_DATA:
			String currentFilePath2 = mCurrentPhotoFile.getPath();
			setImage(currentFilePath2);
			break;
		case CAMERA_CROP_DATA:
			/*
			 * String path = mIntent.getStringExtra("PATH"); Bitmap bitmap =
			 * BitmapFactory.decodeFile(path);
			 */
			AbToastUtil.showToast(this, "deal image");
			break;
		}
	}

	/**
	 * 从相册得到的url转换为SD卡中图片路径
	 */
	public String getPath(Uri uri) {
		if (AbStrUtil.isEmpty(uri.getAuthority())) {
			return null;
		}
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(uri, projection, null, null, null);
		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		String path = cursor.getString(column_index);
		return path;
	}
}
