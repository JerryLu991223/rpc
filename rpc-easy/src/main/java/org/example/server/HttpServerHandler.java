package org.example.server;

import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import org.example.model.RpcRequest;
import org.example.model.RpcResponse;
import org.example.registry.LocalRegistry;
import org.example.serializer.JdkSerializer;
import org.example.serializer.Serializer;

import java.io.IOException;
import java.lang.reflect.Method;

/**
 *  HTTP请求处理 -> 业务流程:
 *      1. 反序列化请求为对象，并从请求对象中获得参数.
 *      2. 根据服务名称从本地注册器中获取到对应的服务实现类.
 *      3. 通过反射机制调用方法，得到返回结果.
 *      4. 对返回结果进行封装和序列化，并写入到响应中.
 */
public class HttpServerHandler implements Handler<HttpServerRequest> {

    @Override
    public void handle(HttpServerRequest request) {
        final Serializer serializer = new JdkSerializer();

        System.out.printf("Received request: " + request.method() + " " + request.uri() + "\n");

        // 异步处理 HTTP 请求
        request.bodyHandler(body -> {
           byte[] bytes = body.getBytes();
            RpcRequest rpcRequest = null;
            try {
                rpcRequest = serializer.deserialize(bytes, RpcRequest.class);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // 构造响应结果的对象
            RpcResponse rpcResponse = new RpcResponse();
            if (rpcRequest == null) {
                rpcResponse.setMessage("rpcRequest is null!");
                doResponse(request, rpcResponse, serializer);
                return;
            }

            try {
                // 通过反射调用服务的实现类
                Class<?> implClass = LocalRegistry.get(rpcRequest.getServiceName());
                Method method = implClass.getMethod(rpcRequest.getMethodName(), rpcRequest.getParameterTypes());
                Object result = method.invoke(implClass.getDeclaredConstructor().newInstance(), (Object) rpcRequest.getParameterTypes());
                rpcResponse.setData(result);
                rpcResponse.setDataType(method.getReturnType());
                rpcResponse.setMessage("Ok!");
            } catch (Exception e) {
                e.printStackTrace();
                rpcResponse.setMessage(e.getMessage());
                rpcResponse.setException(e);
            }

            doResponse(request, rpcResponse, serializer);
        });
    }

    private void doResponse(HttpServerRequest request, RpcResponse rpcResponse, Serializer serializer) {
        HttpServerResponse httpServerResponse = request.response().putHeader("content-type", "application/json");
        try {
            byte[] serialized = serializer.serialize(rpcResponse);
            httpServerResponse.end(Buffer.buffer(serialized));
        } catch (IOException e) {
            e.printStackTrace();
            httpServerResponse.end(Buffer.buffer());
        }
    }
}
