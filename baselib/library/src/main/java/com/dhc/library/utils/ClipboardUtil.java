package com.dhc.library.utils;

import android.content.Context;
import android.text.ClipboardManager;

public class ClipboardUtil {
	public static final void clipboardCopyText(Context context, CharSequence text) {
		ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
		if (cm != null) {
			cm.setText(text);
		}
	}

	public static final int clipboardTextLength(Context context) {
		ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
		CharSequence text = cm != null ? cm.getText() : null;
		return text != null ? text.length() : 0;
	}
}
