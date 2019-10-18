package com.gxx.back.controller;

import com.gxx.back.bean.Role;
import com.gxx.back.common.BaseResponse;
import com.gxx.back.common.StatusCode;
import com.gxx.back.service.impl.RoleServiceImpl;
import com.gxx.back.utils.DateUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleServiceImpl roleService;

    /**
     * 获取角色列表
     * @return
     */
    @RequestMapping("/list")
    public JSONObject getRoleList(){
        List<Role> roleList = roleService.getRoleList();
        JSONObject jb = new JSONObject();
        jb.put("code","0");
        jb.put("msg","");
        jb.put("count",roleList.size());
        jb.put("data",roleList);
        return jb;
    }

    /**
     * 新增角色
     * @param role
     * @return
     */
    @RequestMapping("/add")
    public BaseResponse addRole(@RequestBody Role role){
        BaseResponse baseResponse = new BaseResponse(StatusCode.Success);
        try {
            role.setCreateTime(DateUtil.getNowDate());
            roleService.addRole(role);
        }catch (Exception e){
            baseResponse = new BaseResponse(StatusCode.Fail);
            e.printStackTrace();
        }
        return baseResponse;
    }

    /**
     * 获取单条角色信息
     * @param roleId
     * @return
     */
    @RequestMapping("/getRoleById")
    public BaseResponse getRoleById(Integer roleId){
        BaseResponse baseResponse = new BaseResponse(StatusCode.Success);
        try {
            Role role = roleService.getRoleById(roleId);
            baseResponse.setData(role);
        }catch (Exception e){
            baseResponse = new BaseResponse(StatusCode.Fail);
            e.printStackTrace();
        }
        return baseResponse;
    }

    /**
     * 编辑角色
     * @param role
     * @return
     */
    @RequestMapping("/update")
    public BaseResponse updateRole(@RequestBody Role role){
        BaseResponse baseResponse = new BaseResponse(StatusCode.Success);
        try {
            role.setUpdateTime(DateUtil.getNowDate());
            roleService.updateRole(role);
        }catch (Exception e){
            baseResponse = new BaseResponse(StatusCode.Fail);
            e.printStackTrace();
        }
        return baseResponse;
    }

    /**
     * 删除角色、同时删除角色菜单关联表，优先判断角色是否已被用户绑定
     * @param roleId
     * @return
     */
    @RequestMapping("/delete")
    public BaseResponse deleteRole(Integer roleId){
        BaseResponse baseResponse = new BaseResponse(StatusCode.Success);
        try {
            //判断是否已有用户关联
            int hasFlag = roleService.roleHasUser(roleId);
            if(hasFlag > 0){
                baseResponse = new BaseResponse(StatusCode.UserHasRole);
            }else{
                roleService.deleteRole(roleId);
            }
        }catch (Exception e){
            baseResponse = new BaseResponse(StatusCode.Fail);
            e.printStackTrace();
        }
        return baseResponse;
    }

    /**
     * 获取用户权限列表（已分配，和所有）
     * @param userId
     * @return
     */
    @RequestMapping("/getUserRoleList")
    public BaseResponse getUserRoleList(Integer userId){
        BaseResponse baseResponse = new BaseResponse(StatusCode.Success);
        try {
            List<Role> roleList =  roleService.getUserRoleList(userId);
            JSONArray ja = new JSONArray();
            for (int i=0;i<roleList.size();i++){
                ja.add(roleList.get(i).getRoleId());
            }
            List<Role> allRoleList =  roleService.getRoleList();
            JSONArray allja = new JSONArray();
            for (int i=0;i<allRoleList.size();i++){
                JSONObject jb = new JSONObject();
                jb.put("value",allRoleList.get(i).getRoleId());
                jb.put("title",allRoleList.get(i).getRoleName());
                allja.add(jb);
            }
            Map map = new HashMap();
            map.put("all",allja);
            map.put("exit",ja);
            baseResponse.setData(map);
        }catch (Exception e){
            baseResponse = new BaseResponse(StatusCode.Fail);
            e.printStackTrace();
        }
        return baseResponse;
    }
}
