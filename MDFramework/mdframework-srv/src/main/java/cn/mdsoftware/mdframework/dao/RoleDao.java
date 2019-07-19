package cn.mdsoftware.mdframework.dao;

import cn.mdsoftware.mdframework.bean.entity.RoleDO;

import java.util.List;
import java.util.Map;

/**
 * 角色
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2017-10-02 20:24:47
 */
public interface RoleDao {

	RoleDO get(Long roleId);
	
	List<RoleDO> list(Map<String, Object> map);

	int count(Map<String, Object> map);
	
	int save(RoleDO role);
	
	int update(RoleDO role);
	
	int remove(Long roleId);
	
	int batchRemove(Long[] roleIds);

    List<RoleDO> listRole();
}
