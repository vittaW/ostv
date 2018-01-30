package com.vittaw.mvplibrary.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.umeng.analytics.MobclickAgent;
import com.vittaw.mvplibrary.dialog.LoadingDialogView;
import com.vittaw.mvplibrary.utils.TypeUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;


public abstract class BaseFragment<P extends BasePresenter> extends SupportFragment implements BaseView {

    //类名
    private static String CLASS_NAME;

    public P mPresenter;

    protected View layout;
    Unbinder unbinder;

    protected LoadingDialogView dialog;
    protected Context mContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //通过反射获取类名
        CLASS_NAME = this.getClass().getSimpleName();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (layout == null) {
            layout = inflater.inflate(getLayoutId(), container, false);
        }
        //View 注入
        unbinder = ButterKnife.bind(this, layout);
        mContext = getContext();
        //初始化presenter
        mPresenter = TypeUtil.getObject(this, 0);

        //说明类上有泛型 - 有网络请求
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }

        //初始化View
        initView();
        return layout;
    }

    protected abstract int getLayoutId();

    protected abstract void initView();

    //intnet 跳转
    protected void intent2Activity(Class<? extends AppCompatActivity> clazz) {
        Intent intent = new Intent(getContext(), clazz);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(CLASS_NAME); //统计页面，"MainScreen"为页面名称，可自定义
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(CLASS_NAME);
    }

    /**
     * uBinder : ButterKnife 绑定的生命周期 onCreateView - onDestroyView
     * Fragment的生命周期不同于Activity,当Butterknife在onCreateView上进行绑定时，需要再onDestroyView上进行解绑,Butterknife.bind()方法提供了一个Unbinder 返回值，在onDestroyView上调用相关的unbinder方法即可
     * <p>
     * Presenter 的生命周期 onCreateView - onDestroyView
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        if (mPresenter != null) {//== null 类上没泛型
            mPresenter.detachView();//管理Presenter的生命周期
        }
        dismissLoadingDialog();
    }

    /**
     * loading dialog 是否可以取消
     */
    public void showLoadingDialog(Activity activity, boolean cancelable) {
        if (dialog != null && dialog.isShowing()) {
            return;
        }
        dialog = new LoadingDialogView(activity, cancelable);
        dialog.show();
    }

    private boolean shouldShowLoading = false;
    /**
     * loading dialog 是否可以取消
     */
    public void showLoadingDialog(Activity activity) {
        shouldShowLoading = true;
        if (isSupportVisible()){
            showLoadingDialog(activity, true);
        }
    }

    /**
     * dismiss dialog
     */
    public void dismissLoadingDialog() {
        shouldShowLoading = false;
        try {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        if (shouldShowLoading){
            showLoadingDialog(_mActivity);
        }
    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
        dismissLoadingDialog();
    }

    @Override
    public void onStartLoad() {

    }

    @Override
    public void onStopLoad() {

    }

    @Override
    public void onError(String errorInfo) {

    }
}
