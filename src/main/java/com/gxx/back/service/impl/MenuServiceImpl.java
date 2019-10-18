package com.gxx.back.service.impl;

import com.gxx.back.bean.Menu;
import com.gxx.back.dao.MenuDao;
import com.gxx.back.dao.RoleDao;
import com.gxx.back.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuDao dao;
    @Autowired
    private RoleDao roleDao;

    @Override
    public List<Menu> getMenuListByUserId(int userId) {
        return dao.getMenuListByUserId(userId);
    }

    @Override
    public List<Menu> getMenuList() {
        return dao.getMenuList();
    }

    @Transactional
    @Override
    public void addMenu(Menu menu) {
        dao.addMenu(menu);
        dao.addRoleMenu(1,menu.getMenuId());
    }

    @Override
    public void addRoleMenu(int roleId, int menuId) {
        dao.addRoleMenu(roleId, menuId);
    }

    @Transactional
    @Override
    public void deleteMenu(int menuId) {
        dao.deleteMenu(menuId);
        dao.deleteRoleMenu(menuId);
    }

    @Override
    public void deleteRoleMenu(int menuId) {
        dao.deleteRoleMenu(menuId);
    }

    @Override
    public Menu getMenuById(int menuId) {
        return dao.getMenuById(menuId);
    }

    @Override
    public void updateMenu(Menu menu) {
        dao.updateMenu(menu);
    }

    @Override
    public List<Menu> getMenuListByRoleId(int roleId) {
        return dao.getMenuListByRoleId(roleId);
    }
    @Transactional
    public void grantRoleMenu(int roleId,List<Integer> menuList){
        roleDao.deleteRoleMenu(roleId);
        for (int i=0;i<menuList.size();i++){
            dao.addRoleMenu(roleId,menuList.get(i));
        }

    }
}
