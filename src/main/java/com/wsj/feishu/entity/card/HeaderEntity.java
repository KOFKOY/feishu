package com.wsj.feishu.entity.card;

import com.wsj.feishu.entity.CardInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public  class HeaderEntity {
    /**
     * title : {"tag":"plain_text","content":"待办审批"}
     */
    private TitleEntity title;
    /**
     * 颜色 blue wathet turquoise.....详情见https://open.feishu.cn/document/ukTMukTMukTM/ukTNwUjL5UDM14SO1ATN
     */
    private String template;

    @Data
    @AllArgsConstructor
    public static class TitleEntity {
        /**
         * tag : plain_text （仅支持"plain_text")
         * content : 待办审批
         */
        private String tag;
        private String content;
    }
}
