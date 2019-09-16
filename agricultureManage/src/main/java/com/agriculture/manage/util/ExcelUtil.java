package com.agriculture.manage.util;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * @create with IntelliJ IDEA.
 * @author: zlp
 * @date : 2018/3/14  19:19
 * @description:
 */
public class ExcelUtil {

    private static Logger logger = LoggerFactory.getLogger(ExcelUtil.class);

    private static final String EXCEL_XLSX = "xlsx";//2007后版本
    private static final String EXCEL_XLS = "xls";//2003版本

    public static List<List<String>> readerExcel(File file){
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        List<List<String>> lists = null;
        Workbook workbook = null;
        try {
            InputStream inputStream = new FileInputStream(file);
            if(file.getName().endsWith(EXCEL_XLS)){
                workbook = new HSSFWorkbook(inputStream);
            }else if(file.getName().endsWith(EXCEL_XLSX)){
                workbook = new XSSFWorkbook(inputStream);
            }else{
                logger.error("文件非Excel格式："+file.getName());
                throw new Exception("文件不是Excel");
            }
            int sheetCount = workbook.getNumberOfSheets();
            if(sheetCount!=1){
                logger.error("excel只读取第一页");
            }
            Sheet sheet = workbook.getSheetAt(0);
            int count =0;
            lists = new ArrayList<List<String>>();
            for (Row row : sheet) {
                if(count==0){
                    count++;
                    continue;
                }
                if(row.getCell(0).toString().equals("")){
                    logger.error("该行没有数据："+count);
                    continue;
                }
                String rowValue = "";
                List<String> list = new ArrayList<String>();
                for (int i=0;i<row.getLastCellNum();i++) {
                    Cell cell = row.getCell(i);
                    if (cell == null) {
                        list.add("");
                        continue;
                    }
                    int cellType = cell.getCellType();
                    String cellValue = "";
                    switch (cellType){
                        case Cell.CELL_TYPE_STRING:
                            cellValue = cell.getRichStringCellValue().getString();
                            break;
                        case Cell.CELL_TYPE_NUMERIC:
                            if (DateUtil.isCellDateFormatted(cell)) {
                                cellValue = fmt.format(cell.getDateCellValue());
                            } else {
                                cell.setCellType(Cell.CELL_TYPE_STRING);
                                cellValue = String.valueOf(cell.getRichStringCellValue().getString());
                            }
                            break;
                        case Cell.CELL_TYPE_BOOLEAN:    // 布尔型
                            cellValue = String.valueOf(cell.getBooleanCellValue());
                            break;
                        case Cell.CELL_TYPE_BLANK: // 空白
                            cellValue = cell.getStringCellValue();
                            break;
                        case Cell.CELL_TYPE_ERROR: // 错误
                            cellValue = "错误";
                            break;
                        case Cell.CELL_TYPE_FORMULA:    // 公式
                            // 得到对应单元格的公式
                            //cellValue = cell.getCellFormula();
                            // 得到对应单元格的字符串
                            cell.setCellType(Cell.CELL_TYPE_STRING);
                            cellValue = String.valueOf(cell.getRichStringCellValue().getString());
                            break;
                        default:
                            cellValue = "";
                    }
                    list.add(cellValue);

                }
                lists.add(list);
            }

        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
        }finally {
            if(workbook != null){
                try {
                    workbook.close();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }
        return lists;
    }

    /**
     * 创建excel表格
     * @param lists 需要写入内容
     * @param title 表格头部
     * @param filePath 保存文件夹
     * @return
     */
    public static String createExcel(List<List<String>> lists,Object[] title,String filePath){
        //String[] title={"序号","姓名","身份证号","单位名称","考生号","考试名称","考试轮次","考试教室","座位号","开考时间","结束时间"};
        //创建excel工作簿
        HSSFWorkbook workbook=new HSSFWorkbook();
        //创建工作表sheet
        HSSFSheet sheet=workbook.createSheet();
        //创建第一行
        HSSFRow row=sheet.createRow(0);
        HSSFCell cell=null;
        List<Integer> lengWith = new ArrayList<>();
        //插入第一行数据的表头
        for(int i=0;i<title.length;i++){
            cell=row.createCell(i);
            cell.setCellValue(title[i].toString());
            lengWith.add(title[i].toString().length());
        }
        for (int i=0;i<lists.size();i++){
            HSSFRow nrow=sheet.createRow(i+1);
            List<String> list = lists.get(i);
            for (int j=0;j<list.size();j++){
                HSSFCell ncell=nrow.createCell(j);
                ncell.setCellValue(list.get(j));
                if(j<lengWith.size()){
                    if(list.get(j) != null && list.get(j).length()>lengWith.get(j)){
                        lengWith.set(j,list.get(j).length());
                    }
                }
            }
        }
        System.out.println(lengWith.toString());
        for (int i=0;i<lengWith.size();i++){
            sheet.setColumnWidth(i,(short)((lengWith.get(i)*256)*2+184));
        }
        String name = UUID.randomUUID().toString();
        File file=new File(filePath+"\\"+name+".xls");
        try {
            file.createNewFile();
            //将excel写入
            FileOutputStream stream= FileUtils.openOutputStream(file);
            workbook.write(stream);
            stream.close();
            return file.getPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
