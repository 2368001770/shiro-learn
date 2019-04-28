package com.czj.test;

import com.czj.shiro.realm.CustomRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * 自定义 Realm 测试类
 * @author czj
 */
public class CustomerRealmTest {

    @Test
    public void testAuthentication(){

        CustomRealm customRealm = new CustomRealm();

        //加密
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        //设置加密算法名称
        matcher.setHashAlgorithmName("md5");
        //设置加密次数
        matcher.setHashIterations(1);

        //自定义 Realm 中设置加密对象
        customRealm.setCredentialsMatcher(matcher);

        // 1、构建 SecurityManager 环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(customRealm);

        // 2、主体提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("czj","123456");

        subject.login(token);

        System.out.println("isAuthenticated: " + subject.isAuthenticated());

        subject.checkRole("admin");

        subject.checkPermission("user:delete");

        subject.logout();

        System.out.println("isAuthenticated: " + subject.isAuthenticated());
    }
}
