package cn.mdsoftware.mdframework.service;

import cn.mdsoftware.mdframework.bean.entity.OrganizationInfoDO;
import cn.mdsoftware.mdframework.bean.api.HttpResponse;
import com.github.pagehelper.PageInfo;
import java.util.List;
import java.util.Map;

/**
 * 组织架构
 * 
 * @author ·½ÒæÖ¾
 * @email 573110463.com
 * @date 2018-12-07 15:24:13
 */
public interface OrganizationInfoService {
	
	OrganizationInfoDO get(Integer id);

    PageInfo<List<OrganizationInfoDO>> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(OrganizationInfoDO organizationInfo);
	
	int update(OrganizationInfoDO organizationInfo);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);

    HttpResponse<PageInfo> listByWhere(Integer pageNum, Integer pageSize);

    HttpResponse saveOrganizationInfo(OrganizationInfoDO organizationInfo);

    HttpResponse updateOrganizationInfo(OrganizationInfoDO organizationInfo);
    
    HttpResponse<OrganizationInfoDO> getOrganizationInfo(Integer id);

    HttpResponse deleteOrganizationInfo(Integer id);

    List<OrganizationInfoDO> tree(Map<String, Object> map);
}
