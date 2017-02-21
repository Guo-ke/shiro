package com.github.lishunxing.Realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.realm.Realm;

/**
 * Created by guoke on 2017/2/9.
 */
public class MyRealm implements Realm {
    public String getName() {
        return "myRealm";
    }

    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }

    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        SimpleAuthenticationInfo info=null;
        if(!"lishunxing".equals(token.getPrincipal().toString())){
            throw new UnknownAccountException("用户名错误");
        }

        if(!"123".equals(new String((char[])token.getCredentials()))){
            throw new IncorrectCredentialsException("密码错误");
        }

        return new SimpleAuthenticationInfo(token.getPrincipal(),token.getCredentials(),getName());
    }
}
