package com.gmg.demo.util;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author gmg
 * @title: ProfileUtil
 * @projectName
 * @description: TODO
 * @date 2019/4/10 16:58
 */
@Component
public class ProfileUtil implements ApplicationContextAware {

    private static ApplicationContext context = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ProfileUtil.context = applicationContext;
    }

    // 获取当前环境参数  exp: dev,prod,test
    public static String getActiveProfile() {
        String[] profiles = context.getEnvironment().getActiveProfiles();
        if (!ArrayUtils.isEmpty(profiles)) {
            return profiles[0];
        }
        return "";
    }

    public static String loadConfig(String key) {
        Properties properties = new Properties();
        InputStream inputStream = ProfileUtil.class.getResourceAsStream("/application-" + ProfileUtil.getActiveProfile() + ".properties");
        String result = null;
        try {
            properties.load(inputStream);
            result = properties.getProperty(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}

