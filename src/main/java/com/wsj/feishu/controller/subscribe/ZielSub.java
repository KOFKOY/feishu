package com.wsj.feishu.controller.subscribe;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.larksuite.oapi.core.Config;
import com.larksuite.oapi.core.Context;
import com.larksuite.oapi.core.Decrypt;
import com.larksuite.oapi.core.api.AccessTokenType;
import com.larksuite.oapi.core.api.Api;
import com.larksuite.oapi.core.api.request.Request;
import com.larksuite.oapi.core.api.response.Response;
import com.larksuite.oapi.core.event.DefaultHandler;
import com.larksuite.oapi.core.event.Event;
import com.larksuite.oapi.core.event.EventServlet;
import com.larksuite.oapi.core.utils.Jsons;
import com.larksuite.oapi.service.application.v1.ApplicationService;
import com.larksuite.oapi.service.application.v1.model.AppOpenEvent;
import com.larksuite.oapi.service.application.v1.model.AppStatusChangeEvent;
import com.larksuite.oapi.service.authen.v1.model.UserAccessTokenInfo;
import com.larksuite.oapi.service.contact.v3.ContactService;
import com.larksuite.oapi.service.contact.v3.model.UserCreatedEvent;
import com.larksuite.oapi.service.contact.v3.model.UserUpdatedEvent;
import com.wsj.feishu.constant.Constant;
import com.wsj.feishu.entity.CreateUser;
import com.wsj.feishu.entity.Encrypt;
import com.wsj.feishu.entity.SubscribeInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
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
    @Autowired
    private RestTemplate restTemplate;

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
                // Print the request ID of the request
                log.info("Handle requestId:{}", context.getRequestID());
                // Print event
                log.info("Handle event:{}", event);
            }
        });

        Event.setTypeHandler(this.getConfig(), "user.created_v2", (context, event) -> {
            log.info("setTypeHandler requestId:{}", context.getRequestID());
            log.info("setTypeHandler event:{}", event);
        });


        ApplicationService applicationService = new ApplicationService(this.getConfig());
        applicationService.setAppOpenEventHandler(new ApplicationService.AppOpenEventHandler() {
            @Override
            public void Handle(Context context, AppOpenEvent event) throws Exception {
                log.info("setAppOpenEventHandler requestId:{}", context.getRequestID());
                log.info("setAppOpenEventHandler event:{}", Jsons.DEFAULT_GSON.toJson(event));
            }
        });
        applicationService.setAppStatusChangeEventHandler(new ApplicationService.AppStatusChangeEventHandler() {
            @Override
            public void Handle(Context context, AppStatusChangeEvent event) throws Exception {
                log.info("setAppStatusChangeEventHandler requestId:{}", context.getRequestID());
                log.info("setAppStatusChangeEventHandler event:{}", Jsons.DEFAULT_GSON.toJson(event));
            }
        });


        ContactService contactService = new ContactService(this.getConfig());
        contactService.setUserCreatedEventHandler(new ContactService.UserCreatedEventHandler() {
            @Override
            public void Handle(Context context, UserCreatedEvent event) throws Exception {
                log.info("setUserCreatedEventHandler requestId:{}", context.getRequestID());
                log.info("setUserCreatedEventHandler event:{}", Jsons.DEFAULT_GSON.toJson(event));
            }
        });
        contactService.setUserUpdatedEventHandler(new ContactService.UserUpdatedEventHandler() {
            @Override
            public void Handle(Context context, UserUpdatedEvent event) throws Exception {
                log.info("setUserUpdatedEventHandler requestId:{}", context.getRequestID());
                log.info("setUserUpdatedEventHandler event:{}", Jsons.DEFAULT_GSON.toJson(event));
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
        SubscribeInfo subscribeInfo = null;
        subscribeInfo = mapper.readValue(decrypt1, SubscribeInfo.class);
        if (subscribeInfo.getEvent().getMessage()!= null) {
            try {
                messageSub(subscribeInfo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(subscribeInfo.getEvent().getType()!=null && subscribeInfo.getEvent().getType().equals("remove_user_from_chat")){
            String chat_id = subscribeInfo.getEvent().getChat_id();
            sendTextMessageV1(subscribeInfo.getEvent().getUsers().get(0).getName()+"删库跑路了！！！", chat_id);
        }if(subscribeInfo.getEvent().getType()!=null && subscribeInfo.getEvent().getType().equals("add_user_to_chat")){
            String chat_id = subscribeInfo.getEvent().getChat_id();
            sendTextMessageV1( "热烈欢迎" + subscribeInfo.getEvent().getUsers().get(0).getName() + "同志加入我们蓝天计划！！！", chat_id);
        }
        //endregion
        //返回Encrypt中的challenge，验证飞书填写的URL，第一次验证成功后即可
        return null;
    }

    /**
     * messageSub
     * 消息类型的事件信息
     * @param subscribeInfo
     * @throws {@link Exception}
     * @author 王帅杰
     * @history
     */
    private void messageSub(SubscribeInfo subscribeInfo) throws Exception {
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
            sendContent = getCompanyInfo();
        }else if(sendContent.contains("获取个人信息")){
            sendContent = getUserInfo(subscribeInfo.getEvent().getSender().getSender_id().getOpen_id());
        }else if(sendContent.contains(("更新群名称为->"))){
            sendContent = updateChatInfo(chat_id,sendContent);
        }else if(sendContent.contains(("获取所有部门信息"))){
            sendContent = getDeptList();
        }else if(sendContent.contains(("获取部门成员根据部门OpenId->"))){
            sendContent = getUserByOpenId(sendContent);
        }else if(sendContent.contains(("创建用户"))){
            sendContent = createUser();
        }else if(sendContent.contains(("修改用户姓名->"))){
            sendContent = modifyUser(sendContent,subscribeInfo.getEvent().getSender().getSender_id().getOpen_id());
        }else if(sendContent.contains(("删除用户"))){
            sendContent = deleteUser(subscribeInfo.getEvent().getSender().getSender_id().getOpen_id());
        }else if(sendContent.contains(("ls"))){
            String[] options = {"获取企业信息","获取个人信息","更新群名称为->XXX(群名称)","获取所有部门信息"
                    ,"获取部门成员根据部门OpenId->xxx(openId)","创建用户","修改用户姓名->xxx(姓名)","删除用户"};
            sendContent = mapper.writeValueAsString(options);
        }
        //获取发送人的user_id  用于@
        String user_id = subscribeInfo.getEvent().getSender().getSender_id().getOpen_id();
        //回复@信息的格式
        contentBody = "<at user_id=\""+user_id+"\"></at>" + sendContent;
        //去除@机器人的信息
        if (contentBody.contains("@_user_1")) {
            contentBody = contentBody.replace("@_user_1","").trim();
        }
//        this.sendTextMessage(tenant_key, contentBody, chat_id);
        this.sendTextMessageV1(contentBody, chat_id);
    }

    private String deleteUser(String open_id) throws Exception {
        Request<CreateUser, HashMap<Object, Object>> request = Request.newRequest("contact/v3/users/"+open_id+"?user_id_type=open_id",
                "DELETE", AccessTokenType.Tenant, null, new HashMap<>());
        Response<HashMap<Object, Object>> send = Api.send(config, request);
        return "删除成功";
    }

    private String modifyUser(String name , String openId) throws Exception {
        String[] split = name.split("->");
        CreateUser createUser = new CreateUser();
        createUser.setName(split[1]);
        createUser.setEmployee_type(1);
        Request<CreateUser, HashMap<Object, Object>> request = Request.newRequest("contact/v3/users/"+openId,
                "PATCH", AccessTokenType.Tenant, createUser, new HashMap<>());
        Response<HashMap<Object, Object>> send = Api.send(config, request);
        log.info(send.toString());
        if (send.getCode() == 0) {
            return "修改成功";
        }
        return send.getMsg();
    }

    private String createUser() throws Exception {
        CreateUser createUser = new CreateUser();
        createUser.setName("许七安");
        createUser.setMobile("17073721806");
        //IT部门的OpenId
        createUser.setDepartment_ids(Arrays.asList("od-189d115e589a7ba50825daf8811a0f47"));
        //IT部门管理员的leader_user_id
        createUser.setLeader_user_id("ou_f4c091cd5dfb69c279d5d9280dc7a175");
        createUser.setEmployee_type(1);
        Request<CreateUser, HashMap<Object, Object>> request = Request.newRequest("contact/v3/users",
                "POST", AccessTokenType.Tenant, createUser, new HashMap<>());
        Response<HashMap<Object, Object>> send = Api.send(config, request);
        log.info(send.toString());
        if (send.getCode() == 0) {
            return "创建成功";
        }
        return send.getMsg();
    }

    private String getUserByOpenId(String sendContent) throws Exception {
        String[] split = sendContent.split("->");
        Request<Map<String, Object>, Map<String, Object>> request = Request.newRequest("contact/v3/users?department_id="+split[1],
                "GET", AccessTokenType.Tenant, null, new HashMap<>());
        Response<Map<String, Object>> send = Api.send(config, request);
        Object items = send.getData().get("items");
        log.info(mapper.writeValueAsString(send));
        return mapper.writeValueAsString(items);
    }

    private String getDeptList() throws Exception {
        /*ContactService contactService = new ContactService(config);
        Map<String, Object> query = new HashMap<>();
        query.put("parent_department_id", 0);
        Response<DepartmentListResult> response = contactService.getDepartments().list(Request.setQueryParams(query)).setFetchChild(true).execute();
        System.out.println(response.getRequestID());
        System.out.println(response.getHTTPStatusCode());
        System.out.println(Jsons.DEFAULT_GSON.toJson(response));*/

        //parent_department_id 父部门的ID，填上获取部门下所有子部门
        //fetch_child 递归获取所有子部门
        //参考url:https://open.feishu.cn/document/uAjLw4CM/ukTMukTMukTM/reference/contact-v3/department/list
        Request<Map<String, Object>, Map<String, Object>> request = Request.newRequest("contact/v3/departments?parent_department_id=0&fetch_child=true",
                "GET", AccessTokenType.Tenant, null, new HashMap<>());
        Response<Map<String, Object>> send = Api.send(config, request);
        Object items = send.getData().get("items");
        log.info(mapper.writeValueAsString(send));
        return mapper.writeValueAsString(items);
    }

    private String updateChatInfo(String chat_id,String sendContent) {
        String[] split = sendContent.split("->");
        Map<String, Object> message = new HashMap<>();
        message.put("name", split[1].trim());
        message.put("description", split[1].trim()+"的描述QAQ");
        Request<Map<String, Object>, Map<String, Object>> request = Request.newRequest("im/v1/chats/"+chat_id,
                "PUT", AccessTokenType.Tenant, message, new HashMap<>());
        try {
            Response<Map<String, Object>> send = Api.send(config, request);
        } catch (Exception e) {
            e.printStackTrace();
            return "更新失败";
        }
        return "更新成功";
    }

    private String getUserInfo(String openId) throws Exception {
        UserAccessTokenInfo userAccessTokenInfo = Constant.userMap.get(openId);
        if(userAccessTokenInfo == null) return "请先登录";
        Request<Map<String, Object>, Map<String, Object>> request = Request.newRequest("authen/v1/user_info",
                "GET", AccessTokenType.User, null, new HashMap<>(),Request.setUserAccessToken(userAccessTokenInfo.getAccessToken()));
        Response<Map<String, Object>> send = Api.send(config, request);
        log.info("个人信息->"+send.getData());
        return mapper.writeValueAsString(send.getData());
    }

    private String getCompanyInfo() throws Exception {
        /*Context context = new Context();
        context.set("-----ctxKeyConfig", config);
        TenantAccessToken tenantAccessToken = AccessToken.getInternalTenantAccessToken(context);
        //获取TenantAccessToken
        String tenantAccessToken1 = tenantAccessToken.getTenantAccessToken();
        log.info("打印Token->" + tenantAccessToken1);*/

        Map<String, Object> message = new HashMap<>();
        Request<Map<String, Object>, Map<String, Object>> request = Request.newRequest("tenant/v2/tenant/query",
                        "GET", AccessTokenType.Tenant, null, new HashMap<>());
        Response<Map<String, Object>> send = Api.send(config, request);
        System.out.println(send.getData());
        return mapper.writeValueAsString(send.getData());
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

    /**
     * sendTextMessage
     * 老版发送信息接口
     * @param tenantKey
     * @param contentMsg
     * @param chatId
     * @throws {@link Exception}
     * @author 王帅杰
     * @history
     */
    @Deprecated
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

    /**
     * sendTextMessageV1
     * 新版发送信息接口
     * @param contentMsg
     * @param chatId
     * @throws {@link Exception}
     * @author 王帅杰
     * @history
     */
    private  void sendTextMessageV1(String contentMsg,String chatId) throws Exception {
        log.info("发送的信息->" + contentMsg);
        Map<String, Object> message = new HashMap<>();
        //如果是user_id,机器人回单聊发送信息
        //chat_id，在发送信息的窗口发送信息
        message.put("receive_id", chatId);
        message.put("msg_type", "text");
        Map<String, String> content = new HashMap<>();
        content.put("text", contentMsg);
        message.put("content", mapper.writeValueAsString(content));
        //v4旧版本  v1新版本
        Request<Map<String, Object>, Map<String, Object>> request = Request.newRequest("im/v1/messages?receive_id_type=chat_id",
                "POST", AccessTokenType.Tenant, message, new HashMap<>());
        Response<Map<String, Object>> response = Api.send(config, request);
        System.out.println(response.getRequestID());
        System.out.println(response.getHTTPStatusCode());
        System.out.println(Jsons.DEFAULT_GSON.toJson(response));
    }
}
