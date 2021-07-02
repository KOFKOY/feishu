package com.wsj.feishu.controller.subscribe;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.JsonObject;
import com.larksuite.oapi.core.Config;
import com.larksuite.oapi.core.Context;
import com.larksuite.oapi.core.Decrypt;
import com.larksuite.oapi.core.api.AccessTokenType;
import com.larksuite.oapi.core.api.Api;
import com.larksuite.oapi.core.api.handler.AccessToken;
import com.larksuite.oapi.core.api.request.Request;
import com.larksuite.oapi.core.api.response.Response;
import com.larksuite.oapi.core.api.token.TenantAccessToken;
import com.larksuite.oapi.core.event.DefaultHandler;
import com.larksuite.oapi.core.event.Event;
import com.larksuite.oapi.core.event.EventServlet;
import com.larksuite.oapi.core.utils.Jsons;
import com.larksuite.oapi.service.application.v1.ApplicationService;
import com.larksuite.oapi.service.im.v1.ImService;
import com.wsj.feishu.entity.Encrypt;
import com.wsj.feishu.entity.SubscribeInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequestMapping("/ziel")
@RestController
public class ZielSub extends EventServlet {
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private Config config;

    public ZielSub(Config config) {
        super(config);
    }

    @PostConstruct
    public void init(){
        log.info("================设置首次启用应用事件处理者====================");
        // 设置首次启用应用事件处理者
        Event.setTypeHandler(this.getConfig(), "app_open", new DefaultHandler() {
            @Override
            public void Handle(Context context, Map<String, Object> event) throws Exception {
                // 打印请求的Request ID
                log.info("requestId:{}", context.getRequestID());
                // 打印事件
                log.info("event:{}", event);
            }
        });
    }

    @PostMapping("/sub")
    public Encrypt subscription(@RequestBody Encrypt verification) throws Exception {
        Decrypt decrypt = new Decrypt(config.getAppSettings().getEncryptKey());
        String decrypt1 = decrypt.decrypt(verification.getEncrypt());
        //region 验证订阅URL
//        Encrypt encrypt = mapper.readValue(decrypt1, Encrypt.class);
//        log.info("______________________致欧测试事件订阅解密JSON_______________________");
//        log.info(decrypt1);
//        log.info(encrypt.toString());
        //endregion
        //region 自定义事件 机器人回复信息
        log.info("______________________致欧测试事件订阅解密JSON_______________________");
        log.info(decrypt1);
        SubscribeInfo subscribeInfo = mapper.readValue(decrypt1, SubscribeInfo.class);
        log.info("时间戳---->" + subscribeInfo.getHeader().getCreate_time());
        log.info("内容---->" + subscribeInfo.getEvent().getMessage().getContent());

        SubscribeInfo.EventEntity.MessageEntity message = subscribeInfo.getEvent().getMessage();
        String tenant_key = subscribeInfo.getHeader().getTenant_key();
        String chat_id = subscribeInfo.getEvent().getMessage().getChat_id();
        String content = message.getContent();
        Map<String, String> map = mapper.readValue(content, Map.class);
        String sendContent = map.getOrDefault("text", "未识别的text");
        String contentBody = null;
        if (sendContent.contains("获取企业信息")) {
            contentBody = getCompanyInfo();
        }else if(sendContent.contains("获取个人信息")){

        }else if(sendContent.contains(("更新群名称为->"))){

        }else{
            //获取发送人的user_id  用于@
            String user_id = subscribeInfo.getEvent().getSender().getSender_id().getUser_id();
            //回复@信息的格式
            contentBody = "<at user_id=\""+user_id+"\"></at>" + sendContent;
            //去除@机器人的信息
            if (contentBody.contains("@_user_1")) {
                contentBody = contentBody.replace("@_user_1","").trim();
            }
        }



        this.sendTextMessage(tenant_key, contentBody, chat_id);
        //endregion

        //返回Encrypt中的challenge，验证飞书填写的URL，第一次验证成功后即可
        return null;
    }

    private String getCompanyInfo() throws Exception {
        Context context = new Context();
        context.set("-----ctxKeyConfig", config);
        TenantAccessToken tenantAccessToken = AccessToken.getInternalTenantAccessToken(context);
        String tenantAccessToken1 = tenantAccessToken.getTenantAccessToken();

        log.info("打印Token->" + tenantAccessToken1);
        return "开发中，敬请期待";
    }

    /**
     * robot
     * 消息卡片请求网址
     * @param encrypt
     * @param request
     * @return {@link com.wsj.feishu.entity.Encrypt}
     * @author 王帅杰
     * @history
     */
    @PostMapping("/robot")
    public Encrypt robot(@RequestBody Encrypt encrypt, HttpServletRequest request){
        log.info("*********************致欧测试机器人消息卡片请求网址*********************");
        return encrypt;
    }


    private  void sendTextMessage(String tenantKey,String contentMsg,String chatId) throws Exception {
        Map<String, Object> message = new HashMap<>();
        //如果是user_id,机器人回单聊发送信息
        //chat_id，在发送信息的窗口发送信息
        message.put("chat_id", chatId);
        message.put("msg_type", "text");
        Map<String, Object> content = new HashMap<>();
        content.put("text", contentMsg);
        message.put("content", content);
        //v4旧版本  v1新版本
        Request<Map<String, Object>, Map<String, Object>> request = Request.newRequest("message/v4/send",
                "POST", AccessTokenType.Tenant, message, new HashMap<>(), Request.setTenantKey(tenantKey));
        Response<Map<String, Object>> response = Api.send(config, request);
        System.out.println(response.getRequestID());
        System.out.println(response.getHTTPStatusCode());
        System.out.println(Jsons.DEFAULT_GSON.toJson(response));
    }
}
