package cn.mdsoftware.mdframework.service;

import cn.mdsoftware.mdframework.bean.entity.CommParamsInfoDO;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author 110
 * @email 110@163.com
 * @date 2018-01-24 11:20:01
 */
public interface CommParamsInfoService {
	
	CommParamsInfoDO get(Integer id);
	
	List<CommParamsInfoDO> list(Map<String, Object> map);


	List<Map<String,Object>> listByUnique(Map<String, Object> map);
	List<Map<String,Object>> listByUniqueApi(Map<String, Object> map);

	int count(Map<String, Object> map);
	
	int save(CommParamsInfoDO commParamsInfo);
	
	int update(CommParamsInfoDO commParamsInfo);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
