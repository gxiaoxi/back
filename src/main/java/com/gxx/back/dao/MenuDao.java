package com.gxx.back.dao;

import com.gxx.back.bean.Menu;

import java.util.List;

public interface MenuDao {
    /**
     * 通过用户id获取菜单列表
     * @param userId
     * @return
     */
    List<Menu> getMenuListByUserId(int userId);

    /**
     * 通过角色id获取菜单列表
     * @param roleId
     * @return
     */
    List<Menu> getMenuListByRoleId(int roleId);

    /**
     * 获取所有菜单列表
     * @return
     */
    List<Menu> getMenuList();

    /**
     * 新增菜单
     * @param menu
     */
    void addMenu(Menu menu);

    /**
     * 新增角色菜单关联
     * @param roleId
     * @param menuId
     */
    void addRoleMenu(int roleId,int menuId);

    /**
     * 删除菜单
     * @param menuId
     */
    void deleteMenu(int menuId);

    /**
     * 删除角色菜单关联
     * @param menuId
     */
    void deleteRoleMenu(int menuId);

    /**
     * 获取单条菜单信息
     * @param menuId
     * @return
     */
    Menu getMenuById(int menuId);

    /**
     * 编辑菜单
     * @param menu
     */
    void updateMenu(Menu menu);
}
