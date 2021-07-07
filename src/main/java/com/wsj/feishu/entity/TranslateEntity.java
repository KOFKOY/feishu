package com.wsj.feishu.entity;

import java.util.List;

public class TranslateEntity {
    /**
     * glossary : [{"from":"飞书","to":"Lark"},{"from":"王帅杰","to":"Wsj"}]
     * source_language : zh
     * target_language : en
     * text : 我叫王帅杰，你呢
     */
    private List<GlossaryEntity> glossary;
    private String source_language;
    private String target_language;
    private String text;

    public void setGlossary(List<GlossaryEntity> glossary) {
        this.glossary = glossary;
    }

    public void setSource_language(String source_language) {
        this.source_language = source_language;
    }

    public void setTarget_language(String target_language) {
        this.target_language = target_language;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<GlossaryEntity> getGlossary() {
        return glossary;
    }

    public String getSource_language() {
        return source_language;
    }

    public String getTarget_language() {
        return target_language;
    }

    public String getText() {
        return text;
    }

    public static class GlossaryEntity {
        /**
         * from : 飞书
         * to : Lark
         */
        private String from;
        private String to;

        public void setFrom(String from) {
            this.from = from;
        }

        public void setTo(String to) {
            this.to = to;
        }

        public String getFrom() {
            return from;
        }

        public String getTo() {
            return to;
        }
    }
}
