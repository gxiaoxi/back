package com.gxx.back.service.impl;

import com.gxx.back.bean.Role;
import com.gxx.back.dao.RoleDao;
import com.gxx.back.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao dao;

    @Override
    public List<Role> getRoleList() {
        return dao.getRoleList();
    }

    @Override
    public void addRole(Role role) {
        dao.addRole(role);
    }

    @Override
    public void updateRole(Role role) {
        dao.updateRole(role);
    }

    @Override
    public Role getRoleById(int roleId) {
        return dao.getRoleById(roleId);
    }

    @Transactional
    @Override
    public void deleteRole(int roleId) {
        dao.deleteRole(roleId);
        dao.deleteRoleMenu(roleId);
    }

    @Override
    public void deleteRoleMenu(int roleId) {
        dao.deleteRoleMenu(roleId);
    }

    @Override
    public int roleHasUser(int roleId) {
        return dao.roleHasUser(roleId);
    }

    @Override
    public List<Role> getUserRoleList(int userId) {
        return dao.getUserRoleList(userId);
    }
}
