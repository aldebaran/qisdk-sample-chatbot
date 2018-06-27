package com.softbankrobotics.chatbotsample;

import com.aldebaran.qi.sdk.object.conversation.Phrase;

import java.util.List;

public interface UiNotifier {
    void colorDialogFlow();
    void colorQiChatBot();
    void setText(String text);

    void updateQiChatSuggestions(List<Phrase> recommendation);
}
