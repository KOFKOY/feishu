package com.wsj.feishu.entity;

import java.util.List;

public class CreateUser {

    /**
     * need_send_notification : false
     * country : 中国
     * work_station : 杭州
     * notification_option : {"channels":["sms"],"language":"zh-CN"}
     * gender : 1
     * avatar_key : 2500c7a9-5fff-4d9a-a2de-3d59614ae28g
     * city : 杭州
     * mobile : 13011111111
     * employee_no : 1
     * department_ids : ["od-4e6ac4d14bcd5071a37a39de902c7141"]
     * join_time : 2147483647
     * enterprise_email : demo@mail.com
     * employee_type : 1
     * user_id : ou_7dab8a3d3cdcc9da365777c7ad535d62
     * name : 张三
     * custom_attrs : [{"id":"DemoId","type":"TEXT","value":{"text":"DemoText","pc_url":"http://www.feishu.cn","url":"http://www.feishu.cn"}}]
     * en_name : San Zhang
     * orders : [{"user_order":100,"department_id":"od-4e6ac4d14bcd5071a37a39de902c7141","department_order":100}]
     * leader_user_id : ou_7dab8a3d3cdcc9da365777c7ad535d62
     * job_title : xxxxx
     * email : zhangsan@gmail.com
     * mobile_visible : false
     */
    private boolean need_send_notification;
    private String country;
    private String work_station;
    private Notification_optionEntity notification_option;
    private int gender;
    private String avatar_key;
    private String city;
    private String mobile;
    private String employee_no;
    private List<String> department_ids;
    private int join_time;
    private String enterprise_email;
    private int employee_type;
    private String user_id;
    private String name;
    private List<Custom_attrsEntity> custom_attrs;
    private String en_name;
    private List<OrdersEntity> orders;
    private String leader_user_id;
    private String job_title;
    private String email;
    private boolean mobile_visible;

    public void setNeed_send_notification(boolean need_send_notification) {
        this.need_send_notification = need_send_notification;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setWork_station(String work_station) {
        this.work_station = work_station;
    }

    public void setNotification_option(Notification_optionEntity notification_option) {
        this.notification_option = notification_option;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public void setAvatar_key(String avatar_key) {
        this.avatar_key = avatar_key;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setEmployee_no(String employee_no) {
        this.employee_no = employee_no;
    }

    public void setDepartment_ids(List<String> department_ids) {
        this.department_ids = department_ids;
    }

    public void setJoin_time(int join_time) {
        this.join_time = join_time;
    }

    public void setEnterprise_email(String enterprise_email) {
        this.enterprise_email = enterprise_email;
    }

    public void setEmployee_type(int employee_type) {
        this.employee_type = employee_type;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCustom_attrs(List<Custom_attrsEntity> custom_attrs) {
        this.custom_attrs = custom_attrs;
    }

    public void setEn_name(String en_name) {
        this.en_name = en_name;
    }

    public void setOrders(List<OrdersEntity> orders) {
        this.orders = orders;
    }

    public void setLeader_user_id(String leader_user_id) {
        this.leader_user_id = leader_user_id;
    }

    public void setJob_title(String job_title) {
        this.job_title = job_title;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMobile_visible(boolean mobile_visible) {
        this.mobile_visible = mobile_visible;
    }

    public boolean isNeed_send_notification() {
        return need_send_notification;
    }

    public String getCountry() {
        return country;
    }

    public String getWork_station() {
        return work_station;
    }

    public Notification_optionEntity getNotification_option() {
        return notification_option;
    }

    public int getGender() {
        return gender;
    }

    public String getAvatar_key() {
        return avatar_key;
    }

    public String getCity() {
        return city;
    }

    public String getMobile() {
        return mobile;
    }

    public String getEmployee_no() {
        return employee_no;
    }

    public List<String> getDepartment_ids() {
        return department_ids;
    }

    public int getJoin_time() {
        return join_time;
    }

    public String getEnterprise_email() {
        return enterprise_email;
    }

    public int getEmployee_type() {
        return employee_type;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getName() {
        return name;
    }

    public List<Custom_attrsEntity> getCustom_attrs() {
        return custom_attrs;
    }

    public String getEn_name() {
        return en_name;
    }

    public List<OrdersEntity> getOrders() {
        return orders;
    }

    public String getLeader_user_id() {
        return leader_user_id;
    }

    public String getJob_title() {
        return job_title;
    }

    public String getEmail() {
        return email;
    }

    public boolean isMobile_visible() {
        return mobile_visible;
    }

    public class Notification_optionEntity {
        /**
         * channels : ["sms"]
         * language : zh-CN
         */
        private List<String> channels;
        private String language;

        public void setChannels(List<String> channels) {
            this.channels = channels;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public List<String> getChannels() {
            return channels;
        }

        public String getLanguage() {
            return language;
        }
    }

    public class Custom_attrsEntity {
        /**
         * id : DemoId
         * type : TEXT
         * value : {"text":"DemoText","pc_url":"http://www.feishu.cn","url":"http://www.feishu.cn"}
         */
        private String id;
        private String type;
        private ValueEntity value;

        public void setId(String id) {
            this.id = id;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setValue(ValueEntity value) {
            this.value = value;
        }

        public String getId() {
            return id;
        }

        public String getType() {
            return type;
        }

        public ValueEntity getValue() {
            return value;
        }

        public class ValueEntity {
            /**
             * text : DemoText
             * pc_url : http://www.feishu.cn
             * url : http://www.feishu.cn
             */
            private String text;
            private String pc_url;
            private String url;

            public void setText(String text) {
                this.text = text;
            }

            public void setPc_url(String pc_url) {
                this.pc_url = pc_url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getText() {
                return text;
            }

            public String getPc_url() {
                return pc_url;
            }

            public String getUrl() {
                return url;
            }
        }
    }

    public class OrdersEntity {
        /**
         * user_order : 100
         * department_id : od-4e6ac4d14bcd5071a37a39de902c7141
         * department_order : 100
         */
        private int user_order;
        private String department_id;
        private int department_order;

        public void setUser_order(int user_order) {
            this.user_order = user_order;
        }

        public void setDepartment_id(String department_id) {
            this.department_id = department_id;
        }

        public void setDepartment_order(int department_order) {
            this.department_order = department_order;
        }

        public int getUser_order() {
            return user_order;
        }

        public String getDepartment_id() {
            return department_id;
        }

        public int getDepartment_order() {
            return department_order;
        }
    }
}
