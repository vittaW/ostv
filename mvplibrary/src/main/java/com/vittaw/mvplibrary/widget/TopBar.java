package com.vittaw.mvplibrary.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vittaw.mvplibrary.R;
import com.vittaw.mvplibrary.R2;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 自定义的topbar
 * 以后修改颜色，样式背景都在这个里面
 */

/**
 * -------------------------------------
 * 作者：王文婷@<WVitta@126.com>
 * -------------------------------------
 * 时间：Add by 2017/10/25 17:41
 * -------------------------------------
 * 描述：导航栏
 * -------------------------------------
 * 备注：1.默认背景是fafafa
 *      2.修改背景色，需要设置background 和 设置侧滑返回的颜色
 * -------------------------------------
 */
public class TopBar extends RelativeLayout {

    private static final String TAG = "TopBar";

    @BindView(R2.id.back)
    ImageView back;
    @BindView(R2.id.title)
    TextView textView;
    @BindView(R2.id.image_view_right)
    ImageView imageViewRight;
    @BindView(R2.id.text_view_right)
    TextView textViewRight;
    @BindView(R2.id.close)
    ImageView mClose;
    @BindView(R2.id.divider)
    View divider;
    //默认的背景颜色 和 状态栏颜色
    private int default_bg_color;

    public TopBar(Context context) {
        this(context, null);
    }

    public TopBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TopBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TopBar);
        boolean isFitsSystemWindows = ta.getBoolean(R.styleable.TopBar_mFitsSystemWindows, false);
        ta.recycle();

        View topbar = LayoutInflater.from(context).inflate(R.layout.custom_top_bar, this, true);
        ButterKnife.bind(this);
        default_bg_color = Color.parseColor("#fefefe");
        //默认给一个白色的背景
        if (getBackground() == null) {
            setBackgroundColor(default_bg_color);
        }
    }

    @Override
    public void setBackground(Drawable background) {
        super.setBackground(background);
    }

    public TopBar setTitle(CharSequence title) {
        textView.setVisibility(VISIBLE);
        textView.setText(title);
        return this;
    }

    //设置状态栏侧滑返回的颜色 - 默认色
    public TopBar setColorForSwipBack(Activity activity) {
//        setColorForSwipBack(activity, default_bg_color);
        return this;
    }

    public TopBar setBackVisible(final Activity activity) {
        back.setVisibility(VISIBLE);
        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.back) {
                    if (onLeftCloseClickListener != null) {
                        onLeftCloseClickListener.OnLeftCloseClickListener(view);
                    } else {
                        activity.finish();
                    }
                }
            }
        });
        return this;
    }

    public TopBar setCloseVisible(final Activity activity) {
        mClose.setVisibility(VISIBLE);
        mClose.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.close) {
                    if (onLeftCloseClickListener != null) {
                        onLeftCloseClickListener.OnLeftCloseClickListener(view);
                    } else {
                        activity.finish();
                    }
                }
            }
        });
        return this;
    }


    /**
     * 右边ImageView控件显示
     *
     * @return
     */
    public TopBar setRightImageVisibile() {
        imageViewRight.setVisibility(VISIBLE);
        imageViewRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onRightImageClickListener != null) {
                    onRightImageClickListener.onRightImageClickListener(view);
                }
            }
        });
        return this;
    }

    public interface OnRightImageClickListener {
        void onRightImageClickListener(View view);
    }

    private OnRightImageClickListener onRightImageClickListener;

    public TopBar setOnRightImageClickListener(OnRightImageClickListener onRightImageClickListener) {
        this.onRightImageClickListener = onRightImageClickListener;
        return this;
    }

    /**
     * 右边ImageView图标更换
     *
     * @param resId
     * @return
     */
    public TopBar setRightImageSrc(@DrawableRes int resId) {
        imageViewRight.setVisibility(VISIBLE);
        imageViewRight.setImageResource(resId);
        imageViewRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onRightImageClickListener != null) {
                    onRightImageClickListener.onRightImageClickListener(view);
                }
            }
        });
        return this;
    }

    /**
     * 右边TextView控件显示
     *
     * @return
     */
    public TopBar setRightTextVisibile() {
        textViewRight.setVisibility(VISIBLE);
        textViewRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onRightTextClickListener != null) {
                    onRightTextClickListener.onRightTextClickListener(view);
                }
            }
        });
        return this;
    }

    public interface OnRightTextClickListener {
        void onRightTextClickListener(View view);
    }

    private OnRightTextClickListener onRightTextClickListener;

    public TopBar setOnRightTextClickListener(OnRightTextClickListener onRightTextClickListener) {
        this.onRightTextClickListener = onRightTextClickListener;
        return this;
    }

    public interface OnLeftCloseClickListener {
        void OnLeftCloseClickListener(View view);
    }

    private OnLeftCloseClickListener onLeftCloseClickListener;

    //new 这个接口后不会自动返回
    public void setOnLeftCloseClickListener(OnLeftCloseClickListener onLeftCloseClickListener) {
        this.onLeftCloseClickListener = onLeftCloseClickListener;
    }

    /**
     * 右边TextView标题更换
     *
     * @param title
     * @return
     */
    public TopBar setRightTextViewTitle(String title) {
        textViewRight.setText(title);
        return this;
    }

    public TextView getTitleView() {
        return textView;
    }

    public TopBar hideDivider() {
        divider.setVisibility(GONE);
        return this;
    }
}
