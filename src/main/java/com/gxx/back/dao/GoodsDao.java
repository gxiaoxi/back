package com.gxx.back.dao;

import com.gxx.back.bean.Goods;

import java.util.List;

public interface GoodsDao {
    /**
     * 获取商品列表
     * @return
     */
    List<Goods> getGoodsList();

    /**
     * 删除商品
     *
     * @param goodsId
     */
    void deleteGoods(int goodsId);

    /**
     * 新增商品
     *
     * @param goods
     */
    void addGoods(Goods goods);

    /**
     * 获取品牌是否已存在
     * @param brandId
     * @return
     */
    int brandExit(int brandId);

    /**
     * 除本身外，品牌是否已被占用
     * @param brandId
     * @param goodsId
     * @return
     */
    int brandExitWithoutIt(int brandId,int goodsId);
    /**
     * 更新商品
     * @param goods
     */
    void updateGoods(Goods goods);

}
