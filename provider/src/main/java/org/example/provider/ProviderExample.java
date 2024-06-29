package org.example.provider;


import org.example.common.service.UserService;
import org.example.registry.LocalRegistry;
import org.example.server.HttpServer;
import org.example.server.VertxHttpServer;

public class ProviderExample {

    /**
     * 提供服务
     * @param args
     */
    public static void main( String[] args ) {
        System.out.println( "This is rpc-provider-example!");

        // 注册服务
        LocalRegistry.register(UserService.class.getName(), UserServiceImpl.class);

        // 启动web服务
        HttpServer httpServer = new VertxHttpServer();
        httpServer.doStart(8080);
    }
}
