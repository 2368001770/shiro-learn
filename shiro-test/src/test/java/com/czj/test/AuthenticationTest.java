package com.czj.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

/**
 * 测试类
 * @author czj
 */
public class AuthenticationTest {

    SimpleAccountRealm simpleAccountRealm = new SimpleAccountRealm();

    @Before
    public void addUser(){
        simpleAccountRealm.addAccount("czj","123456","admin","user");
    }

    @Test
    public void testAuthentication(){

        // 1、构建 SecurityManager 环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(simpleAccountRealm);

        // 2、主体提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("czj","123456");
        subject.login(token);

        System.out.println("isAuthenticated: " + subject.isAuthenticated() );

        //检查用户是否有 admin 和 user 角色
        subject.checkRoles("admin","user");
        System.out.println(subject.hasRole("admin"));

        //退出认证
        subject.logout();

        System.out.println("isAuthenticated: " + subject.isAuthenticated());
    }
}
