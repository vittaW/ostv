package com.vittaw.mvplibrary.base;

import java.lang.ref.WeakReference;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 作者：王文婷 邮箱：WVitta@126.com
 * 创建时间：2018/1/5 12:01
 * 描述：PresenterLifeCycle
 */

public interface PresenterLifeCycle<V extends BaseView> {

    void attachView(V view);
    void detachView();

}
