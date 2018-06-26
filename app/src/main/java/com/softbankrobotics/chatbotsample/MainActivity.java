/*
 *  Copyright (C) 2018 Softbank Robotics Europe
 *  See COPYING for the license
 */
package com.softbankrobotics.chatbotsample;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.aldebaran.qi.sdk.QiSDK;
import com.aldebaran.qi.sdk.design.activity.RobotActivity;
import com.aldebaran.qi.sdk.object.conversation.Phrase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Main activity of the application.
 */
public class MainActivity extends RobotActivity implements UiNotifier {

    Robot robot;

    private TextView qiChatBotIcon;
    private TextView dialogFlowIcon;
    private TextView pepperTxt;
    private boolean isDialogFlow = false;
    private List<Phrase> qiChatRecommendation = new ArrayList<>();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dialogFlowIcon = findViewById(R.id.dialogFlow);
        qiChatBotIcon = findViewById(R.id.qiChatBot);
        pepperTxt = findViewById(R.id.pepperTxt);

        findViewById(R.id.btn_exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });

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

    @Override
    public void colorDialogFlow() {
        this.isDialogFlow = true;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                qiChatBotIcon.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.idle_button_background));
                dialogFlowIcon.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.green_button_background));
            }
        });
    }

    @Override
    public void colorQiChatBot() {
        if (!isDialogFlow) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    qiChatBotIcon.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.green_button_background));
                    dialogFlowIcon.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.idle_button_background));
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
                    pepperTxt.setText(text);
                }
            });
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

//        qiChatSuggestion.setVisibility(View.VISIBLE);
    }


    private void updateCommandUI() {
        if (qiChatRecommendation.isEmpty()) {
            return;
        }
        final int randomNum = ThreadLocalRandom.current().nextInt(0, qiChatRecommendation.size());
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //qiChatSuggestion.setText(qiChatRecommendation.get(randomNum).getText());
            }
        });
    }

    @Override
    public void updateQiChatRecommendation(List<Phrase> recommendation) {
        this.qiChatRecommendation = recommendation;

    }
}
