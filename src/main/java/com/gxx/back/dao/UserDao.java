package com.gxx.back.dao;

import com.gxx.back.bean.User;

import java.util.List;

public interface UserDao {
    /**
     * 登录时校验,通过登录用户名查询用户信息：密码对比，获取角色权限等
     * @param userName
     * @return
     */
    User getUserLoginInfo(String userName);

    /**
     * 登录成功后，通过登录用户名获取用户信息
     * @param userName
     * @return
     */
    User getUserInfo(String userName);

    /**
     * 获取用户列表
     * @return
     */
    List<User> getUserList();

    /**
     * 更新用户状态
     * @param userId
     * @param state
     */
    void updateUserState(int userId,int state,String updateTime);

    /**
     * 更新用户级别
     * @param userId
     * @param level
     */
    void updateUserLevel(int userId,int level,String updateTime);

    /**
     * 删除用户
     * @param userId
     */
    void deleteUser(int userId);

    /**
     * 删除用户角色关联
     * @param userId
     */
    void deleteUserRole(int userId);

    /**
     * 新增用户
     * @param user
     */
    void addUser(User user);

    /**
     * 新增用户角色关联
     * @param userId
     * @param roleId
     */
    void addUserRole(int userId,int roleId);

    /**
     * 获取单个用户信息
     * @param userId
     * @return
     */
    User getUserById(int userId);

    /**
     * 更新用户信息（除去密码）
     * @param user
     */
    void updateUser(User user);
}
