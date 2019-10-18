package com.gxx.back.dao;

import com.gxx.back.bean.Sort;

import java.util.List;

public interface SortDao {
    /**
     * 获取分类列表
     * @return
     */
    List<Sort> getSortList();

    /**
     * 删除分类
     * @param sortId
     */
    void deleteSort(int sortId);

    /**
     * 新增分类
     * @param sort
     */
    void addSort(Sort sort);

    /**
     * 修改分类信息
     * @param sort
     */
    void updateSort(Sort sort);

    /**
     * 更新分类状态
     * @param sortId
     * @param state
     */
    void updateSortState(int sortId,int state);

    /**
     * 获取分类是否已被品牌绑定
     * @param sortId
     * @return
     */
    int hasBrand(int sortId);

    /**
     * 获取有效的分类
     * @return
     */
    List<Sort> getSortListByState();

    /**
     * 判断分类名是否已存在
     * @param sortName
     * @return
     */
    int sortExit(String sortName);

    /**
     * 判断分类名是否已存在除了自己
     * @param sortName
     * @param sortId
     * @return
     */
    int sortExitWithoutIt(String sortName,int sortId);

    /**
     * 通过名称获取分类
     * @param sortName
     * @return
     */
    Sort getSortByName(String sortName);
}
