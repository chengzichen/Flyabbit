package com.dhc.library.widget;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.flyco.dialog.utils.CornerUtils;
import com.flyco.dialog.widget.internal.BaseAlertDialog;
import com.dhc.library.utils.string.StringUtil;

/**
 * 创建者     邓浩宸
 * 创建时间   2016/12/9 14:17
 * 描述	  TODO
 */

public class MateriaEditDialog extends BaseAlertDialog<MateriaEditDialog> {


    private EditText mEditText;
    protected int mTextHintColor;

    public MateriaEditDialog(Context context) {
        super(context);


        mEditText = new EditText(context);
        /** default value*/
        mTitleTextColor = Color.parseColor("#DE000000");
        mTitleTextSize = 22f;
        mContentTextColor = Color.parseColor("#8a000000");
        mContentTextSize = 16f;
        mLeftBtnTextColor = Color.parseColor("#383838");
        mRightBtnTextColor = Color.parseColor("#468ED0");
        mMiddleBtnTextColor = Color.parseColor("#00796B");
        mTextHintColor = Color.parseColor("#33000000");
        /** default value*/


    }

    @Override
    public View onCreateView() {

        /** title */
        mTvTitle.setGravity(Gravity.CENTER_VERTICAL);
        mTvTitle.setPadding(dp2px(20), dp2px(20), dp2px(20), dp2px(0));
        mTvTitle.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        mLlContainer.addView(mTvTitle);


        /** content */
        mTvContent = null;
        mEditText.setPadding(dp2px(20), dp2px(20), dp2px(20), dp2px(20));
        LinearLayout.LayoutParams linearLayout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        linearLayout.setMargins(dp2px(20), 0, dp2px(20), 0);
        mEditText.setLayoutParams(linearLayout);
        mLlContainer.addView(mEditText);

        /**btns*/
        mLlBtns.setGravity(Gravity.RIGHT);
        mLlBtns.addView(mTvBtnLeft);
        mLlBtns.addView(mTvBtnMiddle);
        mLlBtns.addView(mTvBtnRight);

        mTvBtnLeft.setPadding(dp2px(15), dp2px(8), dp2px(15), dp2px(8));
        mTvBtnRight.setPadding(dp2px(15), dp2px(8), dp2px(15), dp2px(8));
        mTvBtnMiddle.setPadding(dp2px(15), dp2px(8), dp2px(15), dp2px(8));
        mLlBtns.setPadding(dp2px(20), dp2px(0), dp2px(10), dp2px(10));

        mLlContainer.addView(mLlBtns);


        return mLlContainer;
    }


    @Override
    public void setUiBeforShow() {

        /**set background color and corner radius */
        float radius = dp2px(mCornerRadius);
        mLlContainer.setBackgroundDrawable(CornerUtils.cornerDrawable(mBgColor, radius));
        mTvBtnLeft.setBackgroundDrawable(CornerUtils.btnSelector(radius, mBgColor, mBtnPressColor, -2));
        mTvBtnRight.setBackgroundDrawable(CornerUtils.btnSelector(radius, mBgColor, mBtnPressColor, -2));
        mTvBtnMiddle.setBackgroundDrawable(CornerUtils.btnSelector(radius, mBgColor, mBtnPressColor, -2));


        /** title */
        mTvTitle.setVisibility(mIsTitleShow ? View.VISIBLE : View.GONE);

        mTvTitle.setText(TextUtils.isEmpty(mTitle) ? "温馨提示" : mTitle);
        mTvTitle.setTextColor(mTitleTextColor);
        mTvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTitleTextSize);

        /** content */
        mEditText.setGravity(mContentGravity);
        mEditText.setHint(mContent);
        mEditText.setHintTextColor(mTextHintColor);
        mEditText.setTextColor(mContentTextColor);
        mEditText.addTextChangedListener(new EditTextWatcher(mEditText,60));

        /**btns*/
        mTvBtnLeft.setText(mBtnLeftText);
        mTvBtnRight.setText(mBtnRightText);
        mTvBtnMiddle.setText(mBtnMiddleText);

        mTvBtnLeft.setTextColor(mLeftBtnTextColor);
        mTvBtnRight.setTextColor(mRightBtnTextColor);
        mTvBtnMiddle.setTextColor(mMiddleBtnTextColor);

        mTvBtnLeft.setTextSize(TypedValue.COMPLEX_UNIT_SP, mLeftBtnTextSize);
        mTvBtnRight.setTextSize(TypedValue.COMPLEX_UNIT_SP, mRightBtnTextSize);
        mTvBtnMiddle.setTextSize(TypedValue.COMPLEX_UNIT_SP, mMiddleBtnTextSize);

        if (mBtnNum == 1) {
            mTvBtnLeft.setVisibility(View.GONE);
            mTvBtnRight.setVisibility(View.GONE);
        } else if (mBtnNum == 2) {
            mTvBtnMiddle.setVisibility(View.GONE);
        }

        mTvBtnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnBtnLeftClickL != null) {
                    mOnBtnLeftClickL.onBtnClick();
                } else {
                    dismiss();
                }
            }
        });

        mTvBtnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnBtnRightClickL != null) {
                    mOnBtnRightClickL.onBtnClick();
                } else {
                    dismiss();
                }
            }
        });

        mTvBtnMiddle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnBtnMiddleClickL != null) {
                    mOnBtnMiddleClickL.onBtnClick();
                } else {
                    dismiss();
                }
            }
        });
    }
    public String getEditMessage() {
        if (mEditText != null)
            return mEditText.getEditableText().toString();
        else return null;
    }

    public static class EditTextWatcher implements TextWatcher {



        private int maxLength;
        private EditText editText;


        public EditTextWatcher(EditText editText, int maxLength) {
            this.editText=editText;
            this.maxLength = maxLength;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (editText == null)
                return;
            int editStart = editText.getSelectionStart();
            int editEnd = editText.getSelectionEnd();
            editText.removeTextChangedListener(this);
            while (StringUtil.counterChars(s.toString()) > maxLength) {
                s.delete(editStart - 1, editEnd);
                editStart--;
                editEnd--;
            }
            editText.setSelection(editStart);
            editText.addTextChangedListener(this);
        }
    }
}
