package com.gxx.back.service.impl;

import com.gxx.back.bean.Goods;
import com.gxx.back.dao.GoodsDao;
import com.gxx.back.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsDao dao;

    @Override
    public List<Goods> getGoodsList() {
        return dao.getGoodsList();
    }

    @Override
    public void deleteGoods(int goodsId) {
        dao.deleteGoods(goodsId);
    }

    @Override
    public void addGoods(Goods goods) {
        dao.addGoods(goods);
    }

    @Override
    public int brandExit(int brandId) {
        return dao.brandExit(brandId);
    }

    @Override
    public void updateGoods(Goods goods) {
        dao.updateGoods(goods);
    }

    @Override
    public int brandExitWithoutIt(int brandId, int goodsId) {
        return dao.brandExitWithoutIt(brandId, goodsId);
    }
    @Transactional
    public void addGoodsList(List<Goods> goodsList){
        for(int i=0;i<goodsList.size();i++){
            dao.addGoods(goodsList.get(i));
        }
    }
}
