package com.example.demo.enums;

public enum DateTimePattern {
    COIN_DESK_UPDATED("MMM dd, yyyy HH:mm:ss z"),
    RETURN_DATETIME("yyyy/MM/dd HH:mm:ss");

    private String pattern;

    DateTimePattern(String pattern) {
        this.pattern = pattern;
    }

    public String getPattern() {
        return pattern;
    }
}
