/*
 *  Copyright (C) 2018 Softbank Robotics Europe
 *  See COPYING for the license
 */
package com.softbankrobotics.chatbotsample;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.aldebaran.qi.sdk.QiSDK;
import com.aldebaran.qi.sdk.design.activity.RobotActivity;

/**
 * Main activity of the application.
 */
public class MainActivity extends RobotActivity implements UiNotifier {

    Robot robot;

    private ConstraintLayout qiChatBotContainer;
    private ConstraintLayout dialogFlowContainer;
    private TextView dialogTxt;
    private boolean isDialogFlow = false;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dialogFlowContainer = findViewById(R.id.dialogFlowContainer);
        qiChatBotContainer = findViewById(R.id.qiChatBotContainer);
        dialogTxt = findViewById(R.id.tv);
        // In this sample, instead of implementing robotlifecycle callbacks in the main activity,
        // we delegate them to a robot dedicated class.
        robot = new Robot(this);
        QiSDK.register(this, robot);
    }

    @Override
    protected void onDestroy() {
        QiSDK.unregister(this, robot);
        super.onDestroy();
    }

    public void changeDialogFlowColor(boolean isDialogFlow) {
        this.isDialogFlow = isDialogFlow;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dialogFlowContainer.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.green));
                qiChatBotContainer.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimary));
            }
        });
    }

    public void changeChatBotColor() {

    }


    @Override
    public void colorDialogFlow() {
        this.isDialogFlow = true;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dialogFlowContainer.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.green));
                qiChatBotContainer.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimary));
            }
        });
    }

    @Override
    public void colorQiChatBot() {
        if (!isDialogFlow) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    qiChatBotContainer.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.green));
                    dialogFlowContainer.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorAccent));
                }
            });
        }
        isDialogFlow = false;
    }

    @Override
    public void setText(final String text) {
        if (!isDialogFlow) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    dialogTxt.setText(text);
                }
            });
        }

    }
}
