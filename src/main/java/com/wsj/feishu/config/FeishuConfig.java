package com.wsj.feishu.config;

import com.larksuite.oapi.core.AppSettings;
import com.larksuite.oapi.core.Config;
import com.larksuite.oapi.core.DefaultStore;
import com.larksuite.oapi.core.Domain;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class FeishuConfig {
    @Value("${feishu.appid}")
    public String appId;
    @Value("${feishu.appsecret}")
    public String appSecret;
    @Value("${feishu.verificationtoken}")
    public String verificationToken;
    @Value("${feishu.encryptkey}")
    public String encryptKey;

    @Bean
    public Config initConfig(){
        AppSettings appSettings = Config.createInternalAppSettings(appId,appSecret,verificationToken,encryptKey);
        Config config = new Config(Domain.FeiShu, appSettings, new DefaultStore());//RedisStore
        return config;
    }
    @Bean
    public AppSettings initAppSettings(){
        AppSettings appSettings = Config.createInternalAppSettings(appId,appSecret,verificationToken,encryptKey);
        return appSettings;
    }
}
