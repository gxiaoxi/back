package com.gxx.back.service;

import com.gxx.back.bean.Menu;

import java.util.List;

public interface MenuService {
    List<Menu> getMenuListByUserId(int userId);
    List<Menu> getMenuList();
    void addMenu(Menu menu);
    void addRoleMenu(int roleId,int menuId);
    void deleteMenu(int menuId);
    void deleteRoleMenu(int menuId);
    Menu getMenuById(int menuId);
    void updateMenu(Menu menu);
    List<Menu> getMenuListByRoleId(int roleId);
}
