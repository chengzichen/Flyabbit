package com.fanyu.library;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    private String TAG="1111";

    @Test
    public void useAppContext() throws Exception {
        Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(FlowableEmitter<String> e) throws Exception {
                Log.e(TAG, "===create: " + Thread.currentThread().getName());
                e.onNext("1");
            }
        }, BackpressureStrategy.ERROR)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .map(new Function<String, Integer>() {
                    @Override
                    public Integer apply(String s) throws Exception {
                        Log.e(TAG, "===String -> Integer: " + Thread.currentThread().getName());
                        return Integer.valueOf(s);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<Integer, Flowable<String>>() {
                    @Override
                    public Flowable<String> apply(final Integer integer) throws Exception {
                        Log.e(TAG, "===Integer->Observable: " + Thread.currentThread().getName());
                        return Flowable.create(new FlowableOnSubscribe<String>() {
                            @Override
                            public void subscribe(FlowableEmitter<String> e) throws Exception {
                                Log.e(TAG, "===Observable<String> call: " + Thread.currentThread().getName());
                                for (int i = 0; i < integer; i++) {
                                    e.onNext(i + "");
                                }
                                e.onComplete();
                            }
                        },BackpressureStrategy.ERROR);
                    }

                })
                .observeOn(Schedulers.io())
                .map(new Function<String, Long>() {
                    @Override
                    public Long apply(String s) throws Exception {
                        Log.e(TAG, "===String->Long: " + Thread.currentThread().getName());
                        return Long.parseLong(s);
                    }

                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Long>() {

                    @Override
                    public void onSubscribe(Subscription s) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        Log.e(TAG, "===onNext: " + Thread.currentThread().getName());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
