package com.gxx.back.controller;

import com.gxx.back.bean.User;
import com.gxx.back.common.BaseResponse;
import com.gxx.back.common.StatusCode;
import com.gxx.back.service.impl.UserServiceImpl;
import com.gxx.back.utils.DateUtil;
import com.gxx.back.utils.MD5Util;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @RequestMapping("/list")
    public JSONObject getUserList(){
        JSONObject jb = new JSONObject();
        List<User> userList = userService.getUserList();
        jb.put("code",0);
        jb.put("msg","");
        jb.put("count",userList.size());
        jb.put("data",userList);
        return jb;
    }
    @RequestMapping("/updateUserState")
    public void updateUserState(Integer userId,Integer state){
        userService.updateUserState(userId,state,DateUtil.getNowDate());
    }
    @RequestMapping("/updateUserLevel")
    public void updateUserLevel(Integer userId,Integer level){
        userService.updateUserLevel(userId,level,DateUtil.getNowDate());
    }

    /**
     * 删除用户、同时删除用户角色关联
     * @param userId
     * @return
     */
    @RequestMapping("/delete")
    public BaseResponse deleteUser(Integer userId){
        BaseResponse baseResponse = new BaseResponse(StatusCode.Success);
        try {
            userService.deleteUser(userId);
        }catch (Exception e){
            baseResponse = new BaseResponse(StatusCode.Fail);
            e.printStackTrace();
        }
        return baseResponse;
    }

    /**
     * 新增用户
     * @param user
     * @return
     */
    @RequestMapping("/add")
    public BaseResponse addUser(@RequestBody User user){
        BaseResponse baseResponse = new BaseResponse(StatusCode.Success);
        try {
            //判断用户名不能重复
            if(userService.getUserInfo(user.getUserName()) == null){
                user.setCreateTime(DateUtil.getNowDate());
                String password = MD5Util.generate(user.getUserPassWord());
                user.setUserPassWord(password);
                userService.addUser(user);
            }else{
                baseResponse = new BaseResponse(StatusCode.UserNameExit);
            }
        }catch (Exception e){
            baseResponse = new BaseResponse(StatusCode.Fail);
            e.printStackTrace();
        }
        return baseResponse;
    }

    /**
     * 获取单个用户信息
     * @param userId
     * @return
     */
    @RequestMapping("/getUserById")
    public BaseResponse getUserById(Integer userId){
        BaseResponse baseResponse = new BaseResponse(StatusCode.Success);
        try {
            User user = userService.getUserById(userId);
            baseResponse.setData(user);
        }catch (Exception e){
            baseResponse = new BaseResponse(StatusCode.Fail);
            e.printStackTrace();
        }
        return baseResponse;
    }

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    @RequestMapping("/update")
    public BaseResponse updateUser(@RequestBody User user){
        BaseResponse baseResponse = new BaseResponse(StatusCode.Success);
        try {
            user.setUpdateTime(DateUtil.getNowDate());
            userService.updateUser(user);
        }catch (Exception e){
            baseResponse = new BaseResponse(StatusCode.Fail);
            e.printStackTrace();
        }
        return baseResponse;
    }
    @RequestMapping("/grant")
    public BaseResponse grant(Integer userId,String grantJson){
        BaseResponse baseResponse = new BaseResponse(StatusCode.Success);
        try {
            JSONArray ja = JSONArray.fromObject(grantJson);
            List<Integer> roleIds = new ArrayList<Integer>();
            for (int i=0;i<ja.size();i++){
                JSONObject jb = JSONObject.fromObject(ja.get(i));
                int roleId = Integer.parseInt(jb.get("value").toString());
                roleIds.add(roleId);
            }
            userService.grantUserRole(userId,roleIds);
        }catch (Exception e){
            baseResponse = new BaseResponse(StatusCode.Fail);
            e.printStackTrace();
        }
        return baseResponse;
    }
}
