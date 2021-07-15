package com.wsj.feishu.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.larksuite.oapi.core.Config;
import com.larksuite.oapi.core.Context;
import com.larksuite.oapi.core.Decrypt;
import com.larksuite.oapi.core.api.request.Request;
import com.larksuite.oapi.core.api.response.Response;
import com.larksuite.oapi.core.event.DefaultHandler;
import com.larksuite.oapi.core.event.Event;
import com.larksuite.oapi.core.event.EventServlet;
import com.larksuite.oapi.core.utils.Jsons;
import com.larksuite.oapi.service.authen.v1.AuthenService;
import com.larksuite.oapi.service.authen.v1.model.AuthenAccessTokenReqBody;
import com.larksuite.oapi.service.authen.v1.model.AuthenRefreshAccessTokenReqBody;
import com.larksuite.oapi.service.authen.v1.model.UserAccessTokenInfo;
import com.larksuite.oapi.service.authen.v1.model.UserInfo;
import com.wsj.feishu.constant.Constant;
import com.wsj.feishu.entity.Encrypt;
import com.wsj.feishu.entity.SubscribeInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/login")
public class LoginController{
    @Autowired
    private Config config;
    @Autowired
    private ObjectMapper mapper;
    //key ->state
    private Map<String, Map<String,String>> redis模拟 = new HashMap<>();

    @GetMapping("/test2")
    public void test(){
        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxx");
    }
    /**
     * scanCodeLogin
     * 扫码登录
     * @param code
     * @param state
     * @return {@link java.lang.String}
     * @author 王帅杰
     * @history
     */
    @GetMapping
    public void scanCodeLogin(String code, String state,HttpServletResponse resp) throws Exception {
        log.info("GET 扫码登录");
        log.info("CODE---->" + code);
        log.info("STATE---->" + state);
        //https://open.feishu.cn/open-apis/authen/v1/access_token
        String url = null;
        if (state.equals("baidu")) {
            url = "http://www.baidu.com";
        } else if (state.equals("google")) {
            url = "https://translate.google.cn/";
        } else if (state.equals("github")) {
            url = "https://github.com/";
        }
        AuthenService service = new AuthenService(config);
        AuthenAccessTokenReqBody body = new AuthenAccessTokenReqBody();
        body.setGrantType("authorization_code");
        body.setCode(code);
        AuthenService.AuthenAccessTokenReqCall reqCall = service.getAuthens().accessToken(body);
        Response<UserAccessTokenInfo> response = reqCall.execute();
        UserAccessTokenInfo data = response.getData();
        System.out.println(Jsons.DEFAULT_GSON.toJson(data));
        Constant.userMap.put(data.getOpenId(), data);
        //登录成功 保存到redis
        Map<String, String> temp = new HashMap<>();
        temp.put("url", url);
        temp.put("name", data.getName());
        temp.put("userId", data.getUserId());
        redis模拟.put(state,temp);
        resp.sendRedirect(url);
    }

    @GetMapping("/test")
    public String test(String code) throws JsonProcessingException {
        log.info("轮询->" + code);
        code = "baidu";
        String result = null;
        if (redis模拟.getOrDefault(code, null) != null) {
            result = mapper.writeValueAsString(redis模拟.get(code));
            redis模拟.remove(code);
        }else{
            code = "github";
            if (redis模拟.getOrDefault(code, null) != null) {
                result = mapper.writeValueAsString(redis模拟.get(code));
                redis模拟.remove(code);
            }
        }
        return result;
    }

    /**
     * getUserInfoByAaccessToken
     * 根据AccessToken获取用户信息
     * @throws {@link Exception}
     * @author 王帅杰
     * @history
     */
    private  void getUserInfoByAccessToken(String accessToken) throws Exception {
        AuthenService service = new AuthenService(config);
        AuthenService.AuthenUserInfoReqCall reqCall = service.getAuthens().userInfo(Request.setUserAccessToken(accessToken));
        Response<UserInfo> response = reqCall.execute();
        System.out.println(Jsons.DEFAULT_GSON.toJson(response.getData()));
        System.out.println(response.getRequestID());
    }


    /**
     * refreshAccessToken
     * 刷新AccessToken
     * @param refreshToken
     * @throws {@link Exception}
     * @author 王帅杰
     * @history
     */
    private  void refreshAccessToken(String refreshToken) throws Exception {
        AuthenService service = new AuthenService(config);
        AuthenRefreshAccessTokenReqBody body = new AuthenRefreshAccessTokenReqBody();
        body.setGrantType("refresh_token");
        body.setRefreshToken(refreshToken);
        AuthenService.AuthenRefreshAccessTokenReqCall reqCall = service.getAuthens().refreshAccessToken(body);
        Response<UserAccessTokenInfo> response = reqCall.execute();
        System.out.println(Jsons.DEFAULT_GSON.toJson(response));
        System.out.println(response.getRequestID());

    }
}
