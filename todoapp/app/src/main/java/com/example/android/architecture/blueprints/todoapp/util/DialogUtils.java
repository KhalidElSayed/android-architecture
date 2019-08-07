package com.example.android.architecture.blueprints.todoapp.util;


import android.app.Activity;
import android.graphics.Typeface;

import com.example.android.architecture.blueprints.todoapp.R;
import com.tapadoo.alerter.Alerter;

public class DialogUtils {

  public enum MessageType {
    SUCCESS,
    ERROR,
    DEFAULT
  }

  public DialogUtils() {
  }

  /*public static MaterialDialog.Builder getBaseDialog() {
    return getBaseDialog(App.getContext());
  }

  public static MaterialDialog.Builder getBaseDialog(Context context) {
    GravityEnum dir, btnDir;

        *//*if (Utils.isAndroidHasRtl()) {
            if (LanguageUtils.isRTL()) {
                dir = GravityEnum.START;
                btnDir = GravityEnum.START;
            } else {
                dir = GravityEnum.START;
                btnDir = GravityEnum.END;
            }
        } else {
            dir = GravityEnum.END;
            btnDir = GravityEnum.END;
        }*//*

    dir = GravityEnum.END;
    btnDir = GravityEnum.END;

    MaterialDialog.Builder builder =
            new MaterialDialog.Builder(context)
                    .titleGravity(dir)
                    .contentGravity(dir)
                    .buttonsGravity(btnDir);
    return builder;
  }

  public static MaterialDialog.Builder getBaseDialog(Context context, boolean isArabic) {
    GravityEnum dir, btnDir;

    if (isArabic) {
      dir = GravityEnum.END;
      btnDir = GravityEnum.END;
    } else {
      dir = GravityEnum.START;
      btnDir = GravityEnum.START;
    }

    MaterialDialog.Builder builder =
            new MaterialDialog.Builder(context)
                    .titleGravity(dir)
                    .contentGravity(dir)
                    .buttonsGravity(btnDir);
    return builder;
  }*/

  public static void showMessage(Activity activity, String msg, MessageType type) {
    Alerter alerter = Alerter.create(activity)
            .setText(msg)
            .setTextTypeface(Typeface.createFromAsset(activity.getAssets(), "fonts/Bahij_TheSansArabic-Plain.ttf"));

    switch (type) {
      case SUCCESS:
        alerter.setIcon(R.drawable.ic_check_white_24dp)
                .setBackgroundColorRes(R.color.jade);
        break;
      case ERROR:
        alerter.setIcon(R.drawable.ic_error_outline_white_24dp)
                .setBackgroundColorRes(R.color.ruby_red);
        break;
      case DEFAULT:
        break;
    }

    alerter.show();
  }

}
