package com.wsj.feishu.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class SubscribeInfo {

    /**
     * schema : 2.0
     * header : {"event_id":"9afe3dbbad3db911c11875739a2d2bda","event_type":"im.message.receive_v1","tenant_key":"13b9b129f00f975d","create_time":"1625199198141","app_id":"cli_a05687963b38500b","token":"x4IMRuzr0qDzThWyy18V4bbrtZqAR1Zr"}
     * event : {"sender":{"tenant_key":"13b9b129f00f975d","sender_type":"user","sender_id":{"open_id":"ou_f4c091cd5dfb69c279d5d9280dc7a175","user_id":"5dbf1798","union_id":"on_36f1cf0f72559e710247ce245811b2f7"}},"message":{"chat_type":"group","create_time":"1625199197805","mentions":[{"tenant_key":"13b9b129f00f975d","name":"致欧测试","id":{"open_id":"ou_143f589239271a461fa964fdb869ccb8","user_id":"","union_id":"on_8c27014ede61f60e1eaa280d36624f93"},"key":"@_user_1"}],"message_id":"om_5e22a089a11a0e87a708d9f15485d252","message_type":"text","content":"{\"text\":\"@_user_1 555\"}","chat_id":"oc_99112c5ae3aadd4fd9304d4b23effc88"}}
     */
    private String schema;
    private HeaderEntity header;
    private EventEntity event;

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public void setHeader(HeaderEntity header) {
        this.header = header;
    }

    public void setEvent(EventEntity event) {
        this.event = event;
    }

    public String getSchema() {
        return schema;
    }

    public HeaderEntity getHeader() {
        return header;
    }

    public EventEntity getEvent() {
        return event;
    }
    public static class HeaderEntity {
        /**
         * event_id : 9afe3dbbad3db911c11875739a2d2bda
         * event_type : im.message.receive_v1
         * tenant_key : 13b9b129f00f975d
         * create_time : 1625199198141
         * app_id : cli_a05687963b38500b
         * token : x4IMRuzr0qDzThWyy18V4bbrtZqAR1Zr
         */
        private String event_id;
        private String event_type;
        private String tenant_key;
        private String create_time;
        private String app_id;
        private String token;

        public void setEvent_id(String event_id) {
            this.event_id = event_id;
        }

        public void setEvent_type(String event_type) {
            this.event_type = event_type;
        }

        public void setTenant_key(String tenant_key) {
            this.tenant_key = tenant_key;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public void setApp_id(String app_id) {
            this.app_id = app_id;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getEvent_id() {
            return event_id;
        }

        public String getEvent_type() {
            return event_type;
        }

        public String getTenant_key() {
            return tenant_key;
        }

        public String getCreate_time() {
            return create_time;
        }

        public String getApp_id() {
            return app_id;
        }

        public String getToken() {
            return token;
        }
    }
    @NoArgsConstructor
    public static class EventEntity {
        /**
         * sender : {"tenant_key":"13b9b129f00f975d","sender_type":"user","sender_id":{"open_id":"ou_f4c091cd5dfb69c279d5d9280dc7a175","user_id":"5dbf1798","union_id":"on_36f1cf0f72559e710247ce245811b2f7"}}
         * message : {"chat_type":"group","create_time":"1625199197805","mentions":[{"tenant_key":"13b9b129f00f975d","name":"致欧测试","id":{"open_id":"ou_143f589239271a461fa964fdb869ccb8","user_id":"","union_id":"on_8c27014ede61f60e1eaa280d36624f93"},"key":"@_user_1"}],"message_id":"om_5e22a089a11a0e87a708d9f15485d252","message_type":"text","content":"{\"text\":\"@_user_1 555\"}","chat_id":"oc_99112c5ae3aadd4fd9304d4b23effc88"}
         */
        private String type;
        private String chat_id;

        public String getChat_id() {
            return chat_id;
        }

        public void setChat_id(String chat_id) {
            this.chat_id = chat_id;
        }

        private List<UsersEntity> users;
        private SenderEntity sender;
        private MessageEntity message;
        private EventEntity.Old_objectEntity old_object;
        private EventEntity.ObjectEntity object;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public List<UsersEntity> getUsers() {
            return users;
        }

        public void setUsers(List<UsersEntity> users) {
            this.users = users;
        }

        public void setSender(SenderEntity sender) {
            this.sender = sender;
        }

        public static class UsersEntity{
            private String name;
            private String open_id;
            private String user_id;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getOpen_id() {
                return open_id;
            }

            public void setOpen_id(String open_id) {
                this.open_id = open_id;
            }

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }
        }
        /**
         * setOld_object
         *
         * @param old_object
         * @author 王帅杰
         * @history
         */
        public void setOld_object(EventEntity.Old_objectEntity old_object) {
            this.old_object = old_object;
        }

        /**
         * setObject
         *
         * @param object
         * @author 王帅杰
         * @history
         */
        public void setObject(EventEntity.ObjectEntity object) {
            this.object = object;
        }

        /**
         * getOld_object
         *
         * @return {@link com.wsj.feishu.entity.EventEntity.Old_objectEntity}
         * @author 王帅杰
         * @history
         */
        public EventEntity.Old_objectEntity getOld_object() {
            return old_object;
        }

        /**
         * getObject
         *
         * @return {@link com.wsj.feishu.entity.EventEntity.ObjectEntity}
         * @author 王帅杰
         * @history
         */
        public EventEntity.ObjectEntity getObject() {
            return object;
        }

        /**
         * @author 王帅杰
         * @version 2.0
         * @package com.wsj.feishu.entity
         * @title Old_objectEntity
         * @description
         * @date 2021-07-05 16:01:17
         * @Copyright 2019 www.zielsmart.com Inc. All rights reserved
         * 注意：本内容仅限于郑州致欧网络科技有限公司内部传阅，禁止外泄以及用于其他的商业目的
         */
        public static class Old_objectEntity {
            /**
             * name
             */
            private String name;

            /**
             * setName
             *
             * @param name
             * @author 王帅杰
             * @history
             */
            public void setName(String name) {
                this.name = name;
            }

            /**
             * getName
             *
             * @return {@link java.lang.String}
             * @author 王帅杰
             * @history
             */
            public String getName() {
                return name;
            }
        }

        /**
         * @author 王帅杰
         * @version 2.0
         * @package com.wsj.feishu.entity
         * @title ObjectEntity
         * @description
         * @date 2021-07-05 16:01:17
         * @Copyright 2019 www.zielsmart.com Inc. All rights reserved
         * 注意：本内容仅限于郑州致欧网络科技有限公司内部传阅，禁止外泄以及用于其他的商业目的
         */
        public static class ObjectEntity {
            /**
             * country
             */
            private String country;
            /**
             * work_station
             */
            private String work_station;
            /**
             * gender
             */
            private int gender;
            /**
             * city
             */
            private String city;
            /**
             * open_id
             */
            private String open_id;
            /**
             * mobile
             */
            private String mobile;
            /**
             * employee_no
             */
            private String employee_no;
            /**
             * avatar
             */
            private EventEntity.ObjectEntity.AvatarEntity avatar;
            /**
             * department_ids
             */
            private List<String> department_ids;
            /**
             * employee_type
             */
            private int employee_type;
            /**
             * user_id
             */
            private String user_id;
            /**
             * name
             */
            private String name;
            /**
             * en_name
             */
            private String en_name;
            /**
             * orders
             */
            private List<EventEntity.ObjectEntity.OrdersEntity> orders;
            /**
             * leader_user_id
             */
            private String leader_user_id;
            /**
             * email
             */
            private String email;
            /**
             * status
             */
            private EventEntity.ObjectEntity.StatusEntity status;

            /**
             * setCountry
             *
             * @param country
             * @author 王帅杰
             * @history
             */
            public void setCountry(String country) {
                this.country = country;
            }

            /**
             * setWork_station
             *
             * @param work_station
             * @author 王帅杰
             * @history
             */
            public void setWork_station(String work_station) {
                this.work_station = work_station;
            }

            /**
             * setGender
             *
             * @param gender
             * @author 王帅杰
             * @history
             */
            public void setGender(int gender) {
                this.gender = gender;
            }

            /**
             * setCity
             *
             * @param city
             * @author 王帅杰
             * @history
             */
            public void setCity(String city) {
                this.city = city;
            }

            /**
             * setOpen_id
             *
             * @param open_id
             * @author 王帅杰
             * @history
             */
            public void setOpen_id(String open_id) {
                this.open_id = open_id;
            }

            /**
             * setMobile
             *
             * @param mobile
             * @author 王帅杰
             * @history
             */
            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            /**
             * setEmployee_no
             *
             * @param employee_no
             * @author 王帅杰
             * @history
             */
            public void setEmployee_no(String employee_no) {
                this.employee_no = employee_no;
            }

            /**
             * setAvatar
             *
             * @param avatar
             * @author 王帅杰
             * @history
             */
            public void setAvatar(EventEntity.ObjectEntity.AvatarEntity avatar) {
                this.avatar = avatar;
            }

            /**
             * setDepartment_ids
             *
             * @param department_ids
             * @author 王帅杰
             * @history
             */
            public void setDepartment_ids(List<String> department_ids) {
                this.department_ids = department_ids;
            }

            /**
             * setEmployee_type
             *
             * @param employee_type
             * @author 王帅杰
             * @history
             */
            public void setEmployee_type(int employee_type) {
                this.employee_type = employee_type;
            }

            /**
             * setUser_id
             *
             * @param user_id
             * @author 王帅杰
             * @history
             */
            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            /**
             * setName
             *
             * @param name
             * @author 王帅杰
             * @history
             */
            public void setName(String name) {
                this.name = name;
            }

            /**
             * setEn_name
             *
             * @param en_name
             * @author 王帅杰
             * @history
             */
            public void setEn_name(String en_name) {
                this.en_name = en_name;
            }

            /**
             * setOrders
             *
             * @param orders
             * @author 王帅杰
             * @history
             */
            public void setOrders(List<EventEntity.ObjectEntity.OrdersEntity> orders) {
                this.orders = orders;
            }

            /**
             * setLeader_user_id
             *
             * @param leader_user_id
             * @author 王帅杰
             * @history
             */
            public void setLeader_user_id(String leader_user_id) {
                this.leader_user_id = leader_user_id;
            }

            /**
             * setEmail
             *
             * @param email
             * @author 王帅杰
             * @history
             */
            public void setEmail(String email) {
                this.email = email;
            }

            /**
             * setStatus
             *
             * @param status
             * @author 王帅杰
             * @history
             */
            public void setStatus(EventEntity.ObjectEntity.StatusEntity status) {
                this.status = status;
            }

            /**
             * getCountry
             *
             * @return {@link java.lang.String}
             * @author 王帅杰
             * @history
             */
            public String getCountry() {
                return country;
            }

            /**
             * getWork_station
             *
             * @return {@link java.lang.String}
             * @author 王帅杰
             * @history
             */
            public String getWork_station() {
                return work_station;
            }

            /**
             * getGender
             *
             * @return int
             * @author 王帅杰
             * @history
             */
            public int getGender() {
                return gender;
            }

            /**
             * getCity
             *
             * @return {@link java.lang.String}
             * @author 王帅杰
             * @history
             */
            public String getCity() {
                return city;
            }

            /**
             * getOpen_id
             *
             * @return {@link java.lang.String}
             * @author 王帅杰
             * @history
             */
            public String getOpen_id() {
                return open_id;
            }

            /**
             * getMobile
             *
             * @return {@link java.lang.String}
             * @author 王帅杰
             * @history
             */
            public String getMobile() {
                return mobile;
            }

            /**
             * getEmployee_no
             *
             * @return {@link java.lang.String}
             * @author 王帅杰
             * @history
             */
            public String getEmployee_no() {
                return employee_no;
            }

            /**
             * getAvatar
             *
             * @return {@link com.wsj.feishu.entity.EventEntity.ObjectEntity.AvatarEntity}
             * @author 王帅杰
             * @history
             */
            public EventEntity.ObjectEntity.AvatarEntity getAvatar() {
                return avatar;
            }

            /**
             * getDepartment_ids
             *
             * @return {@link java.util.List<java.lang.String>}
             * @author 王帅杰
             * @history
             */
            public List<String> getDepartment_ids() {
                return department_ids;
            }

            /**
             * getEmployee_type
             *
             * @return int
             * @author 王帅杰
             * @history
             */
            public int getEmployee_type() {
                return employee_type;
            }

            /**
             * getUser_id
             *
             * @return {@link java.lang.String}
             * @author 王帅杰
             * @history
             */
            public String getUser_id() {
                return user_id;
            }

            /**
             * getName
             *
             * @return {@link java.lang.String}
             * @author 王帅杰
             * @history
             */
            public String getName() {
                return name;
            }

            /**
             * getEn_name
             *
             * @return {@link java.lang.String}
             * @author 王帅杰
             * @history
             */
            public String getEn_name() {
                return en_name;
            }

            /**
             * getOrders
             *
             * @return {@link java.util.List<com.wsj.feishu.entity.EventEntity.ObjectEntity.OrdersEntity>}
             * @author 王帅杰
             * @history
             */
            public List<EventEntity.ObjectEntity.OrdersEntity> getOrders() {
                return orders;
            }

            /**
             * getLeader_user_id
             *
             * @return {@link java.lang.String}
             * @author 王帅杰
             * @history
             */
            public String getLeader_user_id() {
                return leader_user_id;
            }

            /**
             * getEmail
             *
             * @return {@link java.lang.String}
             * @author 王帅杰
             * @history
             */
            public String getEmail() {
                return email;
            }

            /**
             * getStatus
             *
             * @return {@link com.wsj.feishu.entity.EventEntity.ObjectEntity.StatusEntity}
             * @author 王帅杰
             * @history
             */
            public EventEntity.ObjectEntity.StatusEntity getStatus() {
                return status;
            }

            /**
             * @author 王帅杰
             * @version 2.0
             * @package com.wsj.feishu.entity
             * @title AvatarEntity
             * @description
             * @date 2021-07-05 16:01:18
             * @Copyright 2019 www.zielsmart.com Inc. All rights reserved
             * 注意：本内容仅限于郑州致欧网络科技有限公司内部传阅，禁止外泄以及用于其他的商业目的
             */
            public static class AvatarEntity {
                /**
                 * avatar_640
                 */
                private String avatar_640;
                /**
                 * avatar_origin
                 */
                private String avatar_origin;
                /**
                 * avatar_72
                 */
                private String avatar_72;
                /**
                 * avatar_240
                 */
                private String avatar_240;

                /**
                 * setAvatar_640
                 *
                 * @param avatar_640
                 * @author 王帅杰
                 * @history
                 */
                public void setAvatar_640(String avatar_640) {
                    this.avatar_640 = avatar_640;
                }

                /**
                 * setAvatar_origin
                 *
                 * @param avatar_origin
                 * @author 王帅杰
                 * @history
                 */
                public void setAvatar_origin(String avatar_origin) {
                    this.avatar_origin = avatar_origin;
                }

                /**
                 * setAvatar_72
                 *
                 * @param avatar_72
                 * @author 王帅杰
                 * @history
                 */
                public void setAvatar_72(String avatar_72) {
                    this.avatar_72 = avatar_72;
                }

                /**
                 * setAvatar_240
                 *
                 * @param avatar_240
                 * @author 王帅杰
                 * @history
                 */
                public void setAvatar_240(String avatar_240) {
                    this.avatar_240 = avatar_240;
                }

                /**
                 * getAvatar_640
                 *
                 * @return {@link java.lang.String}
                 * @author 王帅杰
                 * @history
                 */
                public String getAvatar_640() {
                    return avatar_640;
                }

                /**
                 * getAvatar_origin
                 *
                 * @return {@link java.lang.String}
                 * @author 王帅杰
                 * @history
                 */
                public String getAvatar_origin() {
                    return avatar_origin;
                }

                /**
                 * getAvatar_72
                 *
                 * @return {@link java.lang.String}
                 * @author 王帅杰
                 * @history
                 */
                public String getAvatar_72() {
                    return avatar_72;
                }

                /**
                 * getAvatar_240
                 *
                 * @return {@link java.lang.String}
                 * @author 王帅杰
                 * @history
                 */
                public String getAvatar_240() {
                    return avatar_240;
                }
            }

            /**
             * @author 王帅杰
             * @version 2.0
             * @package com.wsj.feishu.entity
             * @title OrdersEntity
             * @description
             * @date 2021-07-05 16:01:18
             * @Copyright 2019 www.zielsmart.com Inc. All rights reserved
             * 注意：本内容仅限于郑州致欧网络科技有限公司内部传阅，禁止外泄以及用于其他的商业目的
             */
            public static class OrdersEntity {
                /**
                 * user_order
                 */
                private int user_order;
                /**
                 * department_id
                 */
                private String department_id;
                /**
                 * department_order
                 */
                private int department_order;

                /**
                 * setUser_order
                 *
                 * @param user_order
                 * @author 王帅杰
                 * @history
                 */
                public void setUser_order(int user_order) {
                    this.user_order = user_order;
                }

                /**
                 * setDepartment_id
                 *
                 * @param department_id
                 * @author 王帅杰
                 * @history
                 */
                public void setDepartment_id(String department_id) {
                    this.department_id = department_id;
                }

                /**
                 * setDepartment_order
                 *
                 * @param department_order
                 * @author 王帅杰
                 * @history
                 */
                public void setDepartment_order(int department_order) {
                    this.department_order = department_order;
                }

                /**
                 * getUser_order
                 *
                 * @return int
                 * @author 王帅杰
                 * @history
                 */
                public int getUser_order() {
                    return user_order;
                }

                /**
                 * getDepartment_id
                 *
                 * @return {@link java.lang.String}
                 * @author 王帅杰
                 * @history
                 */
                public String getDepartment_id() {
                    return department_id;
                }

                /**
                 * getDepartment_order
                 *
                 * @return int
                 * @author 王帅杰
                 * @history
                 */
                public int getDepartment_order() {
                    return department_order;
                }
            }

            /**
             * @author 王帅杰
             * @version 2.0
             * @package com.wsj.feishu.entity
             * @title StatusEntity
             * @description
             * @date 2021-07-05 16:01:18
             * @Copyright 2019 www.zielsmart.com Inc. All rights reserved
             * 注意：本内容仅限于郑州致欧网络科技有限公司内部传阅，禁止外泄以及用于其他的商业目的
             */
            public static class StatusEntity {
                /**
                 * is_activated
                 */
                private boolean is_activated;
                /**
                 * is_frozen
                 */
                private boolean is_frozen;
                /**
                 * is_resigned
                 */
                private boolean is_resigned;

                /**
                 * setIs_activated
                 *
                 * @param is_activated
                 * @author 王帅杰
                 * @history
                 */
                public void setIs_activated(boolean is_activated) {
                    this.is_activated = is_activated;
                }

                /**
                 * setIs_frozen
                 *
                 * @param is_frozen
                 * @author 王帅杰
                 * @history
                 */
                public void setIs_frozen(boolean is_frozen) {
                    this.is_frozen = is_frozen;
                }

                /**
                 * setIs_resigned
                 *
                 * @param is_resigned
                 * @author 王帅杰
                 * @history
                 */
                public void setIs_resigned(boolean is_resigned) {
                    this.is_resigned = is_resigned;
                }

                /**
                 * isIs_activated
                 *
                 * @return boolean
                 * @author 王帅杰
                 * @history
                 */
                public boolean isIs_activated() {
                    return is_activated;
                }

                /**
                 * isIs_frozen
                 *
                 * @return boolean
                 * @author 王帅杰
                 * @history
                 */
                public boolean isIs_frozen() {
                    return is_frozen;
                }

                /**
                 * isIs_resigned
                 *
                 * @return boolean
                 * @author 王帅杰
                 * @history
                 */
                public boolean isIs_resigned() {
                    return is_resigned;
                }
            }
        }

        public void setMessage(MessageEntity message) {
            this.message = message;
        }

        public SenderEntity getSender() {
            return sender;
        }

        public MessageEntity getMessage() {
            return message;
        }
        @NoArgsConstructor
        public static class SenderEntity {
            /**
             * tenant_key : 13b9b129f00f975d
             * sender_type : user
             * sender_id : {"open_id":"ou_f4c091cd5dfb69c279d5d9280dc7a175","user_id":"5dbf1798","union_id":"on_36f1cf0f72559e710247ce245811b2f7"}
             */
            private String tenant_key;
            private String sender_type;
            private Sender_idEntity sender_id;

            public void setTenant_key(String tenant_key) {
                this.tenant_key = tenant_key;
            }

            public void setSender_type(String sender_type) {
                this.sender_type = sender_type;
            }

            public void setSender_id(Sender_idEntity sender_id) {
                this.sender_id = sender_id;
            }

            public String getTenant_key() {
                return tenant_key;
            }

            public String getSender_type() {
                return sender_type;
            }

            public Sender_idEntity getSender_id() {
                return sender_id;
            }
            @NoArgsConstructor
            public static class Sender_idEntity {
                /**
                 * open_id : ou_f4c091cd5dfb69c279d5d9280dc7a175
                 * user_id : 5dbf1798
                 * union_id : on_36f1cf0f72559e710247ce245811b2f7
                 */
                private String open_id;
                private String user_id;
                private String union_id;

                public void setOpen_id(String open_id) {
                    this.open_id = open_id;
                }

                public void setUser_id(String user_id) {
                    this.user_id = user_id;
                }

                public void setUnion_id(String union_id) {
                    this.union_id = union_id;
                }

                public String getOpen_id() {
                    return open_id;
                }

                public String getUser_id() {
                    return user_id;
                }

                public String getUnion_id() {
                    return union_id;
                }
            }
        }
        public static class MessageEntity {
            /**
             * chat_type : group
             * create_time : 1625199197805
             * mentions : [{"tenant_key":"13b9b129f00f975d","name":"致欧测试","id":{"open_id":"ou_143f589239271a461fa964fdb869ccb8","user_id":"","union_id":"on_8c27014ede61f60e1eaa280d36624f93"},"key":"@_user_1"}]
             * message_id : om_5e22a089a11a0e87a708d9f15485d252
             * message_type : text
             * content : {"text":"@_user_1 555"}
             * chat_id : oc_99112c5ae3aadd4fd9304d4b23effc88
             */
            private String chat_type;
            private String create_time;
            private List<MentionsEntity> mentions;
            private String message_id;
            private String message_type;
            private String content;
            private String chat_id;

            public void setChat_type(String chat_type) {
                this.chat_type = chat_type;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public void setMentions(List<MentionsEntity> mentions) {
                this.mentions = mentions;
            }

            public void setMessage_id(String message_id) {
                this.message_id = message_id;
            }

            public void setMessage_type(String message_type) {
                this.message_type = message_type;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public void setChat_id(String chat_id) {
                this.chat_id = chat_id;
            }

            public String getChat_type() {
                return chat_type;
            }

            public String getCreate_time() {
                return create_time;
            }

            public List<MentionsEntity> getMentions() {
                return mentions;
            }

            public String getMessage_id() {
                return message_id;
            }

            public String getMessage_type() {
                return message_type;
            }

            public String getContent() {
                return content;
            }

            public String getChat_id() {
                return chat_id;
            }
            @NoArgsConstructor
            public static class MentionsEntity {
                /**
                 * tenant_key : 13b9b129f00f975d
                 * name : 致欧测试
                 * id : {"open_id":"ou_143f589239271a461fa964fdb869ccb8","user_id":"","union_id":"on_8c27014ede61f60e1eaa280d36624f93"}
                 * key : @_user_1
                 */
                private String tenant_key;
                private String name;
                private IdEntity id;
                private String key;

                public void setTenant_key(String tenant_key) {
                    this.tenant_key = tenant_key;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public void setId(IdEntity id) {
                    this.id = id;
                }

                public void setKey(String key) {
                    this.key = key;
                }

                public String getTenant_key() {
                    return tenant_key;
                }

                public String getName() {
                    return name;
                }

                public IdEntity getId() {
                    return id;
                }

                public String getKey() {
                    return key;
                }

                public static class IdEntity {
                    /**
                     * open_id : ou_143f589239271a461fa964fdb869ccb8
                     * user_id :
                     * union_id : on_8c27014ede61f60e1eaa280d36624f93
                     */
                    private String open_id;
                    private String user_id;
                    private String union_id;

                    public void setOpen_id(String open_id) {
                        this.open_id = open_id;
                    }

                    public void setUser_id(String user_id) {
                        this.user_id = user_id;
                    }

                    public void setUnion_id(String union_id) {
                        this.union_id = union_id;
                    }

                    public String getOpen_id() {
                        return open_id;
                    }

                    public String getUser_id() {
                        return user_id;
                    }

                    public String getUnion_id() {
                        return union_id;
                    }
                }
            }
        }
    }
}
