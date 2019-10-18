package com.gxx.back.controller;

import com.gxx.back.bean.Brand;
import com.gxx.back.bean.Goods;
import com.gxx.back.bean.Sort;
import com.gxx.back.common.BaseResponse;
import com.gxx.back.common.StatusCode;
import com.gxx.back.service.impl.BrandServiceImpl;
import com.gxx.back.service.impl.GoodsServiceImpl;
import com.gxx.back.service.impl.SortServiceImpl;
import com.gxx.back.utils.DateUtil;
import com.gxx.back.utils.ExcelUtil;
import net.sf.json.JSONObject;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private GoodsServiceImpl goodsService;
    @Autowired
    private SortServiceImpl sortService;
    @Autowired
    private BrandServiceImpl brandService;
    /**
     * 获取商品列表
     * @return
     */
    @RequestMapping("/list")
    public JSONObject getGoodsList(){
        JSONObject jb = new JSONObject();
        List<Goods> goodsList = goodsService.getGoodsList();
        jb.put("code",0);
        jb.put("msg","");
        jb.put("count",goodsList.size());
        jb.put("data",goodsList);
        return jb;
    }

    /**
     * 删除商品
     * @param goodsId
     * @return
     */
    @RequestMapping("/delete")
    public BaseResponse deleteGoods(Integer goodsId){
        BaseResponse baseResponse = new BaseResponse(StatusCode.Success);
        try {
            goodsService.deleteGoods(goodsId);
        }catch (Exception e){
            baseResponse = new BaseResponse(StatusCode.Fail);
            e.printStackTrace();
        }
        return baseResponse;
    }

    /**
     * 新增商品 - 判断品牌id跟分类id不能重复
     * @param goods
     * @return
     */
    @RequestMapping("/add")
    public BaseResponse addGoods(@RequestBody Goods goods, HttpServletRequest request){
        BaseResponse baseResponse = new BaseResponse(StatusCode.Success);
        try {

            int exitFlag = goodsService.brandExit(goods.getBrandId());
            if(exitFlag > 0){
                baseResponse = new BaseResponse(StatusCode.BrandIdExit);
            }else{
                goods.setCreateTime(DateUtil.getNowDate());
                goods.setCreator(Integer.parseInt(request.getSession().getAttribute("userid").toString()));
                goodsService.addGoods(goods);
            }

        }catch (Exception e){
            baseResponse = new BaseResponse(StatusCode.Fail);
            e.printStackTrace();
        }
        return baseResponse;
    }

    /**
     * 更新商品- 需先判断除本身以外。品牌是否已被占用
     * @param goods
     * @return
     */
    @RequestMapping("/update")
    public BaseResponse updateGoods(@RequestBody Goods goods){
        BaseResponse baseResponse = new BaseResponse(StatusCode.Success);
        try {

            int exitFlag = goodsService.brandExitWithoutIt(goods.getBrandId(),goods.getGoodsId());
            if(exitFlag > 0){
                baseResponse = new BaseResponse(StatusCode.BrandIdExit);
            }else{
                goodsService.updateGoods(goods);
            }

        }catch (Exception e){
            baseResponse = new BaseResponse(StatusCode.Fail);
            e.printStackTrace();
        }
        return baseResponse;
    }

    /**
     * 下载导入模板
     * @param response
     */
    @RequestMapping("/exportTemp")
    public void exportTemp(HttpServletResponse response) {
        try {
            String[] headers = { "商品名称","分类名称","品牌名称"};
            String fileName="商品导入模板";
            ExcelUtil.exportTemp(response,headers,fileName);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @RequestMapping("/import")
    public BaseResponse importData(MultipartFile file, HttpServletRequest request) {
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
            List<Goods> importData = new ArrayList<Goods>();
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
                String goodsName = row.getCell(0).getStringCellValue();
                String sortName = row.getCell(1).getStringCellValue();
                String brandName = row.getCell(2).getStringCellValue();
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
                //通过sortId/brandName 获取品牌名称
                Brand brand = brandService.getBrandByNameAndSortId(brandName,sort.getSortId());
                if(brand == null){
                    errormap.put(i+2,"品牌名称无效");
                    baseResponse = new BaseResponse(StatusCode.Fail);
                    baseResponse.setData(errormap);
                    return baseResponse;
                }

                Goods goods = new Goods();
                goods.setGoodsName(goodsName);
                goods.setBrandId(brand.getBrandId());
                goods.setSortId(sort.getSortId());
                goods.setCreateTime(DateUtil.getNowDate());
                goods.setCreator(Integer.parseInt(request.getSession().getAttribute("userid").toString()));
                importData.add(goods);
            }

            boolean importExitFlag = true;
            //数据检验
            for(int i=0;i<importData.size();i++){
                //1、判断导入数据中是否有重复数据
                for(int j=0;j<importData.size();j++){
                    if(importData.get(i).getBrandId() == importData.get(j).getBrandId() && i!=j){
                        importExitFlag = false;
                        errormap.put(i+2, "与表中第"+(j+2)+"行分类品牌数据重复");
                    }
                }
            }
            if(importExitFlag){
                boolean exitFlag = true;
                //导入数据自身无误、与数据库现有数据对比是否有重复
                for (int i=0;i<importData.size();i++){
                    if(goodsService.brandExit(importData.get(i).getBrandId()) > 0){
                        exitFlag = false;
                        errormap.put(i+2, "品牌下已有商品");
                    }
                }
                if(exitFlag){
                    //数据校验无误。插入数据库
                    goodsService.addGoodsList(importData);
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
