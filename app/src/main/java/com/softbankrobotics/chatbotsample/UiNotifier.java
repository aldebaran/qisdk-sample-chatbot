package com.softbankrobotics.chatbotsample;

import com.aldebaran.qi.sdk.object.conversation.Phrase;

import java.util.List;

public interface UiNotifier {
    void setText(String text);

    void isDialogFlow(boolean dialogFlow);
    void updateQiChatSuggestions(List<Phrase> recommendation);
}
