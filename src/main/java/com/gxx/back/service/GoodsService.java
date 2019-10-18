package com.gxx.back.service;

import com.gxx.back.bean.Goods;

import java.util.List;

public interface GoodsService {
    List<Goods> getGoodsList();
    void deleteGoods(int goodsId);
    void addGoods(Goods goods);
    int brandExit(int brandId);
    void updateGoods(Goods goods);
    int brandExitWithoutIt(int brandId,int goodsId);
}
