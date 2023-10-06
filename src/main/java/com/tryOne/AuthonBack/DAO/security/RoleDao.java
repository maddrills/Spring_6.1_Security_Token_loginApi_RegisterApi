package com.tryOne.AuthonBack.DAO.security;

import com.tryOne.AuthonBack.entity.security.Role;

import java.util.Set;

public interface RoleDao {

    public Role findRoleByName(String theRoleName);

    public void addARoll(Role theRole);
}
