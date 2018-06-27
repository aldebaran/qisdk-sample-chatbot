/*
 *  Copyright (C) 2018 Softbank Robotics Europe
 *  See COPYING for the license
 */
package com.softbankrobotics.chatbotsample;

import android.util.Log;

import com.aldebaran.qi.sdk.QiContext;
import com.aldebaran.qi.sdk.object.conversation.BaseChatbot;
import com.aldebaran.qi.sdk.object.conversation.BaseChatbotReaction;
import com.aldebaran.qi.sdk.object.conversation.Phrase;
import com.aldebaran.qi.sdk.object.conversation.ReplyPriority;
import com.aldebaran.qi.sdk.object.conversation.StandardReplyReaction;
import com.aldebaran.qi.sdk.object.locale.Locale;
import com.softbankrobotics.chatbotsample.dialogflow.DialogflowAgent;

import ai.api.model.AIResponse;
import ai.api.model.Result;

import static com.aldebaran.qi.sdk.object.conversation.ReplyPriority.FALLBACK;

/**
 * A sample chatbot that delegates questions/answers to a Dialogflow agent.
 */
public class DialogflowChatbot extends BaseChatbot {

    private static final String TAG = "DialogflowChatbot";
    private static final String EXCITEMENT_ACTION = "excitement";
    private final UiNotifier uiNotifier;

    DialogflowChatbot(final QiContext context, UiNotifier uiNotifier) {
        super(context);
        this.uiNotifier = uiNotifier;
    }

    @Override
    public StandardReplyReaction replyTo(final Phrase phrase, final Locale locale) {

        if (phrase.getText().isEmpty()) {
            // The phrase may be empty when the robot ears something
            // but cannot recognize words. Return an empty reply.
            EmptyChatbotReaction emptyReac = new EmptyChatbotReaction(getQiContext());
            return new StandardReplyReaction(emptyReac, ReplyPriority.FALLBACK);
        } else {

            // Ask the online DialogFlow agent to answer to the phrase
            DialogflowAgent dfAgent = DialogflowAgent.getInstance();
            AIResponse aiResponse = dfAgent.answerTo(phrase.getText());
            // Return a reply built from the agent's response
            return replyFromAIResponse(aiResponse);
        }
    }

    @Override
    public void acknowledgeHeard(final Phrase phrase, final Locale locale) {
        Log.i(TAG, "The robot heard: "+ phrase.getText());
    }

    @Override
    public void acknowledgeSaid(final Phrase phrase, final Locale locale) {
        uiNotifier.setText(phrase.getText());
        Log.i(TAG, "The robot uttered this reply, provided by another chatbot: "+ phrase.getText());
    }

    /*
     * Build a reply that can be processed by our chatbot, based on the response from Dialogflow
     */
    private StandardReplyReaction replyFromAIResponse(final AIResponse response) {
        Log.d(TAG, "replyFromAIResponse");
        uiNotifier.isDialogFlow(true);
        // Extract relevant data from Dialogflow response
        final Result result = response.getResult();
        String answer       = result.getFulfillment().getSpeech();
        String intentName   = result.getMetadata().getIntentName();
        String action       = result.getAction();

        // Set the priority of our reply, here by detecting the fallback nature of the Dialogflow
        // response according the name of the intent that was triggered

        BaseChatbotReaction reaction = null;
        if (EXCITEMENT_ACTION.equals(action)) {
            // An action is provided with the Dialogflow response: then add an animation to our reply
            reaction = new ChatbotUtteredAndAnimatedReaction(getQiContext(), answer, R.raw.nicereaction_a001);
        } else {
            // Otherwise let's have a simple reaction where the answer is just said
            reaction = new ChatbotUtteredReaction(getQiContext(), answer);
        }

        // Make the reply and return it
        return new StandardReplyReaction(reaction, FALLBACK);
    }
}
