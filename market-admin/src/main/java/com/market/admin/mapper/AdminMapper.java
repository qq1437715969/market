package com.market.admin.mapper;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.market.bean.Admin;
import com.market.bean.Menu;
import com.market.dto.AdminLoginDto;

@Mapper
public interface AdminMapper extends BaseMapper<Admin>  {
	
	@Select("select admin_id , admin_name , pass , create_time , modify_time , status , last_login_time from tb_admin where admin_id = #{adminId}")
	@ResultMap("BaseAdminMap")
	Admin selectById(@Param("adminId")String adminId);
	
	List<Menu> getMenuByRoleId(String roleId);
	
	List<Menu> getMenuByRoleIds(String roleIds);
	
	String getRolesById(String adminId);
	
	@Select("select menu_id , menu_name , menu_type, parent_id , url , count_num , create_time , modify_time  from tb_admin_menu order by count_num")
	@ResultMap("BaseMenuMap")
	List<Menu> getAllMenus();
	
	@Update("update tb_admin set last_login_time = #{loginTime,jdbcType=TIMESTAMP} where admin_id = #{adminId}")
	Integer updateLoginTime(AdminLoginDto loginDto);

}
