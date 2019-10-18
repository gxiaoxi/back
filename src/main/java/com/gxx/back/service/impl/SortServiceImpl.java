package com.gxx.back.service.impl;

import com.gxx.back.bean.Sort;
import com.gxx.back.dao.SortDao;
import com.gxx.back.service.SortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class SortServiceImpl implements SortService {

    @Autowired
    private SortDao dao;

    @Override
    public List<Sort> getSortList() {
        return dao.getSortList();
    }

    @Override
    public void deleteSort(int sortId) {
        dao.deleteSort(sortId);
    }

    @Override
    public void addSort(Sort sort) {
        dao.addSort(sort);
    }
    @Override
    public void updateSort(Sort sort) {
        dao.updateSort(sort);
    }

    @Override
    public void updateSortState(int sortId, int state) {
        dao.updateSortState(sortId, state);
    }

    @Override
    public int hasBrand(int sortId) {
        return dao.hasBrand(sortId);
    }

    @Override
    public List<Sort> getSortListByState() {
        return dao.getSortListByState();
    }

    @Override
    public int sortExit(String sortName) {
        return dao.sortExit(sortName);
    }

    @Override
    public int sortExitWithoutIt(String sortName, int sortId) {
        return dao.sortExitWithoutIt(sortName, sortId);
    }

    @Override
    public Sort getSortByName(String sortName) {
        return dao.getSortByName(sortName);
    }

    @Transactional
    public void addSortList(List<Sort> sortList){
        for (int i=0;i<sortList.size();i++){
            dao.addSort(sortList.get(i));
        }
    }
}
