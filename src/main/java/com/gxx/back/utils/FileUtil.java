package com.gxx.back.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class FileUtil {
    /**
     * 通过网络文件地址下载文件
     * @param response
     * @param urlStr
     * @param fileName
     * @throws Exception
     */
    public static void downloadByNet(HttpServletResponse response,String urlStr,String fileName) throws Exception {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        // 设置超时间为3秒
        conn.setConnectTimeout(3 * 1000);
        // 防止屏蔽程序抓取而返回403错误
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        // 得到输入流
        InputStream inputStream = conn.getInputStream();
        response.reset();//设置为没有缓存
        response.setContentType("application/octet-stream;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename="+new String(fileName.getBytes("UTF-8"), "ISO8859-1"));
        response.addHeader("Pargam", "no-cache");
        response.addHeader("Cache-Control", "no-cache");
        OutputStream output = response.getOutputStream();
        byte[] buf = new byte[1024];
        int r = 0;
        while ((r = inputStream.read(buf, 0, buf.length)) != -1) {
            output.write(buf, 0, r);
        }
        output.flush();
        output.close();
    }
}
