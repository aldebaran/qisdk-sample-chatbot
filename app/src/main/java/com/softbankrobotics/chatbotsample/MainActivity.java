/*
 *  Copyright (C) 2018 Softbank Robotics Europe
 *  See COPYING for the license
 */
package com.softbankrobotics.chatbotsample;

import android.os.Bundle;

import com.aldebaran.qi.sdk.QiSDK;
import com.aldebaran.qi.sdk.design.activity.RobotActivity;

/**
 * Main activity of the application.
 */
public class MainActivity extends RobotActivity {

    Robot robot = new Robot();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // In this sample, instead of implementing robotlifecycle callbacks in the main activity,
        // we delegate them to a robot dedicated class.

        QiSDK.register(this, robot);
    }

    @Override
    protected void onDestroy() {
        QiSDK.unregister(this, robot);
        super.onDestroy();
    }
}
