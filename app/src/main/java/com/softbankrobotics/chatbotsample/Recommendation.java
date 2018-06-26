package com.softbankrobotics.chatbotsample;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Recommendation {

    private static Recommendation instance;
    private List<String> qiRecommendation;
    private List<String> dialogFlowRecommendation;

    private Recommendation() {
        init();
    }

    private void init() {
        qiRecommendation = new ArrayList<>();
        qiRecommendation.add("buy/purshase");
        qiRecommendation.add("Tell me a joke");
        qiRecommendation.add("What's it softbank robotics");
        qiRecommendation.add("When is Daily for Android Team ?");
        qiRecommendation.add("this is suggestion 5");
        qiRecommendation.add("this is suggestion 6");
        qiRecommendation.add("this is suggestion 7");
        qiRecommendation.add("this is suggestion 8");
        qiRecommendation.add("this is suggestion 9");
        qiRecommendation.add("this is suggestion 10");

        dialogFlowRecommendation = new ArrayList<>();
        dialogFlowRecommendation.add("You're annoying.");
        dialogFlowRecommendation.add("How old are you?");
        dialogFlowRecommendation.add("Who are you?");
        dialogFlowRecommendation.add("Answer my question.");
        dialogFlowRecommendation.add("You're bad.");
        dialogFlowRecommendation.add("Can you get smarter?");
        dialogFlowRecommendation.add("Who is your boss?");
        dialogFlowRecommendation.add("Are you busy?");
        dialogFlowRecommendation.add("Can you help me?");
        dialogFlowRecommendation.add("You're a chatbot.");
        dialogFlowRecommendation.add("You're so clever.");
        dialogFlowRecommendation.add("You're crazy.");
        dialogFlowRecommendation.add("You're fired.");
        dialogFlowRecommendation.add("You are funny.");
        dialogFlowRecommendation.add("You are good.");
        dialogFlowRecommendation.add("Are you happy?");
        dialogFlowRecommendation.add("Do you have a hobby?");
        dialogFlowRecommendation.add("Are you hungry?");
        dialogFlowRecommendation.add("Will you marry me?");
        dialogFlowRecommendation.add("Are we friends?");
        dialogFlowRecommendation.add("Where do you work?");
        dialogFlowRecommendation.add("Where are you from?");
        dialogFlowRecommendation.add("Hello/Hi/Hey");

        dialogFlowRecommendation.add("Thank you!");
        dialogFlowRecommendation.add("Well done!");
        dialogFlowRecommendation.add("Great!");

        dialogFlowRecommendation.add("I'm very angry right now.");
        dialogFlowRecommendation.add("I don't want to talk.");
        dialogFlowRecommendation.add("Today is my birthday.");
        dialogFlowRecommendation.add("I like you.");
        dialogFlowRecommendation.add("I'm so lonely.");


    }

    public static Recommendation getInstance() {
        if (instance == null) {
            instance = new Recommendation();
        }
        return instance;
    }

    public List<String> getQiRecommendation() {
        return qiRecommendation;
    }

    public List<String> getDialogFlowRecommendation() {
        return dialogFlowRecommendation;
    }

    public int[] get2RandomInt(int maxRange) {
        int[] result = new int[2];
        Set<Integer> used = new HashSet<>();
        Random gen = new Random();
        for (int i = 0; i < result.length; i++) {
            int newRandom;
            do {
                newRandom = gen.nextInt(maxRange);
            } while (used.contains(newRandom));
            result[i] = newRandom;
            used.add(newRandom);
        }
        return result;
    }
}
