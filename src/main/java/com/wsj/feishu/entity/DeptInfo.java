package com.wsj.feishu.entity;

import java.util.List;

public class DeptInfo {
    /**
     * create_group_chat : false
     * i18n_name : {"en_us":"Demo Name","zh_cn":"Demo名称","ja_jp":"デモ名"}
     * name : DemoName
     * leader_user_id : ou_7dab8a3d3cdcc9da365777c7ad535d62
     * unit_ids : ["custom_unit_id"]
     * parent_department_id : od-4e6ac4d14bcd5071a37a39de902c7141
     * order : 100
     */
    private boolean create_group_chat;
    private I18n_nameEntity i18n_name;
    private String name;
    private String leader_user_id;
    private List<String> unit_ids;
    private String parent_department_id;
    private String order;

    public void setCreate_group_chat(boolean create_group_chat) {
        this.create_group_chat = create_group_chat;
    }

    public void setI18n_name(I18n_nameEntity i18n_name) {
        this.i18n_name = i18n_name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLeader_user_id(String leader_user_id) {
        this.leader_user_id = leader_user_id;
    }

    public void setUnit_ids(List<String> unit_ids) {
        this.unit_ids = unit_ids;
    }

    public void setParent_department_id(String parent_department_id) {
        this.parent_department_id = parent_department_id;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public boolean isCreate_group_chat() {
        return create_group_chat;
    }

    public I18n_nameEntity getI18n_name() {
        return i18n_name;
    }

    public String getName() {
        return name;
    }

    public String getLeader_user_id() {
        return leader_user_id;
    }

    public List<String> getUnit_ids() {
        return unit_ids;
    }

    public String getParent_department_id() {
        return parent_department_id;
    }

    public String getOrder() {
        return order;
    }

    public class I18n_nameEntity {
        /**
         * en_us : Demo Name
         * zh_cn : Demo名称
         * ja_jp : デモ名
         */
        private String en_us;
        private String zh_cn;
        private String ja_jp;

        public void setEn_us(String en_us) {
            this.en_us = en_us;
        }

        public void setZh_cn(String zh_cn) {
            this.zh_cn = zh_cn;
        }

        public void setJa_jp(String ja_jp) {
            this.ja_jp = ja_jp;
        }

        public String getEn_us() {
            return en_us;
        }

        public String getZh_cn() {
            return zh_cn;
        }

        public String getJa_jp() {
            return ja_jp;
        }
    }
}
