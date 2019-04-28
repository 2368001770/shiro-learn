package com.czj.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 自定义 Realm
 * @author czj
 *
 */
public class CustomRealm extends AuthorizingRealm {

    Map<String,String> userMap = new HashMap<String, String>();

    {
        userMap.put("czj","c6c5d724325fe2663808e221f4931b46");
        super.setName("customRealm");
    }

    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        String username = (String) principalCollection.getPrimaryPrincipal();

        Set<String> roles = getRolesByUsername(username);

        Set<String> permissions = getPermissionsByUsername(username);

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setRoles(roles);
        simpleAuthorizationInfo.setStringPermissions(permissions);

        return simpleAuthorizationInfo;
    }

    /**
     * 认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        /**
         * 1、从主体传过来的认证信息中获取用户名
         */
        String username = (String) authenticationToken.getPrincipal();

        /**
         * 2、通过用户名从数据库中获取凭证
         */
        String password = getPasswordByUsername(username);
        if(password == null){
            return null;
        }

        SimpleAuthenticationInfo authenticationInfo =
                new SimpleAuthenticationInfo(username,password,"customRealm");

        /**
         * 设置加密的盐
         */
        authenticationInfo.setCredentialsSalt(ByteSource.Util.bytes("czj"));

        return authenticationInfo;
    }

    private Set<String> getRolesByUsername(String username){

        Set<String> set = new HashSet<String>();

        set.add("admin");
        set.add("user");
        return set;
    }

    private Set<String> getPermissionsByUsername(String username){

        Set<String> set = new HashSet<String>();

        set.add("user:delete");
        set.add("user:update");
        return set;
    }

    private String getPasswordByUsername(String username){
        return userMap.get(username);
    }

    public static void main(String[] args) {
        Md5Hash md5Hash = new Md5Hash("123456","czj");
        System.out.println(md5Hash.toString());
    }
}
