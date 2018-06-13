/*
 *  Copyright (C) 2018 Softbank Robotics Europe
 *  See COPYING for the license
 */
package com.softbankrobotics.chatbotsample;

import android.util.Log;

import com.aldebaran.qi.sdk.QiContext;
import com.aldebaran.qi.sdk.RobotLifecycleCallbacks;
import com.aldebaran.qi.sdk.builder.ChatBuilder;
import com.aldebaran.qi.sdk.builder.QiChatbotBuilder;
import com.aldebaran.qi.sdk.builder.TopicBuilder;
import com.aldebaran.qi.sdk.object.conversation.Chat;
import com.aldebaran.qi.sdk.object.conversation.Chatbot;
import com.aldebaran.qi.sdk.object.conversation.Phrase;
import com.aldebaran.qi.sdk.object.conversation.QiChatbot;
import com.aldebaran.qi.sdk.object.conversation.Topic;

/**
 * Class that gathers main robot-related operations of our application.
 */
public class Robot implements RobotLifecycleCallbacks {

    private static final String TAG = "Robot";

    private Chat chat;
    private QiContext qiContext;

    @Override
    public void onRobotFocusGained(final QiContext theContext) {
        Log.d(TAG, "onRobotFocusGained");
        this.qiContext = theContext;

        // Now that the focus is owned by this app, the chat can be run
        runChat();
    }

    @Override
    public void onRobotFocusLost() {
        Log.d(TAG, "onRobotFocusLost");
        this.qiContext = null;

        removeChatListeners();
    }

    @Override
    public void onRobotFocusRefused(final String reason) {
        Log.e(TAG, "Robot is not available: " + reason);
    }

    private void runChat() {
        Log.d(TAG, "runChat()");

        // Create chatbots
        Chatbot qichatbot = createQiChatbot();
        Chatbot dialogFlowChatbot = new DialogflowChatbot(qiContext);

        // Create the chat from its chatbots
        chat = ChatBuilder.with(qiContext)
                               .withChatbot(qichatbot, dialogFlowChatbot)
                               .build();

        setChatListeners();

        chat.async().run();
    }

    private QiChatbot createQiChatbot() {

        // Create a topic
        Topic topic = TopicBuilder.with(qiContext)
                                  .withResource(R.raw.shop)
                                  .build();

        // Create the QiChatbot from a topic
        return QiChatbotBuilder.with(qiContext)
                               .withTopic(topic)
                               .build();
    }

    private void setChatListeners() {
        chat.addOnStartedListener(new Chat.OnStartedListener() {
            @Override
            public void onStarted() {
                Log.i(TAG, "chat.onStarted()");
            }
        });

        chat.addOnListeningChangedListener(new Chat.OnListeningChangedListener() {
            @Override
            public void onListeningChanged(final Boolean aBoolean) {
                Log.i(TAG, "chat.onListeningChanged(): " + aBoolean);
            }
        });

        chat.addOnSayingChangedListener(new Chat.OnSayingChangedListener() {
            @Override
            public void onSayingChanged(final Phrase phrase) {
                Log.i(TAG, "chat.onSayingChanged(): " + phrase.getText());
            }
        });

        chat.addOnHeardListener(new Chat.OnHeardListener() {
            @Override
            public void onHeard(final Phrase phrase) {
                Log.i(TAG, "chat.onHeard: " + phrase.getText());
            }
        });

        chat.addOnNoPhraseRecognizedListener(new Chat.OnNoPhraseRecognizedListener() {
            @Override
            public void onNoPhraseRecognized() {
                Log.i(TAG, "chat.onNoPhraseRecognized()");
            }
        });

        chat.addOnNormalReplyFoundForListener(new Chat.OnNormalReplyFoundForListener() {
            @Override
            public void onNormalReplyFoundFor(final Phrase input) {
                Log.i(TAG, "chat.onNormalReplyFoundFor() phrase.getText() = " + input.getText());
            }
        });

        chat.addOnFallbackReplyFoundForListener(new Chat.OnFallbackReplyFoundForListener() {
            @Override
            public void onFallbackReplyFoundFor(final Phrase input) {
                Log.i(TAG, "chat.onFallbackReplyFoundFor() input.getText() =" + input.getText());
            }
        });

        chat.addOnNoReplyFoundForListener(new Chat.OnNoReplyFoundForListener() {
            @Override
            public void onNoReplyFoundFor(final Phrase input) {
                Log.i(TAG, "chat.onNoReplyFoundFor() input.getText() = " + input.getText());
            }
        });
    }

    private void removeChatListeners() {
        if (chat == null) {
            return;
        }

        chat.removeAllOnStartedListeners();
        chat.removeAllOnListeningChangedListeners();
        chat.removeAllOnSayingChangedListeners();
        chat.removeAllOnHeardListeners();
        chat.removeAllOnNoPhraseRecognizedListeners();
        chat.removeAllOnNormalReplyFoundForListeners();
        chat.removeAllOnFallbackReplyFoundForListeners();
        chat.removeAllOnNoReplyFoundForListeners();
    }
}
