package cn.mdsoftware.mdframework.service;

import cn.mdsoftware.mdframework.bean.entity.UserDO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public interface UserService {
	UserDO get(Long id);
	List<UserDO> list(Map<String, Object> map);
	int count(Map<String, Object> map);
	int save(UserDO user);
	int update(UserDO user);
	int updateInfo(UserDO user);
	int remove(Long userId);
	int batchremove(List<Long> userIds);
	boolean exit(Map<String, Object> params);
	Set<String> listRoles(Long userId);
	int resetPwd(UserDO user);
}
