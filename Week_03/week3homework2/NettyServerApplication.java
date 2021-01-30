package com.hymanting.geekjavatrain.socket.week3homework2;

import com.hymanting.geekjavatrain.socket.week3homework2.inbound.HttpInboundServer;

import java.util.Arrays;

public class NettyServerApplication {
    
    public final static String GATEWAY_NAME = "NIOGateway";
    public final static String GATEWAY_VERSION = "2.0.0";
    
    public static void main(String[] args) {

        //网关port
        String proxyPort = System.getProperty("proxyPort","8888");

        String proxyServers = System.getProperty("proxyServers","http://localhost:8801");
        int port = Integer.parseInt(proxyPort);
        System.out.println(GATEWAY_NAME + " " + GATEWAY_VERSION +" starting...");
        HttpInboundServer server = new HttpInboundServer(port, Arrays.asList(proxyServers.split(",")));
        System.out.println(GATEWAY_NAME + " " + GATEWAY_VERSION +" started at http://localhost:" + port + " for server:" + server.toString());
        try {
            server.run();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
