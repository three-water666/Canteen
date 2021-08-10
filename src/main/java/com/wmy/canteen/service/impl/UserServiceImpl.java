package com.wmy.canteen.service.impl;

import com.wmy.canteen.dao.UserDao;
import com.wmy.canteen.dao.impl.UserDaoImpl;
import com.wmy.canteen.domain.User;
import com.wmy.canteen.service.UserService;
import com.wmy.canteen.util.MailUtils;
import com.wmy.canteen.util.UuidUtil;

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
        //设置编码，以及激活状态
        user.setCode(UuidUtil.getUuid());
        user.setStatus("N");
        //3.如果不存在,保存用户
        userDao.save(user);

        //发送邮件激活
        String content="<a href='http://localhost/yuncanteen/activeUserServlet?code="+user.getCode()+"'>点击激活【工大食堂账号】</a>";
        MailUtils.sendMail(user.getEmail(),content,"激活邮件");
        return true;
    }

    @Override
    public boolean active(String code) {
        //激活账户，将账户状态设置为Y
        //1.按照code查找用户
        User user=userDao.findByCode(code);
        if(user==null){
            return false;
        }
        //2.激活账户
        userDao.updateStatus(user);
        return true;
    }
}
