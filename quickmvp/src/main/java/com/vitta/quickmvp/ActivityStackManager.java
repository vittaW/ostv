package com.vitta.quickmvp;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.util.List;
import java.util.Stack;

/**
 * -------------------------------------
 * 作者：王文婷@<WVitta@126.com>
 * -------------------------------------
 * 时间：2018/1/30 17:32
 * -------------------------------------
 * 描述：activity栈管理器，项目只有一份，在App里初始化;
 * 栈 ：吃饱了吐；行为是先入后出,压栈和弹栈；
 * 队列 ：吃饱了拉；行为是先入先出；
 * -------------------------------------
 * 备注：
 * -------------------------------------
 */
public class ActivityStackManager {

    private Stack<Activity> mActivityStack;
    private Context mContext;

    public ActivityStackManager(Context context) {
        mActivityStack = new Stack<>();
        mContext = context;
    }

    /**
     * 入栈
     * @param activity
     */
    public void pushActivity(Activity activity) {
        mActivityStack.push(activity);
    }

    /**
     * 弹栈
     * @param activity
     */
    public void popActivity(Activity activity) {
        mActivityStack.remove(activity);
    }

    /**
     * 销毁所有的activity
     */
    public void finishAll(){
        Activity pop;
        while (!mActivityStack.empty()){
            pop = mActivityStack.pop();
            if (pop != null) {
                pop.finish();
            }
        }
    }

    /**
     * 是否包含activity
     * @param activity
     * @return
     */
    public boolean containsActivity(Activity activity){
        return mActivityStack != null && mActivityStack.contains(activity);
    }

    /**
     * 结束activity 之上的所有activity
     * @return
     */
    public boolean finishToActivity(Class<? extends Activity> cls){
        while (!mActivityStack.empty()) {
            Activity pop = mActivityStack.pop();
            if (pop != null){
                if (pop.getClass().getName().equals(cls.getName())){
                    return true;
                }
                pop.finish();
            }
        }
        return false;
    }

    /**
     * 获取栈顶activity 全名称
     * @return activity全名称
     */
    public String getTopActivity() {
        android.app.ActivityManager manager = (android.app.ActivityManager) mContext.getSystemService(mContext.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(1);

        if (runningTaskInfos != null) {
            return (runningTaskInfos.get(0).topActivity).toString();
        } else{
            return null;
        }
    }

}
