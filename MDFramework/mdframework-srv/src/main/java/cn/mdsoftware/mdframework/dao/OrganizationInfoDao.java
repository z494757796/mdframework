package cn.mdsoftware.mdframework.dao;

import cn.mdsoftware.mdframework.bean.entity.OrganizationInfoDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 组织架构
 * @author ·½ÒæÖ¾
 * @email 573110463.com
 * @date 2018-12-07 15:24:13
 */
@Mapper
public interface OrganizationInfoDao {

	OrganizationInfoDO get(Integer id);
	
	List<OrganizationInfoDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(OrganizationInfoDO organizationInfo);
	
	int update(OrganizationInfoDO organizationInfo);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
