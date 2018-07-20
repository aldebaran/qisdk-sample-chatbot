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

    EmptyChatbotReaction(final QiContext context) {
        super(context);
    }

    @Override
    public void runWith(final SpeechEngine speechEngine) {
        // Not used.
    }

    @Override
    public void stop() {
        // Not used.
    }
}
