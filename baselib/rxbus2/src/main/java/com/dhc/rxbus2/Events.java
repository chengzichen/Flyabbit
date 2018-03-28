package com.dhc.rxbus2;

/**
 * 创建者     邓浩宸
 * 创建时间   2017/3/14 15:16
 * 描述	      ${TODO}
 */
public class Events <T>{

    public int code;
    public T content;

    public Events(int code, T content) {
        this.code = code;
        this.content = content;
    }

    public <T> T getContent() {
        return (T) content;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setContent(T content) {
        this.content = content;
    }
}
