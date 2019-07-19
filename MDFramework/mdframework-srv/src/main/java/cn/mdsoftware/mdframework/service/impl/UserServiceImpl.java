package cn.mdsoftware.mdframework.service.impl;

import cn.mdsoftware.mdframework.bean.entity.UserDO;
import cn.mdsoftware.mdframework.bean.entity.UserRoleDO;
import cn.mdsoftware.mdframework.dao.UserDao;
import cn.mdsoftware.mdframework.dao.UserRoleDao;
import cn.mdsoftware.mdframework.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Transactional
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserDao userDao;
	@Autowired
    UserRoleDao userRoleDao;
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	@Override
	public UserDO get(Long id) {
		List<Long> roleIds = userRoleDao.listRoleId(id);
		UserDO user = userDao.get(id);
		user.setroleIds(roleIds);
		return user;
	}

	@Override
	public List<UserDO> list(Map<String, Object> map) {
		return userDao.list(map);
	}

	@Override
	public int count(Map<String, Object> map) {
		return userDao.count(map);
	}

	@Transactional
	@Override
	public int save(UserDO user) {
		int count = userDao.save(user);
		Long userId = user.getUserId();
		List<Long> roles = user.getroleIds();
		userRoleDao.removeByUserId(userId);
		List<UserRoleDO> list = new ArrayList<>();
		for (Long roleId : roles) {
			UserRoleDO ur = new UserRoleDO();
			ur.setUserId(userId);
			ur.setRoleId(roleId);
			list.add(ur);
		}
		if (list.size() > 0) {
			userRoleDao.batchSave(list);
		}
		return count;
	}

	@Override
	public int update(UserDO user) {
		int r = userDao.update(user);
		Long userId = user.getUserId();
		List<Long> roles = user.getroleIds();
		userRoleDao.removeByUserId(userId);
		List<UserRoleDO> list = new ArrayList<>();
		for (Long roleId : roles) {
			UserRoleDO ur = new UserRoleDO();
			ur.setUserId(userId);
			ur.setRoleId(roleId);
			list.add(ur);
		}
		if (list.size() > 0) {
			userRoleDao.batchSave(list);
		}
		return r;
	}

	@Override
	public int updateInfo(UserDO user) {
		int r = userDao.update(user);
		return r;
	}

	@Override
	public int remove(Long userId) {
		userRoleDao.removeByUserId(userId);
		return userDao.remove(userId);
	}

	@Override
	public boolean exit(Map<String, Object> params) {
		boolean exit;
		exit = userDao.list(params).size() > 0;
		return exit;
	}

	@Override
	public Set<String> listRoles(Long userId) {
		return null;
	}

	@Override
	public int resetPwd(UserDO user) {
		int r = userDao.update(user);
		return r;
	}

	@Transactional
	@Override
	public int batchremove(List<Long> userIds) {
		int count = userDao.batchRemove(userIds);
		userRoleDao.batchRemoveByUserId(userIds);
		return count;
	}

}
