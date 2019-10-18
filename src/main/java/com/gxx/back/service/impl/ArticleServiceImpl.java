package com.gxx.back.service.impl;

import com.gxx.back.bean.Article;
import com.gxx.back.bean.ArticleView;
import com.gxx.back.dao.ArticleDao;
import com.gxx.back.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleDao dao;

    @Override
    public void addArticle(Article article) {
        dao.addArticle(article);
    }

    @Override
    public Article getArticleById(int articleId) {
        return dao.getArticleById(articleId);
    }

    @Override
    public List<Article> getArticleList() {
        return dao.getArticleList();
    }

    @Override
    public void deleteArticle(int articleId) {
        dao.deleteArticle(articleId);
    }

    @Override
    public void updateArticle(Article article) {
        dao.updateArticle(article);
    }

    @Override
    public void addRead(int articleId) {
        dao.addRead(articleId);
    }

}
