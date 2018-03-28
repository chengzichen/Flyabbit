package com.dhc.rxbus2;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 * 创建者：邓浩宸
 * 时间 ：2017/3/15 11:45
 * 描述 ：为RxBus使用的Subscriber, 主要提供next事件的try,catch
 */
public abstract class RxBusSubscriber<T> extends ResourceSubscriber<T> {

    @Override
    public void onNext(T t) {
        try {
            onEvent(t);
        } catch (Exception e) {
            e.printStackTrace();
            onError(e);
        }
    }

    @Override
    public void onComplete() {
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
    }

    protected abstract void onEvent(T t);
}
