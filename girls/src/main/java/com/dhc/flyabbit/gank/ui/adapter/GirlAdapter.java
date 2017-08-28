package com.dhc.flyabbit.gank.ui.adapter;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dhc.flyabbit.gank.R;
import com.dhc.flyabbit.gank.modle.bean.GankItemBean;
import com.dhc.library.utils.sys.ScreenUtil;

import java.util.List;



public class GirlAdapter extends BaseQuickAdapter<GankItemBean, BaseViewHolder> {


    public GirlAdapter(@Nullable List<GankItemBean> data) {
        super(R.layout.item_girl, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, GankItemBean item) {
        //存在记录的高度时先Layout再异步加载图片
        if (item.getHeight() > 0) {
            ViewGroup.LayoutParams layoutParams = helper.getView(R.id.iv_girl).getLayoutParams();
            layoutParams.height = item.getHeight();
        }

        Glide.with(mContext).load(item.getUrl()).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.mipmap.default_loading)
                .into(new SimpleTarget<Bitmap>(ScreenUtil.getDisplayWidth() / 2, ScreenUtil.getDisplayWidth() / 2) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        if (helper.getAdapterPosition() != RecyclerView.NO_POSITION) {
                            if (item.getHeight() <= 0) {
                                int width = resource.getWidth();
                                int height = resource.getHeight();
                                int realHeight = (ScreenUtil.getDisplayWidth() / 2) * height / width;
                                item.setHeight(realHeight);
                                ViewGroup.LayoutParams lp = helper.getView(R.id.iv_girl).getLayoutParams();
                                lp.height = realHeight;
                            }
                            ((ImageView) helper.getView(R.id.iv_girl)).setImageBitmap(resource);
                        }
                    }
                });
    }


}
