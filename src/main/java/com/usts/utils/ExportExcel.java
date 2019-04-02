package com.usts.utils;

import com.usts.model.Grinding_Wheel;
import com.usts.model.Tuple;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

@Service
public class ExportExcel {

    // 这个时候Tuple就是<XX号0:00-1:00,24m36s>or<XX号白班,6h35m25s>
    public static HSSFWorkbook export(List<Tuple> list) {
        String[] excelHeader = new String[]{"时间","工作时间"};

        HSSFWorkbook wb = new HSSFWorkbook();// 创建一个excel文件
        HSSFSheet sheet = wb.createSheet("sheet1");// 创建一个excel文件
        HSSFRow row = sheet.createRow((int) 0);
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

        for (int i = 0; i < excelHeader.length; i++) {
            HSSFCell cell1 = row.createCell(i);
            cell1.setCellValue(excelHeader[i]);
            cell1.setCellStyle(style);
            sheet.autoSizeColumn(i);
        }
        int count = 1;
        for(Tuple tuple:list){
            row = sheet.createRow(count);
            row.createCell(0).setCellValue(tuple.getName().toString());
            row.createCell(1).setCellValue(tuple.getValue().toString());
            count++;
        }
        return wb;
    }
}