package com.wsj.feishu.controller;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import cn.afterturn.easypoi.util.PoiSheetUtil;
import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.larksuite.oapi.core.Config;
import com.larksuite.oapi.core.api.AccessTokenType;
import com.larksuite.oapi.core.api.Api;
import com.larksuite.oapi.core.api.request.Request;
import com.larksuite.oapi.core.api.response.Response;
import com.wsj.feishu.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/excel")
@Slf4j
public class ExcelController {
    @Autowired
    private Config config;
    @Autowired
    private ObjectMapper mapper;

    @GetMapping
    public void upload() throws Exception {
        log.info("开始导入");
        ImportParams params = new ImportParams();
        //设置标题的行数，有标题时一定要有
        params.setTitleRows(1);
        //设置表头的行数
        params.setHeadRows(1);
        ExcelImportResult<User> result = ExcelImportUtil.importExcelMore(new File("F:\\用户信息.xlsx"), User.class,
                params);
        System.out.println(result.getList().size());
        log.info("结束");

    }

    @PostMapping("/modifyFeishuUserId")
    public String importExcel2(@RequestParam("file") MultipartFile file) throws IOException {
        ImportParams importParams = new ImportParams();
        importParams.setHeadRows(1);//头占用的行数
        importParams.setTitleRows(0);//标题占用的行数，没有写0
        ExcelImportResult<UserByExcel> result = null;
        try {
            result = ExcelImportUtil.importExcelMore(file.getInputStream(), UserByExcel.class,
                    importParams);
            List<UserByExcel> list = result.getList();
            long time = System.currentTimeMillis();
            FileWriter fileWriter = new FileWriter("D:\\log\\手机号为空"+time+".txt");
            FileWriter phoneNotExist = new FileWriter("D:\\log\\手机号在飞书中不存在"+time+".txt");
            FileWriter findException = new FileWriter("D:\\log\\查询异常"+time+".txt");
            FileWriter modifyCompleted = new FileWriter("D:\\log\\已经修改过的手机号"+time+".txt");
            list.stream().forEach(obj -> {
                if (StrUtil.isBlank(obj.getPhone())) {
                    fileWriter.append(obj.getName()+"\n");
                    return;
                }
                try {
                    this.getUserInfo(obj.getPhone(),obj.getNewUserId(),phoneNotExist,findException,modifyCompleted);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "处理成功,总数据条数：" + result.getList().size();
    }

    @GetMapping("/getUserInfo")
    public void modify() throws Exception {
        long time = System.currentTimeMillis();
        FileWriter phoneNotExist = new FileWriter("D:\\log\\手机号在飞书中不存在"+time+".txt");
        FileWriter findException = new FileWriter("D:\\log\\查询异常"+time+".txt");
        FileWriter modifyCompleted = new FileWriter("D:\\log\\已经修改过的手机号"+time+".txt");
        //张丽君  13927490900   600526643
        getUserInfo("17093757119", "600241522", phoneNotExist, findException,modifyCompleted);
    }

    public String getUserInfo(String phone,String newUserId,FileWriter phoneNotExist,FileWriter findException,FileWriter modifyCompleted) throws Exception {
        Request<HashMap<Object,Object>, HashMap<Object, Object>> request = Request.newRequest("user/v1/batch_get_id?mobiles="+phone,
                "GET", AccessTokenType.Tenant, null, new HashMap<>());
        Response<HashMap<Object, Object>> send = Api.send(config, request);
        if (send.getCode()==0) {
            HashMap<Object, Object> data = send.getData();
            Object  mobile_users1 = data.get("mobile_users");
            if (mobile_users1 != null) {
                String mobile_users = mapper.writeValueAsString(mobile_users1);
                Map<Object,List<Map<Object,Object>>> phoneMap = new HashMap<Object, List<Map<Object,Object>>>();
                phoneMap = mapper.readValue(mobile_users, Map.class);
                Map<Object,Object> resultMap = phoneMap.get(phone).get(0);
                String user_id = resultMap.get("user_id").toString();
                log.info("userId->"+user_id +" :::::: OId->"+newUserId);
                modifyUserId(user_id,newUserId,phone,modifyCompleted);
            }else{
                //Object mobiles_not_exist = data.get("mobiles_not_exist");
                phoneNotExist.append(phone+"\n");
            }
        }else{
            log.info("查询异常:");
            findException.append(phone+"\n");
        }
        return null;
    }

    public void modifyUserId(String userId,String newUserId,String phone,FileWriter modifyCompleted) throws Exception {
        if (userId.equalsIgnoreCase(newUserId)) {
            long l = System.currentTimeMillis();
            log.info(phone+"的userId已经修改过了！");
            modifyCompleted.append(phone+"\n");
            return;
        }
        ModifyEmployee employee = new ModifyEmployee(userId,newUserId);
        Request<ModifyEmployee, HashMap<Object, Object>> request = Request.newRequest("contact/v1/user/update",
                "POST", AccessTokenType.Tenant, employee, new HashMap<>());
        Response<HashMap<Object, Object>> send = Api.send(config, request);
        log.info(send.getMsg().equalsIgnoreCase("success") ? "修改成功" : phone+"修改失败");
    }


}
