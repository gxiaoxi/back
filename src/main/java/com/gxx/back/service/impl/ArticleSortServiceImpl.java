package com.gxx.back.service.impl;

import com.gxx.back.bean.ArticleSort;
import com.gxx.back.bean.ArticleView;
import com.gxx.back.dao.ArticleSortDao;
import com.gxx.back.service.ArticleSortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleSortServiceImpl implements ArticleSortService {

    @Autowired
    private ArticleSortDao dao;

    @Override
    public List<ArticleSort> getArticleSortList() {
        return dao.getArticleSortList();
    }

    @Override
    public void addArticleSort(ArticleSort articleSort) {
        dao.addArticleSort(articleSort);
    }

    @Override
    public void deleteArticleSort(int sortId) {
        dao.deleteArticleSort(sortId);
    }

    @Override
    public void updateArticleSort(ArticleSort articleSort) {
        dao.updateArticleSort(articleSort);
    }

    @Override
    public List<ArticleView> getViewsList() {
        return dao.getViewsList();
    }

    @Override
    public List<ArticleSort> getFirstArticleSortList() {
        return dao.getFirstArticleSortList();
    }
}
