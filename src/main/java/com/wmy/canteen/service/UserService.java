package com.wmy.canteen.service;

import com.wmy.canteen.domain.User;

public interface UserService {
    boolean regist(User user);
    boolean active(String code);
    User login(User user);
}
