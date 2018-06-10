package com.example.shanto.lab18.provatsoft.apps.flag71.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.text.Html;

public class ProgressDialogManager extends ProgressDialog {
    private String message;
    ProgressDialog pDialog;

    public ProgressDialogManager(Context context, String message) {
        super(context);
        this.pDialog = new ProgressDialog(context);
        this.pDialog.setIndeterminate(false);
        this.pDialog.setCancelable(true);
        this.message = message;
    }

    public void show() {
        this.pDialog.setMessage(Html.fromHtml(String.format("<center>%s</center>", new Object[]{this.message})));
        this.pDialog.show();
    }

    public void dismiss() {
        this.pDialog.dismiss();
    }
}
