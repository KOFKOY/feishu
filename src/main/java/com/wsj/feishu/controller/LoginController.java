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

import javax.annotation.PostConstruct;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/login")
public class LoginController{
    @Autowired
    private Config config;
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
    public String scanCodeLogin(String code,String state) throws Exception {
        log.info("GET 扫码登录");
        log.info("CODE---->" + code);
        log.info("STATE---->" + state);
        //TODO 获取code，获取tenant_access_token 获取用户信息
        //https://open.feishu.cn/open-apis/authen/v1/access_token
        AuthenService service = new AuthenService(config);
        AuthenAccessTokenReqBody body = new AuthenAccessTokenReqBody();
        body.setGrantType("authorization_code");
        body.setCode(code);
        AuthenService.AuthenAccessTokenReqCall reqCall = service.getAuthens().accessToken(body);
        Response<UserAccessTokenInfo> response = reqCall.execute();
        System.out.println(Jsons.DEFAULT_GSON.toJson(response));
        System.out.println(Jsons.DEFAULT_GSON.toJson(response.getData()));
        System.out.println(response.getRequestID());
        UserAccessTokenInfo data = response.getData();
        Constant.userMap.put(data.getOpenId(), data);
        return null;
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
