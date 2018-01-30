package com.vitta.quickmvp;

/**
 * -------------------------------------
 * 作者：王文婷@<WVitta@126.com>
 * -------------------------------------
 * 时间：2018/1/30 17:22
 * -------------------------------------
 * 描述：控制器的生命周期接口，控制和页面周期相同
 * -------------------------------------
 * 备注：
 * -------------------------------------
 */
public interface PresenterLifeCycle {

    void onAttach(BaseView view);

    void onDetach();

}
