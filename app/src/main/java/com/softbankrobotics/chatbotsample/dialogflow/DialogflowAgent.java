/*
 *  Copyright (C) 2018 Softbank Robotics Europe
 *  See COPYING for the license
 */
package com.softbankrobotics.chatbotsample.dialogflow;

import android.util.Log;

import com.google.gson.JsonElement;
import com.softbankrobotics.chatbotsample.BuildConfig;

import java.util.HashMap;
import java.util.Map;

import ai.api.AIConfiguration;
import ai.api.AIDataService;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;
import ai.api.model.Metadata;
import ai.api.model.Result;
import ai.api.model.Status;

import static ai.api.AIConfiguration.SupportedLanguages.English;

/**
 * Singleton aimed at accessing a specific Dialogflow agent dedicated to this sample.
 */
public final class DialogflowAgent {

    private static final String TAG = "DialogflowAgent";

    private static final DialogflowAgent INSTANCE = new DialogflowAgent();

    private final AIDataService aiDataService;

    private DialogflowAgent() {
        // The access token is a
        final AIConfiguration config = new AIConfiguration(BuildConfig.DIALOGFLOW_CLIENT_ACCESS_TOKEN, English);
        aiDataService = new AIDataService(config);
    }

    /**
     * @return the unique instance of this class
     */
    public static DialogflowAgent getInstance() {
        return INSTANCE;
    }

    /**
     * Ask a question to DialogFlow.
     * @param question the text we expect Dialogflow to answer to
     * @return the Dialogflow response
     */
    public AIResponse answerTo(final String question) {
        Log.d(TAG, "answerTo: " + question);

        // Prepare the request
        final AIRequest aiRequest = new AIRequest();
        aiRequest.setQuery(question);

        // Execute the request
        RequestTask task = new RequestTask(aiDataService);
        task.execute(aiRequest);

        // Wait for the response and return it
        AIResponse response = task.getResponse();
        logAIResponse(response);
        return response;
    }

    private void logAIResponse(final AIResponse response) {

        final Status status = response.getStatus();
        Log.d(TAG, "Status code: " + status.getCode());
        Log.d(TAG, "Status type: " + status.getErrorType());

        final Result result = response.getResult();
        Log.d(TAG, "Resolved query: " + result.getResolvedQuery());

        Log.d(TAG, "Action: " + result.getAction());

        final String speech = result.getFulfillment().getSpeech();
        Log.d(TAG, "Speech: " + speech);

        final Metadata metadata = result.getMetadata();
        if (metadata != null) {
            Log.d(TAG, "Intent id: " + metadata.getIntentId());
            Log.d(TAG, "Intent name: " + metadata.getIntentName());
        }
        final HashMap<String, JsonElement> params = result.getParameters();
        if (params != null && !params.isEmpty()) {
            Log.d(TAG, "Parameters: ");
            for (final Map.Entry<String, JsonElement> entry : params.entrySet()) {
                Log.d(TAG, String.format("%s: %s", entry.getKey(), entry.getValue().toString()));
            }
        }
    }
}
