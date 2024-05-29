package com.staxrt.tutorial.enums;

public enum AnswerType {
    SINGLE_CHOICE("Single Choice"),
    MULTIPLE_CHOICE("Multiple Choice"),
    SORT("Sort");

    public final String label;

    private AnswerType(String label) {
        this.label = label;
    }
}