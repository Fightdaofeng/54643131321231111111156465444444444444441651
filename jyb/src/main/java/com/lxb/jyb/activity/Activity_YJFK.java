package com.lxb.jyb.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.ab.util.AbDialogUtil;
import com.ab.util.AbFileUtil;
import com.ab.util.AbStrUtil;
import com.ab.util.AbToastUtil;
import com.lxb.jyb.R;
import com.lxb.jyb.activity.adapter.GridImageAdapter;
import com.lxb.jyb.activity2.AlbumActivity;
import com.lxb.jyb.activity2.ImageList;
import com.lxb.jyb.util.SetStatiColor;

import java.io.File;
import java.util.ArrayList;

public class Activity_YJFK extends FragmentActivity implements OnClickListener {
    private TextView top_title, top_txt;
    private int imgwidth, imgheigh;
    private GridView imggrid;
    private EditText editText;
    /**
     * 图片相关
     */
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

    private ArrayList<ImageView> views;
    private ArrayList<Bitmap> bitmaps;
    private ArrayList<String> dataList = new ArrayList<String>();
    private GridImageAdapter gridImageAdapter;

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
        setContentView(R.layout.activity_yjfk);
        initFind();
        dataList.add("camera_default");
        AlbumActivity.setmHandler(handler);
        init();
        initListener();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == AlbumActivity.SELECT_OK) {
                ArrayList<String> tDataList = (ArrayList<String>) msg.obj;
                if (tDataList != null) {
                    Log.e("handleMessage", "" + "running");

                    Log.e("tDataList.size()", "" + tDataList.size());
                    if (tDataList.size() < 8) {
                        tDataList.add("camera_default");
                    }
                    dataList.clear();
                    dataList.addAll(tDataList);

                    init();
                }
            }
        }
    };

    private void init() {
        gridImageAdapter = new GridImageAdapter(this, dataList);
        imggrid.setAdapter(gridImageAdapter);
        gridImageAdapter.notifyDataSetChanged();
    }

    private void initFind() {
        // TODO Auto-generated method stub
        initFolderDir();
        bitmaps = new ArrayList<>();
        views = new ArrayList<>();
        editText = (EditText) findViewById(R.id.edit);
        top_title = (TextView) findViewById(R.id.top_title);
        top_title.setText("意见反馈");
        top_txt = (TextView) findViewById(R.id.top_txt);
        top_txt.setVisibility(View.VISIBLE);
        top_txt.setText("提交");
        top_txt.setOnClickListener(this);
        findViewById(R.id.top_msg).setVisibility(View.GONE);
        findViewById(R.id.top_return).setOnClickListener(this);

        imggrid = (GridView) findViewById(R.id.img_grid);
//        addimg = (ImageView) findViewById(R.id.tianjia_img);
//        addimg.setOnClickListener(this);

//        imgwidth = addimg.getMeasuredWidth();
//        imgheigh = addimg.getMeasuredHeight();
    }

    private void initListener() {

        imggrid.setOnItemClickListener(new GridView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (position == dataList.size() - 1 && "camera_default".equals(dataList.get(position))) {
                    showPickDialog();

                } else {
                    dialog(position);
                }

            }

        });

    }

    private ArrayList<String> getIntentArrayList(ArrayList<String> dataList) {

        ArrayList<String> tDataList = new ArrayList<String>();

        for (String s : dataList) {
            if (!s.contains("default")) {
                tDataList.add(s);
            }
        }

        return tDataList;

    }

    protected void dialog(final int index) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Activity_YJFK.this);
        builder.setMessage("确认删除吗？");

        builder.setTitle("提示");

        builder.setPositiveButton("确认", new Dialog.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dataList.remove(index);
                ArrayList<String> tDataList = getIntentArrayList(dataList);
                tDataList.add("camera_default");
                dataList.clear();
                dataList.addAll(tDataList);
                init();
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("取消", new Dialog.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();
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
            case R.id.top_return:
                this.finish();
                break;


//
//            case R.id.tianjia_img:
//                showPickDialog();
//                break;
        }
    }

    private void initImage(Bitmap bitmap) {


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
                AbDialogUtil.removeDialog(Activity_YJFK.this);
                selectImageFromGallry();
                // 从相册中去获取

            }

        });

        camButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AbDialogUtil.removeDialog(Activity_YJFK.this);
                doPickPhotoAction();
            }

        });

        cancelButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AbDialogUtil.removeDialog(Activity_YJFK.this);
            }

        });
        AbDialogUtil.showDialog(avatarView, Gravity.BOTTOM);
    }

    protected void selectImageFromGallry() {
        try {
//            Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
//            intent.setType("image/*");
//            startActivityForResult(intent, PHOTO_PICKED_WITH_DATA);
            Intent intent = new Intent(Activity_YJFK.this, ImageList.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            Bundle bundle = new Bundle();
            String sti=editText.getText().toString();
            bundle.putString("value",sti);
            bundle.putStringArrayList("dataList",
                    getIntentArrayList(dataList));
            intent.putExtras(bundle);
            startActivityForResult(intent, 0);
        } catch (ActivityNotFoundException e) {
            AbToastUtil.showToast(Activity_YJFK.this, "没有找到照片");
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
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                Bundle bundle = mIntent.getExtras();
                String str=bundle.getString("value");
                editText.setText(str);
                editText.postInvalidate();
                ArrayList<String> tDataList = (ArrayList<String>) bundle.getSerializable("dataList");
                if (tDataList != null) {
                    if (tDataList.size() < 8) {
                        tDataList.add("camera_default");
                    }
                    dataList.clear();
                    dataList.addAll(tDataList);
                    gridImageAdapter.setDataList(dataList);
                    gridImageAdapter.notifyDataSetChanged();
                }
            }
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
//                head_img.setImageBitmap(bitmap);
                bitmaps.add(bitmap);
                initImage(bitmap);

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
        String[] projection = {MediaStore.Images.Media.DATA};
        @SuppressWarnings("deprecation")
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String path = cursor.getString(column_index);
        return path;
    }

    @Override
    protected void onResume() {
        super.onResume();
        gridImageAdapter.notifyDataSetChanged();
    }
}
