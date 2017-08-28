package com.dhc.library.widget;

import android.content.Context;

/**
 * 菜单显示条目
 */
public class PopupMenuItem {

    private int tag;

    private int icon;

    private String title;

    private Context context;


    public PopupMenuItem(int tag, int icon, String title) {
        this.tag = tag;
        this.icon = icon;
        this.title = title;
    }

    /**
     * 只有文字
     *
     * @param tag
     * @param title
     */
    public PopupMenuItem(int tag, String title) {
        this(tag, 0, title);
    }

    public PopupMenuItem(Context context, int tag, String title) {
        this(tag, title);
        this.context = context;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }


}
