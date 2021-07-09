package com.wsj.feishu.controller;

import com.larksuite.oapi.core.AppSettings;
import com.larksuite.oapi.core.Config;
import com.larksuite.oapi.core.DefaultStore;
import com.larksuite.oapi.core.Domain;
import com.larksuite.oapi.core.api.request.Request;
import com.larksuite.oapi.core.api.response.Response;
import com.larksuite.oapi.core.utils.Jsons;
import com.larksuite.oapi.service.authen.v1.AuthenService;
import com.larksuite.oapi.service.authen.v1.model.AuthenAccessTokenReqBody;
import com.larksuite.oapi.service.authen.v1.model.UserAccessTokenInfo;
import com.larksuite.oapi.service.authen.v1.model.UserInfo;
import com.wsj.feishu.entity.Encrypt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/generalOrder")
public class GeneralOrderController {

    AppSettings appSettings = Config.createInternalAppSettings("cli_a062aadfedb9d00e","cyGUzj2UmxF3zVt5Coy33bSicdhduVV3","lcExBvKCWJ1AXFreB6XkdCgesoCpDMyr","");
    Config config = new Config(Domain.FeiShu, appSettings, new DefaultStore());//RedisStore

    @PostMapping
    public void login(HttpServletResponse res) throws IOException {
        log.info("POST测试");
        res.sendRedirect("test.html");
    }

    @GetMapping
    public void getlogin(String code, String state,HttpServletResponse res) throws Exception {
        log.info("GET测试");
        log.info("CODE->"+code);
        log.info("state->"+state);
        AuthenService service = new AuthenService(config);
        AuthenAccessTokenReqBody body = new AuthenAccessTokenReqBody();
        body.setGrantType("authorization_code");
        body.setCode(code);
        AuthenService.AuthenAccessTokenReqCall reqCall = service.getAuthens().accessToken(body);
        Response<UserAccessTokenInfo> response = reqCall.execute();
        res.sendRedirect("test.html?userId="+response.getData().getUserId());
    }

    private  UserInfo getUserInfoByAccessToken(String accessToken) throws Exception {
        AuthenService service = new AuthenService(config);
        AuthenService.AuthenUserInfoReqCall reqCall = service.getAuthens().userInfo(Request.setUserAccessToken(accessToken));
        Response<UserInfo> response = reqCall.execute();
        return response.getData();
    }
}
