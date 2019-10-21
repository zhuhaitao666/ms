package com.mujiwulian.ms.utils;


import org.apache.commons.codec.digest.DigestUtils;

public class MD5Util {

    private static final String salt="1a2b3c";
    //直接加密
    public static  String md5(String src){
        return DigestUtils.md5Hex(src);
    }
    public static String inputPassForm(String password){
        String str= salt+password;
        return DigestUtils.md5Hex(str);
    }
    /**
     两次加密
     第一步防止浏览器获取
     第二步防止数据库破解
    */
    public static String twice(String password){
        String once=md5(password);
        String dbString=inputPassForm(once);
        return dbString;
    }

    public static void main(String[] args) {
        System.out.println(inputPassForm("123456"));
    }

}
