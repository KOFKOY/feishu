package com.wsj.feishu.constant;

import com.larksuite.oapi.service.authen.v1.model.UserAccessTokenInfo;

import java.util.HashMap;
import java.util.Map;

public class Constant {
    //key: openId/openId+refreshToken  value: userInfo
    public static Map<String, UserAccessTokenInfo> userMap = new HashMap<>();

}
