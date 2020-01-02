package com.it313.big.common.utils.excel;


import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExportExcel2<T> {

    private String title = "";//标题
    private String[] headers = null;//行头列
    private Object[] fields = null;//字段列名
    private String colArray = null;//所占列数
    private String[] subHeads = null;//子标题
    private boolean subHeadHidden = false;
    private Map<String,Integer> keyFieldCol = new HashMap<String, Integer>();
    private Map<String,Object> colArrayMap = new HashMap<String,Object>();
    private Map<String,Object> colDefaults = new HashMap<String,Object>();//列默认值{1:"选择"}表示第二列的默认值为选择

    //设置列默认值
    private int sRow = 0;//起始行

    private boolean isMap = false;

    public void exportExcel(String title,String[] headers,String[] fileds, Collection<T> dataset, OutputStream out){
        exportExcel(title, headers,fileds, dataset, out);
    }

    /**
     * 这是一个通用的方法，利用了JAVA的反射机制，可以将放置在JAVA集合中并且符号一定条件的数据以EXCEL 的形式输出到指定IO设备上
     * @param title 表格标题名
     * @param headers 表格属性列名数组
     * @param dataset 需要显示的数据集合,集合中可以放置符合javabean风格的类的对象或者map对象。此方法支持属性的数据类型有基本数据类型及String,Date,byte[](图片数据)
     * @param out 与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
     * @param pattern 如果有时间数据，设定输出格式。默认为"yyy-MM-dd"
     */
    public void exportExcel(String title, Object[] headers, String[] fileds, Collection<T> dataset, OutputStream out, String pattern)
    {
        // 声明一个工作薄
        SXSSFWorkbook workbook = new SXSSFWorkbook();
        // 生成一个表格
        Sheet sheet = workbook.createSheet(title);
        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth((short) 15);
        CellStyle style1 = workbook.createCellStyle();
        style1.setFillForegroundColor(HSSFColor.WHITE.index);
        style1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style1.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style1.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style1.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style1.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style1.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        style1.setVerticalAlignment(HSSFCellStyle.ALIGN_LEFT);
        // 生成另一个字体
        Font font1 = workbook.createFont();
        font1.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        font1.setFontHeight((short) 320);
        // 把字体应用到当前的样式
        style1.setFont(font1);

        // 生成并设置另一个样式
        CellStyle style = workbook.createCellStyle();
        // 设置这些样式
        style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 生成一个字体
        Font font = workbook.createFont();
        font.setColor(HSSFColor.VIOLET.index);
        font.setFontHeightInPoints((short) 12);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        // 把字体应用到当前的样式
        style.setFont(font);

        int index1=0;

        // 产生表格标题行
        Row row = sheet.createRow(index1);
        index1++;
        for (short i = 0; i < headers.length; i++)
        {
            Cell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i].toString());
            cell.setCellValue(text);
        }
        if(dataset!=null){
            // 遍历集合数据，产生数据行
            Iterator<T> it = dataset.iterator();
            int index = index1;
            while (it.hasNext())
            {
                index++;
                row = sheet.createRow(index);
                T t = (T) it.next();
                Object[] fields = null;
                if(t instanceof Map){
                    fields = ((Map<?, ?>) t).keySet().toArray();
                    isMap = true;
                }else{
                    // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
                    fields = t.getClass().getDeclaredFields();
                }
                int tempIndex = fields.length-1;
                for (short i = 0; i < fields.length; i++){
                    Cell cell = row.createCell(i);
                    //cell.setCellStyle(style2);
                    Object value = null;
                    try {
                        if(isMap){
                            value = ((Map<?, ?>) t).get(fields[i]);
                        }else{
                            Field field = (Field) fields[i];
                            String fieldName = field.getName();
                            String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                            Class tCls = t.getClass();
                            Method getMethod = tCls.getMethod(getMethodName,new Class[]{});
                            value = getMethod.invoke(t, new Object[]{});
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    try
                    {
                        // 判断值的类型后进行强制类型转换
                        String textValue = null;
                        if (value instanceof Date){
                            Date date = (Date) value;
                            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                            textValue = sdf.format(date);
                        }else {
                            // 其它数据类型都当作字符串简单处理
                            textValue = (value!=null?value.toString():"");
                        }
                        // 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
                        if (textValue != null)
                        {
                            Pattern p = Pattern.compile("^//d+(//.//d+)?$");
                            Matcher matcher = p.matcher(textValue);
                            if (matcher.matches()) {
                                // 是数字当作double处理
                                cell.setCellValue(Double.parseDouble(textValue));
                            } else {
                                HSSFRichTextString richString = new HSSFRichTextString(textValue);
//		                            HSSFFont font3 = workbook.createFont();
//		                            font3.setColor(HSSFColor.BLUE.index);
//		                            richString.applyFont(font3);
                                cell.setCellValue(richString);
                            }
                        }
                    }
                    catch (SecurityException e){
                        e.printStackTrace();
                    }
                    finally{
                        // 清理资源
                    }
                }
            }
        }
        try {
            workbook.write(out);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**   导出图表数据重写以上方法（加入大标题，序号和比例值 ）
     * 这是一个通用的方法，利用了JAVA的反射机制，可以将放置在JAVA集合中并且符号一定条件的数据以EXCEL 的形式输出到指定IO设备上
     * @param headers 表格属性列名数组
     * @param dataset 需要显示的数据集合,集合中可以放置符合javabean风格的类的对象或者map对象。此方法支持属性的数据类型有基本数据类型及String,Date,byte[](图片数据)
     * @param out 与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
     * @param pattern 如果有时间数据，设定输出格式。默认为"yyy-MM-dd"
     * * @param configMap  配置
     * 		{
     * 		title       String     标题
     * 		headers 	String[]   列标题
     * 		searchRow   String 	       搜索行
     * 		fields      String[]   字段名
     * 		colDefaults Map<String,Object>   列默认值
     * 		dropList    List<Map<String,String[]>>下拉区域
     * 		dataType    String    数据类型 0的时候是普通的一行一行数据，1有合并单元格的数据
     * 		subHeadHidden boolean 子列标题是否隐藏
    }
     */
    public void exportReportExcel(String tips, Object[] hideRow, Object[] headers, Collection<T> dataset, OutputStream out, String pattern,Map<String,Object> configMap)
    {
        //声明和赋值变量
        this.title =  configMap.get("title")!=null?configMap.get("title").toString():"";
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = workbook.createSheet(title);
        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth(15);
        HSSFCellStyle headStyle = this.getDefaultStyle(workbook);
        // 生成另一个字体
        HSSFFont headFont = this.getHeadFont(workbook);
        // 把字体应用到当前的样式
        headStyle.setFont(headFont);

        // 生成并设置另一个样式
        HSSFCellStyle style = this.getDefaultStyle(workbook);

        //标题
        this.headers = configMap.get("headers")!=null?(String[])configMap.get("headers"):new String[1];
        //设置字段列名
        this.fields = configMap.get("fields")!=null?(configMap.get("fields") instanceof String ? configMap.get("fields").toString().split(";"):(String[])configMap.get("fields")):null;
        this.colArray = configMap.get("colArrayJson")!=null?(String)configMap.get("colArrayJson"):null;


        int index1=0;
        int	totalCol = 	headers!=null?headers.length:configMap.get("totalCol")!=null?Integer.parseInt(configMap.get("totalCol").toString()):1;
        //搜索行
        String searchRow = "";
        if(configMap.get("searchRow")!=null)
            searchRow = configMap.get("searchRow").toString();

        if(!searchRow.isEmpty()){
            HSSFRow row = sheet.createRow(index1);
            row.setHeightInPoints(20);
            HSSFCell cell = row.createCell(0);
            HSSFRichTextString text = new HSSFRichTextString(searchRow);
            cell.setCellValue(text);
            sheet.addMergedRegion(new Region((short)0, (short)0, (short)0, (short)(totalCol-1)));
            index1++;
        }

        //图片
        if(configMap.get("img")!=null){
            int pictureIdx = workbook.addPicture((byte[])configMap.get("img"), XSSFWorkbook.PICTURE_TYPE_PNG);
            CreationHelper helper = workbook.getCreationHelper();
            Drawing drawing = sheet.createDrawingPatriarch();
            ClientAnchor anchor = helper.createClientAnchor();
            anchor.setCol1(headers.length+1);
            anchor.setRow1(3);
            Picture pict = drawing.createPicture(anchor, pictureIdx);
            pict.resize();
        }
        // 产生表格提示行
        if(tips!=null){
            HSSFRow row = sheet.createRow(index1);
            row.setHeightInPoints(20);
            HSSFCell cell = row.createCell(0);
            cell.setCellStyle(headStyle);
            HSSFRichTextString text = new HSSFRichTextString(tips);
            cell.setCellValue(text);
            sheet.addMergedRegion(new Region((short)index1, (short)0, (short)index1, (short)(totalCol-1)));
            index1++;
        }

        // 产生表格标题行
        HSSFRow row = sheet.createRow(index1);

        if(headers!=null){
            for (short i = 0; i < headers.length; i++)
            {
                HSSFCell cell = row.createCell(i);
                HSSFRichTextString text = new HSSFRichTextString(headers[i].toString());
                cell.setCellStyle(this.getDefaultStyle(workbook));
                cell.setCellValue(text);
            }
            index1++;
        }

        //子列列名
        if(subHeads!=null){
            int _temp = 0,val=0;
            for(int i=0;i<subHeads.length;i++){
                String subHeadStr = subHeads[i].toString();
                Map<String,Object> colNumMap = (Map<String,Object>)colArrayMap.get(subHeadStr);
                int rowNum = colNumMap.get("rowNum")!=null?Integer.parseInt(colNumMap.get("rowNum").toString()):1;

                keyFieldCol.put(subHeadStr, _temp);
                val =_temp+Integer.parseInt(colNumMap.get("colNum").toString());

                row.setHeightInPoints(20);
                HSSFCell cell = row.createCell(_temp);
                cell.setCellStyle(headStyle);
                HSSFRichTextString text = new HSSFRichTextString(subHeadStr);
                cell.setCellValue(text);
                sheet.addMergedRegion(new CellRangeAddress((short)index1,(short)index1+(rowNum-1), (short)_temp, (short)(val-1)));
                _temp = val;
            }
            index1++;
        }

        //额外子标题
        if(configMap.get("extraSubHeads")!=null&&!configMap.get("extraSubHeads").toString().isEmpty()){
            String extraSubHeads = configMap.get("extraSubHeads").toString();
            String[] extArr = extraSubHeads.split(";");
            HSSFRow hrow = sheet.createRow(index1);
            for(int i=0,len=extArr.length;i<len;i++){
                HSSFCell cell = hrow.createCell(totalCol-len+i);
                cell.setCellValue(extArr[i]);
            }
            sheet.addMergedRegion(new CellRangeAddress((short)index1-1,(short)index1, (short)0, (short)0));
            index1++;

        }
        //产生隐藏行
        if(hideRow!=null){
            HSSFRow hrow = sheet.createRow(index1);
            index1++;
            hrow.setZeroHeight(true);
            for (short i = 0; i < hideRow.length; i++)
            {
                HSSFCell cell = hrow.createCell(i);
                HSSFRichTextString text = new HSSFRichTextString(hideRow[i].toString());
                cell.setCellValue(text);
            }
        }

        sRow = index1;
        //写入数据
        String dataType = configMap.get("dataType")!=null?configMap.get("dataType").toString():"0";
        if("0".equals(dataType)){
            writeData(workbook,dataset);
        }else if("1".equals(dataType)){
//		        	writeMulData(workbook,dataset);
        }

        //所有的都设置表格都设置边框
        for(int i=0;i<sRow;i++){
            HSSFRow _row = sheet.getRow(i);
            if(_row!=null){
                for(int j=0;j<totalCol;j++){
                    HSSFCell _cell = _row.getCell(j)!=null?_row.getCell(j):_row.createCell(j);
                    _cell.setCellStyle(style);
                }
            }
        }

        try {
            workbook.write(out);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param configMap  配置
     * 		{
     * 		title       String     标题
     * 		headers 	String[]   列标题
     * 		fields      String[]   字段名
     * 		colDefaults Map<String,Object>   列默认值
     * 		dropList    List<Map<String,String[]>>下拉区域
    }
     * @param dataset
     * @param out
     */
    public void exportExcel(Map<String,Object> configMap,Collection<T> dataset, OutputStream out){
        //声明和赋值变量
        this.title =  configMap.get("title")!=null?configMap.get("title").toString():"";

        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        //设置工作薄样式
        setWorkbook(workbook);
        // 生成一个表格
        HSSFSheet sheet = workbook.createSheet(title);
        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth((short) 15);

        //设置列头标题
        this.headers = configMap.get("headers")!=null?(String[])configMap.get("headers"):new String[1];
        //设置字段列名
        this.fields = configMap.get("fields")!=null?(String[])configMap.get("fields"):null;
        //设置列默认值
        this.colDefaults = configMap.get("colDefaults")!=null?(Map<String,Object>)configMap.get("colDefaults"):new HashMap<String,Object>();

        HSSFRow headerRow = sheet.createRow(sRow);
        for(int i=0,len=headers.length;i<len;i++){
            HSSFCell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }
        sRow++;
        //隐藏行
        if(configMap.get("hidRow")!=null){
            Object[] hidRow = (Object[]) configMap.get("hidRow");
            HSSFRow hrow = sheet.createRow(sRow);
            sRow++;
            hrow.setZeroHeight(true);
            for (short i = 0; i < hidRow.length; i++){
                HSSFCell cell = hrow.createCell(i);
                HSSFRichTextString text = new HSSFRichTextString(hidRow[i].toString());
                cell.setCellValue(text);
            }
        }
        //隐藏列
        if(configMap.get("hidCol")!=null){
            int[] hidCol = (int[]) configMap.get("hidCol");
            for (short i = 0; i < hidCol.length; i++){
                sheet.setColumnHidden(hidCol[i],true);//隐藏列
            }
        }
        //写入数据
        writeData(workbook,dataset);

        //其他设置
        //1、下拉框设置
        if(configMap.get("dropList")!=null){
            List<Map<String,String[]>> dropList = (List<Map<String,String[]>>)configMap.get("dropList");
            for(int i=0,len=dropList.size();i<len;i++){
                Map<String,String[]> dropMap = dropList.get(i);
                Entry<String, String[]> entry = dropMap.entrySet().iterator().next();
                String[] rowCol= entry.getKey().split(",");
                CellRangeAddressList regions = new CellRangeAddressList(Integer.parseInt(rowCol[0]),Integer.parseInt(rowCol[1]),Integer.parseInt(rowCol[2]),Integer.parseInt(rowCol[3]));
                // 生成下拉框内容
                DVConstraint constraint = DVConstraint.createExplicitListConstraint(entry.getValue());
                // 绑定下拉框和作用区域
                HSSFDataValidation data_validation = new HSSFDataValidation(regions,constraint);
                sheet.addValidationData(data_validation);
            }
        }
        //指定区域样式设置

        try {
            workbook.write(out);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    //设置工作薄样式
    private void setWorkbook(HSSFWorkbook workbook){
        HSSFCellStyle style1 = workbook.createCellStyle();
        style1.setVerticalAlignment(HSSFCellStyle.ALIGN_CENTER);
        style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 生成另一个字体
        HSSFFont font1 = workbook.createFont();
        font1.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        font1.setFontHeight((short) 300);
        // 把字体应用到当前的样式
        style1.setFont(font1);

        // 生成并设置另一个样式
        HSSFCellStyle style = workbook.createCellStyle();
        // 设置这些样式
        style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 生成一个字体
        HSSFFont font = workbook.createFont();
        font.setColor(HSSFColor.VIOLET.index);
        font.setFontHeightInPoints((short) 12);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        // 把字体应用到当前的样式
        style.setFont(font);

    }

    /*处理数据格式  "reportData": [
                     {
                         "statName": "0110 口腔科",
                         "list": [
                             {
                                 "statValue_total": 9,
                                 "data": [
                                     {
                                         "deptId": "2016042709550122536",
                                         "deptName": "0110 口腔科",
                                         "statName": "0110 口腔科",
                                         "statType": "分布情况",
                                         "assetType": 210,
                                         "statObj": "辅助设备",
                                         "assetStatus": "1",
                                         "statValue": 9,
                                         "statValue1": "9",
                                         "statValue2": "0",
                                         "statValue3": "0",
                                         "statValue4": "0"
                                     }
                                 ],
                                 "statValue1_total": 9,
                                 "statValue2_total": 0,
                                 "statValue3_total": 0,
                                 "statValue4_total": 0
                             }
                         ]
                     },
                     {
                         "statName": "闽侯县医院/信息科",
                         "list": [
                             {
                                 "statValue_total": 8,
                                 "data": [
                                     {
                                         "deptId": "2",
                                         "deptName": "信息科",
                                         "statName": "闽侯县医院/信息科",
                                         "statType": "分布情况",
                                         "assetType": 210,
                                         "statObj": "辅助设备",
                                         "assetStatus": "1",
                                         "statValue": 3,
                                         "statValue1": "2",
                                         "statValue2": "1",
                                         "statValue3": "0",
                                         "statValue4": "0"
                                     },
                                     {
                                         "deptId": "2",
                                         "deptName": "信息科",
                                         "statName": "闽侯县医院/信息科",
                                         "statType": "分布情况",
                                         "assetType": 209,
                                         "statObj": "诊断设备",
                                         "assetStatus": "1",
                                         "statValue": 1,
                                         "statValue1": "1",
                                         "statValue2": "0",
                                         "statValue3": "0",
                                         "statValue4": "0"
                                     },
                                     {
                                         "deptId": "2",
                                         "deptName": "信息科",
                                         "statName": "闽侯县医院/信息科",
                                         "statType": "分布情况",
                                         "assetType": 208,
                                         "statObj": "治疗设备",
                                         "assetStatus": "1",
                                         "statValue": 4,
                                         "statValue1": "4",
                                         "statValue2": "0",
                                         "statValue3": "0",
                                         "statValue4": "0"
                                     }
                                 ],
                                 "statValue1_total": 7,
                                 "statValue2_total": 1,
                                 "statValue3_total": 0,
                                 "statValue4_total": 0
                             }
                         ]
                     }

                 ]
         }*/
    //写数据
    private void writeMulData(HSSFWorkbook workbook,Collection<T> dataset){
        DecimalFormat  df2 = new DecimalFormat("############.##");

        HSSFSheet sheet = workbook.getSheetAt(0);
        // 遍历集合数据，产生数据行
        Iterator<T> it = dataset.iterator();

        int index = sRow;
        //最外层数据
        while (it.hasNext()){
            T t = (T) it.next();
            if(t instanceof Map){
                Map<String,Object> map = (Map<String,Object>) t;
                Object statName = map.get("statName");

                //获取第二层数据
                List<Map<String,Object>> dataList = (List<Map<String,Object>>)map.get("list");

                //下一list数据的row
                int nextDataRow = index;
                List<String> totalDataList = new ArrayList<String>();
                for(int i=0,len=dataList.size();i<len;i++){
                    Map<String,Object> detailMap = dataList.get(i);
                    //获取第三层数据
                    List<Map<String,Object>> detailDataList = (List<Map<String,Object>>)detailMap.get("data");

                    int rowIndex = index;
                    Map<String,Object> objMap = null;
                    Map<String,String> keyFieldsMap = null;

                    int s = 0;
                    for(int j=0,jLen=detailDataList.size();j<jLen;j++){
                        Map<String,Object> _tempMap =  detailDataList.get(j);
                        s = keyFieldCol.get(_tempMap.get("statType").toString());

                        objMap = (Map<String,Object>)this.colArrayMap.get(_tempMap.get("statType").toString());
                        keyFieldsMap = (Map<String,String>)objMap.get("keyFields");
                        int k=0;
                        //如果是第一个条数据添加表头
                        if(!this.subHeadHidden&&j==0){
                            HSSFRow headRow = sheet.getRow(rowIndex)!=null?sheet.getRow(rowIndex):sheet.createRow(rowIndex);
                            for(Entry<String, String> entry:keyFieldsMap.entrySet()){
                                HSSFCell cell = headRow.createCell(k+s);
                                cell.setCellValue(entry.getValue());
                                k++;
                            }
                            rowIndex++;
                        }

                        k=0;
                        HSSFRow row = sheet.getRow(rowIndex)!=null?sheet.getRow(rowIndex):sheet.createRow(rowIndex);
                        for(Entry<String, String> entry:keyFieldsMap.entrySet()){
                            HSSFCell cell = row.createCell(k+s);
                            if(_tempMap.get(entry.getKey())!=null){
                                cell.setCellValue(_tempMap.get(entry.getKey()).toString());
                            }else if("ratio".equals(entry.getKey())){//如果是比例的字段，没有数据需要自己计算
                                double total = Double.parseDouble(detailMap.get("statValue_total").toString());
                                double curD = Double.parseDouble(_tempMap.get("statValue").toString());
                                double radio = (curD/total)*100;
                                cell.setCellValue(df2.format(radio)+"%");
                            }

                            k++;
                        }
                        rowIndex++;
                    }
                    //最后一列添加汇总数据
                    int k=0;
                    for(Entry<String, String> entry:keyFieldsMap.entrySet()){
                        if("ratio".equals(entry.getKey())){
                            totalDataList.add("");
                        }else if(detailMap.get(entry.getKey()+"_total")!=null){
                            String _total = detailMap.get(entry.getKey()+"_total").toString();
                            //去小数点0
                            if(_total.indexOf(".0")>-1){
                                _total=_total.substring(0,_total.indexOf(".0"));
                            }
                            totalDataList.add(_total);
                        }else{
                            totalDataList.add("合计");
                        }
                        k++;
                    }
                    //设置最外层数据的下一行开始位置
                    nextDataRow =nextDataRow>rowIndex?nextDataRow:rowIndex;
                }

                HSSFRow totalRow = sheet.getRow(nextDataRow)!=null?sheet.getRow(nextDataRow):sheet.createRow(nextDataRow);
                for(int i=0,len=totalDataList.size();i<len;i++){
                    HSSFCell cell = null;
                    if(statName!=null)
                        cell = totalRow.createCell(i+1);
                    else
                        cell = totalRow.createCell(i);
                    cell.setCellValue(totalDataList.get(i));
                }

                if(statName!=null){
                    sheet.addMergedRegion(new CellRangeAddress((short)index,(short)nextDataRow, (short)0, (short)0));
                    HSSFRow row = sheet.getRow(index)!=null?sheet.getRow(index):sheet.createRow(index);
                    HSSFCell cell = row.createCell(0);
                    cell.setCellValue(statName.toString());
                }
                index = nextDataRow;
            }

            index++;
        }
        sRow = index;
    }
    //写数据
    private void writeData(HSSFWorkbook workbook,Collection<T> dataset){
        HSSFSheet sheet = workbook.getSheetAt(0);

        // 遍历集合数据，产生数据行
        Iterator<T> it = dataset.iterator();
        int index = sRow;
        int k = 1;
        while (it.hasNext()){
            //创建Row
            HSSFRow row = sheet.createRow(index);

            T t = (T) it.next();
            if(index==sRow&& t instanceof Map)
                isMap = true;
            //获取fields
            if(this.fields==null && t instanceof Map){
                this.fields = ((Map<?, ?>) t).keySet().toArray();
            }else if(this.fields==null){
                // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
                this.fields = t.getClass().getDeclaredFields();
            }

            //遍历fields填充数据
            for (int i = 0,len=this.fields.length; i<len ; i++){
                HSSFCell cell = row.createCell(i);
                //获取value值
                Object value = getKeyValue(t,this.fields[i],this.colDefaults.get(i+""));
                //如果是序列值
                if(value==null&&"rownum".equals(fields[i])){
                    value = k;
                }
                // 判断值的类型后进行强制类型转换
                String textValue = convertValue(value);
                //设置cell值
                setCellValue(cell,textValue);

            }
            k++;
            index++;
        }

        //合并第一行的数据
        String preRowCell = "";
        int preIndex=sRow;
        for(int i=sRow,len=index;i<len;i++){
            HSSFRow row = sheet.getRow(i);
            HSSFCell cell = row.getCell(0);
            String curCell = cell.getStringCellValue();
            //首行
            if(i==sRow){
                preRowCell = cell.getStringCellValue();
            }else if(!preRowCell.equals(curCell)){
                sheet.addMergedRegion(new CellRangeAddress((short)preIndex,(short)i-1, (short)0, (short)0));
                preIndex = i;
                preRowCell = cell.getStringCellValue();
            }
        }
        sRow = index;
    }

    //获取dataList[i]里面的各个属性值
    //fields以&开头的话，这列会直接当值用
    private Object getKeyValue(T t,Object fields,Object defaultValue){
        Object value = null;
        try {
            if(isMap){
                value = ((Map<?, ?>) t).get(fields);
            }else{
                Field field = (Field) fields;
                String fieldName = field.getName();
                String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                Class tCls = t.getClass();
                Method getMethod = tCls.getMethod(getMethodName,new Class[]{});
                value = getMethod.invoke(t, new Object[]{});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(value==null&&defaultValue!=null){
            value = defaultValue;
        }else if(value==null){
            value = "无";
        }
        return value;
    }
    //格式转换
    private String convertValue(Object value){
        String textValue = "";
        if (value instanceof Date){
            Date date = (Date) value;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            textValue = sdf.format(date);
        } else if (value instanceof byte[]){} else {
            // 其它数据类型都当作字符串简单处理
            textValue = (value!=null?value.toString():"");
        }

        return textValue;
    }
    //设置单元格值
    private void setCellValue(HSSFCell cell,String textValue){
        Pattern p = Pattern.compile("^//d+(//.//d+)?$");
        Matcher matcher = p.matcher(textValue);
        if (matcher.matches()) {
            // 是数字当作double处理
            cell.setCellValue(Double.parseDouble(textValue));
        } else {
            HSSFRichTextString richString = new HSSFRichTextString(textValue);
            cell.setCellValue(richString);
        }
    }

    //默认样式
    private HSSFCellStyle getDefaultStyle(HSSFWorkbook workbook){
        HSSFCellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(HSSFColor.WHITE.index);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setLeftBorderColor(HSSFColor.BLACK.index);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setRightBorderColor(HSSFColor.BLACK.index);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setTopBorderColor(HSSFColor.BLACK.index);
        style.setVerticalAlignment(HSSFCellStyle.ALIGN_LEFT);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        return style;
    }

    private HSSFFont getHeadFont(HSSFWorkbook workbook){
        HSSFFont font = workbook.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        font.setFontHeight((short) 300);
        return font;
    }
}