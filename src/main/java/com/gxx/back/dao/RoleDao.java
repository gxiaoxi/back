package com.gxx.back.dao;

import com.gxx.back.bean.Menu;
import com.gxx.back.bean.Role;

import java.util.List;

public interface RoleDao {
    /**
     * 获取角色列表
     * @return
     */
    List<Role> getRoleList();

    /**
     * 新增角色
     * @param role
     */
    void addRole(Role role);

    /**
     * 编辑角色
     * @param role
     */
    void updateRole(Role role);

    /**
     * 获取单条角色信息
     * @param roleId
     * @return
     */
    Role getRoleById(int roleId);

    /**
     * 删除角色
     * @param roleId
     */
    void deleteRole(int roleId);

    /**
     * 删除角色菜单关联
     * @param roleId
     */
    void deleteRoleMenu(int roleId);

    /**
     * 角色是否已被用户绑定
     * @param roleId
     * @return
     */
    int roleHasUser(int roleId);

    /**
     * 获取用户已分配的角色列表
     * @param userId
     * @return
     */
    List<Role> getUserRoleList(int userId);
}
