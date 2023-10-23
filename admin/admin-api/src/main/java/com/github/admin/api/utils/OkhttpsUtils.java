package com.github.admin.api.utils;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@Slf4j
public class OkhttpsUtils {
    /**
     * getClient
     * @return OkHttpClient
     */

    private static OkHttpClient getClient() {
        OkHttpClient okHttpClient = OKHttpClientBuilder.buildOKHttpClient().
                connectTimeout(6000, TimeUnit.SECONDS).
                readTimeout(6000, TimeUnit.SECONDS).
                writeTimeout(6000, TimeUnit.SECONDS).
                build();
        return okHttpClient;
    }


    /**
     * getRequest
     * @param url url
     * @return Request
     * @throws MalformedURLException 异常
     */
    private static Request getRequest(String url, Map<String,String> header) throws MalformedURLException {
        Request.Builder builder = getBuilder(header);
        URL uri = new URL(url);
        return builder.url(uri).build();
    }


    public static String doGet(String url){
        return doGet(url,null,new HashMap<>());
    }

    /**
     * doGet
     * @param url url
     * @param param param
     * @return 结果
     */

    public static String doGet(String url, String param, Map<String,String> header) {
        if (param != null) {
            url = url + "?" + param;
        }

        String result = null;
        try {
            OkHttpClient okHttpClient = getClient();
            Request request = getRequest(url,header);
            Response response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                ResponseBody body = response.body();
                if (body != null) {
                    result = body.string();
                } else {
                    throw new IOException("response body is null");
                }
            } else {
                response.close();
            }
        } catch (IOException e) {
            log.error("GET请求异常,url = {}", url, e);
        }
        return result;
    }

    /**
     * getBuilder
     * @return Builder
     */

    private static Request.Builder getBuilder(Map<String,String> header) {
        Request.Builder builder = new Request.Builder();

        builder.addHeader("accept", "application/json").
                addHeader("connection", "Keep-Alive").
                addHeader("Content-Type", "application/json;charset=UTF-8");
        if(header != null && header.entrySet().size()>0){
            for(Map.Entry<String,String>  entry:header.entrySet()){
                builder.addHeader(entry.getKey(),entry.getValue());
            }
        }
        return builder;
    }

    /**
     * doPost
     * @param url url
     * @param param param
     * @return 请求结果
     */

    public static String doPost(String url, String param,Map<String,String> header) {

        OkHttpClient okHttpClient = getClient();
        Request.Builder builder = getBuilder(header);
        String result = null;
        try {
            RequestBody requestBody = RequestBody.create(param.getBytes("UTF-8"),
                    MediaType.parse(org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE));
            builder.post(requestBody);
            Request request = builder.url(url).build();
            Response response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                ResponseBody body = response.body();
                if (body != null) {
                    result = body.string();
                } else {
                    throw new IOException("response body is null");
                }
            } else {
                response.close();
            }
        } catch (Exception e) {
            log.error("POST请求异常,url = {}", url, e);
        }
        return result;
    }

    /**
     * doDelete
     * @param url url
     * @param param param
     * @return 请求结果
     */

    public static String doDelete(String url, String param,Map<String,String> header) {

        OkHttpClient okHttpClient = getClient();
        Request.Builder builder = getBuilder(header);
        String result = null;
        try {
            if (param != null) {
                RequestBody requestBody = RequestBody.create(param.getBytes("UTF-8"),
                        MediaType.parse(org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE));
                builder.delete(requestBody);
            } else {
                builder.delete();
            }
            Request request = builder.url(url).build();
            Response response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                ResponseBody body = response.body();
                if (body != null) {
                    result = body.string();
                } else {
                    throw new IOException("response body is null");
                }
            } else {
                response.close();
            }
        } catch (Exception e) {
            log.error("DELETE请求异常,url = {}", url, e);
        }
        return result;
    }

    /**
     * doPut
     * @param url url
     * @param param param
     * @return 请求结果
     */

    public static String doPut(String url, String param,Map<String,String> header) {

        OkHttpClient okHttpClient = getClient();
        Request.Builder builder = getBuilder(header);
        String result = null;
        try {
            RequestBody requestBody = RequestBody.create(param.getBytes("UTF-8"),
                    MediaType.parse(org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE));
            builder.put(requestBody);
            Request request = builder.url(url).build();
            Response response = okHttpClient.newCall(request).execute();

            if (response.isSuccessful()) {
                ResponseBody body = response.body();
                if (body != null) {
                    result = body.string();
                } else {
                    throw new IOException("response body is null");
                }
            } else {
                response.close();
            }
        } catch (Exception e) {
            log.error("PUT请求异常,url = {}", url, e);
        }
        return result;
    }

}
