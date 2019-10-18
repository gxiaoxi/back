package com.gxx.back.controller;

import com.gxx.back.bean.Sort;
import com.gxx.back.common.BaseResponse;
import com.gxx.back.common.StatusCode;
import com.gxx.back.service.impl.SortServiceImpl;
import com.gxx.back.utils.DateUtil;
import com.gxx.back.utils.ExcelUtil;
import com.gxx.back.utils.StringUtil;
import net.sf.json.JSONObject;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sort")
public class SortController {
    @Autowired
    private SortServiceImpl sortService;

    /**
     * 获取分类列表
     * @return
     */
    @RequestMapping("/list")
    public JSONObject getSortList(){
        JSONObject jb = new JSONObject();
        List<Sort> sortList = sortService.getSortList();
        jb.put("code",0);
        jb.put("msg","");
        jb.put("count",sortList.size());
        jb.put("data",sortList);
        return jb;
    }

    /**
     * 删除单条分类 -- 需先判断是否已被品牌绑定
     * @param sortId
     * @return
     */
    @RequestMapping("/delete")
    public BaseResponse deleteSort(Integer sortId){
        BaseResponse baseResponse = new BaseResponse(StatusCode.Success);
        try {
            int hasFlag = sortService.hasBrand(sortId);
            if(hasFlag > 0){
                baseResponse = new BaseResponse(StatusCode.BrandHasSort);
            }else{
                sortService.deleteSort(sortId);
            }

        }catch (Exception e){
            baseResponse = new BaseResponse(StatusCode.Fail);
            e.printStackTrace();
        }
        return baseResponse;
    }

    /**
     * 新增分类 - 先判断分类名是否已存在
     * @param sort
     * @param request
     * @return
     */
    @RequestMapping("/add")
    public BaseResponse addSort(@RequestBody Sort sort, HttpServletRequest request){
        BaseResponse baseResponse = new BaseResponse(StatusCode.Success);
        try {
            int exitFlag = sortService.sortExit(sort.getSortName());
            if(exitFlag > 0){
                baseResponse = new BaseResponse(StatusCode.SortNameExit);
            }else{
                sort.setCreateTime(DateUtil.getNowDate());
                sort.setCreator(Integer.parseInt(request.getSession().getAttribute("userid").toString()));
                sortService.addSort(sort);
            }

        }catch (Exception e){
            baseResponse = new BaseResponse(StatusCode.Fail);
            e.printStackTrace();
        }
        return baseResponse;
    }

    /**
     * 获取有效的分类列表
     * @return
     */
    @RequestMapping("/getSortListByState")
    public BaseResponse getSortListByState(){
        BaseResponse baseResponse = new BaseResponse(StatusCode.Success);
        try {
            baseResponse.setData(sortService.getSortListByState());
        }catch (Exception e){
            baseResponse = new BaseResponse(StatusCode.Fail);
            e.printStackTrace();
        }
        return baseResponse;
    }

    /**
     * 更新分类 - 先判断分类名是否已存在除去自身
     * @param sort
     * @return
     */
    @RequestMapping("/update")
    public BaseResponse updateSort(@RequestBody Sort sort){
        BaseResponse baseResponse = new BaseResponse(StatusCode.Success);
        try {
            int exitFlag = sortService.sortExitWithoutIt(sort.getSortName(),sort.getSortId());
            if(exitFlag > 0){
                baseResponse = new BaseResponse(StatusCode.SortNameExit);
            }else {
                sortService.updateSort(sort);
            }
        }catch (Exception e){
            baseResponse = new BaseResponse(StatusCode.Fail);
            e.printStackTrace();
        }
        return baseResponse;
    }

    /**
     * 更新分类状态
     * @param sortId
     * @param state
     * @return
     */
    @RequestMapping("/updateSortState")
    public BaseResponse updateSortState(Integer sortId,Integer state){
        BaseResponse baseResponse = new BaseResponse(StatusCode.Success);
        try {
            sortService.updateSortState(sortId,state);
        }catch (Exception e){
            baseResponse = new BaseResponse(StatusCode.Fail);
            e.printStackTrace();
        }
        return baseResponse;
    }

    /**
     * excle导入分类- 先判断导入数据是否存在重复、再判断导入数据是否与数据库中存在重复
     * @param file
     * @return
     */
    @RequestMapping("/import")
    public BaseResponse importData(MultipartFile file,HttpServletRequest request){
        BaseResponse baseResponse = new BaseResponse(StatusCode.Success);
        Map<Integer,String> errormap = new HashMap<>();
        try {
            String contentType = file.getContentType();
            String fileName = file.getOriginalFilename();
            if(file.isEmpty()){
                baseResponse = new BaseResponse(StatusCode.FileIsNone);
            }
            //根据路径获取这个操作excel的实例
            HSSFWorkbook wb = new HSSFWorkbook(file.getInputStream());
            //根据页面index 获取sheet页
            HSSFSheet sheet = wb.getSheetAt(0);
            //实体类集合
            List<Sort> importData = new ArrayList<Sort>();
            HSSFRow row = null;
            //循环sesheet页中数据从第二行开始，第一行是标题
            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                //获取每一行数据
                row = sheet.getRow(i);
                //如果有空数据则直接跳出
                if(row == null ){
                    errormap.put(i+2,"不能为空");
                    baseResponse = new BaseResponse(StatusCode.Fail);
                    baseResponse.setData(errormap);
                    return baseResponse;
                }
                String sortName = row.getCell(0).getStringCellValue();
                Sort sort = new Sort();
                //sort.setSortId(Integer.valueOf((int) row.getCell(0).getNumericCellValue()));
                sort.setSortName(sortName);
                sort.setCreateTime(DateUtil.getNowDate());
                sort.setCreator(Integer.parseInt(request.getSession().getAttribute("userid").toString()));
                importData.add(sort);
            }

            boolean importExitFlag = true;
            for (int i=0;i<importData.size();i++){
                //数据校验，导入的数据本身不能有重复

                for (int j=0;j<importData.size();j++) {
                    if (importData.get(i).getSortName().equals(importData.get(j).getSortName()) && i!=j) {
                        importExitFlag = false;
                        errormap.put(i+2, "与表中第"+(j+2)+"行数据重复");
                    }
                }
            }
            System.out.println("importExitFlag:"+importExitFlag);
            System.out.println("errormap:"+errormap);
            if(importExitFlag){
                boolean exitFlag = true;
                //导入数据自身无误、与数据库现有数据对比是否有重复
                for (int i=0;i<importData.size();i++){
                    if(sortService.sortExit(importData.get(i).getSortName()) > 0){
                        exitFlag = false;
                        errormap.put(i+2, "数据已存在");
                    }
                }
                if(exitFlag){
                    //数据校验无误。插入数据库
                    sortService.addSortList(importData);
                }else{
                    baseResponse = new BaseResponse(StatusCode.Fail);
                    baseResponse.setData(errormap);
                }

                System.out.println(exitFlag);
                System.out.println(errormap);
            }else{
                baseResponse = new BaseResponse(StatusCode.Fail);
                baseResponse.setData(errormap);
            }

        }catch (Exception e){
            baseResponse = new BaseResponse(StatusCode.Fail);
            e.printStackTrace();
        }



        return baseResponse;
    }
    @RequestMapping("/exportTemp")
    public void exportTemp(HttpServletResponse response) {
        try {
            String[] headers = { "分类名称"};
            String fileName="分类导入模板";
            ExcelUtil.exportTemp(response,headers,fileName);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
