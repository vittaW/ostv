package com.vitta.quickmvp;

/**
 * -------------------------------------
 * 作者：王文婷@<WVitta@126.com>
 * -------------------------------------
 * 时间：2018/1/30 17:22
 * -------------------------------------
 * 描述：所有控制器的基类,控制处理页面逻辑
 * -------------------------------------
 * 备注：
 * -------------------------------------
 */
public class BasePresenter implements PresenterLifeCycle {

    protected BaseView mView;

    /**
     * 控制和activity生命周期相同，在onCreate时调用。初始化mView桥
     * @param view
     */
    @Override
    public void onAttach(BaseView view) {
        this.mView = view;
    }

    /**
     * 在页面onDestory时调用，销毁自己（销毁与自己相关的对象，去掉所有的网络请求）
     */
    @Override
    public void onDetach() {
        this.mView = null;
    }
}
