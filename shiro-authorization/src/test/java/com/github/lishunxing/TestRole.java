package com.github.lishunxing;

import com.github.lishunxing.common.ShiroUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

import java.util.Arrays;

/**
 * Created by guoke on 2017/2/14.
 */
public class TestRole {

    /**
     * 测试hasRole，如果没有对应角色就返回false
     */
    @Test
    public void testHasRole(){
        Subject subject= ShiroUtils.getSubject("classpath:shiro-role.ini","lishunxing","123456");
        if(subject.hasRole("role1")) System.out.println("lishunxing hash role ==> role1");
        if(subject.hasRole("role2")) System.out.println("lishunxing hash role ==> role2");
        if(subject.hasRole("role3")) System.out.println("lishunxing hash role ==> role3");
    }

    /**
     * 测试hasRoles，返回boolean数组
     */
    @Test
    public void testHasRoleArray(){
        Subject subject= ShiroUtils.getSubject("classpath:shiro-role.ini","lishunxing","123456");
        boolean[] booleen = subject.hasRoles(Arrays.asList("role1", "role2", "role3"));
        for (int i = 0; i < booleen.length; i++) {
            if(booleen[i])  System.out.println("lishunxing hash role ==> role"+(i+1));
        }
    }

    /**
     * 测试checkRole，如没有此角色会报错
     */
    @Test
    public void testCheckRole(){
        Subject subject= ShiroUtils.getSubject("classpath:shiro-role.ini","lishunxing","123456");
        subject.checkRole("role1");
    }

    /**
     * 测试checkRoles，可以传入多个String参数
     */
    @Test
    public void testCheckRoleArray(){
        Subject subject= ShiroUtils.getSubject("classpath:shiro-role.ini","lishunxing","123456");
        try {
            subject.checkRoles("role1","role3");
        } catch (AuthorizationException e) {
            e.printStackTrace();
        }
    }
}
