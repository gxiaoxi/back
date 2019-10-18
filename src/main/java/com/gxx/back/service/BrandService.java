package com.gxx.back.service;

import com.gxx.back.bean.Brand;

import java.util.List;

public interface BrandService {
    List<Brand> getBrandList();
    void addBrand(Brand brand);
    void deleteBrand(int brandId);
    int hasGoods(int brandId);
    Brand getBrandById(int brandId);
    void updateBrand(Brand brand);
    int brandNameExit(int sortId,String brandName);
    List<Brand> getBrandListBySort(int sortId);
    int brandNameUpdateExit(int sortId,String brandName,int brandId);
    Brand getBrandByNameAndSortId(String brandName,int sortId);
}
