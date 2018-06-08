/*
 *  Copyright (C) 2018 Softbank Robotics Europe
 *  See COPYING for the license
 */
package com.softbankrobotics.chatbotsample;

import android.util.Log;

import com.aldebaran.qi.Future;
import com.aldebaran.qi.sdk.QiContext;
import com.aldebaran.qi.sdk.builder.SayBuilder;
import com.aldebaran.qi.sdk.object.conversation.BaseChatbotReaction;
import com.aldebaran.qi.sdk.object.conversation.Say;
import com.aldebaran.qi.sdk.object.conversation.SpeechEngine;

import java.util.concurrent.ExecutionException;

/**
 * A ChatbotReaction that simply utters its reply.
 *
 */
public class ChatbotUtteredReaction extends BaseChatbotReaction {

        private static final String TAG = "DialogflowChatbotReac";

        private String toBeSaid;
        private Future<Void> fsay;


        ChatbotUtteredReaction(final QiContext qiContext, final String answer) {
            super(qiContext);
            toBeSaid = answer;
        }

        @Override
        public void runWith(final SpeechEngine speechEngine) {

            // All Say actions that must be executed inside this method must be created via the SpeechEngine
            Say say = SayBuilder.with(speechEngine)
                                .withText(toBeSaid)
                                .build();

            try {
                // The say action must be executed asynchronously in order to get
                // a future that can be canceled by the head thanks to the stop() method
                fsay = say.async().run();

                // However, runWith must not be leaved before the say action is terminated : thus wait on the future
                fsay.get();

            } catch (ExecutionException e) {
                Log.e(TAG, "Error during say", e);
            }
        }


        @Override
        public void stop() {

            // All actions created in runWith should be canceled when stop is called

            if (fsay != null) {
                fsay.requestCancellation();
            }
        }

}
