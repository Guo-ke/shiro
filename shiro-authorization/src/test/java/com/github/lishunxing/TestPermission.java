package com.github.lishunxing;

import com.github.lishunxing.common.ShiroUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.permission.WildcardPermission;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * Created by guoke on 2017/2/14.
 * 关于权限匹配，isPermitted方法可以检测改用户所包含的角色是否具有对应的权限
 * 默认需要传入一个Permission对象，以字符串传入的具体实现对象为WildcardPermission
 * 关于权限验证符的匹配方式为前缀匹配
 * user == user.* == user.*.*
 * 但是类似于 *:delete 可以匹配 user:delete source:delete 但是不匹配 user:source:delete 如想匹配要写为 *:*:delete
 *
 * isPermitted为判断1一个权限
 * isPermittedAll为判断多个权限，所有都有返回TRUE
 * checkPermitted为true不返回，false抛出异常
 */
public class TestPermission {

    @Test
    public void testSuccessPermission(){
        Subject subject= ShiroUtils.getSubject("classpath:shiro-permission.ini","lishunxing","123");
        if(subject.isPermitted("user:update")) System.out.println("user lishunxing has permission user:update");
        if(subject.isPermitted("user:update:1")) System.out.println("user lishunxing has permission user:update:1");
        if(subject.isPermitted("user:insert:1")) System.out.println("user lishunxing has permission user:insert:1");
        subject.logout();
    }

    @Test
    public void testFailPermission(){
        Subject subject= ShiroUtils.getSubject("classpath:shiro-permission.ini","guoke","456");
        if(subject.isPermitted("user:update")) System.out.println("user guoke has permission user:update");
        if(subject.isPermitted("user:update:1")) System.out.println("user guoke has permission user:update:1");
        if(subject.isPermitted(new WildcardPermission("user:insert:1"))) System.out.println("user guoke has permission user:insert:1");
        subject.logout();
    }

    @Test
    public void testSuccessPermissionAll(){
        Subject subject= ShiroUtils.getSubject("classpath:shiro-permission.ini","lishunxing","123");
        System.out.println(subject.isPermittedAll("user:update","user:delete"));
        subject.logout();
    }

    @Test
    public void testCheckPermitted(){
        Subject subject= ShiroUtils.getSubject("classpath:shiro-permission.ini","lishunxing","123");
        try {
            subject.checkPermission("user:update");
            subject.checkPermission("company:update");
        } catch (AuthorizationException e) {
            System.out.println(e.getMessage());
        }
        subject.logout();
    }

}
