package com.wsj.feishu.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserByExcel {
    @Excel(name = "姓名", width = 15,orderNum = "0")
    private String name;

    @Excel(name = "邮箱", width = 15,orderNum = "1")
    private String email;

    @Excel(name = "工号", width = 15,orderNum = "2")
    private String cardId;

    @Excel(name = "编码", width = 15,orderNum = "3")
    private String newCode;

    @Excel(name = "手机号码", width = 15,orderNum = "4")
    private String phone;

    @Excel(name = "OId", width = 15,orderNum = "5")
    private String newUserId;

}
