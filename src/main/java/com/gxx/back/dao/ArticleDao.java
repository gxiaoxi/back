package com.gxx.back.dao;

import com.gxx.back.bean.Article;
import com.gxx.back.bean.ArticleView;

import java.util.List;

public interface ArticleDao {
    void addArticle(Article article);
    Article getArticleById(int articleId);
    List<Article> getArticleList();
    void deleteArticle(int articleId);
    void updateArticle(Article article);
    void addRead(int articleId);
}
