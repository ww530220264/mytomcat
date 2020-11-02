package com.cat.servlet.http;

import lombok.Data;

import java.io.IOException;
import java.io.InputStream;
import java.net.ProtocolException;
import java.util.HashMap;
import java.util.Map;

@Data
public class MyHttpServletRequest {

    private String method;
    private String uri;
    private String protocal;
    private String version;
    private InputStream inputStream;
    private Map<String, String> headers = new HashMap<>();
    private String body;
    private Map<String, String> queryParams = new HashMap<>();

    public MyHttpServletRequest(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public MyHttpServletRequest parse() throws IOException {
        byte[] bytes = new byte[this.inputStream.available()];
        inputStream.read(bytes);
        String requestString = new String(bytes, "UTF-8");
        if (requestString.length() < 1) {
            return this;
        }
        int requestLineIndex = requestString.indexOf("\r\n");
        if (requestLineIndex < 0) {
            throw new ProtocolException("HTTP请求行格式错误!");
        }
        String requestLine = requestString.substring(0, requestLineIndex);
        parseRequestLine(requestLine);
        String headerAndBody = requestString.substring(requestLineIndex + 1);
        String[] hbs = headerAndBody.split("\r\n\r\n");
        if (hbs.length > 0) {
            parseRequestHeaders(hbs[0]);
        }
        if (hbs.length > 1) {
            parseRequestBody(hbs[1]);
        }
        return this;
    }

    private MyHttpServletRequest parseRequestBody(String body) {
        if ("application/json".equals(this.headers.get("Content-Type"))) {
            this.body = body.replaceAll("[\r\t\n]", "");
        }
        return this;
    }

    private MyHttpServletRequest parseRequestHeaders(String headers) throws ProtocolException {
        String[] splits = headers.split("\r\n");
        if (splits.length > 0) {
            String headerName;
            String headerValue;
            for (String split : splits) {
                String[] kv = split.split(": ");
                if (kv.length == 2) {
                    headerName = kv[0].replaceAll("[\r\n\t]", "");
                    if (headerName.length() < 1) {
                        throw new ProtocolException("the header name's length < 0!");
                    }
                    headerValue = kv[1].replaceAll("[\r\n\t\\s]", "");
                    if (headerValue.length() < 1) {
                        throw new ProtocolException("the header value's length < 0!");
                    }
                    this.headers.put(headerName, headerValue);
                } else {
                    throw new ProtocolException("the format of the header is illegal!");
                }
            }
        } else {
            System.err.println("there is no header");
        }
        return this;
    }

    private MyHttpServletRequest parseRequestLine(String requestLine) throws ProtocolException {
        String[] splits = requestLine.split(" ");
        if (splits.length < 3) {
            throw new ProtocolException("HTTP请求行格式错误!");
        }
        this.method = splits[0];
        String url;
        if ((url = splits[1]).startsWith("/")) {
            String[] uriAndParams = url.split("\\?");
            if (uriAndParams.length == 1) {
                this.uri = url;
            } else if (uriAndParams.length == 2) {
                this.uri = uriAndParams[0];
                String paramStr = uriAndParams[1];
                if (paramStr.length() > 0 && paramStr.indexOf("=") > 0) {
                    String[] params = paramStr.split("&");
                    if (params.length == 1) {
                        String[] kv;
                        if ((kv = paramStr.split("=")).length == 2) {
                            queryParams.put(kv[0], kv[1]);
                        }
                    } else {
                        for (String param : params) {
                            String[] kv;
                            if ((kv = param.split("=")).length == 2) {
                                queryParams.put(kv[0], kv[1]);
                            }
                        }
                    }
                }
            }
        }
        String protocalAndVersion = splits[2];
        if (protocalAndVersion.indexOf("/") < 0) {
            throw new ProtocolException("HTTP请求行格式错误!");
        }
        String[] pvs = protocalAndVersion.split("/");
        this.protocal = pvs[0];
        this.version = pvs[1];

        return this;
    }


}
