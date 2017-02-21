package com.github.lishunxing.common;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

/**
 * Created by guoke on 2017/2/14.
 */
public class ShiroUtils {

    public static Subject getSubject(String iniName,String username,String password){
        Factory<SecurityManager> iniSecurityManagerFactory = new IniSecurityManagerFactory(iniName);
        SecurityManager securityManager=iniSecurityManagerFactory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token=new UsernamePasswordToken(username,password);

        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }
        return subject;
    }
}
