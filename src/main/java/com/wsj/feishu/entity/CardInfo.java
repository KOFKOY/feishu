package com.wsj.feishu.entity;

import com.wsj.feishu.entity.card.ElementsEntity;
import com.wsj.feishu.entity.card.HeaderEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.annotation.security.DenyAll;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardInfo {

    /**
     * open_id : ou_f4c091cd5dfb69c279d5d9280dc7a175
     * msg_type : interactive
     * update_multi : false
     * card : {"elements":[{"tag":"div","fields":[{"text":{"tag":"lark_md","content":"XX同学的进击之路"},"is_short":false},{"text":{"tag":"lark_md","content":"**申请人:**王晓磊"},"is_short":true},{"text":{"tag":"lark_md","content":""},"is_short":false},{"text":{"tag":"lark_md","content":"**时间：**\n2020-4-8 至 2020-4-10（共3天）"},"is_short":false},{"text":{"tag":"lark_md","content":""},"is_short":false}]},{"tag":"hr"},{"tag":"div","text":{"tag":"lark_md","content":"[点击查看详情](https://www.baidu.com)"}}],"header":{"title":{"tag":"plain_text","content":"待办审批"}},"config":{"wide_screen_mode":true}}
     */
    private String open_id;
    private String msg_type;
    private boolean update_multi;
    private CardEntity card;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CardEntity {
        /**
         * elements : [{"tag":"div","fields":[{"text":{"tag":"lark_md","content":"XX同学的进击之路"},"is_short":false},{"text":{"tag":"lark_md","content":"**申请人:**王晓磊"},"is_short":true},{"text":{"tag":"lark_md","content":""},"is_short":false},{"text":{"tag":"lark_md","content":"**时间：**\n2020-4-8 至 2020-4-10（共3天）"},"is_short":false},{"text":{"tag":"lark_md","content":""},"is_short":false}]},{"tag":"hr"},{"tag":"div","text":{"tag":"lark_md","content":"[点击查看详情](https://www.baidu.com)"}}]
         * header : {"title":{"tag":"plain_text","content":"待办审批"}}
         * config : {"wide_screen_mode":true}
         */
        private List<ElementsEntity> elements;
        private HeaderEntity header;
        private ConfigEntity config;

        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        public static class ConfigEntity {
            /**
             * wide_screen_mode : true  是否根据屏幕宽度动态调整消息卡片宽度，默认值为true
             */
            private boolean wide_screen_mode = true;
            /**
             * 是否允许卡片被转发，默认 false
             */
            private boolean enable_forward = false;
        }
    }
}

