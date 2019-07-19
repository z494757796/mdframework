package cn.mdsoftware.mdframework.dao;

import cn.mdsoftware.mdframework.bean.entity.SysLogDO;

import java.util.List;
import java.util.Map;

public interface LogDao {

	List<SysLogDO> list(Map<String,Object> map);

	int count();

	int save(SysLogDO log);

	int update(SysLogDO log);

	int remove(Long id);

	int batchRemove(List<Long> list);
}
