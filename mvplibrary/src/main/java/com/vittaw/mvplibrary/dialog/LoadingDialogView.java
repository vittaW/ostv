package com.vittaw.mvplibrary.dialog;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;

import com.vittaw.mvplibrary.R;


public class LoadingDialogView extends Dialog {
    public Context context;

    public LoadingDialogView(Context context, boolean cancelable) {
        super(context, R.style.loading_alert_dialog);
        this.context = context;
        setCancelable(cancelable);
        setCanceledOnTouchOutside(false);
        Window window = getWindow();
        window.setWindowAnimations(R.style.DialogWindowStyle);
    }

    public LoadingDialogView(Context context) {
        super(context, R.style.loading_alert_dialog);
        this.context = context;
        setCancelable(true);
        setCanceledOnTouchOutside(false);
        Window window = getWindow();
        window.setWindowAnimations(R.style.DialogWindowStyle);
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_alert_dialog);
    }
}