package com.dhc.flyabbit.home.ui.adapter;

import androidx.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dhc.flyabbit.home.R;
import com.dhc.flyabbit.home.modle.bean.GankItemBean;
import com.dhc.flyabbit.home.ui.HomeFragment;

import java.util.List;


public class GankTechAdapter extends BaseQuickAdapter<GankItemBean, BaseViewHolder> {
    private  String mTag;

    public GankTechAdapter(@Nullable List<GankItemBean> data,String tag) {
        super(R.layout.item_tech, data);
        mTag=tag;
    }


    @Override
    protected void convert(BaseViewHolder helper, GankItemBean item) {
        if(mTag.equals(HomeFragment.TECH_ANDROID)) {
            ( (ImageView) helper.getView(R.id.iv_tech_icon)).setImageResource(R.mipmap.ic_android);
        } else if(mTag.equals(HomeFragment.TECH_IOS)) {
             ( (ImageView) helper.getView(R.id.iv_tech_icon)).setImageResource(R.mipmap.ic_ios);
        } else if(mTag.equals(HomeFragment.TECH_WEB)) {
            ( (ImageView) helper.getView(R.id.iv_tech_icon)).setImageResource(R.mipmap.ic_web);
        }
        ( (TextView) helper.getView(R.id.tv_tech_title)).setText(item.getDesc());
        ( (TextView) helper.getView(R.id.tv_tech_author)).setText(item.getWho());
        String date = item.getPublishedAt();
        int idx = date.indexOf(".");
        date = date.substring(0,idx).replace("T"," ");
        ( (TextView) helper.getView(R.id.tv_tech_time)).setText(date);
    }


}
