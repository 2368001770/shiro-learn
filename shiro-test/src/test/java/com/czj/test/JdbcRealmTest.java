package com.czj.test;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * JdbcRealm 测试类
 * @author czj
 */
public class JdbcRealmTest {

    DruidDataSource druidDataSource = new DruidDataSource();

    {
        druidDataSource.setUrl("jdbc:mysql://localhost:3306/test_shiro");
        druidDataSource.setUsername("root");
        druidDataSource.setPassword("root");
    }

    @Test
    public void testAuthentication(){

        JdbcRealm jdbcRealm = new JdbcRealm();
        jdbcRealm.setDataSource(druidDataSource);
        // 查询权限数据，默认为 false
        jdbcRealm.setPermissionsLookupEnabled(true);

       /*
        //自定义表和查询语句
        String sql = "select password from test_users where username = ?";
        jdbcRealm.setAuthenticationQuery(sql);

        String roleSql = "select role_name from test_user_roles where username = ?";
        jdbcRealm.setUserRolesQuery(roleSql);

        String permissionSql = "select permission from test_roles_permissions where role_name = ?";
        jdbcRealm.setPermissionsQuery(permissionSql);
        */

        // 1、构建 SecurityManager 环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(jdbcRealm);

        // 2、主体提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("czj","123456");
        subject.login(token);

        System.out.println("isAuthenticated: " + subject.isAuthenticated() );

        //检查用户是否有 admin 和 user 角色
        subject.checkRoles("admin","user");
        System.out.println(subject.hasRole("admin"));

        //检查用户是否有 user:delete 权限
        subject.checkPermission("user:delete");

        //退出认证
        subject.logout();

        System.out.println("isAuthenticated: " + subject.isAuthenticated());

    }
}
