# EasyPoi
> 导入

可导入多个sheet，主要ImportParams.setStartSheetIndex 设置第几个sheet，从0开始
```java
@PostMapping("/importExcel2")
    public String importExcel2(@RequestParam("file") MultipartFile file) throws IOException {
        Workbook hssfWorkbook = new XSSFWorkbook(file.getInputStream());
        int numberOfSheets = hssfWorkbook.getNumberOfSheets();
        log.info("sheet数量" + numberOfSheets);
        //循环numberOfSheets，设置StartSheetIndex即可
        ImportParams importParams = new ImportParams();
        importParams.setHeadRows(1);//头占用的行数
        importParams.setTitleRows(1);//标题占用的行数，没有写0
        importParams.setStartSheetIndex(2);//取第三个sheet，从0开始
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
```
> 浏览器上传文件

```html
<form method="POST" enctype="multipart/form-data" action="/excel/importExcel2">
        <p>
            文件：<input type="file" name="file"/>
            <input type="submit" value="导入2 文件内容校验"/>
        </p>
    </form>
```