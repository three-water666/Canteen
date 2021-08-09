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
        String sql="insert into student(sno,password,email) values(?,?,?)";
        template.update(sql,user.getSno(),user.getPassword(),user.getEmail());
    }
}
