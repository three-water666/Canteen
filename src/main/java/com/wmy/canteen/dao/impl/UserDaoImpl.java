package com.wmy.canteen.dao.impl;

import com.wmy.canteen.dao.UserDao;
import com.wmy.canteen.domain.User;
import com.wmy.canteen.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDaoImpl implements UserDao {
    JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public User findByUsername(String sno) {
        User user=null;
        try {
            String sql="select * from student where sno=?";
            user=template.queryForObject(sql,new BeanPropertyRowMapper<User>(User.class),sno);
        } catch (Exception e) {

        }
        return user;
    }

    @Override
    public void save(User user) {
        String sql="insert into student(sno,password,email,status,code) values(?,?,?,?,?)";
        template.update(sql,user.getSno(),user.getPassword(),user.getEmail(),user.getStatus(),user.getCode());
    }

    @Override
    public User findByCode(String code) {
        User user=null;
        try {
            String sql="select * from student where code=?";
            user=template.queryForObject(sql,new BeanPropertyRowMapper<User>(User.class),code);
        } catch (DataAccessException e) {

        }
        return user;
    }

    @Override
    public boolean updateStatus(User user) {
        String sql="update student set status='Y' where code=?";
        template.update(sql,user.getCode());
        return true;
    }

    @Override
    public User findBySnoAndPassword(String sno, String password) {
        User user=null;
        try {
            String sql="select * from student where sno=? and password=?";
            user=template.queryForObject(sql,new BeanPropertyRowMapper<User>(User.class),sno,password);
        } catch (DataAccessException e) {

        }
        return user;
    }
}
