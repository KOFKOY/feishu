package com.wsj.feishu;

import com.larksuite.oapi.core.AppSettings;
import com.larksuite.oapi.core.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class FeishuApplication {

    public static void main(String[] args) {
        SpringApplication.run(FeishuApplication.class, args);
    }

}
