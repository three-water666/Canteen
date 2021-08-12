package com.wmy.canteen.dao;

import com.wmy.canteen.domain.User;

public interface UserDao {
    User findByUsername(String sno);
    void save(User user);
    User findByCode(String code);
    boolean updateStatus(User user);
    User findBySnoAndPassword(String sno,String password);
}
