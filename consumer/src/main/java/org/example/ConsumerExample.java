package org.example;

import org.example.common.model.User;
import org.example.common.service.UserService;

public class ConsumerExample {

    public static void main(String[] args) {
        UserService userService = null;
        User user = new User();
        user.setName("zhenghao.lu");
        // 调用
        User newUser = userService.getUser(user);
        if (newUser == null) {
            System.out.printf("user is null!");
        } else {
            System.out.printf(newUser.getName());
        }
    }
}
