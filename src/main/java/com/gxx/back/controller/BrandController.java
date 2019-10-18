package com.gxx.back.controller;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.gxx.back.bean.Brand;
import com.gxx.back.bean.Sort;
import com.gxx.back.common.BaseResponse;
import com.gxx.back.common.StatusCode;
import com.gxx.back.service.SortService;
import com.gxx.back.service.impl.BrandServiceImpl;
import com.gxx.back.service.impl.SortServiceImpl;
import com.gxx.back.utils.DateUtil;
import com.gxx.back.utils.ExcelUtil;
import com.gxx.back.utils.UploadUtil;
import net.sf.json.JSONObject;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.unit.DataUnit;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/brand")
public class BrandController {

    @Autowired
    private BrandServiceImpl brandService;
    @Autowired
    private SortServiceImpl sortService;

    /**
     * 获取品牌列表
     * @return
     */
    @RequestMapping("/list")
    public JSONObject getBrandList(){
        JSONObject jb = new JSONObject();
        List<Brand> brandList = brandService.getBrandList();
        jb.put("code",0);
        jb.put("msg","");
        jb.put("count",brandList.size());
        jb.put("data",brandList);
        return jb;
    }

    @RequestMapping("/upload")
    public BaseResponse uploadImage(MultipartFile file,String name){
        BaseResponse baseResponse = new BaseResponse(StatusCode.Success);
        try {
            String path =  "/home/images/";//"E:/";
            String logoUrl = UploadUtil.uploadFile(file,path,name);
            baseResponse.setData(path + logoUrl);
        }catch (Exception e){
            baseResponse = new BaseResponse(StatusCode.Fail);
            e.printStackTrace();
        }
        return baseResponse;
    }

    /**
     * 新增品牌 - 同分类下品牌名称不能重复
     * @param brand
     * @return
     */
    @RequestMapping("/add")
    public BaseResponse addBrand(@RequestBody Brand brand,HttpServletRequest request){
        BaseResponse baseResponse = new BaseResponse(StatusCode.Success);
        try {
           int exitFlag = brandService.brandNameExit(brand.getSortId(),brand.getBrandName());
           if(exitFlag > 0){
               baseResponse = new BaseResponse(StatusCode.BrandNameExit);
           }else{
               brand.setCreateTime(DateUtil.getNowDate());
               brand.setCreator(Integer.parseInt(request.getSession().getAttribute("userid").toString()));
               brandService.addBrand(brand);
           }
        }catch (Exception e){
            baseResponse = new BaseResponse(StatusCode.Fail);
            e.printStackTrace();
        }
        return baseResponse;
    }

    /**
     * 删除品牌 -- 需要先判断是否已有商品绑定
     * @param brandId
     * @return
     */
    @RequestMapping("/delete")
    public BaseResponse deleteBrand(Integer brandId){
        BaseResponse baseResponse = new BaseResponse(StatusCode.Success);
        try {
            int hasFlag = brandService.hasGoods(brandId);
            if(hasFlag > 0){
                baseResponse = new BaseResponse(StatusCode.GoodsHasBrand);
            }else{
                brandService.deleteBrand(brandId);
            }
        }catch (Exception e){
            baseResponse = new BaseResponse(StatusCode.Fail);
            e.printStackTrace();
        }
        return baseResponse;
    }

    /**
     * 获取单条品牌信息
     * @param brandId
     * @return
     */
    @RequestMapping("/getBrandById")
    public BaseResponse getBrandById(Integer brandId){
        BaseResponse baseResponse = new BaseResponse(StatusCode.Success);
        try {
            baseResponse.setData(brandService.getBrandById(brandId));
        }catch (Exception e){
            baseResponse = new BaseResponse(StatusCode.Fail);
            e.printStackTrace();
        }
        return baseResponse;
    }

    /**
     * 修改品牌 - 同分类下品牌名不能重复，判断时除去本身id
     * @param brand
     * @return
     */
    @RequestMapping("/update")
    public BaseResponse updateBrand(@RequestBody Brand brand){
        BaseResponse baseResponse = new BaseResponse(StatusCode.Success);
        try {
            int exitFlag = brandService.brandNameUpdateExit(brand.getSortId(),brand.getBrandName(),brand.getBrandId());
            if(exitFlag > 0){
                baseResponse = new BaseResponse(StatusCode.BrandNameExit);
            }else{
                brandService.updateBrand(brand);
            }

        }catch (Exception e){
            baseResponse = new BaseResponse(StatusCode.Fail);
            e.printStackTrace();
        }
        return baseResponse;
    }

    /**
     * 通过分类获取品牌列表
     * @param sortId
     * @return
     */
    @RequestMapping("/getBrandListBySort")
    public BaseResponse getBrandListBySort(Integer sortId){
        BaseResponse baseResponse = new BaseResponse(StatusCode.Success);
        try {
            baseResponse.setData(brandService.getBrandListBySort(sortId));
        }catch (Exception e){
            baseResponse = new BaseResponse(StatusCode.Fail);
            e.printStackTrace();
        }
        return baseResponse;
    }

    @RequestMapping("/exportTemp")
    public void exportTemp(HttpServletResponse response) {
        try {
            String[] headers = { "品牌名称","分类名称","logo地址"};
            String fileName="品牌导入模板";
            ExcelUtil.exportTemp(response,headers,fileName);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @RequestMapping("/import")
    public BaseResponse importData(MultipartFile file,HttpServletRequest request) {
        BaseResponse baseResponse = new BaseResponse(StatusCode.Success);
        Map<Integer, String> errormap = new HashMap<>();
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
            List<Brand> importData = new ArrayList<Brand>();
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
                if(row.getCell(0) == null || row.getCell(1) == null || row.getCell(2) == null){
                    errormap.put(i+2,"各参数不能为空");
                    baseResponse = new BaseResponse(StatusCode.Fail);
                    baseResponse.setData(errormap);
                    return baseResponse;
                }
                String brandName = row.getCell(0).getStringCellValue();
                String sortName = row.getCell(1).getStringCellValue();
                String logoUrl = row.getCell(2).getStringCellValue();
                //通过sortName获取sortId
                Sort sort = sortService.getSortByName(sortName);
                if(sort == null){
                    errormap.put(i+2,"分类名称无效");
                    baseResponse = new BaseResponse(StatusCode.Fail);
                    baseResponse.setData(errormap);
                    return baseResponse;
                }
                if(sort.getState() == 1){
                    errormap.put(i+2,"分类已停用");
                    baseResponse = new BaseResponse(StatusCode.Fail);
                    baseResponse.setData(errormap);
                    return baseResponse;
                }
                Brand brand = new Brand();
                brand.setBrandName(brandName);
                brand.setSortId(sort.getSortId());
                brand.setLogoUrl(logoUrl);
                brand.setCreateTime(DateUtil.getNowDate());
                brand.setCreator(Integer.parseInt(request.getSession().getAttribute("userid").toString()));
                importData.add(brand);
            }
            boolean importExitFlag = true;
            //数据检验
            for(int i=0;i<importData.size();i++){
                //1、判断导入数据中是否有重复数据
                for(int j=0;j<importData.size();j++){
                    if(importData.get(i).getBrandName().equals(importData.get(j).getBrandName()) && importData.get(i).getSortId()==importData.get(j).getSortId() && i != j){
                        importExitFlag = false;
                        errormap.put(i+2, "与表中第"+(j+2)+"行数据重复");
                    }
                }
            }
            if(importExitFlag){
                boolean exitFlag = true;
                //导入数据自身无误、与数据库现有数据对比是否有重复
                for (int i=0;i<importData.size();i++){
                    if(brandService.brandNameExit(importData.get(i).getSortId(),importData.get(i).getBrandName()) > 0){
                        exitFlag = false;
                        errormap.put(i+2, "数据已存在");
                    }
                }
                if(exitFlag){
                    //数据校验无误。插入数据库
                    brandService.addBrandList(importData);
                }else{
                    baseResponse = new BaseResponse(StatusCode.Fail);
                    baseResponse.setData(errormap);
                }
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
}
