package com.wsj.feishu.controller.subscribe;

import cn.hutool.core.codec.Base64Encoder;
import cn.hutool.core.io.FileUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.larksuite.oapi.core.Config;
import com.larksuite.oapi.core.Context;
import com.larksuite.oapi.core.Decrypt;
import com.larksuite.oapi.core.api.AccessTokenType;
import com.larksuite.oapi.core.api.Api;
import com.larksuite.oapi.core.api.request.FormData;
import com.larksuite.oapi.core.api.request.FormDataFile;
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
import com.wsj.feishu.entity.*;
import com.wsj.feishu.entity.card.ElementsEntity;
import com.wsj.feishu.entity.card.HeaderEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

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
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
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
        String open_id = subscribeInfo.getEvent().getSender().getSender_id().getOpen_id();
        String messageId = subscribeInfo.getEvent().getMessage().getMessage_id();
        String content = message.getContent();
        Map<String, String> map = mapper.readValue(content, Map.class);
        String sendContent = map.getOrDefault("text", "未识别的text");
        String contentBody = null;
        if (sendContent.contains("获取企业信息")) {
            sendContent = getCompanyInfo();
        }else if(sendContent.contains("获取个人信息")){
            sendContent = getUserInfo(open_id);
        }else if(sendContent.contains("更新群名称为->")){
            sendContent = updateChatInfo(chat_id,sendContent);
        }else if(sendContent.contains("获取所有部门信息")){
            sendContent = getDeptList();
        }else if(sendContent.contains("获取部门成员根据部门OpenId->")){
            sendContent = getUserByOpenId(sendContent);
        }else if(sendContent.contains("创建用户")){
            sendContent = createUser();
        }else if(sendContent.contains("修改用户姓名->")){
            sendContent = modifyUser(sendContent,open_id);
        }else if(sendContent.contains("删除用户")){
            sendContent = deleteUser(open_id);
        }else if(sendContent.contains("创建部门")){
            sendContent = createDept(sendContent);
        }else if(sendContent.contains("根据部门ID获取单个部门信息->")){
            sendContent = getDeptInfoByOpenId(sendContent);
        }else if(sendContent.contains("获取父部门信息")){
            sendContent = getDeptParentInfo(sendContent);
        }else if(sendContent.contains("搜索部门->")){
            sendContent = searchDeptByName(sendContent,open_id);
        }else if(sendContent.contains("修改部门信息->")){
            sendContent = modifyDeptInfo(sendContent);
        }else if(sendContent.contains("删除部门->")){
            sendContent = deleteDept(sendContent);
        }else if(sendContent.contains("发送图片")){
            sendImageV1(chat_id);
            return;
        }else if(sendContent.contains("发送富文本")){
            sendPostText(chat_id);
            return;
        }else if(sendContent.contains("文本翻译->")){
            sendContent = translateText(sendContent,chat_id);
        }else if(sendContent.contains("图片识别")){
            sendContent = imageTranslate();
        }else if(sendContent.contains("下载图片")){
            sendContent = downloadImage();
        }else if(sendContent.contains("发送卡片")){
            sendCardInfo(chat_id);
            return;
        }else if(sendContent.contains("苍井空")||sendContent.contains("政治")){
            deleteMessage(messageId,open_id);
            return;
        }else if(sendContent.contains(("ls"))){
            String items = "\n" +
                    "功能列表如下:↓↓↓\n"+
                    "1.获取企业信息\n"+
                    "2.获取个人信息\n"+
                    "3.更新群名称为->XXX(群名称)\n"+
                    "4.获取所有部门信息\n"+
                    "5.获取部门成员根据部门OpenId->xxx(openId)\n"+
                    "6.创建用户\n"+
                    "7.修改用户姓名->xxx(姓名)\n"+
                    "8.删除用户\n"+
                    "9.创建部门->xxx(名称)\n" +
                    "10.根据部门ID获取单个部门信息->xxx(openId)\n" +
                    "11.获取父部门信息\n"+
                    "12.搜索部门->xxx(部门名称)\n"+
                    "13.修改部门信息->xxx(部门名称)\n"+
                    "14.删除部门->xxx(openId)\n"+
                    "15.发送图片\n"+
                    "16.发送富文本\n"+
                    "####################################\n"+
                    "1.文本翻译->xxx(中文)\n"+
                    "3.撤回测试(发送的文字包含[苍井空|政治])\n"+
                    "4.下载图片\n"+
                    "5.图片识别\n"+
                    "6.发送卡片\n"
                    ;
            sendContent = items;
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
//        this.sendTextMessageV1(contentBody, chat_id);
        this.replyMessage(contentBody, messageId);
    }

    private void sendCardInfo(String chat_id) throws Exception {
        CardInfo cardInfo = new CardInfo();
        cardInfo.setOpen_id("ou_f4c091cd5dfb69c279d5d9280dc7a175");
        cardInfo.setMsg_type("interactive");
        cardInfo.setUpdate_multi(false);
        CardInfo.CardEntity cardEntity = new CardInfo.CardEntity();
        CardInfo.CardEntity.ConfigEntity configEntity = new CardInfo.CardEntity.ConfigEntity();
        configEntity.setWide_screen_mode(true);
        cardEntity.setConfig(configEntity);
        HeaderEntity headerEntity = new HeaderEntity();
        headerEntity.setTemplate("blue");
        headerEntity.setTitle(new HeaderEntity.TitleEntity("plain_text","待办事项"));
        cardEntity.setHeader(headerEntity);
        List<ElementsEntity> elementsList = new ArrayList<>();
        ElementsEntity element1 = new ElementsEntity();
        element1.setTag("div");
        List<ElementsEntity.FieldsEntity> fieldList = new ArrayList<>();
        ElementsEntity.FieldsEntity field1 = new ElementsEntity.FieldsEntity();
        field1.set_short(false);
        field1.setText(new ElementsEntity.TextEntity("lark_md","XXX同学的禁忌之路"));
        ElementsEntity.FieldsEntity field2 = new ElementsEntity.FieldsEntity();
        field2.set_short(true);
        field2.setText(new ElementsEntity.TextEntity("lark_md", "申请人:王晓磊"));
        ElementsEntity.FieldsEntity field3 = new ElementsEntity.FieldsEntity();
        field3.set_short(false);
        field3.setText(new ElementsEntity.TextEntity("lark_md", ""));
        ElementsEntity.FieldsEntity field4 = new ElementsEntity.FieldsEntity();
        field4.set_short(false);
        field4.setText(new ElementsEntity.TextEntity("lark_md","**时间：**\n2020-4-8 至 2020-4-10（共3天）"));
        ElementsEntity.FieldsEntity field5 = new ElementsEntity.FieldsEntity();
        field5.set_short(false);
        field5.setText(new ElementsEntity.TextEntity("lark_md", ""));
        fieldList.add(field1);
        fieldList.add(field2);
        fieldList.add(field3);
        fieldList.add(field4);
        fieldList.add(field5);
        element1.setFields(fieldList);
        ElementsEntity element2 = new ElementsEntity();
        element2.setTag("hr");
        ElementsEntity element3 = new ElementsEntity();
        element3.setTag("div");
        element3.setText(new ElementsEntity.TextEntity("lark_md","[点击查看详情](https://www.baidu.com)"));
        elementsList.add(element1);
        elementsList.add(element2);
        elementsList.add(element3);
        cardEntity.setElements(elementsList);
        cardInfo.setCard(cardEntity);

        log.info("发送的json->" + mapper.writeValueAsString(cardInfo));
        Request<CardInfo, HashMap<Object, Object>> request = Request.newRequest("message/v4/send",
                "POST", AccessTokenType.Tenant, cardInfo, new HashMap<>());
        Response<HashMap<Object, Object>> send = Api.send(config, request);
        log.info("发送卡片->" + send);
    }

    private String downloadImage() throws Exception {
        Map<String, Object> queries = new HashMap<>();
        String resource = Thread.currentThread().getContextClassLoader().getResource("/static/dtest.png").getFile();
        Request<Object, FileOutputStream> request = Request.newRequest("im/v1/images/img_v2_fc2dacea-0f92-443f-8c03-b6660b260dcg",
                "GET", AccessTokenType.Tenant, null, new FileOutputStream(resource), Request.setResponseStream(), Request.setQueryParams(
                        queries));
        request.setContentType("image/webp");
        Response<FileOutputStream> response = Api.send(config, request);
        FileOutputStream data = response.getData();
        System.out.println(response.getRequestID());
        System.out.println(response.getHTTPStatusCode());
        return "下载图片成功";
    }

    /**
     * deleteMessage
     * 撤回信息
     * @param messageId
     * @param openId
     * @throws {@link Exception}
     * @author 王帅杰
     * @history
     */
    private void deleteMessage(String messageId,String openId) throws Exception {
        Request<Map<String, Object>, HashMap<Object, Object>> request = Request.newRequest("im/v1/messages/" + messageId,
                "DELETE", AccessTokenType.User, null, new HashMap<>(),Request.setUserAccessToken(Constant.userMap.get("ou_f4c091cd5dfb69c279d5d9280dc7a175").getAccessToken()));
        Response<HashMap<Object, Object>> response = Api.send(config, request);
        System.out.println(response.getRequestID());
        System.out.println(response.getHTTPStatusCode());
        System.out.println(Jsons.DEFAULT_GSON.toJson(response));
    }

    /**
     * replyMessage
     * 回复信息
     * @param sendContent
     * @param messageId
     * @throws {@link Exception}
     * @author 王帅杰
     * @history
     */
    private void replyMessage(String sendContent, String messageId) throws Exception {
        Map<String, Object> message = new HashMap<>();
        //如果是user_id,机器人回单聊发送信息
        //chat_id，在发送信息的窗口发送信息
        message.put("msg_type", "text");
        Map<String, Object> content = new HashMap<>();
        content.put("text", sendContent);
        message.put("content",mapper.writeValueAsString(content));
        Request<Map<String, Object>, HashMap<Object, Object>> request = Request.newRequest("im/v1/messages/" + messageId + "/reply",
                "POST", AccessTokenType.Tenant, message, new HashMap<>());
        Response<HashMap<Object, Object>> response = Api.send(config, request);
        System.out.println(response.getRequestID());
        System.out.println(response.getHTTPStatusCode());
        System.out.println(Jsons.DEFAULT_GSON.toJson(response));
    }

    private String imageTranslate() throws Exception {
        String resource = Thread.currentThread().getContextClassLoader().getResource("/static/translate.png").getFile();
        byte[] bytes = FileUtil.readBytes(new File(resource));
        String str = Base64Encoder.encode(bytes);
        Map<String, Object> message = new HashMap<>();
        message.put("image", str);
        Request<Map<String, Object>, HashMap<Object, Object>> request = Request.newRequest("optical_char_recognition/v1/image/basic_recognize",
                "POST", AccessTokenType.Tenant, message, new HashMap<>());
        Response<HashMap<Object, Object>> send = Api.send(config, request);
        if (send.getCode() != 0) {
            return send.getMsg();
        }
        return send.getData().get("text_list").toString();
    }

    private String translateText(String sendContent, String chat_id) throws Exception {
        String[] split = sendContent.split("->");
        TranslateEntity translateEntity = new TranslateEntity();
        translateEntity.setSource_language("zh");
        translateEntity.setTarget_language("en");
        translateEntity.setText(split[1]);
        List<TranslateEntity.GlossaryEntity> glossaryList = new ArrayList<>();
        TranslateEntity.GlossaryEntity entity = new TranslateEntity.GlossaryEntity();
        entity.setFrom("王帅杰");
        entity.setTo("Wsj");
        glossaryList.add(entity);
        translateEntity.setGlossary(glossaryList);

        Request<TranslateEntity, HashMap<Object, Object>> request = Request.newRequest("translation/v1/text/translate",
                "POST", AccessTokenType.Tenant, translateEntity, new HashMap<>());
        Response<HashMap<Object, Object>> send = Api.send(config, request);
        if (send.getCode() != 0) {
            return send.getMsg();
        }
        return send.getData().get("text").toString();
    }

    /**
     * sendPostText
     * 发送富文本时，值为Null的字段不进行序列化
     * @param chatId
     * @throws {@link Exception}
     * @author 王帅杰
     * @history
     */
    private void sendPostText(String chatId) throws Exception {
        Map<String, Object> message = new HashMap<>();
        //如果是user_id,机器人回单聊发送信息
        //chat_id，在发送信息的窗口发送信息
        message.put("receive_id", chatId);
        message.put("msg_type", "post");

        PostText postText = new PostText();
        PostText.Zh_cnEntity zhCh = new PostText.Zh_cnEntity();
        zhCh.setTitle("我是第一个标题");
        List<List<PostText.Zh_cnEntity.ContentEntity>> listTotal = new ArrayList<>();
        List<PostText.Zh_cnEntity.ContentEntity> listItems = new ArrayList<>();
        PostText.Zh_cnEntity.ContentEntity contentEntity = new PostText.Zh_cnEntity.ContentEntity();
        contentEntity.setTag("text");
        contentEntity.setText("第一行");
        PostText.Zh_cnEntity.ContentEntity contentEntity2 = new PostText.Zh_cnEntity.ContentEntity();
        contentEntity2.setTag("a");
        contentEntity2.setHref("http://www.feishu.cn");
        contentEntity2.setText("飞书超链接");

        PostText.Zh_cnEntity.ContentEntity contentEntity3 = new PostText.Zh_cnEntity.ContentEntity();
        contentEntity3.setTag("at");
        contentEntity3.setUser_id("5dbf1798");
        contentEntity3.setUser_name("王帅杰");
        PostText.Zh_cnEntity.ContentEntity contentEntity4 = new PostText.Zh_cnEntity.ContentEntity();
        contentEntity4.setTag("img");
        contentEntity4.setImage_key("img_v2_fc2dacea-0f92-443f-8c03-b6660b260dcg");
        contentEntity4.setWidth(100);
        contentEntity4.setHeight(100);
        listItems.add(contentEntity);
        listItems.add(contentEntity2);
        listItems.add(contentEntity3);
        listItems.add(contentEntity4);
        listTotal.add(listItems);
        List<PostText.Zh_cnEntity.ContentEntity> listItems2 = new ArrayList<>();
        PostText.Zh_cnEntity.ContentEntity contentEntity5 = new PostText.Zh_cnEntity.ContentEntity();
        contentEntity5.setTag("text");
        contentEntity5.setText("第二行");
        listItems2.add(contentEntity5);
        listTotal.add(listItems2);
        zhCh.setContent(listTotal);
        postText.setZh_cn(zhCh);
        //postText.setEn_us(new PostText.Zh_cnEntity());
        message.put("content", mapper.writeValueAsString(postText));
        log.info("发送的Message JSON->"+mapper.writeValueAsString(message));
        //v4旧版本  v1新版本
        Request<Map<String, Object>, HashMap<Object, Object>> request = Request.newRequest("im/v1/messages?receive_id_type=chat_id",
                "POST", AccessTokenType.Tenant, message, new HashMap<>());
        Response<HashMap<Object, Object>> response = Api.send(config, request);
        System.out.println(response.getRequestID());
        System.out.println(response.getHTTPStatusCode());
        System.out.println(Jsons.DEFAULT_GSON.toJson(response));
    }

    /**
     * sendImage
     * 老版发送图片接口
     * @param chatId
     * @throws {@link Exception}
     * @author 王帅杰
     * @history
     */
    private void sendImage(String chatId) throws Exception {
        Map<String, Object> message = new HashMap<>();
        //如果是user_id,机器人回单聊发送信息
        //chat_id，在发送信息的窗口发送信息
        message.put("chat_id", chatId);
        message.put("msg_type", "image");
        Map<String, String> content = new HashMap<>();
        content.put("image_key", "img_v2_fc2dacea-0f92-443f-8c03-b6660b260dcg");
        message.put("content", content);
        //v4旧版本  v1新版本
        Request<Map<String, Object>, Map<String, Object>> request = Request.newRequest("message/v4/send",
                "POST", AccessTokenType.Tenant, message, new HashMap<>());
        Response<Map<String, Object>> response = Api.send(config, request);
        System.out.println(response.getRequestID());
        System.out.println(response.getHTTPStatusCode());
        System.out.println(Jsons.DEFAULT_GSON.toJson(response));
    }

    /**
     * sendImageV1
     * 新版发送图片接口
     * @param chatId
     * @throws {@link Exception}
     * @author 王帅杰
     * @history
     */
    private void sendImageV1(String chatId) throws Exception {
        Map<String, Object> message = new HashMap<>();
        //如果是user_id,机器人回单聊发送信息
        //chat_id，在发送信息的窗口发送信息
        message.put("receive_id", chatId);
        message.put("msg_type", "image");
        Map<String, Object> content = new HashMap<>();
        content.put("image_key", "img_v2_fc2dacea-0f92-443f-8c03-b6660b260dcg");
        content.put("height", 100);
        content.put("width", 100);
        message.put("content", mapper.writeValueAsString(content));
        //v4旧版本  v1新版本
        Request<Map<String, Object>, Map<String, Object>> request = Request.newRequest("im/v1/messages?receive_id_type=chat_id",
                "POST", AccessTokenType.Tenant, message, new HashMap<>());
        Response<Map<String, Object>> response = Api.send(config, request);
        System.out.println(response.getRequestID());
        System.out.println(response.getHTTPStatusCode());
        System.out.println(Jsons.DEFAULT_GSON.toJson(response));
    }

    private String createDept(String sendContent) throws Exception{
        String[] split = sendContent.split("->");
        //测试写死了IT 部门的openId
        DeptInfo deptInfo = new DeptInfo();
        deptInfo.setName(split[1]);
        deptInfo.setLeader_user_id("ou_f4c091cd5dfb69c279d5d9280dc7a175");
        deptInfo.setParent_department_id("0");
        Request<DeptInfo, HashMap<Object, Object>> request = Request.newRequest("contact/v3/departments?department_id_type=open_department_id",
                "POST", AccessTokenType.Tenant, deptInfo, new HashMap<>());
        Response<HashMap<Object, Object>> send = Api.send(config, request);
        if (send.getCode() != 0) {
            return send.getMsg();
        }
        return "创建成功";
    }

    private String deleteDept(String sendContent) throws Exception {
        String[] split = sendContent.split("->");
        //测试写死了IT 部门的openId
        Request<DeptInfo, HashMap<Object, Object>> request = Request.newRequest("contact/v3/departments/"+split[1]+"?department_id_type=open_department_id",
                "DELETE", AccessTokenType.Tenant, null, new HashMap<>());
        Response<HashMap<Object, Object>> send = Api.send(config, request);
        if (send.getCode() != 0) {
            return send.getMsg();
        }
        return "删除成功";
    }

    private String modifyDeptInfo(String sendContent) throws Exception {
        String[] split = sendContent.split("->");
        DeptInfo deptInfo = new DeptInfo();
        deptInfo.setName(split[1]);
        //测试写死了IT 部门的openId
        Request<DeptInfo, HashMap<Object, Object>> request = Request.newRequest("contact/v3/departments/od-189d115e589a7ba50825daf8811a0f47?department_id_type=open_department_id",
                "PATCH", AccessTokenType.Tenant, deptInfo, new HashMap<>());
        Response<HashMap<Object, Object>> send = Api.send(config, request);
        if (send.getCode() != 0) {
            return send.getMsg();
        }
        return "修改成功";
    }

    private String searchDeptByName(String sendContent,String openId) throws Exception{
        String[] split = sendContent.split("->");
        Map<String, Object> message = new HashMap<>();
        message.put("query", split[1]);
        Request<Map<String, Object>, HashMap<Object, Object>> request = Request.newRequest("contact/v3/departments/search",
                "POST", AccessTokenType.User, message, new HashMap<>(),Request.setUserAccessToken(Constant.userMap.get(openId).getAccessToken()));
        Response<HashMap<Object, Object>> send = Api.send(config, request);
        if (send.getCode() != 0) {
            return send.getMsg();
        }
        return mapper.writeValueAsString(send.getData());
    }

    private String getDeptParentInfo(String sendContent) throws Exception {
        Request<CreateUser, HashMap<Object, Object>> request = Request.newRequest("contact/v3/departments/parent?department_id_type=open_department_id&department_id=od-1cf995dee49c5b729948244ca81bff31",
                "GET", AccessTokenType.Tenant, null, new HashMap<>());
        Response<HashMap<Object, Object>> send = Api.send(config, request);
        if (send.getCode() != 0) {
            return send.getMsg();
        }
        return mapper.writeValueAsString(send.getData());
    }

    private String getDeptInfoByOpenId(String sendContent) throws Exception {
        String[] split = sendContent.split("->");
        Request<CreateUser, HashMap<Object, Object>> request = Request.newRequest("contact/v3/departments/"+split[1],
                "GET", AccessTokenType.Tenant, null, new HashMap<>());
        Response<HashMap<Object, Object>> send = Api.send(config, request);
        if (send.getCode() != 0) {
            return send.getMsg();
        }
        return mapper.writeValueAsString(send.getData());

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



    @GetMapping("/uploadImage")
    public String uploadImage() throws Exception {
        FormData formData = new FormData();
        formData.addField("image_type", "message");
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("/static/test2.png");
        formData.addFile("image", new FormDataFile().setContentStream(inputStream));
        Request<FormData, Map<String, Object>> request = Request.newRequest("image/v4/put",
                "POST", AccessTokenType.Tenant, formData, new HashMap<>());
        Response<Map<String, Object>> response = Api.send(config, request);
        System.out.println(response.getRequestID());
        System.out.println(response.getHTTPStatusCode());
        System.out.println(Jsons.DEFAULT_GSON.toJson(response));
        return "上传成功";
    }

}
