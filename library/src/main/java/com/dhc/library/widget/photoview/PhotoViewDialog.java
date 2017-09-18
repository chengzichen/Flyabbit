package com.dhc.library.widget.photoview;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.dhc.library.R;
import com.dhc.library.utils.ImageSaveUtils;
import com.dhc.library.utils.sys.ScreenUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * @author 邓浩宸
 * @time 2017/5/25 14:16
 * @desc 嵌套了viewpager的图片浏览
 */
public class PhotoViewDialog extends Dialog {
    private View mView ;
    private Context mContext;
    private PhotoViewDialogViewPager mViewPager;
    private TextView mIndexText;
    private List<String> mImgUrls;
    private List<String> mTitles;
    private List<View> mViews;
    private ShowImagesAdapter mAdapter;
    private int  mPosition;
    private ImageView iv_save_Image,iv_back;
    String url;
    public PhotoViewDialog(@NonNull Context context, List<String> imgUrls, int position) {
        super(context, R.style.transparentBgDialog);
        this.mContext = context;
        this.mImgUrls = imgUrls;
        this.mPosition=position;
        initView();
        initData();
         url=mImgUrls.get(position);
        Log.e("ImgUrls",url);
    }

    private void initView() {
        mView = View.inflate(mContext, R.layout.layout_dialog_images_brower, null);
        mViewPager = (PhotoViewDialogViewPager) mView.findViewById(R.id.vp_images);
        mIndexText = (TextView) mView.findViewById(R.id.tv_image_index);
        iv_save_Image=(ImageView)mView.findViewById(R.id.iv_save_Image);
        iv_back=(ImageView)mView.findViewById(R.id.iv_back);
        mTitles = new ArrayList<>();
        mViews = new ArrayList<>();
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhotoViewDialog.this.cancel();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mView);
        Window window = getWindow();
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = 0;
        wl.height = ScreenUtil.getDisplayHeight();
        wl.width = ScreenUtil.getDisplayWidth();
        wl.gravity = Gravity.CENTER;
        window.setAttributes(wl);


    }

    private void initData() {
        PhotoViewAttacher.OnPhotoTapListener listener = new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                dismiss();
            }
        };
        for (int i = 0; i < mImgUrls.size(); i++) {
            final PhotoView photoView = new uk.co.senab.photoview.PhotoView(mContext);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            photoView.setLayoutParams(layoutParams);
            photoView.setOnPhotoTapListener(listener);
            Glide.with(mContext)
                    .load(mImgUrls.get(i))
                    .error(R.mipmap.result_failed)
                    .into(new SimpleTarget<GlideDrawable>() {
                        @Override
                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                            photoView.setImageDrawable(resource);
                        }
                    });
            mViews.add(photoView);
            mTitles.add(i + "");

            iv_save_Image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Observable.create(new ObservableOnSubscribe<Bitmap>() {
                        @Override
                        public void subscribe(ObservableEmitter<Bitmap> e) throws Exception {
                             Bitmap  bitmap = Glide.with(mContext)
                                    .load(url)
                                    .asBitmap()
                                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                                    .get();
                            e.onNext(bitmap);
                        }
                    }).subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<Bitmap>() {
                                @Override
                                public void accept(Bitmap bitmap) throws Exception {
                                    ImageSaveUtils.saveImageToGallery(mContext,bitmap);//保存照片
                                }
                            });
                }
            });


        }

        mAdapter = new ShowImagesAdapter(mViews, mTitles);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(mPosition);
        mIndexText.setText(mPosition+1 + "/" + mImgUrls.size());

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                url=null;
                mIndexText.setText(position + 1 + "/" + mImgUrls.size());
                url=mImgUrls.get(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


}
