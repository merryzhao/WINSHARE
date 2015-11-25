package com.winxuan.ec.util;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * POI工具类
 * User: juqkai(juqkai@gmail.com)
 * Date: 13-5-7
 * Time: 下午3:32
 */
public class POIUtil {
    /**
     * 提取数据
     * @param is
     * @param col
     * @return
     * @throws java.io.IOException
     */
    public static List<List<String>> fetchData(InputStream is, int col) throws IOException {
        if(col < 0){
            col = 0;
        }
        HSSFWorkbook workbook= new HSSFWorkbook(is);
        HSSFSheet sheet = workbook.getSheetAt(0);
        int rows = sheet.getPhysicalNumberOfRows();
        List<List<String>> delivery = new ArrayList<List<String>>();
        for(int i = 0; i < rows; i ++){
            HSSFRow row = sheet.getRow(i);
            if(row == null){
                continue;
            }
            List<String> item = new ArrayList<String>();
            for (int j = 0; j < row.getPhysicalNumberOfCells(); j++) {
                item.add(fetchVal(row.getCell(j)));
            }
            //过滤第一行的头信息
            if(i == 0){
                continue;
            }
            if(row.getPhysicalNumberOfCells() > col) {
                delivery.add(item);
            }
        }
        return delivery;
    }

    /**
     * 提取头信息
     * @param is
     * @return
     * @throws IOException
     */
    public static List<String> fetchHead(InputStream is) throws IOException{
        HSSFWorkbook workbook= new HSSFWorkbook(is);
        HSSFSheet sheet = workbook.getSheetAt(0);

        HSSFRow row = sheet.getRow(0);
        if(row == null){
            return new ArrayList<String>();
        }
        List<String> item = new ArrayList<String>();
        for (int j = 0; j < row.getPhysicalNumberOfCells(); j++) {
            item.add(fetchVal(row.getCell(j)));
        }
        return item;
    }

    private static String fetchVal(HSSFCell cell){
        String val = "";
        if(cell == null){
            return val;
        }
        if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC && HSSFDateUtil.isCellDateFormatted(cell)){
            double d = cell.getNumericCellValue();
            Date date = HSSFDateUtil.getJavaDate(d);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            val = sdf.format(date);
        } else {
            cell.setCellType (org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
            val = cell.toString().trim();
        }
        return val;
    }
}
