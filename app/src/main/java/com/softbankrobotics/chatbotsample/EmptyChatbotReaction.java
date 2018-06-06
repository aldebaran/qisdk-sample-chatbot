/*
 *  Copyright (C) 2018 Softbank Robotics Europe
 *  See COPYING for the license
 */
package com.softbankrobotics.chatbotsample;

import com.aldebaran.qi.sdk.QiContext;
import com.aldebaran.qi.sdk.object.conversation.BaseChatbotReaction;
import com.aldebaran.qi.sdk.object.conversation.SpeechEngine;

/**
 * A ChatbotReaction that does nothing.
 */
public class EmptyChatbotReaction extends BaseChatbotReaction {

    private static final String TAG = "EmptyChatbotReaction";

    EmptyChatbotReaction(final QiContext context) {
        super(context);
    }

    @Override
    public void runWith(final SpeechEngine speechEngine) {
    }

    @Override
    public void stop() {
    }

}
