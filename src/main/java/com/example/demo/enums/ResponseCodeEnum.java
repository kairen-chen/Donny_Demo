package com.example.demo.enums;

public enum ResponseCodeEnum {
    success("100"), fail("900");

    private String code;

    ResponseCodeEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
