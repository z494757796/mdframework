package cn.mdsoftware.mdframework.service;

import cn.mdsoftware.mdframework.bean.PageDO;
import cn.mdsoftware.mdframework.bean.entity.SysLogDO;
import cn.mdsoftware.mdframework.common.utils.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LogService {
	PageDO<SysLogDO> queryList(Query query);
	int remove(Long id);
	int batchRemove(List<Long> ids);
}
