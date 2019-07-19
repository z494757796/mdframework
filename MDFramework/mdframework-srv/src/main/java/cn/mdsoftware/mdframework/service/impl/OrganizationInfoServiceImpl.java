package cn.mdsoftware.mdframework.service.impl;

import cn.mdsoftware.mdframework.bean.api.HttpResponse;
import cn.mdsoftware.mdframework.common.enums.HttpResponseEnum;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.mdsoftware.mdframework.bean.api.HttpResponse;
import com.github.pagehelper.PageInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.mdsoftware.mdframework.dao.OrganizationInfoDao;
import cn.mdsoftware.mdframework.bean.entity.OrganizationInfoDO;
import cn.mdsoftware.mdframework.service.OrganizationInfoService;



@Service
public class OrganizationInfoServiceImpl implements OrganizationInfoService {
	@Autowired
	private OrganizationInfoDao organizationInfoDao;
	
	@Override
	public OrganizationInfoDO get(Integer id){
		return organizationInfoDao.get(id);
	}
	
	@Override
	public PageInfo<List<OrganizationInfoDO>> list(Map<String, Object> map){
	    Integer offset = (Integer)map.get("offset");
        Integer limit = (Integer)map.get("limit");
        if(offset != null && limit != null){
            PageHelper.offsetPage(offset,limit);
        }
        List<OrganizationInfoDO> list = organizationInfoDao.list(map);
		return new PageInfo(list);
	}

	@Override
	public  List<OrganizationInfoDO> tree(Map<String, Object> map){
        List<OrganizationInfoDO> list = organizationInfoDao.list(map);
		return list;
	}

	@Override
	public int count(Map<String, Object> map){
		return organizationInfoDao.count(map);
	}
	
	@Override
	public int save(OrganizationInfoDO organizationInfo){
		return organizationInfoDao.save(organizationInfo);
	}
	
	@Override
	public int update(OrganizationInfoDO organizationInfo){
		return organizationInfoDao.update(organizationInfo);
	}
	
	@Override
	public int remove(Integer id){
		return organizationInfoDao.remove(id);
	}
	
	@Override
	public int batchRemove(Integer[] ids){
		return organizationInfoDao.batchRemove(ids);
	}

    @Override
    public HttpResponse<PageInfo> listByWhere(Integer pageNum, Integer pageSize){
        if(pageNum != null && pageSize != null){
            PageHelper.startPage(pageNum,pageSize);
        }
        List<OrganizationInfoDO> list = organizationInfoDao.list(new HashMap<String,Object>(){{

        }});
        if(list.size() > 0){
            return HttpResponse.createBySuccess(new PageInfo(list));
        }
        return HttpResponse.createByErrorResponseEnum(HttpResponseEnum.NO_RECORDS);
    }

    @Override
    public HttpResponse saveOrganizationInfo(OrganizationInfoDO organizationInfo){
        int result = organizationInfoDao.save(organizationInfo);
        if(result == 0){
            return HttpResponse.createByErrorMessage("保存失败");
        }
        return HttpResponse.createBySuccess("保存成功");
    }

    @Override
    public HttpResponse updateOrganizationInfo(OrganizationInfoDO organizationInfo){
        int result = organizationInfoDao.update(organizationInfo);
        if(result == 0){
            return HttpResponse.createByErrorMessage("更新失败");
        }
        return HttpResponse.createBySuccess("更新成功");
    }

    @Override
    public HttpResponse<OrganizationInfoDO> getOrganizationInfo(Integer id){
        OrganizationInfoDO organizationInfo = organizationInfoDao.get(id);
        if(organizationInfo != null){
            return HttpResponse.createBySuccess(organizationInfo);
        }
        return HttpResponse.createByErrorResponseEnum(HttpResponseEnum.NO_RECORDS);
    }

    @Override
    public HttpResponse deleteOrganizationInfo(Integer id){
        int result = organizationInfoDao.remove(id);
        if(result == 0){
            return HttpResponse.createByErrorMessage("删除失败");
        }
        return HttpResponse.createBySuccess("删除成功");
    }
	
}
