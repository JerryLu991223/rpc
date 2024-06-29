package org.example.server;

import io.vertx.core.Vertx;

public class VertxHttpServer implements HttpServer{
    @Override
    public void doStart(int port) {
        Vertx vertx = Vertx.vertx();

        io.vertx.core.http.HttpServer server = vertx.createHttpServer();

        server.requestHandler(request -> {
            System.out.printf("Received request: " + request.method() + " " + request.uri() + "\n");
            request.response()
                    .putHeader("content-type", "text/plain")
                    .end("Hello from Vert.x Http Server!");
        });

        server.listen(port, result -> {
            if (result.succeeded()) {
                System.out.printf("System is now listening on port: " + port);
            } else {
                System.err.println("Failed to start vertx server: " + result.cause());
            }
        });
    }
}
