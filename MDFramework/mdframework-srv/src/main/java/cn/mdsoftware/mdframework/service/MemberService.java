package cn.mdsoftware.mdframework.service;

import cn.mdsoftware.mdframework.bean.entity.MemberDO;
import cn.mdsoftware.mdframework.bean.api.HttpResponse;
import com.github.pagehelper.PageInfo;
import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author ·½ÒæÖ¾
 * @email 573110463.com
 * @date 2018-11-30 15:57:00
 */
public interface MemberService {
	
	MemberDO get(Integer id);

    PageInfo<List<MemberDO>> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(MemberDO member);
	
	int update(MemberDO member);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);

    HttpResponse<PageInfo> listByWhere(Integer pageNum, Integer pageSize);

    HttpResponse saveMember(MemberDO member);

    HttpResponse updateMember(MemberDO member);
    
    HttpResponse<MemberDO> getMember(Integer id);

    HttpResponse deleteMember(Integer id);
}
