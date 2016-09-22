package com.hb.rimi.angel.util;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;

/**
 * 拨打客服电话管理类
 * Created by rimi on 2016/6/13.
 */
public class CallManager {
    public static void callPhone(final Context context,final String phone){
        new AlertDialog.Builder(context).setTitle("拨打电话").setMessage(phone)
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" +phone));
                        //intent.setAction(Intent.ACTION_DIAL);
                        //intent.setData(Uri.parse(myCustomtTelephone));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                })
                .setNegativeButton("否", null).show();
    }
}
