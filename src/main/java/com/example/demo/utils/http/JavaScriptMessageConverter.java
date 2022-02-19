package com.example.demo.utils.http;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.nio.charset.Charset;

public class JavaScriptMessageConverter extends AbstractHttpMessageConverter {
    public JavaScriptMessageConverter() {
        super(new MediaType("application", "javascript", Charset.forName("UTF-8")));
    }

    @Override
    protected boolean supports(Class clazz) {
        return false;
    }

    @Override
    protected Object readInternal(Class clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        return null;
    }

    @Override
    protected void writeInternal(Object o, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {

    }
}
