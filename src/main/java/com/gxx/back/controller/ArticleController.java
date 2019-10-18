package com.gxx.back.controller;

import com.gxx.back.bean.Article;
import com.gxx.back.common.BaseResponse;
import com.gxx.back.common.StatusCode;
import com.gxx.back.service.impl.ArticleServiceImpl;
import com.gxx.back.utils.DateUtil;
import com.gxx.back.utils.FileUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.FileNameMap;
import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleServiceImpl articleService;

    /**
     * 新增文章
     * @param article
     * @param request
     * @return
     */
    @RequestMapping("/add")
    public BaseResponse addArticle(@RequestBody Article article, HttpServletRequest request){
        BaseResponse baseResponse = new BaseResponse(StatusCode.Success);
        try {
            article.setCreateTime(DateUtil.getNowDate());
            article.setCreator(Integer.parseInt(request.getSession().getAttribute("userid").toString()));
            articleService.addArticle(article);
        }catch (Exception e){
            baseResponse = new BaseResponse(StatusCode.Fail);
            e.printStackTrace();
        }
        return baseResponse;
    }

    /**
     * 获取单条文章信息
     * @param articleId
     * @return
     */
    @RequestMapping("/getArticleById")
    public BaseResponse addArticle(Integer articleId){
        BaseResponse baseResponse = new BaseResponse(StatusCode.Success);
        try {
            baseResponse.setData(articleService.getArticleById(articleId));
        }catch (Exception e){
            baseResponse = new BaseResponse(StatusCode.Fail);
            e.printStackTrace();
        }
        return baseResponse;
    }

    /**
     * 获取文章列表
     * @return
     */
    @RequestMapping("/list")
    public JSONObject getArticleList(){
        JSONObject jb=new JSONObject();
        List<Article> articleList = articleService.getArticleList();
        jb.put("code",0);
        jb.put("msg","");
        jb.put("count",articleList.size());
        jb.put("data",articleList);
        return jb;
    }

    /**
     * 删除文章
     * @param articleId
     * @return
     */
    @RequestMapping("/delete")
    public BaseResponse deleterticle(Integer articleId){
        BaseResponse baseResponse = new BaseResponse(StatusCode.Success);
        try {
            articleService.deleteArticle(articleId);
        }catch (Exception e){
            baseResponse = new BaseResponse(StatusCode.Fail);
            e.printStackTrace();
        }
        return baseResponse;
    }

    /**
     * 更新文章
     * @param article
     * @return
     */
    @RequestMapping("/update")
    public BaseResponse updateArticle(@RequestBody Article article){
        BaseResponse baseResponse = new BaseResponse(StatusCode.Success);
        try {
            articleService.updateArticle(article);
        }catch (Exception e){
            baseResponse = new BaseResponse(StatusCode.Fail);
            e.printStackTrace();
        }
        return baseResponse;
    }

    /**
     * 阅读加1
     * @param articleId
     * @return
     */
    @RequestMapping("/addRead")
    public BaseResponse addRead(Integer articleId){
        BaseResponse baseResponse = new BaseResponse(StatusCode.Success);
        try {
            articleService.addRead(articleId);
        }catch (Exception e){
            baseResponse = new BaseResponse(StatusCode.Fail);
            e.printStackTrace();
        }
        return baseResponse;
    }

    @RequestMapping("/download")
    public void downloadFile(HttpServletResponse response,String url) throws Exception {
        String fileName = url.substring(url.lastIndexOf("/")+1);
        FileUtil.downloadByNet(response,url,fileName);
    }
}
