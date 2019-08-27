package com.gmg.demo.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * @author gmg
 * @title: TigerGraphController
 * @projectName neo4j_demo
 * @description: TODO
 * @date 2019/6/27 20:06
 */
@RestController
@RequestMapping("tigerGraph")
public class TigerGraphController {

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping("getTigerGraph")
    public String getTigerGraph(){
        String response= restTemplate.getForObject("http://192.168.1.166:9000/query/social/a?p=Tom",String.class);
        return response;
    }
    @RequestMapping("postTigerGraph")
    public String postTigerGraph(){
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(new MediaType[] { new MediaType("application",
                "json", Charset.forName("UTF-8")) }));
        final HttpEntity<String> request = new HttpEntity<>("{\"vertices\":{\"person\":{\"gmg\":{\"name\":{\"value\":\"gmg\"},\"age\":{\"value\":12},\"gender\":{\"value\":\"1\"},\"state\":{\"value\":\"0\"}}}}}",headers);
        final ResponseEntity<String> response = restTemplate.exchange("http://192.168.1.166:9000/graph/social", HttpMethod.POST, request, String.class);
        final String respString = response.getBody();
        return respString;
    }

    @RequestMapping("getRelationObject")
    public String getRelationObject(String customerId){
        JSONObject response= restTemplate.getForObject("http://192.168.1.166:9000/query/aml/getRelationObject?customerId="+customerId, JSONObject.class);
        JSONArray jsonArray=response.getJSONArray("results");
        return jsonArray.getJSONObject(0).toJSONString();
    }


}
