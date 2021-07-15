package com.wsj.feishu.controller;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import cn.afterturn.easypoi.util.PoiSheetUtil;
import cn.hutool.poi.excel.ExcelUtil;
import com.wsj.feishu.entity.Test2;
import com.wsj.feishu.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/excel")
@Slf4j
public class ExcelController {

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

    @PostMapping("/importExcel2")
    public String importExcel2(@RequestParam("file") MultipartFile file) throws IOException {
        ImportParams importParams = new ImportParams();
        importParams.setHeadRows(1);//头占用的行数
        importParams.setTitleRows(1);//标题占用的行数，没有写0
        ExcelImportResult<Test2> result = null;
        try {
            result = ExcelImportUtil.importExcelMore(file.getInputStream(), Test2.class,
                    importParams);
            List<Test2> list = result.getList();
            list.stream().forEach(ojb->{
                System.out.println(ojb.getName());
            });
        } catch (Exception e) {
        }
        return "处理成功,总数据条数："+result.getList().size();
    }


}
