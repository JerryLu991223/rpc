package org.example.registry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 本地服务注册，后面会用第三方服务注册中心.
 * 用一个map来存储，key是服务名称，value是服务的实现类.
 * 之后调用的服务就可以根据服务名称来得到对应的实现类，用反射来进行方法调用.
 */
public class LocalRegistry {
    private static final Map<String, Class<?>> map = new ConcurrentHashMap<>();

    public static void register(String serviceName, Class<?> implClass) {
        map.put(serviceName, implClass);
    }

    public static Class<?> get(String serviceName) {
        return map.get(serviceName);
    }

    public static void remove(String serviceName) {
        map.remove(serviceName);
    }
}
