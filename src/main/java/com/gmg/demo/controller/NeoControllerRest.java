package com.gmg.demo.controller;


import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.nio.charset.Charset;
import java.util.*;



/**
 * @author gmg
 * @title: RestTemplateUtil
 * @projectName neo4j_demo
 * @description: TODO
 * @date 2019/4/30 10:54
 */
@RestController
@RequestMapping("/neo4j/data")
public class NeoControllerRest {
    private static final Logger log = LoggerFactory.getLogger(NeoControllerRest.class);
    @Resource(name = "restTemplate")
    private RestTemplate restTemplate;

    @RequestMapping(value = "/getData", method = RequestMethod.POST)
    public String getHello() {
        //建立连接
        log.info("getData接口调用开始时间:"+new Date());// 接口调用开始时间


        final String plainCreds = "neo4j:pw123456";
        final byte[] plainCredsBytes = plainCreds.getBytes();

        final byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
        final String base64Creds = new String(base64CredsBytes);

        final HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Creds);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(new MediaType[] { new MediaType("application",
                "json", Charset.forName("UTF-8")) }));
        final HttpEntity<String> request = new HttpEntity<>("{\n" +
                "  \"statements\" : [ {\n" +
                "    \"statement\" : \"MATCH (n) RETURN n LIMIT 25\"\n" +
                "  } ]\n" +
                "}",headers);
        final ResponseEntity<String> response = restTemplate.exchange("http://192.168.1.166:7474/db/data/transaction/commit", HttpMethod.POST, request, String.class);
        final String respString = response.getBody();

        return respString;
    }
}

