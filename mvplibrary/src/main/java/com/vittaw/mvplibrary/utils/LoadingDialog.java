package com.vittaw.mvplibrary.utils;

import android.app.Activity;

import com.vittaw.mvplibrary.dialog.LoadingDialogView;

public class LoadingDialog {

    private static LoadingDialogView dialog;

    /**
     * loading dialog 是否可以取消
     */
    public static void showLoadingDialog(Activity activity, boolean cancelable) {
        if (dialog != null && dialog.isShowing()) {
            return;
        }
        dialog = new LoadingDialogView(activity, cancelable);
        dialog.show();
    }

    /**
     * loading dialog 是否可以取消
     */
    public static void showLoadingDialog(Activity activity) {
        showLoadingDialog(activity, true);
    }

    /**
     * dismiss dialog
     */
    public static void dismissLoadingDialog() {
        try {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
