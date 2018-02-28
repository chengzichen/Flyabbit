package com.dhc.library.utils;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.Toolbar;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dhc.lib.uikit.R;
import com.dhc.library.data.bean.OptionsButton;
import com.dhc.library.utils.sys.ScreenUtil;

import java.util.List;

/**
 * 创建者     邓浩宸
 * 创建时间   2016/12/9 14:17
 * 描述	  TODO
 */

public class ToolbarUtil {

    ImageView rightImageView;

    private TextView mTitle;

    private TextView mTVNavigateString;

    private TextView mTVRightString;

    protected Activity mActivity;

    private Toolbar toolbar;


    public ToolbarUtil(Activity activity, Toolbar toolbar, boolean haveLine) {
        mActivity = activity;
        this.toolbar = toolbar;
        if (haveLine) {
            toolbar.setBackgroundResource(R.drawable.bg_toolbar_shape);
            toolbar.setPadding(0, 0, 0, ScreenUtil.dip2px(1f));
        }
    }

    public ImageView getRightImageView() {
        return rightImageView;
    }

    public TextView getTitle() {
        return mTitle;
    }

    public TextView getTVNavigateString() {
        return mTVNavigateString;
    }

    public TextView getTVRightString() {
        return mTVRightString;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public void addOptionButton(List<OptionsButton> mOptionsButtons) {
        if (toolbar == null)
            throw new RuntimeException(
                    " toolbar must  be in your layout and find it ");
        Toolbar.LayoutParams layoutParams;
        for (OptionsButton button : mOptionsButtons) {
            if (button.iconId != 0 && button.rightString == null) {
                layoutParams = new Toolbar.LayoutParams(Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.MATCH_PARENT, Gravity.RIGHT | Gravity.CENTER);
                rightImageView = new ImageView(mActivity);
                rightImageView.setImageResource(button.iconId);
                TypedValue typedValue = new TypedValue();
                mActivity.getTheme().resolveAttribute(R.attr.selectableItemBackground, typedValue, true);
                rightImageView.setBackgroundResource(typedValue.resourceId);
                rightImageView.setPadding(ScreenUtil.dip2px(16), 0, ScreenUtil.dip2px(16), 0);
                rightImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (button == null)
                            return;
                        button.onClick(mActivity, v, "");
                    }
                });
                toolbar.addView(rightImageView, layoutParams);
            } else if (!TextUtils.isEmpty(button.rightString) || !TextUtils.isEmpty(button.navigateString)) {
                TextView textView = new TextView(mActivity);
                textView.setTextSize(button.navigateTextSize == 0 ? 16 : button.navigateTextSize);
                if (!TextUtils.isEmpty(button.navigateString)) {
                    layoutParams = new Toolbar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, Gravity.LEFT | Gravity.CENTER);
                    textView.setText(button.navigateString);
                    if (button.needNavigateIocn) {
                        Drawable drawable = mActivity.getResources().getDrawable(R.mipmap.back);
                        /// 这一步必须要做,否则不会显示.
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight() - 2);
                        textView.setCompoundDrawablePadding(ScreenUtil.dip2px(5));
                        textView.setCompoundDrawables(drawable, null, null, null);
                    }
                    if (button.navigateColor != 0) {
                        textView.setTextColor(button.navigateColor);
                    }
                    TypedValue typedValue = new TypedValue();
                    mActivity.getTheme().resolveAttribute(R.attr.selectableItemBackground, typedValue, true);
                    textView.setBackgroundResource(typedValue.resourceId);
                    mTVNavigateString = textView;
                } else {
                    layoutParams = new Toolbar.LayoutParams(Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.MATCH_PARENT, Gravity.RIGHT | Gravity.CENTER);
                    textView.setText(button.rightString);
                    TypedValue typedValue = new TypedValue();
                    mActivity.getTheme().resolveAttribute(R.attr.selectableItemBackground, typedValue, true);
                    textView.setBackgroundResource(typedValue.resourceId);
                    mTVRightString = textView;
                }
                if (button.rightColor != 0) {
                    textView.setTextColor(button.rightColor);
                }
                textView.setGravity(Gravity.CENTER);
                textView.setPadding(ScreenUtil.dip2px(10), 0, ScreenUtil.dip2px(10), 0);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        button.onClick(mActivity, v, "");
                    }
                });
                toolbar.addView(textView, layoutParams);
            }
        }
    }


    public void setLine(boolean line) {


        Toolbar.LayoutParams params = new Toolbar.LayoutParams(Toolbar.LayoutParams.MATCH_PARENT, ScreenUtil.dip2px(1), Gravity.BOTTOM);
        View view = new View(mActivity);
        //        view.setBackgroundColor(Color.parseColor());
        view.setLayoutParams(params);
        toolbar.addView(view);
    }

    /**
     * 标题栏设置标题,这里的title是自己定义的title,如果使用toolbar的titlte使用
     *
     * @param title
     */
    public void setTitle(CharSequence title) {
        if (toolbar == null)
            throw new RuntimeException(
                    " toolbar must  be in your layout and find it ");
        if (mTitle == null) {
            Toolbar.LayoutParams params = new Toolbar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
            mTitle = new TextView(mActivity);
            mTitle.setTextSize(15);
            mTitle.setText(title);
            mTitle.setTextColor(Color.WHITE);
            TextPaint tp = mTitle.getPaint();
            tp.setFakeBoldText(true);
            toolbar.addView(mTitle, params);
        } else {
            mTitle.setText(title);
        }
    }
}
