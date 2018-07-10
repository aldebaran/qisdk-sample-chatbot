/*
 *  Copyright (C) 2018 Softbank Robotics Europe
 *  See COPYING for the license
 */
package com.softbankrobotics.chatbotsample;

import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.Group;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.aldebaran.qi.sdk.QiSDK;
import com.aldebaran.qi.sdk.design.activity.RobotActivity;
import com.aldebaran.qi.sdk.object.conversation.Phrase;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Main activity of the application.
 */
public class MainActivity extends RobotActivity implements UiNotifier {

    private Robot robot;

    private TextView qiChatBotIcon;
    private TextView dialogFlowIcon;
    private TextView pepperTxt;
    private TextView suggestion1;
    private TextView suggestion2;
    private TextView suggestion3;
    private TextView suggestion4;
    private boolean isDialogFlow = false;
    private Group robotViewGroup;
    private List<Phrase> suggestions = new ArrayList<>();
    final Handler handler = new Handler();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dialogFlowIcon = findViewById(R.id.dialogFlow);
        qiChatBotIcon = findViewById(R.id.qiChatBot);
        pepperTxt = findViewById(R.id.pepperTxt);
        robotViewGroup = findViewById(R.id.robotViewGroup);
        suggestion1 = findViewById(R.id.suggestion1);
        suggestion2 = findViewById(R.id.suggestion2);
        suggestion3 = findViewById(R.id.suggestion3);
        suggestion4 = findViewById(R.id.suggestion4);
        fillSuggestion();
        findViewById(R.id.btn_exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
            }
        });
        findViewById(R.id.resetSuggestions).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillSuggestion();
            }
        });

        // In this sample, instead of implementing robotlifecycle callbacks in the main activity,
        // we delegate them to a robot dedicated class.
        robot = new Robot(this);
        QiSDK.register(this, robot);

    }

    private void fillSuggestion() {
        if (suggestions.isEmpty()) {
            return;
        }
        int[] fourRandomInt = get4RandomInt(suggestions.size());
        suggestion1.setText(String.format(getString(R.string.suggestion_format), suggestions.get(fourRandomInt[0]).getText()));
        suggestion2.setText(String.format(getString(R.string.suggestion_format), suggestions.get(fourRandomInt[1]).getText()));
        suggestion3.setText(String.format(getString(R.string.suggestion_format), suggestions.get(fourRandomInt[2]).getText()));
        suggestion4.setText(String.format(getString(R.string.suggestion_format), suggestions.get(fourRandomInt[3]).getText()));

    }

    @Override
    protected void onDestroy() {
        QiSDK.unregister(this, robot);
        super.onDestroy();
    }

    public void colorDialogFlow() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                qiChatBotIcon.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.top_left_rounded_background));
                dialogFlowIcon.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.green_top_right_rounded_background));
                pepperTxt.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.peper_talk_green_background));
            }
        });
    }

    public void colorQiChatBot() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                qiChatBotIcon.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.green_top_left_rounded_background));
                dialogFlowIcon.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.top_right_rounded_background));
                pepperTxt.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.peper_talk_green_background));
            }
        });
    }

    @Override
    public void setText(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                robotViewGroup.setVisibility(View.VISIBLE);
                if (!TextUtils.isEmpty(text)) {
                    pepperTxt.setText(text);
                    if (isDialogFlow) {
                        colorDialogFlow();
                        isDialogFlow = false;
                    } else {
                        colorQiChatBot();
                    }
                }
                resetLayout(text);
            }
        });

    }

    @Override
    public void isDialogFlow(boolean dialogFlow) {
        isDialogFlow = dialogFlow;
    }

    private void resetLayout(final String text) {
        if (TextUtils.isEmpty(text)) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    pepperTxt.setText("");
                    dialogFlowIcon.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.top_right_rounded_background));
                    qiChatBotIcon.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.top_left_rounded_background));
                    pepperTxt.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.peper_talk_background));
                    fillSuggestion();
                }
            }, 500);
        }
    }

    @Override
    public void updateQiChatSuggestions(List<Phrase> suggestions) {
        this.suggestions = suggestions;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                fillSuggestion();
            }
        });
    }

    public int[] get4RandomInt(int maxRange) {
        int[] result = new int[4];
        Set<Integer> used = new HashSet<>();
        Random gen = new Random();
        for (int i = 0; i < result.length; i++) {
            int newRandom;
            do {
                newRandom = gen.nextInt(maxRange);
            } while (used.contains(newRandom));
            result[i] = newRandom;
            used.add(newRandom);
        }
        return result;
    }

}
