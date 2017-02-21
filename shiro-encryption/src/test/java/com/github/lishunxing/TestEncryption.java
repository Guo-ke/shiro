package com.github.lishunxing;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.AesCipherService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.Assert;
import org.junit.Test;

import java.security.Key;

/**
 * Created by guoke on 2017/2/16.
 * shiro的编码解码
 * shiro的加密解密
 * 加密解密可以通过制定一个salt（盐值），防止密码碰撞从而破解
 */
public class TestEncryption {

    @Test
    public void testBase64(){
        String str1="hello";
        String encodeBase64= Base64.encodeToString(str1.getBytes());
        System.out.println(encodeBase64);
        String decodeBase64=Base64.decodeToString(encodeBase64.getBytes());
        System.out.println(decodeBase64);
        Assert.assertEquals(str1,decodeBase64);
    }

    @Test
    public void testHEX(){
        String str1="hello";
        String encodeHex= Hex.encodeToString(str1.getBytes());
        System.out.println(encodeHex);
        String decodeHex=new String(Hex.decode(encodeHex.getBytes()));
        System.out.println(decodeHex);
        Assert.assertEquals(str1,decodeHex);
    }

    /**
     * Shiro提供了类似MD5,SHA256,SHA512等加密方式
     * 可以自己提供一个盐进行加密，也可以指定加密次数
     */
    @Test
    public void testEncryption(){
        String str1="hello";
        String salt="guoke";
        String md5=new Md5Hash(str1,salt).toString();
        System.out.println(md5);
        String iterationMd5=new Md5Hash(str1,salt,2).toString();
        System.out.println(iterationMd5);
        String sha=new Sha256Hash(str1,salt).toString();
        System.out.println(sha);
    }

    /**
     * Shiro还提供了一个通用的方法进行加密
     */
    @Test
    public void testHash(){
        String str1="hello";
        String salt="guoke";
        String md51=new Md5Hash(str1,salt).toString();
        String md52=new SimpleHash("MD5",str1,salt).toString();
        Assert.assertEquals(md51,md52);
    }

    /**
     * AES对称加密
     */
    @Test
    public void testAES(){
        String str1="hello";
        AesCipherService aesCipherService=new AesCipherService();
        aesCipherService.setKeySize(128);
        Key key = aesCipherService.generateNewKey();
        //加密
        String encrypt=aesCipherService.encrypt(str1.getBytes(),key.getEncoded()).toHex();
        System.out.println(encrypt);
        //解密
        String decrypt=new String(aesCipherService.decrypt(Hex.decode(encrypt),key.getEncoded()).getBytes());
        Assert.assertEquals(str1,decrypt);


    }



}
