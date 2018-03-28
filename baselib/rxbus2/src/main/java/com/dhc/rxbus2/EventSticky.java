package com.dhc.rxbus2;

/**
 * 创建者：邓浩宸
 * 时间 ：2017/3/15 13:45
 * 描述 ：Rxbus使用粘性的消息
 */
public class EventSticky<T> {

    public EventSticky(int code, T content) {
        this.code = code;
        this.content = content;
    }

    public int code;
    public T content;
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
