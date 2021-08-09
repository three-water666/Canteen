package com.wmy.canteen.dao;

import com.wmy.canteen.domain.User;

public interface UserDao {
    User findByUsername(String sno);
    void save(User user);
}
