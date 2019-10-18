package com.gxx.back.controller;

import com.gxx.back.bean.ArticleSort;
import com.gxx.back.bean.ArticleView;
import com.gxx.back.common.BaseResponse;
import com.gxx.back.common.BaseTree;
import com.gxx.back.common.StatusCode;
import com.gxx.back.service.impl.ArticleSortServiceImpl;
import com.gxx.back.utils.BaseTreeUtil;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/articleSort")
public class ArticleSortController {
    @Autowired
    private ArticleSortServiceImpl articleService;

    @RequestMapping("/getFirstList")
    public BaseResponse getFirstList(){
        BaseResponse baseResponse = new BaseResponse(StatusCode.Success);
        try {
            List<ArticleSort> articleSortList = articleService.getFirstArticleSortList();
            baseResponse.setData(articleSortList);
        }catch (Exception e){
            e.printStackTrace();
            baseResponse = new BaseResponse(StatusCode.Fail);
        }
        return baseResponse;
    }

    @RequestMapping("/list")
    public JSONArray getArticleSortList(Integer id) throws Exception {
        JSONArray ja = new JSONArray();
        List<ArticleSort> articleSortList = articleService.getArticleSortList();
        List<BaseTree> list = BaseTreeUtil.createTreeChildJson(articleSortList,id);
        ja = JSONArray.fromObject(list);
        return ja;
    }
    @RequestMapping("/listviews")
    public JSONArray getArticleSortListViews(String id) throws Exception {
        JSONArray ja = new JSONArray();
        List<ArticleView> articleViewList = articleService.getViewsList();
        List<BaseTree> list = BaseTreeUtil.createTreeChildJson2(articleViewList,id);
        ja = JSONArray.fromObject(list);
        return ja;
    }

    @RequestMapping("/delete")
    public BaseResponse deleteArticleSort(int sortId){
        BaseResponse baseResponse = new BaseResponse(StatusCode.Success);
        try {
            articleService.deleteArticleSort(sortId);
        }catch (Exception e){
            baseResponse = new BaseResponse(StatusCode.Fail);
        }
        return baseResponse;
    }
    @RequestMapping("/add")
    public BaseResponse addArticleSort(@RequestBody ArticleSort articleSort){
        BaseResponse baseResponse = new BaseResponse(StatusCode.Success);
        try {
            articleService.addArticleSort(articleSort);
        }catch (Exception e){
            baseResponse = new BaseResponse(StatusCode.Fail);
        }
        return baseResponse;
    }
    @RequestMapping("/update")
    public BaseResponse updateArticleSort(@RequestBody ArticleSort articleSort){
        BaseResponse baseResponse = new BaseResponse(StatusCode.Success);
        try {
            articleService.updateArticleSort(articleSort);
        }catch (Exception e){
            baseResponse = new BaseResponse(StatusCode.Fail);
        }
        return baseResponse;
    }
}
