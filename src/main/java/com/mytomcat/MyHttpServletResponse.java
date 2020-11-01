package com.mytomcat;

import lombok.Data;

import java.io.OutputStream;

@Data
public class MyHttpServletResponse {
    private String protocal;
    private String version;
    private String statusCode;
    private String statusName;
    private OutputStream outputStream;

    public MyHttpServletResponse(OutputStream outputStream) {
        this.outputStream = outputStream;
    }
}
