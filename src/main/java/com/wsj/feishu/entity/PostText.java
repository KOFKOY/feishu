package com.wsj.feishu.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

public class PostText {

    /**
     * zh_cn : {"title":"我是一个标题","content":[[{"tag":"text","text":"第一行 :"},{"tag":"a","href":"http://www.feishu.cn","text":"超链接"},{"user_id":"ou_1avnmsbv3k45jnk34j5","user_name":"tom","tag":"at"}],[{"image_key":"img_47354fbc-a159-40ed-86ab-2ad0f1acb42g","width":300,"tag":"img","height":300}],[{"tag":"text","text":"第二行:"},{"tag":"text","text":"文本测试"}],[{"image_key":"img_47354fbc-a159-40ed-86ab-2ad0f1acb42g","width":300,"tag":"img","height":300}]]}
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Zh_cnEntity zh_cn;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Zh_cnEntity en_us;

    public Zh_cnEntity getEn_us() {
        return en_us;
    }

    public void setEn_us(Zh_cnEntity en_us) {
        this.en_us = en_us;
    }

    public void setZh_cn(Zh_cnEntity zh_cn) {
        this.zh_cn = zh_cn;
    }

    public Zh_cnEntity getZh_cn() {
        return zh_cn;
    }

    public static class Zh_cnEntity {
        /**
         * title : 我是一个标题
         * content : [[{"tag":"text","text":"第一行 :"},{"tag":"a","href":"http://www.feishu.cn","text":"超链接"},{"user_id":"ou_1avnmsbv3k45jnk34j5","user_name":"tom","tag":"at"}],[{"image_key":"img_47354fbc-a159-40ed-86ab-2ad0f1acb42g","width":300,"tag":"img","height":300}],[{"tag":"text","text":"第二行:"},{"tag":"text","text":"文本测试"}],[{"image_key":"img_47354fbc-a159-40ed-86ab-2ad0f1acb42g","width":300,"tag":"img","height":300}]]
         */
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String title;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private List<List<ContentEntity>> content;

        public void setTitle(String title) {
            this.title = title;
        }

        public void setContent(List<List<ContentEntity>> content) {
            this.content = content;
        }

        public String getTitle() {
            return title;
        }

        public List<List<ContentEntity>> getContent() {
            return content;
        }

        public static class ContentEntity {
            /**
             * tag : text
             * text : 第一行 :
             */
            @JsonInclude(JsonInclude.Include.NON_NULL)
            private String tag;
            @JsonInclude(JsonInclude.Include.NON_NULL)
            private String text;
            @JsonInclude(JsonInclude.Include.NON_NULL)
            private String href;
            @JsonInclude(JsonInclude.Include.NON_NULL)
            private String user_id;
            @JsonInclude(JsonInclude.Include.NON_NULL)
            private String user_name;
            @JsonInclude(JsonInclude.Include.NON_NULL)
            private String image_key;
            @JsonInclude(JsonInclude.Include.NON_NULL)
            private int width;
            @JsonInclude(JsonInclude.Include.NON_NULL)
            private int height;

            public String getHref() {
                return href;
            }

            public void setHref(String href) {
                this.href = href;
            }

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
            }

            public String getImage_key() {
                return image_key;
            }

            public void setImage_key(String image_key) {
                this.image_key = image_key;
            }

            public int getWidth() {
                return width;
            }

            public void setWidth(int width) {
                this.width = width;
            }

            public int getHeight() {
                return height;
            }

            public void setHeight(int height) {
                this.height = height;
            }

            public void setTag(String tag) {
                this.tag = tag;
            }

            public void setText(String text) {
                this.text = text;
            }

            public String getTag() {
                return tag;
            }

            public String getText() {
                return text;
            }
        }
    }
}
