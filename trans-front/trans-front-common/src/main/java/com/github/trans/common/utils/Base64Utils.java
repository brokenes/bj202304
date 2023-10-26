package com.github.trans.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.Base64;



/**
 *
 * BASE64编码解码工具包
 *
 */

@Slf4j
public class Base64Utils {


    /**
     * 文件读取缓冲区大小
     */
    private static final int CACHE_SIZE = 1024;

    /**
     *
     * BASE64字符串解码为二进制数据
     * @param base64
     * @return
     * @throws Exception
     */
    public static byte[] decode(String base64)  {
        try {
            return Base64.getDecoder().decode(base64.getBytes());
        }catch (Exception e){
            log.error("BASE64字符串解码为二进制数据异常",e);
        }
        return null;
    }

    /**
     *
     * 二进制数据编码为BASE64字符串
     * @param bytes
     * @return
     * @throws Exception
     */
    public static String encode(byte[] bytes) {
        try {
            return new String(Base64.getEncoder().encodeToString(bytes));
        }catch (Exception e){
            log.error("二进制数据编码为BASE64字符串异常",e);
        }
        return null;
    }

    /**
     *
     * 将文件编码为BASE64字符串
     * 大文件慎用，可能会导致内存溢出
     * @param filePath 文件绝对路径
     * @return
     * @throws Exception
     */
    public static String encodeFile(String filePath) {
        byte[] bytes = fileToByte(filePath);
        return encode(bytes);

    }

    /**
     * <p>
     * BASE64字符串转回文件
     * @param filePath 文件绝对路径
     * @param base64 编码字符串
     * @throws Exception
     */
    public static void decodeToFile(String filePath, String base64) {
        byte[] bytes = decode(base64);
        byteArrayToFile(bytes, filePath);
    }

    /**
     *
     * 文件转换为二进制数组
     * @param filePath 文件路径
     * @return
     * @throws Exception
     */
    public static byte[] fileToByte(String filePath) {
        FileInputStream in = null;
        ByteArrayOutputStream out = null;
        try {
            byte[] data = new byte[0];
            File file = new File(filePath);
            if (file.exists()) {
                 in = new FileInputStream(file);
                 out = new ByteArrayOutputStream(2048);
                byte[] cache = new byte[CACHE_SIZE];
                int nRead = 0;
                while ((nRead = in.read(cache)) != -1) {
                    out.write(cache, 0, nRead);
                    out.flush();
                }
                data = out.toByteArray();
            }
            return data;
        }catch (Exception e){
            log.error("文件转换为二进制数组异常",e);
        }finally {
            if(out != null){
                try {
                    out.close();
                } catch (IOException e) {
                    log.error("out文件转换为二进制数组关闭流异常",e);
                }
            }
            if(in != null){
                try {
                    in.close();
                } catch (IOException e) {
                    log.error("in文件转换为二进制数组关闭流异常",e);
                }
            }
        }
        return null;
    }

    /**
     * 二进制数据写文件
     * @param bytes 二进制数据
     * @param filePath 文件生成目录
     *
     */
    public static void byteArrayToFile(byte[] bytes, String filePath) {
        InputStream in = null;
        OutputStream out = null;
        try {
             in = new ByteArrayInputStream(bytes);
             File  destFile = new File(filePath);
            if (!destFile.getParentFile().exists()) {
                destFile.getParentFile().mkdirs();
            }
            destFile.createNewFile();
            out = new FileOutputStream(destFile);
            byte[] cache = new byte[CACHE_SIZE];
            int nRead = 0;
            while ((nRead = in.read(cache)) != -1) {
                out.write(cache, 0, nRead);
                out.flush();
            }
        }catch (Exception e){
            log.error("二进制数据写文件异常",e);
        }finally {
            if(out != null){
                try {
                    out.close();
                } catch (IOException e) {
                    log.error("out二进制数据写文件关闭流异常",e);
                }
            }
            if(in != null){
                try {
                    in.close();
                } catch (IOException e) {
                    log.error("in二进制数据写文件关闭流异常",e);
                }
            }
        }
    }

}
