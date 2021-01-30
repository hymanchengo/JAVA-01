package com.hymanting.geekjavatrain.socket.week3homework2.filter;

import io.netty.handler.codec.http.FullHttpResponse;

import java.util.UUID;

public class HeaderHttpResponseFilter implements HttpResponseFilter {
    @Override
    public void filter(FullHttpResponse response) {
        response.headers().set("reqId", UUID.randomUUID().toString());
    }
}
