package com.github.gn5r.boot.security.mode;

import org.springframework.security.core.GrantedAuthority;

import com.github.gn5r.boot.security.enums.UserRole;

import lombok.Data;

@Data
public class UserRoleModel implements GrantedAuthority {

  private String roleId;
  private String roleName;

  @Override
  public String getAuthority() {
    return this.roleId;
  }

  public boolean isAdministorator() {
  	return UserRole.valueOfRoleId(roleId).isAdministorator();
  }
}
