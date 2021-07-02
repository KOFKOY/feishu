package com.wsj.feishu.entity;

import lombok.Data;

@Data
public class ChatInfo {
    /**
     * edit_permission : all_members
     * at_all_permission : all_members
     * add_member_permission : all_members
     * join_message_visibility : only_owner
     * membership_approval : no_approval_required
     * owner_id : 4d7a3c6g
     * i18n_names : {"en_us":"group chat","zh_cn":"群聊","ja_jp":"グループチャット"}
     * name : 测试群名称
     * share_card_permission : allowed
     * description : 测试群描述
     * avatar : default-avatar_44ae0ca3-e140-494b-956f-78091e348435
     * leave_message_visibility : only_owner
     */
    private String edit_permission;
    private String at_all_permission;
    private String add_member_permission;
    private String join_message_visibility;
    private String membership_approval;
    private String owner_id;
    private I18n_namesEntity i18n_names;
    private String name;
    private String share_card_permission;
    private String description;
    private String avatar;
    private String leave_message_visibility;

    @Data
    public static class I18n_namesEntity {
        /**
         * en_us : group chat
         * zh_cn : 群聊
         * ja_jp : グループチャット
         */
        private String en_us;
        private String zh_cn;
        private String ja_jp;
    }
}
