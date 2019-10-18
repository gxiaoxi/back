package com.gxx.back.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class UploadUtil {

    /**
     * 上传文件并返回文件名
     * @param file
     * @param path 上传路径
     * @param preName 文件名
     * @return
     * @throws IOException
     */
    public static String uploadFile(MultipartFile file,String path,String preName) throws IOException {
        String name = file.getOriginalFilename();//上传文件的真实名称
        String suffixName = name.substring(name.lastIndexOf("."));//获取后缀名
        //String uuid = UUID.randomUUID().toString();//获取随机码作为文件名
        String fileName = preName + suffixName;
        File tempFile = new File(path,fileName);
        if(!tempFile.getParentFile().exists()){
            tempFile.getParentFile().mkdir();
        }
        if(tempFile.exists()){
            tempFile.delete();
        }
        tempFile.createNewFile();
        file.transferTo(tempFile);
        return tempFile.getName();
    }
}
