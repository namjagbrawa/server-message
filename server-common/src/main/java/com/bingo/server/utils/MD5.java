package com.bingo.server.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by ZhangGe on 2017/7/14.
 */
public class MD5 {

    private static Logger logger = LoggerFactory.getLogger(MD5.class);

    public static String str2MD5(String str) {
        //创建一个提供信息摘要算法的对象，初始化为md5算法对象
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
            //计算后获得字节数组
            byte[] buff = md.digest(str.getBytes());
            //把数组每一字节换成16进制连成md5字符串
            return bytesToHex(buff);
        } catch (NoSuchAlgorithmException e) {
            logger.error("MD5解析错误，找不到MD5解析类");
            throw new RuntimeException("找不到MD5解析类");
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuffer md5str = new StringBuffer();
        //把数组每一字节换成16进制连成md5字符串
        int digital;
        for (int i = 0; i < bytes.length; i++) {
            digital = bytes[i];
            if (digital < 0) {
                digital += 256;
            }
            if (digital < 16) {
                md5str.append("0");
            }
            md5str.append(Integer.toHexString(digital));
        }
        return md5str.toString();
    }
}
