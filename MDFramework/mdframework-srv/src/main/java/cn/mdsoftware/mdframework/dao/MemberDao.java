package cn.mdsoftware.mdframework.dao;

import cn.mdsoftware.mdframework.bean.entity.MemberDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * @author ·½ÒæÖ¾
 * @email 573110463.com
 * @date 2018-11-30 15:57:00
 */
@Mapper
public interface MemberDao {

	MemberDO get(Integer id);
	
	List<MemberDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(MemberDO member);
	
	int update(MemberDO member);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
