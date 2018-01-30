package com.vittaw.mvplibrary.base;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Base的好处 可以什么都不写,但一定要预留出来
 */

public class BasePresenter<V extends BaseView> implements PresenterLifeCycle<V> {

    protected V mView;
    //
    /** 管理presenter 的生命周期
     *  只要presenter 依赖的引用都销毁了，presenter 引用自己也会被回收，就不会内存泄漏了
     *  Disposable rxjava 管道中间的阀门，可以中断接收（不会中断发射） - 管理model 网络请求停止
     *  mView == null - 管理销毁view
     */
    private CompositeDisposable compositeDisposable;

    public void addSubscribe(Disposable subscription){
        if (compositeDisposable == null){
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(subscription);
    }

    public void clearSubscribe(){
        if (compositeDisposable != null){
            compositeDisposable.clear();
            compositeDisposable = null;
        }
    }

    @Override
    public void attachView(V view) {
        mView = view;
    }

    @Override
    public void detachView() {
        clearSubscribe();
        mView = null;
    }

}
