package com.wsj.feishu.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.larksuite.oapi.core.Decrypt;
import com.wsj.feishu.entity.Encrypt;
import com.wsj.feishu.entity.SubscribeInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/rebot")
public class Robot {
    @Autowired
    private ObjectMapper mapper;

    @PostMapping
    public String autoReply(@RequestBody Encrypt encrypt) throws JsonProcessingException {

        String base = encrypt.getEncrypt();
        Decrypt decrypt = new Decrypt("wsjTest");
        String decrypt1 = decrypt.decrypt(base);

        SubscribeInfo subscribeInfo = mapper.readValue(decrypt1, SubscribeInfo.class);
        log.info("______________________事件订阅解密JSON_______________________");
        log.info(decrypt1);
        log.info(subscribeInfo.toString());

        return null;
    }
}
