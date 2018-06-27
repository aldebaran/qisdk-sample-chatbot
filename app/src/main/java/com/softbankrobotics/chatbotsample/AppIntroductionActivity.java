package com.softbankrobotics.chatbotsample;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.aldebaran.qi.Consumer;
import com.aldebaran.qi.Future;
import com.aldebaran.qi.sdk.QiContext;
import com.aldebaran.qi.sdk.QiSDK;
import com.aldebaran.qi.sdk.RobotLifecycleCallbacks;
import com.aldebaran.qi.sdk.builder.SayBuilder;
import com.aldebaran.qi.sdk.design.activity.RobotActivity;
import com.aldebaran.qi.sdk.design.activity.conversationstatus.SpeechBarDisplayStrategy;
import com.aldebaran.qi.sdk.object.conversation.Say;

public class AppIntroductionActivity extends RobotActivity implements RobotLifecycleCallbacks {

    private static final String TAG = "AppIntroductionActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSpeechBarDisplayStrategy(SpeechBarDisplayStrategy.OVERLAY);
        setContentView(R.layout.activity_app_introduction);

        QiSDK.register(this, this);

        findViewById(R.id.btn_skip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppIntroductionActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onRobotFocusGained(QiContext qiContext) {
        Say say = SayBuilder.with(qiContext).withText(getString(R.string.app_introduction)).build();
        Future<Void> sayFuture = say.async().run();

        sayFuture.thenConsume(new Consumer<Future<Void>>() {
            @Override
            public void consume(Future<Void> future) throws Throwable {
                Intent intent = new Intent(AppIntroductionActivity.this,  MainActivity.class);
                startActivity(intent);
                finishAffinity();
            }
        });
    }

    @Override
    public void onRobotFocusLost() {
        Log.i(TAG, "Focus lost");
    }

    @Override
    public void onRobotFocusRefused(String reason) {
        Log.i(TAG, "onRobotFocusRefused: " + reason);
    }
}
