package com.wsj.feishu.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.io.Serializable;

@Data
public class Test2 implements Serializable {

    @Excel(name="用户名")
    private String name;

    @Excel(name="年龄")
    private String age;
}
