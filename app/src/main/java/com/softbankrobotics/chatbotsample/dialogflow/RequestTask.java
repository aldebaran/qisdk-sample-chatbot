/*
 *  Copyright (C) 2018 Softbank Robotics Europe
 *  See COPYING for the license
 */
package com.softbankrobotics.chatbotsample.dialogflow;

import android.os.AsyncTask;
import android.util.Log;

import com.aldebaran.qi.Promise;

import ai.api.AIDataService;
import ai.api.AIServiceException;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;

/**
 * An AsynchronousTask aimed at sending requests to a Dialogflow agent.
 */
public class RequestTask extends AsyncTask<AIRequest, Void, AIResponse> {

    private static final String TAG = "RequestTask";

    private AIDataService aiDataService;
    private Promise<AIResponse> responsePromise = new Promise<>();

    RequestTask(final AIDataService anAIDataService) {
        aiDataService = anAIDataService;
    }

    /**
     * Get synchronously the response of the request.
     * @return the result of the request
     */
    AIResponse getResponse() {
        // Wait for the future until its underlying promise is set
        return responsePromise.getFuture().getValue();
    }

    @Override
    protected AIResponse doInBackground(final AIRequest... requests) {
        final AIRequest request = requests[0];
        if (request == null) {
            throw new NullPointerException("No request provided");
        }
        AIResponse result = null;
        try {
            result = aiDataService.request(request);
        } catch (AIServiceException e) {
            Log.e(TAG, "aiDataService request error", e);
        }
        // By setting the promise we unlock any waiting call on answerTo()
        responsePromise.setValue(result);
        return result;
    }

}
