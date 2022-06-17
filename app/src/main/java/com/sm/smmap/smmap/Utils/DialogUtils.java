package com.sm.smmap.smmap.Utils;

import android.content.Context;
import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;

public class DialogUtils {
    public static void show2BtnDialog(Context context, String title, String message, final CallBack callBack){
        final AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle(title)
                .setMessage(message)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (callBack != null)   callBack.onPositive();
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (callBack != null)   callBack.onNegative();
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }

    private void showListDialog(Context context, String[] items,String msg, final OnItemClickCallBack callBack) {
        AlertDialog.Builder listDialog =
                new AlertDialog.Builder(context);
        listDialog.setTitle(msg);
        listDialog.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (callBack != null)   callBack.onItemClick(dialog,which);
                dialog.dismiss();
            }
        });
        listDialog.show();
    }

    public interface OnItemClickCallBack{
        void onItemClick(DialogInterface dialog, int which);
    }

    public interface CallBack{
        void onPositive();
        void onNegative();
    }
}
