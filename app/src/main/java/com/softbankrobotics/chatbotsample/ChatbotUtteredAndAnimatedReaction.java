/*
 *  Copyright (C) 2018 Softbank Robotics Europe
 *  See COPYING for the license
 */
package com.softbankrobotics.chatbotsample;

import android.support.annotation.RawRes;
import android.util.Log;

import com.aldebaran.qi.Future;
import com.aldebaran.qi.sdk.QiContext;
import com.aldebaran.qi.sdk.builder.AnimateBuilder;
import com.aldebaran.qi.sdk.builder.AnimationBuilder;
import com.aldebaran.qi.sdk.object.actuation.Animate;
import com.aldebaran.qi.sdk.object.actuation.Animation;
import com.aldebaran.qi.sdk.object.conversation.BaseChatbotReaction;
import com.aldebaran.qi.sdk.object.conversation.Phrase;
import com.aldebaran.qi.sdk.object.conversation.Say;
import com.aldebaran.qi.sdk.object.conversation.SpeechEngine;

import java.util.concurrent.ExecutionException;

/**
 * A ChatbotReaction that says its reply with an animation.
 *
 */
public class ChatbotUtteredAndAnimatedReaction extends BaseChatbotReaction {

    private static final String TAG = "DialogflowChatbotReac";

    private String toBeSaid;
    private @RawRes int animationResourceId;

    private Future<Void> fsay;
    private Future<Void> fanimate;

    ChatbotUtteredAndAnimatedReaction(final QiContext qiContext, final String answer, final @RawRes int animResID) {
        super(qiContext);
        toBeSaid = answer;
        animationResourceId = animResID;
    }

    @Override
    public void runWith(final SpeechEngine speechEngine) {

        // All Say actions that must be executed inside this method must be created
        // via the provided SpeechEngine.
        Say say = speechEngine.makeSay(new Phrase(toBeSaid));

        Animation animation = AnimationBuilder.with(getQiContext())
                                              .withResources(animationResourceId)
                                              .build();
        Animate animate = AnimateBuilder.with(getQiContext())
                                        .withAnimation(animation)
                                        .build();

        // The actions must be executed asynchronously in order to get futures that allow
        // the actions to be canceled by the chat engine thanks to the stop() method.
        // Additionally, the asynchronous call allows here both actions to be executed in parallel.
        fsay = say.async().run();
        fanimate = animate.async().run();

        try {
            // One must not leave runWith before the actions are terminated: thus wait on the futures
            fsay.get();
            fanimate.get();
        } catch (ExecutionException e) {
            Log.e(TAG, "Error during actions execution", e);
        }
    }


    @Override
    public void stop() {

        // All futures created in runWith should be canceled when stop() is called

        if (fsay != null) {
            fsay.requestCancellation();
        }
        if (fanimate != null) {
            fanimate.requestCancellation();
        }
    }

}
