package com.dhc.library.data.net;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import retrofit2.HttpException;

/**
 * 创建者：邓浩宸
 * 时间 ：2017/5/24 10:27
 * 描述 ：重连机制
 */
public class RetryWhenHandler implements Function<Flowable<? extends Throwable>, Flowable<?>> {

    private int mCount = 3;
    private long mDelay = 3; //s

    private int counter = 0;

    public RetryWhenHandler() {
    }

    public RetryWhenHandler(int count) {
        this.mCount = count;
    }

    public RetryWhenHandler(int count, long delay) {
        this(count);
        this.mCount = count;
        this.mDelay = delay;
    }


    @Override
    public Flowable<?> apply(Flowable<? extends Throwable> flowable) throws Exception {
        return flowable
                .flatMap(new Function<Throwable, Flowable<?>>() {
                    @Override
                    public Flowable<?> apply(Throwable throwable) throws Exception {
                        if (counter < mCount && (throwable instanceof UnknownHostException
                                || throwable instanceof SocketException
                                || throwable instanceof HttpException
                                || throwable instanceof SocketTimeoutException
                        )) {
                            counter++;
                            return Flowable.timer(mDelay, TimeUnit.SECONDS);
                        } else if ((counter < mCount && throwable instanceof NullPointerException
                                && throwable.getMessage() != null && "token_is_need_refresh".equals(throwable.getMessage()))) {
                            counter++;
                            return Flowable.timer(0, TimeUnit.SECONDS);
                        }
                        return Flowable.error(throwable);
                    }

                });
    }
}
