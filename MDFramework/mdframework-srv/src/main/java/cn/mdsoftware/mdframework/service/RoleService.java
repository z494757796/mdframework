package cn.mdsoftware.mdframework.service;

import cn.mdsoftware.mdframework.bean.entity.RoleDO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoleService {
	RoleDO get(Long id);
	List<RoleDO> list();
	int save(RoleDO role);
	int update(RoleDO role);
	int remove(Long id);
	List<RoleDO> list(Long userId);
}
