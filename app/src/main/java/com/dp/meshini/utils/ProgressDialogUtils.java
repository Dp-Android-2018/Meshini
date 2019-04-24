package com.dp.meshini.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;

import com.dp.meshini.R;

public class ProgressDialogUtils {

    private static ProgressDialogUtils progressDialogUtils=null;
    private Dialog dialog=null;

    public ProgressDialogUtils() {
    }

    public static ProgressDialogUtils getInstance() {
        if(progressDialogUtils==null){
            progressDialogUtils=new ProgressDialogUtils();
        }
        return progressDialogUtils;
    }

    public void showProgressDialog(Context activity){
        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progress_dialog_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
        if (!dialog.isShowing())
            dialog.show();
    }

    public void cancelDialog(){
        dialog.dismiss();
    }
}
