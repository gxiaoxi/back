package com.gxx.back.utils;

import com.gxx.back.bean.Article;
import com.gxx.back.bean.ArticleSort;
import com.gxx.back.bean.ArticleView;
import com.gxx.back.bean.Menu;
import com.gxx.back.common.BaseTree;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.*;

public class BaseTreeUtil {
    /**
     * 获取菜单树，两级
     * @param menuList
     * @return
     */
    public static JSONArray getTreeObject(List<Menu> menuList){

        List<BaseTree> allTree = new ArrayList<BaseTree>();
        for (int i=0;i<menuList.size();i++){
            if(menuList.get(i).getParentId() == 0){
                BaseTree parentTree = new BaseTree();
                parentTree.setId(String.valueOf(menuList.get(i).getMenuId()));
                parentTree.setTitle(menuList.get(i).getMenuName());
                parentTree.setField("");
                JSONArray childArray = new JSONArray();
                //List<BaseTree> childList = new ArrayList<BaseTree>();
                for (int j=0;j<menuList.size();j++){
                    if(menuList.get(j).getParentId() == menuList.get(i).getMenuId()){
                        BaseTree childTree = new BaseTree();
                        childTree.setId(String.valueOf(menuList.get(j).getMenuId()));
                        childTree.setTitle(menuList.get(j).getMenuName());
                        childTree.setField("");
                        childArray.add(childTree);
                    }
                }
                if(childArray.size() > 0){
                    parentTree.setChildren(childArray);
                }
                allTree.add(parentTree);
            }
        }
        JSONArray ja  = JSONArray.fromObject(allTree);
        return ja;
    }

    /**
     * 根据integer id拼装树形LIST数据递归 文章分类树
     * @param parentId
     * @return
     * @throws Exception
     */
    public static List<BaseTree> createTreeChildJson(List<ArticleSort> articleSortList, int parentId) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        List<BaseTree> list = new ArrayList<BaseTree>();

        List<ArticleSort> childList = new ArrayList<>();
        for (int i = 0;i<articleSortList.size();i++){
            if(articleSortList.get(i).getParentId() == parentId){
                childList.add(articleSortList.get(i));
            }
        }
        List<ArticleSort> lists =  childList;
        for (ArticleSort msp : lists) {
            BaseTree baseTree = new BaseTree();
            JSONArray ja = new JSONArray();
            int id = msp.getSortId();
            baseTree.setId(String.valueOf(id));
            baseTree.setTitle(msp.getSortName());
            ja = JSONArray.fromObject(createTreeChildJson(articleSortList,id)) ;
            baseTree.setChildren(ja);
            list.add(baseTree);
        }
        return list;

    }

    /**
     * 根据String id拼装树形LIST数据递归 文章与分类树
     * @param parentId
     * @return
     * @throws Exception
     */
    public static List<BaseTree> createTreeChildJson2(List<ArticleView> articleViewList, String parentId) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        List<BaseTree> list = new ArrayList<BaseTree>();

        List<ArticleView> childList = new ArrayList<>();
        for (int i = 0;i<articleViewList.size();i++){
            if(articleViewList.get(i).getParentId().equals(parentId)){
                childList.add(articleViewList.get(i));
            }
        }
        List<ArticleView> lists =  childList;
        for (ArticleView msp : lists) {
            BaseTree baseTree = new BaseTree();
            JSONArray ja = new JSONArray();
            String id = msp.getId();
            baseTree.setId(id);
            baseTree.setTitle(msp.getName());
            ja = JSONArray.fromObject(createTreeChildJson2(articleViewList,id)) ;
            baseTree.setChildren(ja);
            list.add(baseTree);
        }
        return list;

    }
}
