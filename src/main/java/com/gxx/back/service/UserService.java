package com.gxx.back.service;

import com.gxx.back.bean.User;

import java.util.List;


public interface UserService {
    User getUserLoginInfo(String userName);
    User getUserInfo(String userName);
    List<User> getUserList();
    void updateUserState(int userId,int state,String updateTime);
    void updateUserLevel(int userId,int level,String updateTime);
    void deleteUser(int userId);
    void deleteUserRole(int userId);
    void addUser(User user);
    void addUserRole(int userId,int roleId);
    User getUserById(int userId);
    void updateUser(User user);
}
