package com.cat.servlet.http;

import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.ResourceBundle;

public class MyHttpMethod {

    private static final String LSTRING_FILE = "com.mytomcat.servlet.http.LocalStrings";
    private static final ResourceBundle LStrings = ResourceBundle.getBundle(LSTRING_FILE);

    private static final String GET = "GET";
    private static final String POST = "POST";
    private static final String PUT = "PUT";
    private static final String DELETE = "DELETE";
    private static final String HEAD = "HEAD";
    private static final String TRACE = "TRACE";
    private static final String CONNECT = "CONNECT";
    private static final String OPTION = "OPTION";

    public static void main(String[] args) {
        String msg = new String(LStrings.getString("err.test").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        System.out.println(MessageFormat.format(msg,"请联系管理员"));
    }
}
