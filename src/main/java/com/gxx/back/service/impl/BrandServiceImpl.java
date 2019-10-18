package com.gxx.back.service.impl;

import com.gxx.back.bean.Brand;
import com.gxx.back.dao.BrandDao;
import com.gxx.back.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandDao dao;

    @Override
    public List<Brand> getBrandList() {
        return dao.getBrandList();
    }

    @Override
    public void addBrand(Brand brand) {
        dao.addBrand(brand);
    }

    @Override
    public void deleteBrand(int brandId) {
        dao.deleteBrand(brandId);
    }

    @Override
    public int hasGoods(int brandId) {
        return dao.hasGoods(brandId);
    }

    @Override
    public Brand getBrandById(int brandId) {
        return dao.getBrandById(brandId);
    }

    @Override
    public void updateBrand(Brand brand) {
        dao.updateBrand(brand);
    }

    @Override
    public int brandNameExit(int sortId, String brandName) {
        return dao.brandNameExit(sortId, brandName);
    }

    @Override
    public List<Brand> getBrandListBySort(int sortId) {
        return dao.getBrandListBySort(sortId);
    }

    @Override
    public int brandNameUpdateExit(int sortId, String brandName, int brandId) {
        return dao.brandNameUpdateExit(sortId, brandName, brandId);
    }

    @Override
    public Brand getBrandByNameAndSortId(String brandName, int sortId) {
        return dao.getBrandByNameAndSortId(brandName, sortId);
    }

    @Transactional
    public void addBrandList(List<Brand> brandList){
        for(int i=0;i<brandList.size();i++){
            dao.addBrand(brandList.get(i));
        }
    }
}
