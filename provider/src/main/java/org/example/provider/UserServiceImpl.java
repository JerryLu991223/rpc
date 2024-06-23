package org.example.provider;

import org.example.common.model.User;
import org.example.common.service.UserService;

public class UserServiceImpl implements UserService {
    public User getUser(User user) {
        System.out.printf("用户名：" + user.getName());
        return user;
    }
}
