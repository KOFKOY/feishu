package com.wsj.feishu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModifyEmployee {
    private String employee_id;
    private String new_employee_id;
}
