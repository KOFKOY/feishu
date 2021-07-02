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
        private SenderEntity sender;
        private MessageEntity message;

        public void setSender(SenderEntity sender) {
            this.sender = sender;
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
