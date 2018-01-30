package com.vittaw.mvplibrary.base;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.gyf.barlibrary.ImmersionBar;
import com.umeng.analytics.MobclickAgent;
import com.vittaw.mvplibrary.R;
import com.vittaw.mvplibrary.anno.Presenter;
import com.vittaw.mvplibrary.dialog.LoadingDialogView;
import com.vittaw.mvplibrary.utils.TypeUtil;
import com.vittaw.mvplibrary.widget.TopBar;

import butterknife.ButterKnife;
import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper;
import me.yokeyword.fragmentation.SupportActivity;


/**Base 里都干了啥？
 * ①引入Presenter model 对象（管理presenter 的生命周期）
 * ②ButterKnife 绑定View
 * ③设置状态栏
 * ④友盟
 * ⑤
 * @param <P>
 */
public abstract class BaseActivity<P extends BasePresenter> extends SupportActivity implements BGASwipeBackHelper.Delegate,BaseView {
    public BGASwipeBackHelper mSwipeBackHelper;
    public P mPresenter;

    protected LoadingDialogView dialog;

    protected Context mContext;

    private InputMethodManager imm;
    protected ImmersionBar mImmersionBar;

    static {
        //为了兼容vector矢量图 http://blog.csdn.net/qq_15545283/article/details/51472458
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStackManager.getInstance().addActivity(this);
        mContext = this;
        //在设置布局前做操作
        doBeforeLayout();
        //设置布局
        setContentView(getLayoutId());
        //View注入
        ButterKnife.bind(this);
        //初始化沉浸式
        if (isImmersionBarEnabled()) {
            setStatusBar();
        }
        //初始化Presenter
        mPresenter = TypeUtil.getObject(this, 0);//activity 类上的泛型
        if (mPresenter == null){//去类顶上的泛型上找
            Class<? extends BaseActivity> aClass = this.getClass();
            Presenter pAnno = aClass.getAnnotation(Presenter.class);
            if (pAnno != null){
                Class pClass = pAnno.value();
                if (!pClass.getName().endsWith("Presenter")){
                    throw new ClassCastException("Presenter 泛型 必须放Presenter 类");
                }
                try {
                    Object o = pClass.newInstance();
                    mPresenter = (P) o;
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        //初始化View 和 Model
        if (mPresenter != null) {
//            initPresenter();
            mPresenter.attachView(this);
        }
        //初始化View
        initView(savedInstanceState);
    }

    //intnet 跳转
    protected void intent2Activity(Class<? extends AppCompatActivity> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }


    /**
     * ActionBar 返回按钮监听
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 设置页面状态栏
     */
    protected void setStatusBar() {
        mImmersionBar = ImmersionBar.with(this)
//                .statusBarColor(R.color.status_bar_color_white)     //状态栏颜色，不写默认透明色
                .statusBarDarkFont(true, 1f) //原理：如果当前设备支持状态栏字体变色，会设置状态栏字体为黑色，如果当前设备不支持状态栏字体变色，会使当前状态栏加上透明度，否则不执行透明度
                .fitsSystemWindows(true)    //解决状态栏和布局重叠问题，任选其一，默认为false，当为true时一定要指定statusBarColor()，不然状态栏为透明色，还有一些重载方法
                .keyboardEnable(true);

        //指定了一个底部导航栏颜色
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            //竖屏
            mImmersionBar.navigationBarColor(R.color.nav_bar_color_white);
        } else {
            //横屏
            mImmersionBar.navigationBarColor(R.color.nav_bar_color_black);
        }

        mImmersionBar
                .init();  //必须调用方可沉浸式
    }

    /**
     * 预留 : 在设置布局前操作
     */
    protected void doBeforeLayout() {
        initSwipeBackFinish();
    }

    /**
     * 给activity设置布局
     *
     * @return resId
     */
    public abstract int getLayoutId();

    /**
     * mPresenter.setVM() 初始化presenter中持有的view 和 model
     * <p>
     * 在presenter里需要持有view接口,所以要init,将view传到presenter里,不封装的时候,activity实现接口,传this,
     * 现在抽象出来,留给子Activity去实现
     * <p>
     * <p>
     * view → 因为要在activity实现view接口后,传this,所以封装,留给子类去实现
     * model → new 出来的,通过拿到class上的泛型,反射创建一个对象出来
     */
//    public abstract void initPresenter();

    /**
     * 执行显示UI的逻辑
     * @param savedInstanceState
     */
    public abstract void initView(@Nullable Bundle savedInstanceState);

    /**
     * 初始化滑动返回。在 super.onCreateView(savedInstanceState) 之前调用该方法
     */
    private void initSwipeBackFinish() {
        mSwipeBackHelper = new BGASwipeBackHelper(this, this);

        // 「必须在 Application 的 onCreate 方法中执行 BGASwipeBackHelper.init(this) 来初始化滑动返回」
        // 下面几项可以不配置，这里只是为了讲述接口用法。

        // 设置滑动返回是否可用。默认值为 true
        mSwipeBackHelper.setSwipeBackEnable(true);
        // 设置是否仅仅跟踪左侧边缘的滑动返回。默认值为 true
        mSwipeBackHelper.setIsOnlyTrackingLeftEdge(false);
        // 设置是否是微信滑动返回样式。默认值为 true
        mSwipeBackHelper.setIsWeChatStyle(false);
        // 设置阴影资源 id。默认值为 R.drawable.bga_sbl_shadow
        mSwipeBackHelper.setShadowResId(R.drawable.bga_sbl_shadow);
        // 设置是否显示滑动返回的阴影效果。默认值为 true
        mSwipeBackHelper.setIsNeedShowShadow(true);
        // 设置阴影区域的透明度是否根据滑动的距离渐变。默认值为 true
        mSwipeBackHelper.setIsShadowAlphaGradient(true);
        // 设置触发释放后自动滑动返回的阈值，默认值为 0.3f
        mSwipeBackHelper.setSwipeBackThreshold(0.3f);
        // 设置底部导航条是否悬浮在内容上，默认值为 false
        mSwipeBackHelper.setIsNavigationBarOverlap(false);
    }

    /**
     * 是否支持滑动返回。这里在父类中默认返回 true 来支持滑动返回，如果某个界面不想支持滑动返回则重写该方法返回 false 即可
     *
     * @return
     */
    @Override
    public boolean isSupportSwipeBack() {
        return true;
    }

    /**
     * 正在滑动返回
     *
     * @param slideOffset 从 0 到 1
     */
    @Override
    public void onSwipeBackLayoutSlide(float slideOffset) {
    }

    /**
     * 没达到滑动返回的阈值，取消滑动返回动作，回到默认状态
     */
    @Override
    public void onSwipeBackLayoutCancel() {
    }

    /**
     * 滑动返回执行完毕，销毁当前 Activity
     */
    @Override
    public void onSwipeBackLayoutExecuted() {
        mSwipeBackHelper.swipeBackward();
    }

    @Override
    public void onBackPressedSupport() {
        // 正在滑动返回的时候取消返回按钮事件
        if (mSwipeBackHelper.isSliding()) {
            return;
        }
        mSwipeBackHelper.backward();
    }


    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    /**
     * loading dialog 是否可以取消
     */
    protected void showLoadingDialog(boolean cancelable) {
        if (dialog != null && dialog.isShowing()) {
            return;
        }
        dialog = new LoadingDialogView(this, cancelable);
        dialog.show();
    }

    /**
     * loading dialog 是否可以取消
     */
    public void showLoadingDialog() {
        showLoadingDialog(true);
    }

    /**
     * dismiss dialog
     */
    public void dismissLoadingDialog() {
        try {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 是否可以使用沉浸式
     * Is immersion bar enabled boolean.
     * @return the boolean
     */
    protected boolean isImmersionBarEnabled() {
        return true;
    }

    /**
     * 管理Presenter的生命周期 attachView/detachView
     */
    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        dismissLoadingDialog();
        if (mImmersionBar != null) {
            mImmersionBar.destroy();
        }
        ActivityStackManager.getInstance().removeActivity(this);
        super.onDestroy();
    }

    @Override
    public void finish() {
        super.finish();
        hideSoftKeyBoard();
    }

    public void hideSoftKeyBoard() {
        View localView = getCurrentFocus();
        if (this.imm == null) {
            this.imm = ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE));
        }
        if ((localView != null) && (this.imm != null)) {
            this.imm.hideSoftInputFromWindow(localView.getWindowToken(), 2);
        }
    }

    @Override
    public void onStartLoad() {

    }

    @Override
    public void onStopLoad() {

    }

    @Override
    public void onError(String errorInfo) {
        Toast.makeText(this, errorInfo, Toast.LENGTH_SHORT).show();
    }

    protected void setTopbar(int topbarId, String title, String rightTitle, TopBar.OnRightTextClickListener onRightTextClickListener){
        TopBar topBar = (TopBar) findViewById(topbarId);
        if (topBar == null)return;
        topBar.setTitle(title)
                .setBackVisible(this)
                .setRightTextVisibile()
                .setRightTextViewTitle(rightTitle)
                .setOnRightTextClickListener(onRightTextClickListener);
    }

    /**
     * 默认的topbar，只有一个返回按钮;topbar id 必须为<topbar>
     */
    protected void setTopbar(){
        TopBar topBar = (TopBar) findViewById(R.id.topbar);
        if (topBar == null)return;
        topBar.setBackVisible(this);
    }
}
