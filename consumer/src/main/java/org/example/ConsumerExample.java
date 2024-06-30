package org.example;

import org.example.common.model.User;
import org.example.common.service.UserService;
import org.example.proxy.ServiceProxyFactory;

public class ConsumerExample {

    public static void main(String[] args) {
        /*
        // 静态代理，只是写个test case，实际使用动态代理，更加灵活方便
        UserService staticProxyUserService = new UserServiceProxy();
        staticProxyUserService.getUser(user);
         */

        // 动态代理
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
        User user = new User();
        user.setName("zhenghao.lu");
        System.out.println(userService.getUser(user));
    }
}
