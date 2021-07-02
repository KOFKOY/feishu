package com.wsj.feishu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * @author 王帅杰
 * @version 2.0
 * @package com.wsj.feishu.entity
 * @title Encrypt
 * @description  用于事件订阅请求地址验证
 * @date 2021-07-01 17:21:55
 * @Copyright 2019 www.zielsmart.com Inc. All rights reserved
 * 注意：本内容仅限于郑州致欧网络科技有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
@Data
@AllArgsConstructor
@ToString
public class Encrypt {

    /**
     * challenge
     */
    private String challenge;
    /**
     * type
     */
    private String type;
    /**
     * token
     */
    private String token;
    /**
     * encrypt
     */
    private String encrypt;

}
