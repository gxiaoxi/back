package com.gxx.back.controller;

import com.gxx.back.bean.Menu;
import com.gxx.back.common.BaseResponse;
import com.gxx.back.common.StatusCode;
import com.gxx.back.service.impl.MenuServiceImpl;
import com.gxx.back.utils.BaseTreeUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private MenuServiceImpl menuService;

    /**
     * 获取菜单列表
     * @return
     */
    @RequestMapping("/list")
    public JSONObject getMenuList(){
        List<Menu> menuList = menuService.getMenuList();
        JSONObject jb = new JSONObject();
        jb.put("code",0);
        jb.put("msg","");
        jb.put("count",menuList.size());
        jb.put("data", JSONArray.fromObject(menuList));
        return jb;
    }

    /**
     * 新增菜单，并将菜单权限赋予管理员roleId = 1
     * @param menu
     * @return
     */
    @RequestMapping("/add")
    public BaseResponse addMenu(@RequestBody Menu menu){
        BaseResponse baseResponse = new BaseResponse(StatusCode.Success);
        try {
            menuService.addMenu(menu);
        }catch (Exception e){
            baseResponse = new BaseResponse(StatusCode.Fail);
            e.printStackTrace();
        }
        return baseResponse;
    }

    /**
     * 删除菜单、同时删除菜单角色关联
     * @param menuId
     * @return
     */
    @RequestMapping("/delete")
    public BaseResponse deleteMenu(Integer menuId){
        BaseResponse baseResponse = new BaseResponse(StatusCode.Success);
        try {
            menuService.deleteMenu(menuId);
        }catch (Exception e){
            baseResponse = new BaseResponse(StatusCode.Fail);
            e.printStackTrace();
        }
        return baseResponse;
    }

    /**
     * 获取单条菜单信息
     * @param menuId
     * @return
     */
    @RequestMapping("/getMenuById")
    public BaseResponse getMenuById(Integer menuId){
        BaseResponse baseResponse = new BaseResponse(StatusCode.Success);
        try {
           Menu menu = menuService.getMenuById(menuId);
           baseResponse.setData(menu);
        }catch (Exception e){
            baseResponse = new BaseResponse(StatusCode.Fail);
            e.printStackTrace();
        }
        return baseResponse;
    }

    /**
     * 编辑菜单
     * @param menu
     * @return
     */
    @RequestMapping("/update")
    public BaseResponse updateMenu(@RequestBody Menu menu){
        BaseResponse baseResponse = new BaseResponse(StatusCode.Success);
        try {
            menuService.updateMenu(menu);
        }catch (Exception e){
            baseResponse = new BaseResponse(StatusCode.Fail);
            e.printStackTrace();
        }
        return baseResponse;
    }

    /**
     * 获取菜单树
     * @return
     */
    @RequestMapping("/treeList")
    public JSONArray getMenuTreeList(){
        List<Menu> menuList = menuService.getMenuList();
        JSONArray ja =BaseTreeUtil.getTreeObject(menuList);

        return ja;
    }

    /**
     * 通过角色id获取菜单id列表-用于授权
     * @param roleId
     * @return
     */
    @RequestMapping("/getMenuListByRoleId")
    public BaseResponse getMenuListByRoleId(Integer roleId){
        BaseResponse baseResponse = new BaseResponse(StatusCode.Success);
        try {
           List<Menu> menuList = menuService.getMenuListByRoleId(roleId);
           baseResponse.setData(menuList);
        }catch (Exception e){
            baseResponse = new BaseResponse(StatusCode.Fail);
            e.printStackTrace();
        }
        return baseResponse;
    }

    /**
     * 为角色分配菜单权限
     * @param roleId
     * @param grantJson
     * @return
     */
    @RequestMapping("/grant")
    public BaseResponse grantRoleMenu(Integer roleId,String grantJson){
        BaseResponse baseResponse = new BaseResponse(StatusCode.Success);
        try {
            List<Integer> grantList = new ArrayList<Integer>();
            JSONArray ja = JSONArray.fromObject(grantJson);
            for(int i=0;i<ja.size();i++){
                JSONObject jb = JSONObject.fromObject(ja.get(i));
                grantList.add(Integer.parseInt(jb.get("id").toString()));
                JSONArray childJa = JSONArray.fromObject(jb.get("children"));
                for(int j=0;j<childJa.size();j++){
                    JSONObject childJb = JSONObject.fromObject(childJa.get(j));
                    grantList.add(Integer.parseInt(childJb.get("id").toString()));
                }
            }

            menuService.grantRoleMenu(roleId,grantList);

        }catch (Exception e){
            baseResponse = new BaseResponse(StatusCode.Fail);
            e.printStackTrace();
        }
        return baseResponse;
    }
}
