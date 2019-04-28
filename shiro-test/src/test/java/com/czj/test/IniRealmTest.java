package com.czj.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * IinRealm 测试类
 * @author czj
 *
 */
public class IniRealmTest {

    IniRealm iniRealm = new IniRealm("classpath:user.ini");

    @Test
    public void testAuthentication(){

        // 1、构建 SecurityManager 环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(iniRealm);

        // 2、主体提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("czj","123456");
        subject.login(token);

        System.out.println("isAuthenticated: " + subject.isAuthenticated() );

        //检查用户是否有 admin 角色
        subject.checkRoles("admin");
        System.out.println(subject.hasRole("admin"));

        //检查用户是否有 user:delete 和 user:update 权限
        subject.checkPermissions("user:delete","user:update");

        //退出认证
        subject.logout();

        System.out.println("isAuthenticated: " + subject.isAuthenticated());
    }
}
