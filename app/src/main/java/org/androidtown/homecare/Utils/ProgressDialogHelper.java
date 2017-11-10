package org.androidtown.homecare.Utils;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by hanhb on 2017-11-09.
 */

public class ProgressDialogHelper {

    private static ProgressDialog progressDialog;

    public static void show(Context context){
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("잠시만 기다려주세요");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    //원하는 메시지로
    public static void show(Context context, String message){
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(message);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    public static void dismiss(){
        if(progressDialog !=null)
            progressDialog.dismiss();
    }

}
