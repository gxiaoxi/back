package com.gxx.back.utils;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtil {

    /**
     *
     * @param response
     * @param headers 标题行
     * @param fileName 文件名
     * @throws IOException
     */
    public static void exportTemp(HttpServletResponse response,String[] headers,String fileName) throws IOException {


        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();
        //设置列宽
        sheet.setDefaultColumnWidth((short) 18);
        //创建第一行的对象，第一行一般用于填充标题内容。从第二行开始一般是数据
        HSSFRow row = sheet.createRow(0);
        for (short i = 0; i < headers.length; i++) {
            //创建单元格，每行多少数据就创建多少个单元格
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            //给单元格设置内容
            cell.setCellValue(text);
        }
        /*
        //需要导出的数据集合
        List list = new ArrayList();
        //遍历集合，将每个集合元素对象的每个值填充到单元格中
        for(int i=0;i<list.size();i++){
            //从第二行开始填充数据
            row = sheet.createRow(i+1);
            //将数据集合转换成datas
            List<String> datas=new ArrayList<String>();
            datas.add(list.get(i).toString());
            //通过datas填充excel
            for (int j=0;j<datas.size();j++) {
                String string=datas.get(j);
                HSSFCell cell = row.createCell(j);
                HSSFRichTextString richString = new HSSFRichTextString(string);
                HSSFFont font3 = workbook.createFont();
                //定义Excel数据颜色，这里设置为蓝色
                //  font3.setColor(HSSFColor.BLUE.index);
                richString.applyFont(font3);
                cell.setCellValue(richString);
            }

        }*/
        response.setContentType("application/octet-stream;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename="+new String(fileName.getBytes("gb2312"), "ISO8859-1")+".xls");
        response.addHeader("Pargam", "no-cache");
        response.addHeader("Cache-Control", "no-cache");
        OutputStream os=response.getOutputStream();
        workbook.write(os);
        os.flush();
        os.close();
    }
}
