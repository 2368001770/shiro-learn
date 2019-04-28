package com.czj.dao.impl;

import com.czj.dao.UserDao;
import com.czj.vo.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

/**
 * @author czj
 */
@Component
public class UserDaoImpl implements UserDao {

    @Resource
    private JdbcTemplate jdbcTemplate;

    public User getUserByUsername(String username) {

        System.out.println("从数据库中获取当前用户");
        String sql = "select username,password from users where username = ?";
        List<User> list = jdbcTemplate.query(sql,new String[]{ username },new RowMapper<User>(){
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
                User user = new User();
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                return user;
            }
        });

        if(CollectionUtils.isEmpty(list)){
            return null;
        }

        return list.get(0);
    }

    public List<String> getRolesByUsername(String username) {

        System.out.println("从数据库中获取角色数据");
        String sql = "select role_name from user_roles where username = ?";
        return jdbcTemplate.query(sql, new String[]{username}, new RowMapper<String>() {
            public String mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getString("role_name");
            }
        });
    }

    public List<String> getPermissionsByRole(String role) {

        System.out.println("从数据库中获取权限数据");
        String sql = "select permission from roles_permissions where role_name = ?";
        return jdbcTemplate.query(sql, new String[]{role}, new RowMapper<String>() {
            public String mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getString("permission");
            }
        });
    }
}
