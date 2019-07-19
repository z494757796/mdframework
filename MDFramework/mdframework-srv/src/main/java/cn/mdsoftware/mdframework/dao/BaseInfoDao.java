package cn.mdsoftware.mdframework.dao;

import cn.mdsoftware.mdframework.bean.entity.BaseInfoDO;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author 110
 * @email 110@163.com
 * @date 2018-10-11 18:04:59
 */
public interface BaseInfoDao {

	BaseInfoDO get(Integer id);
	
	List<BaseInfoDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(BaseInfoDO baseInfo);
	
	int update(BaseInfoDO baseInfo);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
