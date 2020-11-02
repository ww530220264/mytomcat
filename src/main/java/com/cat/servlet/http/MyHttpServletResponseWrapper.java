package com.cat.servlet.http;


import com.cat.servlet.MyServletResponseWrapper;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

public class MyHttpServletResponseWrapper extends MyServletResponseWrapper implements HttpServletResponse {

    private static final int SC_CONTINUE = 100;// 客户端应当继续发送请求
    private static final int SC_SWITCHING_PROTOCOLS = 101;// 切换新协议
    private static final int SC_OK = 200;// 请求已成功
    private static final int SC_CREATED = 201;// 请求已被实现,且有一个新的资源已经依据请求的需要而建立,且其URI已经随Location头信息返回
    private static final int SC_ACCEPTED = 202;// 请求已被接受,但尚未处理
    private static final int SC_NON_AUTHORITATIVE_INFORMATION = 203;// 服务器已经成功处理了请求
    private static final int SC_NO_CONTENT = 204;//
    private static final int SC_RESET_CONTENT = 205;
    private static final int SC_PARTIAL_CONTENT = 206;
    private static final int SC_MULTIPLE_CHOICES = 300;
    private static final int SC_MOVED_PERMANENTLY = 301;
    private static final int SC_MOVED_TEMPORARILY = 302;
    private static final int SC_FOUND = 302;
    private static final int SC_SEE_OTHER = 303;
    private static final int SC_NOT_MODIFIED = 304;
    private static final int SC_USE_PROXY = 305;
    private static final int SC_TEMPORARY_REDIRECT = 307;
    private static final int SC_BAD_REQUEST = 400;
    private static final int SC_UNAUTHORIZED = 401;
    private static final int SC_PAYMENT_REQUIRED = 402;
    private static final int SC_FORBIDDEN = 403;
    private static final int SC_NOT_FOUND = 404;
    private static final int SC_METHOD_NOT_ALLOWED = 405;
    private static final int SC_NOT_ACCEPTABLE = 406;
    private static final int SC_PROXY_AUTHENTICATION_REQUIRED = 407;
    private static final int SC_REQUEST_TIMEOUT = 408;
    private static final int SC_CONFLICT = 409;
    private static final int SC_GONE = 410;
    private static final int SC_LENGTH_REQUIRED = 411;
    private static final int SC_PRECONDITION_FAILED = 412;
    private static final int SC_REQUEST_ENTITY_TOO_LARGE = 413;
    private static final int SC_REQUEST_URI_TOO_LONG = 414;
    private static final int SC_UNSUPPORTED_MEDIA_TYPE = 415;
    private static final int SC_REQUESTED_RANGE_NOT_SATISFIABLE = 416;
    private static final int SC_EXPECTATION_FAILED = 417;
    private static final int SC_INTERNAL_SERVER_ERROR = 500;
    private static final int SC_NOT_IMPLEMENTED = 501;
    private static final int SC_BAD_GATEWAY = 502;
    private static final int SC_SERVICE_UNAVAILABLE = 503;
    private static final int SC_GATEWAY_TIMEOUT = 504;
    private static final int SC_HTTP_VERSION_NOT_SUPPORTED = 505;

    public MyHttpServletResponseWrapper(HttpServletResponse response) {
        super(response);
    }

    public HttpServletResponse _getHttpServletResponse() {
        return (HttpServletResponse) super.getResponse();
    }

    @Override
    public void addCookie(Cookie cookie) {
        this._getHttpServletResponse().addCookie(cookie);
    }

    @Override
    public boolean containsHeader(String name) {
        return this._getHttpServletResponse().containsHeader(name);
    }

    @Override
    public String encodeURL(String url) {
        return this._getHttpServletResponse().encodeURL(url);
    }

    @Override
    public String encodeRedirectURL(String url) {
        return this._getHttpServletResponse().encodeRedirectURL(url);
    }

    @Override
    public String encodeUrl(String url) {
        return this._getHttpServletResponse().encodeUrl(url);
    }

    @Override
    public String encodeRedirectUrl(String url) {
        return this._getHttpServletResponse().encodeRedirectUrl(url);
    }

    @Override
    public void sendError(int sc, String msg) throws IOException {
        this._getHttpServletResponse().sendError(sc, msg);
    }

    @Override
    public void sendError(int sc) throws IOException {
        this._getHttpServletResponse().sendError(sc);
    }

    @Override
    public void sendRedirect(String location) throws IOException {
        this._getHttpServletResponse().sendRedirect(location);
    }

    @Override
    public void setDateHeader(String name, long date) {
        this._getHttpServletResponse().setDateHeader(name, date);
    }

    @Override
    public void addDateHeader(String name, long date) {
        this._getHttpServletResponse().addDateHeader(name, date);
    }

    @Override
    public void setHeader(String name, String value) {
        this._getHttpServletResponse().setHeader(name, value);
    }

    @Override
    public void addHeader(String name, String value) {
        this._getHttpServletResponse().addHeader(name, value);
    }

    @Override
    public void setIntHeader(String name, int value) {
        this._getHttpServletResponse().setIntHeader(name, value);
    }

    @Override
    public void addIntHeader(String name, int value) {
        this._getHttpServletResponse().addIntHeader(name, value);
    }

    @Override
    public void setStatus(int sc) {
        this._getHttpServletResponse().setStatus(sc);
    }

    @Override
    public void setStatus(int sc, String sm) {
        this._getHttpServletResponse().setStatus(sc, sm);
    }

    @Override
    public int getStatus() {
        return this._getHttpServletResponse().getStatus();
    }

    @Override
    public String getHeader(String name) {
        return this._getHttpServletResponse().getHeader(name);
    }

    @Override
    public Collection<String> getHeaders(String name) {
        return this._getHttpServletResponse().getHeaders(name);
    }

    @Override
    public Collection<String> getHeaderNames() {
        return this._getHttpServletResponse().getHeaderNames();
    }
}
