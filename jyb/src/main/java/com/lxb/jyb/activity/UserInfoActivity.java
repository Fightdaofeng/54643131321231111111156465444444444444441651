package com.lxb.jyb.activity;

import java.io.File;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ab.util.AbDialogUtil;
import com.ab.util.AbFileUtil;
import com.ab.util.AbStrUtil;
import com.ab.util.AbToastUtil;
import com.lxb.jyb.R;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * MT4账户信息
 * 
 * @author Liuxiaobin
 *
 */
public class UserInfoActivity extends FragmentActivity implements
		OnClickListener {
	private LinearLayout userImg, top_return;
	private TextView top_title, top_right;
	private View avatarView;
	private File mCurrentPhotoFile;
	private String mFileName;
	private File PHOTO_DIR = null;
	/* 用来标识请求照相功能的activity */
	private static final int CAMERA_WITH_DATA = 3023;
	/* 用来标识请求gallery的activity */
	private static final int PHOTO_PICKED_WITH_DATA = 3021;
	/* 用来标识请求裁剪图片后的activity */
	private static final int CAMERA_CROP_DATA = 3022;
	private Bitmap bitmap;
	private ImageLoader imageLoader;
	private ImageView head_img;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getWindow()
				.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		getWindow().addFlags(
				WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.user_info);
		initFind();

	}

	private void initFind() {
		// TODO Auto-generated method stub
		initFolderDir();
		findViewById(R.id.top_msg).setVisibility(View.GONE);
		top_right = (TextView) findViewById(R.id.top_right);
		top_right.setVisibility(View.VISIBLE);
		top_right.setBackgroundResource(0);
		top_right.setText("解绑");
		top_title = (TextView) findViewById(R.id.top_title);
		top_title.setText("账号信息");
		top_return = (LinearLayout) findViewById(R.id.top_return);
		head_img = (ImageView) findViewById(R.id.head_img);

		setOnclick();
	}

	private void initFolderDir() {
		String photo_dir = AbFileUtil.getImageDownloadDir(this);
		if (AbStrUtil.isEmpty(photo_dir)) {
			AbToastUtil.showToast(this, "存储卡不存在");
		} else {
			PHOTO_DIR = new File(photo_dir);
		}
	}

	private void setOnclick() {
		// TODO Auto-generated method stub
		top_return.setOnClickListener(this);
		top_right.setOnClickListener(this);
		head_img.setOnClickListener(this);
	}

	private void showPickDialog() {
		avatarView = getLayoutInflater().inflate(R.layout.choose_avatar, null);
		Button albumButton = (Button) avatarView
				.findViewById(R.id.choose_album);
		Button camButton = (Button) avatarView.findViewById(R.id.choose_cam);
		Button cancelButton = (Button) avatarView
				.findViewById(R.id.choose_cancel);
		albumButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				AbDialogUtil.removeDialog(UserInfoActivity.this);
				// 从相册中去获取
				selectImageFromGallry();
			}

		});

		camButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				AbDialogUtil.removeDialog(UserInfoActivity.this);
				doPickPhotoAction();
			}

		});

		cancelButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				AbDialogUtil.removeDialog(UserInfoActivity.this);
			}

		});
		AbDialogUtil.showDialog(avatarView, Gravity.BOTTOM);
	}

	protected void selectImageFromGallry() {
		try {
			Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
			intent.setType("image/*");
			startActivityForResult(intent, PHOTO_PICKED_WITH_DATA);
		} catch (ActivityNotFoundException e) {
			AbToastUtil.showToast(UserInfoActivity.this, "没有找到照片");
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
			if (!AbStrUtil.isEmpty(currentFilePath)) {
				Intent intent1 = new Intent(this, CropImageActivity.class);
				intent1.putExtra("PATH", currentFilePath);
				startActivityForResult(intent1, CAMERA_CROP_DATA);
			} else {
				AbToastUtil.showToast(this, "未在存储卡中找到这个文件");
			}
			break;
		case CAMERA_WITH_DATA:
			String currentFilePath2 = mCurrentPhotoFile.getPath();
			Intent intent2 = new Intent(this, CropImageActivity.class);
			intent2.putExtra("PATH", currentFilePath2);
			startActivityForResult(intent2, CAMERA_CROP_DATA);
			break;
		case CAMERA_CROP_DATA:
			String path = mIntent.getStringExtra("PATH");
			bitmap = BitmapFactory.decodeFile(path);
			head_img.setImageBitmap(bitmap);
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
		@SuppressWarnings("deprecation")
		Cursor cursor = managedQuery(uri, projection, null, null, null);
		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		String path = cursor.getString(column_index);
		return path;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.top_return:
			this.finish();
			break;

		case R.id.head_img:
			showPickDialog();
			break;
		}
	}

}
