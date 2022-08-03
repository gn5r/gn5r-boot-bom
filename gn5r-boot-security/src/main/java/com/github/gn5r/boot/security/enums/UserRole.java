package com.github.gn5r.boot.security.enums;

import java.util.Arrays;
import java.util.List;

/**
 * ユーザーロールEnum
 * 
 * @author gn5r
 *
 */
public enum UserRole {

	/** ユーザー */
	USER("USER", "ユーザー", false),
	/** 管理者 */
	ADMIN("ADMIN", "管理者", true);
	
	private final String roleId;
	private final String roleName;
	private final boolean isAdmin;

	private UserRole(String roleId, String roleName, boolean isAdmin) {
		this.roleId = roleId;
		this.roleName = roleName;
		this.isAdmin = isAdmin;
	}
	
	/**
	 * ロールIDからEnumを取得する
	 * <p>見つからない場合は{@code null}を返却する</p>
	 * 
	 * @param roleId ロールID
	 * @return ユーザーロールEnum
	 */
	public static UserRole valueOfRoleId(String roleId) {
		return UserRole.toList().stream().filter(role -> role.roleId.equals(roleId)).findAny().orElse(null);
	}
	
	/**
	 * ロールIDを取得する
	 * 
	 * @return ロールID
	 */
	public String getRoleId() {
		return this.roleId;
	}
	
	/**
	 * ロール名を取得する
	 * 
	 * @return ロール名
	 */
	public String getRoleName() {
		return this.roleName;
	}
	
	/**
	 * 管理者権限を取得する
	 * 
	 * @return 管理者権限
	 */
	public boolean isAdministorator() {
		return this.isAdmin;
	}
	
	/**
	 * RoleEnumの配列を{@linkplain List}へ変換する
	 * 
	 * @return ロールリスト
	 */
	public static List<UserRole> toList() {
		return Arrays.asList(UserRole.values());
	}
}
