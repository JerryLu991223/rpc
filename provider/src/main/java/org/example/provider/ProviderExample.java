package org.example.provider;


import org.example.server.HttpServer;
import org.example.server.VertxHttpServer;

public class ProviderExample {

    /**
     * 提供服务
     * @param args
     */
    public static void main( String[] args ) {
        System.out.println( "This is rpc-provider-example!");
        HttpServer httpServer = new VertxHttpServer();
        httpServer.doStart(8080);
    }
}
