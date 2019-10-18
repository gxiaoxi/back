package com.gxx.back.service.impl;

import com.gxx.back.bean.User;
import com.gxx.back.dao.UserDao;
import com.gxx.back.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao dao;


    @Override
    public User getUserLoginInfo(String userName) {
        return dao.getUserLoginInfo(userName);
    }

    @Override
    public User getUserInfo(String userName) {
        return dao.getUserInfo(userName);
    }

    @Override
    public List<User> getUserList() {
        return dao.getUserList();
    }

    @Override
    public void updateUserState(int userId, int state,String updateTime) {
        dao.updateUserState(userId,state,updateTime);
    }

    @Override
    public void updateUserLevel(int userId, int level,String updateTime) {
        dao.updateUserLevel(userId, level,updateTime);
    }

    @Transactional
    @Override
    public void deleteUser(int userId) {
        dao.deleteUser(userId);
        dao.deleteUserRole(userId);
    }

    @Override
    public void deleteUserRole(int userId) {
        dao.deleteUserRole(userId);
    }

    @Override
    public void addUser(User user) {
        dao.addUser(user);
    }

    @Override
    public void addUserRole(int userId, int roleId) {
        dao.addUserRole(userId, roleId);
    }

    @Override
    public User getUserById(int userId) {
        return dao.getUserById(userId);
    }

    @Override
    public void updateUser(User user) {
        dao.updateUser(user);
    }

    @Transactional
    public void grantUserRole(int userId,List<Integer> roleIds){
        dao.deleteUserRole(userId);
        for (int i=0;i<roleIds.size();i++){
            dao.addUserRole(userId,roleIds.get(i));
        }
    }
}
