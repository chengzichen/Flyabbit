package com.dhc.filyabbit;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bm.library.PhotoView;
import com.dhc.lib.imageload.ImageLoaderManager;


/**
 * Created by liuheng on 2015/6/21.
 */
public class ImgActivity extends Activity {


    PhotoView mPhotoView;
    String url ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img);
        url= getIntent().getStringExtra("url");
        mPhotoView = (PhotoView) findViewById(R.id.img1);
        mPhotoView.enable();

        mPhotoView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(ImgActivity.this, "长按了", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

//        使用ImageLoader
//        ImaggeLoaderConfiguration configuration = ImageLoaderConfiguration
//                .createDefault(this);
//        ImageLoader.getInstance().init(configuration);
//        ImageLoader.etInstance().displayImage(url, (ImageView) findViewById(R.id.img1));

        ImageLoaderManager.getInstance().showImage(ImageLoaderManager.getDefaultOptions
                (mPhotoView,url));

//        使用Glide加载的gif图片同样支持缩放功能
//        Glide.with(this)
//                .load(gif)
//                .crossFade()
//                .placeholder(R.mipmap.bbb)
//                .into(((PhotoView) findViewById(R.id.img1)));

//        ImageLoaderManager.getInstance().
    }
}
