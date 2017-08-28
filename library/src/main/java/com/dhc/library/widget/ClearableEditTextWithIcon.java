package com.dhc.library.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Toast;

import com.dhc.library.R;


/**
 * 带有图标和删除符号的可编辑输入框，用户可以自定义传入的显示图标
 * 
 * @author 
 * 
 */
public class ClearableEditTextWithIcon extends AppCompatEditText implements OnTouchListener, TextWatcher {

	// 删除符号
	Drawable deleteImage = getResources().getDrawable(R.mipmap.nim_icon_edit_delete);

	Drawable icon;
	//输入表情前的光标位置
	private int cursorPos;
	//输入表情前EditText中的文本
	private String inputAfterText;
	//是否重置了EditText的内容
	private boolean resetText;
	public ClearableEditTextWithIcon(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public ClearableEditTextWithIcon(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public ClearableEditTextWithIcon(Context context) {
		super(context);
		init();
	}

	private void init() {
		ClearableEditTextWithIcon.this.setOnTouchListener(this);
		ClearableEditTextWithIcon.this.addTextChangedListener(this);
		deleteImage.setBounds(0, 0, deleteImage.getIntrinsicWidth(), deleteImage.getIntrinsicHeight());
		manageClearButton();
		initEditText();
	}

	/**
	 * 传入显示的图标资源id
	 * 
	 * @param id
	 */
	public void setIconResource(int id) {
		icon = getResources().getDrawable(id);
		icon.setBounds(0, 0, icon.getIntrinsicWidth(), icon.getIntrinsicHeight());
		manageClearButton();
	}

    /**
     * 传入删除图标资源id
     * @param id
     */
    public void setDeleteImage(int id) {
        deleteImage = getResources().getDrawable(id);
        deleteImage.setBounds(0, 0, deleteImage.getIntrinsicWidth(), deleteImage.getIntrinsicHeight());
        manageClearButton();
    }

	void manageClearButton() {
		if (this.getText().toString().equals(""))
			removeClearButton();
		else
			addClearButton();
	}

	void removeClearButton() {
		this.setCompoundDrawables(this.icon, this.getCompoundDrawables()[1], null, this.getCompoundDrawables()[3]);
	}

	void addClearButton() {
		this.setCompoundDrawables(this.icon, this.getCompoundDrawables()[1], deleteImage,
				this.getCompoundDrawables()[3]);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		ClearableEditTextWithIcon et = ClearableEditTextWithIcon.this;

		if (et.getCompoundDrawables()[2] == null)
			return false;
		if (event.getAction() != MotionEvent.ACTION_UP)
			return false;
		if (event.getX() > et.getWidth() - et.getPaddingRight() - deleteImage.getIntrinsicWidth()) {
			et.setText("");
			ClearableEditTextWithIcon.this.removeClearButton();
		}
		return false;
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		ClearableEditTextWithIcon.this.manageClearButton();
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {

	}

	@Override
	public void afterTextChanged(Editable s) {

	}
	// 初始化edittext 控件
	private void initEditText() {
		addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int before, int count) {
				if (!resetText) {
					cursorPos = getSelectionEnd();
					// 这里用s.toString()而不直接用s是因为如果用s，
					// 那么，inputAfterText和s在内存中指向的是同一个地址，s改变了，
					// inputAfterText也就改变了，那么表情过滤就失败了
					inputAfterText= s.toString();
				}

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (!resetText) {
					if (count >= 2) {//表情符号的字符长度最小为2
						CharSequence input = s.subSequence(cursorPos, cursorPos + count);
						if (containsEmoji(input.toString())) {
							resetText = true;
							Toast.makeText(getContext(), "不支持输入Emoji表情符号", Toast.LENGTH_SHORT).show();
							//是表情符号就将文本还原为输入表情符号之前的内容
							setText(inputAfterText);
							CharSequence text = getText();
							if (text instanceof Spannable) {
								Spannable spanText = (Spannable) text;
								Selection.setSelection(spanText, text.length());
							}
						}
					}
				} else {
					resetText = false;
				}
			}

			@Override
			public void afterTextChanged(Editable editable) {

			}
		});
	}


	/**
	 * 检测是否有emoji表情
	 *
	 * @param source
	 * @return
	 */
	public static boolean containsEmoji(String source) {
		int len = source.length();
		for (int i = 0; i < len; i++) {
			char codePoint = source.charAt(i);
			if (!isEmojiCharacter(codePoint)) { //如果不能匹配,则该字符是Emoji表情
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断是否是Emoji
	 *
	 * @param codePoint 比较的单个字符
	 * @return
	 */
	private static boolean isEmojiCharacter(char codePoint) {
		return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) ||
				(codePoint == 0xD) || ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
				((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) || ((codePoint >= 0x10000)
				&& (codePoint <= 0x10FFFF));
	}
}
