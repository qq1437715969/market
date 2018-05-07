package com.market.admin.mapper;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.market.bean.Admin;
import com.market.bean.Menu;

@Mapper
public interface AdminMapper extends BaseMapper<Admin>  {
	
	@Select("select admin_id , admin_name , pass , create_time , modify_time , status , last_login_time from tb_admin")
	@ResultMap("BaseAdminMap")
	Admin selectById(String adminId);
	
	List<Menu> getMenuByRoleId(String roleId);
	
	List<Menu> getMenuByRoleIds(String roleIds);
	
	String getRolesById(String adminId);

}
