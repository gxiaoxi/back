package com.gxx.back.dao;

import com.gxx.back.bean.Brand;

import java.util.List;

public interface BrandDao {

    /**
     * 获取品牌列表
     * @return
     */
    List<Brand> getBrandList();

    /**
     * 新增品牌
     * @param brand
     */
    void addBrand(Brand brand);

    /**
     * 删除品牌
     * @param brandId
     */
    void deleteBrand(int brandId);

    /**
     * 获取品牌是否已被商品绑定
     * @param brandId
     * @return
     */
    int hasGoods(int brandId);

    /**
     * 获取当个品牌信息
     * @param brandId
     * @return
     */
    Brand getBrandById(int brandId);

    /**
     * 更新品牌
     * @param brand
     */
    void updateBrand(Brand brand);

    /**
     * 判断分类下，品牌名是否已存在
     * @param sortId
     * @param brandName
     * @return
     */
    int brandNameExit(int sortId,String brandName);

    /**
     * 更新是判断名称是否存在
     * @param sortId
     * @param brandName
     * @param brandId
     * @return
     */
    int brandNameUpdateExit(int sortId,String brandName,int brandId);

    /**
     * 通过分类获取brand列表
     * @param sortId
     * @return
     */
    List<Brand> getBrandListBySort(int sortId);

    /**
     * 通过分类与品牌名称获取品牌id
     * @param brandName
     * @param sortId
     * @return
     */
    Brand getBrandByNameAndSortId(String brandName,int sortId);
}
