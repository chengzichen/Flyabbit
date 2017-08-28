package com.dhc.library.widget;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dhc.library.R;
import com.dhc.library.utils.sys.ScreenUtil;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * 创建者：yqlee
 * 时间 ：2017-04-20 上午 9:37
 * 描述 ：轮播图banner
 */
public class BannerRollViewPager extends ViewPager {
    private Context mContext;

    public BannerRollViewPager(Context context) {
        this(context, null);
    }

    public BannerRollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        views = new ArrayList<>();
        handler = new MyHandler((Activity) context);
    }

    private List<ImageView> views;
    private boolean needGono = true;
    private int currentPager;
    private List<String> datas;
    private RollViewPagerAdapter adapter;
    private int prviousIndex;
    private ViewGroup dotContainer;
    private MyHandler handler;
    private boolean isRolling;
//    private DisplayImageOptions defaultOptions;

    public class MyHandler extends Handler {
        private WeakReference<Activity> mActivity;

        public MyHandler(Activity activity) {
            mActivity = new WeakReference<Activity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Activity activity = mActivity.get();
            if (activity != null) {
                setCurrentItem(currentPager);
                startRoll();
            }
        }
    }

    int x;
    int y;
    long pressTime;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x = (int) ev.getX();
                y = (int) ev.getY();
                pressTime = System.currentTimeMillis();
                handler.removeCallbacksAndMessages(null);
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                if (Math.abs(x - (int) ev.getX()) < 10 && Math.abs(y - (int) ev.getY()) < 10 && System.currentTimeMillis() - pressTime < 1000) {
                    if (callBack != null) {
                        callBack.onPress(getRealPosition(currentPager));
                    }
                }
                startRoll();
                break;
        }
        return super.onTouchEvent(ev);
    }


    public void setData(List<String> datas, ViewGroup dotContainer) {
        if (datas == null || datas.isEmpty()) return;
        this.datas = datas;
        initViews(datas.size());
        if (adapter == null) {
            adapter = new RollViewPagerAdapter();
            this.setAdapter(adapter);
            this.dotContainer = dotContainer;
            initDot(datas.size());
            this.setOnPageChangeListener(new RollPageChangeListener());
            setPageToMiddle();
        } else {
            handler.removeCallbacksAndMessages(null);
            initDot(datas.size());
            setPageToMiddle();
            adapter.notifyDataSetChanged();
        }
        startRoll();
    }

    private void initViews(int size) {
        views.clear();
        int j = (size == 1 ? 1 : size + 2);
        for (int i = 0; i < j; i++) {
            views.add(new ImageView(getContext()));
        }
    }


    private void setPageToMiddle() {

        currentPager = 100000 / 2;
        currentPager = currentPager - ((50000) % datas.size());
        setCurrentItem(currentPager, false); // 设置当前选中的页面
    }

    private void startRoll() {
        if (needRoll() && needGono) {
            isRolling = true;
            handler.postDelayed(new Task(), 3000);
        } else {
            needGono = false;
            isRolling = false;
        }
    }

    private class Task implements Runnable {
        @Override
        public void run() {
            currentPager = (currentPager + 1);
            handler.obtainMessage().sendToTarget();
        }

    }


    private boolean needRoll() {
        return datas != null && datas.size() > 1 && adapter != null;
    }


    private class RollPageChangeListener implements OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            currentPager = position;
            position = getRealPosition(position);
            try {
                //圆点
                dotContainer.getChildAt(prviousIndex).setBackgroundResource(R.mipmap.ic_dot_normal);
                dotContainer.getChildAt(position).setBackgroundResource(R.mipmap.ic_dot_selected);
            } catch (Exception e) {

            }
            prviousIndex = position;

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }


    private int getRealPosition(int postion) {
        return postion % datas.size();
    }

    /**
     * 自动滑动ViewPager
     */
    public class RollViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return datas == null ? 0 : datas.size() == 1 ? datas.size() : 100000;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            final ImageView imageView = views.get(position % views.size());
            if (imageView.getParent() != null) {
                ((ViewGroup) imageView.getParent()).removeView(imageView);
            }


//            ILFactory.getLoader().loadNet(imageView,datas.get(getRealPosition(position)));

           /* ImageLoader.getInstance().loadImage(datas.get(getRealPosition(position)), defaultOptions, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String s, View view) {

                }

                @Override
                public void onLoadingFailed(String s, View view, FailReason failReason) {

                }

                @Override
                public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                    imageView.setBackgroundDrawable(new BitmapDrawable(bitmap));
                    if (imageLoadedCallBack != null) {
                        imageLoadedCallBack.onloaded();
                        setImageLoadedCallBack(null);
                    }
                }

                @Override
                public void onLoadingCancelled(String s, View view) {

                }
            });*/
            container.addView(imageView);
            return imageView;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    public void onRecycler() {
        needGono = false;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
    }

    public void stopRoll() {
        needGono = false;
        isRolling = false;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }

    public void reStartRoll() {
        if (isRolling) {
            return;
        }
        needGono = true;
        startRoll();
    }


    private void initDot(int size) {
        if (size <= 1 || dotContainer == null) {//只有一张图片不需要点点的
            return;
        }
        dotContainer.removeAllViews();
        for (int i = 0; i < size; i++) {

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(

                    ScreenUtil.dip2px(6), ScreenUtil.dip2px(6));
            // 初始化点
            View m = new View(mContext);
            // 设置点的左右的间距
            params.setMargins(5, 0, 5, 0);
            // 把点的宽高添加进来
            m.setLayoutParams(params);
            if (i == 0) {
                m.setBackgroundResource(R.mipmap.ic_dot_selected);
            } else {
                m.setBackgroundResource(R.mipmap.ic_dot_normal);
            }
            dotContainer.addView(m);
        }
    }


    private IOnImagePressCallBack callBack;
    private IOnImageLoadedCallBack imageLoadedCallBack;

    public void setCallBack(IOnImagePressCallBack callBack) {
        this.callBack = callBack;
    }

    public void setImageLoadedCallBack(IOnImageLoadedCallBack callBack) {
        this.imageLoadedCallBack = callBack;
    }


    public interface IOnImageLoadedCallBack {
        void onloaded();
    }

    public interface IOnImagePressCallBack {
        void onPress(int position);
    }

}