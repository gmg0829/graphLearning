package com.gmg.demo.controller;

/**
 * @author gmg
 * @title: NeoControllerHttp
 * @projectName neo4j_demo
 * @description: TODO
 * @date 2019/4/30 12:48
 */

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.*;


@RestController
@RequestMapping("/neo4j/datahttp")
public class NeoControllerHttp {
    private static final Logger log = LoggerFactory.getLogger(NeoControllerRest.class);

    @RequestMapping(value = "/getData", method = RequestMethod.POST)
    public String getHello() {
        //建立连接
        log.info("getData接口调用开始时间:"+new Date());// 接口调用开始时间
        String url = "http://192.168.1.166:7474/db/data/transaction/commit";
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials( new AuthScope("192.168.1.166", 7474),
                new UsernamePasswordCredentials("neo4j", "pw123456"));
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultCredentialsProvider(credsProvider).build();
        //参数准备以及封装请求
        StringEntity s = new StringEntity("{\n" +
                "  \"statements\" : [ {\n" +
                "    \"statement\" : \"MATCH (n) RETURN n LIMIT 25\"\n" +
                "  } ]\n" +
                "}","UTF-8");
        s.setContentEncoding("UTF-8");
        s.setContentType("application/json");//发送json数据需要设置contentType


        HttpPost httppost = new HttpPost(url);
        httppost.setEntity(s);
        httppost.addHeader("Content-Type", "application/json");
        httppost.addHeader("charset", "UTF-8");
        CloseableHttpResponse response = null;

        //获取结果
        String respString = "";
        try {
            response = httpClient.execute(httppost);
            respString = EntityUtils.toString(response.getEntity());
            log.info(respString);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return respString;
    }
}

