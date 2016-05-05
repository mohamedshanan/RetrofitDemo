package com.renasoft.retrofitfulldemo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class Utils {
    public static void showDialog(Context context, String title, String message) {
        AlertDialog mDialog = new AlertDialog.Builder(context).create();
        mDialog.setTitle(title);
        mDialog.setMessage(message);
        mDialog.setIcon(R.drawable.ic_launcher);
        mDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        mDialog.show();
    }
}
