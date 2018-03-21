package com.fanyu.library;

import android.util.Log;

import org.junit.Test;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    private String TAG="1111";

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);


    }

    @Test
    public void randomTest() {
        Observable.create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> e) throws Exception {
                        Log.e(TAG, "===create: " + Thread.currentThread().getName());
                        e.onNext("1");
                    }

                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .map(new Function<String, Integer>() {
                    @Override
                    public Integer apply(String s) throws Exception {
                            Log.e(TAG, "===String -> Integer: " + Thread.currentThread().getName());
                            return Integer.valueOf(s);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<Integer, Observable<String>>() {
                    @Override
                    public Observable<String> apply(final Integer integer) throws Exception {
                        Log.e(TAG, "===Integer->Observable: " + Thread.currentThread().getName());
                        return Observable.create(new ObservableOnSubscribe<String>() {
                            @Override
                            public void subscribe(ObservableEmitter<String> e) throws Exception {
                                Log.e(TAG, "===Observable<String> call: " + Thread.currentThread().getName());
                                for (int i = 0; i < integer; i++) {
                                    e.onNext(i + "");
                                }
                                e.onComplete();
                            }
                        });
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
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

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