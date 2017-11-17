package com.datatakehnn.services.apps_integrator;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.datatakehnn.R;

import static android.content.ContentValues.TAG;

/**
 * Created by user on 17/11/2017.
 */

public class IntentIntegrator {

    public static final String DEFAULT_TITLE = "Instale Medidor?";
    public static final String DEFAULT_MESSAGE =
            "Esta aplicación requiere Medidor de distancia . ¿Te gustaría instalarlo?";
    public static final String DEFAULT_YES = "SI";
    public static final String DEFAULT_NO = "No";



    private final Activity activity;
    private String title;
    private String message;
    private String buttonYes;
    private String buttonNo;
    private String PACKAGE_NAME = "";


    public IntentIntegrator(Activity activity,String package_name) {
        this.activity = activity;
        title = DEFAULT_TITLE;
        message = DEFAULT_MESSAGE;
        buttonYes = DEFAULT_YES;
        buttonNo = DEFAULT_NO;
        PACKAGE_NAME=package_name;
    }


    public AlertDialog showDownloadDialog() {
        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(activity);
        downloadDialog.setTitle(title);
        downloadDialog.setMessage(message);
        downloadDialog.setIcon(R.drawable.logo_datatakeh);
        downloadDialog.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String packageName =PACKAGE_NAME;
                Uri uri = Uri.parse("market://details?id=" + packageName);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    activity.startActivity(intent);
                } catch (ActivityNotFoundException anfe) {
                    // Hmm, market is not installed
                    Log.w(TAG, "Google Play is not installed; cannot install " + packageName);
                }
            }
        });
        downloadDialog.setNegativeButton(buttonNo, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {}
        });
        return downloadDialog.show();
    }

}
