package com.github.lishunxing;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by guoke on 2017/2/9.
 */
public class TestMyRealm {

    private static final Logger logger= LoggerFactory.getLogger(TestMyRealm.class);

    @Test
    public void testMyRealm(){
        Factory<SecurityManager> securityManagerFactory=new IniSecurityManagerFactory("classpath:shiro-myrealm.ini");

        SecurityManager securityManager = securityManagerFactory.getInstance();

        SecurityUtils.setSecurityManager(securityManager);

        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token=new UsernamePasswordToken("lishunxing","123");

        try {
            subject.login(token);
        }catch (AuthenticationException ex){
            logger.warn("登录失败==>" + ex.getMessage());
            return;
        }
        logger.info("登录成功！");
        //7.退出
        subject.logout();
    }
}
