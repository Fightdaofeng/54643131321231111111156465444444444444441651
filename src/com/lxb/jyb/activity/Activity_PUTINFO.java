package com.lxb.jyb.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.util.AbDialogUtil;
import com.ab.util.AbFileUtil;
import com.ab.util.AbStrUtil;
import com.ab.util.AbToastUtil;
import com.lxb.jyb.R;
import com.lxb.jyb.activity.adapter.GuanzhuTextAdapter;
import com.lxb.jyb.activity.view.RoundImageView;
import com.lxb.jyb.bean.CalSXItem;
import com.lxb.jyb.tool.MyClickListener;
import com.lxb.jyb.util.SetStatiColor;
import com.lxb.jyb.wheelview.ArrayWheelAdapter;
import com.lxb.jyb.wheelview.OnWheelChangedListener;
import com.lxb.jyb.wheelview.WheelView;

/**
 * 完善个人信息
 * 
 * @author Liuxiaobin
 *
 */
public class Activity_PUTINFO extends BaseActivity implements OnClickListener,
		OnWheelChangedListener {
	private TextView sex_txt, body_txt, city_txt, guanzhu_txt;
	private DatePickerDialog dialog;
	private Calendar calendar;
	private WheelView mViewProvince;
	private WheelView mViewCity;
	private WheelView mViewDistrict;
	private Button mBtnConfirm;
	private PopupWindow citywindow;
	private GuanzhuTextAdapter txtAdapter;
	private String[] arrays = { "美元", "欧元", "英镑", "澳元", "日元", "瑞郎", "加元",
			"人民币", "黄金", "白银", "商品", "股指" };
	private ArrayList<CalSXItem> clsItem;
	private RoundImageView head_img;
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
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getWindow()
				.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		getWindow().addFlags(
				WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		SetStatiColor.setMiuiStatusBarDarkMode(this, true);
		setContentView(R.layout.activity_wanshanxinxi);
		initFind();
	}

	private void initFind() {
		// TODO Auto-generated method stub
		initFolderDir();
		findViewById(R.id.sex_lay).setOnClickListener(this);
		sex_txt = (TextView) findViewById(R.id.sex_txt);
		findViewById(R.id.body_lay).setOnClickListener(this);
		body_txt = (TextView) findViewById(R.id.body_txt);
		findViewById(R.id.selcity_lay).setOnClickListener(this);
		city_txt = (TextView) findViewById(R.id.city_txt);
		findViewById(R.id.guanzhu_lay).setOnClickListener(this);
		guanzhu_txt = (TextView) findViewById(R.id.guanzhu_txt);
		head_img = (RoundImageView) findViewById(R.id.head_img);
		head_img.setOnClickListener(this);
	}
	private void initFolderDir() {
		String photo_dir = AbFileUtil.getImageDownloadDir(this);
		if (AbStrUtil.isEmpty(photo_dir)) {
			AbToastUtil.showToast(this, "存储卡不存在");
		} else {
			PHOTO_DIR = new File(photo_dir);
		}
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.head_img:
			showPickDialog();
			break;
		case R.id.sex_lay:
			initSexPopu();
			break;

		case R.id.sex_man:
			sex_txt.setText("男");
			break;
		case R.id.sex_woman:
			sex_txt.setText("女");
			break;
		case R.id.body_lay:
			calendar = Calendar.getInstance();
			dialog = new DatePickerDialog(this, new OnDateSetListener() {

				@Override
				public void onDateSet(DatePicker view, int year,
						int monthOfYear, int dayOfMonth) {
					// TODO Auto-generated method stub
					body_txt.setText(year + "-" + (monthOfYear + 1) + "-"
							+ dayOfMonth);
				}
			}, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
					calendar.get(Calendar.DAY_OF_MONTH));
			dialog.show();
			break;
		case R.id.selcity_lay:
			initCityPp();
			break;
		case R.id.btn_confirm:
			showSelectedResult();
			break;
		case R.id.guanzhu_lay:
			initGuanZhuPp();
			break;
		}
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
				AbDialogUtil.removeDialog(Activity_PUTINFO.this);
				// 从相册中去获取
				selectImageFromGallry();
			}

		});

		camButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				AbDialogUtil.removeDialog(Activity_PUTINFO.this);
				doPickPhotoAction();
			}

		});

		cancelButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				AbDialogUtil.removeDialog(Activity_PUTINFO.this);
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
			AbToastUtil.showToast(Activity_PUTINFO.this, "没有找到照片");
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
	/**
	 * 关注选择
	 */
	private void initGuanZhuPp() {
		// TODO Auto-generated method stub
		View pp = LayoutInflater.from(this).inflate(
				R.layout.guanzhu_popupwindow, null);
		GridView gridview = (GridView) pp.findViewById(R.id.item_add);
		PopupWindow popu1 = new PopupWindow(pp, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);

		popu1.setFocusable(true);
		popu1.setOutsideTouchable(false);
		// 实例化一个ColorDrawable颜色为半透明
		ColorDrawable dw = new ColorDrawable(0xb0000000);
		popu1.setBackgroundDrawable(dw);

		// 设置popWindow的显示和消失动画
		popu1.setAnimationStyle(R.style.mypopwindow_anim_style);
		clsItem = new ArrayList<CalSXItem>();
		for (int i = 0; i < arrays.length; i++) {
			clsItem.add(new CalSXItem(arrays[i]));
		}

		txtAdapter = new GuanzhuTextAdapter(clsItem, Activity_PUTINFO.this,
				clickListener);
		gridview.setAdapter(txtAdapter);
		popu1.showAtLocation(Activity_PUTINFO.this.findViewById(R.id.bl),
				Gravity.BOTTOM, 0, 0);

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
			Toast.makeText(Activity_PUTINFO.this, string, 1).show();
		}
	};


	/**
	 * 城市选择popupwindow
	 */
	private void initCityPp() {
		// TODO Auto-generated method stub
		View view = LayoutInflater.from(this).inflate(
				R.layout.city_popupwindow, null);
		citywindow = new PopupWindow(view,
				WindowManager.LayoutParams.MATCH_PARENT,
				WindowManager.LayoutParams.WRAP_CONTENT);
		// 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
		citywindow.setFocusable(true);
		// 实例化一个ColorDrawable颜色为半透明
		ColorDrawable dw = new ColorDrawable(0xb0000000);
		citywindow.setBackgroundDrawable(dw);

		// 设置popWindow的显示和消失动画
		citywindow.setAnimationStyle(R.style.mypopwindow_anim_style);
		// 在底部显示
		citywindow.showAtLocation(Activity_PUTINFO.this.findViewById(R.id.bl),
				Gravity.BOTTOM, 0, 0);

		// popWindow消失监听方法
		citywindow.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				System.out.println("popWindow消失");
			}
		});
		mViewProvince = (WheelView) view.findViewById(R.id.id_province);
		mViewCity = (WheelView) view.findViewById(R.id.id_city);
		mViewDistrict = (WheelView) view.findViewById(R.id.id_district);
		mBtnConfirm = (Button) view.findViewById(R.id.btn_confirm);

		mViewProvince.addChangingListener(this);
		mViewCity.addChangingListener(this);
		mViewDistrict.addChangingListener(this);
		mBtnConfirm.setOnClickListener(this);
		initProvinceDatas();
		mViewProvince.setViewAdapter(new ArrayWheelAdapter<String>(
				Activity_PUTINFO.this, mProvinceDatas));
		mViewProvince.setVisibleItems(7);
		mViewCity.setVisibleItems(7);
		mViewDistrict.setVisibleItems(7);

		updateCities();
		updateAreas();
	}

	/**
	 * 显示性别的popupwindow
	 */
	private void initSexPopu() {
		// TODO Auto-generated method stub
		View view = LayoutInflater.from(this).inflate(R.layout.sex_popupwindow,
				null);
		PopupWindow window = new PopupWindow(view,
				WindowManager.LayoutParams.MATCH_PARENT,
				WindowManager.LayoutParams.WRAP_CONTENT);
		// 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
		window.setFocusable(true);
		// 实例化一个ColorDrawable颜色为半透明
		ColorDrawable dw = new ColorDrawable(0xb0000000);
		window.setBackgroundDrawable(dw);

		// 设置popWindow的显示和消失动画
		window.setAnimationStyle(R.style.mypopwindow_anim_style);
		// 在底部显示
		window.showAtLocation(Activity_PUTINFO.this.findViewById(R.id.bl),
				Gravity.BOTTOM, 0, 0);

		// 这里检验popWindow里的button是否可以点击
		view.findViewById(R.id.sex_man).setOnClickListener(this);
		view.findViewById(R.id.sex_woman).setOnClickListener(this);

		// popWindow消失监听方法
		window.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				System.out.println("popWindow消失");
			}
		});

	}

	@Override
	public void onChanged(WheelView wheel, int oldValue, int newValue) {
		// TODO Auto-generated method stub
		if (wheel == mViewProvince) {
			updateCities();
		} else if (wheel == mViewCity) {
			updateAreas();
		} else if (wheel == mViewDistrict) {
			mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[newValue];
			mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
		}
	}

	private void updateAreas() {
		int pCurrent = mViewCity.getCurrentItem();
		mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
		String[] areas = mDistrictDatasMap.get(mCurrentCityName);

		if (areas == null) {
			areas = new String[] { "" };
		}
		mViewDistrict
				.setViewAdapter(new ArrayWheelAdapter<String>(this, areas));
		mViewDistrict.setCurrentItem(0);
	}

	private void updateCities() {
		int pCurrent = mViewProvince.getCurrentItem();
		mCurrentProviceName = mProvinceDatas[pCurrent];
		String[] cities = mCitisDatasMap.get(mCurrentProviceName);
		if (cities == null) {
			cities = new String[] { "" };
		}
		mViewCity.setViewAdapter(new ArrayWheelAdapter<String>(this, cities));
		mViewCity.setCurrentItem(0);
		updateAreas();
	}

	private void showSelectedResult() {
		city_txt.setText(mCurrentProviceName + "-" + mCurrentCityName);
		// Toast.makeText(
		// Activity_PUTINFO.this,
		// "当前选择的城市为:" + mCurrentProviceName + "," + mCurrentCityName
		// + "," + mCurrentDistrictName + "," + mCurrentZipCode,
		// Toast.LENGTH_SHORT).show();
	}
}
