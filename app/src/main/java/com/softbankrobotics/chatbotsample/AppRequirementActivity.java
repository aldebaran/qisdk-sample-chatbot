package com.softbankrobotics.chatbotsample;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import com.aldebaran.qi.sdk.design.activity.RobotActivity;
import com.aldebaran.qi.sdk.design.activity.conversationstatus.SpeechBarDisplayStrategy;

import java.util.Locale;

public class AppRequirementActivity extends RobotActivity {

    private static final String APP_LANGUAGE = "English";
    private static final Class FIRST_ACTIVITY = AppIntroductionActivity.class;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSpeechBarDisplayStrategy(SpeechBarDisplayStrategy.OVERLAY);

        if (!Locale.getDefault().getDisplayLanguage().equals(APP_LANGUAGE)) {
            new AlertDialog.Builder(this)
                    .setTitle("Language warning")
                    .setMessage("This application is only available in English. Please set the tablet language to \"English\".")
                    .setPositiveButton(R.string.setting, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCALE_SETTINGS), 0);
                            finishAffinity();
                        }
                    })
                    .setNegativeButton(R.string.exit, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finishAffinity();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        } else {
            finish();
            startActualApp();
        }
    }

    private void startActualApp() {
        Intent intent = new Intent(this, FIRST_ACTIVITY);
        startActivity(intent);
    }
}
