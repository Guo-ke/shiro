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
 * Created by guoke on 2017/2/7.
 * 测试shiro一个最简单的认证流程。
 * 总体流程：
 * 通过.ini来获取SecurityManager实例，在调用subject.login方法进行验证。这里默认的
 * shiro默认通过ModularRealmAuthenticator来进行认证
 * ModularRealmAuthenticator默认的认证策略是AtLeastOneSuccessfulStrategy
 */
public class TestAuthentication {

    private static final Logger logger= LoggerFactory.getLogger(TestAuthentication.class);

    @Test
    public void testAuthentication(){
        //1.首先通过.ini文件获取SecutiryManager工厂
        Factory<SecurityManager> securityManagerFactory=new IniSecurityManagerFactory("classpath:shiro.ini");

        //2.获取SecurityManager实例
        SecurityManager securityManager = securityManagerFactory.getInstance();

        //3.在SecurityUtils中绑定SecurityManager。因为shiro是多线程的，所以提供了一个通用的工具类来获取Subject绑定到当前线程，也就是当前的用户，故需要绑定SecurityManager
        SecurityUtils.setSecurityManager(securityManager);

        //4.获取当前登录的用户（Subject）
        Subject subject = SecurityUtils.getSubject();

        //5.设置用户token，简单来讲输入.ini中配置的用户名和密码就可以了。
        UsernamePasswordToken token=new UsernamePasswordToken("lishunxing","123");

        try {
            //6.如果身份验证失败请捕获AuthenticationException或其子类，常见的如： DisabledAccountException（禁用的帐号）、LockedAccountException（锁定的帐号）、UnknownAccountException（错            误的帐号）、ExcessiveAttemptsException（登录失败次数过多）、IncorrectCredentialsException （错误的凭证）、ExpiredCredentialsException（过期的凭证）等，具体请查看其继承关系；对于            页面的错误消息展示，最好使用如“用户名/密码错误”而不是“用户名错误”/“密码错误”，防止一些恶意用户非法扫描帐号库；
            subject.login(token);
        }catch (AuthenticationException ex){
            logger.warn("登录失败==>" + ex.getMessage());
        }
        logger.info("登录成功！");
        //7.退出
        subject.logout();

    }

}
