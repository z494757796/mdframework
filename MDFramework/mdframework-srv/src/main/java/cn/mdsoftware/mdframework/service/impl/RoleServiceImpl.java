package cn.mdsoftware.mdframework.service.impl;

import cn.mdsoftware.mdframework.bean.entity.RoleDO;
import cn.mdsoftware.mdframework.bean.entity.RoleMenuDO;
import cn.mdsoftware.mdframework.dao.RoleDao;
import cn.mdsoftware.mdframework.dao.RoleMenuDao;
import cn.mdsoftware.mdframework.dao.UserDao;
import cn.mdsoftware.mdframework.dao.UserRoleDao;
import cn.mdsoftware.mdframework.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
	@Autowired
    RoleDao roleDao;
	@Autowired
    RoleMenuDao roleMenuDao;
	@Autowired
    UserDao userDao;
	@Autowired
    UserRoleDao userRoleDao;

	@Override
	public List<RoleDO> list() {
		List<RoleDO> roles = roleDao.listRole();
		return roles;
	}

	@Override
	public List<RoleDO> list(Long userId) {
		List<Long> rolesIds = userRoleDao.listRoleId(userId);
		List<RoleDO> roles = roleDao.listRole();
		for (RoleDO roleDO : roles) {
			roleDO.setRoleSign("false");
			for (Long roleId : rolesIds) {
				if (roleDO.getRoleId() == roleId) {
					roleDO.setRoleSign("true");
					break;
				}
			}
		}
		return roles;
	}

	@Transactional
	@Override
	public int save(RoleDO role) {
		int count = roleDao.save(role);
		List<Long> menuIds = role.getMenuIds();
		Long roleId = role.getRoleId();
		List<RoleMenuDO> rms = new ArrayList<>();
		for (Long menuId : menuIds) {
			RoleMenuDO rmDo = new RoleMenuDO();
			rmDo.setRoleId(roleId);
			rmDo.setMenuId(menuId);
			rms.add(rmDo);
		}
		roleMenuDao.removeByRoleId(roleId);
		if (rms.size() > 0) {
			roleMenuDao.batchSave(rms);
		}
		return count;
	}

	@Transactional
	@Override
	public int remove(Long id) {
		int count = roleDao.remove(id);
		roleMenuDao.remove(id);
		return count;
	}

	@Override
	public RoleDO get(Long id) {
		RoleDO roleDO = roleDao.get(id);
		return roleDO;
	}

	@Override
	public int update(RoleDO role) {
		int r = roleDao.update(role);
		List<Long> menuIds = role.getMenuIds();
		Long roleId = role.getRoleId();
		roleMenuDao.removeByRoleId(roleId);
		List<RoleMenuDO> rms = new ArrayList<>();
		for (Long menuId : menuIds) {
			RoleMenuDO rmDo = new RoleMenuDO();
			rmDo.setRoleId(roleId);
			rmDo.setMenuId(menuId);
			rms.add(rmDo);
		}
		roleMenuDao.removeByRoleId(roleId);
		if (rms.size() > 0) {
			roleMenuDao.batchSave(rms);
		}
		return r;
	}

}
