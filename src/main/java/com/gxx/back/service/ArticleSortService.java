package com.gxx.back.service;

import com.gxx.back.bean.ArticleSort;
import com.gxx.back.bean.ArticleView;

import java.util.List;

public interface ArticleSortService {
    List<ArticleSort> getArticleSortList();
    void addArticleSort(ArticleSort articleSort);
    void deleteArticleSort(int sortId);
    void updateArticleSort(ArticleSort articleSort);
    List<ArticleView> getViewsList();
    List<ArticleSort> getFirstArticleSortList();
}
