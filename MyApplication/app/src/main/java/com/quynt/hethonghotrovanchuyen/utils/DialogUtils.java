package com.quynt.hethonghotrovanchuyen.utils;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.TextView;

import com.quynt.hethonghotrovanchuyen.R;

/**
 * PRESENA_Android
 * <p/>
 * Created by Paditech on 3/16/2016.
 * Copyright (c) 2015 Paditech. All rights reserved.
 */
public class DialogUtils {

    public static Dialog showLoadingDialog(Context context) {
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setCancelable(false);
        dialog.setMessage("Loading...");
        return dialog;
    }

//    public static void showErrorDialog(Context context, String message) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setMessage(message)
//                .setCancelable(false)
//                .setTitle(R.string.alert_title_error)
//                .setPositiveButton(R.string.alert_button_ok, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        dialog.dismiss();
//                    }
//                });
//        AlertDialog alert = builder.create();
//        alert.show();
//        TextView textView = (TextView) alert.findViewById(android.R.id.message);
//        textView.setTextSize(14);
//    }
//
//    public static void showDialog(Context context) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setView(R.layout.feedback_dialog)
//                .setPositiveButton(R.string.alert_button_ok, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        dialog.dismiss();
//                    }
//                });
//        AlertDialog alert = builder.create();
//        alert.show();
//    }

    public static void showMessageDialog(Context context, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

}
