package com.gxx.back.service;

import com.gxx.back.bean.Sort;

import java.util.List;

public interface SortService {
    List<Sort> getSortList();
    void deleteSort(int sortId);
    void addSort(Sort sort);
    void updateSort(Sort sort);
    void updateSortState(int sortId,int state);
    int hasBrand(int sortId);
    List<Sort> getSortListByState();
    int sortExit(String sortName);
    int sortExitWithoutIt(String sortName,int sortId);
    Sort getSortByName(String sortName);
}
