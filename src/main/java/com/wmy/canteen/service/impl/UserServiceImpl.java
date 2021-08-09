package com.wmy.canteen.service.impl;

import com.wmy.canteen.dao.UserDao;
import com.wmy.canteen.dao.impl.UserDaoImpl;
import com.wmy.canteen.domain.User;
import com.wmy.canteen.service.UserService;

public class UserServiceImpl implements UserService {
    private UserDao userDao=new UserDaoImpl();

    @Override
    public boolean regist(User user) {
        //1.根据用户名查找用户
        User u=userDao.findByUsername(user.getSno());
        //2.如果存在,返回用户已存在
        if(u!=null){
            return false;
        }
        //3.如果不存在,保存用户
        userDao.save(user);
        return true;
    }
}
