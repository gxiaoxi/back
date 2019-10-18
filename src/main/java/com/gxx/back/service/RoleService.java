package com.gxx.back.service;

import com.gxx.back.bean.Role;

import java.util.List;

public interface RoleService {
    List<Role> getRoleList();
    void addRole(Role role);
    void updateRole(Role role);
    Role getRoleById(int roleId);
    void deleteRole(int roleId);
    void deleteRoleMenu(int roleId);
    int roleHasUser(int roleId);
    List<Role> getUserRoleList(int userId);
}
