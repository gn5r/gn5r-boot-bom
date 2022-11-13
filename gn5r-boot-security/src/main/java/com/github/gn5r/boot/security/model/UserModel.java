package com.github.gn5r.boot.security.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

@Data
public class UserModel implements UserDetails {

  private String userId;
  private String userName;
  private String email;
  private String password;
  private boolean deleted;
  private List<UserRoleModel> authorities;

  /**
   * ユーザー名を取得する
   * <p>
   * ゲッターである{@linkplain UserModel#getUsername()}は<code>userId</code>を取得する
   * </p>
   * 
   * @return ユーザー名
   */
  public String getUserName() {
    return this.userName;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.authorities;
  }

  @Override
  public String getPassword() {
    return this.password;
  }

  /**
   * ユーザーIDを取得する
   * <p>
   * {@linkplain UserDetails}ではユーザーIDがusernameと定義されている
   * </p>
   * 
   * @return ユーザーID
   * @see UserDetails
   */
  @Override
  public String getUsername() {
    return this.userId;
  }

  @Override
  public boolean isAccountNonExpired() {
    return !this.deleted;
  }

  @Override
  public boolean isAccountNonLocked() {
    return !this.deleted;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return !this.deleted;
  }

  @Override
  public boolean isEnabled() {
    return !this.deleted;
  }

}
