/*
 *  Copyright (C) 2018 Softbank Robotics Europe
 *  See COPYING for the license
 */
package com.softbankrobotics.chatbotsample;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
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

    private ConstraintLayout qiChatBotContainer;
    private ConstraintLayout dialogFlowContainer;
    private TextView dialogTxt;
    private TextView qiChatSuggestion;
    private boolean isDialogFlow = false;

    private final AlphaAnimation fadeOutAnimation = new AlphaAnimation(1.0f, 0.0f);
    private final AlphaAnimation fadeInAnimation = new AlphaAnimation(0.0f, 1.0f);
    private List<Phrase> qiChatRecommendation = new ArrayList<>();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dialogFlowContainer = findViewById(R.id.dialogFlowContainer);
        qiChatBotContainer = findViewById(R.id.qiChatBotContainer);
        dialogTxt = findViewById(R.id.tv);
        qiChatSuggestion = findViewById(R.id.qiChatSuggest);
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

    @Override
    protected void onResume() {
        super.onResume();

        qiChatSuggestion.setVisibility(View.VISIBLE);
        startAutoCommandUpdate();
    }

    private void startAutoCommandUpdate() {
        fadeOutAnimation.setStartOffset(3000);
        fadeOutAnimation.setDuration(3000);
        fadeOutAnimation.setAnimationListener(fadeOutAnimationListener);
        fadeInAnimation.setStartOffset(1000);
        fadeInAnimation.setDuration(3000);
        fadeInAnimation.setAnimationListener(fadeInAnimationListener);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                qiChatSuggestion.startAnimation(fadeInAnimation);
            }
        });
    }


    Animation.AnimationListener fadeInAnimationListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {
            qiChatSuggestion.setAlpha(1.0f);
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            qiChatSuggestion.startAnimation(fadeOutAnimation);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
            // nothing here
        }
    };

    Animation.AnimationListener fadeOutAnimationListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {
            // nothing here
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            updateCommandUI();
            qiChatSuggestion.setAlpha(0.0f);
            qiChatSuggestion.startAnimation(fadeInAnimation);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
            // nothing here
        }
    };

    private void updateCommandUI() {
        if(qiChatRecommendation.size()==0){
            return;
        }
        final int randomNum = ThreadLocalRandom.current().nextInt(0, qiChatRecommendation.size() );
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                qiChatSuggestion.setText(qiChatRecommendation.get(randomNum).getText());
            }
        });
    }

    @Override
    public void updateQiChatRecommendation(List<Phrase> recommendation) {
        this.qiChatRecommendation = recommendation ;
    }
}
