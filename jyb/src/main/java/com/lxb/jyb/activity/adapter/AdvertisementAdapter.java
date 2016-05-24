package com.lxb.jyb.activity.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.Toast;

import com.lxb.jyb.R;
import com.lxb.jyb.tool.ImageDownLoader;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AdvertisementAdapter extends PagerAdapter {
    private Context context;
    private List<View> views;
    ArrayList<String> advertiseArray;
    private ImageDownLoader loader;
    private Bitmap bitmap;
    private String imgurl;
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
        imgurl=head_img;
        ImageView ivAdvertise = (ImageView) view.findViewById(R.id.ivAdvertise);
        setImageView(ivAdvertise, head_img);

        ivAdvertise.setTag(position);
        ivAdvertise.setOnTouchListener(new OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Toast.makeText(context, "按下了" + v.getTag(), Toast.LENGTH_LONG).show();
                        break;

                    default:
                        break;
                }
                return false;
            }
        });
        return view;
    }

    class GetImg extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            bitmap = loader.getHttpBitmap(imgurl);

            return null;
        }
    }

    private void setImageView(final ImageView imageView, final String str) {
        //把网络访问的代码放在这里
        Log.i("图片地址:", str);
        imageView.setImageResource(R.drawable.empty_photo);
//        try {
//            URL url = new URL(str); //path图片的网络地址
//            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//            if(httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
//                Bitmap bitmap  = BitmapFactory.decodeStream(httpURLConnection.getInputStream());
//                imageView.setImageBitmap(bitmap);//加载到ImageView上
//            }else{
//                imageView.setImageResource(R.drawable.empty_photo);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


    }

    private void stmg(ImageView img) {
        img.setImageBitmap(bitmap);
    }
}
