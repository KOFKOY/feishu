package com.wsj.feishu.entity.card;

import com.wsj.feishu.entity.CardInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ElementsEntity {
    /**
     * tag : div
     * fields : [{"text":{"tag":"lark_md","content":"XX同学的进击之路"},"is_short":false},{"text":{"tag":"lark_md","content":"**申请人:**王晓磊"},"is_short":true},{"text":{"tag":"lark_md","content":""},"is_short":false},{"text":{"tag":"lark_md","content":"**时间：**\n2020-4-8 至 2020-4-10（共3天）"},"is_short":false},{"text":{"tag":"lark_md","content":""},"is_short":false}]
     */
    private String tag;
    private List<FieldsEntity> fields;
    private TextEntity text;
    /**
     * 图片模块
     */
    private TextEntity alt;
    private ExtraEntity extra;
    private String mode;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ExtraEntity {
        private String tag;
        private String img_key;
        private TextEntity alt;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FieldsEntity {
        /**
         * text : {"tag":"lark_md","content":"XX同学的进击之路"}
         * is_short : false
         */
        private TextEntity text;
        private boolean is_short;
    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TextEntity {
        /**
         * tag : lark_md
         * content : XX同学的进击之路
         */
        private String tag;
        private String content;
    }
}
