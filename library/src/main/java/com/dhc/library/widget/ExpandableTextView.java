package com.dhc.library.widget;

/**
 * @author YK
 * @time 2017/6/23 15:36
 * @desc TODO
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dhc.library.R;


public class ExpandableTextView extends LinearLayout {

    private Context mContext;
    private TextView contentTv;
    private int maxLine;
    private int textColor;
    private int textSize;


    private TextView expandTv;

    public static final int STATUS_EXPAND = 1;
    public static final int STATUS_RETRACT = 2;

    private int currentStatus = STATUS_RETRACT;

    public ExpandableTextView(Context context) {
        this(context, null);
    }

    public ExpandableTextView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public ExpandableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ExpandTextViewShow);
        maxLine = array.getInt(R.styleable.ExpandTextViewShow_expandMaxLine, 3);
        textColor = array.getColor(R.styleable.ExpandTextViewShow_expandTextColor, context.getResources().getColor(R.color.clr_e0e0e0));
        textSize = array.getDimensionPixelSize(R.styleable.ExpandTextViewShow_expandTextSize,
                context.getResources().getDimensionPixelSize(R.dimen.sp_14));//sp
        int padding = array.getDimensionPixelSize(R.styleable.ExpandTextViewShow_expandTextPadding,
                context.getResources().getDimensionPixelSize(R.dimen.dp_10));//10dp

        array.recycle();

        setOrientation(LinearLayout.VERTICAL);
        setGravity(Gravity.END);
        setPadding(padding, padding, padding, padding);
        initView();
    }

    private void initView() {
        contentTv = new TextView(mContext);
        contentTv.setLineSpacing(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5f,  getResources().getDisplayMetrics()), 1.0f);
        contentTv.setEllipsize(TextUtils.TruncateAt.END);
        contentTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        contentTv.setTextColor(textColor);
        LinearLayout.LayoutParams textParams = new LayoutParams(-1, -2);
        contentTv.setLayoutParams(textParams);

        expandTv = new TextView(mContext);
        LinearLayout.LayoutParams expandTvParams = new LayoutParams(-2, -2);
        expandTvParams.topMargin =  mContext.getResources().getDimensionPixelSize(R.dimen.dp_5);//5dp
        expandTv.setLayoutParams(expandTvParams);
        expandTv.setText(getResources().getString(R.string.to_expand_hint));
        expandTv.setTextColor(getResources().getColor(R.color.clr_ff6239));
        this.addView(contentTv);
        this.addView(expandTv);
        expandTv.setVisibility(View.GONE);
        expandTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentStatus == STATUS_RETRACT) {
                    currentStatus = STATUS_EXPAND;
                    contentTv.setMaxLines(Integer.MAX_VALUE);
                    expandTv.setText(getResources().getString(R.string.to_shrink_hint));
                } else {
                    currentStatus = STATUS_RETRACT;
                    contentTv.setMaxLines(maxLine);
                    expandTv.setText(getResources().getString(R.string.to_expand_hint));
                }
            }
        });

    }

    /**
     *
     * @param text  这个问题搞得我都快奔溃了  日你大爷
     *
     */
    public void setText(String text) {
        contentTv.setMaxLines(Integer.MAX_VALUE);
        contentTv.setText(text.trim());
        int widthMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.AT_MOST);
        int heightMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        contentTv.measure(widthMeasureSpec, heightMeasureSpec);
        contentTv.post(new Runnable() {
            @Override
            public void run() {
                if(contentTv.getLineCount()>maxLine){
                    expandTv.setVisibility(View.VISIBLE);
                    contentTv.setMaxLines(maxLine);
                }else{
                    expandTv.setVisibility(View.GONE);
                }
            }
        });
    }

    /**
     *
     * @param textView  textView
     * @param content  内容
     * @param width   宽度
     * @param maxLine 最大的行数
     * @return   高度和宽度的集合
     */
    public static int[] measureTextViewHeight(TextView textView, String content, int width, int maxLine){
        Log.i("Alex","宽度是"+width);
        TextPaint textPaint  = textView.getPaint();
        StaticLayout staticLayout = new StaticLayout(content, textPaint, width, Layout.Alignment.ALIGN_NORMAL, 1, 0, false);
        int[] result = new int[2];
        if(staticLayout.getLineCount()>maxLine) {//如果行数超出限制
            int lastIndex = staticLayout.getLineStart(maxLine) - 1;
            result[0] = lastIndex;
            result[1] = new StaticLayout(content.substring(0, lastIndex), textPaint, width, Layout.Alignment.ALIGN_NORMAL, 1, 0, false).getHeight();
            return result;
        }else {//如果行数没有超出限制
            result[0] = -1;
            result[1] = staticLayout.getHeight();
            return result;
        }
    }
}
