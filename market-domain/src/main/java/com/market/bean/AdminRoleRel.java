package com.market.bean;

import com.baomidou.mybatisplus.annotations.TableName;

@TableName("tb_admin_role_rel")
public class AdminRoleRel {

	private String adminId;
	
	private Integer roleId;

	public String getAdminId() {
		return adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

}
