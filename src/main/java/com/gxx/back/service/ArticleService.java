package com.gxx.back.service;

import com.gxx.back.bean.Article;
import com.gxx.back.bean.ArticleView;

import java.util.List;

public interface ArticleService {
    void addArticle(Article article);
    Article getArticleById(int articleId);
    List<Article> getArticleList();
    void deleteArticle(int articleId);
    void updateArticle(Article article);
    void addRead(int articleId);
}
