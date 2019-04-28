package com.czj.dao;

import com.czj.vo.User;

import java.util.List;

/**
 * @author czj
 */
public interface UserDao {

    User getUserByUsername(String username);

    List<String> getRolesByUsername(String username);

    List<String> getPermissionsByRole(String role);

}
